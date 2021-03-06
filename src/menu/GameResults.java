package menu;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

/**
 * save and retrieve history game
 *
 * @author Pawel and Marcin
 */
public class GameResults {

    /**
     * path to file storing game history
     */
    private final String scoresPath = "GameResults.txt";

    /**
     * list of winners objects
     */
    public ArrayList winnersList;

    /**
     * reference to winners class
     */
    private Winners winners;

    /**
     * default constructor initialising winnerList
     */
    public GameResults() {
        winnersList = new ArrayList();
    }

    /**
     * method saves player nickname and his score
     *
     * @param nickname player nickname
     * @param score player result of the game
     */
    public void saveToFile(String nickname, int score) {
        try {
            FileWriter file = new FileWriter(scoresPath, true);
            BufferedWriter out = new BufferedWriter(file);
            String temp = nickname + "=" + score;
            out.write(temp + "\r\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    /**
     * method reads history of the game saved before
     */
    public void readFromFile() {
        winnersList = new ArrayList();

        Properties instanceProperties = new Properties();
        try {
            FileInputStream reader = new FileInputStream(scoresPath);
            instanceProperties.load(reader);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException el) {
            el.printStackTrace();
        }

        if (!instanceProperties.isEmpty()) {
            Set<String> temp = instanceProperties.stringPropertyNames();

            for (String instance : temp) {
                System.out.println(instanceProperties.getProperty(instance));

                winners = new Winners(instance, Integer.valueOf(instanceProperties.getProperty(instance)));
                winnersList.add(winners);
            }
        }
    }

    /**
     * method reads remote history of the game saved before
     *
     * @param data data to read
     */
    public void readFromRemoteFile(String data) {
        winnersList = new ArrayList();

        if (data.length() > 2) {
            Properties instanceProperties = new Properties();
            String[] splitSpace = data.split(" ");
            for (String splitted : splitSpace) {
                String[] splitEquals = splitted.split("=");
                instanceProperties.put(splitEquals[0], splitEquals[1]);
            }

            if (!instanceProperties.isEmpty()) {
                Set<String> temp = instanceProperties.stringPropertyNames();

                for (String instance : temp) {
                    System.out.println(instanceProperties.getProperty(instance));

                    winners = new Winners(instance, Integer.valueOf(instanceProperties.getProperty(instance)));
                    winnersList.add(winners);
                }
            }
        }

    }

    /**
     * cleaning game history
     */
    public void cleanHistory() {
        try {
            FileOutputStream fos = new FileOutputStream(scoresPath);
            readFromFile();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}
