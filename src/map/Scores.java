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
     * flag to stop timer counting
     */
    private boolean timeMode;

    /**
     * time counter
     */
    private int timeCounter;

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

        time = new JLabel();

        timeCounter = 0;

        Timer timeController = new Timer(1000, null);
        timeController.start();
        
        timeController.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (timeMode == false) {
                    timeCounter += 1;
                    time.setText("Time = " + timeCounter);
                    repaint();

                    if (timeCounter == 100) {
                        timeController.stop();
                    }
                }
            }
        });
        
        JPanel insideCenterPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(pause, BorderLayout.BEFORE_FIRST_LINE);

        insideCenterPanel.add(continueButton, BorderLayout.NORTH);

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
    }
    

    /**
     * method which set time mode,
     * to be fluent or stopped
     * @param flag decision parameter
     */
    public void setTimeMode(boolean flag){
        timeMode = flag;
    }
}
