package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.DevelopmentCardType;
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
	
	private void initializeAllPlayerInfo() {
		for(int i = 0; i < numOfPlayers; i++) {
			JPanel playerPanel = new JPanel();
			playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));
			
			Player player = this.playerController.getPlayer(i);
			JLabel playerNameLabel = new JLabel(Messages.getString("PlayerInfo.0") + (i+1));
			playerNameLabel.setFont(new Font("Ariel", Font.BOLD, 24));
			playerNameLabel.setOpaque(true);
			playerNameLabel.setForeground(new Color(128,128,128));
			playerNameLabel.setBackground(player.getColor());
			playerNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
			playerPanel.add(playerNameLabel);
			
			JLabel victoryPointsLabel = new JLabel(Messages.getString("PlayerInfo.1") + player.getNumberOfVictoryPoints());
			victoryPointsLabel.setFont(new Font("Ariel", Font.BOLD + Font.ITALIC, 14));
			playerPanel.add(victoryPointsLabel);
			
			JLabel resourcesLabel = new JLabel(Messages.getString("PlayerInfo.2"));
			resourcesLabel.setFont(new Font("Ariel", Font.BOLD, 14));
			playerPanel.add(resourcesLabel);
			displayPlayerResources(playerPanel, player);
			
			JLabel devCardsLabel = new JLabel(Messages.getString("PlayerInfo.3"));
			devCardsLabel.setFont(new Font("Ariel", Font.BOLD, 14));
			devCardsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
			playerPanel.add(devCardsLabel);
			displayPlayerDevelopmentCards(playerPanel, player);
			
			
			playerPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));
			add(playerPanel);
		}
	}

	private void displayPlayerResources(JPanel playerPanel, Player player) {
		for(TileType type : TileType.values()) {
			if(type != TileType.desert) {
				JLabel resourceLabel = new JLabel(type.getTileTypeText() + Messages.getString("PlayerInfo.4") + player.getResourceCount(type));
				playerPanel.add(resourceLabel);
			}
		}
	}

	private void displayPlayerDevelopmentCards(JPanel playerPanel, Player player) {
		for(DevelopmentCardType type : DevelopmentCardType.values()) {
			JLabel devCardLabel = new JLabel(type.getDevCardTypeText() + Messages.getString("PlayerInfo.4") + player.getDevelopmentCardCount(type));
			playerPanel.add(devCardLabel);
		}
	}
	
	public void updateAllPlayerInfo() {
		this.removeAll();
		initializeAllPlayerInfo();
		revalidate();
	}
	
}
