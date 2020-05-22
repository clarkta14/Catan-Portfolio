package objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import gui.GameStates;
import org.junit.Test;

public class CatanBoard_CitiesTest extends CatanBoardTest {
	
	Player currentPlayer;
	
	@Before
	public void basicSetupForAddCityTests() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		clearClicks();
		this.currentPlayer = pc.getCurrentPlayer();
		addResourcesForCities(currentPlayer, 1);
		
		tileNums.add(0);
		tileNums.add(1);
		tileNums.add(4);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
	}

	@Test
	public void testAddCityToTilesValid() {
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertTrue(result);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		result = cb.addCityToTiles(tileNums, cornerNums);
		assertTrue(result);
		assertEquals(2, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		assertEquals(0, currentPlayer.getResourceCount(TileType.wheat));
		assertEquals(0, currentPlayer.getResourceCount(TileType.ore));
		
		ArrayList<Tile> tiles = cb.getTiles();
		for (int i = 0; i < 3; i++) {
			HashMap<Integer, Settlement> settlementMap = tiles.get(tileNums.get(i)).getSettlements();
			Settlement city = settlementMap.get(cornerNums.get(i));
			if (city == null) {
				fail("City should be present");
			}
			assertTrue(city.isCity());
		}
	}
	
	@Test
	public void testAddCityToTilesNotOnSettlement() {
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertTrue(result);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		
		ArrayList<Settlement> settlements = getSettlementsFromClickedTiles();
		
		clearClicks();
		
		tileNums.add(0);
		tileNums.add(1);
		tileNums.add(4);
		
		cornerNums.add(4);
		cornerNums.add(1);
		cornerNums.add(5);
		result = cb.addCityToTiles(tileNums, cornerNums);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		assertFalse(result);
		assertEquals(3, currentPlayer.getResourceCount(TileType.ore));
		assertEquals(2, currentPlayer.getResourceCount(TileType.wheat));
		
		for (Settlement s : settlements) {
			assertFalse(s.isCity());
		}
	}

	@Test
	public void testAddCityToTilesOnOtherPlayerSettlement() {
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		pc.nextPlayer();
		pc.nextPlayer();
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertTrue(result);
		ArrayList<Settlement> settlements = getSettlementsFromClickedTiles();
		
		pc.nextPlayer();
		pc.nextPlayer();
		pc.nextPlayer();
		assertEquals(pc.getCurrentPlayer(), currentPlayer);

		result = cb.addCityToTiles(tileNums, cornerNums);
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		assertFalse(result);
		assertEquals(3, currentPlayer.getResourceCount(TileType.ore));
		assertEquals(2, currentPlayer.getResourceCount(TileType.wheat));
		for (Settlement s : settlements) {
			assertFalse(s.isCity());
		}
	}
	
	@Test
	public void testAddCityToTilesNotEnoughResources() {
		currentPlayer.removeResource(TileType.wheat, 1);
		
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertTrue(result);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		
		ArrayList<Settlement> settlements = getSettlementsFromClickedTiles();
		
		result = cb.addCityToTiles(tileNums, cornerNums);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		assertFalse(result);
		assertEquals(3, currentPlayer.getResourceCount(TileType.ore));
		assertEquals(1, currentPlayer.getResourceCount(TileType.wheat));
		
		for (Settlement s : settlements) {
			assertFalse(s.isCity());
		}
	}
	
	@Test
	public void testAddCityToTilesAlreadyCity() {
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertTrue(result);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		
		ArrayList<Settlement> settlements = getSettlementsFromClickedTiles();
		
		result = cb.addCityToTiles(tileNums, cornerNums);
		assertEquals(2, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		assertTrue(result);
		addResourcesForCities(currentPlayer, 1);
		result = cb.addCityToTiles(tileNums, cornerNums);
		assertEquals(2, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		assertFalse(result);
		assertEquals(1, currentPlayer.numCities);
		
		for (Settlement s : settlements) {
			assertTrue(s.isCity());
		}
	}

}
