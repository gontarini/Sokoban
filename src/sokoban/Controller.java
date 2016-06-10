package sokoban;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
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
    private final MainMenu menu;

    /**
     * Reference to game frame
     */
    private GameFrame game;

    /**
     * Reference to class with history of the game
     */
    private final GameResults results;

    /**
     * Parametric constructor which takes reference to current objects of the
     * game
     *
     * @param refMenu reference to MainMenu object
     */
    public Controller(MainMenu refMenu) {
        menu = refMenu;
        menu.addListener(this);
        results = new GameResults();        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "PLAY":
                menu.setVisible(false);
                game = new GameFrame(menu.getLevel());
                game.addListener(this);
                game.setVisible(true);
                break;
            case "LIST":
                results.readFromFile();
                setHistoryOfTheGameText();
                menu.panel3.add(menu.listLabel, BorderLayout.CENTER);
                menu.add(menu.panel3, BorderLayout.CENTER);
                menu.panel3.setVisible(true);
                menu.panel2.setVisible(false);
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
                menu.setVisible(true);
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
            text+="</html>";
            menu.listLabel.setText(text);
        } else {
            menu.listLabel.setText("No history rejected");
        }
    }
}

