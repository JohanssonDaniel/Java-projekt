package superimposiblegame;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by pierre on 2014-04-16.
 * BoardModel contains all the obstacles in the game and the floor. It also includes that recieves the player position and checks whether an obstacle will hit or not
 */
public class BoardModel {

    private final int pixelWidth;
    private final int pixelHeight;
    private final ArrayList<Obstacle> brickArrayList;
    private final ArrayList<Obstacle> brickEnemies;

    private final static int  brickSize = Obstacle.SIZE;
    private final static int pixelsPerUpdate = brickSize / 10; // how many pixels the bricks move to the right every update

    private final int heightOffset;

    public BoardModel(int pixelWidth, int pixelHeight) {
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;

        brickArrayList = new ArrayList<Obstacle>();
        brickEnemies = new ArrayList<Obstacle>();
        heightOffset = this.pixelHeight - brickSize; // Moves the coords for the bricks one brick height up
    }

    public ArrayList<Obstacle> getBrickArrayList() {
        return brickArrayList;
    }

    /**
     * Hardcoded level, every row is an obstacle that is added to the board
     */
    public void createEnemies() {
        brickArrayList.add(new OvalObstacle(400, heightOffset - brickSize));
        brickArrayList.add(new TriangleObstacle(500, heightOffset - brickSize));
        brickArrayList.add(new SquareObstacle(700, heightOffset - brickSize));
        brickArrayList.add(new SquareObstacle(730, heightOffset - brickSize));
        brickArrayList.add(new SquareObstacle(800, heightOffset - brickSize));
        brickArrayList.add(new SquareObstacle(830, heightOffset - brickSize));
        brickArrayList.add(new SquareObstacle(860, heightOffset - brickSize));
        brickArrayList.add(new SquareObstacle(890, heightOffset - 2* brickSize));
        brickArrayList.add(new SquareObstacle(920, heightOffset - 2* brickSize));
        brickArrayList.add(new SquareObstacle(950, heightOffset - 2* brickSize));

        brickArrayList.add(new SquareObstacle(1010, heightOffset - 3* brickSize));
        //brickArrayList.add(new Brick(1040, heightOffset - 3*brickSize));
        brickArrayList.add(new SquareObstacle(1080, heightOffset - 3* brickSize));
        brickArrayList.add(new SquareObstacle(1150, heightOffset - 3* brickSize));
        brickArrayList.add(new SquareObstacle(1220, heightOffset - 3* brickSize));
        brickArrayList.add(new SquareObstacle(1290, heightOffset - 3* brickSize));
        brickArrayList.add(new SquareObstacle(1360, heightOffset - 3* brickSize));

        brickArrayList.add(new SquareObstacle(1430, heightOffset - 3* brickSize));
        brickArrayList.add(new SquareObstacle(1500, heightOffset - 3* brickSize));
        brickArrayList.add(new SquareObstacle(1570, heightOffset - 3* brickSize));

        brickArrayList.add(new SquareObstacle(1640, heightOffset - 4* brickSize));
        brickArrayList.add(new SquareObstacle(1710, heightOffset - 4* brickSize));
        brickArrayList.add(new SquareObstacle(1780, heightOffset - 4* brickSize));

        brickArrayList.add(new SquareObstacle(1840, heightOffset - 3* brickSize));
        brickArrayList.add(new SquareObstacle(1900, heightOffset - 2* brickSize));
        brickArrayList.add(new SquareObstacle(1960, heightOffset - brickSize));


        brickArrayList.add(new SquareObstacle(2000, heightOffset - brickSize));
        brickArrayList.add(new SquareObstacle(2030, heightOffset - 2* brickSize));
        brickArrayList.add(new SquareObstacle(2060, heightOffset - 3* brickSize));
        brickArrayList.add(new SquareObstacle(2090, heightOffset - 4* brickSize));
        brickArrayList.add(new SquareObstacle(2120, heightOffset - 5* brickSize));
        brickArrayList.add(new SquareObstacle(2150, heightOffset - 6* brickSize));
        brickArrayList.add(new SquareObstacle(2180, heightOffset - 7* brickSize));
        brickArrayList.add(new SquareObstacle(2210, heightOffset - 8* brickSize));
        brickArrayList.add(new SquareObstacle(2240, heightOffset - 9* brickSize));

        brickArrayList.add(new SquareObstacle(2300, heightOffset - 6* brickSize));
        brickArrayList.add(new SquareObstacle(2360, heightOffset - 3* brickSize));

        brickArrayList.add(new SquareObstacle(2400, heightOffset - brickSize));
    }

    /**
     * adds squareObstacles to the board which is later seen as floor by the player
     */
    public void createFloor(){
        for (int i = 0; i <= pixelWidth; i+= brickSize){
            brickArrayList.add(new SquareObstacle(i, heightOffset));
        }
    }

    /**
     * Separates the obstacles and the floor so that it is easier to handle collision
     */
    public void seperateEnemies() {
        for (Obstacle brick : brickArrayList){
            if (brick.getPositionY() < heightOffset){
                brickEnemies.add(brick);
            }
        }
    }

    /**
     * Moves the obstacles on tick closer to the player each update, this gives the illusion that it is the player that moves
     */
    public void moveEnemiesCloser() {
        Iterator<Obstacle> brickIterator = brickEnemies.iterator();

        while (brickIterator.hasNext()) {
            Obstacle brick = brickIterator.next();
            int newPositionX = brick.getPositionX() - pixelsPerUpdate;
            if (newPositionX > -brickSize) {
                brick.setPositionX(newPositionX);
            } else {
                brickIterator.remove();
                brickArrayList.remove(brick);
            }
        }
    }

    /**
     * Checks whether the players next y position is the floor
     * @param nextPlayerPositionY the players next Y position
     * @return true or false
     */
    public boolean willHitFloor(int nextPlayerPositionY) {
        if (nextPlayerPositionY >= heightOffset){
            return true;
        }
        return false;
    }

    /**
     * Checks whether the players next X and y positions is colliding with an obstacle
     * @param nextPlayerX
     * @param nextPlayerY
     * @return
     */
    public boolean collideWhileJumping(int nextPlayerX, int nextPlayerY){
	int nextPlayerXWidth = nextPlayerX + brickSize;
        for (Obstacle obstacle : brickEnemies){
	    int nextObstacleXWidth = obstacle.getPositionX() + brickSize;
	    if (!(nextPlayerXWidth < obstacle.getPositionX() && !(nextPlayerX > nextObstacleXWidth))) { //Ignores obstacle that are behind or ahead of the nextX position
		if (obstacle.intersects(nextPlayerX, nextPlayerY)) {
		    return true;
		}
	    }
        }
        return false;
    }
}
