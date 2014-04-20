package superimposiblegame;

import java.awt.*;

/**
 * Created by Daniel Johansson on 2014-04-06.
 * the player can safely land on it but will crash if it hits its side
 * Since it extends Obsacle it implements the intersects and draw function
 */
public class SquareObstacle extends Obstacle {

    public SquareObstacle(int positionX, int positionY) {
        super(positionX, positionY);
    }



    /**
     * brickWidth is the righ side of the brick
     * brickHeight is the bottom of the brick
     *
     * the same goes for playerHeight and playerWidth )
     * @param playerPositionX
     * @param playerPositionY
     * @return
     */

    @Override
    public boolean intersects(int playerPositionX, int playerPositionY) {
        int brickWidth = getSize().width;
        int brickHeight = getSize().height;
        int playerWidth = brickWidth;
        int playerHeight = brickHeight;
        if (playerWidth <= 0 || playerHeight <= 0 || brickWidth <= 0 || brickHeight <= 0) {
            return false;
        }
        int brickPositionX = getPositionX();
        int brickPositionY = getPositionY();
        //int playerPositionX = brick.getPositionX();
        //int playerPositionY = brick.getPositionY();
        playerWidth += playerPositionX;
        playerHeight += playerPositionY;
        brickWidth += brickPositionX;
        brickHeight += brickPositionY;
        if ((playerWidth >= brickPositionX && !(playerPositionX > brickWidth)) && (playerHeight > brickPositionY && !(playerPositionY > brickHeight))){
            return true;
        }
        return false;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(getPositionX(),getPositionY(), getSize().width, getSize().height);
    }
}
