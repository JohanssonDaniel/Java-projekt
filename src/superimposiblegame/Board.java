package superimposiblegame;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Board {
    private final int pixelWidth;
    private final int pixelHeight;
    private final List<Obstacle> brickArrayList;
    private final List<Obstacle> brickEnemies;

    private final static int BRICK_SIZE = Obstacle.SIZE;
    private final static int SPEED = 3;

    private final int heightOffset;

    public Board(int pixelWidth, int pixelHeight) {
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
        brickArrayList = new ArrayList<Obstacle>();
        brickEnemies = new ArrayList<Obstacle>();
        heightOffset = this.pixelHeight - BRICK_SIZE; // Moves the coords for the bricks one brick height up

        brickArrayList.add(new TriangleObstacle(400, heightOffset - BRICK_SIZE));

        brickArrayList.add(new SquareObstacle(500, heightOffset - BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(700, heightOffset - BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(730, heightOffset - BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(800, heightOffset - BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(830, heightOffset - BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(860, heightOffset - BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(890, heightOffset - 2*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(920, heightOffset - 2*BRICK_SIZE));
        brickArrayList.add(new SquareObstacle(950, heightOffset - 2*BRICK_SIZE));

        brickArrayList.add(new SquareObstacle(1010, heightOffset - 3*BRICK_SIZE));
        //brickArrayList.add(new SquareObstacle(1040, heightOffset - 3*BRICK_SIZE));
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


        createFloor();
        seperateEnemies();
    }

    private void createFloor(){
        for (int i = 0; i <= pixelWidth; i+= BRICK_SIZE){
            brickArrayList.add(new SquareObstacle(i, heightOffset));
        }
    }

    private void seperateEnemies() {
        for (Obstacle obstacle : brickArrayList){
            if (obstacle.getPositionY() < heightOffset){
                brickEnemies.add(obstacle);
            }
        }
    }

    public void moveEnemiesCloser() {
        Iterator<Obstacle> brickIterator = brickEnemies.iterator();

        while (brickIterator.hasNext()) {
            Obstacle obstacle = brickIterator.next();
            int newPositionX = obstacle.getPositionX() - SPEED;
            if (newPositionX > -BRICK_SIZE) {
                obstacle.setPositionX(newPositionX);
            } else {
                brickIterator.remove();
                brickArrayList.remove(obstacle);
            }
        }
    }

    /*public int findFloor(){
        int locationY = pixelHeight;
        for (Obstacle obstacle : brickArrayList) {
            if (obstacle.getPositionY() < locationY && obstacle.getPositionX() == 0)
                locationY = obstacle.getPositionY();   // reduces locationY and sends that y value to the player
        }
        return locationY - BRICK_SIZE; //Puts the player on brick above the floors y value
    }*/

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

    public void displayBoard(Graphics g) {
        for(Obstacle obstacle : brickArrayList) {
            obstacle.draw(g);
        }
    }
}
