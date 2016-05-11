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
     * @param refGame reference to GameFrame object
     */
    public Controller(MainMenu refMenu, GameFrame refGame) {
        menu = refMenu;
        game = refGame;
        
        menu.addListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        System.out.println(s);
    }
    
    
}
