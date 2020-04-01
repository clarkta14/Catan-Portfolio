package objects;

public class Road extends GameStructures{
	private int angle;

	public Road(Player owner) {
		super(owner);
	}
	
	public int getAngle() {
		return this.angle;
	}
}
