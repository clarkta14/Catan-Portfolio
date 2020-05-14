package objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CatanBoard_TradingTest extends CatanBoardTest {

	@BeforeEach
	private void tradingBasicSetup() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
	}

	@Test
	public void testTradeWithBank_4DifferentResourcesAsPayment() {
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.ore, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> payment = new HashMap<>();
		payment.put(TileType.brick, 1);
		payment.put(TileType.ore, 1);
		payment.put(TileType.wool, 1);
		payment.put(TileType.wheat, 1);
		assertTrue(cb.tradeWithBank(payment, TileType.wood));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wood));
	}

	@Test
	public void testTradeWithBank_3DifferentResourcesAsPayment() {
		pc.getCurrentPlayer().addResource(TileType.brick, 2);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> payment = new HashMap<>();
		payment.put(TileType.brick, 2);
		payment.put(TileType.wool, 1);
		payment.put(TileType.wheat, 1);
		assertTrue(cb.tradeWithBank(payment, TileType.ore));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
	}

	@Test
	public void testTradeWithBank_3DifferentResourcesAsPayment_ForSameType() {
		pc.getCurrentPlayer().addResource(TileType.brick, 2);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> payment = new HashMap<>();
		payment.put(TileType.brick, 2);
		payment.put(TileType.wool, 1);
		payment.put(TileType.wheat, 1);
		assertTrue(!cb.tradeWithBank(payment, TileType.brick));
		assertEquals(2, pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
	}

	@Test
	public void testTradeWithBank_NotEnoughResources() {
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> payment = new HashMap<>();
		payment.put(TileType.brick, 2);
		payment.put(TileType.wool, 1);
		payment.put(TileType.wheat, 1);
		assertTrue(!cb.tradeWithBank(payment, TileType.ore));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
	}

	@Test
	public void testTradeWithBank_NotEnoughResourcesPayed() {
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> payment = new HashMap<>();
		payment.put(TileType.brick, 1);
		payment.put(TileType.wool, 1);
		payment.put(TileType.wheat, 1);
		assertTrue(!cb.tradeWithBank(payment, TileType.ore));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
	}

	@Test
	public void testTradeWithPlayer_SuccessfulTrade() {
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> giveThese = new HashMap<>();
		giveThese.put(TileType.brick, 1);
		giveThese.put(TileType.wool, 1);
		giveThese.put(TileType.wheat, 1);

		pc.getPlayer(1).addResource(TileType.ore, 2);
		pc.getPlayer(1).addResource(TileType.wool, 1);
		pc.getPlayer(1).addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> forThese = new HashMap<>();
		forThese.put(TileType.ore, 1);

		assertTrue(cb.tradeWithPlayer(1, giveThese, forThese));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.ore));

		assertEquals(1, pc.getPlayer(1).getResourceCount(TileType.brick));
		assertEquals(2, pc.getPlayer(1).getResourceCount(TileType.wool));
		assertEquals(2, pc.getPlayer(1).getResourceCount(TileType.wheat));
		assertEquals(1, pc.getPlayer(1).getResourceCount(TileType.ore));
	}

	@Test
	public void testTradeWithPlayer_SuccessfulTradePaying0() {
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> giveThese = new HashMap<>();

		pc.getPlayer(1).addResource(TileType.ore, 2);
		pc.getPlayer(1).addResource(TileType.wool, 1);
		pc.getPlayer(1).addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> forThese = new HashMap<>();
		forThese.put(TileType.ore, 1);

		assertTrue(cb.tradeWithPlayer(1, giveThese, forThese));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.ore));

		assertEquals(0, pc.getPlayer(1).getResourceCount(TileType.brick));
		assertEquals(1, pc.getPlayer(1).getResourceCount(TileType.wool));
		assertEquals(1, pc.getPlayer(1).getResourceCount(TileType.wheat));
		assertEquals(1, pc.getPlayer(1).getResourceCount(TileType.ore));
	}

	@Test
	public void testTradeWithPlayer_PlayerDoesNotHaveEnoughResourcesForBuying() {
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> giveThese = new HashMap<>();
		giveThese.put(TileType.brick, 1);
		giveThese.put(TileType.wool, 1);
		giveThese.put(TileType.wheat, 1);

		pc.getPlayer(1).addResource(TileType.ore, 2);
		pc.getPlayer(1).addResource(TileType.wool, 1);
		pc.getPlayer(1).addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> forThese = new HashMap<>();
		forThese.put(TileType.ore, 1);

		assertTrue(!cb.tradeWithPlayer(1, giveThese, forThese));

		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.brick));

		assertEquals(0, pc.getPlayer(1).getResourceCount(TileType.brick));
		assertEquals(1, pc.getPlayer(1).getResourceCount(TileType.wool));
		assertEquals(1, pc.getPlayer(1).getResourceCount(TileType.wheat));
		assertEquals(2, pc.getPlayer(1).getResourceCount(TileType.ore));
	}

	@Test
	public void testTradeWithPlayer_PlayerDoesNotHaveEnoughResourcesForSelling() {
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> giveThese = new HashMap<>();
		giveThese.put(TileType.brick, 1);
		giveThese.put(TileType.wool, 1);
		giveThese.put(TileType.wheat, 1);

		pc.getPlayer(1).addResource(TileType.ore, 2);
		pc.getPlayer(1).addResource(TileType.wool, 1);
		pc.getPlayer(1).addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> forThese = new HashMap<>();
		forThese.put(TileType.ore, 3);

		assertTrue(!cb.tradeWithPlayer(1, giveThese, forThese));

		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.brick));

		assertEquals(0, pc.getPlayer(1).getResourceCount(TileType.brick));
		assertEquals(1, pc.getPlayer(1).getResourceCount(TileType.wool));
		assertEquals(1, pc.getPlayer(1).getResourceCount(TileType.wheat));
		assertEquals(2, pc.getPlayer(1).getResourceCount(TileType.ore));
	}

	@Test
	public void testTradeWithPlayer_TradeWithNonExistantPlayer() {
		pc.getCurrentPlayer().addResource(TileType.brick, 1);
		pc.getCurrentPlayer().addResource(TileType.wool, 1);
		pc.getCurrentPlayer().addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> giveThese = new HashMap<>();
		giveThese.put(TileType.brick, 1);
		giveThese.put(TileType.wool, 1);
		giveThese.put(TileType.wheat, 1);

		pc.getPlayer(1).addResource(TileType.ore, 2);
		pc.getPlayer(1).addResource(TileType.wool, 1);
		pc.getPlayer(1).addResource(TileType.wheat, 1);
		HashMap<TileType, Integer> forThese = new HashMap<>();
		forThese.put(TileType.ore, 3);

		assertTrue(!cb.tradeWithPlayer(4, giveThese, forThese));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wool));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.wheat));
		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.ore));
		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.brick));

		assertEquals(0, pc.getPlayer(1).getResourceCount(TileType.brick));
		assertEquals(1, pc.getPlayer(1).getResourceCount(TileType.wool));
		assertEquals(1, pc.getPlayer(1).getResourceCount(TileType.wheat));
		assertEquals(2, pc.getPlayer(1).getResourceCount(TileType.ore));
	}
}
