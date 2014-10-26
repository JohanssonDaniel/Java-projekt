package se.liu.ida.piehe154.tddd78.project.superimposiblegame.models;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers.MenuController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Daniel Johansson 2014-10-25.
 * The model for the menu is a list of JButtons that gets created at the start of the gae and updated each time
 * the player completes a game.
 */
public class MenuModel
{
    private static final int MAP_BUTTON_WIDTH = 500;
    private static final int MAP_BUTTON_HEIGHT = 50;
    private static final int DESC_FONT_SIZE = 22;
    private static final Font DESC_FONT = new Font("SansSerif", Font.PLAIN, DESC_FONT_SIZE);

    private MenuController menuController;
    private List<JButton> buttons = new ArrayList<JButton>();

    public MenuModel(MenuController menuController) {
	this.menuController = menuController;
    }

    /**
     * Creates and saves the same amount of buttons as there are saved maps in a List.
     * Checks to see if a map is completed and changes the color of the button if that is the case
     * @param allMaps
     * @param completedMaps
     */
    public void createButtons(Iterable<String> allMaps, List<String> completedMaps){

	LineBorder orangeBorder = new LineBorder(Color.ORANGE,2,true);

	for (String map : allMaps) {
	    JButton mapButton = new JButton(map);
	    mapButton.setPreferredSize(new Dimension(MAP_BUTTON_WIDTH, MAP_BUTTON_HEIGHT));
	    if (completedMaps.contains(map)){
		mapButton.setBackground(Color.GREEN);
	    }else{
		mapButton.setBackground(Color.CYAN);
	    }
	    mapButton.setFont(DESC_FONT);
	    mapButton.addActionListener(menuController);
	    mapButton.setBorder(orangeBorder);
	    buttons.add(mapButton);
	}
    }

    /**
     * Updates the color of the button if its associated map has been completed
     * @param completedMaps
     */
    public void updateButtons(List<String> completedMaps) {
	for (JButton button : buttons) {
	    String mapName = button.getText();
	    if (completedMaps.contains(mapName)){
		button.setBackground(Color.GREEN);
	    }
	}
    }

    public Iterable<JButton> getButtons() {
	return buttons;
    }
}
