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
//		showGUI();
		playerNames = new ArrayList<String>();
	}


	private void showGUI() {
		JFrame frame = new JFrame("CatanSetup");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		// keeps panels from overwriting each other
		GridLayout layout = new GridLayout();
		frame.setLayout(layout);
		
		this.addPlayerPanel(frame);
		this.addInstructionPanel(frame);
		
		frame.setResizable(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true); 
	}
	
	private void addPlayerPanel(JFrame frame) {
		JPanel addPlayersPanel = new JPanel();
		
//		SpringLayout layout = new SpringLayout();
//		addPlayersPanel.setLayout(layout);
		
		JTextField player1NameField = new JTextField(25);
		JTextField player2NameField = new JTextField(25);
		JTextField player3NameField = new JTextField(25);
		JTextField player4NameField = new JTextField(25);
		
		JLabel player1NameFieldLabel = new JLabel("black");
		JLabel player2NameFieldLabel = new JLabel("red");
		JLabel player3NameFieldLabel = new JLabel("blue");
		JLabel player4NameFieldLabel = new JLabel("green");
		
		addPlayersPanel.add(player1NameFieldLabel);
		addPlayersPanel.add(player1NameField);
		addPlayersPanel.add(player2NameFieldLabel);
		addPlayersPanel.add(player2NameField);
		addPlayersPanel.add(player3NameFieldLabel);
		addPlayersPanel.add(player3NameField);
		addPlayersPanel.add(player4NameFieldLabel);
		addPlayersPanel.add(player4NameField);
		
		frame.add(addPlayersPanel);
	}
	
	private void addInstructionPanel(JFrame frame) {
		JPanel instructionsPanel = new JPanel();
		
//		SpringLayout layout = new SpringLayout();
//		instructionsPanel.setLayout(layout);
		
		this.addInstructionsButton(instructionsPanel);
		this.addStartButton(instructionsPanel, frame);
		
		frame.add(instructionsPanel);
	}

	private void addInstructionsButton(JPanel instructionsPanel) {
		JButton instuctionsButton = new JButton("Instructions");
		
		instuctionsButton.addActionListener(new ActionListener() { 
			@Override  
			public void actionPerformed(ActionEvent e) { 
				System.out.println("Pull up another window that has the instructions. Not implemented.");
			}
		});
		
		instructionsPanel.add(instuctionsButton);
	}
	
	private void addStartButton(JPanel instructionsPanel, JFrame frame) {
		JButton startButton = new JButton("Start");
		
		startButton.addActionListener(new ActionListener() { 
			@Override  
			public void actionPerformed(ActionEvent e) { 
				GameWindow catan = new GameWindow();
				frame.dispose();
			}
		});
		
		instructionsPanel.add(startButton);
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
