package objects;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
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
		ArrayList<Integer> edge = new ArrayList<Integer>( Arrays.asList(5, 0));
		
		assertFalse(tile.checkValidRoadPlacement(edge, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacement2() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		ArrayList<Integer> edge = new ArrayList<Integer>( Arrays.asList(3, 2));
		
		assertTrue(tile.checkValidRoadPlacement(edge, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacement3() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Road roadToPlace = new Road(player1);
		ArrayList<Integer> edge = new ArrayList<Integer>( Arrays.asList(3, 2));
		
		assertFalse(tile.checkValidRoadPlacement(edge, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacement4() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		ArrayList<Integer> edge = new ArrayList<Integer>( Arrays.asList(2, 3));
		
		assertTrue(tile.checkValidRoadPlacement(edge, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacementTooLong() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(1, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		ArrayList<Integer> edge = new ArrayList<Integer>( Arrays.asList(1, 4));
		
		assertFalse(tile.checkValidRoadPlacement(edge, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacementDifferentPlayer() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		
		Player player2 = new Player(Color.blue);
		Road roadToPlace = new Road(player2);
		ArrayList<Integer> edge = new ArrayList<Integer>( Arrays.asList(3, 2));
		
		assertFalse(tile.checkValidRoadPlacement(edge, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacementConnectedToRoad() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		ArrayList<Integer> edge = new ArrayList<Integer>( Arrays.asList(3, 2));
		
		assertTrue(tile.checkValidRoadPlacement(edge, roadToPlace));
		tile.addRoad(3, 2, roadToPlace);
		
		edge = new ArrayList<Integer>( Arrays.asList(2, 1));
		Road roadToConnect = new Road(player1);
		assertTrue(tile.checkValidRoadPlacement(edge, roadToConnect));
	}
	
	@Test
	public void testCheckValidRoadPlacementConnectedToRoad2() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		ArrayList<Integer> edge = new ArrayList<Integer>(Arrays.asList(3, 2));
		
		assertTrue(tile.checkValidRoadPlacement(edge, roadToPlace));
		tile.addRoad(3, 2, roadToPlace);
		
		edge = new ArrayList<Integer>( Arrays.asList(1, 2));
		Road roadToConnect = new Road(player1);
		assertTrue(tile.checkValidRoadPlacement(edge, roadToConnect));
	}
	
	@Test
	public void testCheckValidRoadPlacementConnectedToRoad3() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		ArrayList<Integer> edge = new ArrayList<Integer>( Arrays.asList(2, 3));
		
		assertTrue(tile.checkValidRoadPlacement(edge, roadToPlace));
		tile.addRoad(2, 3, roadToPlace);
		
		edge = new ArrayList<Integer>( Arrays.asList(4, 3));
		Road roadToConnect = new Road(player1);
		assertTrue(tile.checkValidRoadPlacement(edge, roadToConnect));
	}
	
	@Test
	public void testCheckValidRoadPlacementConnectedToRoadDifferentPlayer() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		ArrayList<Integer> edge = new ArrayList<Integer>( Arrays.asList(3, 2));
		
		assertTrue(tile.checkValidRoadPlacement(edge, roadToPlace));
		tile.addRoad(3, 2, roadToPlace);
		
		Player player2 = new Player(Color.blue);
		Road roadToConnect = new Road(player2);
		edge = new ArrayList<Integer>( Arrays.asList(2, 1));
		assertFalse(tile.checkValidRoadPlacement(edge, roadToConnect));
	}
	
	@Test
	public void testCheckValidRoadPlacementLocationTaken() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		ArrayList<Integer> edge = new ArrayList<Integer>( Arrays.asList(3, 2));
		
		assertTrue(tile.checkValidRoadPlacement(edge, roadToPlace));
		tile.addRoad(3, 2, roadToPlace);
		
		Road roadToConnect = new Road(player1);
		assertFalse(tile.checkValidRoadPlacement(edge, roadToConnect));
	}
	
	@Test
	public void testCheckVaildSettlementPlacementCorner0NoSettlements() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		
		boolean actual = tile.checkValidSettlementPlacement(0);
		assertTrue(actual);
	}
	
	@Test
	public void testCheckVaildSettlementPlacementTwoSettlementsSamePlace() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlement0 = new Settlement(player1);
		tile.addSettlement(0, settlement0);
		
		boolean actual = tile.checkValidSettlementPlacement(0);
		assertFalse(actual);
	}
	
	@Test
	public void testCheckVaildSettlementPlacementTwoSettlementsCorners0and1() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlement1 = new Settlement(player1);
		tile.addSettlement(1, settlement1);
		
		boolean actual = tile.checkValidSettlementPlacement(0);
		assertFalse(actual);
	}
	
	@Test
	public void testCheckVaildSettlementPlacementTwoSettlementsCorners0and5() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlement1 = new Settlement(player1);
		tile.addSettlement(5, settlement1);
		
		boolean actual = tile.checkValidSettlementPlacement(0);
		assertFalse(actual);
	}
	
	@Test
	public void testCheckVaildSettlementPlacementTwoSettlementsCorners5and0() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlement0 = new Settlement(player1);
		tile.addSettlement(0, settlement0);
		
		boolean actual = tile.checkValidSettlementPlacement(5);
		assertFalse(actual);
	}
	
	@Test
	public void testCheckVaildSettlementPlacementTwoSettlementsTwoSpacesAway() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlement2 = new Settlement(player1);
		tile.addSettlement(2, settlement2);
		
		boolean actual = tile.checkValidSettlementPlacement(0);
		assertTrue(actual);
	}
	
	@Test
	public void testCheckRoadAtCornerForGivenPlayerNoRoads() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		
		boolean result = tile.checkRoadAtCornerForGivenPlayer(0, player1);
		
		assertFalse(result);
	}
	
	@Test
	public void testCheckRoadAtCornerForGivenPlayerOneValidRoad() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Road road = new Road(player1);
		tile.addRoad(0, 1, road);
		
		boolean result = tile.checkRoadAtCornerForGivenPlayer(0, player1);
		
		assertTrue(result);
	}
	
	@Test
	public void testCheckRoadAtCornerForGivenPlayerOneInvalidRoad() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Road road = new Road(player1);
		tile.addRoad(2, 1, road);
		
		boolean result = tile.checkRoadAtCornerForGivenPlayer(0, player1);
		
		assertFalse(result);
	}
	
	@Test
	public void testCheckRoadAtCornerForGivenPlayerOneValidRoadDifferentPlayer() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Player player2 = new Player(Color.orange);
		Road road = new Road(player2);
		tile.addRoad(0, 1, road);
		
		boolean result = tile.checkRoadAtCornerForGivenPlayer(0, player1);
		
		assertFalse(result);
	}

}
