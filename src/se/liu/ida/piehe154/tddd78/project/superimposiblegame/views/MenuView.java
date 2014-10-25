package se.liu.ida.piehe154.tddd78.project.superimposiblegame.views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by DannePanne on 2014-10-25.
 */
public class MenuView extends JFrame
{
    private static final int DESC_FONT_SIZE = 22;
    private JPanel gamePanel;       // This is where the game is drawn
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 600;
    private static final int WELCOME_FONT_SIZE = 34;
    private static final int WELCOME_LABEL_WIDTH = 500;
    private static final int WELCOME_LABEL_HEIGHT = 150;
    private static final int DESC_LABEL_WIDTH = 240;
    private static final int DESC_LABEL_HEIGHT = 50;


    private JLabel welcome = new JLabel("Super Impossible Game");
    private JLabel description = new JLabel("please choose a map");

    public MenuView() {
	Container container = getContentPane();
	gamePanel = new JPanel();
	gamePanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
	gamePanel.setFocusable(true);
	gamePanel.requestFocus();    // the JPanel now has focus which allows it to recieve keyboard events

	container.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
	container.add(gamePanel, "Center");

	setResizable(false);
	setVisible(true);
	pack();
    }

    public void createView(Iterable<JButton> buttons){

	setLayout(new FlowLayout());

	Font welcomeFont = new Font("SansSerif", Font.BOLD, WELCOME_FONT_SIZE);
	Font descFont = new Font("SansSerif", Font.PLAIN, DESC_FONT_SIZE);

	welcome.setFont(welcomeFont);
	welcome.setPreferredSize(new Dimension(WELCOME_LABEL_WIDTH, WELCOME_LABEL_HEIGHT));

	description.setFont(descFont);
	description.setPreferredSize(new Dimension(DESC_LABEL_WIDTH,DESC_LABEL_HEIGHT));

	gamePanel.add(welcome); gamePanel.add(description);

	for (JButton button: buttons) {
	    gamePanel.add(button);
	}
	setVisible(true);
	pack();
    }

    public void updateView() {
	gamePanel.repaint();
    }
}
