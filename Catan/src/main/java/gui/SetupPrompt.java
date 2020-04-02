package gui;

import java.util.Scanner;

public class SetupPrompt {
	
	private int numPlayers = 0;
	
	public void scanPlayerNum() {
		Scanner input = new Scanner(System.in);
		System.out.println("How many players in this game?");
		getPlayerNum(input.nextLine());
		input.close();
	}


	public void getPlayerNum(String numPlayersString) {
		if(numPlayersString.equals("3")) {
			this.numPlayers = 3;
		} else if(numPlayersString.equals("4")) {
			this.numPlayers = 4;
		} else {
			throw new IllegalArgumentException("numPlayers must either be intergers 3 or 4");
		}
	}
	
	public int getNumPlayers() {
		if(this.numPlayers == 0) {
			throw new IllegalArgumentException("numPlayers is not yet initialized");
		} else {
			return this.numPlayers;
		}
	}
}
