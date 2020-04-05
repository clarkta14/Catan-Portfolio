package objects;

import static org.junit.Assert.*;

import java.util.ArrayList;

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

}
