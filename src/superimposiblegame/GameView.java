package superimposiblegame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by pierre on 2014-04-18.
 */
public class GameView extends JFrame {

    private JPanel gamePanel;       // This is where the game is drawn

    private static final int PIXEL_WIDTH = 800;
    private static final int PIXEL_HEIGHT = 600;

    private Graphics doubleBufferedGraphic;
    private Image doubleBufferedImage = null;

    private volatile boolean isPaused = false;
    private volatile boolean running = false;
    private volatile boolean gameOver = false;

    private boolean showMenu;

    private BufferedImage menuImage;
    private BufferedImage gameOverImage;

    private Font resetFont;
    private final static int FONT_SIZE = 28;
    private int resetCounter;

    public GameView(){
        Container container = getContentPane(); //Creates the pane that stores the content
        gamePanel = new JPanel();

        gamePanel.setBackground(Color.WHITE);
        gamePanel.setPreferredSize(new Dimension(PIXEL_WIDTH, PIXEL_HEIGHT));

        isPaused = true;

        showMenu = true;
        try {
            menuImage = ImageIO.read(new File("src/Images/altmenu.png"));
            gameOverImage = ImageIO.read(new File("src/Images/altover.png"));

        }   catch (IOException ex){
            System.out.println("Error: " + ex);
        }

        doubleBufferedImage = null;

        BoardView boardView = new BoardView();
        BoardModel boardModel = new BoardModel(PIXEL_WIDTH, PIXEL_HEIGHT);

        resetFont = new Font("FreesiaUFC", Font.BOLD, FONT_SIZE);
        resetCounter = 0;


        container.add(gamePanel, "Center");    //Adds the component board and sets its placement
    }
}
