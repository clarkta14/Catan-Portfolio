package objects;

public class VictoryPointDevelopmentCard extends DevelopmentCard{

	private DevelopmentCardType developmentCardType = DevelopmentCardType.victory_point;

	@Override
	public void playCard() {
		System.out.print("Playing Victory Point Card");
	}
	
	@Override
	public DevelopmentCardType getDevelopmentCardType() {
		return this.developmentCardType;
	}
}
