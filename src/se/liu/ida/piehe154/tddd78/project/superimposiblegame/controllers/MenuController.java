package se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.MVCGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by pierre on 2014-10-22.
 */
public class MenuController implements ActionListener{
    private MVCGame game;

    public MenuController(MVCGame game) {
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
