package SuperImpossibleGame;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by DannePanne on 2014-03-31.
 */
public class Brick implements gameObject {

    private final static Dimension brickSize = new Dimension(30,30);
    private int positionX, positionY;

    public Brick(int x, int y) {
        positionX = x;
        positionY = y;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public static Dimension getBrickSize() {
        return brickSize;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(positionX,positionY, brickSize.width, brickSize.height);
    }
}
