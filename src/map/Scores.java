package map;

import java.awt.BorderLayout;
import java.awt.Graphics;
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


    //TODO 
    //add updating timer in the right corner of that panel
    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        
    }
    
}
