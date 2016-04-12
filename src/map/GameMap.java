package map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * <h1>Game board</h1>
 * Class specifying outlook of the game board
 *
 * @author Pawel and Marcin
 */
public class GameMap extends JPanel {

    /**
     * Image of the wall
     */
    private BufferedImage originalImageWall;

    /**
     * Image of the path
     */
    private BufferedImage originalImagePath;

//    /**
//     * Image of the character                     w zasadzie sprawa chyba niepotrzebna
//     */                                              ale się może przydać do statycznego obrazka
//    private BufferedImage originalImageCharacter;
    /**
     * configurations of the Panel read from file
     */
    private Board boardMap;

    /**
     * panel width
     */
    private int panelWidth;
    /**
     * panel length
     */
    private int panelHeight;

    /**
     * image of the character
     */
    private Image characterImage; //temporary animation

    /**
     * constructor
     *
     * @param level
     */
    public GameMap(int level) {
        initialize(level);
    }

    /**
     * load map configurations initialize JPanel object with an image
     *
     * @param level
     */
    private void initialize(int level) {
        boardMap = new Board();
        try {
            boardMap.load(level);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            loadImage(boardMap.wallPath, boardMap.characterPath, boardMap.pathPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * load specified images
     *
     * @param wall
     * @param character
     */
    private void loadImage(String wall, String character, String path) throws IOException {
        File fileWall = new File(wall);
        originalImageWall = ImageIO.read(fileWall);

        characterImage = Toolkit.getDefaultToolkit().createImage(character); //tutaj zrobilem inne wczytywanie 

        File filePath = new File(path);
        originalImagePath = ImageIO.read(filePath);
    }

    /**
     * children method of paintComponent for drawing "B" - load image of wall
     * "P" - load image of path "C" - load image of character
     *
     * @param g graphic context
     * @param xSize scale size of image (width)
     * @param ySize scale size of image (length)
     */
    private void paintMap(Graphics g, int xSize, int ySize) {

        for (int i = 0; i < boardMap.boardHeight; i++) {
            for (int j = 0; j < boardMap.boardWidth; j++) {
                switch (boardMap.mapTable[i][j]) {
                    case ("B"): //wall
                        g.drawImage(originalImageWall, j * xSize, i * ySize, xSize, ySize, null);
                        break;
                    case ("P"): //path
                        g.drawImage(originalImagePath, j * xSize, i * ySize, xSize, ySize, null);
                        break;
                    case ("C"): //character
                        if (characterImage != null) {
                            g.drawImage(characterImage, j * xSize, i * ySize, xSize, ySize, this);
                        } // przekazuje Image z normalnym kontekstem graficznym i wkazanie na jakiś domyślny ImageObserver
                        // czy jakoś tak, on chyba kontroluje poprawne wyświetlanie gifa
                        break;

                }
            }
        }
    }

    /**
     * override paintComponent method
     *
     * @param g instance of Graphic class
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        panelWidth = getWidth();
        panelHeight = getHeight();

        int xSize = panelWidth / (boardMap.boardWidth);
        int ySize = panelHeight / boardMap.boardHeight;

        paintMap(g, xSize, ySize);
    }
}
