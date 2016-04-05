package map;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
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
    private Image wallImage;
    
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
        Board boardMap = new Board();
        try{
            boardMap.load(level);
        }catch (IOException e){
            e.printStackTrace();
        }
        
        loadImage(boardMap.wallPath,boardMap.characterPath);
        
//        Dimension dim = new Dimension();
        
    }
    /**
     * load specified images
     * @param wall
     * @param character 
     */
    private void loadImage(String wall, String character){
        ImageIcon wallIcon = new ImageIcon(wall);
        ImageIcon characterIcon = new ImageIcon(character);
        
        wallImage = wallIcon.getImage();
        characterImage = characterIcon.getImage();  
    }

    /**
     * override paintComponent method
     * @param g instance of Graphic class
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        g.drawImage(wallImage, 0, 0, null);
           
    }
    
    
    
}
