package superimposiblegame;

import java.awt.*;

/**
 * Created by pierre on 2014-04-16.
 */
public class PlayerView {
    public void draw(Graphics g, int playerPositionX, int playerPositionY, int PLAYER_WIDTH, int PLAYER_HEIGHT) {
        g.setColor(Color.BLUE);
        //g.drawOval(playerPositionX, playerPositionY, PLAYER_WIDTH, PLAYER_HEIGHT);
        g.fillRect(playerPositionX, playerPositionY, PLAYER_WIDTH, PLAYER_HEIGHT);
    }
}
