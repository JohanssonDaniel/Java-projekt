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
     * @param playerPositionX
     * @param playerPositionY
     * @return
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
        int ovalPositionX = objectX;
        int obalPositionY = objectY;
        //int playerPositionX = brick.getPositionX();
        //int playerPositionY = brick.getPositionY();
        playerWidth += playerPositionX;
        playerHeight += playerPositionY;
        ovalWidth += ovalPositionX;
        ovalHeight += obalPositionY;
        if ((playerWidth >= ovalPositionX && !(playerPositionX > ovalWidth)) &&
	    (playerHeight > obalPositionY && !(playerPositionY > ovalHeight))){
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