package objects;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import org.easymock.EasyMock;
import org.junit.Test;

import gui.GameStates;

public class CatanBoardTest {
	
	int player1 = 0;
	int player2 = 1;
	int player3 = 2;
	int player4 = 3;
	
	PlayersController pc;
	CatanBoard cb;
	
	ArrayList<Integer> tileNums = new ArrayList<Integer>();
	ArrayList<Integer> cornerNums = new ArrayList<Integer>();
	
	HashMap<Integer, ArrayList<Integer>> tileToCorners = new HashMap<Integer, ArrayList<Integer>>();
	HashMap<Integer, Integer> tileToRoadOrientation = new HashMap<Integer, Integer>();
	

	@Test
	public void testConstruct4Player() {
		pc = new PlayersController(4);
		cb = new CatanBoard(pc);
		assertEquals(cb.getTiles().size(), 19);
		
		ArrayList<Integer> numbers = new ArrayList<>();
		for (Tile t: cb.getTiles()) {
			numbers.add(t.getNumber());
		}
		
		numbers.remove(Integer.valueOf(2));
		numbers.remove(Integer.valueOf(12));
		for(int i = 3; i <= 11; i++) {
			numbers.remove(Integer.valueOf(i));
			numbers.remove(Integer.valueOf(i));
		}
		
		assertEquals(0, numbers.size());
		
		ArrayList<TileType> types = new ArrayList<>();
		for (Tile t: cb.getTiles()) {
			types.add(t.getType());
		}
		
		types.remove(TileType.desert);
		for(int i = 0; i < 4; i++) {
            types.remove(TileType.wool);
            types.remove(TileType.wheat);
            types.remove(TileType.wood);
        }
		for(int i = 0; i < 3; i++) {
            types.remove(TileType.brick);
            types.remove(TileType.ore);
        }
		
		assertEquals(0, types.size());
		
		ArrayList<Point> positionsExpected = new ArrayList<>();
		for(int x = 1; x < 6; x++){
            int ylow = -1;
            int yhigh = -1;
            switch (x) {
                case 1:
                    ylow = 1;
                    yhigh = 4;
                    break;
                case 2:
                    ylow = 1;
                    yhigh = 5;
                    break;
                case 3:
                    ylow = 1;
                    yhigh = 6;
                    break;
                case 4:
                    ylow = 2;
                    yhigh = 6;
                    break;
                case 5:
                    ylow = 3;
                    yhigh = 6;
                    break;
            }
            
            for(int y = ylow; y < yhigh; y++){
                positionsExpected.add(new Point(x,y));
            }
		}
		
		ArrayList<Point> positionsActual = new ArrayList<>();
		for (Tile t: cb.getTiles()) {
			positionsActual.add(t.getLocation());
		}
		
		for(Point p: positionsExpected) {
			positionsActual.remove(p);
		}
		
		assertEquals(0, positionsActual.size());
		
		ArrayList<PortType> portTypesExpected = new ArrayList<PortType>();
    	for (int i = 0; i < 4; i++) {
    		portTypesExpected.add(PortType.three);
    	}
    	portTypesExpected.add(PortType.brick);
    	portTypesExpected.add(PortType.wool);
    	portTypesExpected.add(PortType.wood);
    	portTypesExpected.add(PortType.ore);
    	portTypesExpected.add(PortType.wheat);
    	for(PortType p: portTypesExpected) {
    		cb.portTypes.remove(p);
    	}
    	assertEquals(0, cb.portTypes.size());
    	
    	HashMap<TileType, PortType> resourceToPortsExpected = new HashMap<>();
    	resourceToPortsExpected.put(TileType.brick, PortType.brick);
    	resourceToPortsExpected.put(TileType.wool, PortType.wool);
    	resourceToPortsExpected.put(TileType.wood, PortType.wood);
    	resourceToPortsExpected.put(TileType.ore, PortType.ore);
    	resourceToPortsExpected.put(TileType.wheat, PortType.wheat);
    	for(TileType t: resourceToPortsExpected.keySet()) {
    		cb.resourceToPorts.remove(t);
    	}
    	assertEquals(0, cb.resourceToPorts.size());
    	
    	assertNotEquals(-1, cb.robber);
    	
    	assertNotEquals(TileType.desert, cb.getTiles().get(18).getType());
	}
	
	@Test
	public void testConstruct3Player() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		assertEquals(cb.getTiles().size(), 19);
		
		ArrayList<Integer> numbers = new ArrayList<>();
		for (Tile t: cb.getTiles()) {
			numbers.add(t.getNumber());
		}
		
