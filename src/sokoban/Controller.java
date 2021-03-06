package sokoban;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import map.GameFrame;
import menu.GameResults;
import menu.LevelSelection;
import menu.MainMenu;
import menu.Winners;

/**
 * <h1> Controller of application </h1>
 * This class manages actions performed in application
 *
 * @author marcin and pawel
 */
public class Controller implements ActionListener, ItemListener {

    /**
     * Reference to menu frame
     */
    private MainMenu menu;

    /**
     * Reference to game frame
     */
    private GameFrame game;

    /**
     * Reference to class with history of the game
     */
    private final GameResults results;

    /**
     * level already passed
     */
    private int passedLev;

    /**
     * specifing certain amount of attemp to complete current level
     */
    private int numberOfAttemps;

    /**
     * blockade box thread
     */
    private Thread t;

    /**
     * possible amount of attemps
     */
    private final int possibleAttemps;

    /**
     * attemps number frame
     */
    private JFrame frame;

    /**
     * flag that turns network options on
     */
    private boolean networkFlag;

    /**
     * instance of networkClient class
     */
    private NetworkClient networkClient;

    /**
     * initializing parameters
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Controller() {
        numberOfAttemps = 0;
        passedLev = 0;
        possibleAttemps = 3;
        menu = new MainMenu(passedLev);
        menu.addListener(this);
        menu.addItemListener(this);
        results = new GameResults();
        networkFlag = false;
    }

    /**
     * overriding a method which is responsible for handling variaties of events
     * There is a description of special events below:
     * 
     * "PLAY" - event occured by clicked the play button on. It is responsible for turning the game on.
     * "LIST" - event occured by clicked the list button on. It is responsible for presenting previous game history.
     * "SELECT" - event occured by clicked the select button on. It is responsible for extending menu options.
     * "EXIT" - event occured by clicked the exit button on. It closes whole application.
     * "PAUSE" - event occured by clicked the pause button on. It is responsible for stopping game until the continue button is clicked on.
     * "CONTINUE" - event occured by clicked the continue button on. It is responsible for resuming stopped game before.
     * "CONFIRM" - event occured by clicked the confirm button on. It is responsible for saving current score.
     * "TRY AGAIN" - event occured by clicked the "try again" button on. It lets the player to play once again.
     * @param e event occured
     */
    @Override
    @SuppressWarnings("Convert2Lambda")
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "PLAY":
                menu.dispose();
                if (networkFlag == false) {
                    game = new GameFrame(menu.getLevel(), networkFlag);
                    game.addListener(this);
                    game.setVisible(true);
                } else {
                    networkClient.sendRequest("level " + menu.getLevel());
                    game = new GameFrame(networkClient.receiveData(), networkFlag);
                    game.addListener(this);
                    game.setVisible(true);
                }

                break;
            case "LIST":
                if (networkFlag == false) {
                    results.readFromFile();
                    setHistoryOfTheGameText();
                } else {
                    networkClient.sendRequest("history");
                    results.readFromRemoteFile(networkClient.receiveData());
                    setHistoryOfTheGameText();
                }

