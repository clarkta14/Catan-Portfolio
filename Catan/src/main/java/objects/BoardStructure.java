package objects;

public abstract class BoardStructure {
	private Player owner = null;
	private StructurePosition locationOnBoard;
	
	public void setOwner(Player plyr) {
		if(owner == null) {
			this.owner = plyr;
		}else {
			throw new IllegalArgumentException("Structure already has assigned player");
		}
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	protected void setLocation(StructurePosition structurePosition) {
		this.locationOnBoard = structurePosition;
	}
	
	public StructurePosition getLocationOnBoard() {
		return this.locationOnBoard;
	}
}
