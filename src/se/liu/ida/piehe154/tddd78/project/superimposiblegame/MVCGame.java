package se.liu.ida.piehe154.tddd78.project.superimposiblegame;

import se.liu.ida.piehe154.tddd78.project.superimposiblegame.controllers.GameController;

/**
 * Created by pierre on 4/7/14.
 */

public final class MVCGame {
    private MVCGame() {}

    public static void main(String[] args) {

	//We dont need to use it as a variable, we just start the game. Hence the warning "new GameController() is ignored"
	new GameController();
    }
}
