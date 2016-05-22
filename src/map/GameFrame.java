package map;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * <h1>Main frame of the game</h1>
 *
 * @author Pawel and Marcin
 */
public class GameFrame extends JFrame{

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
     *
     * @param level
     */
    public GameFrame(String level) {
        initialize(level);
    }

    /**
     * basic method, which is invoke during creating object of such class
     * it makes objects contain game board with action and panel exploring game time 
     * and give opportunity to manipulate pause mode etc.
     * @param level particular level to be loaded
     */
    private void initialize(String level) {
        gameMap = new GameMap(level);
        scores = new Scores();

        add(gameMap, BorderLayout.CENTER);
        add(scores, BorderLayout.EAST);

        pack();

        setTitle("Sokoban");
        setSize(330, 330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                gameMap.requestFocusInWindow();
            }
        });
    }

    /**
     * method which gives as a parameter listener to scores fields
     * @param listener the following listener
     */
    public void addListener(ActionListener listener) {
        scores.addListener(listener);
    }

    /**
     * method helps making gameMap focused
     */
    public void setGameMapFocused(){
        gameMap.requestFocusInWindow();
    }
    /**
     * method decides whether game mode is on or off
     * if flag equals true the mode is paused, otherwise it's on
     * @param flag equal value to pcFlag
     */
    public void setPause(boolean flag){
        gameMap.pcFlag = flag;
        scores.setTimeMode(flag);
    }
}
