package se.liu.ida.piehe154.tddd78.project.superimposiblegame.views;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.ObstacleShape;

import java.awt.*;

/**
 * Created by Daniel Johansson on 2014-04-06.
 * Draws the image of a square
 */
public class SquareShape implements ObstacleShape
{
    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.black);
        g.fillRect(x,y, width, height);
    }
}
