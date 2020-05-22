package objects;

import static org.junit.Assert.*;

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
	
	@Test
	public void testGetTotalNumberOfPlayers() {
		PlayersController pc = new PlayersController(4);
		assertEquals(4, pc.getTotalNumOfPlayers());
	}
	
	@Test
	public void testDetermineLargestArmy_PlayerPlaysLTThreeKnightCards() {
		PlayersController pc = new PlayersController(3);
		Player playerOne = pc.getCurrentPlayer();

		playerOne.knightsPlayed++;
		pc.determineLargestArmy(playerOne);
		
		assertEquals(0, pc.getNumKnightsOfLargestArmy());
		assertEquals(0, playerOne.getDevelopmentCardCount(DevelopmentCardType.largest_army_card));
		assertEquals(0, playerOne.getNumberOfVictoryPoints());
	}
	
	@Test
	public void testDetermineLargestArmy_PlayerPlaysThreeKnightCards() {
		PlayersController pc = new PlayersController(3);
		Player playerOne = pc.getCurrentPlayer();
		
		for (int i = 0; i < 3; i++) {
			playerOne.knightsPlayed++;
			pc.determineLargestArmy(playerOne);
		}

		assertEquals(3, pc.getNumKnightsOfLargestArmy());
		assertEquals(1, playerOne.getDevelopmentCardCount(DevelopmentCardType.largest_army_card));
		assertEquals(2, playerOne.getNumberOfVictoryPoints());
	}
	
	@Test
	public void testDetermineLargestArmy_PlayerPlaysGTThreeKnightCards() {
		PlayersController pc = new PlayersController(3);
		Player playerOne = pc.getCurrentPlayer();
		
		for (int i = 0; i < 6; i++) {
			playerOne.knightsPlayed++;
			pc.determineLargestArmy(playerOne);
		}

		assertEquals(6, pc.getNumKnightsOfLargestArmy());
		assertEquals(1, playerOne.getDevelopmentCardCount(DevelopmentCardType.largest_army_card));
		assertEquals(2, playerOne.getNumberOfVictoryPoints());
	}
	
	@Test
	public void testDetermineLargestArmy_PlayerPlaysKnightCardsLTLargestArmy() {
		PlayersController pc = new PlayersController(3);
		Player playerOne = pc.getCurrentPlayer();
		
		for (int i = 0; i < 3; i++) {
			playerOne.knightsPlayed++;
			pc.determineLargestArmy(playerOne);
		}
		
		pc.nextPlayer();
		
		Player playerTwo = pc.getCurrentPlayer();

		playerTwo.knightsPlayed++;
		pc.determineLargestArmy(playerTwo);

		assertEquals(3, pc.getNumKnightsOfLargestArmy());
		assertEquals(1, playerOne.getDevelopmentCardCount(DevelopmentCardType.largest_army_card));
		assertEquals(2, playerOne.getNumberOfVictoryPoints());
		assertEquals(0, playerTwo.getDevelopmentCardCount(DevelopmentCardType.largest_army_card));
		assertEquals(0, playerTwo.getNumberOfVictoryPoints());
	}
	
	@Test
	public void testDetermineLargestArmy_PlayerPlaysKnightCardsEqualLargestArmy() {
		PlayersController pc = new PlayersController(3);
		Player playerOne = pc.getCurrentPlayer();
		
		for (int i = 0; i < 3; i++) {
			playerOne.knightsPlayed++;
			pc.determineLargestArmy(playerOne);
		}
		
		pc.nextPlayer();
		
		Player playerTwo = pc.getCurrentPlayer();

		for (int i = 0; i < 3; i++) {
			playerTwo.knightsPlayed++;
			pc.determineLargestArmy(playerTwo);
		}

		assertEquals(3, pc.getNumKnightsOfLargestArmy());
		assertEquals(1, playerOne.getDevelopmentCardCount(DevelopmentCardType.largest_army_card));
		assertEquals(2, playerOne.getNumberOfVictoryPoints());
		assertEquals(0, playerTwo.getDevelopmentCardCount(DevelopmentCardType.largest_army_card));
		assertEquals(0, playerTwo.getNumberOfVictoryPoints());
	}
	
	@Test
	public void testDetermineLargestArmy_PlayerPlaysKnightCardsGTLargestArmy() {
		PlayersController pc = new PlayersController(3);
		Player playerOne = pc.getCurrentPlayer();
		
		for (int i = 0; i < 3; i++) {
			playerOne.knightsPlayed++;
			pc.determineLargestArmy(playerOne);
		}
		
		pc.nextPlayer();
		
		Player playerTwo = pc.getCurrentPlayer();

		for (int i = 0; i < 4; i++) {
			playerTwo.knightsPlayed++;
			pc.determineLargestArmy(playerTwo);
		}

		assertEquals(4, pc.getNumKnightsOfLargestArmy());
		assertEquals(0, playerOne.getDevelopmentCardCount(DevelopmentCardType.largest_army_card));
		assertEquals(0, playerOne.getNumberOfVictoryPoints());
		assertEquals(1, playerTwo.getDevelopmentCardCount(DevelopmentCardType.largest_army_card));
		assertEquals(2, playerTwo.getNumberOfVictoryPoints());
	}
}
