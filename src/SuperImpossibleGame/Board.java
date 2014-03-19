package SuperImpossibleGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Board extends JPanel implements Runnable {
    private static int PWIDTH = 500;
    private static int PHEIGHT = 300;

    private Thread animator;

    private Player p;
    private Obstacle obs;

    private Graphics dbg;
    private Image dbImage = null;

    private Random rand = new Random();

    private static boolean running = false;
    private boolean gameOver = false;

    private final int MOVEMENT = 10;
    private long period;



    private static final int NO_DELAYS_PER_YIELD = 16;
  /* Number of frames with a delay of 0 ms before the animation thread yields
     to other running threads. */

    private static int MAX_FRAME_SKIPS = 5;   // was 2;
    // no. of frames that can be skipped in any one animation loop
    // i.e the games state is updated but not rendered

    private long framesSkipped = 0L;


    private Point playerPoint;

    public Board(long period) throws HeadlessException {
        this.period = period;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
        obs = new Obstacle();
        p = new Player(PWIDTH, PHEIGHT); //Creates a player who knows how big the game is and what obstacles there are

        setFocusable(true);
        requestFocus();    // the JPanel now has focus which allows it to recieve keyboard evens

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                testKey(e.getKeyCode());
            }
        });
    }

    private void testKey(int keyCode) {
        if (!gameOver){
            switch (keyCode){
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    p.move(0, -MOVEMENT);
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    p.move(-MOVEMENT,0);
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    p.move(0,MOVEMENT);
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    p.move(MOVEMENT,0);
                    break;
                default:
                    p.move(0,0);
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

    public void run(){
        //Main thread
        long beforeTime, afterTime, timeDiff, sleepTime;
        long overSleepTime = 0L;
        int noDelays = 0;
        long excess = 0L;
        beforeTime = System.nanoTime();

        running = true;

        while (running){
            gameUpdate();
            gameRender();
            paintScreen();

            afterTime = System.nanoTime();
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
            int skips = 0;
            while((excess > period) && (skips < MAX_FRAME_SKIPS)) {
                excess -= period;
                gameUpdate();    // update state but don't render
                skips++;
            }
            framesSkipped += skips;
        }
        System.exit(0);
    }

    private void gameUpdate(){
        if(!gameOver){
            obs.remove(); //Removes an obstacle so that the board does not get overcrowded
            obs.add(rand.nextInt(PWIDTH),rand.nextInt(PHEIGHT)); //Adds an obstacle at a random place

            playerPoint = new Point(p.getPositionX(),p.getPositionY());

            if (obs.collide(playerPoint,p.getPlayerSize().height)){
                gameOver = true;
            }
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

        obs.draw(dbg);
        p.draw(dbg);
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
