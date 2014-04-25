package superimposiblegame;

/**
 * Created by pierre on 4/7/14.
 */
@SuppressWarnings("ResultOfObjectAllocationIgnored")
public final class MVCGame {
    private MVCGame() {}

    public static void main(String[] args) {

	//We dont need to use it as a variable.
	new GameController();
    }
}
