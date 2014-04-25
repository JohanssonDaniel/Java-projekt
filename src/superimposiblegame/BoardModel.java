package superimposiblegame;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by pierre on 2014-04-16.
 * BoardModel contains all the obstacles in the game and the floor. It also includes that recieves the player position and checks whether an obstacle will hit or not
 */
@SuppressWarnings("MagicNumber")
public class BoardModel {

    private final int pixelWidth;
    private final int pixelHeight;
    private final ArrayList<Obstacle> brickArrayList;
    private final ArrayList<Obstacle> brickEnemies;

    private final static int BRICK_SIZE = Obstacle.SIZE;
    private final static int PIXELS_PER_UPDATE = BRICK_SIZE / 10; // how many pixels the bricks move to the right every update

    private final int heightOffset;

    public BoardModel(int pixelWidth, int pixelHeight) {
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;

        brickArrayList = new ArrayList<Obstacle>();
        brickEnemies = new ArrayList<Obstacle>();
        heightOffset = this.pixelHeight - BRICK_SIZE; // Moves the coords for the bricks one brick height up
    }


    public Iterable<Obstacle> getBrickArrayList() {
        return brickArrayList;
    }

    /**
     * Hardcoded level, every row is an obstacle that is added to the board
     */
    public void createEnemies() {

        brickArrayList.add(new Obstacle(400, heightOffset - BRICK_SIZE, new OvalObstacle()));
        brickArrayList.add(new Obstacle(500, heightOffset - BRICK_SIZE, new TriangleObstacle()));
        brickArrayList.add(new Obstacle(700, heightOffset - BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(730, heightOffset - BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(800, heightOffset - BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(830, heightOffset - BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(860, heightOffset - BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(890, heightOffset - 2* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(920, heightOffset - 2* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(950, heightOffset - 2* BRICK_SIZE, new SquareObstacle()));

        brickArrayList.add(new Obstacle(1010, heightOffset - 3* BRICK_SIZE, new SquareObstacle()));
        //brickArrayList.add(new Brick(1040, heightOffset - 3*BRICK_SIZE));
        brickArrayList.add(new Obstacle(1080, heightOffset - 3* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(1150, heightOffset - 3* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(1220, heightOffset - 3* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(1290, heightOffset - 3* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(1360, heightOffset - 3* BRICK_SIZE, new SquareObstacle()));

        brickArrayList.add(new Obstacle(1430, heightOffset - 3* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(1500, heightOffset - 3* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(1570, heightOffset - 3* BRICK_SIZE, new SquareObstacle()));

        brickArrayList.add(new Obstacle(1640, heightOffset - 4* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(1710, heightOffset - 4* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(1780, heightOffset - 4* BRICK_SIZE, new SquareObstacle()));

        brickArrayList.add(new Obstacle(1840, heightOffset - 3* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(1900, heightOffset - 2* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(1960, heightOffset - BRICK_SIZE, new SquareObstacle()));


        brickArrayList.add(new Obstacle(2000, heightOffset - BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(2030, heightOffset - 2* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(2060, heightOffset - 3* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(2090, heightOffset - 4* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(2120, heightOffset - 5* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(2150, heightOffset - 6* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(2180, heightOffset - 7* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(2210, heightOffset - 8* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(2240, heightOffset - 9* BRICK_SIZE, new SquareObstacle()));

        brickArrayList.add(new Obstacle(2300, heightOffset - 6* BRICK_SIZE, new SquareObstacle()));
        brickArrayList.add(new Obstacle(2360, heightOffset - 3* BRICK_SIZE, new SquareObstacle()));

        brickArrayList.add(new Obstacle(2400, heightOffset - BRICK_SIZE, new SquareObstacle()));

    }

    /**
     * adds squareObstacles to the board which is later seen as floor by the player
     */
    public void createFloor(){
        for (int i = 0; i <= pixelWidth; i+= BRICK_SIZE){
            brickArrayList.add(new Obstacle(i, heightOffset, new SquareObstacle()));
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
            int newPositionX = brick.getPositionX() - PIXELS_PER_UPDATE;
            if (newPositionX > -BRICK_SIZE) {
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
     * @param nextPlayerX Next player position in X-axis
     * @param nextPlayerY Next player position in Y-axis
     * @return if player collides with obstacle
     */
    public boolean willCollide(int nextPlayerX, int nextPlayerY){
	int nextPlayerXWidth = nextPlayerX + BRICK_SIZE;
        for (Obstacle obstacle : brickEnemies){
	    int nextObstacleXWidth = obstacle.getPositionX() + BRICK_SIZE;
	    if (!(nextPlayerXWidth < obstacle.getPositionX() && !(nextPlayerX > nextObstacleXWidth))) { //Ignores obstacle that are behind or ahead of the nextX position
		//if (obstacle.intersects(nextPlayerX, nextPlayerY)) {
        if (obstacle.runIntersect(nextPlayerX, nextPlayerY, obstacle.getPositionX(), obstacle.getPositionY(), obstacle.getSize().width, obstacle.getSize().height) )
		    return true;
	    }
        }
        return false;
    }
}
