package se.liu.ida.piehe154.tddd78.project.superimposiblegame;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers.MenuController;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.Obstacle;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.ObstacleTypes;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.OvalShape;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.SquareShape;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.TriangleShape;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by pierre on 4/7/14.
 */

public class Menu extends JFrame {
    private JPanel gamePanel;       // This is where the game is drawn
    private MenuController menuController;
    public JButton map1 = new JButton("Map");
    public JButton map2 = new JButton("Diehard");
    public JLabel welcome = new JLabel("Super Impossible Game");
    public JLabel description = new JLabel("please choose a map");


    private static ArrayList<String> completedMaps = new ArrayList<String>();
    private static ArrayList<String> allMaps = new ArrayList<String>();
    public ArrayList<JButton> buttons = new ArrayList<JButton>();

    public Menu() {
        Container container = getContentPane();
        gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(600, 600));
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();    // the JPanel now has focus which allows it to recieve keyboard events

        buttons();

        container.setPreferredSize(new Dimension(600, 600));
        container.add(gamePanel, "Center");
        //resetCounter = 0;
        setResizable(false);
        setVisible(true);
        pack();
    }

    private void buttons(){
        setLayout(new FlowLayout());
        /*map1.setPreferredSize(new Dimension(500, 100));
        map1.setBackground(Color.CYAN);
        map2.setPreferredSize(new Dimension(500,100));
        map2.setBackground(Color.CYAN);*/
        Font welcomeFont = new Font("SansSerif", Font.BOLD, 34);
        Font descFont = new Font("SansSerif", Font.PLAIN, 22);

        welcome.setFont(welcomeFont);
        welcome.setPreferredSize(new Dimension(500,150));
        description.setFont(descFont);
        description.setPreferredSize(new Dimension(240,50));

	MenuController menuController = new MenuController(this);

	LineBorder orangeBorder = new LineBorder(Color.ORANGE,2,true);
	add(welcome); add(description);
	for (int n = 0; n < allMaps.size(); n++) {
	    String mapName = allMaps.get(n);
	    JButton map = new JButton(mapName);
	    map.setPreferredSize(new Dimension(500, 100));
	    if (completedMaps.contains(mapName)){
		map.setBackground(Color.GREEN);
	    }else{
		map.setBackground(Color.CYAN);
	    }
	    map.setFont(descFont);
	    map.addActionListener(menuController);
	    map.setBorder(orangeBorder);
	    add(map);
	    buttons.add(n,map);
	}

        /*map1.setFont(descFont);
        map2.setFont(descFont);*/

         //add(map1); add(map2);
	/*map1.addActionListener(menuController);
        map2.addActionListener(menuController);

        LineBorder orangeBorder = new LineBorder(Color.ORANGE,2,true);
        map1.setBorder(orangeBorder);
        map2.setBorder(orangeBorder);*/

        setSize(800, 800);
        setVisible(true);
        pack();
    }
    private static void readCompletedMaps() {
	String thisLine;
	File inFile = new File("completedMaps/completed.txt");
	// open input stream test.txt for reading purpose.

	try{
	    BufferedReader br = new BufferedReader(new java.io.FileReader(inFile));

	    while ((thisLine = br.readLine()) != null) {
		completedMaps.add(thisLine);
	    }

	}catch(Exception e){
	    e.printStackTrace();
	}

    }
    private static void readAllMaps() {
    	String thisLine;
    	File inFile = new File("maps/allMaps");
    	// open input stream test.txt for reading purpose.

    	try{
    	    BufferedReader br = new BufferedReader(new java.io.FileReader(inFile));

    	    while ((thisLine = br.readLine()) != null) {
    		allMaps.add(thisLine);
    	    }

    	}catch(Exception e){
    	    e.printStackTrace();
    	}

        }
    public static void main(String[] args) {
        //new GameController("map");
	readCompletedMaps();
	readAllMaps();
        new Menu();
    }
}