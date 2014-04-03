package SuperImpossibleGame;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by DannePanne on 2014-03-31.
 */
public class Obstacle implements Shape{
    private ArrayList<Rectangle> obstacles;
    private int PHEIGHT, PWIDTH;

    public Obstacle(int PWIDTH, int PHEIGHT) {
        this.PHEIGHT = PHEIGHT;
        this.PWIDTH = PWIDTH;

        obstacles = new ArrayList<Rectangle>();
        obstacles.add(new Rectangle(PWIDTH / 2, PHEIGHT - 60, 30, 30));
    }
    /*public boolean collide(int playerX, int playerY){
        Point point = new Point(playerX, playerY);
        for (Rectangle rectangle : obstacles){
            if (rectangle.intersects(point)){
                return true;
            }
        }
        return false;
   }*/

    public boolean collide(Point p){
        Rectangle playerPoint = new Rectangle(p.x,p.y,30,30);

        for(Rectangle r : obstacles){
            if (r.intersects(playerPoint)){
                return true;
            }
        }
        return false;
    }





    @Override
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for(Rectangle r : obstacles) {
            g.fillRect(r.x,r.y,r.width,r.height);
        }
    }
}
