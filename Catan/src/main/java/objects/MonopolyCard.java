package objects;

public class MonopolyCard extends DevelopmentCard {
	private PlayersController playerController;

	public MonopolyCard(PlayersController playerController) {
		this.playerController = playerController;
	}
	
	@Override
	public void playCard() {
		throw new UnsupportedOperationException("Monopoly card needs a resource type");
	}

	public void playCard(TileType resourceToGain) {
		int totalPlayers = playerController.getTotalNumOfPlayers();
		int currentPlayerNum = playerController.getCurrentPlayerNum();
		Player currentPlayer = playerController.getCurrentPlayer();
		for (int i = 0; i < totalPlayers; i++) {
			if (i != currentPlayerNum) {
				Player playerToSteal = playerController.getPlayer(i);
				int amountToSteal = playerToSteal.getResourceCount(resourceToGain);
				playerToSteal.removeResource(resourceToGain, amountToSteal);
				currentPlayer.addResource(resourceToGain, amountToSteal);
			}
		}
	}

	@Override
	public DevelopmentCardType getDevelopmentCardType() {
		return DevelopmentCardType.monopoly_card;
	}
	
}
