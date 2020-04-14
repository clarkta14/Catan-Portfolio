package objects;

import java.awt.Color;
import java.util.HashMap;

public class Player {
	private Color color;
	private HashMap<TileType, Integer> resources;
		
	@SuppressWarnings("serial")
	public Player(Color color) {
		this.color = color;
		this.resources = new HashMap<TileType, Integer>() {{
			for(TileType type : TileType.values()) {
				put(type, 0);
			}
		}};
	}

	public Color getColor() {
		return this.color;
	}

	public void addResource(TileType type, int numberOfResource) {
		if(numberOfResource < 0 || this.resources.get(type) == Integer.MAX_VALUE || numberOfResource > Integer.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		int prevNumResource = this.resources.get(type);
		this.resources.replace(type, prevNumResource + numberOfResource);
	}

	public int getResource(TileType type) {
		return this.resources.get(type);
	}
}
