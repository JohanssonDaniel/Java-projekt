package superimposiblegame;

/**
 * Created by pierre on 4/7/14.
 */

public final class MVCGame {
    private MVCGame() {}

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {

	//We dont need to use it as a variable, hence the SuppressWarnings.
	new GameController();
    }
}
