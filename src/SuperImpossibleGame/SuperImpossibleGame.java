package SuperImpossibleGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SuperImpossibleGame extends JFrame implements WindowListener{
    private static int DEFAULT_FPS = 30;

    private GameComponent gameComponent;       // This is where the game is drawn

    public SuperImpossibleGame(long period) {
        super("SuperImpossibleGame"); //Title
        createGui(period);
        addWindowListener(this);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void createGui(long period){
        Container c = getContentPane(); //Creates the pane that stores the content
        gameComponent = new GameComponent(period);
        c.add(gameComponent, "Center");    //Adds the component board and sets its placement
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        gameComponent.stopGame();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
        gameComponent.pauseGame();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        gameComponent.resumeGame();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        gameComponent.resumeGame();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public static void main(String[] args) {
        int fps = DEFAULT_FPS;

        long period = (long) 1000.0/fps; //The period decides how long a game update can be

        new SuperImpossibleGame(period*1000000L); //Creates a new SuperImpossibleGame.SuperImpossibleGame! with a period of 40/1000 * 1000000L
    }
}