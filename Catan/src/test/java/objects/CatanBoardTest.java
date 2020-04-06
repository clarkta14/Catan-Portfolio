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
	public void testBoardInitSetup3Player() {
		CatanBoard cb = new CatanBoard(3);
		int p1tile = 1; int p1corner = 1;
		int p2tile = 5; int p2corner = 5;
		int p3tile = 15; int p3corner = 3;
		
		addPlayerClickNums(player1, p1tile, p1corner);
		addPlayerClickNums(player2, p2tile, p2corner);
		addPlayerClickNums(player3, p3tile, p3corner);
		
		registerPlayerClick(cb, player1);
		registerPlayerClick(cb, player2);
		registerPlayerClick(cb, player3);
		
		checkPlayerTile(cb, p1tile, p1corner, player1);
		checkPlayerTile(cb, p2tile, p2corner, player2);
		checkPlayerTile(cb, p3tile, p3corner, player3);
	}

	private void checkPlayerTile(CatanBoard cb, int tileNum, int cornerNum, int playerNum) {
		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(tileNum);
		HashMap<Integer, Settlement> settlements = tile.getSettlements();
		Settlement s = settlements.get(cornerNum);
		assertEquals(s.getOwner(), cb.players.get(playerNum));
	}
	
	private void registerPlayerClick(CatanBoard cb, int player) {
		if (player == 1) {
			cb.locationClicked(tileNums1, corners1);
		} else if (player == 2) {
			cb.locationClicked(tileNums2, corners2);
		} else if (player == 3) {
			cb.locationClicked(tileNums3, corners3);
		} else {
			cb.locationClicked(tileNums4, corners4);
		}
	}

	private void addPlayerClickNums(int player, int tileNum, int cornerNum) {
		if (player == 1) {
			tileNums1.add(tileNum);
			corners1.add(cornerNum);
		} else if (player == 2) {
			tileNums2.add(tileNum);
			corners2.add(cornerNum);
		} else if (player == 3) {
			tileNums3.add(tileNum);
			corners3.add(cornerNum);
		} else {
			tileNums4.add(tileNum);
			corners4.add(cornerNum);
		}
	}

}
