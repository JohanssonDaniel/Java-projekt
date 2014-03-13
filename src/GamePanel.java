import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Created by DannePanne on 2014-03-13.
 */
public class GamePanel extends JPanel implements Runnable {
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


    private Point playerPoint;

    public GamePanel() throws HeadlessException {

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
        obs = new Obstacle();
        p = new Player(PWIDTH, PHEIGHT); //Creates a player who knows how big the game is and what obstacles there are

        setFocusable(true);
        requestFocus();    // the JPanel now has focus, so receives key events

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                testKey(e.getKeyCode());
            }
        });
    }

    private void testKey(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_W:
                System.out.println("W");
                p.move(0, -MOVEMENT);
                break;
            case KeyEvent.VK_A:
                System.out.println("A");
                p.move(-MOVEMENT,0);
                break;
            case KeyEvent.VK_S:
                System.out.println("S");
                p.move(0,MOVEMENT);
                break;
            case KeyEvent.VK_D:
                System.out.println("D");
                p.move(MOVEMENT,0);
                break;
            default:
                p.move(0,0);
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
        running = true;

        while (running){
            gameUpdate();
            gameRender();
            paintScreen();
        }
        System.exit(0);
    }

    private void gameUpdate(){
        obs.add(rand.nextInt(PWIDTH),rand.nextInt(PHEIGHT));

        playerPoint = new Point(p.getPositionX(),p.getPositionY());

        if (!obs.collide(playerPoint,p.getPlayerSize().height)){
            gameOver = true;
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
        g = this.getGraphics(); //Gets the combined graphics from JComponent(gameRender)
        if ((g != null) && (dbImage != null))
            g.drawImage(dbImage, 0, 0, null);
        g.dispose();

    }


}
