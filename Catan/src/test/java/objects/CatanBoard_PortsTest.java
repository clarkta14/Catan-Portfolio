package objects;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class CatanBoard_PortsTest {
	private PlayersController pc;
	private CatanBoard cb;
	private int[] portTiles = new int[] {0, 1, 6, 11, 15, 17, 16, 12, 3};
	private int[][] portCorners = new int[][] {{0,5}, {4,5}, {4,5}, {3,4}, {2,3}, {2,3}, {1,2}, {0,1}, {0,1}};

	@Test
	public void testTileLocationsArePorts() {
		pc = new PlayersController(3);
		cb = new CatanBoard(pc);
		
		ArrayList<Tile> tiles = cb.getTiles();
		
		checkTileLocationsArePorts(tiles);
	}

	private void checkTileLocationsArePorts(ArrayList<Tile> tiles) {
		Tile tile;
		for (int i = 0; i < portTiles.length; i++) {
			tile = tiles.get(portTiles[i]);
			for (int j = 0; j < 2; j++) {
				Settlement port = new Settlement(pc.getCurrentPlayer());
				tile.addSettlement(portCorners[i][j], port);
				assertFalse(port.portType == null);
				assertFalse(port.portType == PortType.none);
			}
		}
		
	}

}
