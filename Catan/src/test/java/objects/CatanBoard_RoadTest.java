package objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.GameStates;

class CatanBoard_RoadTest extends CatanBoardTest {
	
	@BeforeEach
	public void basicSetupRoad() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
	}

	@Test
	public void testBuyRoad_WithEnoughResources() {
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.wood, 1);
		
		assertTrue(cb.buyRoad());
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.wood));
	}
	
	@Test
	public void testBuyRoad_WithoutEnoughResources() {
		ArrayList<Integer> tilesToAddSettlementsTo = new ArrayList<>();
		HashSet<Integer> bannedtiles = new HashSet<>();
		for(Tile t : cb.getTiles()) {
			if(t.getType() == TileType.brick || t.getType() == TileType.wood) {
				bannedtiles.add(t.getNumber());
			}
		}
		for(int i = 1; i <= 12; i++) {
			if(!bannedtiles.contains(i)) {
				tilesToAddSettlementsTo.add(i);
			}
		}
		for(int tileNum : tilesToAddSettlementsTo) {
			cb.addSettlementToTiles(new ArrayList<>(Arrays.asList(tileNum)), new ArrayList<>(Arrays.asList(0)), GameStates.drop_settlement_setup);
		}
		for(int tileNum : tilesToAddSettlementsTo) {
			cb.distributeResources(tileNum);
		}
		
		assertTrue(pc.getCurrentPlayer().getResourceCount(TileType.brick) == 0);
		assertTrue(pc.getCurrentPlayer().getResourceCount(TileType.wood) == 0);
		
		int numOfBricksBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.brick);
		int numOfWoodBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.wood);
		
		assertTrue(!cb.buyRoad());
		assertEquals(numOfBricksBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(numOfWoodBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.wood));
	}
	
	@Test
	public void testBuyRoad_WithNoResources() {
		assertTrue(pc.getCurrentPlayer().getResourceCount(TileType.brick) == 0);
		assertTrue(pc.getCurrentPlayer().getResourceCount(TileType.wood) == 0);
		
		int numOfBricksBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.brick);
		int numOfWoodBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.wood);
		
		assertTrue(!cb.buyRoad());
		assertEquals(numOfBricksBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(numOfWoodBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.wood));
	}
	
	@Test
	public void testPlaceRoadNotEnoughResources() {
		int p1tile = 1; int p1corner = 1;
		int p1roadTile = 1; int p1road1 = 1; int p1road2 = 2;

		// Place Settlement
		addPlayerClickNums(p1tile, p1corner);
		registerPlayerClick();
		checkPlayerTileForSettlement(p1tile, p1corner, player1);
		
		// Try to Place Road
		addPlayerDragNums(p1roadTile, p1road1, p1road2);
		boolean result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertFalse(result);
	}

}
