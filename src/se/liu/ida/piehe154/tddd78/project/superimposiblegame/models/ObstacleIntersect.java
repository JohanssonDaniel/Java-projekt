package se.liu.ida.piehe154.tddd78.project.superimposiblegame.models;

/**
 * Created by pierre on 2014-10-18.
 */
public interface ObstacleIntersect {
    public boolean intersect(int positionX, int positionY, int width, int height,int playerPositionX, int playerPositionY, int playerWidth, int playerHeight);
}
