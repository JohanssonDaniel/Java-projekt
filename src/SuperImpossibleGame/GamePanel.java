package SuperImpossibleGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    private static final int PIXEL_WIDTH = 800;
    private static final int PIXEL_HEIGHT = 600;

    private Thread animator;

    private Player player;
    private Board board;

    private Graphics doubleBufferedGraphic;
    private Image doubleBufferedImage = null;

    private volatile static boolean isPaused = false;
    private volatile static boolean running = false;
    private volatile boolean gameOver = false;

    private boolean showMenu;

    private BufferedImage menuImage;
    private BufferedImage gameOverImage;

    private long period;



    private static final int NO_DELAYS_PER_YIELD = 16;
  /* Number of frames with a delay of 0 ms before the animation thread yields
     to other running threads. */

    private static int MAX_FRAME_SKIPS = 5;   // was 2;
    // no. of frames that can be skipped in any one animation loop
    // i.e the games state is updated but not rendered

    public GamePanel(long period) throws HeadlessException {
        this.period = period;

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(PIXEL_WIDTH, PIXEL_HEIGHT));

        isPaused = true;

        showMenu = true;
        try {
            menuImage = ImageIO.read(new File("src/images/gameMenu.png"));
            gameOverImage = ImageIO.read(new File("src/images/gameOver.png"));

        }   catch (IOException ex){
            System.out.println("Error: " + ex);
        }


        board = new Board(PIXEL_WIDTH, PIXEL_HEIGHT);
        player = new Player(board); //Creates a player who knows how big the game is and what obstacles there are;

        setFocusable(true);
        requestFocus();    // the JPanel now has focus which allows it to recieve keyboard events

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                testKey(e.getKeyCode());
            }
        });
    }

    private void testKey(int keyCode) {
        if (keyCode == KeyEvent.VK_M){
            if (showMenu){
                isPaused = false;
                showMenu = false;
            }
            else{
                isPaused = true;
                showMenu = true;
            }
        }
        else if (keyCode == KeyEvent.VK_ESCAPE) {
            stopGame();
        }
        else if (keyCode == KeyEvent.VK_R){
            resetGame();
        }
        if (!isPaused && !gameOver){
            if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                player.jump();
            }
        }
    }

    private void resetGame() {
        board = new Board(PIXEL_WIDTH, PIXEL_HEIGHT);
        player = new Player(board);
        if (gameOver){
            gameOver = false;
        }
    }

    public void addNotify()
    { //Start the JComponent thread
        super.addNotify();
        startGame();
    }


    private void startGame()
    {//Creates an new animator thread and then starts it
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void resumeGame()
    // called when the JFrame is activated / deiconified
    { if (!showMenu) {
        isPaused = false;
        }
    }


    public void pauseGame()
    // called when the JFrame is deactivated / iconified
    { isPaused = true;   }


    public void stopGame()
    // called when the JFrame is closing
    {  running = false;   }

    public void run() {
        //Main thread
        /*long beforeTime, afterTime, timeDiff, sleepTime;
        long overSleepTime = 0L;
        int noDelays = 0;
        long excess = 0L;
        beforeTime = System.nanoTime();*/

        running = true;

        while (running) {
            gameUpdate();
            gameRender();
            paintScreen();

            /*afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            sleepTime = (period - timeDiff) - overSleepTime;

            if (sleepTime > 0) {   // some time left in this cycle
                try {
                    Thread.sleep(sleepTime/1000000L);  // nano -> ms
                }
                catch(InterruptedException ex){}
                overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
            }
            else {    // sleepTime <= 0; the frame took longer than the period
                excess -= sleepTime;  // store excess time value
                overSleepTime = 0L;

                if (++noDelays >= NO_DELAYS_PER_YIELD) {
                    Thread.yield();   // give another thread a chance to run
                    noDelays = 0;
                }
            }

            beforeTime = System.nanoTime();

      /* If frame animation is taking too long, update the game state
         without rendering it, to get the updates/sec nearer to
         the required FPS. */
         /*   int skips = 0;
            while((excess > period) && (skips < MAX_FRAME_SKIPS)) {
                excess -= period;
                gameUpdate();    // update state but don't render
                skips++;
            }
        }*/
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            System.exit(0);
    }

    private void gameUpdate(){
        if(!gameOver && !isPaused) {
            if (player.willCollide()) {
                player.stop();
                gameOver = true;
            }
            player.updatePlayer();
            board.moveEnemiesCloser();
        }
    }

    private void gameRender()
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

        board.displayBoard(doubleBufferedGraphic);
        player.draw(doubleBufferedGraphic);

        if (showMenu){
            showMenu(doubleBufferedGraphic);
        }
        if (gameOver){
            showGameOver(doubleBufferedGraphic);
        }
    }

    private void showMenu(Graphics graphics){
        graphics.drawImage(menuImage, 0, 0, null);
    }

    private void showGameOver(Graphics graphics){
        graphics.drawImage(gameOverImage, 200, 100, null);
    }

    private void paintScreen()
    {//Takes the image created by gameRender and paints it onto the screen
        Graphics g;
        try { //Apparently the g.dispose() gives out a warning if you do not use a try-catch block
            g = this.getGraphics();
            if ((g != null) && (doubleBufferedImage != null))
                g.drawImage(doubleBufferedImage, 0, 0, null);
            g.dispose();
        }
        catch (Exception e)
        { System.out.println("Graphics context error: " + e);  }
    }
}
