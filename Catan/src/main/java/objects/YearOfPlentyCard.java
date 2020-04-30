package objects;

public class YearOfPlentyCard extends DevelopmentCard {
	private PlayersController playerController;

	public YearOfPlentyCard(PlayersController playerController) {
		this.playerController = playerController;
	}

	@Override
	public void playCard() {
		throw new UnsupportedOperationException("Year of Plenty card needs two resource types");
	}

	@Override
	public DevelopmentCardType getDevelopmentCardType() {
		return DevelopmentCardType.year_of_plenty_card;
	}

	public void playCard(TileType resource1, TileType resource2) {
		Player currentPlayer = this.playerController.getCurrentPlayer();
		
		currentPlayer.addResource(resource1, 1);
		currentPlayer.addResource(resource2, 1);
	}

}
