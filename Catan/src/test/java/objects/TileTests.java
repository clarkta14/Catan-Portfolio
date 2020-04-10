package objects;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class TileTests {

	@Test
	public void testCheckValidRoadPlacement() {
		Tile tile = new Tile(null, 1, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		
		assertEquals(false, tile.checkValidRoadPlacement(5, 0));
	}

}
