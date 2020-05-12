package objects;

public class ProgressDevelopmentCard extends DevelopmentCard{

	private DevelopmentCardType developmentCardType = DevelopmentCardType.progress;
	
	@Override
	public void playCard(TileType... resources) {
		System.out.print("Playing Progress Card");
	}

	@Override
	public DevelopmentCardType getDevelopmentCardType() {
		return this.developmentCardType;
	}
}
