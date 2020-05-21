package objects;

import gui.Messages;

/**
*
* @author Indresh
*/
public enum TileType {
   wool(Messages.getString("TileType.wool")),
   wood(Messages.getString("TileType.wood")),
   brick(Messages.getString("TileType.brick")),
   wheat(Messages.getString("TileType.wheat")),
   ore(Messages.getString("TileType.ore")),
   desert(Messages.getString("TileType.desert"));
	
	private String tileTypeText;

	private TileType(String tileTypeText) {
		this.tileTypeText = tileTypeText;
	}

	public String getTileTypeText() {
		return tileTypeText;
	}
}
