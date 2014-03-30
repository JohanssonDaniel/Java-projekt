package SuperImpossibleGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SuperImpossibleGame extends JFrame implements WindowListener{
    private static int DEFAULT_FPS = 30;

    private Board gp;       // This is where the game is drawn

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
        gp = new Board(period);
        c.add(gp, "Center");    //Adds the component gp and sets its placement
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        gp.stopGame();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
        gp.pauseGame();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        gp.resumeGame();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        gp.resumeGame();
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
