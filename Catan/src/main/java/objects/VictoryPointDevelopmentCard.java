package objects;

public class VictoryPointDevelopmentCard extends DevelopmentCard{

	private DevelopmentCardType developmentCardType = DevelopmentCardType.victory_point;
	
	@Override
	public DevelopmentCardType getDevelopmentCardType() {
		return this.developmentCardType;
	}
}
