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
     *
     * @param level
     */
    public GameFrame(String level) {
        initialize(level);
    }

    /**
     *
     * @param level
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

    public void addListener(ActionListener listener) {
        scores.addListener(listener);
    }

}