		numbers.remove(Integer.valueOf(2));
		numbers.remove(Integer.valueOf(12));
		for(int i = 3; i <= 11; i++) {
			numbers.remove(Integer.valueOf(i));
			numbers.remove(Integer.valueOf(i));
		}
		
		assertEquals(0, numbers.size());
		
		ArrayList<TileType> types = new ArrayList<>();
		for (Tile t: cb.getTiles()) {
			types.add(t.getType());
		}
		
		types.remove(TileType.desert);
		for(int i = 0; i < 4; i++) {
            types.remove(TileType.wool);
            types.remove(TileType.wheat);
            types.remove(TileType.wood);
        }
		for(int i = 0; i < 3; i++) {
            types.remove(TileType.brick);
            types.remove(TileType.ore);
        }
		
		assertEquals(0, types.size());
		
		ArrayList<Point> positionsExpected = new ArrayList<>();
		for(int x = 1; x < 6; x++){
            int ylow = -1;
            int yhigh = -1;
            switch (x) {
                case 1:
                    ylow = 1;
                    yhigh = 4;
                    break;
                case 2:
                    ylow = 1;
                    yhigh = 5;
                    break;
                case 3:
                    ylow = 1;
                    yhigh = 6;
                    break;
                case 4:
                    ylow = 2;
                    yhigh = 6;
                    break;
                case 5:
                    ylow = 3;
                    yhigh = 6;
                    break;
            }
            
            for(int y = ylow; y < yhigh; y++){
                positionsExpected.add(new Point(x,y));
            }
		}
		
		ArrayList<Point> positionsActual = new ArrayList<>();
		for (Tile t: cb.getTiles()) {
			positionsActual.add(t.getLocation());
		}
		
		for(Point p: positionsExpected) {
			positionsActual.remove(p);
		}
		
		assertEquals(0, positionsActual.size());
		
		ArrayList<PortType> portTypesExpected = new ArrayList<PortType>();
    	for (int i = 0; i < 4; i++) {
    		portTypesExpected.add(PortType.three);
    	}
    	portTypesExpected.add(PortType.brick);
    	portTypesExpected.add(PortType.wool);
    	portTypesExpected.add(PortType.wood);
    	portTypesExpected.add(PortType.ore);
    	portTypesExpected.add(PortType.wheat);
    	for(PortType p: portTypesExpected) {
    		cb.portTypes.remove(p);
    	}
    	assertEquals(0, cb.portTypes.size());
    	
    	HashMap<TileType, PortType> resourceToPortsExpected = new HashMap<>();
    	resourceToPortsExpected.put(TileType.brick, PortType.brick);
    	resourceToPortsExpected.put(TileType.wool, PortType.wool);
    	resourceToPortsExpected.put(TileType.wood, PortType.wood);
    	resourceToPortsExpected.put(TileType.ore, PortType.ore);
    	resourceToPortsExpected.put(TileType.wheat, PortType.wheat);
    	for(TileType t: resourceToPortsExpected.keySet()) {
    		cb.resourceToPorts.remove(t);
    	}
    	assertEquals(0, cb.resourceToPorts.size());
    	
    	assertNotEquals(-1, cb.robber);
    	
