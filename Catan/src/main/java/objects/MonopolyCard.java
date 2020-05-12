package objects;

public class MonopolyCard extends DevelopmentCard {
	private PlayersController playerController;

	public MonopolyCard(PlayersController playerController) {
		this.playerController = playerController;
	}

	@Override
	public void playCard(TileType... resources) {
		int totalPlayers = playerController.getTotalNumOfPlayers();
		int currentPlayerNum = playerController.getCurrentPlayerNum();
		Player currentPlayer = playerController.getCurrentPlayer();
		for (int i = 0; i < totalPlayers; i++) {
			if (i != currentPlayerNum) {
				Player playerToSteal = playerController.getPlayer(i);
				int amountToSteal = playerToSteal.getResourceCount(resources[0]);
				playerToSteal.removeResource(resources[0], amountToSteal);
				currentPlayer.addResource(resources[0], amountToSteal);
			}
		}
	}

	@Override
	public DevelopmentCardType getDevelopmentCardType() {
		return DevelopmentCardType.monopoly_card;
	}
	
}
