package se.liu.ida.piehe154.tddd78.project.superimposiblegame.models;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers.GameController;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.OvalShape;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.SquareShape;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.TriangleShape;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pierre on 2014-04-18.
 *
 * GameModel models the game via the run function which when they will update and render
 */

public class GameModel implements Runnable {

    private Thread animator;
    private GameController theController;

    private final static int RUN_SLEEP_MS = 20;
    private final static int LOAD_BEFORE_START_MENU_MS = 200;
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

    /**
     * Starts the run thread (method)
     */
    public void startGame()
    {//Creates an new animator thread and then starts it
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void resumeGame() {
        // called when the JFrame is activated / deiconified
         if (!showMenu) {
            isPaused = false;
        }
    }


    public void pauseGame()
    // called when the JFrame is deactivated / iconified
    { isPaused = true; }


    public void stopGame()
    // called when the JFrame is closing
    { running = false; }

    @SuppressWarnings("SuppressionAnnotation")
    public void run() {

        running = true;
        startMenu();

        while (running) {
            gameUpdate();
            //theController.gameRender();
            //theController.paintScreen();

            try {
                //Thread sleeps every 20 ms
                //noinspection BusyWait
                Thread.sleep(RUN_SLEEP_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    /**
     * Makes sure other classes load before showing the menu.
     * boardChanged is used to show the start menu at the start of the game
     */

    public void startMenu(){
        try {
            Thread.sleep(LOAD_BEFORE_START_MENU_MS);
            theController.boardChanged();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asks the GameController to ask the other contollers to update themselves
     * If the player will crash in this update would mean that it is gameover
     */
    private void gameUpdate(){
        if (theController.hasPlayerWonTheGame()){
	    setGameOver(true);
	    saveCompletedLevel(theController.getChoosenMap());
	}
        if(!gameOver && !isPaused) {
	    theController.getPlayerWillCollide();
	    gameOver = theController.getGameOver();
            theController.playerUpdate();
            theController.boardMoveEnemies();
        }
    }

    private void saveCompletedLevel(String mapName) {
	File file = new File("completedMaps/completed.txt");
	try {
	    if (!file.exists()) {
		file.createNewFile();
	    }
	} catch (Exception e){
	    e.printStackTrace();
	}
	String thisLine;
	String mapUrl = "completedMaps/completed.txt";
	boolean alreadyCompleted = false;
	File inFile = new File(mapUrl);
	// open input stream test.txt for reading purpose.

	ArrayList<String> mapLines = new ArrayList<String>();

	try {
	    BufferedReader br = new BufferedReader(new java.io.FileReader(inFile));

	    while ((thisLine = br.readLine()) != null) {
		mapLines.add(thisLine);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	for (int n = 0; n < mapLines.size(); n++) {
	    thisLine = mapLines.get(n);
	    if ((thisLine.equals(mapName))) {
		alreadyCompleted = true;
	    }
	}
	if(!alreadyCompleted){
	    addToFile(mapName, file);
	}
    }

    private void addToFile(final String mapName, File file) {
	try {
	    FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(),true);
	    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	    bufferedWriter.write(mapName);
	    bufferedWriter.newLine();
	    bufferedWriter.close();
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
}
