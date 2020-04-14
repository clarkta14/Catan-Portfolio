package objects;

import static org.junit.Assert.*;

import java.awt.Color;
import org.junit.Test;

public class PlayerTests {

	@Test
	public void constructPlayer() {
		Player player = new Player(Color.orange);
		assertEquals(Color.orange, player.getColor());
	}
	
	@Test
	public void construct2PlayerDifferentColors() {
		Player player1 = new Player(Color.orange);
		assertEquals(Color.orange, player1.getColor());
		
		Player player2 = new Player(Color.blue);
		assertEquals(Color.blue, player2.getColor());
	}
	
	@Test
	public void testAddOneBrickResource() {
		Player player = new Player(Color.orange);
		player.addResource(TileType.brick, 1);
		assertEquals(1, player.getResource(TileType.brick));
	}
	
	@Test
	public void testAddOneWoolResource() {
		Player player = new Player(Color.orange);
		player.addResource(TileType.wool, 1);
		assertEquals(1, player.getResource(TileType.wool));
	}
}
