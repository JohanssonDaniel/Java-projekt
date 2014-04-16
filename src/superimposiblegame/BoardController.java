package superimposiblegame;

import java.awt.*;

/**
 * Created by pierre on 2014-04-16.
 */
public class BoardController {

    private BoardView theView;
    private BoardModel theModel;

    public BoardController(BoardView theView, BoardModel theModel) {
        this.theView = theView;
        this.theModel = theModel;


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
