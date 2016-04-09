package map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * <h1>Game board</h1>
 * Class specifying outlook of the game board
 * @author Pawel and Marcin
 */
public class GameMap extends JPanel{
    
    /**
     * Image of the wall
     */
    private BufferedImage originalImageWall;
    
    /**
     * configurations of the Panel read from file
     */
    private Board boardMap; //added in order to see board configurations in paint method
    
    /**
     * panel width
     */
    private int panelWidth; //added
    /**
     * panel lenght
     */
    private int panelLength; //added
    /**
     * Image of the character
     */
    private Image characterImage;
    /**
     * constructor
     * @param level
     */
    public GameMap(int level){
        initialize(level);
    }
    
    /**
     * load map configurations
     * initialze JPanel object with an image
     * @param level
     */
    private void initialize(int level){
        boardMap = new Board();
        try{
            boardMap.load(level);
        }catch (IOException e){
            e.printStackTrace();
        }
        
        try{
            loadImage(boardMap.wallPath,boardMap.characterPath);        
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * load specified images
     * @param wall
     * @param character 
     */
    private void loadImage(String wall, String character) throws IOException{               
        File fileWall = new File(wall);
        originalImageWall = ImageIO.read(fileWall);
    }
    
    /**
     * resizing image 
     * @param originalImage
     * @param width
     * @param height
     * @return resized image
     */
    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height){
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.SCALE_SMOOTH);
        return resizedImage;
    }

    /**
     * override paintComponent method
     * @param g instance of Graphic class
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        panelWidth = getWidth();
        panelLength = getHeight();
        
        int xSize = panelWidth/(boardMap.boardWidth);
        int ySize = panelLength/boardMap.boardLength;
               
        BufferedImage resizedImageWall = resizeImage(originalImageWall,xSize,ySize);
        Graphics2D g2 = resizedImageWall.createGraphics();
        
        g2 = (Graphics2D) g;
        g2.drawImage(originalImageWall, 0, 0,xSize,ySize,null);
        g2.dispose();
    }
}
