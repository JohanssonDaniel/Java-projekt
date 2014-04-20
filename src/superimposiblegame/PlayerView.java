package superimposiblegame;

import java.awt.*;

/**
 * Created by pierre on 2014-04-16.
 * Paints the player on the first render
 */
public class PlayerView {
    public void draw(Graphics g, int playerPositionX, int playerPositionY, int playerWidth, int playerHeight) {
        g.setColor(Color.BLUE);
        //g.drawOval(playerPositionX, playerPositionY, PLAYER_WIDTH, PLAYER_HEIGHT);
        g.fillRect(playerPositionX, playerPositionY, playerWidth, playerHeight);
    }
}
