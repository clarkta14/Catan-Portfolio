package objects;

public class KnightDevelopmentCard extends DevelopmentCard{
	
	private DevelopmentCardType developmentCardType = DevelopmentCardType.knight;

	@Override
	public void playCard() {
		System.out.print("Playing Knight Card");
	}

	@Override
	public DevelopmentCardType getDevelopmentCardType() {
		return this.developmentCardType;
	}
}
