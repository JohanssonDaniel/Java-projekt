package se.liu.ida.piehe154.tddd78.project.superimposiblegame.views;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.ObstacleShape;

import java.awt.*;

/**
 * Created by Daniel Johansson on 2014-04-14.
 * Draws the image of a triangle
 */
public class TriangleShape implements ObstacleShape
{
    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {

	int[] polygonX = { x, (x + (width /2)), (x + width) };
 	int[] polygonY = {  y + height, y, (y + height) };
 	int n = polygonX.length;

        g.setColor(Color.black);
        g.fillPolygon(polygonX, polygonY, n);
    }
}
