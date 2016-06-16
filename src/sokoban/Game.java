package sokoban;

import java.awt.EventQueue;
import java.io.IOException;

/**
 * <h1>Class handling all threads in the game</h1>
 * @author pawel and Marcin
 * @since 2016-04-03
 */
public class Game {

    /**
     * makes control on all the threads gathered
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    @SuppressWarnings("Convert2Lambda")
    public static void main(String[] args) throws IOException {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Controller controller = new Controller();
            }
        });

    }

}
