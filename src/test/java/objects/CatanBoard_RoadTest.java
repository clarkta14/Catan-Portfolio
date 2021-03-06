package objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.Test;
import org.junit.Before;
import gui.GameStates;

public class CatanBoard_RoadTest extends CatanBoardTest {
	
	@Before
	public void basicSetupRoad() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		tileToCorners.clear();
		tileToRoadOrientation.clear();
	}
	
	@Test
	public void testPlaceRoadNotEnoughResources() {
		int p1tile = 1; int p1corner = 1;
		int p1roadTile = 1; int p1road1 = 1; int p1road2 = 2;

		addPlayerClickNums(p1tile, p1corner);
		registerPlayerClick();
		checkPlayerTileForSettlement(p1tile, p1corner, player1);
		
		addPlayerDragNums(p1roadTile, p1road1, p1road2);
		boolean result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertFalse(result);
	}
	
	@Test
	public void testPlaceRoadNotValidPlacement() {
		addResourcesForSettlements(pc.getCurrentPlayer(), 1);
		addResourcesForRoad(pc.getCurrentPlayer(), 2);
		
		addPlayerClickNums(4, 0);
		registerPlayerClick();
		checkPlayerTileForSettlement(4, 0, pc.getCurrentPlayerNum());
		
		addPlayerDragNums(4, 0, 5);
		boolean result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		addPlayerDragNums(4, 5, 0);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertFalse(result);
	}

	@Test
	public void testPlaceRoadSetupNotValid() {
		int p1tile = 1; int p1corner = 1;
		int p1roadTile = 1; int p1road1 = 1; int p1road2 = 2;

		// Place Settlement
		addPlayerClickNums(p1tile, p1corner);
		registerPlayerClick();
		checkPlayerTileForSettlement(p1tile, p1corner, player1);
		
		// Try to Place Road
		addPlayerDragNums(p1roadTile, p1road1, p1road2);
		boolean result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road_setup);
		assertTrue(result);
		
		addPlayerDragNums(p1roadTile, 2, 3);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road_setup);
		assertFalse(result);
	}
	
	@Test
	public void testPlaceRoadNotValid() {
		int p1tile = 1; int p1corner = 1;
		int p1roadTile = 1;

		// Place Settlement
		addPlayerClickNums(p1tile, p1corner);
		registerPlayerClick();
		checkPlayerTileForSettlement(p1tile, p1corner, player1);
		
		// Try to Place Road
		addPlayerDragNums(p1roadTile, 2, 3);
		boolean result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road_setup);
		assertFalse(result);
	}
	
	@Test
	public void testLongestRoadVictoryPoints_continuous5() {
		addResourcesForSettlements(pc.getCurrentPlayer(), 1);
		addResourcesForRoad(pc.getCurrentPlayer(), 5);

		addPlayerClickNums(4, 0);
		registerPlayerClick();
		checkPlayerTileForSettlement(4, 0, pc.getCurrentPlayerNum());
		
		addPlayerDragNums(4, 0, 5);
		boolean result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		addPlayerDragNums(4, 5, 4);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		addPlayerDragNums(4, 4, 3);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		addPlayerDragNums(4, 3, 2);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		addPlayerDragNums(4, 2, 1);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		assertEquals(3, pc.getCurrentPlayer().getNumberOfVictoryPoints());
	}
	
	@Test
	public void testLongestRoadVictoryPoints_continuous5_2Players() {
		addResourcesForSettlements(pc.getCurrentPlayer(), 1);
		addResourcesForRoad(pc.getCurrentPlayer(), 5);

		addPlayerClickNums(4, 0);
		registerPlayerClick();
		checkPlayerTileForSettlement(4, 0, pc.getCurrentPlayerNum());
		
		addPlayerDragNums(4, 0, 5);
		boolean result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		addPlayerDragNums(4, 5, 4);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		addPlayerDragNums(4, 4, 3);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		addPlayerDragNums(4, 3, 2);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		addPlayerDragNums(4, 2, 1);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		assertEquals(3, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		
		pc.nextPlayer();
		
		addResourcesForSettlements(pc.getCurrentPlayer(), 1);
		addResourcesForRoad(pc.getCurrentPlayer(), 6);

		addPlayerClickNums(13, 0);
		registerPlayerClick();
		checkPlayerTileForSettlement(13, 0, pc.getCurrentPlayerNum());
		
		addPlayerDragNums(13, 0, 5);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		addPlayerDragNums(13, 5, 4);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		addPlayerDragNums(13, 4, 3);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		addPlayerDragNums(13, 3, 2);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		addPlayerDragNums(13, 2, 1);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		assertEquals(1, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		
		addPlayerDragNums(13, 1, 0);
		result = cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road);
		assertTrue(result);
		clearDrags();
		
		assertEquals(3, pc.getCurrentPlayer().getNumberOfVictoryPoints());
		
		assertEquals(1, pc.getPlayer(pc.getCurrentPlayerNum() - 1 ).getNumberOfVictoryPoints());
	}

}
