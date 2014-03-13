import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Created by DannePanne on 2014-03-13.
 */
public class SuperImpossibleGame extends JFrame implements WindowListener{

    private GamePanel gp;       // This is where the game is drawn

    public SuperImpossibleGame() {
        super("SuperImpossibleGame"); //Title
        createGui();
        addWindowListener(this);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void createGui(){
        Container c = getContentPane(); //Creates the pane that stores the content
        gp = new GamePanel();
        c.add(gp, "Center");    //Adds the component gp and sets its placement
    }
    


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public static void main(String[] args) {
        new SuperImpossibleGame(); //Creates a new SuperImpossibleGame!
    }
}
