package SuperImpossibleGame;

import java.awt.*;

public class Player extends RectangleObject implements gameObject{

    private static final int NOT_JUMPING = 0;
    private static final int RISING = 1;
    private static final int FALLING = 2;
    private final int horizontalStep;
    private int vertMoveMode;

    private Board board;
    private boolean moving;

    private static final int MAX_UP_COUNT = 8;
    private int upCount;
    private int vertStep;

    public Player(Board board) {
        super(0, board.findFloor());
        this.board = board;

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
        if (vertMoveMode == NOT_JUMPING){
           checkWillFall();
        }
        else if (vertMoveMode == RISING) {
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
            int newPositionY = getPositionY();
            newPositionY -= vertStep;
            setPositionY(newPositionY);
            upCount++;
        }
    }

    private void finishJumping() {
        vertMoveMode = NOT_JUMPING;
        upCount = 0;
    }

    private void checkWillFall(){
        int nextPlayerPositionX = getPositionX() + horizontalStep;
        int nextPlayerPositionXWidth = nextPlayerPositionX + getSize().width;

        if (board.willFallOfBrick(nextPlayerPositionX, nextPlayerPositionXWidth)){
            vertMoveMode = FALLING;
        }
    }

    private void updateFalling() {
        int nextPlayerPositionY = getPositionY() + vertStep;
        int nextPlayerPositionX = getPositionX() + horizontalStep;
        if (board.willHitFloor(nextPlayerPositionY) || board.collideWhileJumping(nextPlayerPositionX, nextPlayerPositionY)){
            setPositionY(getPositionY());
            finishJumping();
        }
        else{
            setPositionY(nextPlayerPositionY);
        }

    }

    public boolean willCollide(){

        int nextPlayerPositionX = getPositionX() + horizontalStep;
        if (board.collideWhileJumping(nextPlayerPositionX, getPositionY())){
            return true;
        }
        return false;
    }
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(getPositionX(), getPositionY(), getSize().width, getSize().height);
    }
}
