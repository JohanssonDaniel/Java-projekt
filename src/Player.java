import java.awt.*;

/**
 * Created by DannePanne on 2014-03-13.
 */
public class Player implements Shape {
    private int positionX, positionY;
    private Dimension playerSize = new Dimension();

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Dimension getPlayerSize() {
        return playerSize;
    }

    public Player(int PWIDTH, int PHEIGHT) {
        positionX = PWIDTH/2;
        positionY = PHEIGHT/2;

        playerSize.width = 30;
        playerSize.height = 30;
    }

    public void move(int x, int y){
        positionX += x;
        positionY += y;

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(positionX,positionY,playerSize.width,playerSize.height);
    }
}
