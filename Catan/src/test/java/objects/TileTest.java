package objects;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class TileTest {

	@Test
	public void testConstructTile() {
		Point location = new Point(1,1);
		int number = 3;
		Tile tile = new Tile(location, number, TileType.wheat);
		assertEquals(location, tile.getLocation());
		assertEquals(number, tile.getNumber());
	}

}
