package se.liu.ida.piehe154.tddd78.project.superimposiblegame;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers.MenuController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by pierre on 4/7/14.
 */

public class Menu extends JFrame{
    private JPanel gamePanel;       // This is where the game is drawn
    private MenuController menuController;
    public JButton map1 = new JButton("Map");
    public JButton map2 = new JButton("Diehard");
    public JLabel welcome = new JLabel("Super Impossible Game");
    public JLabel description = new JLabel("please choose a map");



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
        map1.setPreferredSize(new Dimension(500, 100));
        map1.setBackground(Color.CYAN);
        map2.setPreferredSize(new Dimension(500,100));
        map2.setBackground(Color.CYAN);
        Font welcomeFont = new Font("SansSerif", Font.BOLD, 34);
        Font descFont = new Font("SansSerif", Font.PLAIN, 22);

        welcome.setFont(welcomeFont);
        welcome.setPreferredSize(new Dimension(500,150));
        description.setFont(descFont);
        description.setPreferredSize(new Dimension(240,50));

        map1.setFont(descFont);
        map2.setFont(descFont);

        add(welcome); add(description); add(map1); add(map2);

        MenuController menuController = new MenuController(this);
        map1.addActionListener(menuController);
        map2.addActionListener(menuController);

        LineBorder orangeBorder = new LineBorder(Color.ORANGE,2,true);
        map1.setBorder(orangeBorder);
        map2.setBorder(orangeBorder);

        setSize(800, 800);
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        //new GameController("map");
        new Menu();
    }
}