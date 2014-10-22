package se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by pierre on 2014-10-22.
 */
public class MenuController implements ActionListener{
    private Menu game;

    public MenuController(Menu game) {
        this.game = game;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() ==game.map1) {
            new GameController("map");
        } else if (e.getSource() == game.map2) {
            System.out.println("2");
            new GameController("diehard");
        }
    }
}
