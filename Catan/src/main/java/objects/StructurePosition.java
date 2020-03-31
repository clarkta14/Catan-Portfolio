package objects;

public class StructurePosition extends Position{
	private int angle;
	
	public StructurePosition(int x, int y, int angle) {
		super(x, y);
		this.angle = angle;
	}
	
	public int getAngle() {
		return this.angle;
	}
}
