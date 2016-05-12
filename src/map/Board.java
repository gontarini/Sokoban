package map;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * <h1> Board configurations</h1>
 * This class stores configuration data to generate a map
 *
 * @author Pawel and Marcin
 */
public class Board {

    /**
     * path to wall image
     */
    protected String wallPath;

    /**
     * path to path image
     */
    protected String pathPath;

    /**
     * two-dimensional table containing map configuration
     */
    protected String[][] mapTable;

    /**
     * width of the board
     */
    protected int boardWidth;

    /**
     * length of the board
     */
    protected int boardHeight;

    /**
     * path to the character image
     */
    protected String characterPath;
    
    /**
     * path to the ball image
     */
    protected String ballPath;
    
    /**
     * path to the hole image
     */
    protected String holePath;
    
    /**
     * path to the end image
     */
    protected String endPath;
    
    /**
     * path to the boleHall image
     */
    protected String ballHolePath;
    
    /**
     * Loading map configuration from the specified file
     *
     * @param levelNumber level number to load from file
     * @throws java.io.FileNotFoundException
     */
    public void load(int levelNumber) throws FileNotFoundException, IOException {
        String filePath = levelNumber + ".txt";
        Properties instanceProperties = new Properties();
        FileInputStream reader = new FileInputStream(filePath);

        try {
            instanceProperties.load(reader);
        } finally {
            reader.close();
        }

        boardWidth = Integer.parseInt(instanceProperties.getProperty("width"));
        boardHeight = Integer.parseInt(instanceProperties.getProperty("height"));
        wallPath = instanceProperties.getProperty("wall");
        characterPath = instanceProperties.getProperty("character");
        pathPath = instanceProperties.getProperty("path");
        ballPath = instanceProperties.getProperty("ball");
        holePath = instanceProperties.getProperty("hole");
        endPath = instanceProperties.getProperty("end");
        ballHolePath = instanceProperties.getProperty("ballHole");

        mapTable = new String[boardHeight][boardWidth];

        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                mapTable[i][j] = instanceProperties.getProperty(i + "_" + j);
            }
        }

    }

    /**
     * creator of simple map
     *
     * @param x
     * @param y
     */
    public static void writeSimpleBoard(int x, int y) {

        String height = "height=" + y + "\n";
        String width = "width=" + x + "\n";
        String wall = "wall=sciana.jpg" + "\n";
        String character = "character=superman.gif" + "\n";
        String path = "path=path.JPG" + "\n";

        try {
            FileOutputStream fos = new FileOutputStream("2.txt");
            fos.write(height.getBytes());
            fos.write(width.getBytes());
            fos.write(wall.getBytes());
            fos.write(character.getBytes());
            fos.write(path.getBytes());

            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (i == 0 || j == 0 || i == x - 1 || j == y - 1) {

                        String s = j + "_" + i + "=W\n";
                        fos.write(s.getBytes());
                    }
                    else if ((i == x / 2 || i == x / 2 - 1) && (j == y / 2 || j == y / 2 - 1)) {
                        String s = j + "_" + i + "=W\n";
                        fos.write(s.getBytes());
                    } else if (i == 2 && j == 2) {
                        String s = j + "_" + i + "=C\n";
                        fos.write(s.getBytes());
                    } else {
                        String s = j + "_" + i + "=P\n";
                        fos.write(s.getBytes());
                    }

                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }
}
