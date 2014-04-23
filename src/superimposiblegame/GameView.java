package superimposiblegame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by pierre on 2014-04-18.
 * The Fram which the game is rendered on
 */
public class GameView extends JFrame {

    private GameController gameController;
    private JPanel gamePanel;       // This is where the game is drawn

    public static final int PIXEL_WIDTH = 800;
    public static final int PIXEL_HEIGHT = 600;

    private Graphics doubleBufferedGraphic = null;
    private Image doubleBufferedImage = null;

    private BufferedImage menuImage = null;
    private BufferedImage gameOverImage = null;

    private Font resetFont;
    private final static int FONT_SIZE = 28;
    //private int resetCounter;

    public GameView(GameController gameController){
        super("superimposiblegame");
        Container container = getContentPane(); //Creates the pane that stores the content
        this.gameController = gameController;

        gamePanel = new JPanel();

        gamePanel.setBackground(Color.WHITE);
        gamePanel.setPreferredSize(new Dimension(PIXEL_WIDTH, PIXEL_HEIGHT));

        gamePanel.setFocusable(true);
        gamePanel.requestFocus();    // the JPanel now has focus which allows it to recieve keyboard events
        loadImages();
        doubleBufferedImage = null;

        resetFont = new Font("FreesiaUFC", Font.BOLD, FONT_SIZE);
        container.add(gamePanel, "Center");
        //resetCounter = 0;
        setResizable(false);
        setVisible(true);
        pack();
    }


    /*public void addNotify()  { //TROR DEN SKALL VARA HÄR?
        super.addNotify();
        gameController.startGame();
    }*/

    public void addKeyListener(KeyListener keyListener){
        gamePanel.addKeyListener(keyListener);
    }

    /**
     * Loads the custom gameover screen and menu from the project
     */
    private void loadImages() {
        try {
            menuImage = ImageIO.read(new File("src/Images/altmenu.png"));
            gameOverImage = ImageIO.read(new File("src/Images/altover.png"));

        }   catch (IOException ex){
            System.out.println("Error: " + ex);
        }
    }

    /**
     * gets the rendered images from the contoller and combindes them to one rendered image
     */
    public void gameRender()
    {//Creates the image that is later printed out
        if (doubleBufferedImage == null){
            doubleBufferedImage = createImage(PIXEL_WIDTH, PIXEL_HEIGHT);
            if (doubleBufferedImage == null) {
                System.out.println("doubleBufferedImage is null");
                return;
            }
            else
                doubleBufferedGraphic = doubleBufferedImage.getGraphics();
        }
        doubleBufferedGraphic.setColor(Color.white);
        doubleBufferedGraphic.fillRect(0, 0, PIXEL_WIDTH, PIXEL_HEIGHT);

        gameController.boardDisplay(doubleBufferedGraphic);
        gameController.playerDraw(doubleBufferedGraphic);
        showResets(doubleBufferedGraphic);

        if (gameController.isGameOver()){
            showGameOver(doubleBufferedGraphic);
        }
        if (gameController.isShowMenu()){
            showMenu(doubleBufferedGraphic);
        }
    }

    /**
     * Show how many times the player has reseted, is incremented when the player pressed the R key
     * @param graphics
     */
    private void showResets(Graphics graphics){
        if (!gameController.isShowMenu()) {
            graphics.setColor(Color.black);
            graphics.setFont(resetFont);
            graphics.drawString("Resets: " + gameController.getResetCounter(), 10, 50);
        }
    }

    /**
     * Show the gameover screen
     * @param graphics
     */
    private void showGameOver(Graphics graphics){
        if (!gameController.isShowMenu()) {
            graphics.drawImage(gameOverImage, 200, 100, null);
        }
    }

    /**
     * Show the game menu
     * @param graphics
     */
    private void showMenu(Graphics graphics){
        graphics.drawImage(menuImage, 0, 0, null);
    }

    /**
     * Paints the rendered image from gameRender() on the screen
     */
    public void paintScreen()
    {//Takes the image created by gameRender and paints it onto the screen
        Graphics graphics;
        //Apparently the g.dispose() gives out a warning if you do not use a try-catch block
        graphics = this.getGraphics();
        if ((graphics != null) && (doubleBufferedImage != null))
            graphics.drawImage(doubleBufferedImage, 0, 0, null);
        try{
            graphics.dispose();
        }
        catch (NullPointerException e)
        { System.out.println("Graphics context error: " + e);  }
    }

}
