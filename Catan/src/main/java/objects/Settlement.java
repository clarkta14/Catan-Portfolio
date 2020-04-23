package objects;

public class Settlement extends GameStructures{

	public Settlement(Player owner) {
		super(owner);
	}

	public boolean isCity() {
		return true;
	}
	
}
