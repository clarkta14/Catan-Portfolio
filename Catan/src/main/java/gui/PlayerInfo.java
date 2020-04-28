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
			JLabel playerNameLabel = new JLabel(Messages.getString("PlayerInfo.0") + (i+1)); //$NON-NLS-1$
			playerNameLabel.setFont(new Font("Ariel", Font.BOLD, 24));
			playerNameLabel.setOpaque(true);
			playerNameLabel.setForeground(new Color(128,128,128));
			playerNameLabel.setBackground(player.getColor());
			playerNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
			playerPanel.add(playerNameLabel);
			
			JLabel resourcesLabel = new JLabel(Messages.getString("PlayerInfo.1")); //$NON-NLS-1$
			resourcesLabel.setFont(new Font("Ariel", Font.BOLD, 14));
			playerPanel.add(resourcesLabel);
			displayPlayerResources(playerPanel, player);
			
			// TODO: when implemented, create label for victory points
			
			JLabel devCardsLabel = new JLabel(Messages.getString("PlayerInfo.2")); //$NON-NLS-1$
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
				JLabel resourceLabel = new JLabel(type.name() + Messages.getString("PlayerInfo.3") + player.getResourceCount(type)); //$NON-NLS-1$
				playerPanel.add(resourceLabel);
			}
		}
	}

	private void displayPlayerDevelopmentCards(JPanel playerPanel, Player player) {
		for(DevelopmentCardType type : DevelopmentCardType.values()) {
			JLabel devCardLabel = new JLabel(type.name() + Messages.getString("PlayerInfo.3") + player.getDevelopmentCardCount(type)); //$NON-NLS-1$
			playerPanel.add(devCardLabel);
		}
	}
	
	public void updateAllPlayerInfo() {
		this.removeAll();
		initializeAllPlayerInfo();
		revalidate();
	}
	
}
