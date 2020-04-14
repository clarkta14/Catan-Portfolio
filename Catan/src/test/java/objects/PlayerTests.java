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
		assertEquals(1, addAndGetResourceForPlayer(player, TileType.brick, 1));
	}

	@Test
	public void testAddOneWoolResource() {
		Player player = new Player(Color.orange);
		assertEquals(1, addAndGetResourceForPlayer(player, TileType.wool, 1));
	}

	@Test
	public void testAddTwoBrickResource() {
		Player player = new Player(Color.orange);
		assertEquals(2, addAndGetResourceForPlayer(player, TileType.brick, 2));
	}

	@Test
	public void testAddOneResourceTwice() {
		Player player = new Player(Color.orange);
		player.addResource(TileType.brick, 1);
		assertEquals(1, player.getResource(TileType.brick));
		player.addResource(TileType.brick, 1);
		assertEquals(2, player.getResource(TileType.brick));
	}

	@Test
	public void testAddNegativeResources() {
		try {
			Player player = new Player(Color.orange);
			player.addResource(TileType.brick, -1);
			fail();
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	@Test
	public void testAddMaxResourcesPlusOne() {
		try {
			Player player = new Player(Color.orange);
			player.addResource(TileType.brick, Integer.MAX_VALUE);
			player.addResource(TileType.brick, 1);
			fail();
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	@Test
	public void testAddMaxPlusOneResources() {
		try {
			Player player = new Player(Color.orange);
			player.addResource(TileType.brick, Integer.MAX_VALUE + 1);
			fail();
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	@Test
	public void testRemoveOneFromOneBrickResource() {
		Player player = new Player(Color.orange);
		assertEquals(1, addAndGetResourceForPlayer(player, TileType.brick, 1));

		player.removeResource(TileType.brick, 1);
		assertEquals(0, player.getResource(TileType.brick));
	}

	@Test
	public void testRemoveOneFromTwoBrickResources() {
		Player player = new Player(Color.orange);
		assertEquals(2, addAndGetResourceForPlayer(player, TileType.brick, 2));

		player.removeResource(TileType.brick, 1);
		assertEquals(1, player.getResource(TileType.brick));
	}

	@Test
	public void testRemoveOneFromZeroBrickResources() {
		try {
			Player player = new Player(Color.orange);
			player.removeResource(TileType.brick, 1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// pass
		}
	}
	
	@Test
	public void testRemoveTwoFromOneBrickResources() {
		try {
			Player player = new Player(Color.orange);
			assertEquals(1, addAndGetResourceForPlayer(player, TileType.brick, 1));
			player.removeResource(TileType.brick, 2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// pass
		}
	}

	private int addAndGetResourceForPlayer(Player player, TileType type, int numberOfResource) {
		player.addResource(type, numberOfResource);
		return player.getResource(type);
	}
}
