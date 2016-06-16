package menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.swing.DefaultComboBoxModel;

/**
 * <h1>Level selection</h1>
 * List of available game levels
 *
 * @author pawel and Marcin
 */
public class LevelSelection extends DefaultComboBoxModel {

    /**
     * path to the file which stores game levels
     */
    private final String levelPath = "levelSelection.txt";

    /**
     * amount of existing levels
     */
    private int levelAmount;

    /**
     * amount of level previously passed
     */
    protected int passed;

    /**
     * invoke method to read local data
     *
     * @param number number of already passed levels
     * @throws IOException
     */
    public LevelSelection(int number) throws IOException {

        passed = number;
        readData();
    }

    /**
     * invokes methods to read remote data
     *
     * @param number number of already passed levels
     * @param data remote data of available levels
     */
    public LevelSelection(int number, String data) {
        passed = number;
        readRemoteData(data);
    }

    /**
     * read data and invoke method which creates ComboBox
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void readData() throws FileNotFoundException, IOException {
        Properties instanceProperties = new Properties();
        FileInputStream reader = new FileInputStream(levelPath);

        try {
            instanceProperties.load(reader);
        } finally {
            reader.close();
        }

        levelAmount = Integer.parseInt(instanceProperties.getProperty("Amount"));
        makeBox(instanceProperties);

    }

    /**
     * reads remote data and invoke method which creates ComboBox
     *
     * @param data data used to create ComboBox
     */
    private void readRemoteData(String data) {
        Properties instanceProperties = new Properties();
        String[] splitSpace = data.split(" ");
        for (String splitted : splitSpace) {
            String[] splitEquals = splitted.split("=");
            instanceProperties.put(splitEquals[0], splitEquals[1]);
        }
        levelAmount = Integer.parseInt(instanceProperties.getProperty("Amount"));
        makeBox(instanceProperties);

    }

    /**
     * fills ComboBox with data
     *
     * @param prop properties instance which includes data to add
     */
    private void makeBox(Properties prop) {

        for (int i = 1; i <= levelAmount; i++) {
            if (passed + 1 >= i) {
                this.addElement((prop.getProperty(String.valueOf(i))));
            }
        }
    }
}
