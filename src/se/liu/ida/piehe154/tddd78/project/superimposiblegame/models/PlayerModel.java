package se.liu.ida.piehe154.tddd78.project.superimposiblegame.models;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.LjudSpelare;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers.PlayerController;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.Audio.AudioPlayer;

import java.util.HashMap;

/**
 * Created by pierre on 2014-04-16.
 * Contians the information of how the player is represented in the game, it size how many updates before it's stops to jump
 * It also checks wether it's next position will collidesWith with an object in the GameBoard
 */
public class PlayerModel {

    private int playerPositionY;
    private int playerPositionX;


    private enum State{NOT_JUMPING, RISING, FALLING}
    private State playerState;

    private static final int MAX_UP_COUNT = 8;
    private int upCount;

    private final int horizontalStep;
    private final int verticalStep;

    private final static int PLAYER_START_POSITION_X = 300;
    private final static int PLAYER_START_POSITION_Y = 540;

    private final static int PLAYER_HEIGHT = 30;
    private final static int PLAYER_WIDTH = 30;
    private HashMap<String, AudioPlayer> playerSFX;
    //private final LjudSpelare JUMP_SOUND = new LjudSpelare("https://www.dropbox.com/s/6ciy39twgjaalpx/Jump4.wav?dl=1");

    private boolean gameOver = false;

    public PlayerModel() { //int START_Y_POSITION

        playerPositionX = PLAYER_START_POSITION_X;
        playerPositionY = PLAYER_START_POSITION_Y;

        verticalStep = PLAYER_HEIGHT; //A players jump of its height each update
        horizontalStep = PLAYER_WIDTH / 5;

        playerState = State.NOT_JUMPING;
        upCount = 0;

	playerSFX = new HashMap<String, AudioPlayer>();
	playerSFX.put("jump", new AudioPlayer("/Sound/Jump4.wav"));
    }

    /**
     * Jump changes the state of the player to jumping which will make the player rise in the nextcoming updates
     */

    /*public void jump() {
        if (playerState == State.NOT_JUMPING) {
	    playerSFX.get("jump").play();
            playerState = State.RISING;
            upCount = 0;
        }
    }*/

    /**
     * Updates the player based on its state
     */
    /*public void updatePlayer() {
        if (playerState == State.NOT_JUMPING || playerState == State.FALLING ){
            updateFalling();
        }
        else if (playerState == State.RISING) {
            updateRising();
        }
    }

    /**
     * Checks wether the player has jump high enough or updates its Y position
     * If the player jumps into a an obstacle from beneath it it will crash
     *//*
    private void updateRising() {

        int nextPlayerPositionY = playerPositionY;
        int nextPlayerPositionX = playerPositionX + horizontalStep;

        if (upCount == MAX_UP_COUNT) {
            playerState = State.FALLING;
            upCount = 0;
        }
        else {
	    if(theController.getCollide(nextPlayerPositionY, nextPlayerPositionX, PLAYER_WIDTH, PLAYER_HEIGHT)){
		playerState = State.FALLING;
		upCount = 0;
	    }
	    else{
		playerPositionY -= verticalStep;
		upCount++;
	    }
        }
        theController.notifyBoardListener();
    }

    private void finishJumping() {
        playerState = State.NOT_JUMPING;
        upCount = 0;
    }

    /**
     * Checks wether the player will fall on a object on the board
     *//*
    private void updateFalling() {

        int nextPlayerPositionY = playerPositionY + verticalStep;
        int nextPlayerPositionX = playerPositionX + horizontalStep;

        if (theController.getCollide(nextPlayerPositionY, nextPlayerPositionX, PLAYER_WIDTH, PLAYER_HEIGHT)) {
	    if(theController.getIntersectedObstacle() != null) {
		String shapeName = theController.getIntersectedObstacle().getShapeName();
		if (shapeName.equals("triangle")) {
		    gameOver = true;
		} else if (shapeName.equals("square")) {
		    finishJumping();
		} else if (shapeName.equals("oval")) {
		    playerState = State.NOT_JUMPING;
		    jump();
		}
	    }
        }
        else{
            playerPositionY = nextPlayerPositionY;
        }
        theController.notifyBoardListener();

    }

    /**
     * Checks whether the player will collidesWith with something that would end the game
     * @return if player collides
     *//*
    public void willCollide(){

        int nextPlayerPositionX = playerPositionX + horizontalStep;
        if (theController.getWillCollide(nextPlayerPositionX, playerPositionY, PLAYER_WIDTH, PLAYER_HEIGHT)){
            gameOver = true;
        }
    }*/

    public int getPlayerPositionY() {
        return playerPositionY;
    }

    public int getPlayerPositionX() {
        return playerPositionX;
    }

    public int getPlayerHeight() {
        return PLAYER_HEIGHT;
    }

    public int getPlayerWidth() {
        return PLAYER_WIDTH;
    }



    public int getHorizontalStep() {
	return horizontalStep;
    }

    public int getVerticalStep() {
	return verticalStep;
    }

    public HashMap<String, AudioPlayer> getPlayerSFX() {
	return playerSFX;
    }

    public boolean isNotJumping() {
	return !(playerState == State.RISING);
    }

    public boolean isStationary(){
	return playerState == State.NOT_JUMPING;
    }

    public void setPlayerPositionY(final int playerPositionY) {
	this.playerPositionY = playerPositionY;
    }

    public void setPlayerPositionX(final int playerPositionX) {
	this.playerPositionX = playerPositionX;
    }

    public void setUpCount(int upCount) {
	this.upCount = upCount;
    }

    public void playJumpSound(){

    }

    public void setPlayerJumping() {
	playerState = State.RISING;
	playerSFX.get("jump").play();
	playerSFX.get("jump").stop();
    }

    public void setPlayerStopJumping(){
	playerState = State.NOT_JUMPING;
    }
    public void increaseUpCount() {upCount++;}

    public boolean getGameOver() {
	return gameOver;
    }

    public State getPlayerState() {
	return playerState;
    }

    public static int getMaxUpCount() {
	return MAX_UP_COUNT;
    }

    public boolean reachedMaxUpCount() {
	return upCount == MAX_UP_COUNT;
    }

    public void setPlayerFalling() {
	playerState = State.FALLING;
    }
}