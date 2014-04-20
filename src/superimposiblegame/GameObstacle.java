package superimposiblegame;

import java.awt.*;

/**
 * Interface for the different obstacles
 */
public interface GameObstacle
{
    public void draw(Graphics g, int x, int y, int width, int height);
    public boolean intersects(int playerPositionX, int playerPositionY, int objectX, int objectY, int objectWidth, int objectHeight);
}
