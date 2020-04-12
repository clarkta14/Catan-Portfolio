package objects;

import static org.junit.Assert.assertEquals;

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
	public void testNextPlayerSetup() {
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
	public void testNextPlayerAfterSetup() {
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
}
