package SuperImpossibleGame;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private final int PIXEL_WIDTH;
    private final int PIXEL_HEIGHT;

    private ArrayList<Brick> brickArrayList;
    private ArrayList[] columnList; //List of all the bricks in a column

    private final int BRICK_SIZE = Brick.SIZE;
    //TOG BORT fältet private Brick brick, brick i denna klass användes endast för att hämta strleken på en rektangel så vi behöver bara hämta konstanten.

    private final static double MOVE_FACTOR = 0.25;
    private int moveSize;

    private int heightOffset;
    private int numCols, numRows;

    private static final int floorCoord = 0;

    public Board(int pixelWidth, int pixelHeight) {

        PIXEL_WIDTH = pixelWidth;
        PIXEL_HEIGHT = pixelHeight;

        numCols = PIXEL_WIDTH / BRICK_SIZE; //calculates the number of columns a static screen can have
        numRows = PIXEL_HEIGHT / BRICK_SIZE; //calculates the number of rows a static screen can have

        brickArrayList = new ArrayList<Brick>();

        heightOffset = PIXEL_HEIGHT - BRICK_SIZE; // Moves the coords for the bricks one brick height up

        moveSize = (int)(pixelWidth * MOVE_FACTOR); //Decides how many pixels the image moves to the right each update
        if (moveSize == 0){
            moveSize = 1;
        };
        createFloor();


        createColumns();

    }

    public void createFloor(){
        for (int x = 0; x <= numCols; x ++){
            brickArrayList.add(new Brick(x, floorCoord));
        }
    }

    public int findFloor(){

        int locationY = PIXEL_HEIGHT;

        return locationY - BRICK_SIZE*2; //Puts the player on brick above the floors y value
    }

    private void createColumns(){ //Lägger till rätt bricks i rätt kolumn
        columnList = new ArrayList[numCols+1];
        for (int c = 0; c <= numCols; c++) {
            columnList[c] = new ArrayList();
        }
        Brick brick;
        for (int b = 0; b < brickArrayList.size(); b++){
            brick = (Brick) brickArrayList.get(b);
            columnList[brick.getPositionX()].add(brick); //Adds the bricks with same x-coord to the same list
        }
    }

    public boolean collideWithSideOfBrick(Point player) {
        Point coordPlayer = pixelToCoord(player.x, player.y);
        ArrayList column = columnList[coordPlayer.x];

        for (Brick bricks : brickArrayList){
            if (coordPlayer.y == bricks.getPositionY()){
                return true;
            }
        }
        return false;
    }

    private Point pixelToCoord(int pixelX, int pixelY){ //Gör om spelarens pixelPosition till koordinat
        int coordX =  (pixelX % PIXEL_WIDTH)/PIXEL_WIDTH;
        int coordY =  (pixelY % PIXEL_HEIGHT)/PIXEL_HEIGHT;
        return new Point(coordX, coordY-1);
    }

    private Brick biggestY(int positionX){//Picks out the biggest positionY aka, the brick that is highest
        Brick highestBrick = null;
        for (Brick brick : brickArrayList){
            if (brick.getPositionX() == positionX){
                if (highestBrick == null){
                    highestBrick = brick;
                }
                else if (brick.getPositionY() <= highestBrick.getPositionY() ){
                    highestBrick = brick;
                }
            }
        }
        return highestBrick;
    }

    public int checkTopOfBrick(Point nextPoint, int step) {
        Brick brick = biggestY(nextPoint.x);
        int tempStep = step;
        if (brick == null)
            return tempStep;

        else{
            if (nextPoint.y == brick.getPositionY()) {
                int yMapWorld = nextPoint.y - brick.getPositionY();    /*-height*/
                int topOffset = yMapWorld;
                int smallStep = 0;
                tempStep = smallStep;
                }
            }
        return tempStep;
    }

    public void displayBoard(Graphics g) {
        ArrayList column;
        int xCoord = 0;
        g.setColor(Color.DARK_GRAY);
        for (int pixelX = 0; pixelX < PIXEL_WIDTH; pixelX += BRICK_SIZE) {
            column = columnList[xCoord];
            for (int i = 0; i < column.size(); i++) { //Ritar ut spelplanen kolumn för kolumn
                Brick brick = (Brick) column.get(i);
                brick.draw(g, PIXEL_HEIGHT, pixelX, numRows);
            }
            xCoord++;
        }
    }
}
