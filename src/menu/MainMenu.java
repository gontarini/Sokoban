package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
    public LevelSelection levelSelction;

    /**
     * Select level button
     */
    private JButton selectLevelButton;

    /**
     * pane to scroll list of levels
     */
    public JScrollPane levelPane;

    /**
     * Combo Box with available levels
     */
    public JComboBox levelBox;

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
     * history cleaner button
     */
    public JButton cleanerButton;
    
    /**
     * component which indicates whether player wants to use network
     */
    public JCheckBox checkbox;

    /**
     * class constructor
     *
     * @param number passed levels
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MainMenu(int number) {

        try {
            initialize(number);
        } catch (IOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * builds gui of the game menu
     * @throws IOException
     */
    @SuppressWarnings("Convert2Lambda")
    private void initialize(int number) throws IOException {
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
        
        checkbox = new JCheckBox("Network");
        

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
        panel.add(checkbox);
        panel.setBackground(Color.darkGray);

        add(panel, BorderLayout.NORTH);

        panel2.setVisible(false);
        panel3.setVisible(false);

        pack();
        setVisible(true);
    }

    /**
     * adding a listener to the specifed component, such as playbutton and
     * listbutton
     *
     * @param listener listener for components
     */
    public void addListener(ActionListener listener) {
        playButton.addActionListener(listener);
        listButton.addActionListener(listener);
        selectLevelButton.addActionListener(listener);
    }
    
    public void addItemListener(ItemListener listener){
        checkbox.addItemListener(listener);
    }

    /**
     * gives a selected level in level Box
     *
     * @return selected level
     */
    public String getLevel() {
        return (String) levelBox.getItemAt(levelBox.getSelectedIndex());
    }
}
