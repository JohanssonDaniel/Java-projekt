package SuperImpossibleGame;

import java.awt.*;
import java.util.ArrayList;

public class Brick implements Shape {
    private final int PWIDTH;
    private final int PHEIGHT;
    private int BOXLENGTH = 30;
    private ArrayList<Rectangle> obstacles;

    private final static double MOVE_FACTOR = 0.25;
    private int moveSize;

    public int getBOXLENGTH() {
        return BOXLENGTH;
    }

    public Brick(int PWIDTH, int PHEIGHT) {
        this.PWIDTH = PWIDTH;
        this.PHEIGHT = PHEIGHT;
        obstacles = new ArrayList<Rectangle>();

        moveSize = (int)(PWIDTH * MOVE_FACTOR); //Decides how many pixels the image moves to the right each update
        if (moveSize == 0){
            moveSize = 1;
        }
        add(PWIDTH-100,PHEIGHT-BOXLENGTH);
        //xMapHead later

        createFloor();
    }

    public void createFloor(){
        for (int i = 0; i <= PWIDTH; i+= BOXLENGTH){
            obstacles.add(new Rectangle(i,PHEIGHT-BOXLENGTH,BOXLENGTH,BOXLENGTH));
        }
    }

    public void add(int x, int y){
        obstacles.add(new Rectangle(x,y,BOXLENGTH, BOXLENGTH));
    }

    public boolean collide(Point p){
        Rectangle playerPoint = new Rectangle(p.x,p.y,BOXLENGTH,BOXLENGTH);

        for(Rectangle r : obstacles){
            if (r.intersects(playerPoint)){
                return true;
            }
        }
        return false;
    }

    public int findFloor(){
        int locY = PHEIGHT;
        for (Rectangle r : obstacles) {
            if (r.getY() < locY)
                locY = (int) r.getY();   // reduce locY (i.e. move up)
        }
        return locY-BOXLENGTH;
    }

    public int checkTopOfObstacle(int nextX, int nextY, int step){
        Point p = new Point(nextX,nextY);
        if (collide(p)){
            int yMapWorld = nextY - PHEIGHT + BOXLENGTH;/*-height*/
            int mapY = yMapWorld/BOXLENGTH;  // map y- index
            int topOffset = yMapWorld - (mapY * BOXLENGTH);
            int smallStep = step - topOffset;
            return smallStep;
        }
        return step;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for(Rectangle r : obstacles) {
            g.fillRect(r.x,r.y,r.width,r.height);
        }
    }
}
