package se.liu.ida.piehe154.tddd78.project.superimposiblegame.views;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.ObstacleShape;

import java.awt.*;

/**
 * Created by pierre on 2014-04-18.
 * Draws the image of an Oval
 */

public class OvalShape implements ObstacleShape
{

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.black);
        g.fillOval(x,y, width, height);
    }
}