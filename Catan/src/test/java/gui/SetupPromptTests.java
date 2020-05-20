package gui;

import static org.junit.Assert.*;

import org.junit.Test;

public class SetupPromptTests {
	
	@Test
	public void getNumPlayersNotInitialized() {
		SetupPrompt setupWindow = new SetupPrompt();
		try {
			setupWindow.getNumPlayers();
			fail("Did not throw IAE Exception");
		} catch(IllegalArgumentException e) {
			
		}
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
			catanSetup.setNumPlayers(input);
			fail("Did not throw IAE Exception");
		} catch(IllegalArgumentException e) {
			
		}
	}
	
	private void testPlayerNumNormal(String input) {
		SetupPrompt catanSetup = new SetupPrompt();
		catanSetup.setNumPlayers(input);
		assertEquals(Integer.parseInt(input), catanSetup.getNumPlayers());
	}
}