package SuperImpossibleGame;

import java.awt.*;

/**
 * Created by DannePanne on 2014-04-06.
 */
public class Brick implements gameObject{
    private static final Dimension brickDimension = new Dimension(30,30);

    private int positionX, positionY;

    public static Dimension getBrickDimension() {
        return brickDimension;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Brick(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public boolean collideSide(Brick brick){ //Based on the intersect from the Rectangel class

        int brickWidth = this.brickDimension.width;
        int brickHeight = this.brickDimension.height;
        int playerWidth = brick.brickDimension.width;
        int playerHeight = brick.brickDimension.height;

        if (playerWidth <= 0 || playerHeight <= 0 || brickWidth <= 0 || brickHeight <= 0) {
            return false;
        }
        int brickPositionX = this.positionX;
        int brickPositionY = this.positionY;
        int playerPositionX = brick.getPositionX();
        int playerPositionY = brick.getPositionY();

        playerWidth += playerPositionX;
        playerHeight = playerPositionY - playerHeight;
        brickWidth += brickPositionX;
        brickHeight += brickPositionY;
        if ((playerHeight == brickPositionY) && (playerWidth >= brickPositionX  )){
            return true;
        }
    //    return (!(playerHeight >= brickPositionY) && (playerWidth > brickPositionX)); //Checks that the player is not above the brick and that the width is inside the brick
        return false;
    }

    public boolean collideTop(Brick brick){

        int brickWidth = brickDimension.width;
        int brickHeight = brickDimension.height;
        int playerWidth = brick.brickDimension.width;
        int playerHeight = brick.brickDimension.height;

        if (playerWidth <= 0 || playerHeight <= 0 || brickWidth <= 0 || brickHeight <= 0) {
            return false;
        }
        int brickPositionX = positionX;
        int brickPositionY = positionY;
        int playerPositionX = brick.getPositionX();
        int playerPositionY = brick.getPositionY();

        playerWidth += playerPositionX;
        playerHeight -= playerPositionY;
        brickWidth += brickPositionX;
        brickHeight += brickPositionY;
        if ((playerHeight >= brickPositionY &&
                ((playerWidth >= brickPositionX) ||
                        (playerWidth >= brickWidth)))){
            System.out.println("2");
            return true;
        } //Checks that the player is above the brick and that the width is inside the brick
        return false;
}

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(positionX,positionY,brickDimension.width,brickDimension.height);
    }
}
