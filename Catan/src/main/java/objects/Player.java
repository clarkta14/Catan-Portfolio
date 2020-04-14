package objects;

import java.awt.Color;

public class Player {
	private Color color;
		
	public Player(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public void addResource(TileType brick, int numberOfResource) {
		
	}

	public int getResource(TileType brick) {
		return 1;
	}
}
