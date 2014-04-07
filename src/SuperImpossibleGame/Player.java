package SuperImpossibleGame;

import java.awt.*;

public class Player implements gameObject {

    private static final int NOT_JUMPING = 0;
    private static final int RISING = 1;
    private static final int FALLING = 2;
    private final int horizontalStep;
    private int vertMoveMode;

    private Board board;

    private int positionX, positionY;
    private boolean moving;
    private static final Dimension playerSize = new Dimension(30, 30);

    private static final int MAX_UP_COUNT = 8;
    private int upCount;
    private int xWorld, yWorld; //Players poistion in the "world"
    private int playerNextPositionX, playerNextPositionY;
    private int vertStep;

    public Dimension getPlayerSize() {
        return playerSize;
    }

    public Player(Board board) {
        this.board = board;
        positionX = 0;
        positionY = board.findFloor();

        xWorld = positionX;
        yWorld = positionY;

        vertStep = playerSize.height; //A players jump of its height each update
        horizontalStep = playerSize.width / 10;

        vertMoveMode = NOT_JUMPING;
        upCount = 0;

        moving = true;
    }

    public void move() {
        if (moving){
            positionX += horizontalStep;
        }
    }

    public void stop(){
        if (moving) {
            moving = false;
        }
    }

    public void jump() {
        if (vertMoveMode == NOT_JUMPING) {
            vertMoveMode = RISING;
            upCount = 0;
        }
    }

    public void updatePlayer() {

        if (vertMoveMode == RISING) {
            updateRising();
        }
        else if (vertMoveMode == FALLING) {
            updateFalling();
        }
        move();
    }

    private void updateRising() {
        if (upCount == MAX_UP_COUNT) {
            vertMoveMode = FALLING;
            upCount = 0;
        } else {

            yWorld -= vertStep;
            positionY -= vertStep;
            upCount++;
        }
    }

    private void updateFalling() {

        playerNextPositionX = xWorld + horizontalStep;
        playerNextPositionY = yWorld + vertStep;

        Point nextPoint = new Point(playerNextPositionX, playerNextPositionY);

        int yTrans = board.checkTopOfBrick(nextPoint, vertStep);
        if (yTrans == 0)   // hit the top of a brick
            finishJumping();
        else {    // can move downwards another step
            yWorld += yTrans;   // update position
            positionY += yTrans;
        }
    }

    private void finishJumping() {
        vertMoveMode = NOT_JUMPING;
        upCount = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(positionX, positionY, playerSize.width, playerSize.height);
        g.setColor(Color.CYAN);
    }

    public boolean willCollide(){

        playerNextPositionX = positionX + horizontalStep;
        playerNextPositionY = positionY + vertStep;

        Point point = new Point(playerNextPositionX, playerNextPositionY);

        if (board.collideWithSideOfBrick(point)){
            return true;
        }
        return false;
    }
}
