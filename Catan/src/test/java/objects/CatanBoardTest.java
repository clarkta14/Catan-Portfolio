package objects;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class CatanBoardTest {
	
	// Player numbers
	int player1 = 1;
	int player2 = 2;
	int player3 = 3;
	int player4 = 4;
	
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
		int playerNum = 2; int tileNum = 1; int cornerNum= 0;
		makePlayerClick(playerNum, tileNum, cornerNum);
		
		cb.locationClicked(tileNums2, corners2, playerNum);
		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(tileNum);
		HashMap<Integer, Settlement> settlements = tile.getSettlements();
		
		Settlement s = settlements.get(cornerNum);
		assertEquals(s.getOwner(), cb.players.get(playerNum));
	}

	private void makePlayerClick(int player, int tileNum, int cornerNum) {
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
