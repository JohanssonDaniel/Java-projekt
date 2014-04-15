package superimposiblegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SuperImpossibleGame extends JFrame implements WindowListener{

    private GamePanel gamePanel;       // This is where the game is drawn

    public SuperImpossibleGame() {
        super("superimposiblegame"); //Title
        createGui();
        addWindowListener(this);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void createGui(){
        Container container = getContentPane(); //Creates the pane that stores the content
        gamePanel = new GamePanel();
        container.add(gamePanel, "Center");    //Adds the component board and sets its placement
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        gamePanel.stopGame();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
        gamePanel.pauseGame();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        gamePanel.resumeGame();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        gamePanel.resumeGame();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
