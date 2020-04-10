package objects;

public class Road extends GameStructures{
	private int angle;

	public Road(Player owner) {
		super(owner);
		this.angle = 0;
	}
	
	public void setAngle(int angle) {
		if (angle == 4) {
			throw new IllegalArgumentException("Angle must be 1, 2, or 3.");
		}
		this.angle = angle;
	}
	
	public int getAngle() {
		return this.angle;
	}
}
