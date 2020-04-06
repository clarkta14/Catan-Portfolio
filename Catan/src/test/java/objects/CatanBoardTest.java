package objects;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class CatanBoardTest {

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
		ArrayList<Integer> tileNums = new ArrayList<Integer>();
		ArrayList<Integer> corners = new ArrayList<Integer>();
		tileNums.add(tileNum);
		corners.add(cornerNum);
		
		cb.locationClicked(tileNums, corners, playerNum);
		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(tileNum);
		HashMap<Integer, Settlement> settlements = tile.getSettlements();
		
		Settlement s = settlements.get(cornerNum);
		assertEquals(s.getOwner(), cb.players.get(playerNum));
	}

}
