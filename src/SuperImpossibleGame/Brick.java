package SuperImpossibleGame;

import java.awt.*;

/**
 * Created by DannePanne on 2014-04-06.
 */
public class Brick extends RectangleObject implements gameObject {

    public Brick(int positionX, int positionY) {
        super(positionX, positionY);
    }

    public boolean collideSide(Brick player){ //Based on the intersect from the Rectangel class

        int brickWidth = getSize().width;
        int brickHeight = getSize().height;
        int playerWidth = getSize().width;
        int playerHeight = getSize().height;

        if (playerWidth <= 0 || playerHeight <= 0 || brickWidth <= 0 || brickHeight <= 0) {
            return false;
        }
        int brickPositionX = getPositionX();
        int brickPositionY = getPositionY();
        int playerPositionX = player.getPositionX();
        int playerPositionY = player.getPositionY();

        playerWidth += playerPositionX;
        playerHeight = playerPositionY - playerHeight;
        brickWidth += brickPositionX;
        brickHeight += brickPositionY;
        if ((playerHeight >= brickPositionY) && (playerWidth >= brickPositionX  )){
            return true;
        }
    //    return (!(playerHeight >= brickPositionY) && (playerWidth > brickPositionX)); //Checks that the player is not above the brick and that the width is inside the brick
        return false;
    }

    /*public boolean collideTop(Brick brick){

        int brickWidth = getSize().width;
        int brickHeight = getSize().height;
        int playerWidth = brick.getSize().width;
        int playerHeight = brick.getSize().height;

        if (playerWidth <= 0 || playerHeight <= 0 || brickWidth <= 0 || brickHeight <= 0) {
            return false;
        }
        int brickPositionX = super.getPositionX();
        int brickPositionY = super.getPositionY();
        int playerPositionX = brick.getPositionX();
        int playerPositionY = brick.getPositionY();

        playerWidth += playerPositionX;
        playerHeight = playerPositionY + playerHeight;
        brickWidth += brickPositionX;
        brickHeight += brickPositionY;
        if ((playerHeight == brickPositionY &&
                ((playerWidth >= brickPositionX) ||
                        (playerWidth >= brickWidth)))){
            return true;
        } //Checks that the player is above the brick and that the width is inside the brick
        return false;
}*/
    public boolean intersects(Brick brick) {
        int brickWidth = getSize().width;
        int brickHeight = getSize().height;
        int playerWidth = brick.getSize().width;
        int playerHeight = brick.getSize().height;
        if (playerWidth <= 0 || playerHeight <= 0 || brickWidth <= 0 || brickHeight <= 0) {
            return false;
        }
        int brickPositionX = super.getPositionX();
        int brickPositionY = super.getPositionY();
        int playerPositionX = brick.getPositionX();
        int playerPositionY = brick.getPositionY();
        playerWidth += playerPositionX;
        playerHeight += playerPositionY;
        brickWidth += brickPositionX;
        brickHeight += brickPositionY;
        //      overflow || intersect
        return ((playerWidth < playerPositionX || playerWidth > brickPositionX) &&
                (playerHeight < playerPositionY || playerHeight > brickPositionY) &&
                (brickWidth < brickPositionX || brickWidth > playerPositionX) &&
                (brickHeight < brickPositionY || brickHeight > playerPositionY));
    }

    @Override
    public void draw(Graphics g, int PIXEL_HEIGHT, int pixelX, int numRows) {
        int pixelY = PIXEL_HEIGHT - ((numRows-getPositionY())); //Räknar ut vart y ska vara baserat på dess kolumn
        g.setColor(Color.black);
        g.fillRect(pixelX, pixelY, getSize().width, getSize().height);
    }
}
