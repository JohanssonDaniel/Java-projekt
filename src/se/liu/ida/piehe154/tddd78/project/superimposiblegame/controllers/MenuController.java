package se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.Menu;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.Audio.AudioPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by pierre on 2014-10-22.
 */
public class MenuController implements ActionListener{
    private Menu menu;
    private AudioPlayer bgMusic;

    public MenuController(Menu menu) {
        this.menu = menu;
	    bgMusic = new AudioPlayer("/Sound/SuperNinjaAssasin.mp3");
	    bgMusic.play();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonPressed;
        buttonPressed = (JButton)e.getSource();
        String mapName = buttonPressed.getText();
        //noinspection ResultOfObjectAllocationIgnored
        new GameController(mapName, menu);
    }
}
