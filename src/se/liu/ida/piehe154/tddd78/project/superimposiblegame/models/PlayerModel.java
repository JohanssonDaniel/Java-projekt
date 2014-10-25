package se.liu.ida.piehe154.tddd78.project.superimposiblegame.models;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.audio.AudioPlayer;

import java.util.AbstractMap;
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
    private AbstractMap<String, AudioPlayer> playerSFX;

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

    public boolean isNotJumping() {
	return playerState != State.RISING;
    }

    public boolean isStationary(){
	return playerState == State.NOT_JUMPING;
    }

    public void setPlayerPositionY(final int playerPositionY) {
	this.playerPositionY = playerPositionY;
    }

    public void setUpCount(int upCount) {
	this.upCount = upCount;
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

    public boolean reachedMaxUpCount() {
	return upCount == MAX_UP_COUNT;
    }

    public void setPlayerFalling() {
	playerState = State.FALLING;
    }
}