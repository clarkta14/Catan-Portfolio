package gui;

import java.util.Scanner;

public class SetupPrompt {
	
	public int numPlayers;
	
	public void scanPlayerNum() {
		Scanner input = new Scanner(System.in);
		System.out.println("How many players in this game?");
		this.numPlayers = getPlayerNum(input.nextLine());
	}


	public int getPlayerNum(String numPlayersString) {
		if(numPlayersString.equals("3")) {
			return 3;
		} else if(numPlayersString.equals("4")) {
			return 4;
		} else {
			throw new IllegalArgumentException("numPlayers must either be intergers 3 or 4");
		}
	}
}
