package superimposiblegame;

import java.awt.*;
import java.awt.event.*;

public class GameController implements WindowListener, Runnable {

    private GameView theView;       // This is where the game is drawn
    private PlayerController playerController;
    private BoardController boardController;

    private Thread animator;

    private volatile boolean running = false;
    private volatile boolean gameOver = false;
    private volatile boolean isPaused = false;
    private boolean showMenu;

    private static final int PIXEL_WIDTH = GameView.PIXEL_WIDTH;
    private static final int PIXEL_HEIGHT = GameView.PIXEL_HEIGHT;

    private int resetCounter;

    public GameController() throws HeadlessException {
        animator = null;
        isPaused = true;
        showMenu = true;

        BoardView boardView = new BoardView();
        BoardModel boardModel = new BoardModel(PIXEL_WIDTH, PIXEL_HEIGHT);
        //int START_Y_POSITION = boardController.getFloor();
        this.boardController = new BoardController(boardView, boardModel);
        this.playerController = new PlayerController(boardController); //Creates a playerController who knows how big the game is and what obstacles there are;
        this.theView = new GameView(this);

        resetCounter = 0;
        theView.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                testKey(e.getKeyCode());
            }
        });
        theView.addWindowListener(this);
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
        else if (keyCode == KeyEvent.VK_P){
            if (isPaused){
                isPaused = false;
            }
            else {
                pauseGame();
            }
        }
        else if (keyCode == KeyEvent.VK_R){
            resetGame();
        }
        if (!isPaused && !gameOver){
            if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                playerController.jump();
            }
        }
    }

    public boolean isShowMenu() {
        return showMenu;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void playerDraw(Graphics g) {
        playerController.draw(g);
    }

    public void boardDisplay(Graphics g) {
        boardController.display(g);
    }

    public int getResetCounter() {
        return resetCounter;
    }

    private void resetGame() {
        BoardView boardView = new BoardView();
        BoardModel boardModel = new BoardModel(PIXEL_WIDTH, PIXEL_HEIGHT);
        boardController = new BoardController(boardView, boardModel);
        int START_Y_POSITION = boardController.getFloor();
        playerController = new PlayerController(boardController);
        if (gameOver){
            gameOver = false;
        }
        resetCounter++;
    }


    public void startGame()
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
    { isPaused = true; }


    public void stopGame()
    // called when the JFrame is closing
    { running = false; }


    public void run() {

        running = true;

        while (running) {
            gameUpdate();
            theView.gameRender();
            theView.paintScreen();

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
            if (playerController.willCollide()) {
                playerController.stop();
                gameOver = true;
            }
            playerController.updatePlayer();
            boardController.moveEnemies();
        }
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        stopGame();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
        pauseGame();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        resumeGame();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        resumeGame();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
