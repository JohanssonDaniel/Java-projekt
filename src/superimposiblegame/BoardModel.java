package superimposiblegame;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by pierre on 2014-04-16.
 */
public class BoardModel {

    private final int PIXEL_WIDTH;
    private final int PIXEL_HEIGHT;
    private final ArrayList<Obstacle> brickArrayList;
    private final ArrayList<Obstacle> brickEnemies;

    private final int BRICK_SIZE = Obstacle.SIZE;
    private final int SPEED = 3;

    private final int heightOffset;

    public BoardModel(int pixelWidth, int pixelHeight) {
        this.PIXEL_WIDTH = pixelWidth;
        this.PIXEL_HEIGHT = pixelHeight;

        brickArrayList = new ArrayList<Obstacle>();
        brickEnemies = new ArrayList<Obstacle>();
        heightOffset = PIXEL_HEIGHT - BRICK_SIZE; // Moves the coords for the bricks one brick height up
    }

    public ArrayList<Obstacle> getBrickArrayList() {
        return brickArrayList;
    }

    public void createEnemies() {
        brickArrayList.add(new OvalObstacle(400, heightOffset - BRICK_SIZE));
        brickArrayList.add(new TriangleObstacle(500, heightOffset - BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(700, heightOffset - BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(730, heightOffset - BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(800, heightOffset - BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(830, heightOffset - BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(860, heightOffset - BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(890, heightOffset - 2*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(920, heightOffset - 2*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(950, heightOffset - 2*BRICK_SIZE));

        brickArrayList.add(new SquareObstacle(1010, heightOffset - 3*BRICK_SIZE));
        //brickArrayList.add(new Brick(1040, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(1080, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(1150, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(1220, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(1290, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(1360, heightOffset - 3*BRICK_SIZE));

        brickArrayList.add(new SquareObstacle(1430, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(1500, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(1570, heightOffset - 3*BRICK_SIZE));

        brickArrayList.add(new SquareObstacle(1640, heightOffset - 4*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(1710, heightOffset - 4*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(1780, heightOffset - 4*BRICK_SIZE));

        brickArrayList.add(new SquareObstacle(1840, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(1900, heightOffset - 2*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(1960, heightOffset - BRICK_SIZE));


        brickArrayList.add(new SquareObstacle(2000, heightOffset - BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(2030, heightOffset - 2*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(2060, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(2090, heightOffset - 4*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(2120, heightOffset - 5*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(2150, heightOffset - 6*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(2180, heightOffset - 7*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(2210, heightOffset - 8*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(2240, heightOffset - 9*BRICK_SIZE));

        brickArrayList.add(new SquareObstacle(2300, heightOffset - 6*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(2360, heightOffset - 3*BRICK_SIZE));

        brickArrayList.add(new SquareObstacle(2400, heightOffset - BRICK_SIZE));
    }

    public void createFloor(){
        for (int i = 0; i <= PIXEL_WIDTH; i+= BRICK_SIZE){
            brickArrayList.add(new SquareObstacle(i, heightOffset));
        }
    }

    public void seperateEnemies() {
        for (Obstacle brick : brickArrayList){
            if (brick.getPositionY() < heightOffset){
                brickEnemies.add(brick);
            }
        }
    }

    public void moveEnemiesCloser() {
        Iterator<Obstacle> brickIterator = brickEnemies.iterator();

        while (brickIterator.hasNext()) {
            Obstacle brick = brickIterator.next();
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
        for (Obstacle brick : brickArrayList) {
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
        for (Obstacle obstacle : brickEnemies){
            if (obstacle.intersects(nextPlayerX, nextPlayerY)){
                return true;
            }
        }
        return false;
    }
}
