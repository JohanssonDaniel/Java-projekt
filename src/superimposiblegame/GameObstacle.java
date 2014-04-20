package superimposiblegame;

import java.awt.*;

/**
 * Interface for the different obstacles
 */
public interface GameObstacle
{
    public void draw(Graphics g);
    public boolean intersects(int playerPositionX, int playerPositionY);
}
