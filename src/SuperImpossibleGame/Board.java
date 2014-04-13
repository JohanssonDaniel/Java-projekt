package SuperImpossibleGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Board {
    private final int PIXEL_WIDTH;
    private final int PIXEL_HEIGHT;
    private ArrayList<Brick> brickArrayList;
    private ArrayList<Brick> brickEnemies;
    private final int BRICK_SIZE = Brick.SIZE;
    private final int SPEED = 3;

    private final static double MOVE_FACTOR = 0.25;
    private int moveSize;

    private int heightOffset;

    public Board(int pixelWidth, int pixelHeight) {
        this.PIXEL_WIDTH = pixelWidth;
        this.PIXEL_HEIGHT = pixelHeight;
        brickArrayList = new ArrayList<Brick>();
        brickEnemies = new ArrayList<Brick>();

        heightOffset = PIXEL_HEIGHT - BRICK_SIZE; // Moves the coords for the bricks one brick height up

        moveSize = (int)(pixelWidth * MOVE_FACTOR); //Decides how many pixels the image moves to the right each update
        if (moveSize == 0){
            moveSize = 1;
        }
        brickArrayList.add(new Brick(500, heightOffset - BRICK_SIZE));
        brickArrayList.add(new Brick(700, heightOffset - BRICK_SIZE));
        brickArrayList.add(new Brick(730, heightOffset - BRICK_SIZE));
        brickArrayList.add(new Brick(800, heightOffset - BRICK_SIZE));
        brickArrayList.add(new Brick(2000, heightOffset - BRICK_SIZE));


        createFloor();
        seperateEnemies();
    }

    public void createFloor(){
        for (int i = 0; i <= PIXEL_WIDTH; i+= BRICK_SIZE){
            brickArrayList.add(new Brick(i, heightOffset));
        }
    }

    public void seperateEnemies() {
        for (Brick brick : brickArrayList){
            if (brick.getPositionY() < heightOffset){
                brickEnemies.add(brick);
            }
        }
    }

    public void updateEnemies() {
        Iterator<Brick> brickIterator = brickEnemies.iterator();

        while (brickIterator.hasNext()) {
            Brick brick = brickIterator.next();
            int newPosition_X = brick.getPositionX() - SPEED;
            if (newPosition_X > -BRICK_SIZE) {
                brick.setPositionX(newPosition_X);
            } else {
                brickIterator.remove();
                brickArrayList.remove(brick);
            }
        }
        /*
        for (Brick brick : brickEnemies) {
            int newPosition_X = brick.getPositionX();
            brick.setPositionX(newPosition_X);
        }
        /*
        int lengthofBrickEnemies = brickEnemies.size();
        for (int i = 0; i < lengthofBrickEnemies; i++) {
            Brick brick = brickEnemies.get(i);
            int newPosition_X = brick.getPositionX() - SPEED;
            if (newPosition_X > 0) {
                brick.setPositionX(newPosition_X);
            } else {
                brickEnemies.remove(i);
            }

        }*/
    }

    public int findFloor(){
        int locationY = PIXEL_HEIGHT;
        for (Brick brick : brickArrayList) {
            if (brick.getPositionY() < locationY && brick.getPositionX() == 0)
                locationY = brick.getPositionY();   // reduces locationY and sends that y value to the player
        }
        return locationY - BRICK_SIZE; //Puts the player on brick above the floors y value
    }

    public boolean willHitFloor(int nextPlayerPositionY) {
        if (nextPlayerPositionY >=  heightOffset){
            return true;
        }
        return false;
    }

    public boolean collideWhileJumping(int nextPlayerX, int nextPlayerY){
        Brick playerBrick = new Brick(nextPlayerX, nextPlayerY);
        for (Brick bricks : brickEnemies){
            if (bricks.intersects(playerBrick)){
                return true;
            }

        }
        return false;
    }

    public boolean willFallOfBrick(int nextPlayerPositionLeft, int nextPlayerPositionRight) {
        for (Brick bricks : brickEnemies){
            int brickLeftSide = bricks.getPositionX();
            int brickRightSide = brickLeftSide + BRICK_SIZE;
            if ((brickLeftSide < nextPlayerPositionLeft && nextPlayerPositionLeft < brickRightSide )  ||
                  ((brickLeftSide < nextPlayerPositionRight) && (nextPlayerPositionRight < brickRightSide))){
                return true;

            }
        }
        return false;
    }

    public void displayBoard(Graphics g) {
        for(Brick b : brickArrayList) {
            b.draw(g);
        }
    }
}
