package se.liu.ida.piehe154.tddd78.project.superimposiblegame.models;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.ObstacleShape;

import java.awt.*;

/**
 * Created by pierre on 4/8/14.
 * Contains the base information for each obstacle in the game, such as its size and X and Y position
 * the size is represented as a size even though the Obstacle models might be of a different,
 * but that just makes it easier to get their height and width when we want to draw them
 *
 */
public class Obstacle {
    private final Dimension size;
    private int positionX, positionY;
    private ObstacleShape obstacle;
    /**
      * Size of Obstacle
      */
    public static final int SIZE = 30;

    protected Obstacle(int positionX, int positionY, ObstacleShape obstacle) {
        this.obstacle = obstacle;
        this.positionX = positionX;
        this.positionY = positionY;
        this.size = new Dimension(SIZE,SIZE);
    }

    /**
     * brickWidth is the righ side of the brick
     * brickHeight is the bottom of the brick
     *
     * the same goes for playerHeight and playerWidth )
     * @param playerPositionX Position of player in X-axis
     * @param playerPositionY Position of player in Y-axis
     * @return if player intersects with osbtacle
     */

    public boolean intersect(int playerPositionX, int playerPositionY, int playerWidth, int playerHeight) {
        int brickWidth = this.size.width;
        int brickHeight = this.size.height;

	if (brickWidth <= 0 || brickHeight <= 0) {
          return false;
     	}

	int playerRightSide = playerWidth + playerPositionX;
	int playerBottomSide = playerHeight + playerPositionY;
        brickWidth += this.positionX;
        brickHeight += this.positionY;

        if ((playerRightSide >= this.positionX && !(playerPositionX > brickWidth)) && (playerBottomSide > this.positionY && !(playerPositionY > brickHeight))){
            return true;
        }
        return false;
    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        this.obstacle.draw(g, x, y, width, height);
    }

    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }

    public Dimension getSize() { return size; }

    public void setPositionX(int newPosition) {
        positionX = newPosition;
    }

}
