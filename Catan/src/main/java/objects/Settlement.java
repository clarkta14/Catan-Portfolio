package objects;

public class Settlement extends GameStructures{
	
	private boolean isCity = false;
	public PortType portType = PortType.none;

	public Settlement(Player owner) {
		super(owner);
	}

	public boolean isCity() {
		return isCity;
	}
	
	public void upgradeToCity() {
		isCity = true;
	}
	
}
