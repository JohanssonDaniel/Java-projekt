package SuperImpossibleGame;

import java.awt.*;

/**
 * Created by pierre on 4/8/14.
 */
public abstract class RectangleObject implements gameObject{
    private Dimension rectangle;
    private int positionX, positionY;
    public static int SIZE = 30;

    public RectangleObject(int positionX, int positionY) {
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
