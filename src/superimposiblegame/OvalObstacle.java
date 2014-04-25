package superimposiblegame;

import java.awt.*;

/**
 * Created by pierre on 2014-04-18.
 * This obstacle is ment to make the player bounce if it lands on it
 * Since it extends Obsacle it implements the intersects and draw function
 */

public class OvalObstacle implements GameObstacle {

    /**
     * ovalWidth is the righ side of the oval
     * ovalHeight is the bottom of the oval
     *
     * the same goes for playerHeight and playerWidth )
     * @param playerPositionX Position of player in X-axis
     * @param playerPositionY Position of player in Y-axis
     * @return if player intersects with osbtacle
     */
    @Override
    public boolean intersects(int playerPositionX, int playerPositionY, int objectX, int objectY, int objectWidth, int objectHeight) {
        int ovalWidth = objectWidth;
        int ovalHeight = objectHeight;
        int playerWidth = ovalWidth;
        int playerHeight = ovalHeight;
        if (playerWidth <= 0 || playerHeight <= 0 || ovalWidth <= 0 || ovalHeight <= 0) {
            return false;
        }

        playerWidth += playerPositionX;
        playerHeight += playerPositionY;
        ovalWidth += objectX;
        ovalHeight += objectY;
        if ((playerWidth >= objectX && !(playerPositionX > ovalWidth)) &&
	    (playerHeight > objectY && !(playerPositionY > ovalHeight))){
            return true;
        }
        return false;
    }
    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.black);
        g.fillOval(x,y, width, height);
    }
}