package objects;

public class Settlement{
	private Player owner;
	
	public Settlement(Player owner) {
		this.owner = owner;
	}
	
	public Player getOwner() {
		return this.owner;
	}
}
