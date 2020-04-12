package objects;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class CatanBoardTest {
	
	// Player numbers
	int player1 = 0;
	int player2 = 1;
	int player3 = 2;
	int player4 = 3;
	
	// Stores click locations for players in the tests
	ArrayList<Integer> tileNums = new ArrayList<Integer>();
	ArrayList<Integer> cornerNums = new ArrayList<Integer>();
	
	HashMap<Integer, ArrayList<Integer>> tileToCorners = new HashMap<Integer, ArrayList<Integer>>();
	HashMap<Integer, Integer> tileToRoadOrientation = new HashMap<Integer, Integer>();
	

	@Test
	public void testConstruct4Player() {
		PlayersController pc = new PlayersController(4);
		CatanBoard cb = new CatanBoard(pc);
		assertEquals(cb.players.size(), 4);
	}
	
	@Test
	public void testConstruct3Player() {
		CatanBoard cb = new CatanBoard(3);
		assertEquals(cb.players.size(), 3);
	}
	
	@Test
	public void testLocationClicked1() {
		CatanBoard cb = new CatanBoard(4);
		int tileNum = 1; int cornerNum= 0;
		addPlayerClickNums(tileNum, cornerNum);
		
		registerPlayerClick(cb);
		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(tileNum);
		HashMap<Integer, Settlement> settlements = tile.getSettlements();
		
		Settlement s = settlements.get(cornerNum);
		assertEquals(s.getOwner(), cb.players.get(player1));
	}
	
	@Test
	public void testBoardInitSetupSettlementRoad() {
		CatanBoard cb = new CatanBoard(3);
		int p1tile = 1; int p1corner = 1;
		int p1roadTile = 1; int p1road1 = 1; int p1road2 = 2;

		// Place Settlement
		addPlayerClickNums( p1tile, p1corner);
		registerPlayerClick(cb);
		checkPlayerTileForSettlement(cb, p1tile, p1corner, player1);
		
		// Place Road
		addPlayerDragNums(p1roadTile, p1road1, p1road2);
		registerPlayerDrag(cb);
		Road r = checkPlayerTileForRoad(cb, player1, p1roadTile, p1road1, p1road2);
		assertEquals(r.getAngle(), 2);
	}
	
	private Road checkPlayerTileForRoad(CatanBoard cb, int playerNum, int tileNum, int roadCorner1, int roadCorner2) {
		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(tileNum);
		HashMap<ArrayList<Integer>, Road> roads = tile.getRoads();
		ArrayList<Integer> edge = new ArrayList<Integer>();
		edge.add(roadCorner1); edge.add(roadCorner2);
		Road r = roads.get(edge);
		assertEquals(r.getOwner(), cb.players.get(playerNum));
		return r;
	}

	@Test
	public void testBoardInitSetup3Player() {
		CatanBoard cb = new CatanBoard(3);
		int p1tile = 1; int p1corner = 1; int p1road = 2;
		int p2tile = 5; int p2corner = 5; int p2road = 0;
		int p3tile = 15; int p3corner = 3; int p3road = 4;
		
		int p1tile2 = 3; int p1corner2 = 1; int p1road2 = 2;
		int p2tile2 = 10; int p2corner2 = 5; int p2road2 = 4;
		int p3tile2 = 18; int p3corner2 = 3; int p3road2 = 2;
		
		// Everyone places first settlements and roads
		makePlayerTurnInit(cb, p1tile, p1corner, p1road, player1, 2);
		
		makePlayerTurnInit(cb, p2tile, p2corner, p2road, player2, 0);

		makePlayerTurnInit(cb, p3tile, p3corner, p3road, player3, 1);
		
		// Second Round of settlements
		makePlayerTurnInit(cb, p3tile2, p3corner2, p3road2, player3, 0);

		makePlayerTurnInit(cb, p2tile2, p2corner2, p2road2, player2, 2);
		
		makePlayerTurnInit(cb, p1tile2, p1corner2, p1road2, player1, 2);
	}

	private void makePlayerTurnInit(CatanBoard cb, int tileNum, int settlementCorner, int roadCorner, int playerNum, int rAngle) {
		addPlayerClickNums(tileNum, settlementCorner);
		registerPlayerClick(cb);
		checkPlayerTileForSettlement(cb, tileNum, settlementCorner, playerNum);
		addPlayerDragNums(tileNum, settlementCorner, roadCorner);
		registerPlayerDrag(cb);
		Road r = checkPlayerTileForRoad(cb, playerNum, tileNum, settlementCorner, roadCorner);
		assertEquals(rAngle, r.getAngle());
	}
	
	@Test
	public void testBoardInitSetup4Player() {
		CatanBoard cb = new CatanBoard(4);
		int p1tile = 1; int p1corner = 1; int p1road = 2;
		int p2tile = 5; int p2corner = 5; int p2road = 0;
		int p3tile = 15; int p3corner = 3; int p3road = 4;
		int p4tile = 13; int p4corner = 0; int p4road = 1;
		
		int p1tile2 = 3; int p1corner2 = 1; int p1road2 = 2;
		int p2tile2 = 10; int p2corner2 = 5; int p2road2 = 4;
		int p3tile2 = 18; int p3corner2 = 3; int p3road2 = 2;
		int p4tile2 = 11; int p4corner2 = 2; int p4road2 = 3;
		
		// Everyone places first settlements and roads
		makePlayerTurnInit(cb, p1tile, p1corner, p1road, player1, 2);
		
		makePlayerTurnInit(cb, p2tile, p2corner, p2road, player2, 0);

		makePlayerTurnInit(cb, p3tile, p3corner, p3road, player3, 1);
		
		makePlayerTurnInit(cb, p4tile, p4corner, p4road, player4, 1);
		
		// Second Round of settlements
		makePlayerTurnInit(cb, p4tile2, p4corner2, p4road2, player4, 0);
		
		makePlayerTurnInit(cb, p3tile2, p3corner2, p3road2, player3, 0);

		makePlayerTurnInit(cb, p2tile2, p2corner2, p2road2, player2, 2);
		
		makePlayerTurnInit(cb, p1tile2, p1corner2, p1road2, player1, 2);
	}

	private void checkPlayerTileForSettlement(CatanBoard cb, int tileNum, int cornerNum, int playerNum) {
		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(tileNum);
		HashMap<Integer, Settlement> settlements = tile.getSettlements();
		Settlement s = settlements.get(cornerNum);
		assertEquals(s.getOwner(), cb.players.get(playerNum));
	}
	
	private void registerPlayerClick(CatanBoard cb) {
		cb.locationClicked(tileNums, cornerNums);
		clearClicks();
	}
	
	private void registerPlayerDrag(CatanBoard cb) {
		cb.locationClicked(tileToCorners, tileToRoadOrientation);
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
