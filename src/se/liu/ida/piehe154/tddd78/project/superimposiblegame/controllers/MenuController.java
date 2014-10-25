package se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.audio.AudioPlayer;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.models.MenuModel;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.views.MenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by pierre on 2014-10-22.
 */
public class MenuController implements ActionListener{
    private static ArrayList<String> completedMaps = new ArrayList<String>();
    private ArrayList<String> allMaps = new ArrayList<String>();
    private static final Path COMPLETED_MAPS_PATH = Paths.get("completedMaps", "completed.txt");
    private static final Path MENU_MUSIC_PATH = Paths.get("src", "Sound", "SuperNinjaAssasin.mp3");
    private MenuView theView = null;
    private MenuModel theModel = null;
    private AudioPlayer bgMusic;

    public MenuController(ArrayList<String> allMaps) {
	this.allMaps = allMaps;
	bgMusic = new AudioPlayer(MENU_MUSIC_PATH);
	bgMusic.play();
    }

    public void createMenu(){
	theModel  = new MenuModel(this);
	theView = new MenuView();
	readCompletedMaps();
	theModel.createButtons(allMaps, completedMaps);
	theView.createView(theModel.getButtons());
    }

    public void updateMenu(){
	readCompletedMaps();
	theModel.updateButtons(completedMaps);
	theView.updateView();
    }

    @SuppressWarnings("SuppressionAnnotation")
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonPressed;
        buttonPressed = (JButton)e.getSource();
        String mapName = buttonPressed.getText();

        //We dont need to keep track of our game in our MenuController
        GameController gameController = new GameController(mapName, this);
	gameController.startGame();
    }


    private static void readCompletedMaps() {
	// open input stream test.txt for reading purpose.
	BufferedReader bufferedReader = null;
	try{
	    File inFile = new File(COMPLETED_MAPS_PATH.toAbsolutePath().toString());
	    bufferedReader = new BufferedReader(new FileReader(inFile));
	    String thisLine;
	    thisLine = bufferedReader.readLine();
	    while (thisLine != null) {
		completedMaps.add(thisLine);
		thisLine = bufferedReader.readLine();
	    }
	}catch(IOException e){
	    e.printStackTrace();
	}finally {
	    try {
            assert bufferedReader != null;
            bufferedReader.close();
	    }catch (Exception e){
		e.printStackTrace();
	    }
	}
    }
}
