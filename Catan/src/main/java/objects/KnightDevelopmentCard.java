package objects;

public class KnightDevelopmentCard extends DevelopmentCard{
	
	private DevelopmentCardType developmentCardType = DevelopmentCardType.knight;

	@Override
	public DevelopmentCardType getDevelopmentCardType() {
		return this.developmentCardType;
	}
}
