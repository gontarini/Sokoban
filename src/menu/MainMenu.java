package menu;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *<h1>MainMenu</h1>
 * class implementing main GUI interface
 * @author pawel
 */
public class MainMenu extends JFrame {
        
    private PlayButton playButton;
    
    private ExitButton exitButton;
    
    private ListButton listButton;
    
    public MainMenu(){
        initialize();
    }
    
    private void initialize(){
        playButton = new PlayButton();
        exitButton = new ExitButton();
        listButton = new ListButton();
                  
        playButton.setText("PLAY");
        exitButton.setText("EXIT");
        listButton.setText("LIST SCORES");
        
        playButton.setActionCommand("PLAY");
        exitButton.setActionCommand("EXIT");
        listButton.setActionCommand("LIST");
        
        playButton.addActionListener(playButton);
        exitButton.addActionListener(exitButton);
        
              
        setTitle("SOKOBAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,300);
        setLocationRelativeTo(null);
        
//        BorderLayout mainLayout = new BorderLayout(10,10);
        FlowLayout layout = new FlowLayout(100, 10, 10);
        
        JPanel panel = new JPanel(layout);
//        JPanel panel2 = new JPanel(layout);
                
        panel.add(playButton);
        panel.add(exitButton);
        panel.add(listButton);
        
        add(panel);
//        add(panel2);
        
        pack();
    }
    
    
}
