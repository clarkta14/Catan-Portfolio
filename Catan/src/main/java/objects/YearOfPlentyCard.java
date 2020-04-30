package objects;

public class YearOfPlentyCard extends DevelopmentCard {

	public YearOfPlentyCard(PlayersController pc) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playCard() {
		throw new UnsupportedOperationException("Year of Plenty card needs two resource types");
	}

	@Override
	public DevelopmentCardType getDevelopmentCardType() {
		return DevelopmentCardType.year_of_plenty_card;
	}

}
