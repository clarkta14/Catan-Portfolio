package objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DevelopmentCardTests {
    
	@Test
	public void testCreateKnightCard() {
		DevelopmentCard knight = new KnightDevelopmentCard();
		assertEquals(knight.getClass(), KnightDevelopmentCard.class);
	}
}
