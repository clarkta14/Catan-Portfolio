package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import objects.Player;
import objects.Road;
import objects.Settlement;
import objects.Tile;
import objects.TileType;

/**
 *
 * @author Indresh
 */
public class CatanBoard {
    private Tile[][] tiles;
    private ArrayList<Player> players;
    private BoardWindow window;
    
    public CatanBoard(ArrayList<Player> players, BoardWindow bw){
    	this.players = players;
        this.tiles = new Tile[7][7];
        this.window = bw;
        intialBoardSetup();
    }

    private void intialBoardSetup() {        
        // Coordinates
        ArrayList<Point> positions = createTileLocations();
        ArrayList<Integer> wheatLocs = new ArrayList<Integer>(Arrays.asList(2, 3, 6, 8));
        ArrayList<Integer> wheatNums = new ArrayList<Integer>(Arrays.asList(9, 6, 12, 4));
        initilizeTiles(wheatLocs, wheatNums, TileType.wheat, positions);
        
        ArrayList<Integer> woolLocs = new ArrayList<Integer>(Arrays.asList(7, 12, 14, 15));
        ArrayList<Integer> woolNums = new ArrayList<Integer>(Arrays.asList(11, 5, 4, 2));
        initilizeTiles(woolLocs, woolNums, TileType.wool, positions);
        
        ArrayList<Integer> woodLocs = new ArrayList<Integer>(Arrays.asList(1, 5, 13, 18));
        ArrayList<Integer> woodNums = new ArrayList<Integer>(Arrays.asList(8, 11, 3, 9));
        initilizeTiles(woodLocs, woodNums, TileType.wood, positions);
        
        ArrayList<Integer> oreLocs = new ArrayList<Integer>(Arrays.asList(4, 11, 16));
        ArrayList<Integer> oreNums = new ArrayList<Integer>(Arrays.asList(3, 10, 8));
        initilizeTiles(oreLocs, oreNums, TileType.ore, positions);
        
        ArrayList<Integer> brickLocs = new ArrayList<Integer>(Arrays.asList(0, 10, 17));
        ArrayList<Integer> brickNums = new ArrayList<Integer>(Arrays.asList(5, 6, 10));
        initilizeTiles(brickLocs, brickNums, TileType.brick, positions);
        
        Tile desertTile = new Tile(positions.get(9), 7, TileType.desert);
        desertTile.setRobber();
        this.tiles[(int) positions.get(9).getX()][(int) positions.get(9).getY()] = desertTile;
        
        // Initial Player Settlement and Road setup
        
        for(Player player : this.players) {
        	if(player.getColor().equals(Color.BLUE)) {
        		// Settlement
        		this.tiles[1][1].addSettlement(3, new Settlement(player));
        		this.tiles[1][2].addSettlement(1, new Settlement(player));
        		this.tiles[2][2].addSettlement(5, new Settlement(player));
        		this.tiles[3][1].addSettlement(3, new Settlement(player));
        		this.tiles[3][2].addSettlement(1, new Settlement(player));
        		this.tiles[4][2].addSettlement(5, new Settlement(player));
        		//Road
        		createRoadObject(player, 0, 1, 1, 2, 2, 3, 2, 0, 5);
        		createRoadObject(player, 2, 3, 2, 4, 2, 2, 1, 5, 4);
				
        	}else if(player.getColor().equals(Color.RED)) {
        		// Settlement
        		this.tiles[1][2].addSettlement(3, new Settlement(player));
        		this.tiles[1][3].addSettlement(1, new Settlement(player));
        		this.tiles[2][3].addSettlement(5, new Settlement(player));
        		this.tiles[3][4].addSettlement(3, new Settlement(player));
        		this.tiles[3][5].addSettlement(1, new Settlement(player));
        		this.tiles[4][5].addSettlement(5, new Settlement(player));
        		//Road
        		createRoadObject(player, 0, 3, 4, 4, 5, 3, 2, 0, 5);
        		createRoadObject(player, 0, 1, 2, 2, 3, 3, 2, 0, 5);
        		
        	}else if(player.getColor().equals(Color.WHITE)) {
        		// Settlement
        		this.tiles[2][3].addSettlement(3, new Settlement(player));
        		this.tiles[2][4].addSettlement(1, new Settlement(player));
        		this.tiles[3][4].addSettlement(5, new Settlement(player));
        		this.tiles[4][2].addSettlement(3, new Settlement(player));
        		this.tiles[4][3].addSettlement(1, new Settlement(player));
        		this.tiles[5][3].addSettlement(5, new Settlement(player));
        		//Road
        		createRoadObject(player, 1, 2, 3, 2, 4, 4, 3, 1, 0);
        		createRoadObject(player, 2, 4, 3, 5, 3, 2, 1, 5, 4);
        		
        	}else if(player.getColor().equals(Color.ORANGE)) {
        		// Settlement
        		this.tiles[2][1].addSettlement(3, new Settlement(player));
        		this.tiles[2][2].addSettlement(1, new Settlement(player));
        		this.tiles[3][2].addSettlement(5, new Settlement(player));
        		this.tiles[5][5].addSettlement(0, new Settlement(player));
        		this.tiles[5][4].addSettlement(4, new Settlement(player));
        		this.tiles[4][4].addSettlement(2, new Settlement(player));
        		//Road
        		createRoadObject(player, 0, 2, 1, 3, 2, 3, 2, 0, 5);
        		createRoadObject(player, 0, 4, 4, 5, 5, 3, 2, 0, 5);
        	
        	}
        }
        this.window.updateBoard(this.tiles);
    }
    
    private void createRoadObject(Player p, int angle, int tileX, int tileY, int tile2X, int tile2Y, int corner1, int corner2, int corner3, int corner4) {
    	Road r = new Road(p);
		r.setAngle(angle);
		this.tiles[tileX][tileY].addRoad(corner1, corner2, r);
		this.tiles[tile2X][tile2Y].addRoad(corner3, corner4, r);
    }
    
    private void initilizeTiles(List<Integer> positionNums, List<Integer> numbers, TileType type, ArrayList<Point> positions) {
    	if (positionNums.size() != numbers.size()) {
    		return;
    	}
    	for (int i = 0; i < positionNums.size(); i++) {
    		Point p = positions.get(positionNums.get(i));
    		this.tiles[(int) p.getX()][(int) p.getY()] = new Tile(p, numbers.get(i), type);
    	}
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
