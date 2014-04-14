package SuperImpossibleGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Board {
    private final int PIXEL_WIDTH;
    private final int PIXEL_HEIGHT;
    private final ArrayList<Brick> brickArrayList;
    private final ArrayList<Brick> brickEnemies;

    private final int BRICK_SIZE = Brick.SIZE;
    private final int SPEED = 3;

    private final int heightOffset;

    public Board(int pixelWidth, int pixelHeight) {
        this.PIXEL_WIDTH = pixelWidth;
        this.PIXEL_HEIGHT = pixelHeight;
        brickArrayList = new ArrayList<Brick>();
        brickEnemies = new ArrayList<Brick>();
        heightOffset = PIXEL_HEIGHT - BRICK_SIZE; // Moves the coords for the bricks one brick height up

        brickArrayList.add(new Brick(500, heightOffset - BRICK_SIZE));
        brickArrayList.add(new Brick(700, heightOffset - BRICK_SIZE));
        brickArrayList.add(new Brick(730, heightOffset - BRICK_SIZE));
        brickArrayList.add(new Brick(800, heightOffset - BRICK_SIZE));
        brickArrayList.add(new Brick(830, heightOffset - BRICK_SIZE));
        brickArrayList.add(new Brick(860, heightOffset - BRICK_SIZE));
        brickArrayList.add(new Brick(890, heightOffset - 2*BRICK_SIZE));
        brickArrayList.add(new Brick(920, heightOffset - 2*BRICK_SIZE));
        brickArrayList.add(new Brick(950, heightOffset - 2*BRICK_SIZE));

        brickArrayList.add(new Brick(1010, heightOffset - 3*BRICK_SIZE));
        //brickArrayList.add(new Brick(1040, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new Brick(1080, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new Brick(1150, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new Brick(1220, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new Brick(1290, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new Brick(1360, heightOffset - 3*BRICK_SIZE));

        brickArrayList.add(new Brick(1430, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new Brick(1500, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new Brick(1570, heightOffset - 3*BRICK_SIZE));

        brickArrayList.add(new Brick(1640, heightOffset - 4*BRICK_SIZE));
        brickArrayList.add(new Brick(1710, heightOffset - 4*BRICK_SIZE));
        brickArrayList.add(new Brick(1780, heightOffset - 4*BRICK_SIZE));

        brickArrayList.add(new Brick(1840, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new Brick(1900, heightOffset - 2*BRICK_SIZE));
        brickArrayList.add(new Brick(1960, heightOffset - BRICK_SIZE));


        brickArrayList.add(new Brick(2000, heightOffset - BRICK_SIZE));
        brickArrayList.add(new Brick(2030, heightOffset - 2*BRICK_SIZE));
        brickArrayList.add(new Brick(2060, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new Brick(2090, heightOffset - 4*BRICK_SIZE));
        brickArrayList.add(new Brick(2120, heightOffset - 5*BRICK_SIZE));
        brickArrayList.add(new Brick(2150, heightOffset - 6*BRICK_SIZE));
        brickArrayList.add(new Brick(2180, heightOffset - 7*BRICK_SIZE));
        brickArrayList.add(new Brick(2210, heightOffset - 8*BRICK_SIZE));
        brickArrayList.add(new Brick(2240, heightOffset - 9*BRICK_SIZE));

        brickArrayList.add(new Brick(2300, heightOffset - 6*BRICK_SIZE));
        brickArrayList.add(new Brick(2360, heightOffset - 3*BRICK_SIZE));

        brickArrayList.add(new Brick(2400, heightOffset - BRICK_SIZE));


        createFloor();
        seperateEnemies();
    }

    private void createFloor(){
        for (int i = 0; i <= PIXEL_WIDTH; i+= BRICK_SIZE){
            brickArrayList.add(new Brick(i, heightOffset));
        }
    }

    private void seperateEnemies() {
        for (Brick brick : brickArrayList){
            if (brick.getPositionY() < heightOffset){
                brickEnemies.add(brick);
            }
        }
    }

    public void moveEnemiesCloser() {
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
        if (nextPlayerPositionY >= heightOffset){
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

    public void displayBoard(Graphics g) {
        for(Brick b : brickArrayList) {
            b.draw(g);
        }
    }
}
