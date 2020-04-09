package Catan;

import gui.BoardWindow;
import gui.GameWindow;
import gui.SetupPrompt;

/**
 *
 * @author Indresh
 */
public class Main {

	@SuppressWarnings("unused")
	public static void main(String args[]) {
		SetupPrompt catanSetup = new SetupPrompt();
		int numberOfPlayers;
		while(true) {
			try {
				numberOfPlayers = catanSetup.getNumPlayers();
				break;
			} catch (IllegalArgumentException e) { }
		}
		GameWindow catan = new GameWindow(numberOfPlayers);
	}
}