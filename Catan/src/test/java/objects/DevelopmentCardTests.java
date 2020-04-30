package objects;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

public class DevelopmentCardTests {
    
	@Test
	public void testCreateKnightCard() {
		DevelopmentCard knight = new KnightDevelopmentCard();
		assertEquals(knight.getDevelopmentCardType(), DevelopmentCardType.knight);
	}
	
	@Test
	public void testCreateProgressCard() {
		DevelopmentCard progress = new ProgressDevelopmentCard();
		assertEquals(progress.getDevelopmentCardType(), DevelopmentCardType.progress);
	}
	
	@Test
	public void testCreateVictoryPointCard() {
		DevelopmentCard victoryPoint = new VictoryPointDevelopmentCard();
		assertEquals(victoryPoint.getDevelopmentCardType(), DevelopmentCardType.victory_point);
	}
	
	@Test
	public void testCreateMonopolyCard() {
		PlayersController pc = new PlayersController(3);
		DevelopmentCard monopolyCard = new MonopolyCard(pc);
		assertEquals( DevelopmentCardType.monopoly_card, monopolyCard.getDevelopmentCardType());
	}
	
	@Test
	public void testPlayMonopolyCardWrongCall() {
		PlayersController pc = new PlayersController(3);
		DevelopmentCard monopolyCard = new MonopolyCard(pc);
		try {
			monopolyCard.playCard();
			Assert.fail("Should throw UOE");
		} catch (UnsupportedOperationException e) {
			assertEquals("Monopoly card needs a resource type", e.getMessage());
		}
	}
}
