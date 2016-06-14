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

    public LevelSelection(int number, boolean flag) throws IOException {
        if(flag == false){
            passed = number;
        readData();
        }
        else{
            
        }
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
