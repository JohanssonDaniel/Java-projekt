package superimposiblegame;

import java.awt.*;
import java.awt.event.*;

public class GameController implements WindowListener {
    private GameModel theModel = new GameModel(this);
    private GameView theView = new GameView(this);

    private PlayerController playerController;
    private BoardController boardController;

    public GameController() {
        BoardView boardView = new BoardView();
        BoardModel boardModel = new BoardModel(GameView.PIXEL_WIDTH, GameView.PIXEL_HEIGHT);

        this.boardController = new BoardController(boardView, boardModel);
        this.playerController = new PlayerController(boardController); //Creates a playerController who knows how big the game is and what obstacles there are;

        theView.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                testKey(e.getKeyCode());
            }
        });

        theView.addWindowListener(this);
        theModel.startGame();
    }

    private void testKey(int keyCode) {
        if (keyCode == KeyEvent.VK_M){
            if (theModel.isShowMenu()){
                //isPaused = false;
                //showMenu = false;
                theModel.setPaused(false);
                theModel.setShowMenu(false);
            }
            else{
                //isPaused = true;
                //showMenu = true;
                theModel.setPaused(true);
                theModel.setShowMenu(true);
            }
        }
        else if (keyCode == KeyEvent.VK_ESCAPE) {
            theModel.stopGame();
        }
        else if (keyCode == KeyEvent.VK_P){
            if (theModel.isPaused()){
                //isPaused = false;
                theModel.setPaused(false);
            }
            else {
                theModel.pauseGame();
            }
        }
        else if (keyCode == KeyEvent.VK_R){
            resetGame();
        }
        if (!theModel.isPaused() && !theModel.isGameOver()){
            if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                playerController.jump();
            }
        }
    }

    private void resetGame() {
        BoardView boardView = new BoardView();
        BoardModel boardModel = new BoardModel(GameView.PIXEL_WIDTH, GameView.PIXEL_HEIGHT);
        boardController = new BoardController(boardView, boardModel);
        int START_Y_POSITION = boardController.getFloor();
        playerController = new PlayerController(boardController);
        if (theModel.isGameOver()){
            //gameOver = false;
            theModel.setGameOver(false);
        }
        //resetCounter++;
        theModel.addResetCounter();
    }

    // GAMEVIEW USE THESE

    public boolean isGameOver(){
        return theModel.isGameOver();
    }

    public boolean isShowMenu(){
        return theModel.isShowMenu();
    }

    public int getResetCounter(){
        return theModel.getResetCounter();
    }

    // GAMEMODEL USE THESE

    public void playerDraw(Graphics g) {
        playerController.draw(g);
    }

    public void boardDisplay(Graphics g) {
        boardController.display(g);
    }

    public void gameRender() {
        theView.gameRender();
    }

    public void paintScreen() {
        theView.paintScreen();
    }

    public boolean getPlayerWillCollider() {
        return playerController.willCollide();
    }

    public void playerStop() {
        playerController.stop();
    }

    public void playerUpdate() {
        playerController.updatePlayer();
    }

    public void boardMoveEnemies() {
        boardController.moveEnemies();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        theModel.stopGame();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
        theModel.pauseGame();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        theModel.resumeGame();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        theModel.resumeGame();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
