package se.liu.ida.piehe154.tddd78.project.superimposiblegame;

import java.awt.*;

/**
 * Created by pierre on 2014-04-18.
 * This obstacle is ment to make the player bounce if it lands on it
 * Since it extends Obsacle it implements the intersects and draw function
 */

public class OvalShape implements ObstacleShape
{

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.black);
        g.fillOval(x,y, width, height);
    }
}