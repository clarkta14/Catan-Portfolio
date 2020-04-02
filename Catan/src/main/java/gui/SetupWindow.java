package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class SetupWindow {
	
	public List<String> playerNames;
	
	public SetupWindow() {
		playerNames = new ArrayList<String>();
	}
	
	public void scanForInput() {
		Scanner input = new Scanner(System.in);
		System.out.println("How many players in this game?");
		int numPlayers = getPlayerNum(input.nextLine());
		
		for (int i = 1; i <= numPlayers; i++) {
			input = new Scanner(System.in);
			System.out.println("What is the name of Player " + i);
			String playerName = input.nextLine();
			createPlayer(playerName);
		}
	}
	
	public void createPlayer(String playerName) {
		this.playerNames.add(playerName);
	}


	public int getPlayerNum(String numPlayersString) {
		if(numPlayersString == "3") {
			return 3;
		} else if(numPlayersString == "4") {
			return 4;
		} else {
			throw new IllegalArgumentException("numPlayers must either be intergers 3 or 4");
		}
	}
}
