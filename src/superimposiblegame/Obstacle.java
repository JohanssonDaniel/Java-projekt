package superimposiblegame;

import java.awt.*;

/**
 * Created by pierre on 4/8/14.
 * Contains the base information for each obstacle in the game, such as its size and X and Y position
 * the size is represented as a rectangle even though the Obstacle models might be of a different,
 * but that just makes it easier to get their height and width when we want to draw them
 */
public class Obstacle {
    private final Dimension rectangle;
    private int positionX, positionY;
    private GameObstacle obstacle;
    public static final int SIZE = 30;

    protected Obstacle(int positionX, int positionY, GameObstacle obstacle) {
        this.obstacle = obstacle;
        this.positionX = positionX;
        this.positionY = positionY;
        this.rectangle = new Dimension(SIZE,SIZE);
    }

    public boolean runIntersect(int x, int y, int objectX, int objectY, int objectWidth, int objectHeight){
        return this.obstacle.intersects(x,y, objectX, objectY, objectWidth, objectHeight);
    }

    public void runDraw(Graphics g, int x, int y, int width, int height) {
        this.obstacle.draw(g, x, y, width, height);
    }

    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }

    public Dimension getSize() { return rectangle; }

    public void setPositionX(int newPosition) {
        positionX = newPosition;
    }

    public void setPositionY(int newPosition) {
        positionY = newPosition;
    }

}
