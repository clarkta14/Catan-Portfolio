package objects;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class TileTests {
	
	@Test
	public void testConstructorWithType() {
		TileType type = TileType.brick;
		Tile tile = new Tile(null, 5, type);
		assertEquals(type, tile.getType());
	}
	
	@Test
	public void testConstructorWithNumber() {
		int number = 5;
		Tile tile = new Tile(null, number, TileType.brick);
		assertEquals(number, tile.getNumber());
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testSetHexCorners() {
		Tile tile = new Tile(null, 5, TileType.brick);
		ArrayList<Point> points = new ArrayList<Point>() {{
			add(new Point(1,1)); add(new Point(2,2));
		}};
		tile.setHexCorners(points);
		assertEquals(points, tile.getHexCorners());
	}
	
	@Test
	public void testSetLocation() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Point location = new Point(1,1);
		tile.setLocation(location);
		assertEquals(location, tile.getLocation());
	}
	
	@Test
	public void testSetRobber() {
		Tile tile = new Tile(null, 5, TileType.brick);
		tile.setRobber(true);
		assertTrue(tile.isRobber());
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testGetSettlements() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		HashMap<Integer, Settlement> map = new HashMap<Integer, Settlement>() {{
			put(3, settlementPlayer1);
		}};
		assertEquals(map, tile.getSettlements());
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testGetRoads() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		tile.addRoad(3, 2, roadToPlace);
		HashMap<ArrayList<Integer>, Road> roads = tile.getRoads();
		ArrayList<Integer> position = new ArrayList<Integer>() {{
			add(3);
			add(2);
		}};
		assertEquals(roadToPlace, roads.get(position));
	}

	@Test
	public void testCheckValidRoadPlacement1() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		
		assertFalse(tile.checkValidRoadPlacement(5, 0, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacement2() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		
		assertTrue(tile.checkValidRoadPlacement(3, 2, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacement3() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Road roadToPlace = new Road(player1);
		
		assertFalse(tile.checkValidRoadPlacement(3, 2, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacementDifferentPlayer() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		
		Player player2 = new Player(Color.blue);
		Road roadToPlace = new Road(player2);
		assertFalse(tile.checkValidRoadPlacement(3, 2, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacementConnectedToRoad() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		
		assertTrue(tile.checkValidRoadPlacement(3, 2, roadToPlace));
		tile.addRoad(3, 2, roadToPlace);
		
		Road roadToConnect = new Road(player1);
		assertTrue(tile.checkValidRoadPlacement(2, 1, roadToConnect));
	}
	
	@Test
	public void testCheckValidRoadPlacementConnectedToRoadDifferentPlayer() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		
		assertTrue(tile.checkValidRoadPlacement(3, 2, roadToPlace));
		tile.addRoad(3, 2, roadToPlace);
		
		Player player2 = new Player(Color.blue);
		Road roadToConnect = new Road(player2);
		assertFalse(tile.checkValidRoadPlacement(2, 1, roadToConnect));
	}
	
	@Test
	public void testCheckValidRoadPlacementLocationTaken() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		
		assertTrue(tile.checkValidRoadPlacement(3, 2, roadToPlace));
		tile.addRoad(3, 2, roadToPlace);
		
		Road roadToConnect = new Road(player1);
		assertFalse(tile.checkValidRoadPlacement(3, 2, roadToConnect));
	}
	
	@Test public void testCheckVaildSettlementPlacementCorner0NoSettlements() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		
		boolean actual = tile.checkValidSettlementPlacement(0, settlementPlayer1);
		assertTrue(actual);
	}
	
	@Test public void testCheckVaildSettlementPlacementTwoSettlementsSamePlace() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlement0 = new Settlement(player1);
		Settlement settlement1 = new Settlement(player1);
		tile.addSettlement(0, settlement1);
		
		boolean actual = tile.checkValidSettlementPlacement(0, settlement0);
		assertFalse(actual);
	}

}
