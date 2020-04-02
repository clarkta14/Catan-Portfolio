package gui;

import static org.junit.Assert.*;

import org.junit.Test;

public class SetupWindowTests {

	@Test
	public void constructSetupWindow() {
		SetupPrompt setupWindow = new SetupPrompt();
	}
	
	@Test
	public void testThreePlayers() {
		SetupPrompt catanSetup = new SetupPrompt();
		String three = "3";
		int players = catanSetup.getPlayerNum(three);
		assertEquals(3, players);
	}
	
	@Test
	public void testFourPlayers() {
		SetupPrompt catanSetup = new SetupPrompt();
		String four = "4";
		int players = catanSetup.getPlayerNum(four);
		assertEquals(4, players);
	}
	
	@Test
	public void testLessThanThreePlayers() {
		try {
			SetupPrompt catanSetup = new SetupPrompt();
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
			SetupPrompt catanSetup = new SetupPrompt();
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
			SetupPrompt catanSetup = new SetupPrompt();
			String nonNum = "henlo wurld";
			int players = catanSetup.getPlayerNum(nonNum);
			fail("Did not throw IAE Exception");
		} catch(IllegalArgumentException e) {
			// pass
		}
	}
}
