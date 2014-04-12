package SuperImpossibleGame;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private final int PIXEL_WIDTH;
    private final int PIXEL_HEIGHT;
    private ArrayList<Brick> brickArrayList;
    private final int BRICK_SIZE = Brick.SIZE;
    //TOG BORT fältet private Brick brick, brick i denna klass användes endast för att hämta strleken på en rektangel så vi behöver bara hämta konstanten.

    private final static double MOVE_FACTOR = 0.25;
    private int moveSize;

    private int heightOffset;

    public Board(int pixelWidth, int pixelHeight) {
        this.PIXEL_WIDTH = pixelWidth;
        this.PIXEL_HEIGHT = pixelHeight;
        brickArrayList = new ArrayList<Brick>();

        heightOffset = PIXEL_HEIGHT - BRICK_SIZE; // Moves the coords for the bricks one brick height up

        moveSize = (int)(pixelWidth * MOVE_FACTOR); //Decides how many pixels the image moves to the right each update
        if (moveSize == 0){
            moveSize = 1;
        }
        brickArrayList.add(new Brick(500, PIXEL_HEIGHT-60));
        createFloor();
    }

    public void createFloor(){
        for (int i = 0; i <= PIXEL_WIDTH; i+= BRICK_SIZE){
            brickArrayList.add(new Brick(i, heightOffset));
        }
    }

    public int findFloor(){
        int locationY = PIXEL_HEIGHT;
        for (Brick brick : brickArrayList) {
            if (brick.getPositionY() < locationY && brick.getPositionX() == 0)
                locationY = brick.getPositionY();   // reduces locationY and sends that y value to the player
        }
        return locationY - BRICK_SIZE; //Puts the player on brick above the floors y value
    }

    private Brick biggestY(int positionX){//Picks out the biggest positionY aka, the brick that is highest
        Brick highestBrick = null;
        for (Brick brick : brickArrayList){
            if (brick.getPositionX() == positionX){
                if (highestBrick == null){
                    highestBrick = brick;
                }
                else if (brick.getPositionY() <= highestBrick.getPositionY() ){
                    highestBrick = brick;
                }
            }
        }
        return highestBrick;
    }

    public boolean willHitFloor(int nextPlayerPositionY) {
        if (nextPlayerPositionY >=  heightOffset){
            return true;
        }
        else {
            return false;
        }
    }


    public boolean collideWithSideOfBrick(int nextPlayerX) {
        for (Brick bricks : brickArrayList){
            if (bricks.getPositionY() < heightOffset){
                if (nextPlayerX >= bricks.getPositionX()){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean collideWhileJumping(int nextPlayerX, int nextPlayerY){
        Brick playerBrick = new Brick(nextPlayerX, nextPlayerY);
        for (Brick bricks : brickArrayList){
            if (bricks.getPositionY() < heightOffset){
                if (bricks.intersects(playerBrick)){
                    return true;
                }
            }
        }
        return false;
    }

    public void displayBoard(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for(Brick b : brickArrayList) {
            b.draw(g);
        }
    }

    public boolean willFallOfBrick(int nextPlayerPositionLeft, int nextPlayerPositionRight) {
        for (Brick bricks : brickArrayList){
            if (bricks.getPositionY() < heightOffset){
                int brickLeftSide = bricks.getPositionX();
                int brickRightSide = brickLeftSide + BRICK_SIZE;
                if ((brickLeftSide <= nextPlayerPositionLeft && nextPlayerPositionLeft <= brickRightSide )  ||
                        ((brickLeftSide >= nextPlayerPositionRight) && (nextPlayerPositionRight >= brickRightSide))){
                    return true;
                }
            }
        }
        return false;
    }
}
