package objects;

public class YearOfPlentyCard extends DevelopmentCard {
	private PlayersController playerController;

	public YearOfPlentyCard(PlayersController playerController) {
		this.playerController = playerController;
	}

	@Override
	public void playCard(TileType... resources) {
		if (resources.length < 2) {
			throw new UnsupportedOperationException("Year of Plenty card needs two resource types");
		}
		
		Player currentPlayer = this.playerController.getCurrentPlayer();
		
		currentPlayer.addResource(resources[0], 1);
		currentPlayer.addResource(resources[1], 1);
	}

	@Override
	public DevelopmentCardType getDevelopmentCardType() {
		return DevelopmentCardType.year_of_plenty_card;
	}

}
