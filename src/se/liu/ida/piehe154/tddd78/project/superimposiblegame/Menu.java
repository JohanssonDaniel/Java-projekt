package se.liu.ida.piehe154.tddd78.project.superimposiblegame;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers.MenuController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by pierre on 4/7/14.
 */

public class Menu extends JFrame {
    private JPanel gamePanel;       // This is where the game is drawn
    private MenuController menuController;

    private static File mapDirectory = new File("maps/");
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 600;
    private static final int WELCOME_FONT_SIZE = 34;
    private static final int DESC_FONT_SIZE = 22;
    private static final int WELCOME_LABEL_WIDTH = 500;
    private static final int WELCOME_LABEL_HEIGHT = 150;
    private static final int DESC_LABEL_WIDTH = 240;
    private static final int DESC_LABEL_HEIGHT = 50;
    private static final int MAP_BUTTON_WIDTH = 500;
    private static final int MAP_BUTTON_HEIGHT = 50;

    public JLabel welcome = new JLabel("Super Impossible Game");
    public JLabel description = new JLabel("please choose a map");

    private static ArrayList<JButton> buttons = new ArrayList<JButton>();
    private static ArrayList<String> completedMaps = new ArrayList<String>();
    private static ArrayList<String> allMaps = new ArrayList<String>();

    public Menu() {
        Container container = getContentPane();
        gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();    // the JPanel now has focus which allows it to recieve keyboard events

        buttons();

        container.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        container.add(gamePanel, "Center");

        setResizable(false);
        setVisible(true);
        pack();
    }

    private void buttons(){
        setLayout(new FlowLayout());
        Font welcomeFont = new Font("SansSerif", Font.BOLD, WELCOME_FONT_SIZE);
        Font descFont = new Font("SansSerif", Font.PLAIN, DESC_FONT_SIZE);

        welcome.setFont(welcomeFont);
        welcome.setPreferredSize(new Dimension(WELCOME_LABEL_WIDTH,WELCOME_LABEL_HEIGHT));
        description.setFont(descFont);
        description.setPreferredSize(new Dimension(DESC_LABEL_WIDTH,DESC_LABEL_HEIGHT));

        menuController = new MenuController(this);

        LineBorder orangeBorder = new LineBorder(Color.ORANGE,2,true);
        add(welcome); add(description);
        for (int n = 0; n < allMaps.size(); n++) {
            String mapName = allMaps.get(n);
            JButton mapButton = new JButton(mapName);
            mapButton.setPreferredSize(new Dimension(MAP_BUTTON_WIDTH, MAP_BUTTON_HEIGHT));
            if (completedMaps.contains(mapName)){
                mapButton.setBackground(Color.GREEN);
            }else{
                mapButton.setBackground(Color.CYAN);
            }
            mapButton.setFont(descFont);
            mapButton.addActionListener(menuController);
            mapButton.setBorder(orangeBorder);
            add(mapButton);
            buttons.add(n, mapButton);
        }

        //setSize(800, 800);
        setVisible(true);
        pack();
    }
    private static void readCompletedMaps() {
        // open input stream test.txt for reading purpose.
        try{
            File inFile = new File("completedMaps/completed.txt");
            BufferedReader br = new BufferedReader(new FileReader(inFile));
            String thisLine;
            thisLine = br.readLine();
            while (thisLine != null) {
                completedMaps.add(thisLine);
                thisLine = br.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private static void findAllMaps(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                findAllMaps(fileEntry);
            } else {
                String name = fileEntry.getName();
                if (name.endsWith(".txt")) {
                    int indexOfFileExtension = name.indexOf('.', 0); //We only want the name of map, so we remove .txt in filename
                    System.out.println(name.substring(0,indexOfFileExtension));
                    allMaps.add(name.substring(0,indexOfFileExtension));
                }
            }
        }
    }

    public void updateButtons() {
        readCompletedMaps();
        for (int n = 0; n < buttons.size(); n++) {
            JButton button = buttons.get(n);
            String mapName = button.getText();
            if (completedMaps.contains(mapName)){
                button.setBackground(Color.GREEN);
            }
        }
    }

    public static void main(String[] args) {
        readCompletedMaps();
        findAllMaps(mapDirectory);
        new Menu();
    }
}