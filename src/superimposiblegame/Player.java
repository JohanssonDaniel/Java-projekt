package superimposiblegame;

import java.awt.*;

public class Player {

    private int playerPositionY;
    private int playerPositionX;

    private enum State{NOT_JUMPING, RISING, FALLING}
    private State playerState;

    private final BoardController board;
    private boolean moving;

    private static final int MAX_UP_COUNT = 8;
    private int upCount;

    private final int horizontalStep;
    private final int verticalStep;

    private final static int PLAYER_START_POSITION_X = 300;
    private final static int PLAYER_START_POSITION_Y = 540;

    private final static int PLAYER_HEIGHT = 30;
    private final static int PLAYER_WIDTH = 30;

    public Player(BoardController board, int START_Y_POSITION) {
        this.board = board;

        playerPositionX = PLAYER_START_POSITION_X;
        playerPositionY = PLAYER_START_POSITION_Y;

        verticalStep = PLAYER_HEIGHT; //A players jump of its height each update
        horizontalStep = PLAYER_WIDTH / 10;

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

    public void updatePlayer() {
        if (playerState == State.NOT_JUMPING){
            updateFalling();
        }
        else if (playerState == State.RISING) {
            updateRising();
        }
        else if (playerState == State.FALLING) {
            updateFalling();
        }
    }

    private void updateRising() {
        if (upCount == MAX_UP_COUNT) {
            playerState = State.FALLING;
            upCount = 0;
            }
        else {
            playerPositionY -= verticalStep;
            upCount++;
        }
    }

    private void finishJumping() {
        playerState = State.NOT_JUMPING;
        upCount = 0;
    }

    private void updateFalling() {

        int nextPlayerPositionY = playerPositionY + verticalStep;
        int nextPlayerPositionX = playerPositionX + horizontalStep;

        if (board.collide(nextPlayerPositionY, nextPlayerPositionX)) {
            finishJumping();
        }
        else{
            playerPositionY = nextPlayerPositionY;
        }

    }

    public boolean willCollide(){

        int nextPlayerPositionX = playerPositionX + horizontalStep;
        if (board.willColide(nextPlayerPositionX, playerPositionY)){
            return true;
        }
        return false;
    }
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(playerPositionX, playerPositionY, PLAYER_WIDTH, PLAYER_HEIGHT);
    }
}
