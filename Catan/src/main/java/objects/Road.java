package objects;

public class Road extends GameStructures{
	private int angle;

	public Road(Player owner) {
		super(owner);
		this.angle = 0;
	}
	
	public void setAngle(int a) {
		this.angle = a;
	}
	
	public int getAngle() {
		return this.angle;
	}
}
