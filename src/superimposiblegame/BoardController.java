package superimposiblegame;

import java.awt.*;

/**
 * Created by pierre on 2014-04-16.
 */
public class BoardController {

    private BoardView theView = new BoardView();
    private BoardModel theModel = new BoardModel(GameView.PIXEL_WIDTH, GameView.PIXEL_HEIGHT);

    public BoardController() {
        this.theModel.createEnemies();
        this.theModel.createFloor();
        this.theModel.seperateEnemies();
    }

    public int getFloor(){
        return theModel.findFloor();
    }

    public void moveEnemies(){
        theModel.moveEnemiesCloser();
    }

    public void display(Graphics g){
        theView.displayBoard(g, theModel.getBrickArrayList());
    }

    public boolean collide(int nextPlayerPositionY, int nextPlayerPositionX){
        return (theModel.willHitFloor(nextPlayerPositionY) || theModel.collideWhileJumping(nextPlayerPositionX, nextPlayerPositionY));
    }

    public boolean willColide(int nextPlayerPositionX, int playerPositionY) {
        return theModel.collideWhileJumping(nextPlayerPositionX, playerPositionY);
    }
}
