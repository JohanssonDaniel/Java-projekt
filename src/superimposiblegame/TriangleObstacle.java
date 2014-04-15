package superimposiblegame;

import java.awt.*;

/**
 * Created by Daniel Johansson on 2014-04-14.
 */
public class TriangleObstacle extends Obstacle {

    private int[] polygonX;
    private int[] polygonY;
    private int n;

    public TriangleObstacle(int positionX, int positionY) {
        super(positionX, positionY);

        polygonX = new int[3];
        polygonY = new int[3];

        n = polygonX.length;
    }

    private void createTriangle() {
        polygonX[0] = getPositionX();                       polygonY[0] = getPositionY() + getSize().height;
        polygonX[1] = getPositionX() + getSize().width /2;  polygonY[1] = getPositionY();
        polygonX[2] = getPositionX() + getSize().width;     polygonY[2] = getPositionY() + getSize().height;
    }

    @Override
    public boolean intersects(int playerPositionX, int playerPositionY) {
        int triangleWidth = getSize().width;
        int triangleHeight = getSize().height;
        int playerWidth = getSize().width;
        int playerHeight = getSize().height;
        if (playerWidth <= 0 || playerHeight <= 0 || triangleWidth <= 0 || triangleHeight <= 0) {
            return false;
        }
        int trianglePositionX = getPositionX();
        int trianglePositionY = getPositionY();
        //int playerPositionX = brick.getPositionX();
        //int playerPositionY = brick.getPositionY();
        playerWidth += playerPositionX;
        playerHeight += playerPositionY;
        triangleWidth += trianglePositionX;
        triangleHeight += trianglePositionY;
        if (((playerWidth >= trianglePositionX && !(playerPositionX > triangleWidth)) && (playerHeight >= triangleHeight))){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void draw(Graphics g) {
        createTriangle();
        g.setColor(Color.black);
        g.fillPolygon(polygonX, polygonY, n);
    }
}
