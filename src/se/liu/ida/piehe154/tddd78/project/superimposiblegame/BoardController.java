package se.liu.ida.piehe154.tddd78.project.superimposiblegame;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by pierre on 2014-04-16.
 * Contains the methods that is called by the gamecontroller
 */
public class BoardController {

    private BoardView theView = new BoardView();
    private BoardModel theModel = new BoardModel(GameView.getPixelWidth(), GameView.getPixelHeight());

    private List<BoardListener> boardListeners = new ArrayList<BoardListener>();

    public BoardController() {
        this.theModel.createEnemies();
        this.theModel.createFloor();
        this.theModel.seperateEnemies();
    }

    public void addBoardListener(BoardListener bl) {
        boardListeners.add(bl);
    }

    public void notifyListeners() {
        for (BoardListener listener : boardListeners) {
            listener.boardChanged();
        }
    }

    public void moveEnemies(){
        theModel.moveEnemiesCloser();
        notifyListeners();
    }

    public void display(Graphics g){
        theView.displayBoard(g, theModel.getBricks());
    }

    public boolean collidesWith(int nextPlayerPositionY, int nextPlayerPositionX, int playerWidth, int playerHeight){
        return (theModel.willHitFloor(nextPlayerPositionY) || theModel.willCollide(nextPlayerPositionX, nextPlayerPositionY, playerWidth, playerHeight));
    }

    public boolean willColide(int nextPlayerPositionX, int playerPositionY, int playerWidth, int playerHeight) {
        return theModel.willCollide(nextPlayerPositionX, playerPositionY, playerWidth, playerHeight);
    }
}