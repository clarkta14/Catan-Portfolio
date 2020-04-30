package objects;

public class MonopolyCard extends DevelopmentCard {

	public MonopolyCard(PlayersController playerController) {
		
	}

	public void playCard(TileType resourceToGain) {
		
	}

	@Override
	public DevelopmentCardType getDevelopmentCardType() {
		return DevelopmentCardType.monopoly_card;
	}

	@Override
	public void playCard() {
		throw new UnsupportedOperationException("Monopoly card needs a resource type");
	}

}
