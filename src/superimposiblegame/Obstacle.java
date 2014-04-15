package superimposiblegame;

import java.awt.*;

/**
 * Created by pierre on 4/8/14.
 */
public abstract class Obstacle implements GameObstacle {
    private final Dimension rectangle;
    private int positionX, positionY;
    public static final int SIZE = 30;

    protected Obstacle(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.rectangle = new Dimension(SIZE,SIZE);
    }

    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }

    public Dimension getSize() { return rectangle; }

    public void setPositionX(int newPosition) {
        positionX = newPosition;
    }

    public void setPositionY(int newPosition) {
        positionY = newPosition;
    }



}
