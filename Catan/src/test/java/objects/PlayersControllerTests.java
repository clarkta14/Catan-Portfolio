package objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PlayersControllerTests {

	@Test
	public void testCreatePlayers4() {
		PlayersController pc = new PlayersController(4);
		assertEquals(0, pc.getCurrentPlayerNum());
	}
	
	@Test
	public void testCreatePlayers3() {
		PlayersController pc = new PlayersController(3);
		assertEquals(0, pc.getCurrentPlayerNum());
	}
	
	@Test
	public void testNextPlayerSetup_4Players() {
		PlayersController pc = new PlayersController(4);
		assertEquals(0, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		assertEquals(1, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		pc.nextPlayer();
		assertEquals(3, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		assertEquals(3, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		assertEquals(2, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		pc.nextPlayer();
		assertEquals(0, pc.getCurrentPlayerNum());
	}
	
	@Test
	public void testNextPlayerAfterSetup_4Players() {
		PlayersController pc = new PlayersController(4);
		// setup phase
		for(int i = 0; i < 8; i++) {
			pc.nextPlayer();
		}
		assertEquals(0, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		assertEquals(1, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		pc.nextPlayer();
		assertEquals(3, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		assertEquals(0, pc.getCurrentPlayerNum());
	}
	
	@Test
	public void testNextPlayerSetup_3Players() {
		PlayersController pc = new PlayersController(3);
		assertEquals(0, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		assertEquals(1, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		assertEquals(2, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		assertEquals(2, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		assertEquals(1, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		assertEquals(0, pc.getCurrentPlayerNum());
	}
	
	@Test
	public void testNextPlayerAfterSetup_3Players() {
		PlayersController pc = new PlayersController(3);
		// setup phase
		for(int i = 0; i < 6; i++) {
			pc.nextPlayer();
		}
		assertEquals(0, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		assertEquals(1, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		assertEquals(2, pc.getCurrentPlayerNum());
		pc.nextPlayer();
		assertEquals(0, pc.getCurrentPlayerNum());
	}
	
	@Test
	public void testIsInitialSetup3Players() {
		PlayersController pc = new PlayersController(3);
		for(int i = 0; i < 6; i++) {
			assertTrue(pc.isInitialSetup());
			pc.nextPlayer();
		}
		assertFalse(pc.isInitialSetup());
	}
	
	@Test
	public void testIsInitialSetup4Players() {
		PlayersController pc = new PlayersController(4);
		for(int i = 0; i < 8; i++) {
			assertTrue(pc.isInitialSetup());
			pc.nextPlayer();
		}
		assertFalse(pc.isInitialSetup());
	}
	
	@Test
	public void testIsBackwardsSetup3Players() {
		PlayersController pc = new PlayersController(3);
		for(int i = 0; i < 6; i++) {
			if (i > 2) {
				assertTrue(pc.isBackwardsSetup());
			} else {
				assertFalse(pc.isBackwardsSetup());
			}
			pc.nextPlayer();
		}
	}
	
	@Test
	public void testIsBackwardsSetup4Players() {
		PlayersController pc = new PlayersController(4);
		for(int i = 0; i < 8; i++) {
			if (i > 3) {
				assertTrue(pc.isBackwardsSetup());
			} else {
				assertFalse(pc.isBackwardsSetup());
			}
			pc.nextPlayer();
		}
	}
}
