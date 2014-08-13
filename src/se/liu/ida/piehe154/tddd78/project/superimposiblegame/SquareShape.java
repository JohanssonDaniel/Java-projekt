package se.liu.ida.piehe154.tddd78.project.superimposiblegame;

import java.awt.*;

/**
 * Created by Daniel Johansson on 2014-04-06.
 * the player can safely land on it but will crash if it hits its side
 * Since it extends Obsacle it implements the intersects and draw function
 */
public class SquareShape implements ObstacleShape
{

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.black);
        g.fillRect(x,y, width, height);
    }
}
