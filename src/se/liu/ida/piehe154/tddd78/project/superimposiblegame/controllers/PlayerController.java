package se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.ObstacleTypes;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.PlayerModel;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.PlayerView;
import java.awt.*;

/**
 * Created by pierre on 2014-04-16.
 * Delegates method calls from the GameController to the PlayerModel
 */
public class PlayerController
{

    private PlayerView theView = new PlayerView();
    private PlayerModel theModel = new PlayerModel();
    private BoardController boardController;
    private boolean gameOver = false;

    public PlayerController(BoardController boardController) {
	this.boardController = boardController;
	theModel.setPlayerStopJumping();
    }

    public void notifyBoardListener() {
	boardController.notifyListeners();
    }

    /**
     * Updates the player based on its state
     */
    public void updatePlayer() {

	if (theModel.isNotJumping()) {
	    updateFalling();
	} else {
	    updateRising();
	}
	notifyBoardListener();
    }

    /**
     * Checks wether the player will fall on a object on the board
     */
    private void updateFalling() {
	int nextPlayerPositionY = theModel.getPlayerPositionY() + theModel.getVerticalStep();
	int nextPlayerPositionX = theModel.getPlayerPositionX() + theModel.getHorizontalStep();

	if (boardController.collidesWith(nextPlayerPositionX, nextPlayerPositionY, theModel.getPlayerHeight(),
					 theModel.getPlayerWidth())) {
	    ObstacleTypes obstacleType= boardController.getIntersectedObstacle();
	    if (obstacleType == ObstacleTypes.TRIANGLE) {
		gameOver = true;
	    } else if (obstacleType == ObstacleTypes.SQUARE){
		finishJumping();
	    } else if (obstacleType == ObstacleTypes.OVAL) {
		finishJumping();
		jump();
	    }
	}
	else{
	    theModel.setPlayerPositionY(nextPlayerPositionY);
	}
	notifyBoardListener();
    }

    /**
     * Checks wether the player has jump high enough or updates its Y position
     * If the player jumps into a an obstacle from beneath it it will crash
     */
    private void updateRising() {
	int nextPlayerPositionY = theModel.getPlayerPositionY();
	int nextPlayerPositionX = theModel.getPlayerPositionX() + theModel.getHorizontalStep();

	if (theModel.reachedMaxUpCount()){
		startToFall();
	}
	else {
	    if(boardController.collidesWith(nextPlayerPositionX, nextPlayerPositionY, theModel.getPlayerWidth(),
					   theModel.getPlayerHeight())){
		startToFall();
	    }else{
		theModel.increaseUpCount();
		theModel.setPlayerPositionY(theModel.getPlayerPositionY()-theModel.getVerticalStep());
	    }
	}

	notifyBoardListener();
    }

    private void startToFall() {
	theModel.setPlayerFalling();
	theModel.setUpCount(0);
    }

     /**
     * Jump changes the state of the player to jumping which will make the player rise in the nextcoming updates
     */
    public void jump() {

	if (theModel.isStationary()) {
	    theModel.setPlayerJumping();
	    theModel.playJumpSound();
	    theModel.setUpCount(0);
	}
    }

    private void finishJumping(){
	theModel.setPlayerStopJumping();
	theModel.setUpCount(0);
    }
    /**
     * Checks whether the player will collidesWith with something that would end the game
     * @return if player collides
     */
    public void willCollide(){
	int nextPosX = theModel.getPlayerPositionX() + theModel.getHorizontalStep();
	if(boardController.collidesWith(nextPosX,theModel.getPlayerPositionY(),theModel.getPlayerWidth(),theModel.getPlayerHeight())){
	    gameOver = true;
	}
    }

    public void draw(Graphics g){
        theView.draw(g, theModel.getPlayerPositionX(), theModel.getPlayerPositionY(), theModel.getPlayerHeight(), theModel
		.getPlayerWidth());
    }

    public boolean isGameOver() {
	return gameOver;
    }
}
