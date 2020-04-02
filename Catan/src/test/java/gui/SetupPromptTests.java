package gui;

import static org.junit.Assert.*;

import org.junit.Test;

public class SetupPromptTests {

	@Test
	public void constructSetupWindow() {
		SetupPrompt setupWindow = new SetupPrompt();
	}
	
	@Test
	public void testThreePlayers() {
		testPlayerNumNormal("3");
	}
	
	@Test
	public void testFourPlayers() {
		testPlayerNumNormal("4");
	}
	
	@Test
	public void testLessThanThreePlayers() {
		testPlayerNumError(String.valueOf("2"));
	}
	
	@Test
	public void testGreaterThanFourPlayers() {
		testPlayerNumError(String.valueOf("5"));
	}
	
	@Test
	public void testMaxIntPlayers() {
		testPlayerNumError(String.valueOf(Integer.MAX_VALUE));
	}
	
	@Test
	public void testMinIntPlayers() {
		testPlayerNumError(String.valueOf(Integer.MIN_VALUE));
	}
	
	@Test
	public void testNonNumericalForPlayerNum() {
		testPlayerNumError("henlo wurld");
	}
	
	private void testPlayerNumError(String input) {
		try {
			SetupPrompt catanSetup = new SetupPrompt();
			catanSetup.getPlayerNum(input);
			fail("Did not throw IAE Exception");
		} catch(IllegalArgumentException e) {
			// pass
		}
	}
	
	private void testPlayerNumNormal(String input) {
		SetupPrompt catanSetup = new SetupPrompt();
		int players = catanSetup.getPlayerNum(input);
		assertEquals(Integer.parseInt(input), players);
	}
}