                if (menu.cleanerButton == null) {
                    menu.cleanerButton = new JButton();
                    menu.cleanerButton.setText("CLEAN HISTORY");
                }
                menu.cleanerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("cleaned");
                        results.cleanHistory();
                        setHistoryOfTheGameText();
                    }

                });
                menu.panel2.setVisible(false);

                menu.panel3.add(menu.cleanerButton, BorderLayout.LINE_END);
                menu.panel3.setBackground(Color.LIGHT_GRAY);
                menu.panel3.add(menu.listLabel, BorderLayout.CENTER);
                menu.add(menu.panel3, BorderLayout.CENTER);
                menu.panel3.setVisible(true);

                menu.pack();
                break;

            case "SELECT":
                if (networkFlag == false) {
                    try {
                        menu.levelSelction = new LevelSelection(passedLev);
                    } catch (IOException ex) {
                        Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    networkClient.sendRequest("levels");
                    menu.levelSelction = new LevelSelection(passedLev, networkClient.receiveData());
                }
                menu.levelBox = new JComboBox(menu.levelSelction);
                menu.levelBox.setSelectedIndex(0);

                menu.levelPane = new JScrollPane(menu.levelBox);

                menu.panel2.add(menu.levelPane, BorderLayout.BEFORE_FIRST_LINE);
                menu.panel2.add(menu.playButton, BorderLayout.SOUTH);
                menu.panel2.setBackground(Color.LIGHT_GRAY);
                menu.panel2.setVisible(true);
                menu.panel3.setVisible(false);
                menu.add(menu.panel2, BorderLayout.SOUTH);
                menu.pack();
                break;

            case "EXIT":
                menu.setVisible(true);
                game.dispose();
                break;
            case "PAUSE":
                game.setPause(true);
                break;
            case "CONTINUE":
                game.setPause(false);
                game.setGameMapFocused();
                break;
            case "CONFIRM":
                if (networkFlag == false) {
                    results.saveToFile(game.textField.getText(), game.score);
                }
                else{
                    System.out.println("Saved to the remote source");
                    networkClient.sendRequest("save " + game.textField.getText()+"="+game.score);
                }
                game.winner.dispose();
                passedLev++;
                numberOfAttemps = 0;

                menu = new MainMenu(passedLev);
                menu.checkbox.setSelected(networkFlag);
                menu.addListener(this);
                menu.addItemListener(this);
                break;
            case "TRY AGAIN": //3 times to retake level
                createBox();
                break;
            default:
                break;
        }

    }

    /**
     * helpful method to set displayed text in List panel
     */
    private void setHistoryOfTheGameText() {
        String text = "<html>";

        for (Iterator it = results.winnersList.iterator(); it.hasNext();) {
            Winners winner = (Winners) it.next();
            text += "<br>" + winner.getNick() + ": " + winner.getScore();
        }

        if (!text.equals("")) {
            text += "</html>";
            menu.listLabel.setText(text);
        } else {
            menu.listLabel.setText("No history rejected");
        }
    }

    /**
     * creating blocade box which closes the program after 2 seconds, or letting
     * player to take one chance more to complete the level (usage of showBox method)
     */
    @SuppressWarnings("Convert2Lambda")
    private void createBox() {

        if (numberOfAttemps > 1) {

            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    JFrame frame = new JFrame("Blockade");
                    JPanel blockerPanel = new JPanel(new BorderLayout());
                    JLabel blockLabel = new JLabel("To much attempts, you lost...");

                    blockerPanel.setBackground(Color.LIGHT_GRAY);
                    blockerPanel.add(blockLabel, BorderLayout.CENTER);
                    frame.add(blockerPanel, BorderLayout.CENTER);
                    frame.setVisible(true);
                    frame.setSize(300, 50);
                    frame.setLocationRelativeTo(null);
                }
            });
            t.start();

            Timer time = new Timer(2000, null);
            time.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    System.exit(0);
                }
            });

            time.start();
        } else {
            numberOfAttemps++;
            game.dispose();

            if (networkFlag == false) {
                game = new GameFrame(menu.getLevel(), networkFlag);
                game.addListener(this);
                game.setVisible(true);
            } else {
                networkClient.sendRequest("level " + menu.getLevel());
                game = new GameFrame(networkClient.receiveData(), networkFlag);
                game.addListener(this);
                game.setVisible(true);
            }

            showBox();
        }
    }

    /**
     * box presenting how many attemp's left
     */
    private void showBox() {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame("Attemp number " + numberOfAttemps);
                JPanel blockerPanel = new JPanel(new BorderLayout());
                JLabel blockLabel = new JLabel("" + (possibleAttemps - numberOfAttemps) + " attemps left");

                blockerPanel.setBackground(Color.LIGHT_GRAY);
                blockerPanel.add(blockLabel, BorderLayout.CENTER);
                frame.add(blockerPanel, BorderLayout.CENTER);
                frame.setSize(200, 30);
                frame.setVisible(true);
                frame.setLocationRelativeTo(game);

            }
        });
        t.start();

        Timer time = new Timer(2000, null);
        time.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!t.isAlive()) {
                    time.stop();
                    System.out.println("interrupt ");
                    frame.dispose();
                }
            }
        });
        time.start();
    }

    /**
     * handling changes from menu checkbox
     * @param e event appeared
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == 1) {
            networkClient = new NetworkClient();
            networkFlag = true;
        } else {
            networkFlag = false;
            networkClient.sendRequest("logout");
            System.out.println(networkClient.receiveData());
        }
    }
}
