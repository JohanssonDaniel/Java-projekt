package SuperImpossibleGame;

import java.awt.*;
import java.util.ArrayList;

public class Brick implements Shape {
    private final int PIXEL_WIDTH;
    private final int PIXEL_HEIGHT;
    private final int BOX_WIDTH = 30;
    private final int BOX_HEIGHT = 30;
    private ArrayList<Rectangle> brick;

    private final static double MOVE_FACTOR = 0.25;
    private int moveSize;

    public int getBoxWidth() {
        return BOX_WIDTH;
    }

    public Brick(int pixelWidth, int pixelHeight) {
        this.PIXEL_WIDTH = pixelWidth;
        this.PIXEL_HEIGHT = pixelHeight;
        brick = new ArrayList<Rectangle>();

        moveSize = (int)(pixelWidth * MOVE_FACTOR); //Decides how many pixels the image moves to the right each update
        if (moveSize == 0){
            moveSize = 1;
        }
        add(pixelWidth-100,pixelHeight- BOX_WIDTH);
        //xMapHead later

        createFloor();
    }

    public void createFloor(){
        for (int i = 0; i <= PIXEL_WIDTH; i+= BOX_WIDTH){
            brick.add(new Rectangle(i, PIXEL_HEIGHT - BOX_WIDTH, BOX_WIDTH, BOX_HEIGHT));
        }
    }

    public void add(int x, int y){
        brick.add(new Rectangle(x, y, BOX_WIDTH, BOX_HEIGHT));
    }

    public boolean collide(Point p){
        Rectangle playerPoint = new Rectangle(p.x,p.y, BOX_WIDTH, BOX_HEIGHT);

        for(Rectangle r : brick){
            if (r.intersects(playerPoint)){
                return true;
            }
        }
        return false;
    }

    public int findFloor(){
        int locY = PIXEL_HEIGHT;
        for (Rectangle r : brick) {
            if (r.getY() < locY)
                locY = (int) r.getY();   // reduce locY (i.e. move up)
        }
        return locY- BOX_WIDTH;
    }

    public int checkTopOfObstacle(int nextX, int nextY, int step){
        Point p = new Point(nextX,nextY);
        if (collide(p)){
            int yMapWorld = nextY - PIXEL_HEIGHT + BOX_WIDTH;/*-height*/
            int mapY = yMapWorld/ BOX_WIDTH;  // map y- index
            int topOffset = yMapWorld - (mapY * BOX_WIDTH);
            int smallStep = step - topOffset;
            return smallStep;
        }
        return step;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for(Rectangle r : brick) {
            g.fillRect(r.x,r.y,r.width,r.height);
        }
    }
}
