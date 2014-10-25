package se.liu.ida.piehe154.tddd78.project.superimposiblegame.models;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by pierre on 2014-04-16.
 * BoardModel contains all the obstacles in the game and the floor. It also includes that recieves the player position and checks whether an obstacle will hit or not
 */
public class BoardModel {
    private final AbstractList<Obstacle> bricks;
    private final ArrayList<Obstacle> brickEnemies;

    private final static int BRICK_SIZE = Obstacle.SIZE;
    private final static int PIXELS_PER_UPDATE = BRICK_SIZE / 5; // how many pixels the bricks move to the right every update
    private final int pixelHeight;

    public boolean player_won_the_game = false;

    private final int heightOffset;

    public BoardModel(int pixelHeight) {
        this.pixelHeight = pixelHeight;

        bricks = new ArrayList<Obstacle>();
        brickEnemies = new ArrayList<Obstacle>();
        heightOffset = this.pixelHeight - BRICK_SIZE; // Moves the coords for the bricks one brick height up

    }

    public void addObstacle(Obstacle obstacle){
	this.bricks.add(obstacle);
    }


    public Iterable<Obstacle> getBricks() {
        return bricks;
    }

    /**
     * Separates the obstacles and the floor so that it is easier to handle collision
     */
    public void separateEnemies() {
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
        if (!brickEnemies.isEmpty()) {
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
        } else {
            player_won_the_game = true;
        }
    }

    public ArrayList<Obstacle> getBrickEnemies() {
	return brickEnemies;
    }

    public int getHeightOffset() {
	return heightOffset;
    }

}
