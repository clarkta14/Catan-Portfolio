package objects;

public class ProgressDevelopmentCard extends DevelopmentCard{

	private DevelopmentCardType developmentCardType = DevelopmentCardType.progress;

	@Override
	public DevelopmentCardType getDevelopmentCardType() {
		return this.developmentCardType;
	}
}
