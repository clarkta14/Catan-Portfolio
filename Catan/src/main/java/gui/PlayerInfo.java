package gui;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.Player;
import objects.PlayersController;
import objects.TileType;

@SuppressWarnings("serial")
public class PlayerInfo extends JPanel {

	private PlayersController playerController;
	private int numOfPlayers;

	public PlayerInfo(PlayersController playerController, int numOfPlayers) {
		this.playerController = playerController;
		this.numOfPlayers = numOfPlayers;
		initializeAllPlayerInfo();
	}
	
	public void initializeAllPlayerInfo() {
		for(int i = 0; i < numOfPlayers; i++) {
			JPanel playerPanel = new JPanel();
			playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));
			
			Player player = this.playerController.getPlayer(i);
			JLabel playerNameLabel = new JLabel("Player " + (i+1));
			playerNameLabel.setFont(new Font("Ariel", Font.BOLD, 24));
			playerNameLabel.setForeground(player.getColor());
			
			displayPlayerResources(playerPanel, player, playerNameLabel);
			
			// TODO: when implemented, create labels for victory points and development cards
			
			playerPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
			add(playerPanel);
			
		}
	}

	private void displayPlayerResources(JPanel playerPanel, Player player, JLabel playerNameLabel) {
		playerPanel.add(playerNameLabel);
		for(TileType type : TileType.values()) {
			JLabel resourceLabel = new JLabel(type.name() + ": " + player.getResource(type));
			playerPanel.add(resourceLabel);
		}
	}
	
	public void updateAllPlayerInfo() {
		
	}
	
}
