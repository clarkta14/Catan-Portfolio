package Catan;

import gui.BoardWindow;
import gui.CatanBoard;
import gui.GUIObjectConstructor;
import gui.GameWindow;
import gui.SetupPrompt;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String args[]) {
		BoardWindow bw = new BoardWindow();
		GameWindow catan = new GameWindow(bw);
		GUIObjectConstructor objConstructor = new GUIObjectConstructor(bw);
		CatanBoard board = new CatanBoard(4, objConstructor);
	}
}