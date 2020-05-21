package objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.easymock.EasyMock;
import org.junit.Before;
import gui.GameStates;
import org.junit.Test;

public class CatanBoard_ResourceTest extends CatanBoardTest {

	@Before
	public void setupForResourceTests() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
	}
	
	@Test
	public void testDistributeResources() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		cb.addSettlementToTiles(new ArrayList<>(Arrays.asList(5, 4, 1)), new ArrayList<>(Arrays.asList(0, 4, 2)), GameStates.drop_settlement_setup);
		cb.addSettlementToTiles(new ArrayList<>(Arrays.asList(4, 8, 3)), new ArrayList<>(Arrays.asList(1, 5, 3)), GameStates.drop_settlement_setup);
		cb.addSettlementToTiles(new ArrayList<>(Arrays.asList(18, 17, 14)), new ArrayList<>(Arrays.asList(0, 4, 2)), GameStates.drop_settlement_setup);
	
		cb.distributeResources(cb.getTiles().get(5).getNumber());
		if (cb.getTiles().get(5).getType() != TileType.desert)
			assertTrue(pc.getCurrentPlayer().getResourceCount(cb.getTiles().get(5).getType()) > 0);
		cb.distributeResources(cb.getTiles().get(4).getNumber());
		if (cb.getTiles().get(4).getType() != TileType.desert)
			assertTrue(pc.getCurrentPlayer().getResourceCount(cb.getTiles().get(4).getType()) > 0);
	}
	
	@Test
	public void testDistributeResourcesCity() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		Tile tile = cb.getTiles().get(0);
		int tileNum = 0;
		while (tile.getType() == TileType.desert) {
			tileNum++;
			tile = cb.getTiles().get(tileNum);
		}
		
		cb.addSettlementToTiles(new ArrayList<>(Arrays.asList(tileNum)), new ArrayList<>(Arrays.asList(0)), GameStates.drop_settlement_setup);
		Settlement settlement = cb.getTiles().get(tileNum).getSettlements().get(0);
		settlement.upgradeToCity();
	
		cb.distributeResources(tile.getNumber());
		assertEquals(2, pc.getCurrentPlayer().getResourceCount(tile.getType()));
	}
	
	@Test
	public void testDistributeResources_Robber() {
		int robber = -1;
		for(Tile t : cb.getTiles()) {
			if(t.isRobber()) {
				robber = cb.getTiles().indexOf(t);
			}
		}
		
		cb.addSettlementToTiles(new ArrayList<>(Arrays.asList(robber)), new ArrayList<>(Arrays.asList(0)), GameStates.drop_settlement_setup);
		int notRobber = 5;
		if(notRobber == robber) {
			notRobber = 4;
		}
		
		cb.addSettlementToTiles(new ArrayList<>(Arrays.asList(notRobber)), new ArrayList<>(Arrays.asList(0)), GameStates.drop_settlement_setup);
		cb.addSettlementToTiles(new ArrayList<>(Arrays.asList(robber)), new ArrayList<>(Arrays.asList(0)), GameStates.drop_settlement_setup);
		cb.distributeResources(cb.getTiles().get(robber).getNumber());
		assertTrue(pc.getCurrentPlayer().getResourceCount(cb.getTiles().get(5).getType()) == 0);
		cb.distributeResources(cb.getTiles().get(notRobber).getNumber());
		assertTrue(pc.getCurrentPlayer().getResourceCount(cb.getTiles().get(notRobber).getType()) > 0);
	}

	@Test
	public void testDistributeResourcesOnLastSettlementPlacedSetup() {
		ArrayList<Integer> selectedTiles = new ArrayList<Integer>();
		ArrayList<Integer> corners = new ArrayList<Integer>();

		selectedTiles.add(1);
		corners.add(4);
		selectedTiles.add(2);
		corners.add(0);

		cb.addSettlementToTiles(selectedTiles, corners, GameStates.drop_settlement_setup_final);

		Player currentPlayer = pc.getCurrentPlayer();
		TileType TileType1 = cb.getTiles().get(1).getType();
		TileType TileType2 = cb.getTiles().get(2).getType();
		if (TileType1 == TileType2) {
			if (TileType1 == TileType.desert) {
				assertEquals(0, currentPlayer.getResourceCount(TileType1));
			} else {
				assertEquals(2, currentPlayer.getResourceCount(TileType1));
			}
		} else {
			if (TileType1 == TileType.desert) {
				assertEquals(0, currentPlayer.getResourceCount(TileType1));
			} else {
				assertEquals(1, currentPlayer.getResourceCount(TileType1));
			}
			if (TileType2 == TileType.desert) {
				assertEquals(0, currentPlayer.getResourceCount(TileType2));
			} else {
				assertEquals(1, currentPlayer.getResourceCount(TileType2));
			}
		}

	}

	@Test
	public void testDistributeResourcesOnLastSettlementPlacedSetup1Tile() {
		ArrayList<Integer> selectedTiles = new ArrayList<Integer>();
		ArrayList<Integer> corners = new ArrayList<Integer>();

		selectedTiles.add(2);
		corners.add(4);

		cb.addSettlementToTiles(selectedTiles, corners, GameStates.drop_settlement_setup_final);

		Player currentPlayer = pc.getCurrentPlayer();
		TileType TileType2 = cb.getTiles().get(2).getType();
		if (TileType2 == TileType.desert) {
			assertEquals(0, currentPlayer.getResourceCount(TileType2));
		} else {
			assertEquals(1, currentPlayer.getResourceCount(TileType2));
		}

	}

	@Test
	public void testStealRandomResourceFromOpposingPlayerWithNoResources() {
		Random random = EasyMock.mock(Random.class);

		EasyMock.expect(random.nextInt(TileType.values().length)).andStubReturn(TileType.brick.ordinal());

		EasyMock.replay(random);

		cb.stealRandomResourceFromOpposingPlayer(pc.getCurrentPlayer(), pc.getPlayer(1));

		EasyMock.verify(random);

		assertEquals(0, pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(0, pc.getPlayer(1).getResourceCount(TileType.brick));
	}

	@Test
	public void testStealRandomResourceFromOpposingPlayerWithOneResource() {
		Random random = EasyMock.mock(Random.class);

		EasyMock.expect(random.nextInt(TileType.values().length)).andStubReturn(TileType.brick.ordinal());

		EasyMock.replay(random);

		pc.getPlayer(1).addResource(TileType.brick, 1);
		cb.stealRandomResourceFromOpposingPlayer(pc.getCurrentPlayer(), pc.getPlayer(1));

		EasyMock.verify(random);

		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(0, pc.getPlayer(1).getResourceCount(TileType.brick));
	}

	@Test
	public void testStealRandomResourceFromOpposingPlayerWithTwoResources() {
		Random random = EasyMock.mock(Random.class);

		EasyMock.expect(random.nextInt(TileType.values().length)).andStubReturn(TileType.brick.ordinal());

		EasyMock.replay(random);

		pc.getPlayer(1).addResource(TileType.brick, 2);
		cb.stealRandomResourceFromOpposingPlayer(pc.getCurrentPlayer(), pc.getPlayer(1));

		EasyMock.verify(random);

		assertEquals(1, pc.getCurrentPlayer().getResourceCount(TileType.brick));
		assertEquals(1, pc.getPlayer(1).getResourceCount(TileType.brick));
	}

}
