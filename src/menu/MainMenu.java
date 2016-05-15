package menu;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * <h1>MainMenu</h1>
 * class implementing main GUI interface
 *
 * @author pawel
 */
public class MainMenu extends JFrame {

    /**
     * Shows game board on click
     */
    public JButton playButton;

    /**
     * Close whole program
     */
    private JButton exitButton;

    /**
     * List previous scores
     */
    private JButton listButton;
    
    public MainMenu() {
        initialize();
    }
    
    private void initialize() {
        playButton = new JButton();
        exitButton = new JButton();
        listButton = new JButton();
        
        playButton.setText("PLAY");
        exitButton.setText("EXIT");
        listButton.setText("SCORES");
        
        playButton.setActionCommand("PLAY");
        exitButton.setActionCommand("EXIT");
        listButton.setActionCommand("LIST");
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        setTitle("SOKOBAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        
        FlowLayout layout = new FlowLayout(100, 10, 10);
        
        JPanel panel = new JPanel(layout);
        
        panel.add(playButton);
        panel.add(exitButton);
        panel.add(listButton);
        
        add(panel);
        
        pack();
        setVisible(true);
    }
    public void addListener(ActionListener listener) {
        playButton.addActionListener(listener);
        listButton.addActionListener(listener);
    }
    
}
