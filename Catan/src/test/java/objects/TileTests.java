package objects;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

public class TileTests {
	
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
	public void testCheckValidRoadPlacement1() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		
		assertEquals(false, tile.checkValidRoadPlacement(5, 0, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacement2() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		
		assertEquals(true, tile.checkValidRoadPlacement(3, 2, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacement3() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Road roadToPlace = new Road(player1);
		
		assertEquals(false, tile.checkValidRoadPlacement(3, 2, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacementDifferentPlayer() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		
		Player player2 = new Player(Color.blue);
		Road roadToPlace = new Road(player2);
		assertEquals(false, tile.checkValidRoadPlacement(3, 2, roadToPlace));
	}
	
	@Test
	public void testCheckValidRoadPlacementConnectedToRoad() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		
		assertEquals(true, tile.checkValidRoadPlacement(3, 2, roadToPlace));
		tile.addRoad(3, 2, roadToPlace);
		
		Road roadToConnect = new Road(player1);
		assertEquals(true, tile.checkValidRoadPlacement(2, 1, roadToConnect));
	}
	
	@Test
	public void testCheckValidRoadPlacementConnectedToRoadDifferentPlayer() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		
		assertEquals(true, tile.checkValidRoadPlacement(3, 2, roadToPlace));
		tile.addRoad(3, 2, roadToPlace);
		
		Player player2 = new Player(Color.blue);
		Road roadToConnect = new Road(player2);
		assertEquals(false, tile.checkValidRoadPlacement(2, 1, roadToConnect));
	}
	
	@Test
	public void testCheckValidRoadPlacementLocationTaken() {
		Tile tile = new Tile(null, 5, TileType.brick);
		Player player1 = new Player(Color.red);
		Settlement settlementPlayer1 = new Settlement(player1);
		tile.addSettlement(3, settlementPlayer1);
		Road roadToPlace = new Road(player1);
		
		assertEquals(true, tile.checkValidRoadPlacement(3, 2, roadToPlace));
		tile.addRoad(3, 2, roadToPlace);
		
		Road roadToConnect = new Road(player1);
		assertEquals(false, tile.checkValidRoadPlacement(3, 2, roadToConnect));
	}

}
