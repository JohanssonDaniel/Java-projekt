package se.liu.ida.piehe154.tddd78.project.superimposiblegame.views;

import java.awt.*;

/**
 * Created by pierre on 2014-04-16.
 * Paints the player on the first render
 */
public class PlayerView {
    public void draw(Graphics g, int playerPositionX, int playerPositionY, int playerWidth, int playerHeight) {
        g.setColor(Color.BLUE);
        g.fillRect(playerPositionX, playerPositionY, playerWidth, playerHeight);
    }
}
