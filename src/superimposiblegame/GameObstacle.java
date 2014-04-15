package superimposiblegame;

import java.awt.*;

public interface GameObstacle
{
    public void draw(Graphics g);
    public boolean intersects(int playerPositionX, int playerPositionY);
}
