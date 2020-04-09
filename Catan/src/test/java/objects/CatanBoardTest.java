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
	ArrayList<Integer> tileNums1 = new ArrayList<Integer>();
	ArrayList<Integer> corners1 = new ArrayList<Integer>();
	
	ArrayList<Integer> tileNums2 = new ArrayList<Integer>();
	ArrayList<Integer> corners2 = new ArrayList<Integer>();
	
	ArrayList<Integer> tileNums3 = new ArrayList<Integer>();
	ArrayList<Integer> corners3 = new ArrayList<Integer>();
	
	ArrayList<Integer> tileNums4 = new ArrayList<Integer>();
	ArrayList<Integer> corners4 = new ArrayList<Integer>();

	@Test
	public void testConstruct4Player() {
		CatanBoard cb = new CatanBoard(4);
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
		addPlayerClickNums(player1, tileNum, cornerNum);
		
		registerPlayerClick(cb, player1);
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
		addPlayerClickNums(player1, p1tile, p1corner);
		registerPlayerClick(cb, player1);
		checkPlayerTileForSettlement(cb, p1tile, p1corner, player1);
		
		// Place Road
		addPlayerDragNums(player1, p1roadTile, p1road1, p1roadTile, p1road2);
		registerPlayerClick(cb, player1);
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

	private void makePlayerTurnInit(CatanBoard cb, int tile, int corner, int road, int playerNum, int rAngle) {
		addPlayerClickNums(playerNum, tile, corner);
		registerPlayerClick(cb, playerNum);
		checkPlayerTileForSettlement(cb, tile, corner, playerNum);
		addPlayerDragNums(playerNum, tile, corner, tile, road);
		registerPlayerClick(cb, playerNum);
		Road r = checkPlayerTileForRoad(cb, playerNum, tile, corner, road);
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
	
	private void registerPlayerClick(CatanBoard cb, int player) {
		if (player == 0) {
			cb.locationClicked(tileNums1, corners1);
		} else if (player == 1) {
			cb.locationClicked(tileNums2, corners2);
		} else if (player == 2) {
			cb.locationClicked(tileNums3, corners3);
		} else {
			cb.locationClicked(tileNums4, corners4);
		}
		clearClicks(player);
	}
	
	private void clearClicks(int player) {
		if (player == 0) {
			tileNums1.clear();
			corners1.clear();
		} else if (player == 1) {
			tileNums2.clear();
			corners2.clear();
		} else if (player == 2) {
			tileNums3.clear();
			corners3.clear();
		} else {
			tileNums4.clear();
			corners4.clear();
		}
	}

	private void addPlayerClickNums(int player, int tileNum, int cornerNum) {
		if (player == 0) {
			tileNums1.add(tileNum);
			corners1.add(cornerNum);
		} else if (player == 1) {
			tileNums2.add(tileNum);
			corners2.add(cornerNum);
		} else if (player == 2) {
			tileNums3.add(tileNum);
			corners3.add(cornerNum);
		} else {
			tileNums4.add(tileNum);
			corners4.add(cornerNum);
		}
	}
	
	private void addPlayerDragNums(int player, int tileNum1, int cornerNum1, int tileNum2, int cornerNum2) {
		if (player == 0) {
			tileNums1.add(tileNum1);
			corners1.add(cornerNum1);
			tileNums1.add(-1);
			corners1.add(-1);
			tileNums1.add(tileNum2);
			corners1.add(cornerNum2);
		} else if (player == 1) {
			tileNums2.add(tileNum1);
			corners2.add(cornerNum1);
			tileNums2.add(-1);
			corners2.add(-1);
			tileNums2.add(tileNum2);
			corners2.add(cornerNum2);
		} else if (player == 2) {
			tileNums3.add(tileNum1);
			corners3.add(cornerNum1);
			tileNums3.add(-1);
			corners3.add(-1);
			tileNums3.add(tileNum2);
			corners3.add(cornerNum2);
		} else {
			tileNums4.add(tileNum1);
			corners4.add(cornerNum1);
			tileNums4.add(-1);
			corners4.add(-1);
			tileNums4.add(tileNum2);
			corners4.add(cornerNum2);
		}
	}

}
