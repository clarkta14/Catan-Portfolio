package Catan;

import gui.BoardWindow;
import gui.CatanBoard;
import gui.GUIObjectConstructor;
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
		BoardWindow bw = new BoardWindow();
		GameWindow catan = new GameWindow(bw);
		GUIObjectConstructor objConstructor = new GUIObjectConstructor(bw);
		CatanBoard board = new CatanBoard(3, objConstructor);
	}

}