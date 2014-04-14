package SuperImpossibleGame;

import java.awt.*;

public class Player extends RectangleObject implements gameObject{
    private enum State{NOT_JUMPING, RISING, FALLING}
    private State playerState;

    private Board board;
    private boolean moving;

    private static final int MAX_UP_COUNT = 8;
    private int upCount;

    private final int horizontalStep;
    private final int vertStep;

    public Player(Board board) {
        super(300, board.findFloor());
        this.board = board;

        vertStep = getSize().height; //A players jump of its height each update
        horizontalStep = getSize().width / 10;

        playerState = State.NOT_JUMPING;
        upCount = 0;

        moving = true;
    }

    public void stop(){
        if (moving) {
            moving = false;
        }
    }

    public void jump() {
        if (playerState == State.NOT_JUMPING) {
            playerState = State.RISING;
            upCount = 0;
        }
    }

    int count = 0;
    public void updatePlayer() {
        if (playerState == State.NOT_JUMPING){
            //checkWillFall();
            updateFalling();
        }
        else if (playerState == State.RISING) {
            updateRising();
        }
        else if (playerState == State.FALLING) {
            System.out.println("FALLING" + count++);
            updateFalling();
        }
        //move();
        //if(moving) {
        //    board.moveEnemies();
        //}
    }

    private void updateRising() {
        if (upCount == MAX_UP_COUNT) {
            playerState = State.FALLING;
            upCount = 0;
        } else {
            int newPositionY = getPositionY();
            newPositionY -= vertStep;
            setPositionY(newPositionY);
            upCount++;
        }
    }

    private void finishJumping() {
        playerState = State.NOT_JUMPING;
        upCount = 0;
    }

    private void checkWillFall(){

        int nextPlayerPositionX = getPositionX() + horizontalStep;
        int nextPlayerPositionXWidth = nextPlayerPositionX + getSize().width;

        if (board.willFallOfBrick(nextPlayerPositionX, nextPlayerPositionXWidth)){
            playerState = State.FALLING;
            System.out.println("STATE FALLING");
        }
    }

    private void updateFalling() {

        int nextPlayerPositionY = getPositionY() + vertStep;
        int nextPlayerPositionX = getPositionX() + horizontalStep;

        if (board.willHitFloor(nextPlayerPositionY) || board.collideWhileJumping(nextPlayerPositionX, nextPlayerPositionY)) {
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
