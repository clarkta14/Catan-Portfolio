package objects;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashMap;

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
		assertEquals(1, player.getResourceCount(TileType.brick));
		player.addResource(TileType.brick, 1);
		assertEquals(2, player.getResourceCount(TileType.brick));
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
	public void testAddResourceDesert() {
		Player player = new Player(Color.orange);
		player.addResource(TileType.desert, 1);
		assertEquals(0, player.getResourceCount(TileType.desert));
	}

	@Test
	public void testRemoveOneFromOneResource() {
		Player player = new Player(Color.orange);
		assertEquals(1, addAndGetResourceForPlayer(player, TileType.brick, 1));

		player.removeResource(TileType.brick, 1);
		assertEquals(0, player.getResourceCount(TileType.brick));
	}

	@Test
	public void testRemoveOneFromTwoResources() {
		Player player = new Player(Color.orange);
		assertEquals(2, addAndGetResourceForPlayer(player, TileType.brick, 2));

		player.removeResource(TileType.brick, 1);
		assertEquals(1, player.getResourceCount(TileType.brick));
	}

	@Test
	public void testRemoveOneTwiceFromTwoResources() {
		Player player = new Player(Color.orange);
		assertEquals(2, addAndGetResourceForPlayer(player, TileType.brick, 2));

		player.removeResource(TileType.brick, 1);
		assertEquals(1, player.getResourceCount(TileType.brick));

		player.removeResource(TileType.brick, 1);
		assertEquals(0, player.getResourceCount(TileType.brick));
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

	@Test
	public void testCanBuyDevelopmentCard_WithoutEnoughResources_NotEnoughOre() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.wool, 1);
		plyr.addResource(TileType.wheat, 1);
		assertTrue(!plyr.canBuyDevelopmentCard());
	}

	@Test
	public void testCanBuyDevelopmentCard_WithoutEnoughResources_NotEnoughWool() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.ore, 1);
		plyr.addResource(TileType.wheat, 1);
		assertTrue(!plyr.canBuyDevelopmentCard());
	}

	@Test
	public void testCanBuyDevelopmentCard_WithoutEnoughResources_NotEnoughWheat() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.ore, 1);
		plyr.addResource(TileType.wool, 1);
		assertTrue(!plyr.canBuyDevelopmentCard());
	}

	@Test
	public void testAddDevelopmentCard_KnightDC() {
		Player plyr = new Player(Color.orange);
		plyr.addDevelopmentCard(DevelopmentCardType.knight);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.knight).size() == 1);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.progress).size() == 0);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.victory_point).size() == 0);
	}

	@Test
	public void testAddDevelopmentCard_ProgressDC() {
		Player plyr = new Player(Color.orange);
		plyr.addDevelopmentCard(DevelopmentCardType.progress);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.knight).size() == 0);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.progress).size() == 1);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.victory_point).size() == 0);
	}

	@Test
	public void testAddDevelopmentCard_VictoryPointDC() {
		Player plyr = new Player(Color.orange);
		plyr.addDevelopmentCard(DevelopmentCardType.victory_point);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.knight).size() == 0);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.progress).size() == 0);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.victory_point).size() == 1);
	}

	@Test
	public void testRemoveDevelopmentCard_KnightDC() {
		Player plyr = new Player(Color.orange);
		plyr.addDevelopmentCard(DevelopmentCardType.knight);
		plyr.addDevelopmentCard(DevelopmentCardType.knight);
		plyr.removeDevelopmentCard(DevelopmentCardType.knight);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.knight).size() == 1);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.progress).size() == 0);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.victory_point).size() == 0);
	}

	@Test
	public void testRemoveDevelopmentCard_ProgressDC() {
		Player plyr = new Player(Color.orange);
		plyr.addDevelopmentCard(DevelopmentCardType.progress);
		plyr.addDevelopmentCard(DevelopmentCardType.progress);
		plyr.removeDevelopmentCard(DevelopmentCardType.progress);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.knight).size() == 0);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.progress).size() == 1);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.victory_point).size() == 0);
	}

	@Test
	public void testRemoveDevelopmentCard_VictoryPointDC() {
		Player plyr = new Player(Color.orange);
		plyr.addDevelopmentCard(DevelopmentCardType.victory_point);
		plyr.addDevelopmentCard(DevelopmentCardType.victory_point);
		plyr.removeDevelopmentCard(DevelopmentCardType.victory_point);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.knight).size() == 0);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.progress).size() == 0);
		assertTrue(plyr.developmentCards.get(DevelopmentCardType.victory_point).size() == 1);
	}

	@Test
	public void testCanAffordTrade_WithEnoughResources() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.brick, 1);
		plyr.addResource(TileType.wood, 1);
		plyr.addResource(TileType.wool, 1);
		plyr.addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> payment = new HashMap<>();
		payment.put(TileType.brick, 1);
		payment.put(TileType.wood, 1);
		payment.put(TileType.wool, 1);
		payment.put(TileType.wheat, 1);
		assertTrue(plyr.canAffordTrade(payment));
	}

	@Test
	public void testCanAffordTrade_WithEnoughResources_ResourcesReapted() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.brick, 2);
		plyr.addResource(TileType.wood, 1);
		plyr.addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> payment = new HashMap<>();
		payment.put(TileType.brick, 2);
		payment.put(TileType.wood, 1);
		payment.put(TileType.wheat, 1);
		assertTrue(plyr.canAffordTrade(payment));
	}

	@Test
	public void testCanAffordTrade_WithoutEnoughResources() {
		Player plyr = new Player(Color.orange);
		plyr.addResource(TileType.brick, 2);
		plyr.addResource(TileType.wood, 1);
		plyr.addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> payment = new HashMap<>();
		payment.put(TileType.wool, 2);
		payment.put(TileType.wood, 1);
		payment.put(TileType.wheat, 1);
		assertTrue(!plyr.canAffordTrade(payment));
	}

	@Test
	public void testGetVictoryPoints() {
		Player plyr = new Player(Color.orange);
		int vp = plyr.getNumberOfVictoryPoints();
		assertEquals(0, vp);
	}

	@Test
	public void testAlterVPSettlement() {
		Player plyr = new Player(Color.orange);
		plyr.alterVictoryPoints(VictoryPoints.settlement);
		int vp = plyr.getNumberOfVictoryPoints();
		assertEquals(1, vp);
	}

	@Test
	public void testAlterVPCity() {
		Player plyr = new Player(Color.orange);
		plyr.alterVictoryPoints(VictoryPoints.city);
		int vp = plyr.getNumberOfVictoryPoints();
		assertEquals(1, vp);
	}

	@Test
	public void testAlterVPDevoCard() {
		Player plyr = new Player(Color.orange);
		plyr.alterVictoryPoints(VictoryPoints.devolopment_card);
		int vp = plyr.getNumberOfVictoryPoints();
		assertEquals(1, vp);
	}

	@Test
	public void testAlterVPMultiple() {
		Player plyr = new Player(Color.orange);
		plyr.alterVictoryPoints(VictoryPoints.devolopment_card);
		plyr.alterVictoryPoints(VictoryPoints.city);
		plyr.alterVictoryPoints(VictoryPoints.settlement);
		int vp = plyr.getNumberOfVictoryPoints();
		assertEquals(3, vp);
	}

	@Test
	public void testIsVictor10VP() {
		Player plyr = new Player(Color.orange);
		for (int i = 0; i < 10; i++) {
			plyr.alterVictoryPoints(VictoryPoints.devolopment_card);
		}
		assertTrue(plyr.isVictor());
	}

	@Test
	public void testIsVictor0VP() {
		Player plyr = new Player(Color.orange);
		assertFalse(plyr.isVictor());
	}

	@Test
	public void testIsVictor11VP() {
		Player plyr = new Player(Color.orange);
		for (int i = 0; i < 11; i++) {
			plyr.alterVictoryPoints(VictoryPoints.devolopment_card);
		}
		assertTrue(plyr.isVictor());
	}

	@Test
	public void testCanBuySettlementMaxNumber() {
		Player player = new Player(Color.orange);
		player.numSettlements = 5;
		addResourcesForSettlement(player);
		
		assertFalse(player.canBuySettlement());
	}

	@Test
	public void testCanBuySettlementMinNumber() {
		Player player = new Player(Color.orange);
		addResourcesForSettlement(player);
		
		assertTrue(player.canBuySettlement());
	}
	
	@Test
	public void testCanBuySettlementMaxPlus1() {
		Player player = new Player(Color.orange);
		player.numSettlements = 6;
		addResourcesForSettlement(player);
		
		assertFalse(player.canBuySettlement());
	}
	
	@Test
	public void testPlayerInitSettlementsNumber() {
		Player player = new Player(Color.orange);
		assertEquals(player.numSettlements, 2);
	}

	private int addAndGetResourceForPlayer(Player player, TileType type, int numberOfResource) {
		player.addResource(type, numberOfResource);
		return player.getResourceCount(type);
	}
	
	private void addResourcesForSettlement(Player player) {
		player.addResource(TileType.brick, 1);
		player.addResource(TileType.wool, 1);
		player.addResource(TileType.wood, 1);
		player.addResource(TileType.wheat, 1);
	}
}
