package superimposiblegame;

import java.awt.*;

/**
 * Created by Daniel Johansson on 2014-04-06.
 * the player can safely land on it but will crash if it hits its side
 * Since it extends Obsacle it implements the intersects and draw function
 */
public class SquareObstacle implements GameObstacle {

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
    public boolean intersects(int playerPositionX, int playerPositionY, int objectX, int objectY, int objectWidth, int objectHeight) {
        int brickWidth = objectWidth;
        int brickHeight = objectHeight;
        int playerWidth = brickWidth;
        int playerHeight = brickHeight;
        if (playerWidth <= 0 || playerHeight <= 0 || brickWidth <= 0 || brickHeight <= 0) {
            return false;
        }
        int brickPositionX = objectX;
        int brickPositionY = objectY;
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
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.black);
        g.fillRect(x,y, width, height);
    }
}
