package SuperImpossibleGame;

import java.awt.*;

public class Player implements Shape {

    private static final int NOT_JUMPING = 0;
    private static final int RISING = 1;
    private static final int FALLING = 2;
    private final int horizontalStep;
    private int vertMoveMode;

    private Brick br;
    private Obstacle obstacles;

    private int positionX, positionY;
    private boolean moving;
    private Dimension playerSize = new Dimension();

    private static final int MAX_UP_COUNT = 8;
    private int upCount;
    private int xWorld, yWorld; //Players poistion in the "world"
    private int playerNextPositionX, playerNextPositionY;
    private int vertStep;

    public Dimension getPlayerSize() {
        return playerSize;
    }

    public Player(int PWIDTH, int PHEIGHT, Brick br, Obstacle obstacles) {
        this.obstacles = obstacles;
        this.br = br;
        positionX = 0;
        positionY = br.findFloor();

        xWorld = positionX;
        yWorld = positionY;

        playerSize.width = 30;
        playerSize.height = 30;

        vertStep = br.getBOXLENGTH() / 2; //A players jump half a box per tick
        horizontalStep = br.getBOXLENGTH() / 10;

        vertMoveMode = NOT_JUMPING;
        upCount = 0;

        moving = true;
    }

    public void move() {
        if (moving){
            positionX += horizontalStep;
        }
    }

    public void stop(){
        if (moving) {
            moving = false;
        }
    }

    public void jump() {
        if (vertMoveMode == NOT_JUMPING) {
            vertMoveMode = RISING;
            upCount = 0;
        }
    }

    public void updatePlayer() {

        if (vertMoveMode == RISING) {
            updateRising();
        }
        else if (vertMoveMode == FALLING) {
            updateFalling();
        }
        move();

    }

    private void updateRising() {
        if (upCount == MAX_UP_COUNT) {
            vertMoveMode = FALLING;
            upCount = 0;
        } else {
            playerNextPositionX = xWorld + br.getBOXLENGTH() / 2;
            playerNextPositionY = yWorld - vertStep;
            int yTrans = br.checkTopOfObstacle(playerNextPositionX, playerNextPositionY, vertStep);
            if (yTrans == 0) {   // hit the base of a brick
                vertMoveMode = FALLING;   // start falling
                upCount = 0;
            }
            translate(-yTrans);
            yWorld -= yTrans;
            upCount++;
        }
    }

    private void updateFalling() {
        playerNextPositionX = xWorld + br.getBOXLENGTH() / 2;
        playerNextPositionY = yWorld + br.getBOXLENGTH() / 2 + br.getBOXLENGTH();

        int yTrans = br.checkTopOfObstacle(playerNextPositionX,
                playerNextPositionY, vertStep);
        if (yTrans == 0)   // hit the top of a brick
            finishJumping();
        else {    // can move downwards another step
            translate(yTrans);
            yWorld += yTrans;   // update position
        }
    }

    private void translate(int yDist) {
        positionY += yDist;
    }

    private void finishJumping() {
        vertMoveMode = NOT_JUMPING;
        upCount = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(positionX, positionY, playerSize.width, playerSize.height);
    }

    public boolean willCollide(){
        playerNextPositionX = positionX + horizontalStep + playerSize.width/2;
        Point point = new Point(positionX, positionY);
        System.out.println(playerNextPositionX);
        if (obstacles.collide(point)){
            return true;
        }
        return false;
    }
}
