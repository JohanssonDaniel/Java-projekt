package se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.BoardListener;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.GameModel;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.GameView;

import java.awt.*;
import java.awt.event.*;

/**
 * The GameController is the controller that communicates the inforamtion from the Board and Player to the GameView and GameModel
 */
public class GameController extends WindowAdapter implements BoardListener {
    private GameModel theModel = new GameModel(this);
    private GameView theView = new GameView(this);

    private PlayerController playerController;
    private BoardController boardController;
    private MenuController menuController;

    private String choosenMap;

    /**
     * Contructor for GameController, creates a new BoardController and PlayerController
     * @param userMap current map name
     * @param menuController MenuController object
     */
    public GameController(String userMap, MenuController menuController) {
        this.menuController = menuController;
        this.boardController = new BoardController(GameView.getPixelWidth());
        this.playerController = new PlayerController(boardController); //Creates a playerController who knows how big the game is and what obstacles there are;

        boardController.addBoardListener(this);
        boardController.notifyListeners();

        theView.addKeyListener(new KeyAdapter() {
            //Overrides method
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                testKey(e.getKeyCode());
            }
        });

        this.choosenMap = userMap;
        boardController.initMap(choosenMap);
        theView.addWindowListener(this);
    }

    /**
     * Keyboard inputs that change the position of the player (jumping) or interupts the gameProcess by pausing
     * @param keyCode Input keyCode
     */
    private void testKey(int keyCode) {
        if (keyCode == KeyEvent.VK_M){ // M starts the Game, or shows the menuController
            if (theModel.isShowMenu()){
                theModel.setPaused(false);
                theModel.setShowMenu(false);
            }
            else{
                theModel.setPaused(true);
                theModel.setShowMenu(true);
                boardController.notifyListeners(); //So we see the menuController
            }
        }
        else if (keyCode == KeyEvent.VK_ESCAPE) { //Quit game
            theModel.stopGame();
        }
        else if (keyCode == KeyEvent.VK_P){ //Pauses/resumes the game
            if (theModel.isPaused()){
                theModel.setPaused(false);
            }
            else {
                theModel.pauseGame();
                boardController.notifyListeners();
            }
        }
        else if (keyCode == KeyEvent.VK_R){ // Restarts the game
            resetGame();
        }
        if (!theModel.isPaused() && !theModel.isGameOver()){
            if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                playerController.jump();
            }
        }
    }

    /**
     * resets the board and the players position and increments the resetCounter, which is later displayed in the GameView, by one
     */
    private void resetGame() {
        boardController = new BoardController(theView.getWidth());
        boardController.addBoardListener(this);
        boardController.initMap(choosenMap);

        playerController = new PlayerController(boardController);
        if (theModel.isGameOver()){
            theModel.setGameOver(false);
        }
        theModel.addResetCounter();
	    theModel.startGame();
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

    public boolean hasPlayerWonTheGame() {
        return boardController.playerWonTheGame();
    }

    // GAMEMODEL USE THESE

    /**
     * Gets the rendered images from the other controllers and the GameModel so that the GameView can render them together
     * @param g Graphic paramater
     */

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

    public void updateMenu() {
        menuController.updateMenu();
    }

    /**
     * tells the PlayerController to check wether the player will Collide and die in the next update
     * Returns true if player will collidesWith
     */

    public void getPlayerWillCollide() {
	playerController.checkWillCollide();
    }


    public boolean getGameOver(){
	return playerController.isGameOver();
    }

    /**
     * Tells the other controller to update their respective models
     */
    public void playerUpdate() {
        playerController.updatePlayer();
    }

    public void boardMoveEnemies() {
        boardController.moveEnemies();
    }

    @Override
    public void boardChanged() {
        gameRender();
        paintScreen();
    }
    // Overrides WindowListener method
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        theModel.stopGame();
    }

    //Overrides WindowListener method
    public void windowIconified(WindowEvent e) {
	super.windowIconified(e);
        theModel.pauseGame();
    }

    //Overrides WindowListener method
    public void windowDeiconified(WindowEvent e) {
	super.windowDeiconified(e);
        theModel.resumeGame();
    }

    //Overrides WindowListener method
    public void windowActivated(WindowEvent e) {
	super.windowActivated(e);
        theModel.resumeGame();
    }

    public String getChoosenMap() {
	return choosenMap;
    }

    public void startGame() {
	theModel.startGame();
    }
}
