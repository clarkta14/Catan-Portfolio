package objects;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

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
		assertEquals(DevelopmentCardType.monopoly_card, monopolyCard.getDevelopmentCardType());
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

	@Test
	public void testPlayMonopolyCard() {
		PlayersController pc = new PlayersController(3);
		MonopolyCard monopolyCard = new MonopolyCard(pc);
		Player p1 = pc.getPlayer(0);
		Player p2 = pc.getPlayer(1);
		Player p3 = pc.getPlayer(2);

		pc.nextPlayer();
		add2OfAllResourcesToAllPlayers(p1, p2, p3);
		monopolyCard.playCard(TileType.brick);

		assertEquals(0, p1.getResourceCount(TileType.brick));
		assertEquals(6, p2.getResourceCount(TileType.brick));
		assertEquals(0, p3.getResourceCount(TileType.brick));

		ArrayList<TileType> typesToCheck = new ArrayList<TileType>();
		typesToCheck.add(TileType.wool);
		typesToCheck.add(TileType.wood);
		typesToCheck.add(TileType.wheat);
		typesToCheck.add(TileType.ore);
		for (TileType type : typesToCheck) {
			assertEquals(2, p1.getResourceCount(type));
			assertEquals(2, p2.getResourceCount(type));
			assertEquals(2, p3.getResourceCount(type));
		}
	}

	private void add2OfAllResourcesToAllPlayers(Player p1, Player p2, Player p3) {
		p1.addResource(TileType.wool, 2);
		p2.addResource(TileType.wool, 2);
		p3.addResource(TileType.wool, 2);

		p1.addResource(TileType.wood, 2);
		p2.addResource(TileType.wood, 2);
		p3.addResource(TileType.wood, 2);

		p1.addResource(TileType.wheat, 2);
		p2.addResource(TileType.wheat, 2);
		p3.addResource(TileType.wheat, 2);

		p1.addResource(TileType.ore, 2);
		p2.addResource(TileType.ore, 2);
		p3.addResource(TileType.ore, 2);

		p1.addResource(TileType.brick, 2);
		p2.addResource(TileType.brick, 2);
		p3.addResource(TileType.brick, 2);
	}
}
