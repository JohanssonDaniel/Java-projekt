package SuperImpossibleGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameComponent extends JPanel implements Runnable {
    private static int PWIDTH = 800;
    private static int PHEIGHT = 600;

    private Thread animator;

    private Player p;
    private Board board;
    private SuperImpossibleGame.Brick brick;

    private Graphics dbg;
    private Image dbImage = null;

    private static boolean isPaused = false;
    private static boolean running = false;
    private boolean gameOver = false;

    private long period;



    private static final int NO_DELAYS_PER_YIELD = 16;
  /* Number of frames with a delay of 0 ms before the animation thread yields
     to other running threads. */

    private static int MAX_FRAME_SKIPS = 5;   // was 2;
    // no. of frames that can be skipped in any one animation loop
    // i.e the games state is updated but not rendered

    public GameComponent(long period) throws HeadlessException {
        this.period = period;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
        board = new Board(PWIDTH, PHEIGHT);
        brick = new SuperImpossibleGame.Brick(PWIDTH, PHEIGHT);
        p = new Player(PWIDTH, PHEIGHT, board, brick); //Creates a player who knows how big the game is and what obstacles there are;

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
        if (!gameOver && !isPaused){
            switch (keyCode){
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    p.jump();
                    break;
                default:
                    p.move();
            }
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
    { //if (!showHelp)    // CHANGED
        isPaused = false;
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
            if (p.willCollide()) {
                p.stop();
               gameOver = true;
            }
            p.updatePlayer();
        }
    }

    private void gameRender()
    {//Creates the image that is later printed out
        if (dbImage == null){
            dbImage = createImage(PWIDTH, PHEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            }
            else
                dbg = dbImage.getGraphics();
        }
        dbg.setColor(Color.white);
        dbg.fillRect (0, 0, PWIDTH, PHEIGHT);

        board.display(dbg);
        p.draw(dbg);
        brick.draw(dbg);
    }

    private void paintScreen()
    {//Takes the image created by gameRender and paints it onto the screen
        Graphics g;
        try { //Apparently the g.dispose() gives out a warning if you do not use a try-catch block
            g = this.getGraphics();
            if ((g != null) && (dbImage != null))
                g.drawImage(dbImage, 0, 0, null);
            g.dispose();
        }
        catch (Exception e)
        { System.out.println("Graphics context error: " + e);  }
    }
}
