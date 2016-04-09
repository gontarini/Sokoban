package map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
public class GameMap extends JPanel {

    /**
     * Image of the wall
     */
    private BufferedImage originalImageWall;
    
    /**
     * Image of the path
     */
    private BufferedImage originalImagePath;
    
    /**
     * Image of the character
     */
    private BufferedImage originalImageCharacter;

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
            loadImage(boardMap.wallPath, boardMap.characterPath, boardMap.pathPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * load specified images
     *
     * @param wall
     * @param character
     */
    private void loadImage(String wall, String character, String path) throws IOException {
        File fileWall = new File(wall);
        originalImageWall = ImageIO.read(fileWall);
        
        File fileCharacter = new File(character);
        originalImageCharacter = ImageIO.read(fileCharacter);
        
        File filePath = new File(path);
        originalImagePath = ImageIO.read(filePath);
    }

    /**
     * children method of paintComponent for drawing board
     * @param g graphic context
     * @param xSize scale size of image (width)
     * @param ySize scale size of image (length)
     */
    private void paintMap(Graphics g, int xSize, int ySize, Graphics2D gWall, Graphics2D gCharacter, Graphics2D gPath){
        
        for(int i = 0; i<boardMap.boardWidth;i++){
            for(int j=0; j<boardMap.boardHeight;j++){
                switch(boardMap.mapTable[j][i]){
                    case("B"): //wall
                          gWall.drawImage(originalImageWall, i*xSize, j*ySize, xSize, ySize, null);
                        break;
                    case("P"): //path
                        gPath.drawImage(originalImagePath, i*xSize, j*ySize, xSize, ySize, null);
                        break;
                    case("C"): //character
                        gCharacter.drawImage(originalImageCharacter, i*xSize, j*ySize, xSize, ySize, null);
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

        
        Graphics2D gWall = originalImageWall.createGraphics();
        Graphics2D gCharacter = originalImageCharacter.createGraphics();
        Graphics2D gPath = originalImagePath.createGraphics();

        gWall = (Graphics2D) g;
        gCharacter = (Graphics2D) g;
        gPath = (Graphics2D) g;
        
        paintMap(g, xSize, ySize, gWall, gCharacter, gPath);
    }
}
