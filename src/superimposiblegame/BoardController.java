package superimposiblegame;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by pierre on 2014-04-16.
 * Contains the methods that is called by the gamecontroller
 */
public class BoardController {

    private BoardView theView = new BoardView();
    private BoardModel theModel = new BoardModel(GameView.PIXEL_WIDTH, GameView.PIXEL_HEIGHT);

    private List<BoardListener> board_listeners = new ArrayList<BoardListener>();

    public BoardController() {
        this.theModel.createEnemies();
        this.theModel.createFloor();
        this.theModel.seperateEnemies();
    }

    public void addBoardListener(BoardListener bl) {
        board_listeners.add(bl);
    }

    public void notifyListeners() {
        for (BoardListener listener : board_listeners) {
            listener.boardChanged();
        }
    }

    public void moveEnemies(){
        theModel.moveEnemiesCloser();
        notifyListeners();
    }

    public void display(Graphics g){
        theView.displayBoard(g, theModel.getBrickArrayList());
    }

    public boolean collide(int nextPlayerPositionY, int nextPlayerPositionX){
        return (theModel.willHitFloor(nextPlayerPositionY) || theModel.willCollide(nextPlayerPositionX, nextPlayerPositionY));
    }

    public boolean willColide(int nextPlayerPositionX, int playerPositionY) {
        return theModel.willCollide(nextPlayerPositionX, playerPositionY);
    }
}
