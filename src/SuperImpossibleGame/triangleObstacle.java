package SuperImpossibleGame;

import java.awt.*;

/**
 * Created by Daniel Johansson on 2014-04-14.
 */
public class triangleObstacle extends RectangleObject {

    private int[] polygonX;
    private int[] polygonY;
    private int n;

    public triangleObstacle(int positionX, int positionY) {
        super(positionX, positionY);

        polygonX = new int[3];
        polygonY = new int[3];
        n = 3;
    }

    private void createTriangle() {
        polygonX[0] = getPositionX();                       polygonY[0] = getPositionY() + getSize().height;
        polygonX[1] = getPositionX() + getSize().width /2;  polygonY[1] = getPositionY();
        polygonX[2] = getPositionX() + getSize().width;     polygonY[2] = getPositionY() + getSize().height;
    }
    public boolean intersects(Brick brick) {
        int triangleWidth = getSize().width;
        int triangleHeight = getSize().height;
        int playerWidth = brick.getSize().width;
        int playerHeight = brick.getSize().height;
        if (playerWidth <= 0 || playerHeight <= 0 || triangleWidth <= 0 || triangleHeight <= 0) {
            return false;
        }
        int trianglePositionX = getPositionX();
        int trianglePositionY = getPositionY();
        int playerPositionX = brick.getPositionX();
        int playerPositionY = brick.getPositionY();
        playerWidth += playerPositionX;
        playerHeight += playerPositionY;
        triangleWidth += trianglePositionX;
        if (((playerWidth >= trianglePositionX && !(playerPositionX > triangleWidth)) && (playerHeight >= trianglePositionY))){
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
        g.fillPolygon(polygonX, polygonY, 3);
    }
}
