package se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.Menu;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.Audio.AudioPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by pierre on 2014-10-22.
 */
public class MenuController implements ActionListener{
    private Menu game;
    private AudioPlayer bgMusic;

    public MenuController(Menu game) {
        this.game = game;
	bgMusic = new AudioPlayer("/Sound/SuperNinjaAssasin.mp3");
	bgMusic.play();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == game.buttons.get(0)) {
            new GameController("map");
	    bgMusic.stop();
        } else if (e.getSource() == game.buttons.get(1)) {
            new GameController("diehard");
	    bgMusic.stop();
        }
    }
}
