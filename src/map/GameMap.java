package map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * <h1>Game board</h1>
 * Class specifying outlook of the game board
 *
 * @author Pawel and Marcin
 */
public class GameMap extends JPanel implements KeyListener {

    /**
     * Image of the wall
     */
    private BufferedImage originalImageWall;

    /**
     * Image of the path
     */
    private BufferedImage originalImagePath;

    /**
     * Image of the ball
     */
    private BufferedImage originalImageBall;

    /**
     * Image of the hole
     */
    private BufferedImage originalImageHole;

    /**
     * Image of the end
     */
    private BufferedImage originalImageEnd;

    /**
     * location of the character
     */
    private ObjectLocation characterLocation;

    /**
     * Image of the ballHole
     */
    private BufferedImage originalImageBallHole;

    /**
     * flag, which is true if character is standing on the hole
     */
    /**
     * configurations of the Panel read from file
     */
    private Board boardMap;

    /**
     * panel width
     */
    private int panelWidth;
    /**
     * panel length
     */
    private int panelHeight;

    /**
     * image of the character
     */
    private Image characterImage; //temporary animation

    /**
     * variable used to count progress height of single frame
     */
    private float progressHeight = 0.0f;

    /**
     * variable used to count progress width of single frame
     */
    private float progressWidth = 0.0f;

    /**
     * current size of each image of game board
     */
    private int xSize, ySize;

    /**
     * number of frames in one second of animation
     */
    private final int frameNumber = 15;

    /**
     * flag that turns listener off if move is done
     */
    private boolean flag = false;

    /**
     * flag that turns drawing ball on
     */
    private boolean ballFlag = false;

    /**
     * current locations of the moving ball
     */
    private int xBall, yBall;

    /**
     * small parameters used during animation
     */
    private int dx, dy;

    /**
     * pause and continue flag
     */
    protected boolean pcFlag;

    /**
     * constructor 
     *
     * @param levelData variable which is level number for local mode and data for remote mode
     * @param networkFlag
     */
    public GameMap(String levelData, boolean networkFlag) {
        if(networkFlag == false){
        initialize(levelData);
        } else{
            initializeRemote(levelData);
        }
    }
    

