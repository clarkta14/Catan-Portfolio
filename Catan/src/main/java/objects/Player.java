package objects;

import java.awt.Color;
import java.util.HashMap;

public class Player {
	private Color color;
	private HashMap<TileType, Integer> resources;
		
	public Player(Color color) {
		this.color = color;
		this.resources = new HashMap<TileType, Integer>();
	}

	public Color getColor() {
		return this.color;
	}

	public void addResource(TileType brick, int numberOfResource) {
		this.resources.put(brick, numberOfResource);
	}

	public int getResource(TileType type) {
		return this.resources.get(type);
	}
}
