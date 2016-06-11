package sokoban;

import java.awt.EventQueue;
import java.io.IOException;
//import menu.MainMenu;

/**
 *
 * @author pawel and Marcin
 * @since 2016-04-03
 */
public class Game {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

//                MainMenu menu = new MainMenu();
                Controller controller = new Controller();
            }
        });

    }

}
