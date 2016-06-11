package sokoban;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import map.GameFrame;
import menu.GameResults;
import menu.MainMenu;
import menu.Winners;

/**
 * <h1> Controller of application </h1>
 * This class manages actions performed in application
 *
 * @author marcin and pawel
 */
public class Controller implements ActionListener {

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

//    /**
//     * Parametric constructor which takes reference to current objects of the
//     * game
//     *
//     * @param refMenu reference to MainMenu object
//     */
//    public Controller(MainMenu refMenu) {
//        menu = refMenu;
//        menu.addListener(this);
//        results = new GameResults();        
//    }
    public Controller() {
        menu = new MainMenu();
        menu.addListener(this);
        results = new GameResults();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "PLAY":
//                menu.setVisible(false);
                menu.dispose();
                game = new GameFrame(menu.getLevel());
                game.addListener(this);
                game.setVisible(true);
                break;
            case "LIST":
                results.readFromFile();
                setHistoryOfTheGameText();

                if (menu.cleanerButton == null) {
                    menu.cleanerButton = new JButton();
                    menu.cleanerButton.setText("CLEAN HISTORY");
                    menu.cleanerButton.setSize(20, 20);
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
                results.saveToFile(game.textField.getText(), game.score);
                game.winner.dispose();
                menu = new MainMenu();
                menu.addListener(this);
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
            text += "<br>Player: " + winner.getNick() + ", score: " + winner.getScore();
        }

        if (!text.equals("")) {
            text += "</html>";
            menu.listLabel.setText(text);
        } else {
            menu.listLabel.setText("No history rejected");
        }
    }
}
