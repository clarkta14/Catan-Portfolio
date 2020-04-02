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
		String input = "3";
		int players = catanSetup.getPlayerNum(input);
		assertEquals(3, players);
	}
	
	@Test
	public void testFourPlayers() {
		SetupPrompt catanSetup = new SetupPrompt();
		String input = "4";
		int players = catanSetup.getPlayerNum(input);
		assertEquals(4, players);
	}
	
	@Test
	public void testLessThanThreePlayers() {
		try {
			SetupPrompt catanSetup = new SetupPrompt();
			String input = "2";
			catanSetup.getPlayerNum(input);
			fail("Did not throw IAE Exception");
		} catch(IllegalArgumentException e) {
			// pass
		}
	}
	
	@Test
	public void testGreaterThanFourPlayers() {
		try {
			SetupPrompt catanSetup = new SetupPrompt();
			String input = "5";
			catanSetup.getPlayerNum(input);
			fail("Did not throw IAE Exception");
		} catch(IllegalArgumentException e) {
			// pass
		}
	}
	
	@Test
	public void testMaxIntPlayers() {
		try {
			SetupPrompt catanSetup = new SetupPrompt();
			String input = String.valueOf(Integer.MAX_VALUE);
			catanSetup.getPlayerNum(input);
			fail("Did not throw IAE Exception");
		} catch(IllegalArgumentException e) {
			// pass
		}
	}
	
	@Test
	public void testMinIntPlayers() {
		try {
			SetupPrompt catanSetup = new SetupPrompt();
			String input = String.valueOf(Integer.MIN_VALUE);
			catanSetup.getPlayerNum(input);
			fail("Did not throw IAE Exception");
		} catch(IllegalArgumentException e) {
			// pass
		}
	}
	
	@Test
	public void testNonNumericalForPlayerNum() {
		try {
			SetupPrompt catanSetup = new SetupPrompt();
			String input = "henlo wurld";
			catanSetup.getPlayerNum(input);
			fail("Did not throw IAE Exception");
		} catch(IllegalArgumentException e) {
			// pass
		}
	}
}