    /**
     * load map configurations and initialize JPanel object with an image
     *
     * @param level
     */
    private void initialize(String level) {
        boardMap = new Board();
        
        try {
            boardMap.load(level);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            loadImage(boardMap.wallPath, boardMap.characterPath, boardMap.pathPath, boardMap.ballPath, boardMap.holePath, boardMap.ballHolePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        addKeyListener(this);
    }
    
    /**
     * load remote map configurations and initialize JPanel object with an image
     * @param data 
     */
    private void initializeRemote(String data){
        boardMap = new Board();
        
        boardMap.loadRemote(data);

        try {
            loadImage(boardMap.wallPath, boardMap.characterPath, boardMap.pathPath, boardMap.ballPath, boardMap.holePath, boardMap.ballHolePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        addKeyListener(this);
    }

    /**
     * load images of objects
     *
     * @param wall
     * @param character
     * @param path
     * @param ball
     * @param hole
     * @param ballHole
     */
    private void loadImage(String wall, String character, String path, String ball, String hole, String ballHole) throws IOException {
        File fileWall = new File(wall);
        originalImageWall = ImageIO.read(fileWall);
        
        characterImage = Toolkit.getDefaultToolkit().createImage(character);

        File filePath = new File(path);
        originalImagePath = ImageIO.read(filePath);

        File fileBall = new File(ball);
        originalImageBall = ImageIO.read(fileBall);

        File fileHole = new File(hole);
        originalImageHole = ImageIO.read(fileHole);

        File fileBallHole = new File(ballHole);
        originalImageBallHole = ImageIO.read(fileBallHole);
    }

    /**
     * children method of paintComponent for drawing "W" - load image of wall,
     * "P" - load image of path, "C" - load image of character, "B" - load image
     * of ball, "H" - load image of hole, "BH" - load image of ballHole
     *
     * @param g graphic context
     * @param xSize scale size of image (width)
     * @param ySize scale size of image (length)
     */
    private void paintMap(Graphics g, int xSize, int ySize) {

        for (int i = 0; i < boardMap.boardHeight; i++) {
            for (int j = 0; j < boardMap.boardWidth; j++) {
                switch (boardMap.mapTable[i][j]) {
                    case ("W"): //wall
                        g.drawImage(originalImageWall, j * xSize, i * ySize, xSize, ySize, null);
                        break;
                    case ("P"): //path
                        g.drawImage(originalImagePath, j * xSize, i * ySize, xSize, ySize, null);
                        break;
                    case ("C"): //character
                        boardMap.mapTable[i][j] = "P";
                        characterLocation = new ObjectLocation(i, j);
                        break;
                    case ("B"): //ball
                        g.drawImage(originalImageBall, j * xSize, i * ySize, xSize, ySize, null);
                        break;
                    case ("H"): //hole
                        g.drawImage(originalImageHole, j * xSize, i * ySize, xSize, ySize, null);
                        break;
                    case ("BH"): //ballHole
                        g.drawImage(originalImageBallHole, j * xSize, i * ySize, xSize, ySize, null);
                        break;

                }
            }
        }
    }

    /**
     * override paintComponent method
     *
     * @param g instance of Graphic class
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        panelWidth = getWidth();
        panelHeight = getHeight();

        xSize = panelWidth / boardMap.boardWidth;
        ySize = panelHeight / boardMap.boardHeight;

        dx = (int) progressHeight;
        dy = (int) progressWidth;

        paintMap(g, xSize, ySize);

        if (ballFlag == true) {
            g.drawImage(originalImageBall, yBall * xSize + dy, xBall * ySize + dx, xSize, ySize, null);
        }

        if (characterImage != null) {
            g.drawImage(characterImage, characterLocation.getY() * xSize + dy, characterLocation.getX() * ySize + dx, xSize, ySize, this);

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (flag == false && pcFlag == false) {
            int x, y;
            switch (e.getKeyCode()) {
                case (KeyEvent.VK_UP):
                    x = characterLocation.getX();
                    y = characterLocation.getY();

                    if ("P".equals(boardMap.mapTable[x - 1][y])) {
                        animate(e);
                        break;
                    } else if ("B".equals(boardMap.mapTable[x - 1][y])) {
                        if ("P".equals(boardMap.mapTable[x - 2][y])) {

                            ballFlag = true;
                            xBall = x - 1;
                            yBall = y;
                            animate(e);
                            break;
                        } else if ("H".equals(boardMap.mapTable[x - 2][y])) {
                            ballFlag = true;
                            xBall = x - 1;
                            yBall = y;
                            animate(e);
                            break;
                        } else {
                            break;
                        }
                    } else if ("H".equals(boardMap.mapTable[x - 1][y])) {
                        animate(e);
                        break;
                    } else if ("BH".equals(boardMap.mapTable[x - 1][y])) {
                        if ("P".equals(boardMap.mapTable[x - 2][y])) {

                            ballFlag = true;
                            xBall = x - 1;
                            yBall = y;
                            animate(e);
                            break;
                        } else if ("H".equals(boardMap.mapTable[x - 2][y])) {

                            ballFlag = true;
                            xBall = x - 1;
                            yBall = y;
                            animate(e);
                            break;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                case (KeyEvent.VK_RIGHT):
                    x = characterLocation.getX();
                    y = characterLocation.getY();

                    if ("P".equals(boardMap.mapTable[x][y + 1])) {
                        animate(e);
                        break;
                    } else if ("B".equals(boardMap.mapTable[x][y + 1])) {
                        if ("P".equals(boardMap.mapTable[x][y + 2])) {

                            ballFlag = true;
                            xBall = x;
                            yBall = y + 1;
                            animate(e);
                            break;
                        } else if ("H".equals(boardMap.mapTable[x][y + 2])) {

                            ballFlag = true;
                            xBall = x;
                            yBall = y + 1;
                            animate(e);
                            break;
                        } else {
                            break;
                        }
                    } else if ("H".equals(boardMap.mapTable[x][y + 1])) {
                        animate(e);
                        break;
                    } else if ("BH".equals(boardMap.mapTable[x][y + 1])) {
                        if ("P".equals(boardMap.mapTable[x][y + 2])) {

                            ballFlag = true;
                            xBall = x;
                            yBall = y + 1;
                            animate(e);
                            break;
                        } else if ("H".equals(boardMap.mapTable[x][y + 2])) {
                            ballFlag = true;
                            xBall = x;
                            yBall = y + 1;
                            animate(e);
                            break;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                case (KeyEvent.VK_DOWN):
                    x = characterLocation.getX();
                    y = characterLocation.getY();

                    if ("P".equals(boardMap.mapTable[x + 1][y])) {
                        animate(e);

                        break;
                    } else if ("B".equals(boardMap.mapTable[x + 1][y])) {
                        if ("P".equals(boardMap.mapTable[x + 2][y])) {
                            ballFlag = true;
                            xBall = x + 1;
                            yBall = y;
                            animate(e);
                            break;
                        } else if ("H".equals(boardMap.mapTable[x + 2][y])) {
                            ballFlag = true;
                            xBall = x + 1;
                            yBall = y;
                            animate(e);
                            break;
                        } else {
                            break;
                        }
                    } else if ("H".equals(boardMap.mapTable[x + 1][y])) {
                        animate(e);
                        break;
                    } else if ("BH".equals(boardMap.mapTable[x + 1][y])) {
                        if ("P".equals(boardMap.mapTable[x + 2][y])) {
                            ballFlag = true;
                            xBall = x + 1;
                            yBall = y;
                            animate(e);
                            break;
                        } else if ("H".equals(boardMap.mapTable[x + 2][y])) {
                            ballFlag = true;
                            xBall = x + 1;
                            yBall = y;
                            animate(e);
                            break;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                case (KeyEvent.VK_LEFT):
                    x = characterLocation.getX();
                    y = characterLocation.getY();

                    if ("P".equals(boardMap.mapTable[x][y - 1])) {
                        animate(e);
                        break;
                    } else if ("B".equals(boardMap.mapTable[x][y - 1])) {
                        if ("P".equals(boardMap.mapTable[x][y - 2])) {
                            ballFlag = true;
                            xBall = x;
                            yBall = y - 1;
                            animate(e);
                            break;
                        } else if ("H".equals(boardMap.mapTable[x][y - 2])) {
                            ballFlag = true;
                            xBall = x;
                            yBall = y - 1;
                            animate(e);
                            break;
                        } else {
                            break;
                        }
                    } else if ("H".equals(boardMap.mapTable[x][y - 1])) {
                        animate(e);
                        break;
                    } else if ("BH".equals(boardMap.mapTable[x][y - 1])) {
                        if ("P".equals(boardMap.mapTable[x][y - 2])) {
                            ballFlag = true;
                            xBall = x;
                            yBall = y - 1;
                            animate(e);
                            break;
                        } else if ("H".equals(boardMap.mapTable[x][y - 2])) {
                            ballFlag = true;
                            xBall = x;
                            yBall = y - 1;
                            animate(e);
                            break;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private int i = 1;

    private void animate(KeyEvent typed) {

        flag = true;
        Timer timer = new Timer(frameNumber, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float interval = (float) (1.0 / (float) (frameNumber));

                if (pcFlag == false) {

                    switch (typed.getKeyCode()) {
                        case (KeyEvent.VK_UP):
                            progressHeight += -interval * (float) (ySize);
                            i++;
                            repaint();
                            if (i == frameNumber) {
                                timer.stop();
                                progressHeight = 0;
                                dx = 0;
                                flag = false;
                                i = 1;
                                characterLocation.set(characterLocation.getX() - 1, characterLocation.getY());

                                if (ballFlag == true) {
                                    if ("B".equals(boardMap.mapTable[xBall][yBall])) {
                                        if ("P".equals(boardMap.mapTable[xBall - 1][yBall])) {

                                            ballFlag = false;
                                            boardMap.mapTable[xBall][yBall] = "P";
                                            boardMap.mapTable[xBall - 1][yBall] = "B";
                                            break;
                                        } else if ("H".equals(boardMap.mapTable[xBall - 1][yBall])) {
                                            ballFlag = false;
                                            boardMap.mapTable[xBall][yBall] = "P";
                                            boardMap.mapTable[xBall - 1][yBall] = "BH";
                                            boardMap.ballNumber--;
                                            break;
                                        } else {
                                            break;
                                        }
                                    } else if ("BH".equals(boardMap.mapTable[xBall][yBall])) {
                                        if ("P".equals(boardMap.mapTable[xBall - 1][yBall])) {
                                            ballFlag = false;
                                            boardMap.mapTable[xBall][yBall] = "H";
                                            boardMap.mapTable[xBall - 1][yBall] = "B";
                                            boardMap.ballNumber++;
                                            break;
                                        } else if ("H".equals(boardMap.mapTable[xBall - 1][yBall])) {

                                            ballFlag = false;
                                            boardMap.mapTable[xBall][yBall] = "H";
                                            boardMap.mapTable[xBall - 1][yBall] = "BH";
                                            break;
                                        } else {
                                            break;
                                        }
                                    }
                                }
                            }
                            break;

                        case (KeyEvent.VK_DOWN):
                            progressHeight += interval * (float) (ySize);
                            i++;
                            repaint();
                            if (i == frameNumber) {
                                timer.stop();
                                progressHeight = 0;
                                i = 1;
                                characterLocation.set(characterLocation.getX() + 1, characterLocation.getY());
                                flag = false;
                                if ("B".equals(boardMap.mapTable[xBall][yBall])) {
                                    if ("P".equals(boardMap.mapTable[xBall + 1][yBall])) {

                                        ballFlag = false;
                                        boardMap.mapTable[xBall][yBall] = "P";
                                        boardMap.mapTable[xBall + 1][yBall] = "B";
                                        break;
                                    } else if ("H".equals(boardMap.mapTable[xBall + 1][yBall])) {
                                        ballFlag = false;
                                        boardMap.mapTable[xBall][yBall] = "P";
                                        boardMap.mapTable[xBall + 1][yBall] = "BH";
                                        boardMap.ballNumber--;
                                        break;
                                    } else {
                                        break;
                                    }
                                } else if ("BH".equals(boardMap.mapTable[xBall][yBall])) {
                                    if ("P".equals(boardMap.mapTable[xBall + 1][yBall])) {
                                        ballFlag = false;
                                        boardMap.mapTable[xBall][yBall] = "H";
                                        boardMap.mapTable[xBall + 1][yBall] = "B";
                                        boardMap.ballNumber++;
                                        break;
                                    } else if ("H".equals(boardMap.mapTable[xBall + 1][yBall])) {

                                        ballFlag = false;
                                        boardMap.mapTable[xBall][yBall] = "H";
                                        boardMap.mapTable[xBall + 1][yBall] = "BH";
                                        break;
                                    } else {
                                        break;
                                    }
                                }
                            }

                            break;

                        case (KeyEvent.VK_RIGHT):

                            progressWidth += interval * (float) (xSize);
                            i++;
                            repaint();
                            if (i == frameNumber) {
                                timer.stop();
                                progressWidth = 0;
                                i = 1;
                                characterLocation.set(characterLocation.getX(), characterLocation.getY() + 1);
                                flag = false;
                                if ("B".equals(boardMap.mapTable[xBall][yBall])) {
                                    if ("P".equals(boardMap.mapTable[xBall][yBall + 1])) {

                                        ballFlag = false;
                                        boardMap.mapTable[xBall][yBall] = "P";
                                        boardMap.mapTable[xBall][yBall + 1] = "B";
                                        break;
                                    } else if ("H".equals(boardMap.mapTable[xBall][yBall + 1])) {
                                        ballFlag = false;
                                        boardMap.mapTable[xBall][yBall] = "P";
                                        boardMap.mapTable[xBall][yBall + 1] = "BH";
                                        boardMap.ballNumber--;
                                        break;
                                    } else {
                                        break;
                                    }
                                } else if ("BH".equals(boardMap.mapTable[xBall][yBall])) {
                                    if ("P".equals(boardMap.mapTable[xBall][yBall + 1])) {
                                        ballFlag = false;
                                        boardMap.mapTable[xBall][yBall] = "H";
                                        boardMap.mapTable[xBall][yBall + 1] = "B";
                                        boardMap.ballNumber++;
                                        break;
                                    } else if ("H".equals(boardMap.mapTable[xBall][yBall + 1])) {

                                        ballFlag = false;
                                        boardMap.mapTable[xBall][yBall] = "H";
                                        boardMap.mapTable[xBall][yBall + 1] = "BH";
                                        break;
                                    } else {
                                        break;
                                    }
                                }
                            }
                            break;

                        case (KeyEvent.VK_LEFT):

                            progressWidth += -interval * (float) (xSize);
                            i++;
                            repaint();
                            if (i == frameNumber) {
                                timer.stop();
                                progressWidth = 0;
                                i = 1;
                                characterLocation.set(characterLocation.getX(), characterLocation.getY() - 1);
                                flag = false;
                                if ("B".equals(boardMap.mapTable[xBall][yBall])) {
                                    if ("P".equals(boardMap.mapTable[xBall][yBall - 1])) {

                                        ballFlag = false;
                                        boardMap.mapTable[xBall][yBall] = "P";
                                        boardMap.mapTable[xBall][yBall - 1] = "B";
                                        break;
                                    } else if ("H".equals(boardMap.mapTable[xBall][yBall - 1])) {
                                        ballFlag = false;
                                        boardMap.mapTable[xBall][yBall] = "P";
                                        boardMap.mapTable[xBall][yBall - 1] = "BH";
                                        boardMap.ballNumber--;
                                        break;
                                    } else {
                                        break;
                                    }
                                } else if ("BH".equals(boardMap.mapTable[xBall][yBall])) {
                                    if ("P".equals(boardMap.mapTable[xBall][yBall - 1])) {
                                        ballFlag = false;
                                        boardMap.mapTable[xBall][yBall] = "H";
                                        boardMap.mapTable[xBall][yBall - 1] = "B";
                                        boardMap.ballNumber++;
                                        break;
                                    } else if ("H".equals(boardMap.mapTable[xBall][yBall - 1])) {

                                        ballFlag = false;
                                        boardMap.mapTable[xBall][yBall] = "H";
                                        boardMap.mapTable[xBall][yBall - 1] = "BH";
                                        break;
                                    } else {
                                        break;
                                    }
                                }
                            }
                            break;

                    }
                    if (boardMap.ballNumber == 0) {
                          setVisibility(false);
                    }
                }
            }

        });
        timer.start();
    }
    
    private void setVisibility(boolean flag){
        this.setVisible(flag);
    }

}
