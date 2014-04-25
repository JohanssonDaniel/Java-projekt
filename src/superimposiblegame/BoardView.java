package superimposiblegame;

import java.awt.*;

/**
 * Created by pierre on 2014-04-16.
 * Renders the board which is sent to the Boardcontroller
 */
public class BoardView {

    public void displayBoard(Graphics g, Iterable<Obstacle> brickArrayList) {
        for(Obstacle obstacle : brickArrayList) {
            obstacle.runDraw(g, obstacle.getPositionX(), obstacle.getPositionY(), obstacle.getSize().width, obstacle.getSize().height);
        }
    }
}
