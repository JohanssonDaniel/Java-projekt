package SuperImpossibleGame;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private final int PIXEL_WIDTH;
    private final int PIXEL_HEIGHT;
    private ArrayList<Brick> brickArrayList;
    private final int BRICK_SIZE = Brick.SIZE;
    //TOG BORT fältet private Brick brick, brick i denna klass användes endast för att hämta strleken på en rektangel så vi behöver bara hämta konstanten.

    private final static double MOVE_FACTOR = 0.25;
    private int moveSize;

    private int heightOffset;

    public Board(int pixelWidth, int pixelHeight) {
        this.PIXEL_WIDTH = pixelWidth;
        this.PIXEL_HEIGHT = pixelHeight;
        brickArrayList = new ArrayList<Brick>();

        heightOffset = PIXEL_HEIGHT - BRICK_SIZE; // Moves the coords for the bricks one brick height up

        moveSize = (int)(pixelWidth * MOVE_FACTOR); //Decides how many pixels the image moves to the right each update
        if (moveSize == 0){
            moveSize = 1;
        }
        brickArrayList.add(new Brick(500, PIXEL_HEIGHT-60));
        createFloor();
    }

    public void createFloor(){
        for (int i = 0; i <= PIXEL_WIDTH; i+= BRICK_SIZE){
            brickArrayList.add(new Brick(i, heightOffset));
        }
    }

    public int findFloor(){
        int locationY = PIXEL_HEIGHT;
        for (Brick brick : brickArrayList) {
            if (brick.getPositionY() < locationY && brick.getPositionX() == 0)
                locationY = (int) brick.getPositionY();   // reduces locationY and sends that y value to the player
        }
        return locationY - BRICK_SIZE; //Puts the player on brick above the floors y value
    }

    public boolean collideWithSideOfBrick(Point player) {

        Brick playerBrick = new Brick(player.x,player.y);

        for (Brick bricks : brickArrayList){
            if (bricks.collideSide(playerBrick)){
                return true;
            }
        }
        return false;
    }

    public int checkTopOfBrick(Point nextPoint, int step){
        Brick playerBrick = new Brick(nextPoint.x, nextPoint.y);

        for (Brick bricks : brickArrayList){
            if (bricks.collideTop(playerBrick)){
                int yMapWorld = nextPoint.y - PIXEL_HEIGHT + playerBrick.getSize().height;    /*-height*/
                int mapY = (int) (yMapWorld/bricks.getSize().height);  // map y- index
                int topOffset = yMapWorld - (mapY*bricks.getSize().height);
                int smallStep = step - topOffset;
                System.out.println(smallStep);
                return smallStep;
            }
        }
        return step;
    }
    public void displayBoard(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for(Brick b : brickArrayList) {
            b.draw(g);
        }
    }


}
