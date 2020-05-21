package objects;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import gui.GameStates;

public class CatanBoard_PortsTest {
	private PlayersController pc;
	private CatanBoard cb;
	private int[] portTiles = new int[] { 0, 1, 6, 11, 15, 17, 16, 12, 3 };
	private int[][] portCorners = new int[][] { { 0, 5 }, { 4, 5 }, { 4, 5 }, { 3, 4 }, { 2, 3 }, { 2, 3 }, { 1, 2 },
			{ 0, 1 }, { 0, 1 } };

	@Test
	public void testTileLocationsArePorts() {
		testTileLocationsArePortsHalf(0);
		testTileLocationsArePortsHalf(1);
	}

	private void testTileLocationsArePortsHalf(int cornerNum) {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);

		ArrayList<Tile> tiles = cb.getTiles();
		addSettlementsToPorts(tiles, cornerNum);

		checkTileLocationsArePorts(tiles, cornerNum);
	}

	@Test
	public void testTileLocationsArePortsCorrectType() {
		testTileLocationsArePortsCorrectTypeHalf(0);
		testTileLocationsArePortsCorrectTypeHalf(1);
	}

	private void testTileLocationsArePortsCorrectTypeHalf(int cornerNum) {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);

		int woolPorts = 1;
		int brickPorts = 1;
		int orePorts = 1;
		int woodPorts = 1;
		int wheatPorts = 1;
		int threePorts = 4;

		ArrayList<Tile> tiles = cb.getTiles();
		addSettlementsToPorts(tiles, cornerNum);

		assertEquals(woolPorts, getNumberOfPortType(tiles, PortType.wool, cornerNum));
		assertEquals(brickPorts, getNumberOfPortType(tiles, PortType.brick, cornerNum));
		assertEquals(orePorts, getNumberOfPortType(tiles, PortType.ore, cornerNum));
		assertEquals(woodPorts, getNumberOfPortType(tiles, PortType.wood, cornerNum));
		assertEquals(wheatPorts, getNumberOfPortType(tiles, PortType.wheat, cornerNum));
		assertEquals(threePorts, getNumberOfPortType(tiles, PortType.three, cornerNum));
	}
	
	@Test
	public void tradeWithBank3to1Port() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		cb.portTypes.add(0, PortType.three);
		ArrayList<Integer> selectedTiles = new ArrayList<Integer>();
		selectedTiles.add(portTiles[0]);
		ArrayList<Integer> corners = new ArrayList<Integer>();
		corners.add(portCorners[0][0]);
		cb.addSettlementToTiles(selectedTiles, corners, GameStates.drop_settlement_setup);
		
		Player currentPlayer = pc.getCurrentPlayer();
		currentPlayer.addResource(TileType.brick, 3);
		HashMap<TileType, Integer> payment = new HashMap<TileType, Integer>();
		payment.put(TileType.brick, 3);
		
		assertTrue(cb.tradeWithBank(payment, TileType.ore));
		assertEquals(1, currentPlayer.getResourceCount(TileType.ore));
		assertEquals(0, currentPlayer.getResourceCount(TileType.brick));
	}
	
	@Test
	public void tradeWithBank3to1PortNotEnoughResources() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		cb.portTypes.add(0, PortType.three);
		ArrayList<Integer> selectedTiles = new ArrayList<Integer>();
		selectedTiles.add(portTiles[0]);
		ArrayList<Integer> corners = new ArrayList<Integer>();
		corners.add(portCorners[0][0]);
		cb.addSettlementToTiles(selectedTiles, corners, GameStates.drop_settlement_setup);
		
		Player currentPlayer = pc.getCurrentPlayer();
		currentPlayer.addResource(TileType.brick, 2);
		HashMap<TileType, Integer> payment = new HashMap<TileType, Integer>();
		payment.put(TileType.brick, 2);
		
		assertFalse(cb.tradeWithBank(payment, TileType.ore));
		assertEquals(0, currentPlayer.getResourceCount(TileType.ore));
		assertEquals(2, currentPlayer.getResourceCount(TileType.brick));
	}
	
	@Test
	public void tradeWithBricksPort() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		cb.portTypes.add(0, PortType.brick);
		ArrayList<Integer> selectedTiles = new ArrayList<Integer>();
		selectedTiles.add(portTiles[0]);
		ArrayList<Integer> corners = new ArrayList<Integer>();
		corners.add(portCorners[0][0]);
		cb.addSettlementToTiles(selectedTiles, corners, GameStates.drop_settlement_setup);
		
		Player currentPlayer = pc.getCurrentPlayer();
		currentPlayer.addResource(TileType.brick, 2);
				
		assertTrue(cb.portTrade(TileType.brick, TileType.ore));
		assertEquals(1, currentPlayer.getResourceCount(TileType.ore));
		assertEquals(0, currentPlayer.getResourceCount(TileType.brick));
	}
	
	@Test
	public void tradeWithWheatPortNotEnough() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		cb.portTypes.add(0, PortType.wheat);
		ArrayList<Integer> selectedTiles = new ArrayList<Integer>();
		selectedTiles.add(portTiles[0]);
		ArrayList<Integer> corners = new ArrayList<Integer>();
		corners.add(portCorners[0][0]);
		cb.addSettlementToTiles(selectedTiles, corners, GameStates.drop_settlement_setup);
		
		Player currentPlayer = pc.getCurrentPlayer();
		currentPlayer.addResource(TileType.wheat, 1);
				
		assertFalse(cb.portTrade(TileType.wheat, TileType.wool));
		assertEquals(0, currentPlayer.getResourceCount(TileType.wool));
		assertEquals(1, currentPlayer.getResourceCount(TileType.wheat));
	}
	
	@Test
	public void tradeWithOrePortWrongPortType() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		cb.portTypes.add(0, PortType.wool);
		ArrayList<Integer> selectedTiles = new ArrayList<Integer>();
		selectedTiles.add(portTiles[0]);
		ArrayList<Integer> corners = new ArrayList<Integer>();
		corners.add(portCorners[0][0]);
		cb.addSettlementToTiles(selectedTiles, corners, GameStates.drop_settlement_setup);
		
		Player currentPlayer = pc.getCurrentPlayer();
		currentPlayer.addResource(TileType.ore, 2);
				
		assertFalse(cb.portTrade(TileType.ore, TileType.wood));
		assertEquals(0, currentPlayer.getResourceCount(TileType.wood));
		assertEquals(2, currentPlayer.getResourceCount(TileType.ore));
	}

	private void checkTileLocationsArePorts(ArrayList<Tile> tiles, int cornerNum) {
		for (int i = 0; i < portTiles.length; i++) {
			Tile tile = tiles.get(portTiles[i]);
			Settlement port = tile.getSettlements().get(portCorners[i][cornerNum]);
			assertFalse(port.portType == null);
			assertFalse(port.portType == PortType.none);
		}

	}

	private void addSettlementsToPorts(ArrayList<Tile> tiles, int cornerNum) {
		for (int i = 0; i < portTiles.length; i++) {
			ArrayList<Integer> selectedTiles = new ArrayList<Integer>();
			selectedTiles.add(portTiles[i]);
			ArrayList<Integer> corners = new ArrayList<Integer>();
			corners.add(portCorners[i][cornerNum]);
			cb.addSettlementToTiles(selectedTiles, corners, GameStates.drop_settlement_setup);
		}
	}

	private int getNumberOfPortType(ArrayList<Tile> tiles, PortType type, int cornerNum) {
		int count = 0;
		for (int i = 0; i < portTiles.length; i++) {
			Tile tile = tiles.get(portTiles[i]);
			Settlement port = tile.getSettlements().get(portCorners[i][cornerNum]);
			if (port.portType == type) {
				count++;
			}
		}
		return count;
	}

}
