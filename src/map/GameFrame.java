package map;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * <h1>Main frame of the game</h1>
 * @author Pawel and Marcin
 */
public class GameFrame extends JFrame {

    /**
     * left panel of game frame 
     */
    private GameMap gameMap;
    
    /**
     * right panel of the frame
     */
    private Scores scores;
    /**
     * constructor
     */
    public GameFrame() {
        initialize();
    }

    /**
     * initialize frame
     */
    private void initialize() {
        gameMap = new GameMap(1); // temporar parameter indicates map level
        scores = new Scores();
        
        add(gameMap,BorderLayout.CENTER);
        add(scores,BorderLayout.EAST);
        
        pack();
        
        setTitle("Sokoban");
        setSize(330,330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

}
