package menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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

    /**
     * Combo box of available levels
     */
    private LevelSelection levelSelction;

    /**
     * Select level button
     */
    private JButton selectLevelButton;

    /**
     * pane to scroll list of levels
     */
    private JScrollPane levelPane;

    /**
     * Combo Box with available levels
     */
    private JComboBox levelBox;
    
    /**
     * label to present history of the game 
     */
    public JLabel listLabel;
    
    /**
     * panel contains listLabel
     */
    public JPanel panel3;

    /**
     * panel with comboBox
     */
    public JPanel panel2;
    /**
     * class constructor
     */
    public MainMenu() {
        try {
            initialize();
        } catch (IOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws IOException
     */
    private void initialize() throws IOException {
        selectLevelButton = new JButton();
        listButton = new JButton();
        exitButton = new JButton();

        playButton = new JButton();
        playButton.setText("PLAY");

        selectLevelButton.setText("SELECT LEVEL");
        exitButton.setText("EXIT");
        listButton.setText("SCORES");

        playButton.setActionCommand("PLAY");

        selectLevelButton.setActionCommand("SELECT");
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
        this.setLayout(new BorderLayout());

        FlowLayout flow = new FlowLayout();
        BorderLayout border = new BorderLayout();

        //panel with buttons
        JPanel panel = new JPanel(flow);

        //panel with comboBox
        panel2 = new JPanel(border);
        panel2.setVisible(false);

        //panel with list of scores
        panel3 = new JPanel(border);
        listLabel = new JLabel("List of recent scores");

        panel.add(selectLevelButton);
        panel.add(exitButton);
        panel.add(listButton);

        add(panel, BorderLayout.NORTH);

        levelSelction = new LevelSelection();
        levelBox = new JComboBox(levelSelction);
        levelBox.setSelectedIndex(0);

        levelPane = new JScrollPane(levelBox);

        panel2.add(levelPane, BorderLayout.NORTH);
        panel2.add(playButton, BorderLayout.SOUTH);

        selectLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add(panel2, BorderLayout.CENTER);
                panel2.setVisible(true);
                panel3.setVisible(false);
                pack();
            }
        });
        
        panel3.setVisible(false);

        pack();
        setVisible(true);
    }

    public void addListener(ActionListener listener) {
        playButton.addActionListener(listener);
        listButton.addActionListener(listener);
    }

    public String getLevel() {
        return (String) levelBox.getItemAt(levelBox.getSelectedIndex());
    }
}
