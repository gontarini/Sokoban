package sokoban;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import map.GameFrame;
import menu.MainMenu;

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
     * Parametric constructor which takes reference to current objects of the
     * game
     *
     * @param refMenu reference to MainMenu object
     */
    public Controller(MainMenu refMenu) {
        menu = refMenu;
        menu.addListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("PLAY")) {
            menu.setVisible(false);
            game = new GameFrame(menu.getLevel());
            game.addListener(this);
            game.setVisible(true);
        } else if (command.equals("LIST")) {
            
            
        } else if (command.equals("EXIT")) {
            menu.setVisible(true);
            game.dispose();
        } else if (command.equals("PAUSE")){
            game.setPause(true);
        } else if (command.equals("CONTINUE")){
            game.setPause(false);
            game.setGameMapFocused();
        }
        
        
    }

}
