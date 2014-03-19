package SuperImpossibleGame;

import java.awt.*;
import java.util.ArrayList;

public class Obstacle implements Shape {
    private int BOXLENGTH = 30;
    private ArrayList obstacles;

    public Obstacle() {
        obstacles = new ArrayList();
    }

    public void add(int x, int y){
        obstacles.add(new Rectangle(x,y,BOXLENGTH, BOXLENGTH));
    }

    public void remove(){
        if (obstacles.size() > 0){
            obstacles.remove(0);
        }
    }

    public boolean collide(Point p, int size){
        Rectangle r = new Rectangle(p.x,p.y,size,size);
        Rectangle obs;

        for(int i = 0; i < obstacles.size(); i++){
            obs = (Rectangle) obstacles.get(i);
            if (obs.intersects(r)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Graphics g) {
        Rectangle box;
        g.setColor(Color.BLUE);
        for(int i=0; i < obstacles.size(); i++) {
            box = (Rectangle) obstacles.get(i);
            g.fillRect(box.x,box.y,box.width,box.height);
        }
    }
}
