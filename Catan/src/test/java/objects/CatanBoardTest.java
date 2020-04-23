package objects;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import org.junit.Test;

import gui.GameStates;

public class CatanBoardTest {
	
	// Player numbers
	int player1 = 0;
	int player2 = 1;
	int player3 = 2;
	int player4 = 3;
	
	PlayersController pc;
	CatanBoard cb;
	
	// Stores click locations for players in the tests
	ArrayList<Integer> tileNums = new ArrayList<Integer>();
	ArrayList<Integer> cornerNums = new ArrayList<Integer>();
	
	HashMap<Integer, ArrayList<Integer>> tileToCorners = new HashMap<Integer, ArrayList<Integer>>();
	HashMap<Integer, Integer> tileToRoadOrientation = new HashMap<Integer, Integer>();
	

	@Test
	public void testConstruct4Player() {
		pc = new PlayersController(4);
		cb = new CatanBoard(pc);
		assertEquals(cb.getTiles().size(), 19);
	}
	
	@Test
	public void testConstruct3Player() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		assertEquals(cb.getTiles().size(), 19);
	}
	
	@Test
	public void testLocationClicked1() {
		pc = new PlayersController(4);
		cb = new CatanBoard(pc);
		int tileNum = 1; int cornerNum= 0;
		addPlayerClickNums(tileNum, cornerNum);
		
		registerPlayerClick();
		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(tileNum);
		HashMap<Integer, Settlement> settlements = tile.getSettlements();
		
		Settlement s = settlements.get(cornerNum);
		assertEquals(s.getOwner(), pc.getCurrentPlayer());
	}
	
	@Test
	public void testBoardInitSetupSettlementRoad() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		int p1tile = 1; int p1corner = 1;
		int p1roadTile = 1; int p1road1 = 1; int p1road2 = 2;

		// Place Settlement
		addPlayerClickNums(p1tile, p1corner);
		registerPlayerClick();
		checkPlayerTileForSettlement(p1tile, p1corner, player1);
		
		// Place Road
		addPlayerDragNums(p1roadTile, p1road1, p1road2);
		registerPlayerDragSetup();
		Road r = checkPlayerTileForRoad(player1, p1roadTile, p1road1, p1road2);
		assertEquals(r.getAngle(), 2);
	}
	
	@Test
	public void testBoardInitSetupSettlementRoadBadPlacementNoConnections() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		int p1tile = 1; int p1corner = 1;
		int p1roadTile = 10; int p1road1 = 1; int p1road2 = 2;

		// Place Settlement
		addPlayerClickNums(p1tile, p1corner);
		registerPlayerClick();
		checkPlayerTileForSettlement(p1tile, p1corner, player1);
		
		// Try to Place Road
		addPlayerDragNums(p1roadTile, p1road1, p1road2);
		registerPlayerDragSetup();

		// Check no roads
		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(p1roadTile);
		HashMap<ArrayList<Integer>, Road> roads = tile.getRoads();
		assertEquals(0, roads.size());
	}

	@Test
	public void testBoardInitSetup3Player() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		int p1tile = 1; int p1corner = 1; int p1road = 2;
		int p2tile = 5; int p2corner = 5; int p2road = 0;
		int p3tile = 15; int p3corner = 3; int p3road = 4;
		
		int p1tile2 = 3; int p1corner2 = 1; int p1road2 = 2;
		int p2tile2 = 10; int p2corner2 = 5; int p2road2 = 4;
		int p3tile2 = 18; int p3corner2 = 3; int p3road2 = 2;
		
		// Everyone places first settlements and roads
		makePlayerTurnInit(p1tile, p1corner, p1road, player1, 2);
		
		makePlayerTurnInit(p2tile, p2corner, p2road, player2, 0);

		makePlayerTurnInit(p3tile, p3corner, p3road, player3, 1);
		
		// Second Round of settlements
		makePlayerTurnInit(p3tile2, p3corner2, p3road2, player3, 0);

		makePlayerTurnInit(p2tile2, p2corner2, p2road2, player2, 2);
		
		makePlayerTurnInit(p1tile2, p1corner2, p1road2, player1, 2);
	}
	
	@Test
	public void testBoardInitSetup4Player() {
		pc = new PlayersController(4);
		cb = new CatanBoard(pc);
		int p1tile = 1; int p1corner = 1; int p1road = 2;
		int p2tile = 5; int p2corner = 5; int p2road = 0;
		int p3tile = 15; int p3corner = 3; int p3road = 4;
		int p4tile = 13; int p4corner = 0; int p4road = 1;
		
		int p1tile2 = 3; int p1corner2 = 1; int p1road2 = 2;
		int p2tile2 = 10; int p2corner2 = 5; int p2road2 = 4;
		int p3tile2 = 18; int p3corner2 = 3; int p3road2 = 2;
		int p4tile2 = 11; int p4corner2 = 2; int p4road2 = 3;
		
		// Everyone places first settlements and roads
		makePlayerTurnInit(p1tile, p1corner, p1road, player1, 2);
		
		makePlayerTurnInit(p2tile, p2corner, p2road, player2, 0);

		makePlayerTurnInit(p3tile, p3corner, p3road, player3, 1);
		
		makePlayerTurnInit(p4tile, p4corner, p4road, player4, 1);
		
		// Second Round of settlements
		makePlayerTurnInit(p4tile2, p4corner2, p4road2, player4, 0);
		
		makePlayerTurnInit(p3tile2, p3corner2, p3road2, player3, 0);

		makePlayerTurnInit(p2tile2, p2corner2, p2road2, player2, 2);
		
		makePlayerTurnInit(p1tile2, p1corner2, p1road2, player1, 2);
	}
	
	@Test
	public void testAddSettlementToTiles() {
		basicSetupForAddSettlementTests();
		
		tileNums.add(0);
		tileNums.add(1);
		tileNums.add(4);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
		
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertTrue(result);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
	}
	
	@Test
	public void testAddSettlementToTilesTwoSettlementsSamePlace() {
		basicSetupForAddSettlementTests();
		
		tileNums.add(0);
		tileNums.add(1);
		tileNums.add(4);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
		
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertFalse(result);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
	}
	
	@Test
	public void testAddSettlementToTilesTwoSettlementsOneSpaceAway() {
		basicSetupForAddSettlementTests();
		
		tileNums.add(0);
		tileNums.add(1);
		tileNums.add(4);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
		
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());

		clearClicks();
		
		tileNums.add(1);
		tileNums.add(4);
		tileNums.add(5);
		
		cornerNums.add(2);
		cornerNums.add(4);
		cornerNums.add(0);
		
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertFalse(result);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
	}
	
	@Test
	public void testAddSettlementToTilesTwoSettlementsTwoSpacesAway() {
		basicSetupForAddSettlementTests();
		
		tileNums.add(0);
		tileNums.add(1);
		tileNums.add(4);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
		
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		
		clearClicks();
		
		tileNums.add(1);
		tileNums.add(2);
		tileNums.add(5);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
		
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertTrue(result);
		assertEquals(2, pc.getCurrentPlayer().getNumberOfVictoryPoints());
	}
	
	@Test
	public void testAddSettlementToTilesTwoSettlements11SpacesAway() {
		basicSetupForAddSettlementTests();
		
		tileNums.add(3);
		cornerNums.add(0);
		
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		
		clearClicks();
		
		tileNums.add(15);
		cornerNums.add(3);
		
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertTrue(result);
		assertEquals(2, pc.getCurrentPlayer().getNumberOfVictoryPoints());
	}
	
	@Test
	public void testAddSettlementToTilesNotSetupBadPlacement() {
		basicSetupForAddSettlementTests();
		
		tileNums.add(0);
		tileNums.add(1);
		tileNums.add(4);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
		
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement);
		assertFalse(result);
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
	}
	
	@Test
	public void testEndTurnAndRoll() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		//TODO: figure out a better way to test.
		for (int i = 0; i < 10000; i++) {
			int result = cb.endTurnAndRoll();
			if(result < 2 || result > 12) {
				fail("Rolled out of bounds: " + result);
			}
		}
	}
	
	@Test
	public void testAddSettlementToTilesNotSetupValidPlacement() {
		basicSetupForAddSettlementTests();
		Road newRoad = new Road(pc.getCurrentPlayer());
		
		cb.getTiles().get(0).addRoad(2, 3, newRoad);
		
		tileNums.add(0);
		tileNums.add(1);
		tileNums.add(4);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
		
		Player currentPlayer = pc.getCurrentPlayer();
		
		currentPlayer.addResource(TileType.brick, 1);
		currentPlayer.addResource(TileType.wood, 1);
		currentPlayer.addResource(TileType.wool, 1);
		currentPlayer.addResource(TileType.wheat, 1);
		
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement);
		assertTrue(result);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		
		
		assertEquals(0, currentPlayer.getResourceCount(TileType.brick));
		assertEquals(0, currentPlayer.getResourceCount(TileType.wood));
		assertEquals(0, currentPlayer.getResourceCount(TileType.wool));
		assertEquals(0, currentPlayer.getResourceCount(TileType.wheat));
	}
	
	@Test
	public void testAddSettlementToTilesNotSetupValidPlacementNoResources() {
		basicSetupForAddSettlementTests();
		Road newRoad = new Road(pc.getCurrentPlayer());
		
		cb.getTiles().get(0).addRoad(2, 3, newRoad);
		
		tileNums.add(0);
		tileNums.add(1);
		tileNums.add(4);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
		
		Player currentPlayer = pc.getCurrentPlayer();
		
		currentPlayer.addResource(TileType.brick, 1);
		currentPlayer.addResource(TileType.wood, 1);
		currentPlayer.addResource(TileType.wool, 1);
		
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement);
		assertFalse(result);
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		
		assertEquals(1,  currentPlayer.getResourceCount(TileType.brick));
		assertEquals(1,  currentPlayer.getResourceCount(TileType.wood));
		assertEquals(1,  currentPlayer.getResourceCount(TileType.wool));
	}
	
	@Test
	public void testAddSettlementToTilesNotSetupWrongOwner() {
		basicSetupForAddSettlementTests();
		Road newRoad = new Road(pc.getCurrentPlayer());
		
		cb.getTiles().get(0).addRoad(2, 3, newRoad);
		
		tileNums.add(0);
		tileNums.add(1);
		tileNums.add(4);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
		
		pc.nextPlayer();
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement);
		assertFalse(result);
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
	}
	
	@Test
	public void testDistributeResources() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		//Adding settlements for players at specified locations
		cb.addSettlementToTiles(new ArrayList<>(Arrays.asList(5, 4, 1)), new ArrayList<>(Arrays.asList(0, 4, 2)), GameStates.drop_settlement_setup);
		cb.addSettlementToTiles(new ArrayList<>(Arrays.asList(4, 8, 3)), new ArrayList<>(Arrays.asList(1, 5, 3)), GameStates.drop_settlement_setup);
		cb.addSettlementToTiles(new ArrayList<>(Arrays.asList(18, 17, 14)), new ArrayList<>(Arrays.asList(0, 4, 2)), GameStates.drop_settlement_setup);
	
		cb.distributeResources(cb.getTiles().get(5).getNumber());
		if (cb.getTiles().get(5).getType() != TileType.desert)
			assertTrue(pc.getCurrentPlayer().getResourceCount(cb.getTiles().get(5).getType()) > 0);
		cb.distributeResources(cb.getTiles().get(4).getNumber());
		if (cb.getTiles().get(4).getType() != TileType.desert)
			assertTrue(pc.getCurrentPlayer().getResourceCount(cb.getTiles().get(4).getType()) > 0);
	}
	
	@Test
	public void testBuyRoad_WithEnoughResources() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.wood, 1);
		
		assertTrue(cb.buyRoad());
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.wood));
	}
	
	@Test
	public void testBuyRoad_WithoutEnoughResources() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
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
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		assertTrue(pc.getCurrentPlayer().getResourceCount(TileType.brick) == 0);
		assertTrue(pc.getCurrentPlayer().getResourceCount(TileType.wood) == 0);
		
		int numOfBricksBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.brick);
		int numOfWoodBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.wood);
		
		assertTrue(!cb.buyRoad());
		assertEquals(numOfBricksBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(numOfWoodBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.wood));
	}
	
	@Test
	public void testBuySettlement_WithEnoughResources() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.wood, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		
		assertTrue(cb.buySettlement());
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.wood));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.wheat));
	}
	
	@Test
	public void testBuySettlement_WithoutEnoughResources() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		ArrayList<Integer> tilesToAddSettlementsTo = new ArrayList<>();
		HashSet<Integer> bannedtiles = new HashSet<>();
		for(Tile t : cb.getTiles()) {
			if(t.getType() == TileType.brick || t.getType() == TileType.wood || t.getType() == TileType.wool || t.getType() == TileType.wheat) {
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
		assertTrue(pc.getCurrentPlayer().getResourceCount(TileType.wool) == 0);
		assertTrue(pc.getCurrentPlayer().getResourceCount(TileType.wheat) == 0);
		
		int numOfBricksBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.brick);
		int numOfWoodBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.wood);
		int numOfWoolBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.wool);
		int numOfWheatBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.wheat);
		
		assertTrue(!cb.buySettlement());
		assertEquals(numOfBricksBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(numOfWoodBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.wood));
		assertEquals(numOfWoolBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(numOfWheatBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.wheat));
	}
	
	@Test
	public void testBuySettlement_WithNoResources() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		assertTrue(pc.getCurrentPlayer().getResourceCount(TileType.brick) == 0);
		assertTrue(pc.getCurrentPlayer().getResourceCount(TileType.wood) == 0);
		assertTrue(pc.getCurrentPlayer().getResourceCount(TileType.wool) == 0);
		assertTrue(pc.getCurrentPlayer().getResourceCount(TileType.wheat) == 0);
		
		int numOfBricksBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.brick);
		int numOfWoodBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.wood);
		int numOfWoolBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.wool);
		int numOfWheatBeforeBuy = pc.getCurrentPlayer().getResourceCount(TileType.wheat);
		
		assertTrue(!cb.buyRoad());
		assertEquals(numOfBricksBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(numOfWoodBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.wood));
		assertEquals(numOfWoolBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(numOfWheatBeforeBuy,  pc.getCurrentPlayer().getResourceCount(TileType.wheat));
	}
		
	@Test
	public void testDistributeResourcesOnLastSettlementPlacedSetup() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		ArrayList<Integer> selectedTiles = new ArrayList<Integer>();
		ArrayList<Integer> corners = new ArrayList<Integer>();
		
		selectedTiles.add(1); corners.add(4);
		selectedTiles.add(2); corners.add(0);
		
		cb.addSettlementToTiles(selectedTiles, corners, GameStates.drop_settlement_setup_final);
		
		Player currentPlayer = pc.getCurrentPlayer();
		TileType TileType1 = cb.getTiles().get(1).getType();
		TileType TileType2 = cb.getTiles().get(2).getType();
		if (TileType1 == TileType2) {
			if (TileType1 == TileType.desert) {
				assertEquals(0,  currentPlayer.getResourceCount(TileType1));
			} else {
				assertEquals(2,  currentPlayer.getResourceCount(TileType1));
			}
		} else {
			if (TileType1 == TileType.desert) {
				assertEquals(0,  currentPlayer.getResourceCount(TileType1));
			} else {
				assertEquals(1,  currentPlayer.getResourceCount(TileType1));
			}
			if (TileType2 == TileType.desert) {
				assertEquals(0,  currentPlayer.getResourceCount(TileType2));
			} else {
				assertEquals(1,  currentPlayer.getResourceCount(TileType2));
			}
		}
		
	}
	
	@Test
	public void testDistributeResourcesOnLastSettlementPlacedSetup1Tile() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		ArrayList<Integer> selectedTiles = new ArrayList<Integer>();
		ArrayList<Integer> corners = new ArrayList<Integer>();
		
		selectedTiles.add(2); corners.add(4);
		
		cb.addSettlementToTiles(selectedTiles, corners, GameStates.drop_settlement_setup_final);
		
		Player currentPlayer = pc.getCurrentPlayer();
		TileType TileType2 = cb.getTiles().get(2).getType();
		if (TileType2 == TileType.desert) {
			assertEquals(0,  currentPlayer.getResourceCount(TileType2));
		}
		assertEquals(1,  currentPlayer.getResourceCount(TileType2));
		
	}
	
	@Test
	public void testBuyDevelopmentCard_WithEnoughResources() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		pc.getCurrentPlayer().addResource(TileType.ore, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		
		assertTrue(cb.buyDevelopmentCard());
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		int totalDevCards = 0;
		for(Stack<DevelopmentCard> devCardStack : pc.getCurrentPlayer().developmentCards.values()) {
			totalDevCards += devCardStack.size();
		}
		assertTrue(totalDevCards == 1);
	}
	
	@Test
	public void testBuyDevelopmentCard_WithoutEnoughResources_NotEnoughOre() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		
		assertTrue(!cb.buyDevelopmentCard());
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		int totalDevCards = 0;
		for(Stack<DevelopmentCard> devCardStack : pc.getCurrentPlayer().developmentCards.values()) {
			totalDevCards += devCardStack.size();
		}
		assertTrue(totalDevCards == 0);
	}
	
	@Test
	public void testBuyDevelopmentCard_WithoutEnoughResources_NotEnoughWool() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		pc.getCurrentPlayer().addResource(TileType.ore, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		
		assertTrue(!cb.buyDevelopmentCard());
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		int totalDevCards = 0;
		for(Stack<DevelopmentCard> devCardStack : pc.getCurrentPlayer().developmentCards.values()) {
			totalDevCards += devCardStack.size();
		}
		assertTrue(totalDevCards == 0);
	}
	
	@Test
	public void testBuyDevelopmentCard_WithoutEnoughResources_NotEnoughWheat() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		pc.getCurrentPlayer().addResource(TileType.ore, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		
		assertTrue(!cb.buyDevelopmentCard());
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		int totalDevCards = 0;
		for(Stack<DevelopmentCard> devCardStack : pc.getCurrentPlayer().developmentCards.values()) {
			totalDevCards += devCardStack.size();
		}
		assertTrue(totalDevCards == 0);
	}
	
	@Test
	public void testTradeWithBank_4DifferentResourcesAsPayment() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.ore, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> payment = new HashMap<>();
		payment.put(TileType.brick, 1);
		payment.put(TileType.ore, 1);
		payment.put(TileType.wool, 1);
		payment.put(TileType.wheat, 1);
		assertTrue(cb.tradeWithBank(payment, TileType.wood));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.wood));
	}
	
	@Test
	public void testTradeWithBank_3DifferentResourcesAsPayment() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		pc.getCurrentPlayer().addResource(TileType.brick, 2);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> payment = new HashMap<>();
		payment.put(TileType.brick, 2);
		payment.put(TileType.wool, 1);
		payment.put(TileType.wheat, 1);
		assertTrue(cb.tradeWithBank(payment, TileType.ore));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(0,  pc.getCurrentPlayer().getResourceCount(TileType.wheat));
	}
	
	@Test
	public void testTradeWithBank_3DifferentResourcesAsPayment_ForSameType() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		pc.getCurrentPlayer().addResource(TileType.brick, 2);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> payment = new HashMap<>();
		payment.put(TileType.brick, 2);
		payment.put(TileType.wool, 1);
		payment.put(TileType.wheat, 1);
		assertTrue(!cb.tradeWithBank(payment, TileType.brick));
		assertEquals(2,  pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.wheat));
	}
	
	@Test
	public void testTradeWithBank_NotEnoughResources() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> payment = new HashMap<>();
		payment.put(TileType.brick, 2);
		payment.put(TileType.wool, 1);
		payment.put(TileType.wheat, 1);
		assertTrue(!cb.tradeWithBank(payment, TileType.ore));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.wheat));
	}
	
	@Test
	public void testTradeWithBank_NotEnoughResourcesPayed() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> payment = new HashMap<>();
		payment.put(TileType.brick, 1);
		payment.put(TileType.wool, 1);
		payment.put(TileType.wheat, 1);
		assertTrue(!cb.tradeWithBank(payment, TileType.ore));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1,  pc.getCurrentPlayer().getResourceCount(TileType.wheat));
	}
	
	@Test
	public void testPlaceRoadNotEnoughResources() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
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
	
	@Test
	public void testBuySettlementMaxNumberSettlements() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		Player player = pc.getCurrentPlayer();
		addResourcesForSettlements(player, 4);
		
		assertEquals(player.numSettlements, 2);
		cb.buySettlement();
		assertEquals(player.numSettlements, 3);
		cb.buySettlement();
		assertEquals(player.numSettlements, 4);
		cb.buySettlement();
		assertEquals(player.numSettlements, 5);
		cb.buySettlement();
		assertEquals(player.numSettlements, 5);
		
		assertEquals(1, player.getResourceCount(TileType.brick));
		assertEquals(1, player.getResourceCount(TileType.wheat));
		assertEquals(1, player.getResourceCount(TileType.wool));
		assertEquals(1, player.getResourceCount(TileType.wood));
	}
	
	@Test
	public void testBuyCityMaxNumberSettlements() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		Player player = pc.getCurrentPlayer();
		player.numSettlements = 4;
		addResourcesForCities(player, 5);
		
		assertEquals(player.numSettlements, 4);
		assertEquals(player.numCities, 0);
		assertTrue(cb.buyCity());
		assertEquals(player.numSettlements, 3);
		assertEquals(player.numCities, 1);
		assertEquals(8, player.getResourceCount(TileType.wheat));
		assertEquals(12, player.getResourceCount(TileType.ore));
		assertTrue(cb.buyCity());
		assertEquals(player.numSettlements, 2);
		assertEquals(player.numCities, 2);
		assertEquals(6, player.getResourceCount(TileType.wheat));
		assertEquals(9, player.getResourceCount(TileType.ore));
		assertTrue(cb.buyCity());
		assertEquals(player.numSettlements, 1);
		assertEquals(player.numCities, 3);
		assertEquals(4, player.getResourceCount(TileType.wheat));
		assertEquals(6, player.getResourceCount(TileType.ore));
		assertTrue(cb.buyCity());
		assertEquals(player.numSettlements, 0);
		assertEquals(player.numCities, 4);
		assertEquals(2, player.getResourceCount(TileType.wheat));
		assertEquals(3, player.getResourceCount(TileType.ore));
		assertFalse(cb.buyCity());
		assertEquals(player.numSettlements, 0);
		assertEquals(player.numCities, 4);
		assertEquals(2, player.getResourceCount(TileType.wheat));
		assertEquals(3, player.getResourceCount(TileType.ore));
		
		assertEquals(2, player.getResourceCount(TileType.wheat));
		assertEquals(3, player.getResourceCount(TileType.ore));
	}
	
	@Test
	public void testAddCityToTilesValid() {
		basicSetupForAddSettlementTests();
		
		tileNums.add(0);
		tileNums.add(1);
		tileNums.add(4);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
		
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertTrue(result);
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		result = cb.addCityToTiles(tileNums, cornerNums);
		assertEquals(2, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		assertTrue(result);
		
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
	
	private void addResourcesForCities(Player player, int numCities) {
		player.addResource(TileType.wheat, numCities*2);
		player.addResource(TileType.ore, numCities*3);
	}
	
	private void addResourcesForSettlements(Player player, int numSettlements) {
		player.addResource(TileType.brick, numSettlements);
		player.addResource(TileType.wheat, numSettlements);
		player.addResource(TileType.wool, numSettlements);
		player.addResource(TileType.wood, numSettlements);
	}

	private void basicSetupForAddSettlementTests() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		clearClicks();
	}

	private void makePlayerTurnInit(int tileNum, int settlementCorner, int roadCorner, int playerNum, int rAngle) {
		addPlayerClickNums(tileNum, settlementCorner);
		registerPlayerClick();
		checkPlayerTileForSettlement(tileNum, settlementCorner, playerNum);
		addPlayerDragNums(tileNum, settlementCorner, roadCorner);
		registerPlayerDragSetup();
		Road r = checkPlayerTileForRoad(playerNum, tileNum, settlementCorner, roadCorner);
		assertEquals(rAngle, r.getAngle());
		pc.nextPlayer();
	}
	
	private Road checkPlayerTileForRoad(int playerNum, int tileNum, int roadCorner1, int roadCorner2) {
		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(tileNum);
		HashMap<ArrayList<Integer>, Road> roads = tile.getRoads();
		ArrayList<Integer> edge = new ArrayList<Integer>();
		edge.add(roadCorner1); edge.add(roadCorner2);
		Road r = roads.get(edge);
		assertEquals(r.getOwner(), pc.getPlayer(playerNum));
		return r;
	}

	private void checkPlayerTileForSettlement(int tileNum, int cornerNum, int playerNum) {
		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(tileNum);
		HashMap<Integer, Settlement> settlements = tile.getSettlements();
		Settlement s = settlements.get(cornerNum);
		assertEquals(s.getOwner(), pc.getPlayer(playerNum));
	}
	
	private void registerPlayerClick() {
		cb.settlementLocationClick(tileNums, cornerNums, GameStates.drop_settlement_setup);
		clearClicks();
	}
	
	private void registerPlayerDragSetup() {
		cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road_setup);
		clearDrags();
	}
	
	private void clearClicks() {
		tileNums.clear();
		cornerNums.clear();
	}
	
	private void clearDrags() {
		tileToCorners.clear();
		tileToRoadOrientation.clear();
	}

	private void addPlayerClickNums(int tileNum, int cornerNum) {
		tileNums.add(tileNum);
		cornerNums.add(cornerNum);
	}
	
	private void addPlayerDragNums(int tileNum, int cornerNum1, int cornerNum2) {
		ArrayList<Integer> corners = new ArrayList<Integer>();
		corners.add(cornerNum1);
		corners.add(cornerNum2);
		
		tileToCorners.put(tileNum, corners);
		tileToRoadOrientation.put(tileNum, getRoadOrientation(corners));
	}
	
	private int getRoadOrientation(ArrayList<Integer> corners) {
		int p1 = corners.get(0);
		int p2 = corners.get(1);
		if (p1 > p2) {
			int temp = p1;
			p1 = p2;
			p2 = temp;
		}
		if (p1 == 0 && p2 == 5 || p1 == 2 && p2 == 3) {
			return 0;
		} else if (p1 == 0 && p2 == 1 || p1 == 3 && p2 == 4) {
			return 1;
		} else if (p1 == 1 && p2 == 2 || p1 == 4 && p2 == 5) {
			return 2;
		}
		return -1;
	}

}
