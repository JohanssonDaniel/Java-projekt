package se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.BoardListener;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.GameModel;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.GameView;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * The GameController is the controller that communicates the inforamtion from the Board and Player to the GameView and GameModel
 */
public class GameController extends WindowAdapter implements BoardListener {
    private GameModel theModel = new GameModel(this);
    private GameView theView = new GameView(this);

    private PlayerController playerController;
    private BoardController boardController;

    private String choosenMap;

    public enum STATE {
        MAIN_MENU,
        GAME
    };

    public STATE State = STATE.GAME;

    public GameController(String userMap) {
        this.boardController = new BoardController();
        this.playerController = new PlayerController(boardController); //Creates a playerController who knows how big the game is and what obstacles there are;

        boardController.addBoardListener(this);
        boardController.notifyListeners(); //BEHÖVER VI DENNA?

        //final File folder = new File("maps");
        //listFilesForFolder(folder);

        theView.addKeyListener(new KeyAdapter() {
            //Overrides method
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                testKey(e.getKeyCode());
            }
        });

        choosenMap = userMap;
        boardController.initMap(choosenMap);
        theView.addWindowListener(this);
        theModel.startGame();
    }


    public boolean gameState() {
        return State == STATE.GAME;
    }

    /*private void chooseMap() {
        this.boardController = new BoardController();
        this.playerController = new PlayerController(boardController); //Creates a playerController who knows how big the game is and what obstacles there are;

        boardController.addBoardListener(this);
        boardController.notifyListeners(); //BEHÖVER VI DENNA?

        //final File folder = new File("maps");
        //listFilesForFolder(folder);

        theView.addKeyListener(new KeyAdapter() {
            //Overrides method
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                testKey(e.getKeyCode());
            }
        });
    }*/

    //TODO: Möjligtvis kan vi läsa in alla .txt filer och sedan väljer användaren vilken bana den vill köra.
    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }

    /**
     * Keyboard inputs that change the position of the player (jumping) or interupts the gameProcess by pausing
     * @param keyCode Input keyCode
     */
    private void testKey(int keyCode) {
        //if (gameState()) {
            if (keyCode == KeyEvent.VK_M){ // M starts the Game, or shows the menu
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
                    boardController.notifyListeners(); //So we see the menu
                }
            }
            else if (keyCode == KeyEvent.VK_ESCAPE) { //Quit game
                theModel.stopGame();
            }
            else if (keyCode == KeyEvent.VK_P){ //Pauses/resumes the game
                if (theModel.isPaused()){
                    //isPaused = false;
                    theModel.setPaused(false);
                }
                else {
                    theModel.pauseGame();
                    boardController.notifyListeners();
                }
            }
            else if (keyCode == KeyEvent.VK_C) {
                this.State = STATE.MAIN_MENU;
            }
            else if (keyCode == KeyEvent.VK_R){ // Restarts the game
                resetGame();
            }
            if (!theModel.isPaused() && !theModel.isGameOver()){
                if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                    playerController.jump();
                }
            }
        /*} else {
            if (keyCode == KeyEvent.VK_1){
                choosenMap = "map";
                boardController.initMap(choosenMap);
                theModel.setShowMapChooser(false);
                theModel.setShowMenu(true);
                this.State = STATE.GAME;
                boardController.notifyListeners();
            } else if (keyCode == KeyEvent.VK_2) {
                choosenMap = "diehard";
                boardController.initMap(choosenMap);
                theModel.setShowMapChooser(false);
                theModel.setShowMenu(true);
                this.State = STATE.GAME;
                boardController.notifyListeners();
            }
        }*/
    }

    /**
     * resets the board and the players position and increments the resetCounter, which is later displayed in the GameView, by one
     */
    private void resetGame() {
        boardController = new BoardController();
        boardController.addBoardListener(this);
        boardController.initMap(choosenMap);

        //int START_Y_POSITION = boardController.getFloor();
        playerController = new PlayerController(boardController);
        if (theModel.isGameOver()){
            //gameOver = false;
            theModel.setGameOver(false);
        }
        //resetCounter++;
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

    public boolean isShowMapChooser(){
        return theModel.isShowMapChooser();
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

    /**
     * tells the PlayerController to check wether the player will Collide and die in the next update
     * @return if player will collidesWith
     */

    public void getPlayerWillCollide() {
	playerController.willCollide();
    }


    public boolean getGameOver(){
	return playerController.getGameOver();
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

}
