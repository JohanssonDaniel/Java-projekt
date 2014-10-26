package se.liu.ida.piehe154.tddd78.project.superimposiblegame.models;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers.GameController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierre on 2014-04-18.
 * <p/>
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

    static final Path COMPLETED_TXT_PATH = Paths.get("completedMaps", "completed.txt");

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
    public void startGame() {//Creates an new animator thread and then starts it
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
    }

    /**
     * Makes sure other classes load before showing the menu. boardChanged is used to show the start menu at the start of the
     * game
     */

    public void startMenu() {
        try {
            Thread.sleep(LOAD_BEFORE_START_MENU_MS);
            theController.boardChanged();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asks the GameController to ask the other contollers to update themselves If the player will crash in this update would
     * mean that it is gameover
     */
    private void gameUpdate() {
        if (theController.hasPlayerWonTheGame()) {
            gameOver = true;
            saveCompletedLevel(theController.getChoosenMap());
            theController.updateMenu();
        }
        if (!gameOver && !isPaused) {
            theController.getPlayerWillCollide();
            gameOver = theController.getGameOver();
            theController.playerUpdate();
            theController.boardMoveEnemies();
        }
    }

    /**
     * Saves the name of the map if the user has won, so we can
     * keep track of which maps the user has completed.
     */

    private void saveCompletedLevel(String mapName) {
	boolean createdNewFile = false;
	try {
	    File file = new File(COMPLETED_TXT_PATH.toString());
	    if (!file.exists()) {
		createdNewFile = file.createNewFile();
	    }
	    String mapUrl = COMPLETED_TXT_PATH.toString();

	    File inFile = new File(mapUrl);
	    // open input stream test.txt for reading purpose.

	    List<String> mapLines = new ArrayList<String>();
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(inFile));
	    try {
		String thisLine = bufferedReader.readLine();
		while (thisLine != null) {
		    mapLines.add(thisLine);
		    thisLine = bufferedReader.readLine();
		}
		checkIfCompleted(mapLines, mapName, file);
	    }finally {
		bufferedReader.close();
	    }
	}catch (IOException e){
	    e.printStackTrace();
	}


	if (createdNewFile){
	    System.out.println("Created an new completed.txt");
	}
    }

    private void checkIfCompleted(List<String> mapLines, String mapName, File file) {
	boolean alreadyCompleted = false;

	for (int n = 0; n < mapLines.size(); n++) {
	    String line = mapLines.get(n);
	    if ((line.equals(mapName))) {
		alreadyCompleted = true;
	    }
	}
	if (!alreadyCompleted) {
	    addToFile(mapName, file);
	}
    }

    private void addToFile(final String mapName, File file) {
	try {
	    FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
	    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	    try {
		bufferedWriter.write(mapName);
		bufferedWriter.newLine();
	    } finally {
		bufferedWriter.close();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
