package superimposiblegame;

import java.awt.*;

/**
 * Created by pierre on 2014-04-18.
 */
public class GameModel implements Runnable {

    private Thread animator;
    private GameController theController;

    private volatile boolean running = false;
    private volatile boolean gameOver = false;
    private volatile boolean isPaused = false;
    private boolean showMenu;
    private int resetCounter;

    public GameModel(GameController theController) {
        this.theController = theController;
        animator = null;
        isPaused = true;
        showMenu = true;
        resetCounter = 0;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isShowMenu() {
        return showMenu;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getResetCounter() {
        return resetCounter;
    }

    public void addResetCounter() {
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
            theController.gameRender();
            theController.paintScreen();

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
            if (theController.getPlayerWillCollider()) {
                theController.playerStop();
                gameOver = true;
            }
            //playerController.updatePlayer();
            //boardController.moveEnemies();
            theController.playerUpdate();
            theController.boardMoveEnemies();
        }
    }
}
