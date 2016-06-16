package map;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

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
     * latest score achieved
     */
    public int score;

    /**
     * button which finishes and saves particular score connected with written
     * nickname
     */
    private JButton finishedButton;
    
    /**
     * area to write player's nick
     */
    public JTextField textField; 
    
    /**
     * static variable to set button listener
     */
    private ActionListener listen;
    
    /**
     * nickname to be saved there
     */
    public JFrame winner;

    /**
     * constructor
     *
     * @param levelData
     * @param networkFlag
     */
    public GameFrame(String levelData, boolean networkFlag) {
        initialize(levelData, networkFlag);
    }

    /**
     * basic method, which is invoked during creating object of such class it
     * makes objects contain game board with action and panel exploring game
     * time and give opportunity to manipulate pause mode etc.
     *
     * @param level particular level to be loaded
     */
    private void initialize(String levelData, boolean networkFlag) {
        gameMap = new GameMap(levelData, networkFlag);
        scores = new Scores();

        int levelx = 5;
        scores.setMulitplier(levelx);
        
        gameMap.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
                if (gameMap.isVisible() != true) {
                    scores.finishCounting(true);
                    score = scores.getScore();
                    createSavingBox();
                    dispose();
                }
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
            }
        });
        add(gameMap, BorderLayout.CENTER);
        add(scores, BorderLayout.EAST);

        pack();

        setTitle("Sokoban");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                gameMap.requestFocusInWindow();
            }
        });
    }

    /**
     * method which gives as a parameter listener to scores fields
     *
     * @param listener the following listener
     */
    public void addListener(ActionListener listener) {
        scores.addListener(listener);
        listen = listener;
    }

    /**
     * method helps making gameMap focused
     */
    public void setGameMapFocused() {
        gameMap.requestFocusInWindow();
    }

    /**
     * method decides whether game mode is on or off if flag equals true the
     * mode is paused, otherwise it's on
     *
     * @param flag equal value to pcFlag
     */
    public void setPause(boolean flag) {
        gameMap.pcFlag = flag;
        scores.setTimeMode(flag);
    }
    
    /**
     * set listener for FinishedButton in order to be listen in Controller
     */
    private void setButtonListener(){
        finishedButton.addActionListener(listen);
    }
    

    /**
     * creating box after succesfully completed game
     */
    private void createSavingBox() {
        winner = new JFrame("Winner!");
        JPanel winnerPanel = new JPanel(new BorderLayout());
        JLabel winnerLabel = new JLabel("Congratulations, you won!");

        finishedButton = new JButton("Confirm");
        finishedButton.setActionCommand("CONFIRM");
        setButtonListener();
        

        JPanel textPanel = new JPanel(new BorderLayout());
        textField = new JTextField("Nickname");
        textPanel.add(textField, BorderLayout.NORTH);
        

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(finishedButton, BorderLayout.SOUTH);

        winnerPanel.setVisible(true);

        winnerPanel.add(winnerLabel, BorderLayout.NORTH);
        winnerPanel.add(textPanel, BorderLayout.CENTER);
        winnerPanel.add(buttonPanel,BorderLayout.SOUTH);
                
        winner.add(winnerPanel);
        winner.setLocationRelativeTo(null);
        winner.pack();

        winner.setSize(250, 125);
        winner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winner.setVisible(true);

    }
}
