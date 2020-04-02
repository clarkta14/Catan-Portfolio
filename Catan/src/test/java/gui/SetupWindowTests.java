package gui;

import static org.junit.Assert.*;

import org.junit.Test;

public class SetupWindowTests {

	@Test
	public void constructSetupWindow() {
		SetupWindow setupWindow = new SetupWindow();
	}
	
	@Test
	public void testThreePlayers() {
		SetupWindow catanSetup = new SetupWindow();
		String three = "3";
		int players = catanSetup.getPlayerNum(three);
		assertEquals(3, players);
	}
	
	@Test
	public void testFourPlayers() {
		SetupWindow catanSetup = new SetupWindow();
		String four = "4";
		int players = catanSetup.getPlayerNum(four);
		assertEquals(4, players);
	}
	
	@Test
	public void testLessThanThreePlayers() {
		try {
			SetupWindow catanSetup = new SetupWindow();
			String two = "2";
			int players = catanSetup.getPlayerNum(two);
			fail("Did not throw IAE Exception");
		} catch(IllegalArgumentException e) {
			// pass
		}
	}
	
	@Test
	public void testGreaterThanFourPlayers() {
		try {
			SetupWindow catanSetup = new SetupWindow();
			String five = "5";
			int players = catanSetup.getPlayerNum(five);
			fail("Did not throw IAE Exception");
		} catch(IllegalArgumentException e) {
			// pass
		}
	}
	
	@Test
	public void testNonNumericalForPlayerNum() {
		try {
			SetupWindow catanSetup = new SetupWindow();
			String nonNum = "henlo wurld";
			int players = catanSetup.getPlayerNum(nonNum);
			fail("Did not throw IAE Exception");
		} catch(IllegalArgumentException e) {
			// pass
		}
	}
	
	@Test
	public void testCreatePlayer() {
		SetupWindow catanSetup = new SetupWindow();
		String player1 = "John Doe";
		catanSetup.createPlayer(player1);
		assertEquals(player1, catanSetup.playerNames.get(0));
	}

	

}
