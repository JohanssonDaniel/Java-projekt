package superimposiblegame;

import java.awt.*;

/**
 * Created by pierre on 2014-04-16.
 */
public class PlayerController {

    private PlayerView theView = new PlayerView();
    private PlayerModel theModel = new PlayerModel(this);
    private BoardController boardController;

    public PlayerController(BoardController boardController) {
        this.boardController = boardController;
    }

    public boolean getCollide(int playerPositionY, int playerPositionX) {
        return boardController.collide(playerPositionY, playerPositionX);
    }

    public boolean getWillCollide(int playerPositionX, int playerPositionY){
        return boardController.willColide(playerPositionX, playerPositionY);
    }

    public boolean willCollide() {
        return theModel.willCollide();
    }

    public void jump() {
        theModel.jump();
    }

    public void stop() {
        theModel.stop();
    }

    public void updatePlayer() {
        theModel.updatePlayer();
    }

    public void draw(Graphics g){
        theView.draw(g, theModel.getPlayerPositionX(), theModel.getPlayerPositionY(), theModel.getPlayerHeight(), theModel.getPlayerWidth());
    }
}
