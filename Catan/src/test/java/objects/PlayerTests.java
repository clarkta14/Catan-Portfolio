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
	public void testRemoveOneFromOneResource() {
		Player player = new Player(Color.orange);
		assertEquals(1, addAndGetResourceForPlayer(player, TileType.brick, 1));

		player.removeResource(TileType.brick, 1);
		assertEquals(0, player.getResource(TileType.brick));
	}

	@Test
	public void testRemoveOneFromTwoResources() {
		Player player = new Player(Color.orange);
		assertEquals(2, addAndGetResourceForPlayer(player, TileType.brick, 2));

		player.removeResource(TileType.brick, 1);
		assertEquals(1, player.getResource(TileType.brick));
	}
	
	@Test
	public void testRemoveOneTwiceFromTwoResources() {
		Player player = new Player(Color.orange);
		assertEquals(2, addAndGetResourceForPlayer(player, TileType.brick, 2));

		player.removeResource(TileType.brick, 1);
		assertEquals(1, player.getResource(TileType.brick));
		
		player.removeResource(TileType.brick, 1);
		assertEquals(0, player.getResource(TileType.brick));
	}

	@Test
	public void testRemoveOneFromZeroResources() {
		try {
			Player player = new Player(Color.orange);
			player.removeResource(TileType.brick, 1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// pass
		}
	}
	
	@Test
	public void testRemoveTwoFromOneResource() {
		try {
			Player player = new Player(Color.orange);
			assertEquals(1, addAndGetResourceForPlayer(player, TileType.brick, 1));
			player.removeResource(TileType.brick, 2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// pass
		}
	}
	
	@Test
	public void testRemoveNegativeFromResource() {
		try {
			Player player = new Player(Color.orange);
			player.removeResource(TileType.brick, -1);
			fail();
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
	
	@Test
	public void testCanBuyRoad_WithEnoughResources() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.brick, 1);
		plyr.addResource(TileType.wood, 1);
		assertTrue(plyr.canBuyRoad());
	}
	
	@Test
	public void testCanBuyRoad_WithoutEnoughResources_notEnoughWood() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.brick, 1);
		assertTrue(!plyr.canBuyRoad());
	}
	
	@Test
	public void testCanBuyRoad_WithoutEnoughResources_notEnoughBrick() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.wood, 1);
		assertTrue(!plyr.canBuyRoad());
	}
	
	@Test
	public void testCanBuySettlement_WithEnoughResources() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.brick, 1);
		plyr.addResource(TileType.wood, 1);
		plyr.addResource(TileType.wool, 1);
		plyr.addResource(TileType.wheat, 1);
		assertTrue(plyr.canBuySettlement());
	}
	
	@Test
	public void testCanBuySettlement_WithoutEnoughResources_NotEnoughBrick() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.wood, 1);
		plyr.addResource(TileType.wool, 1);
		plyr.addResource(TileType.wheat, 1);
		assertTrue(!plyr.canBuySettlement());
	}
	
	@Test
	public void testCanBuySettlement_WithoutEnoughResources_NotEnoughWood() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.brick, 1);
		plyr.addResource(TileType.wool, 1);
		plyr.addResource(TileType.wheat, 1);
		assertTrue(!plyr.canBuySettlement());
	}
	
	@Test
	public void testCanBuySettlement_WithoutEnoughResources_NotEnoughWool() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.brick, 1);
		plyr.addResource(TileType.wood, 1);
		plyr.addResource(TileType.wheat, 1);
		assertTrue(!plyr.canBuySettlement());
	}
	
	@Test
	public void testCanBuySettlement_WithoutEnoughResources_NotEnoughWheat() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.brick, 1);
		plyr.addResource(TileType.wood, 1);
		plyr.addResource(TileType.wool, 1);
		assertTrue(!plyr.canBuySettlement());
	}
	
	@Test
	public void testCanBuyDevelopmentCard_WithEnoughResources() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.ore, 1);
		plyr.addResource(TileType.wool, 1);
		plyr.addResource(TileType.wheat, 1);
		assertTrue(plyr.canBuyDevelopmentCard());
	}

	private int addAndGetResourceForPlayer(Player player, TileType type, int numberOfResource) {
		player.addResource(type, numberOfResource);
		return player.getResource(type);
	}
}
