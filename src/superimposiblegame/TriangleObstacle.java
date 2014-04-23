package superimposiblegame;

import java.awt.*;

/**
 * Created by Daniel Johansson on 2014-04-14.
 */
public class TriangleObstacle implements GameObstacle {

    /**
     * triangleWidth is the righ side of the triangle
     * trinagleHeight is the bottom of the triangle
     *
     * the same goes for playerHeight and playerWidth )
     * @param playerPositionX
     * @param playerPositionY
     * @return
     */

    @Override
    public boolean intersects(int playerPositionX, int playerPositionY, int objectX, int objectY, int objectWidth, int objectHeight) {
        int triangleWidth = objectWidth;
        int triangleHeight = objectHeight;
        int playerWidth = objectWidth;
        int playerHeight = objectHeight;
        if (playerWidth <= 0 || playerHeight <= 0 || triangleWidth <= 0 || triangleHeight <= 0) {
            return false;
        }
        int trianglePositionX = objectX;
        playerWidth += playerPositionX;
        playerHeight += playerPositionY;
        triangleWidth += trianglePositionX;
        if (trianglePositionX <= playerPositionX && playerPositionX <= triangleWidth || trianglePositionX <= playerWidth && playerWidth <= triangleWidth){
            if (playerHeight > objectY) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {

        int[] polygonX;
        int[] polygonY;
        int n;

        polygonX = new int[3];
        polygonY = new int[3];

        n = polygonX.length;

        polygonX[0] = x;
        polygonY[0] = y + height;
        polygonX[1] = x + width /2;  polygonY[1] = y;
        polygonX[2] = x + width;
        polygonY[2] = y + height;


        g.setColor(Color.black);
        g.fillPolygon(polygonX, polygonY, n);
    }
}
