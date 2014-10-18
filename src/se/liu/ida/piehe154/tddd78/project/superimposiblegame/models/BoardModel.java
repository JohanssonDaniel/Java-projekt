package se.liu.ida.piehe154.tddd78.project.superimposiblegame.models;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.OvalShape;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.SquareShape;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.TriangleShape;

import java.io.BufferedReader;
import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by pierre on 2014-04-16.
 * BoardModel contains all the obstacles in the game and the floor. It also includes that recieves the player position and checks whether an obstacle will hit or not
 */
public class BoardModel {

    private final int pixelWidth;
    private final int pixelHeight;
    private final AbstractList<Obstacle> bricks;
    private final ArrayList<Obstacle> brickEnemies;

    private final static int BRICK_SIZE = Obstacle.SIZE;
    private final static int PIXELS_PER_UPDATE = BRICK_SIZE / 10; // how many pixels the bricks move to the right every update
    private final static char SQUARE = 'x';
    private final static char OVAL = 'o';
    private final static char TRIANGLE = 't';

    private final int heightOffset;

    public BoardModel(int pixelWidth, int pixelHeight) {
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;

        bricks = new ArrayList<Obstacle>();
        brickEnemies = new ArrayList<Obstacle>();
        heightOffset = this.pixelHeight - BRICK_SIZE; // Moves the coords for the bricks one brick height up

    }


    public Iterable<Obstacle> getBricks() {
        return bricks;
    }

    /**
     * Hardcoded level (hence the "Magic Number" warning), every row is an obstacle that is added to the board
     */

    public void createEnemies(String mapName) {

        String thisLine;
        String mapUrl = "maps/" + mapName + ".txt";
        File inFile = new File(mapUrl);
        // open input stream test.txt for reading purpose.

        char brick;
        Integer pointX = 600;
        ArrayList<String> mapLines = new ArrayList<String>();

        try{
            BufferedReader br = new BufferedReader(new java.io.FileReader(inFile));

            while ((thisLine = br.readLine()) != null) {
                mapLines.add(thisLine);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        for (int n =0; n < mapLines.size(); n++) {
            thisLine = mapLines.get(n);
            for (int x = 0; x < thisLine.length(); x++) {
                brick = thisLine.charAt(x);
                if (brick == SQUARE) {
                    bricks.add(new Obstacle(pointX, heightOffset - (x+1)* BRICK_SIZE, new SquareShape())); //(x+1) To get the Obstacle above the floor
                } else if (brick == OVAL) {
                    bricks.add(new Obstacle(pointX, heightOffset - (x+1)* BRICK_SIZE, new OvalShape()));
                } else if (brick == TRIANGLE) {
                    bricks.add(new Obstacle(pointX, heightOffset - (x+1)* BRICK_SIZE, new TriangleShape()));
                }
            }
            pointX += BRICK_SIZE;
        }
    }

    /**
     * adds squareObstacles to the board which is later seen as floor by the player
     */
    public void createFloor(){
        for (int i = 0; i <= pixelWidth; i+= BRICK_SIZE){
            bricks.add(new Obstacle(i, heightOffset, new SquareShape()));
        }
    }

    /**
     * Separates the obstacles and the floor so that it is easier to handle collision
     */
    public void seperateEnemies() {
        for (Obstacle brick : bricks){
            if (brick.getPositionY() < heightOffset){
                brickEnemies.add(brick);
            }
        }
    }

    /**
     * Moves the obstacles on tick closer to the player each update, this gives the illusion that it is the player that moves
     */
    public void moveEnemiesCloser() {
        Iterator<Obstacle> brickIterator = brickEnemies.iterator();

        while (brickIterator.hasNext()) {
            Obstacle brick = brickIterator.next();
            int newPositionX = brick.getPositionX() - PIXELS_PER_UPDATE;
            if (newPositionX > -BRICK_SIZE) {
                brick.setPositionX(newPositionX);
            } else {
                brickIterator.remove();
                bricks.remove(brick);
            }
        }
    }

    /**
     * Checks whether the players next y position is the floor
     * @param nextPlayerPositionY the players next Y position
     * @return true or false
     */
    public boolean willHitFloor(int nextPlayerPositionY) {
        if (nextPlayerPositionY >= heightOffset){
            return true;
        }
        return false;
    }

    /**
     * Checks whether the players next X and y positions is colliding with an obstacle
     * @param nextPlayerX Next player position in X-axis
     * @param nextPlayerY Next player position in Y-axis
     * @return if player collides with obstacle
     */
    public boolean willCollide(int nextPlayerX, int nextPlayerY, int playerWidth, int playerHeight){
	int nextPlayerXWidth = nextPlayerX + BRICK_SIZE;
        for (Obstacle obstacle : brickEnemies){
            int nextObstacleXWidth = obstacle.getPositionX() + BRICK_SIZE;
            if (!(nextPlayerXWidth < obstacle.getPositionX() && !(nextPlayerX > nextObstacleXWidth))) { //Ignores obstacle that are behind or ahead of the nextX position
                //if (obstacle.intersects(nextPlayerX, nextPlayerY)) {
                if (obstacle.intersect(nextPlayerX, nextPlayerY, playerWidth, playerHeight) )
                    return true;
            }
        }
        return false;
    }
}
