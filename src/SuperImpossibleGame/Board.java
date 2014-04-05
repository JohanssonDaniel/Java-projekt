package SuperImpossibleGame;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private final int PIXEL_WIDTH;
    private final int PIXEL_HEIGHT;
    private final int heightOffset;
    private final boolean isMoving;

    private ArrayList<Brick> brickList;

    private Brick brick;

    private final static double MOVE_FACTOR = 0.25;
    private int moveSize;

    private int xMapHead;

    public Board(int PIXEL_WIDTH, int PIXEL_HEIGHT) {
        this.PIXEL_WIDTH = PIXEL_WIDTH;
        this.PIXEL_HEIGHT = PIXEL_HEIGHT;
        heightOffset = PIXEL_HEIGHT - brick.getBrickSize().height;

        brickList = new ArrayList<Brick>();

        moveSize = (int)(PIXEL_WIDTH * MOVE_FACTOR); //Decides how many pixels the image moves to the right each update
        xMapHead = 0;

        isMoving = false;

        createFloor();
    }

    public void createFloor(){
        for (int i = 0; i <= PIXEL_WIDTH; i+= brick.getBrickSize().width){
            brickList.add(new Brick(i, heightOffset));
        }
    }

    public boolean insideBrick(int xWorld, int yWorld){ //Checks if the players net move will bring it inside a brick
        Point playerCoord = worldToMap(xWorld, yWorld);

        for(Brick b : brickList){
            if (playerCoord.y == b.getPositionY()){
                return true;
            }
        }
        return false;
    }

    private Point worldToMap(int xWorld, int yWorld)
    // convert player coord (x,y) to a map index tuple
    {


        //xWorld = xWorld /* % width;   // limit to range (width to -width)*/
        /*if (xWorld < 0)            // make positive
            xWorld += width;*/
        int mapX = xWorld;/*/imWidth);   // map x-index*/

        yWorld = yWorld - PIXEL_HEIGHT;/*;  // relative to map*/
        int mapY = yWorld;  // map y-index

        if (yWorld < 0)   // above the top of the bricks
            mapY = mapY-1;  // match to next 'row' up

        // System.out.println("Map: " + mapX + ", " + mapY);
        return new Point(mapX, mapY);
    }  // end of worldToMap()

    public int findFloor(){
        int locationY = PIXEL_HEIGHT;
        for (Brick b : brickList) {
            if (b.getPositionY() < locationY)
                locationY = (int) b.getPositionY();   // reduce locY (i.e. move up)
        }
        return locationY-brick.getBrickSize().height;
    }

    public int checkTopOfBrick(int nextX, int nextY, int step){
        Point p = new Point(nextX,nextY);
        if (collide(p)){
            int yMapWorld = nextY - PIXEL_HEIGHT + brickSize.height;/*-height*/
            int mapY = yMapWorld/brickSize.height;  // map y- index
            int topOffset = yMapWorld - (mapY * brickSize.height);
            int smallStep = step - topOffset;
            return smallStep;
        }
        return step;
    }

    /*@Override
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for(Rectangle r : brick) {
            g.fillRect(r.x,r.y,r.width,r.height);
        }
    }*/
    public void update(){
        if (isMoving){
            xMapHead = (xMapHead + moveSize);
        }
    }

    public void display(Graphics graphics){

    }

}
