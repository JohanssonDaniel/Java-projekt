package se.liu.ida.piehe154.tddd78.project.superimposiblegame;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers.MenuController;

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

    private static File mapDirectory = new File("maps/");
    public JLabel welcome = new JLabel("Super Impossible Game");
    public JLabel description = new JLabel("please choose a map");


    private static ArrayList<String> completedMaps = new ArrayList<String>();
    private static ArrayList<String> allMaps = new ArrayList<String>();

    public Menu() {
        Container container = getContentPane();
        gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(600, 600));
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();    // the JPanel now has focus which allows it to recieve keyboard events

        buttons();

        container.setPreferredSize(new Dimension(600, 600));
        container.add(gamePanel, "Center");

        setResizable(false);
        setVisible(true);
        pack();
    }

    private void buttons(){
        setLayout(new FlowLayout());
        Font welcomeFont = new Font("SansSerif", Font.BOLD, 34);
        Font descFont = new Font("SansSerif", Font.PLAIN, 22);

        welcome.setFont(welcomeFont);
        welcome.setPreferredSize(new Dimension(500,150));
        description.setFont(descFont);
        description.setPreferredSize(new Dimension(240,50));

        menuController = new MenuController(this);

        LineBorder orangeBorder = new LineBorder(Color.ORANGE,2,true);
        add(welcome); add(description);
        for (int n = 0; n < allMaps.size(); n++) {
            String mapName = allMaps.get(n);
            JButton mapButton = new JButton(mapName);
            mapButton.setPreferredSize(new Dimension(500, 50));
            if (completedMaps.contains(mapName)){
                mapButton.setBackground(Color.GREEN);
            }else{
                mapButton.setBackground(Color.CYAN);
            }
            mapButton.setFont(descFont);
            mapButton.addActionListener(menuController);
            mapButton.setBorder(orangeBorder);
            add(mapButton);
        }

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

    public static void findAllMaps(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                findAllMaps(fileEntry);
            } else {
                String name = fileEntry.getName().toString();
                if (name.endsWith(".txt")) {
                    int indexOfFileExtension = name.indexOf('.', 0); //We only want the name of map, so we remove .txt in filename
                    System.out.println(name.substring(0,indexOfFileExtension));
                    allMaps.add(name.substring(0,indexOfFileExtension));
                }
            }
        }
    }

    public static void main(String[] args) {
        readCompletedMaps();
        findAllMaps(mapDirectory);
        new Menu();
    }
}