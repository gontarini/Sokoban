package map;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * shows current time and score on the right side of the main frame
 * @author Pawel and Marcin
 */
public class Scores extends JPanel{
    
    /**
     * exit button
     */
    private JButton exitButton;
    
    /**
     * constructor
     */
    public Scores(){
        initialize();
    }
    /**
     * initialize the score panel
     */
    private void initialize(){
        exitButton = new JButton("Exit");

        setLayout(new BorderLayout());
        JButton temp = new JButton("temp");
        add(exitButton, BorderLayout.SOUTH);
        add(temp, BorderLayout.NORTH);

    }    
}
