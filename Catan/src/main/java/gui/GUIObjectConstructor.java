package gui;

import java.awt.Point;
import java.util.ArrayList;

import objects.Tile;

public class GUIObjectConstructor {

	private BoardWindow bw;
	
	public GUIObjectConstructor(BoardWindow bw) {
		this.bw = bw;
	}
	
	public void createGUITiles(ArrayList<Tile> boardTiles) {
		ArrayList<Point> points = createTileLocations();
		GUITile[][] guiTiles = new GUITile[7][7];
		for (int i = 0; i < boardTiles.size(); i++) {
			Tile b = boardTiles.get(i);
			Point p = points.get(b.getBoardPosition());
			guiTiles[(int) p.getX()][(int) p.getY()] = new GUITile(p, b);
		}
		
		bw.updateBoard(guiTiles);
	}
	
	/**
     * Creates the locations that the tiles should be available at
     * @return
     */
    private ArrayList<Point> createTileLocations() {
		ArrayList<Point> positions = new ArrayList<>();
		for (int x = 1; x < 6; x++) {
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
			for (int y = ylow; y < yhigh; y++) {
				positions.add(new Point(x, y));
			}
		}
		return positions;
	}
}
