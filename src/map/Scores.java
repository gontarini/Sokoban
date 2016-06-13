package map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * shows current time and score on the right side of the main frame
 *
 * @author Pawel and Marcin
 */
public class Scores extends JPanel {

    /**
     * exit button
     */
    private JButton exitButton;

    /**
     * Displaying elapsed time
     */
    private JLabel time;

    /**
     * allowes usage of pause during game
     */
    private JButton pause;

    /**
     * allowes turn pasue off
     */
    private JButton continueButton;
    
    /**
     * button which let the player to take one attend more
     */
    private JButton onceAgainButton;

    /**
     * flag to stop timer counting
     */
    private boolean timeMode;

    /**
     * time counter
     */
    private int timeCounter;
    
    /**
     * flag to finish time elapse
     */
    private boolean timeStop;
    
    /**
     * variable which specifies multiplier for each level
     */
    private int variableForLevel;
    

    /**
     * constructor
     */
    public Scores() {
        initialize();

    }

    /**
     * initialize the score panel
     */
    private void initialize() {
        exitButton = new JButton("EXIT");
        exitButton.setActionCommand("EXIT");

        pause = new JButton("PAUSE");
        pause.setActionCommand("PAUSE");

        continueButton = new JButton("CONTINUE");
        continueButton.setActionCommand("CONTINUE");
        
        onceAgainButton = new JButton("TRY AGAIN");
        onceAgainButton.setActionCommand("TRY AGAIN");

        time = new JLabel();

        timeCounter = 0;

        Timer timeController = new Timer(1000, null);
        timeController.start();
        
        timeController.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (timeStop==true){
                    timeController.stop();
                }
                if (timeMode == false) {
                    timeCounter += 1;
                    time.setText("Time = " + timeCounter);
                    repaint();
                }
            }
        });
        JPanel doubleInsidePanel = new JPanel(new BorderLayout());
        JPanel insideCenterPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        centerPanel.add(pause, BorderLayout.BEFORE_FIRST_LINE);

        insideCenterPanel.add(continueButton, BorderLayout.NORTH);

        doubleInsidePanel.add(onceAgainButton, BorderLayout.NORTH);
        
        doubleInsidePanel.setBackground(Color.LIGHT_GRAY);
        
        insideCenterPanel.add(doubleInsidePanel, BorderLayout.CENTER);
        
        centerPanel.add(insideCenterPanel, BorderLayout.AFTER_LINE_ENDS);

        setLayout(new BorderLayout());
        add(exitButton, BorderLayout.SOUTH);
        add(time, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        insideCenterPanel.setBackground(Color.LIGHT_GRAY);
        this.setBackground(Color.LIGHT_GRAY);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    /**
     * method to set listeners for each encountered object above
     * @param listener listener to be set
     */
    public void addListener(ActionListener listener) {
        exitButton.addActionListener(listener);
        pause.addActionListener(listener);
        continueButton.addActionListener(listener);
        onceAgainButton.addActionListener(listener);
    }
    

    /**
     * method which set time mode,
     * to be fluent or stopped
     * @param flag decision parameter
     */
    public void setTimeMode(boolean flag){
        timeMode = flag;
    }
    
    /**
     * method responsible for measure score of the game, 
     * which is based on the time passed
     * @return score of the game
     */
    protected int getScore(){
//        int defaultTime = (int) Math.log()
        return (int) Math.pow(variableForLevel*3, 20/timeCounter); 
    }
    
    /**
     * method to finish time elapsing off
     * @param stop 
     */
    protected void finishCounting(boolean stop){ timeStop = true;}
        
    /**
     * setting multiplier for measuring score
     * @param level multiplier for current level
     */
    protected void setMulitplier(int level){
        variableForLevel = level;
    }
   
}
