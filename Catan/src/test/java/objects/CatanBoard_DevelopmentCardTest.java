package objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CatanBoard_DevelopmentCardTest extends CatanBoardTest {

	@BeforeEach
	public void developmentCardSetup() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
	}

	@Test
	public void testBuyDevelopmentCard_WithEnoughResources() {
		pc.getCurrentPlayer().addResource(TileType.ore, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);

		assertTrue(cb.buyDevelopmentCard());
		cb.endTurnAndRoll();
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		int totalDevCards = 0;
		for (Stack<DevelopmentCard> devCardStack : pc.getCurrentPlayer().developmentCards.values()) {
			totalDevCards += devCardStack.size();
		}
		assertTrue(totalDevCards == 1);
	}

	@Test
	public void testBuyDevelopmentCard_WithoutEnoughResources_NotEnoughOre() {
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);

		assertTrue(!cb.buyDevelopmentCard());
		cb.endTurnAndRoll();
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		int totalDevCards = 0;
		for (Stack<DevelopmentCard> devCardStack : pc.getCurrentPlayer().developmentCards.values()) {
			totalDevCards += devCardStack.size();
		}
		assertTrue(totalDevCards == 0);
	}

	@Test
	public void testBuyDevelopmentCard_WithoutEnoughResources_NotEnoughWool() {
		pc.getCurrentPlayer().addResource(TileType.ore, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);

		assertTrue(!cb.buyDevelopmentCard());
		cb.endTurnAndRoll();
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		int totalDevCards = 0;
		for (Stack<DevelopmentCard> devCardStack : pc.getCurrentPlayer().developmentCards.values()) {
			totalDevCards += devCardStack.size();
		}
		assertTrue(totalDevCards == 0);
	}

	@Test
	public void testBuyDevelopmentCard_WithoutEnoughResources_NotEnoughWheat() {
		pc.getCurrentPlayer().addResource(TileType.ore, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);

		assertTrue(!cb.buyDevelopmentCard());
		cb.endTurnAndRoll();
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		int totalDevCards = 0;
		for (Stack<DevelopmentCard> devCardStack : pc.getCurrentPlayer().developmentCards.values()) {
			totalDevCards += devCardStack.size();
		}
		assertTrue(totalDevCards == 0);
	}

	@Test
	public void testBuyDevelopmentCard_WithEnoughResourcesSameTurn() {
		pc.getCurrentPlayer().addResource(TileType.ore, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		cb.developmentCards.push(new KnightDevelopmentCard());

		assertTrue(cb.buyDevelopmentCard());
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		int totalDevCards = 0;
		for (Stack<DevelopmentCard> devCardStack : pc.getCurrentPlayer().developmentCards.values()) {
			totalDevCards += devCardStack.size();
		}
		assertTrue(totalDevCards == 0);

		cb.endTurnAndRoll();
		totalDevCards = 0;
		for (Stack<DevelopmentCard> devCardStack : pc.getCurrentPlayer().developmentCards.values()) {
			totalDevCards += devCardStack.size();
		}
		assertTrue(totalDevCards == 1);
	}

	@Test
	public void testBuyDevelopmentCard_VictoryPointCard() {
		pc.getCurrentPlayer().addResource(TileType.ore, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		cb.developmentCards.push(new VictoryPointDevelopmentCard());

		assertTrue(cb.buyDevelopmentCard());
		assertTrue(pc.getCurrentPlayer().getNumberOfVictoryPoints() == 1);
	}

}
