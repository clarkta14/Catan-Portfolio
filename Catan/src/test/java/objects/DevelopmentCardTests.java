package objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DevelopmentCardTests {
    
	@Test
	public void testCreateKnightCard() {
		DevelopmentCard knight = new KnightDevelopmentCard();
		assertEquals(knight.getClass(), KnightDevelopmentCard.class);
	}
	
	@Test
	public void testCreateProgressCard() {
		DevelopmentCard progress = new ProgressDevelopmentCard();
		assertEquals(progress.getClass(), ProgressDevelopmentCard.class);
	}
	
	@Test
	public void testCreateVictoryPointCard() {
		DevelopmentCard progress = new VictoryPointDevelopmentCard();
		assertEquals(progress.getClass(), VictoryPointDevelopmentCard.class);
	}
}
