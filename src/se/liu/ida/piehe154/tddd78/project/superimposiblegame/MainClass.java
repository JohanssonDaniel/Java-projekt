package se.liu.ida.piehe154.tddd78.project.superimposiblegame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers.MenuController;
/**
 * Created by DannePanne on 2014-10-25.
 */
public final class MainClass
{
    private static ArrayList<String> allMaps = new ArrayList<String>();
    private static final File MAP_FOLDER = new File("maps");

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

    public static void main(String[] args) {
	findAllMaps(allMaps);
	MenuController menuController = new MenuController(allMaps);
	menuController.createMenu();
    }
}
