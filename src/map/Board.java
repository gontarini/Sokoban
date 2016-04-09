package map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *<h1> Board configurations</h1>
 * This class stores configuration data to generate a map
 * @author Pawel and Marcin
 */
public class Board {
    /**
     * path to wall image
     */
    protected String wallPath;
    
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
    protected int boardLength;
    
    /**
     * path to the character image
     */
    protected String characterPath;
    
    /**
     * Loading map configuration from the specified file 
     * @param levelNumber level number to load from file
     * @throws java.io.FileNotFoundException
     */
    public void load(int levelNumber) throws FileNotFoundException, IOException{
        String filePath = levelNumber + ".txt";
        Properties instanceProperties = new Properties();
        FileInputStream reader = new FileInputStream(filePath);

        try{
            instanceProperties.load(reader);                  
        }finally{
            reader.close();
        }
        
        boardWidth = Integer.parseInt(instanceProperties.getProperty("width"));
        boardLength = Integer.parseInt(instanceProperties.getProperty("length"));
        wallPath = instanceProperties.getProperty("wall");
        characterPath = instanceProperties.getProperty("character");
        
        mapTable = new String[boardWidth][boardLength];

        for(int i = 0 ; i < boardWidth; i++){
            for(int j = 0; j < boardLength; j++){
                mapTable[i][j]=instanceProperties.getProperty(i+"_"+j);
            }
        }
        

    }  
}
