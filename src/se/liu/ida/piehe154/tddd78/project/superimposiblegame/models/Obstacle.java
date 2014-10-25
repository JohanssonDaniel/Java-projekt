package se.liu.ida.piehe154.tddd78.project.superimposiblegame.models;

import java.awt.*;

/**
 * Created by pierre on 4/8/14.
 * Contains the base information for each Obstacle in the game, such as its size and X and Y position
 * the size is represented as a size even though the Obstacle models might be of a different,
 * but that just makes it easier to get their height and width when we want to draw them
 *
 */
public class Obstacle {
    private final Dimension size;
    private int positionX, positionY;
    private ObstacleTypes obstacleType;
    private ObstacleShape obsView;
    /**
      * Size of Obstacle
      */
    public static final int SIZE = 30;

    public Obstacle(int positionX, int positionY, ObstacleShape obsView, ObstacleTypes obstacleTypes) {
        this.obsView = obsView;
        this.positionX = positionX;
        this.positionY = positionY;
        this.size = new Dimension(SIZE,SIZE);
	this.obstacleType = obstacleTypes;
    }

    /**
     * brickWidth is the right side of the brick
     * brickHeight is the bottom of the brick
     *
     * the same goes for playerHeight and playerWidth )
     * @param playerPositionX Position of player in X-axis
     * @param playerPositionY Position of player in Y-axis
     * @return if player intersects with obstacle
     */
    public boolean intersect(int playerPositionX, int playerPositionY, int playerWidth) {
	int obsRightSide = SIZE + positionX;
	int obsBottomSide = SIZE + positionY;
	int playerRightSide = playerWidth + playerPositionX;

	if (obsRightSide <= 0 || obsBottomSide <= 0) {
	    return false;
	}

	else if ((playerRightSide >= positionX && playerPositionX < obsRightSide) &&
		 (playerPositionY >= positionY && playerPositionY < obsBottomSide)){
	    return true;
	}
	return false;
    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        this.obsView.draw(g, x, y, width, height);
    }

    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }

    public ObstacleTypes getObstacleType() {
	return obstacleType;
    }

    public Dimension getSize() { return size; }

    public void setPositionX(int newPosition) {
        positionX = newPosition;
    }

}
