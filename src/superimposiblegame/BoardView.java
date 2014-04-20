package superimposiblegame;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by pierre on 2014-04-16.
 * Renders the board which is sent to the Boardcontroller
 */
public class BoardView {

    public void displayBoard(Graphics g, ArrayList<Obstacle> brickArrayList) {
        for(Obstacle obstacle : brickArrayList) {
            obstacle.draw(g);
        }
    }
}
