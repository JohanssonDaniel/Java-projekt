package se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.BoardListener;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.BoardModel;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.Obstacle;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.ObstacleTypes;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.BoardView;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.GameView;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.OvalShape;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.SquareShape;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.TriangleShape;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

/**
 * Created by pierre on 2014-04-16.
 * Contains the methods that is called by the gamecontroller
 */
public class BoardController{

    private static final int BRICK_SIZE = 30;
    private static final String MAP_FOLDER = "maps";
    private static final String TXT_FILE_END = ".txt";
    private static final int ENEMIES_STARTING_X_COORD = 600;   //Enemies starts at X = 600, then we go forward.
    private final int pixelWidth;
    private BoardView theView = new BoardView();
    private BoardModel theModel = new BoardModel(GameView.getPixelHeight());

    private List<BoardListener> boardListeners = new ArrayList<BoardListener>();

    private final static char SQUARE = 's';
    private final static char OVAL = 'o';
    private final static char TRIANGLE = 't';

    private ObstacleTypes currentIntersectedObstacleType = null;

    public BoardController(int pixelWidth) {
	this.pixelWidth = pixelWidth;
    }

    /**
     * Creates enemies from a txt file, using "s", "o", "t"
     * to create different enemy objects
     * @param mapName is the name of the file
     */

    public void createEnemies(String mapName){
        String thisLine;
        Path mapPath = Paths.get(MAP_FOLDER, (mapName + TXT_FILE_END));
        File inFile = mapPath.toFile();
        // open input stream test.txt for reading purpose.

        List<String> mapLines = new ArrayList<String>();
	try {
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(inFile));
	    try {
		thisLine = bufferedReader.readLine();
		while (thisLine != null) {
		    mapLines.add(thisLine);
		    thisLine = bufferedReader.readLine();
		}

	    }finally {
		bufferedReader.close();
	    }
	}catch (IOException e){
		e.printStackTrace();
	    }

        int enemyXCoord = ENEMIES_STARTING_X_COORD;

        for (int n =0; n < mapLines.size(); n++) {
            thisLine = mapLines.get(n);
            for (int row = 0; row < thisLine.length(); row++) {
                char brick = thisLine.charAt(row);
                if (brick == SQUARE) {
                    theModel.addObstacle(new Obstacle(enemyXCoord, (theModel.getHeightOffset() - (row+1)*BRICK_SIZE),new SquareShape(),ObstacleTypes.SQUARE)); //(row+1) To get the Obstacle above the floor
                } else if (brick == OVAL) {
                    theModel.addObstacle((new Obstacle(enemyXCoord, (theModel.getHeightOffset() - (row+1)*BRICK_SIZE),new OvalShape(),ObstacleTypes.OVAL)));
                } else if (brick == TRIANGLE) {
                    theModel.addObstacle((new Obstacle(enemyXCoord, (theModel.getHeightOffset() - (row+1)*BRICK_SIZE),new TriangleShape(),ObstacleTypes.TRIANGLE)));
                }
            }
            enemyXCoord += BRICK_SIZE;
        }
    }

    /**
     * adds squareObstacles to the board which is later seen as floor by the player
     */
    public void createFloor(){
        for (int posX = 0; posX <= pixelWidth; posX += BRICK_SIZE){
            theModel.addObstacle(new Obstacle(posX,theModel.getHeightOffset(),new SquareShape(),ObstacleTypes.SQUARE));
        }
    }

    public void initMap(String map) {

        createEnemies(map);
        createFloor();
	theModel.separateEnemies();
        notifyListeners();
    }

    public void addBoardListener(BoardListener bl) {
        boardListeners.add(bl);
    }

    public void notifyListeners() {
        for (BoardListener listener : boardListeners) {
            listener.boardChanged();
        }
    }

    public boolean playerWonTheGame() {
        return theModel.isPlayerWonTheGame();
    }

    public void moveEnemies(){
        theModel.moveEnemiesCloser();
        notifyListeners();
    }

    public void display(Graphics g){
        theView.displayBoard(g, theModel.getBricks());
    }
    /**
     * Checks whether the players next y position is the floor
     * @param nextPlayerPositionY the players next Y position
     * @return true or false
     */
    public boolean collidesWith(int nextPlayerPositionX, int nextPlayerPositionY, int playerWidth){
	return willHitFloor(nextPlayerPositionY) || willCollide(nextPlayerPositionX,nextPlayerPositionY, playerWidth);
    }
    /**
     * Checks whether the players next y position is the floor
     * @param nextPlayerPositionY the players next Y position
     * @return true or false
     */
    private boolean willHitFloor(int nextPlayerPositionY) {
	if (nextPlayerPositionY >= theModel.getHeightOffset()){
	    currentIntersectedObstacleType = ObstacleTypes.SQUARE;
	    return true;
	}
	return false;
    }
    /**
     * Checks whether the players next X and y positions is colliding with an obstacle
     * @param nextPlayerPositionX Next player position in X-axis
     * @param nextPlayerPositionY Next player position in Y-axis
     * @return if player collides with obstacle
     */
    private boolean willCollide(int nextPlayerPositionX, int nextPlayerPositionY, int playerWidth){
	Iterable<Obstacle> brickEnemies = theModel.getBrickEnemies();
	int nextPlayerXWidth = nextPlayerPositionX + BRICK_SIZE;
	for (Obstacle obstacle : brickEnemies){
	    int nextObstacleXWidth = obstacle.getPositionX() + BRICK_SIZE;
	    if (!(nextPlayerXWidth < obstacle.getPositionX() && !(nextPlayerPositionX > nextObstacleXWidth))) { //Ignores obstacle that are behind or ahead of the nextX position {
		if (obstacle.intersect(nextPlayerPositionX, nextPlayerPositionY, playerWidth) ) {
		    currentIntersectedObstacleType = obstacle.getObstacleType();
		    return true;
		}
	    }
	}
	currentIntersectedObstacleType = null;
	return false;
    }

    public ObstacleTypes getIntersectedObstacle(){
	return currentIntersectedObstacleType;
    }
}
