package se.liu.ida.piehe154.tddd78.project.superimposiblegame.views;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers.GameController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by pierre on 2014-04-18.
 * The Frame which the game is rendered on
 */
public class GameView extends JFrame {

    private GameController gameController;
    private JPanel gamePanel;       // This is where the game is drawn

    private static final int PIXEL_WIDTH = 800;
    private static final int PIXEL_HEIGHT = 600;

    private final static int RESET_STRING_X = 10;
    private final static int RESET_STRING_Y = 50;

    private final static int GAME_OVER_IMAGE_X = 200;
    private final static int GAME_OVER_IMAGE_Y = 100;

    private final static Path MENU_PATH = Paths.get("src", "Images", "altmenu.png");
    private final static Path GAME_OVER_PATH = Paths.get("src", "Images", "altover.png");
    private final static Path YOU_WON_PATH = Paths.get("src", "Images", "youwon.jpeg");

    private Graphics doubleBufferedGraphic = null;
    private Image doubleBufferedImage = null;

    private BufferedImage menuImage = null;
    private BufferedImage gameOverImage = null;
    private BufferedImage gameWonImage = null;

    private Font resetFont;
    private final static int FONT_SIZE = 28;

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
        setResizable(false);
        setVisible(true);
        pack();
    }

    public static int getPixelHeight() {
	return PIXEL_HEIGHT;
    }

    public static int getPixelWidth() {
	return PIXEL_WIDTH;
    }

    //Adds KeyListener, overrides synchronized method.
    @Override public synchronized void addKeyListener(KeyListener keyListener){
        super.addKeyListener(keyListener);
        gamePanel.addKeyListener(keyListener);
    }

    /**
     * Loads the custom gameover screen and menu from the project
     */
    private void loadImages() {

        try {
            menuImage = ImageIO.read(MENU_PATH.toFile());
            gameOverImage = ImageIO.read(GAME_OVER_PATH.toFile());
            gameWonImage = ImageIO.read(YOU_WON_PATH.toFile());

        } catch (IOException ex){
            System.out.println("Error: " + ex);
        }
    }


    public void initRender() {
        if (doubleBufferedImage == null){
            doubleBufferedImage = createImage(PIXEL_WIDTH, PIXEL_HEIGHT);
            if (doubleBufferedImage == null) {
                System.out.println("doubleBufferedImage is null");
                return;
            }
            else {
                doubleBufferedGraphic = doubleBufferedImage.getGraphics();
            }
        }
        doubleBufferedGraphic.setColor(Color.white);
        doubleBufferedGraphic.fillRect(0, 0, PIXEL_WIDTH, PIXEL_HEIGHT);
    }
    /**
     * gets the rendered images from the contoller and combindes them to one rendered image
     */
    public void gameRender()
    {//Creates the image that is later printed out
        initRender();
        gameController.boardDisplay(doubleBufferedGraphic);
        gameController.playerDraw(doubleBufferedGraphic);
        showResets(doubleBufferedGraphic);

        if (gameController.isGameOver()){
            showGameOver(doubleBufferedGraphic);
        }
        if (gameController.isShowMenu()){
            showMenu(doubleBufferedGraphic);
        }
        if (gameController.hasPlayerWonTheGame()) {
            showGameWon(doubleBufferedGraphic);
        }
    }

    /**
     * Show how many times the player has reseted, is incremented when the player pressed the R key
     * @param graphics Graphic paramater
     */

    private void showResets(Graphics graphics){
        if (!gameController.isShowMenu()) {
            graphics.setColor(Color.black);
            graphics.setFont(resetFont);
            graphics.drawString("Resets: " + gameController.getResetCounter(), RESET_STRING_X, RESET_STRING_Y);
        }
    }

    /**
     * Show the gameover screen
     * @param graphics Graphic paramater
     */

    private void showGameOver(Graphics graphics){
        if (!gameController.isShowMenu()) {
            graphics.drawImage(gameOverImage, GAME_OVER_IMAGE_X, GAME_OVER_IMAGE_Y, null);
        }
    }

    private void showGameWon(Graphics graphics) {
        if (!gameController.isShowMenu()) {
            graphics.drawImage(gameWonImage, GAME_OVER_IMAGE_X, GAME_OVER_IMAGE_Y, null);
        }
    }

    /**
     * Show the game menu
     * @param graphics Graphic paramater
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

        assert graphics != null;
        graphics.dispose();

    }

}
