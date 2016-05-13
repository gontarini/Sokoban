package map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * <h1>Game board</h1>
 * Class specifying outlook of the game board
 *
 * @author Pawel and Marcin
 */
public class GameMap extends JPanel implements KeyListener{

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
     * constructor
     *
     * @param level
     */
    public GameMap(int level) {
        initialize(level);
    }

    /**
     * load map configurations initialize JPanel object with an image
     *
     * @param level
     */
    private void initialize(int level) {
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
        
        if(isFocusable() == true){
            System.out.println("slucham");
        }
    }

    /**
     * load images of objects
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

        characterImage = Toolkit.getDefaultToolkit().createImage(character); //tutaj zrobilem inne wczytywanie 

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
     * "P" - load image of path, "C" - load image of character, "B" - load image of ball,
     * "H" - load image of hole, "BH" - load image of ballHole
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
                        characterLocation = new ObjectLocation(i,j);
                        break;
                    case("B"): //ball
                        g.drawImage(originalImageBall, j*xSize, i*ySize, xSize, ySize, null);
                        break;
                    case("H"): //hole
                        g.drawImage(originalImageHole, j*xSize, i*ySize, xSize, ySize, null);
                        break;
                    case("BH"): //ballHole
                        g.drawImage(originalImageBallHole, j*xSize, i*ySize, xSize, ySize, null);
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

        int xSize = panelWidth / (boardMap.boardWidth);
        int ySize = panelHeight / boardMap.boardHeight;

        paintMap(g, xSize, ySize);
         if (characterImage != null) {
                            g.drawImage(characterImage, characterLocation.getY() * xSize, characterLocation.getX() * ySize, xSize, ySize, this);
                        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
            if(isFocusable() == true){
            System.out.println("slucham");
        }
        int x,y;
        System.out.println("wlazlem");
        switch(e.getKeyCode()){
                case(KeyEvent.VK_UP):
                    x = characterLocation.getX();
                    y = characterLocation.getY();
                    
                    if("P".equals(boardMap.mapTable[x-1][y])){
                        characterLocation.set(x-1, y);
                        repaint();
                        break;
                    }
                    else if("B".equals(boardMap.mapTable[x-1][y])){
                        if("P".equals(boardMap.mapTable[x-2][y])){
                            characterLocation.set(x-1, y);
                            boardMap.mapTable[x-1][y] = "P";
                            boardMap.mapTable[x-2][y] = "B";
                            repaint();
                            break;
                        }
                        else if("H".equals(boardMap.mapTable[x-2][y])){
                            characterLocation.set(x-1,y);
                            boardMap.mapTable[x-1][y] = "P";
                            boardMap.mapTable[x-2][y] = "BH";
                            repaint();
                            break;
                        }
                        else break;
                    }
                    else if("H".equals(boardMap.mapTable[x-1][y])){
                        characterLocation.set(x-1,y);
                        repaint();
                        break;
                    }
                    else if("BH".equals(boardMap.mapTable[x-1][y])){
                        if("P".equals(boardMap.mapTable[x-2][y])){
                            characterLocation.set(x-1, y);
                           boardMap.mapTable[x-1][y] = "H";
                            boardMap.mapTable[x-2][y] = "B";
                            repaint();
                            break;
                        }
                        else if("H".equals(boardMap.mapTable[x-2][y])){
                            characterLocation.set(x-1,y);
                           boardMap.mapTable[x-1][y] = "H";
                            boardMap.mapTable[x-2][y] = "BH";
                            repaint();
                            break;
                        }
                        else break;
                    }
                    else {
                        break;
                    }
                case(KeyEvent.VK_RIGHT):
                      x = characterLocation.getX();
                    y = characterLocation.getY();
                    
                    if("P".equals(boardMap.mapTable[x][y+1])){
                        characterLocation.set(x, y+1);
                        repaint();
                        break;
                    }
                    else if("B".equals(boardMap.mapTable[x][y+1])){
                        if("P".equals(boardMap.mapTable[x][y+2])){
                            characterLocation.set(x, y+1);
                            boardMap.mapTable[x][y+1] = "P";
                            boardMap.mapTable[x][y+2] = "B";
                            repaint();
                            break;
                        }
                        else if("H".equals(boardMap.mapTable[x][y+2])){
                            characterLocation.set(x,y+1);
                            boardMap.mapTable[x][y+1] = "P";
                            boardMap.mapTable[x][y+2] = "BH";
                            repaint();
                            break;
                        }
                        else break;
                    }
                    else if("H".equals(boardMap.mapTable[x][y+1])){
                        characterLocation.set(x,y+1);
                        repaint();
                        break;
                    }
                    else if("BH".equals(boardMap.mapTable[x][y+1])){
                        if("P".equals(boardMap.mapTable[x][y+2])){
                            characterLocation.set(x, y+1);
                           boardMap.mapTable[x][y+1] = "H";
                            boardMap.mapTable[x][y+2] = "B";
                            repaint();
                            break;
                        }
                        else if("H".equals(boardMap.mapTable[x][y+2])){
                            characterLocation.set(x,y+1);
                          boardMap.mapTable[x][y+1] = "H";
                            boardMap.mapTable[x][y+2] = "BH";
                            repaint();
                            break;
                        }
                        else break;
                    }
                    else {
                        break;
                    }
                case(KeyEvent.VK_DOWN):
                     x = characterLocation.getX();
                    y = characterLocation.getY();
                    
                    if("P".equals(boardMap.mapTable[x+1][y])){
                        characterLocation.set(x+1, y);
                        repaint();
                        break;
                    }
                    else if("B".equals(boardMap.mapTable[x+1][y])){
                        if("P".equals(boardMap.mapTable[x+2][y])){
                            characterLocation.set(x+1, y);
                            boardMap.mapTable[x+1][y] = "P";
                            boardMap.mapTable[x+2][y] = "B";
                            repaint();
                            break;
                        }
                        else if("H".equals(boardMap.mapTable[x+2][y])){
                            characterLocation.set(x+1,y);
                            boardMap.mapTable[x+1][y] = "P";
                            boardMap.mapTable[x+2][y] = "BH";
                            repaint();
                            break;
                        }
                        else break;
                    }
                    else if("H".equals(boardMap.mapTable[x+1][y])){
                        characterLocation.set(x+1,y);
                        repaint();
                        break;
                    }
                    else if("BH".equals(boardMap.mapTable[x+1][y])){
                        if("P".equals(boardMap.mapTable[x+2][y])){
                            characterLocation.set(x+1, y);
                            boardMap.mapTable[x+1][y] = "H";
                            boardMap.mapTable[x+2][y] = "B";
                            repaint();
                            break;
                        }
                        else if("H".equals(boardMap.mapTable[x+2][y])){
                            characterLocation.set(x+1,y);
                            boardMap.mapTable[x+2][y] = "BH";
                            repaint();
                            break;
                        }
                        else break;
                    }
                    else {
                        break;
                    }
                case(KeyEvent.VK_LEFT):
                     x = characterLocation.getX();
                    y = characterLocation.getY();
                    
                    if("P".equals(boardMap.mapTable[x][y-1])){
                        characterLocation.set(x, y-1);
                        repaint();
                        break;
                    }
                    else if("B".equals(boardMap.mapTable[x][y-1])){
                        if("P".equals(boardMap.mapTable[x][y-2])){
                            characterLocation.set(x, y-1);
                            boardMap.mapTable[x][y-1] = "P";
                            boardMap.mapTable[x][y-2] = "B";
                            repaint();
                            break;
                        }
                        else if("H".equals(boardMap.mapTable[x][y-2])){
                            characterLocation.set(x,y-1);
                            boardMap.mapTable[x][y-1] = "P";
                            boardMap.mapTable[x][y-2] = "BH";
                            repaint();
                            break;
                        }
                        else break;
                    }
                    else if("H".equals(boardMap.mapTable[x][y-1])){
                        characterLocation.set(x,y-1);
                        repaint();
                        break;
                    }
                    else if("BH".equals(boardMap.mapTable[x][y-1])){
                        if("P".equals(boardMap.mapTable[x][y-2])){
                            characterLocation.set(x, y-1);
                            boardMap.mapTable[x][y-1] = "H";
                            boardMap.mapTable[x][y-2] = "B";
                            repaint();
                            break;
                        }
                        else if("H".equals(boardMap.mapTable[x][y-2])){
                            characterLocation.set(x,y-1);
                            boardMap.mapTable[x][y-1] = "H";
                            boardMap.mapTable[x][y-2] = "BH";
                            repaint();
                            break;
                        }
                        else break;
                    }
                    else {
                        break;
                    }
                        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
