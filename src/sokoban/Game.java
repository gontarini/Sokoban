package sokoban;

import java.awt.EventQueue;
import java.io.IOException;
import map.GameFrame;

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
                GameFrame game = new GameFrame();
                game.setVisible(true);
            }
        });

    }

}
