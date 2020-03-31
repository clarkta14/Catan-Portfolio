package objects;

public class Settlement{
	private Position location;
	private Player owner;
	
	public Settlement(int x, int y, Player owner) {
		this.location = new Position(x,y);
		this.owner = owner;
	}
	
	public Position getLocationOnBoard() {
		return this.location;
	}
	
	public Player getOwner() {
		return this.owner;
	}
}
