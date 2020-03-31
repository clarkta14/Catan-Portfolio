package objects;

import java.awt.Point;

public class Settlement {
	private Point location;
	
	public Settlement(Point p) {
		this.location = p;
	}
	
	public Point getLocationOnBoard() {
		return this.location;
	}
}
