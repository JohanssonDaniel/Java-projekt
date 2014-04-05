package SuperImpossibleGame;

import java.awt.*;

public class Player implements gameObject {

    private static final int NOT_JUMPING = 0;
    private static final int RISING = 1;
    private static final int FALLING = 2;
    private final int horizontalStep;
    private int vertMoveMode;

    private Board board;
    private SuperImpossibleGame.Brick obstacles;

    private int positionX, positionY;
    private boolean moving;
    private Dimension playerSize = new Dimension();

    private static final int MAX_UP_COUNT = 8;
    private int upCount;
    private int xWorld, yWorld; //Players poistion in the "world"
    private int playerNextPositionX, playerNextPositionY;
    private int vertStep;

    public Dimension getPlayerSize() {
        return playerSize;
    }

    public Player(int PWIDTH, int PHEIGHT, Board board, SuperImpossibleGame.Brick obstacles) {
        this.obstacles = obstacles;
        this.board = board;
        positionX = 0;
        positionY = board.findFloor();

        xWorld = positionX;
        yWorld = positionY;

        vertStep = board.getBrickSize().height / 2; //A player jumps half a box each update
        horizontalStep = board.getBrickSize().width / 5; //A player moves 1/5 of box eaxh update

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

        playerNextPositionX = xWorld + board.getBrickSize().height / 2;
        playerNextPositionY = yWorld + board.getBrickSize().height / 2;

        int yTrans = board.checkTopOfBrick(playerNextPositionX,
                playerNextPositionY, vertStep);
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
        g.setColor(Color.BLACK);
        g.fillRect(positionX, positionY, playerSize.width, playerSize.height);
    }

    public boolean willCollide(){
        playerNextPositionX = positionX + horizontalStep + playerSize.width/2;
        Point point = new Point(positionX, positionY);
        if (obstacles.collide(point)){
            return true;
        }
        return false;
    }
}
