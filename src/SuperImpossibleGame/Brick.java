package SuperImpossibleGame;

import java.awt.*;

/**
 * Created by DannePanne on 2014-04-06.
 */
public class Brick extends RectangleObject implements gameObject {

    public Brick(int positionX, int positionY) {
        super(positionX, positionY);
    }

    public boolean intersects(Brick brick) {
        int brickWidth = getSize().width;
        int brickHeight = getSize().height;
        int playerWidth = brick.getSize().width;
        int playerHeight = brick.getSize().height;
        if (playerWidth <= 0 || playerHeight <= 0 || brickWidth <= 0 || brickHeight <= 0) {
            return false;
        }
        int brickPositionX = getPositionX();
        int brickPositionY = getPositionY();
        int playerPositionX = brick.getPositionX();
        int playerPositionY = brick.getPositionY();
        playerWidth += playerPositionX;
        playerHeight += playerPositionY;
        brickWidth += brickPositionX;
        brickHeight += brickPositionY;

        return ((playerWidth >= brickPositionX && !(playerPositionX > brickWidth)) && (playerHeight > brickPositionY));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(getPositionX(),getPositionY(), getSize().width, getSize().height);
    }
}
