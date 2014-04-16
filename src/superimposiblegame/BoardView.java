package superimposiblegame;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by pierre on 2014-04-16.
 */
public class BoardView {

    public void displayBoard(Graphics g, ArrayList<Obstacle> brickArrayList) {
        for(Obstacle b : brickArrayList) {
            b.draw(g);
        }
    }
}
