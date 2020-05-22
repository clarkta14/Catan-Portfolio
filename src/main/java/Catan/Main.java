package Catan;

import gui.GameWindow;
import gui.LanguageSelectPrompt;
import gui.SetupPrompt;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String args[]) {
		LanguageSelectPrompt languageSelect = new LanguageSelectPrompt();
		int numberOfPlayers;

		while(true) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (languageSelect.isLanguageSelected()) {
				break;
			}
		}
		
		SetupPrompt catanSetup = new SetupPrompt();
		while(true) {
			try {
				numberOfPlayers = catanSetup.getNumPlayers();
				break;
			} catch (IllegalArgumentException e) { }
		}
		GameWindow catan = new GameWindow(numberOfPlayers);
	}
}