    	assertNotEquals(TileType.desert, cb.getTiles().get(18).getType());
	}
	
	@Test
	public void testLocationClicked1() {
		pc = new PlayersController(4);
		cb = new CatanBoard(pc);
		int tileNum = 1; int cornerNum= 0;
		addPlayerClickNums(tileNum, cornerNum);
		
		registerPlayerClick();
		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(tileNum);
		HashMap<Integer, Settlement> settlements = tile.getSettlements();
		
		Settlement s = settlements.get(cornerNum);
		assertEquals(s.getOwner(), pc.getCurrentPlayer());
	}
	
	@Test
	public void testBoardInitSetupSettlementRoad() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		int p1tile = 1; int p1corner = 1;
		int p1roadTile = 1; int p1road1 = 1; int p1road2 = 2;

		addPlayerClickNums(p1tile, p1corner);
		registerPlayerClick();
		checkPlayerTileForSettlement(p1tile, p1corner, player1);
		
		addPlayerDragNums(p1roadTile, p1road1, p1road2);
		registerPlayerDragSetup();
		Road r = checkPlayerTileForRoad(player1, p1roadTile, p1road1, p1road2);
		assertEquals(r.getAngle(), 2);
	}
	
	@Test
	public void testBoardInitSetupSettlementRoadBadPlacementNoConnections() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		int p1tile = 1; int p1corner = 1;
		int p1roadTile = 10; int p1road1 = 1; int p1road2 = 2;

		addPlayerClickNums(p1tile, p1corner);
		registerPlayerClick();
		checkPlayerTileForSettlement(p1tile, p1corner, player1);
		
		addPlayerDragNums(p1roadTile, p1road1, p1road2);
		registerPlayerDragSetup();

		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(p1roadTile);
		HashMap<ArrayList<Integer>, Road> roads = tile.getRoads();
		assertEquals(0, roads.size());
	}

	@Test
	public void testBoardInitSetup3Player() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		int p1tile = 1; int p1corner = 1; int p1road = 2;
		int p2tile = 5; int p2corner = 5; int p2road = 0;
		int p3tile = 15; int p3corner = 3; int p3road = 4;
		
		int p1tile2 = 3; int p1corner2 = 1; int p1road2 = 2;
		int p2tile2 = 10; int p2corner2 = 5; int p2road2 = 4;
		int p3tile2 = 18; int p3corner2 = 3; int p3road2 = 2;
		
		makePlayerTurnInit(p1tile, p1corner, p1road, player1, 2);
		
		makePlayerTurnInit(p2tile, p2corner, p2road, player2, 0);

		makePlayerTurnInit(p3tile, p3corner, p3road, player3, 1);
		
		makePlayerTurnInit(p3tile2, p3corner2, p3road2, player3, 0);

		makePlayerTurnInit(p2tile2, p2corner2, p2road2, player2, 2);
		
		makePlayerTurnInit(p1tile2, p1corner2, p1road2, player1, 2);
	}
	
	@Test
	public void testBoardInitSetup4Player() {
		pc = new PlayersController(4);
		cb = new CatanBoard(pc);
		int p1tile = 1; int p1corner = 1; int p1road = 2;
		int p2tile = 5; int p2corner = 5; int p2road = 0;
		int p3tile = 15; int p3corner = 3; int p3road = 4;
		int p4tile = 13; int p4corner = 0; int p4road = 1;
		
		int p1tile2 = 3; int p1corner2 = 1; int p1road2 = 2;
		int p2tile2 = 10; int p2corner2 = 5; int p2road2 = 4;
		int p3tile2 = 18; int p3corner2 = 3; int p3road2 = 2;
		int p4tile2 = 11; int p4corner2 = 2; int p4road2 = 3;
		
		makePlayerTurnInit(p1tile, p1corner, p1road, player1, 2);
		
		makePlayerTurnInit(p2tile, p2corner, p2road, player2, 0);

		makePlayerTurnInit(p3tile, p3corner, p3road, player3, 1);
		
		makePlayerTurnInit(p4tile, p4corner, p4road, player4, 1);
		
		makePlayerTurnInit(p4tile2, p4corner2, p4road2, player4, 0);
		
		makePlayerTurnInit(p3tile2, p3corner2, p3road2, player3, 0);

		makePlayerTurnInit(p2tile2, p2corner2, p2road2, player2, 2);
		
		makePlayerTurnInit(p1tile2, p1corner2, p1road2, player1, 2);
	}
	
	@Test
	public void testEndTurnAndRoll_NotASeven() {
		pc = new PlayersController(3);
		
		Random random = EasyMock.strictMock(Random.class);
		cb = EasyMock.partialMockBuilder(CatanBoard.class)
				.addMockedMethod("distributeResources")
				.withConstructor(pc)
				.createMock();
		
		EasyMock.expect(random.nextInt(6)).andReturn(2);
		EasyMock.expect(random.nextInt(6)).andReturn(2);
		
		EasyMock.replay(random);
		
		cb.distributeResources(6);
		
		EasyMock.replay(cb);
		
		int result = cb.endTurnAndRoll(random);
		
		EasyMock.verify(cb, random);
		
		assertNotEquals(7, result);
	}
	
	@Test
	public void testEndTurnAndRoll_RollASeven() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		Random random = EasyMock.strictMock(Random.class);
		cb = EasyMock.partialMockBuilder(CatanBoard.class)
				.addMockedMethod("distributeResources")
				.withConstructor(pc)
				.createMock();
		
		EasyMock.expect(random.nextInt(6)).andReturn(2);
		EasyMock.expect(random.nextInt(6)).andReturn(3);
		
		EasyMock.replay(random);
		
		EasyMock.replay(cb);
		
		int result = cb.endTurnAndRoll(random);
		
		EasyMock.verify(cb, random);
		
		assertEquals(7, result);
	}
	
	public ArrayList<Settlement> getSettlementsFromClickedTiles() {
		ArrayList<Tile> tiles = cb.getTiles();
		ArrayList<Settlement> settlements = new ArrayList<Settlement>();
		for (int i = 0; i < 3; i++) {
			HashMap<Integer, Settlement> settlementMap = tiles.get(tileNums.get(i)).getSettlements();
			Settlement settlement = settlementMap.get(cornerNums.get(i));
			if (settlement == null) {
				fail("Settlement should be present");
			}
			settlements.add(settlement);
		}
		return settlements;
	}
	
	public void addResourcesForSettlements(Player player, int numSettlements) {
		player.addResource(TileType.brick, numSettlements);
		player.addResource(TileType.wheat, numSettlements);
		player.addResource(TileType.wool, numSettlements);
		player.addResource(TileType.wood, numSettlements);
	}

	public void makePlayerTurnInit(int tileNum, int settlementCorner, int roadCorner, int playerNum, int rAngle) {
		addPlayerClickNums(tileNum, settlementCorner);
		registerPlayerClick();
		checkPlayerTileForSettlement(tileNum, settlementCorner, playerNum);
		addPlayerDragNums(tileNum, settlementCorner, roadCorner);
		registerPlayerDragSetup();
		Road r = checkPlayerTileForRoad(playerNum, tileNum, settlementCorner, roadCorner);
		assertEquals(rAngle, r.getAngle());
		pc.nextPlayer();
	}
	
	public Road checkPlayerTileForRoad(int playerNum, int tileNum, int roadCorner1, int roadCorner2) {
		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(tileNum);
		HashMap<ArrayList<Integer>, Road> roads = tile.getRoads();
		ArrayList<Integer> edge = new ArrayList<Integer>();
		edge.add(roadCorner1); edge.add(roadCorner2);
		Road r = roads.get(edge);
		assertEquals(r.getOwner(), pc.getPlayer(playerNum));
		return r;
	}

	public void checkPlayerTileForSettlement(int tileNum, int cornerNum, int playerNum) {
		ArrayList<Tile> tiles = cb.getTiles();
		Tile tile = tiles.get(tileNum);
		HashMap<Integer, Settlement> settlements = tile.getSettlements();
		Settlement s = settlements.get(cornerNum);
		assertEquals(s.getOwner(), pc.getPlayer(playerNum));
	}
	
	public void registerPlayerClick() {
		cb.settlementLocationClick(tileNums, cornerNums, GameStates.drop_settlement_setup);
		clearClicks();
	}
	
	public void registerPlayerDragSetup() {
		cb.roadLocationClick(tileToCorners, tileToRoadOrientation, GameStates.drop_road_setup);
		clearDrags();
	}
	
	public void clearClicks() {
		tileNums.clear();
		cornerNums.clear();
	}
	
	public void clearDrags() {
		tileToCorners.clear();
		tileToRoadOrientation.clear();
	}

	public void addPlayerClickNums(int tileNum, int cornerNum) {
		tileNums.add(tileNum);
		cornerNums.add(cornerNum);
	}
	
	public void addPlayerDragNums(int tileNum, int cornerNum1, int cornerNum2) {
		ArrayList<Integer> corners = new ArrayList<Integer>();
		corners.add(cornerNum1);
		corners.add(cornerNum2);
		
		tileToCorners.put(tileNum, corners);
		tileToRoadOrientation.put(tileNum, getRoadOrientation(corners));
	}
	
	public void addResourcesForCities(Player player, int numCities) {
		player.addResource(TileType.wheat, numCities*2);
		player.addResource(TileType.ore, numCities*3);
	}
	
	public int getRoadOrientation(ArrayList<Integer> corners) {
		int p1 = corners.get(0);
		int p2 = corners.get(1);
		if (p1 > p2) {
			int temp = p1;
			p1 = p2;
			p2 = temp;
		}
		if (p1 == 0 && p2 == 5 || p1 == 2 && p2 == 3) {
			return 0;
		} else if (p1 == 0 && p2 == 1 || p1 == 3 && p2 == 4) {
			return 1;
		} else if (p1 == 1 && p2 == 2 || p1 == 4 && p2 == 5) {
			return 2;
		}
		return -1;
	}
	
	public void addResourcesForRoad(Player player, int numOfRoads) {
		player.addResource(TileType.brick, numOfRoads);
		player.addResource(TileType.wood, numOfRoads);
	}

}
