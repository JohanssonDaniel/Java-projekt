package SuperImpossibleGame;

import java.awt.*;

public class Player extends Rectangle implements gameObject {

    private static final int NOT_JUMPING = 0;
    private static final int RISING = 1;
    private static final int FALLING = 2;
    private final int horizontalStep;
    private int vertMoveMode;

    private Board board;
    private boolean moving;

    // ERSATT MED RECTANGLE ABSTRACTION
    //private int positionX, positionY;
    //private static final Dimension playerSize = new Dimension(30, 30);
    //public Dimension getPlayerSize() { return playerSize; }
    private static final int MAX_UP_COUNT = 8;
    private int upCount;
    private int xWorld, yWorld; //Players poistion in the "world"
    private int playerNextPositionX, playerNextPositionY;
    private int vertStep;

    public Player(Board board) {
        super(0, board.findFloor());
        this.board = board;
        //positionX = 0;
        //positionY = board.findFloor();

        xWorld = getPositionX();
        yWorld = getPositionY();

        vertStep = getSize().height; //A players jump of its height each update
        horizontalStep = getSize().width / 10;

        vertMoveMode = NOT_JUMPING;
        upCount = 0;

        moving = true;
    }

    public void move() {
        if (moving){
            int newPositionX = getPositionX();
            newPositionX += horizontalStep;
            setPositionX(newPositionX);
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
            int newPositionY = getPositionY();
            newPositionY -= vertStep;
            setPositionY(newPositionY);
            //positionY -= vertStep;
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

            int newPositionY = getPositionY();
            newPositionY += yTrans;
            setPositionY(newPositionY);
            //positionY += yTrans;
        }
    }

    private void finishJumping() {
        vertMoveMode = NOT_JUMPING;
        upCount = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(getPositionX(), getPositionY(), getSize().width, getSize().height);
        g.setColor(Color.CYAN);
    }

    public boolean willCollide(){

        playerNextPositionX = getPositionX() + horizontalStep;
        playerNextPositionY = getPositionY() + vertStep;

        Point point = new Point(playerNextPositionX, playerNextPositionY);

        if (board.collideWithSideOfBrick(point)){
            return true;
        }
        return false;
    }
}
