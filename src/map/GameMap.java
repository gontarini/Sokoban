package map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * Image of the bullet
     */
    private BufferedImage originalImageBullet;

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
     * true if shot is fired
     */
    private boolean bulletFlag = false;

    /**
     * rotates and sets locations of the bullet image
     */
    private AffineTransform transform;

    /**
     * present location of the bullet
     */
    private ObjectLocation bulletLocation;

    /**
     * number of available shots
     */
    private int shotNumber = 50;

    /**
     * angle of rotation
     */
    private int angle;

    /**
     * image of bullet which will rotate
     */
    private BufferedImage temporaryBullet;

    /**
     * constructor
     *
     * @param level
     */
    public GameMap(String level) {
        initialize(level);
    }

    /**
     * load map configurations initialize JPanel object with an image
     *
     * @param level
     */
    private void initialize(String level) {
        boardMap = new Board();
        bulletLocation = new ObjectLocation(1, 1);

        try {
            boardMap.load(level);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            loadImage(boardMap.wallPath, boardMap.characterPath, boardMap.pathPath, boardMap.ballPath, boardMap.holePath, boardMap.ballHolePath, boardMap.bulletPath);
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
     * @param bullet
     */
    private void loadImage(String wall, String character, String path, String ball, String hole, String ballHole, String bullet) throws IOException {
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

        File fileBullet = new File(bullet);
        originalImageBullet = ImageIO.read(fileBullet);
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

        if (bulletFlag == true) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(temporaryBullet, transform, null);

        }

        if (characterImage != null && bulletFlag != true) {
            g.drawImage(characterImage, characterLocation.getY() * xSize + dy, characterLocation.getX() * ySize + dx, xSize, ySize, this);
        } else {
            g.drawImage(characterImage, characterLocation.getY() * xSize, characterLocation.getX() * ySize, xSize, ySize, this);
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

                case (KeyEvent.VK_W):
                    bulletLocation.set(characterLocation.getX() - 1, characterLocation.getY());
                    if (shotNumber != 0) {
                        shotNumber--;
                        if((bulletLocation.getX()<0l)||((bulletLocation.getX()-1)<0)||((bulletLocation.getX()-2)<0)){
                            break;
                        }
                        else if ("W".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()])) {                            
                            boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()] = "P";
                            repaint();
                            break;
                        } else if ("P".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()]) || "H".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()])) {
                            if ("W".equals(boardMap.mapTable[bulletLocation.getX() - 1][bulletLocation.getY()])) {
                                boardMap.mapTable[bulletLocation.getX() - 1][bulletLocation.getY()] = "P";
                                repaint();
                                break;
                            } else if ("P".equals(boardMap.mapTable[bulletLocation.getX() - 1][bulletLocation.getY()]) || "H".equals(boardMap.mapTable[bulletLocation.getX() - 1][bulletLocation.getY()])) {
                                animateBullet(e);
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    break;

                case (KeyEvent.VK_S):
                    bulletLocation.set(characterLocation.getX() + 1, characterLocation.getY());
                    if (shotNumber != 0) {
                        shotNumber--;
                        if((bulletLocation.getX()>(boardMap.boardHeight-1))||((bulletLocation.getX()+1)>(boardMap.boardHeight-1))||((bulletLocation.getX()+2)>(boardMap.boardHeight-1))){
                            break;
                        }
                        else if ("W".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()])) {
                            boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()] = "P";
                            repaint();
                            break;
                        } else if ("P".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()]) || "H".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()])) {
                            if ("W".equals(boardMap.mapTable[bulletLocation.getX() + 1][bulletLocation.getY()])) {
                                boardMap.mapTable[bulletLocation.getX() + 1][bulletLocation.getY()] = "P";
                                repaint();
                                break;
                            } else if ("P".equals(boardMap.mapTable[bulletLocation.getX() + 1][bulletLocation.getY()]) || "H".equals(boardMap.mapTable[bulletLocation.getX() + 1][bulletLocation.getY()])) {
                                animateBullet(e);
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    break;

                case (KeyEvent.VK_D):
                    bulletLocation.set(characterLocation.getX(), characterLocation.getY()+1);
                    if (shotNumber != 0) {
                        shotNumber--;
                        if((bulletLocation.getY()>(boardMap.boardWidth-1))||((bulletLocation.getY()+1)>(boardMap.boardWidth-1))||((bulletLocation.getY()+2)>(boardMap.boardWidth-1))){
                            break;
                        }
                        else if ("W".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()])) {
                            boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()] = "P";
                            repaint();
                            break;
                        } else if ("P".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()]) || "H".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()])) {
                            if ("W".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()+1])) {
                                boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()+1] = "P";
                                repaint();
                                break;
                            } else if ("P".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()+1]) || "H".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()+1])) {
                                animateBullet(e);
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    break;
                    
                    case (KeyEvent.VK_A):
                    bulletLocation.set(characterLocation.getX(), characterLocation.getY()-1);
                    if (shotNumber != 0) {
                        shotNumber--;
                        if((bulletLocation.getY()<0)||((bulletLocation.getY()-1)<0)||((bulletLocation.getY()-2)<0)){
                            break;
                        }
                        else if ("W".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()])) {
                            boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()] = "P";
                            repaint();
                            break;
                        } else if ("P".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()]) || "H".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()])) {
                            if ("W".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()-1])) {
                                boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()-1] = "P";
                                repaint();
                                break;
                            } else if ("P".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()-1]) || "H".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()-1])) {
                                animateBullet(e);
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * variable used in frame counting
     */
    private int i = 1;

    /**
     * method which is responsible for animating character and ball
     * @param typed typed key
     */
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

    /**
     * method which is responsible for animating bullets
     * @param typed 
     */
    private void animateBullet(KeyEvent typed) {

        flag = true;
        Timer timer = new Timer(frameNumber, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float interval = (float) (1.0 / (float) (frameNumber));
                
                double xb, yb;

                if (pcFlag == false) {

                    switch (typed.getKeyCode()) {
                        case (KeyEvent.VK_W):

                            temporaryBullet = originalImageBullet;
                            transform = AffineTransform.getTranslateInstance(bulletLocation.getY() * xSize + dy, bulletLocation.getX() * ySize + dx);
                            xb = temporaryBullet.getWidth();
                            yb = temporaryBullet.getHeight();
  
                            bulletFlag = true;
                            progressHeight += -interval * (float) (ySize);
                            transform.scale(1.0 / (xb / xSize), 1.0 / (yb / ySize));
                            i++;
                            repaint();
                            if (i == frameNumber) {
                                timer.stop();
                                progressHeight = 0;
                                dx = 0;
                                dy=0;
                                flag = false;
                                bulletFlag = false;
                                i = 1;
                                bulletLocation.set(bulletLocation.getX() - 1, bulletLocation.getY());

                                if((bulletLocation.getX()-2)<0){
                                    break;
                                }
                                else if ("P".equals(boardMap.mapTable[bulletLocation.getX() - 1][bulletLocation.getY()]) || "H".equals(boardMap.mapTable[bulletLocation.getX() - 1][bulletLocation.getY()])) {
                                    animateBullet(typed);
                                } else if ("W".equals(boardMap.mapTable[bulletLocation.getX() - 1][bulletLocation.getY()])) {
                                    boardMap.mapTable[bulletLocation.getX() - 1][bulletLocation.getY()] = "P";
                                    repaint();
                                    break;
                                }
                                else {
                                    break;
                                }
                            }

                            break;

                        case (KeyEvent.VK_S):

                            temporaryBullet = originalImageBullet;
                            transform = AffineTransform.getTranslateInstance((bulletLocation.getY()+1) * xSize + dy, (bulletLocation.getX()+1) * ySize + dx);
                            xb = temporaryBullet.getWidth();
                            yb = temporaryBullet.getHeight();

                            transform.quadrantRotate(2);
                            bulletFlag = true;

                            progressHeight += +interval * (float) (ySize);
                            transform.scale(1.0 / (xb / xSize), 1.0 / (yb / ySize));
                            i++;
                            repaint();

                            if (i == frameNumber) {
                                timer.stop();
                                progressHeight = 0;
                                dx = 0;
                                dy=0;
                                flag = false;
                                bulletFlag = false;
                                i = 1;
                                bulletLocation.set(bulletLocation.getX() + 1, bulletLocation.getY());

                                if((bulletLocation.getX()+2)>(boardMap.boardHeight-1)){
                                    break;
                                }
                                else if ("P".equals(boardMap.mapTable[bulletLocation.getX() + 1][bulletLocation.getY()]) || "H".equals(boardMap.mapTable[bulletLocation.getX() + 1][bulletLocation.getY()])) {
                                    animateBullet(typed);
                                } else if ("W".equals(boardMap.mapTable[bulletLocation.getX() + 1][bulletLocation.getY()])) {
                                    boardMap.mapTable[bulletLocation.getX() + 1][bulletLocation.getY()] = "P";
                                    repaint();
                                    break;
                                }
                                else {
                                    break;
                                }
                            }

                            break;

                        case (KeyEvent.VK_D):

                            temporaryBullet = originalImageBullet;
                            transform = AffineTransform.getTranslateInstance((bulletLocation.getY()+1) * xSize +dy, bulletLocation.getX() * ySize +dx );
                            xb = temporaryBullet.getWidth();
                            yb = temporaryBullet.getHeight();

                            transform.quadrantRotate(1);
                            bulletFlag = true;

                            progressWidth += +interval * (float) (xSize);
                            transform.scale(1.0 / (xb / ySize), 1.0 / (yb / xSize));
                            i++;
                            repaint();
         
                            if (i == frameNumber) {
                                timer.stop();
                                progressWidth = 0;
                                dx=0;
                                dy = 0;
                                flag = false;
                                bulletFlag = false;
                                i = 1;
                                bulletLocation.set(bulletLocation.getX(), bulletLocation.getY()+1);

                                if((bulletLocation.getY()+2)>(boardMap.boardWidth-1)){
                                    break;
                                }
                                else if ("P".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()+1]) || "H".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()+1])) {
                                    animateBullet(typed);
                                } else if ("W".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()+1])) {
                                    boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()+1] = "P";
                                    repaint();
                                    break;
                                }
                                else {
                                    break;
                                }
                            }


                            break;

                        case (KeyEvent.VK_A):

                            temporaryBullet = originalImageBullet;
                            transform = AffineTransform.getTranslateInstance(bulletLocation.getY() * xSize +dy, (bulletLocation.getX()+1) * ySize +dx );
                            xb = temporaryBullet.getWidth();
                            yb = temporaryBullet.getHeight();

                            transform.quadrantRotate(3);
                            bulletFlag = true;

                            progressWidth += -interval * (float) (xSize);
                            transform.scale(1.0 / (xb / ySize), 1.0 / (yb / xSize));
                            i++;
                            repaint();
         
                            if (i == frameNumber) {
                                timer.stop();
                                progressWidth = 0;
                                dx=0;
                                dy = 0;
                                flag = false;
                                bulletFlag = false;
                                i = 1;
                                bulletLocation.set(bulletLocation.getX(), bulletLocation.getY()-1);

                                if((bulletLocation.getY()-2)<0){
                                    break;
                                }
                                else if ("P".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()-1]) || "H".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()-1])) {
                                    animateBullet(typed);
                                } else if ("W".equals(boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()-1])) {
                                    boardMap.mapTable[bulletLocation.getX()][bulletLocation.getY()-1] = "P";
                                    repaint();
                                    break;
                                }
                                else {
                                    break;
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

    /**
     * sets menu visible based on parameter
     * @param flag 
     */
    private void setVisibility(boolean flag) {
        this.setVisible(flag);
    }

}
