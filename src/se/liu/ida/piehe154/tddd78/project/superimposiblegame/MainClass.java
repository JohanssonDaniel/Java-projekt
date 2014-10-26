package se.liu.ida.piehe154.tddd78.project.superimposiblegame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers.MenuController;
/**
 * Created by Daniel Johansson on 2014-10-25.
 * Main class
 */
public final class MainClass
{
    /**
     * Initialize allMaps
     */
    private static ArrayList<String> allMaps = new ArrayList<String>();
    /**
     * Relative filepath to the folder maps
     */
    private static final File MAP_FOLDER = new File("maps");

    private MainClass() {
    }

    /**
     * Looks into the maps folder and saves all different .txt files as strings
     * @param allMaps the list with all strings
     */
    private static void findAllMaps(List<String> allMaps) {
	try {
	    File[] folderFiles = MAP_FOLDER.listFiles();
        assert folderFiles != null;
        for (File fileEntry : folderFiles) {
		if (fileEntry.isDirectory()) {
		    findAllMaps(allMaps);
		} else {
		    String name = fileEntry.getName();
		    if (name.endsWith(".txt")) {
			int indexOfFileExtension = name.indexOf('.', 0); //We only want the name of map, so we remove .txt in filename
			allMaps.add(name.substring(0,indexOfFileExtension));
		    }
		}
	    }
	}catch (RuntimeException e){
	    e.printStackTrace();
	}
    }

    /**
     * Main class, finds all saved maps and delegates the list to the MenuController
     * @param args
     */
    public static void main(String[] args) {
	findAllMaps(allMaps);
	MenuController menuController = new MenuController(allMaps);
	menuController.createMenu();
    }
}
