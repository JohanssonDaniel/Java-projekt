package superimposiblegame;

/**
 * Created by pierre on 2014-04-16.
 * Contians the information of how the player is represented in the game, it size how many updates before it's stops to jump
 * It also checks wether it's next position will collide with an object in the GameBoard
 */
public class PlayerModel {

    private int playerPositionY;
    private int playerPositionX;

    private enum State{NOT_JUMPING, RISING, FALLING}
    private State playerState;

    private final PlayerController controller;

    private static final int MAX_UP_COUNT = 8;
    private int upCount;

    private final int horizontalStep;
    private final int verticalStep;

    private final static int PLAYER_START_POSITION_X = 300;
    private final static int PLAYER_START_POSITION_Y = 540;

    private final static int PLAYER_HEIGHT = 30;
    private final static int PLAYER_WIDTH = 30;

    public PlayerModel(PlayerController controller) { //int START_Y_POSITION
        this.controller = controller;

        playerPositionX = PLAYER_START_POSITION_X;
        playerPositionY = PLAYER_START_POSITION_Y;

        verticalStep = PLAYER_HEIGHT; //A players jump of its height each update
        horizontalStep = PLAYER_WIDTH / 10;

        playerState = State.NOT_JUMPING;
        upCount = 0;
    }

    /**
     * Jump changes the state of the player to jumping which will make the player rise in the nextcoming updates
     */

    public void jump() {
        if (playerState == State.NOT_JUMPING) {
            playerState = State.RISING;
            upCount = 0;
        }
    }

    /**
     * Updates the player based on its state
     */
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

    /**
     * Checks wether the player has jump high enough or updates its Y position
     */
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

    /**
     * Checks wether the player will fall on a object on the board
     */
    private void updateFalling() {

        int nextPlayerPositionY = playerPositionY + verticalStep;
        int nextPlayerPositionX = playerPositionX + horizontalStep;

        if (controller.getCollide(nextPlayerPositionY, nextPlayerPositionX)) {
            finishJumping();
        }
        else{
            playerPositionY = nextPlayerPositionY;
        }

    }

    /**
     * Checks wether the player wil collide with something that would end the game
     * @return
     */
    public boolean willCollide(){

        int nextPlayerPositionX = playerPositionX + horizontalStep;
        if (controller.getWillCollide(nextPlayerPositionX, playerPositionY)){
            return true;
        }
        return false;
    }

    public int getPlayerPositionY() {
        return playerPositionY;
    }

    public int getPlayerPositionX() {
        return playerPositionX;
    }

    public static int getPlayerHeight() {
        return PLAYER_HEIGHT;
    }

    public static int getPlayerWidth() {
        return PLAYER_WIDTH;
    }
}