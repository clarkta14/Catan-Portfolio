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

public class CatanBoard_SettlementTests extends CatanBoardTest {
	
	@BeforeEach
	private void basicSetupForAddSettlementTests() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		clearClicks();
	}
	
	@Test
	public void testBuySettlementMaxNumberSettlements() {
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
	public void testAddSettlementToTiles() {
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
		
		for (Settlement s: cb.getTiles().get(0).getSettlements().values()) {
			assertEquals(PortType.none, s.portType);
		}
	}
	
	@Test
	public void testAddSettlementToTilesTwoSettlementsSamePlace() {
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
	public void testAddSettlementToTilesNotSetupBadPlacement_SufficientResources() {
		tileNums.add(0);
		tileNums.add(1);
		tileNums.add(4);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
		
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		pc.getCurrentPlayer().addResource(TileType.wood, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement);
		assertFalse(result);
		assertEquals(0, pc.getCurrentPlayer().getNumberOfVictoryPoints());
	}
	
	@Test
	public void testAddSettlementToTilesNotSetupValidPlacement() {
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
	public void testAddSettlementToTiles_Tiles() {
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
	public void testGetPlayersWithSettlementOnTile_SinglePlayer() {
		tileNums.add(0);
		cornerNums.add(3);
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertTrue(result);
		ArrayList<Player> playersWithSettlementsOnTile = cb.getPlayersWithSettlementOnTile(cb.getTiles().get(tileNums.get(0)));
		assertEquals(1, playersWithSettlementsOnTile.size());
		assertEquals(pc.getCurrentPlayer(), playersWithSettlementsOnTile.get(0));
	}
	
	@Test
	public void testGetPlayersWithSettlementOnTile_MultiplePlayersOnSameTile() {
		ArrayList<Integer> tiles = new ArrayList<Integer>();
		ArrayList<Integer> cornersOnTiles = new ArrayList<Integer>();
		tiles.add(0);
		cornersOnTiles.add(3);
		boolean result = cb.addSettlementToTiles(tiles, cornersOnTiles, GameStates.drop_settlement_setup);
		assertTrue(result);
		Player plyr1 = pc.getCurrentPlayer();
		pc.nextPlayer();
		tiles = new ArrayList<Integer>();
		cornersOnTiles = new ArrayList<Integer>();
		tiles.add(0);
		cornersOnTiles.add(6);
		result = cb.addSettlementToTiles(tiles, cornersOnTiles, GameStates.drop_settlement_setup);
		assertTrue(result);
		
		ArrayList<Player> playersWithSettlementsOnTile = cb.getPlayersWithSettlementOnTile(cb.getTiles().get(0));
		assertEquals(2, playersWithSettlementsOnTile.size());
		assertEquals(plyr1, playersWithSettlementsOnTile.get(0));
		assertEquals(pc.getCurrentPlayer(), playersWithSettlementsOnTile.get(1));
	}
	
	@Test
	public void testGetPlayersWithSettlementOnTile_NoSettlementsOnTile() {
		ArrayList<Integer> tiles = new ArrayList<Integer>();
		ArrayList<Integer> cornersOnTiles = new ArrayList<Integer>();
		tiles.add(0);
		cornersOnTiles.add(3);
		boolean result = cb.addSettlementToTiles(tiles, cornersOnTiles, GameStates.drop_settlement_setup);
		assertTrue(result);
		pc.nextPlayer();
		tiles = new ArrayList<Integer>();
		cornersOnTiles = new ArrayList<Integer>();
		tiles.add(0);
		cornersOnTiles.add(6);
		result = cb.addSettlementToTiles(tiles, cornersOnTiles, GameStates.drop_settlement_setup);
		assertTrue(result);
		
		ArrayList<Player> playersWithSettlementsOnTile = cb.getPlayersWithSettlementOnTile(cb.getTiles().get(1));
		assertEquals(0, playersWithSettlementsOnTile.size());
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
	public void getPlayersWithSettlementOnTile2SettlementsSameTile() {
		tileNums.add(0);
		tileNums.add(1);
		tileNums.add(4);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
		
		cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		clearClicks();
		
		tileNums.add(1);
		tileNums.add(2);
		tileNums.add(5);
		
		cornerNums.add(3);
		cornerNums.add(1);
		cornerNums.add(5);
		
		cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		
		Tile tile = cb.getTiles().get(1);
		Player player = pc.getCurrentPlayer();
		ArrayList<Player> players = cb.getPlayersWithSettlementOnTile(tile);
		assertEquals(1, players.size());
		assertTrue(players.contains(player));
		
	}
	
	@Test
	public void testGetPlayersWithSettlementOnRobberTile() {
		Tile robberTile = null;

		ArrayList<Tile> tiles = cb.getTiles();
		int robberTileNum = -1;
		for (int i = 0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			if (tile.isRobber()) {
				robberTile = tile;
				robberTileNum = i;
				break;
			}
		}
		
		Player currentPlayer = pc.getCurrentPlayer();
		tileNums.add(robberTileNum);
		cornerNums.add(0);
		boolean result = cb.addSettlementToTiles(tileNums, cornerNums, GameStates.drop_settlement_setup);
		assertTrue(result);
		assertEquals(1, robberTile.getSettlements().size());
		ArrayList<Player> players = cb.getPlayersWithSettlementOnRobberTile();
		assertEquals(1, players.size());
		assertTrue(players.contains(currentPlayer));
	}
		
}
