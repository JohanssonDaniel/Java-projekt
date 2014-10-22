package se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.Obstacle;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.PlayerModel;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.PlayerView;

import javax.swing.plaf.nimbus.State;
import java.awt.*;

/**
 * Created by pierre on 2014-04-16.
 * Delegates method calls from the GameController to the PlayerModel
 */
public class PlayerController  {

    private PlayerView theView = new PlayerView();
    private PlayerModel theModel = new PlayerModel(this);
    private BoardController boardController;

    public Obstacle getIntersectedObstacle(){
	return boardController.getIntersectedObstacle();
    }

    public PlayerController(BoardController boardController) {
        this.boardController = boardController;
    }

    public Boolean getCollide(int playerPositionY, int playerPositionX, int playerWidth, int playerHeight) {
        return boardController.collidesWith(playerPositionY, playerPositionX, playerWidth, playerHeight);
    }

    public boolean getWillCollide(int playerPositionX, int playerPositionY, int playerWidth, int playerHeight){
        return boardController.willCollide(playerPositionX, playerPositionY, playerWidth, playerHeight);
    }

    public void willCollide() {
        theModel.willCollide();
    }

    public void jump() {
        theModel.jump();
    }

    public void notifyBoardListener() {
        boardController.notifyListeners();
    }


    public void updatePlayer() {
        theModel.updatePlayer();
    }

    public void draw(Graphics g){
        theView.draw(g, theModel.getPlayerPositionX(), theModel.getPlayerPositionY(), PlayerModel.getPlayerHeight(), PlayerModel
		.getPlayerWidth());
    }

    public boolean getGameOver(){
	return theModel.getGameOver();
    }
}
