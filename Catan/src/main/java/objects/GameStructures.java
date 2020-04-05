package objects;

public abstract class GameStructures {
	private Player owner;
	
	public GameStructures(Player owner) {
		this.owner = owner;
	}
	
	public Player getOwner() {
		return this.owner;
	}
}
