package gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
    private ArrayList<Tile> tiles;
    private ArrayList<Player> players;
    private GUIObjectConstructor guiCreator;
    
    public CatanBoard(int numberOfPlayers, GUIObjectConstructor gui){
    	this.players = createPlayers(numberOfPlayers);
        this.tiles = new ArrayList<Tile>();
        this.guiCreator = gui;
        intialBoardSetup();
    }
    
    public ArrayList<Player> createPlayers(int num) {
 	   ArrayList<Player> plyrs = new ArrayList<>();
 	   Player p = new Player(Color.BLUE);
 	   plyrs.add(p);
 	   if(num != 3) {
 		  p = new Player(Color.RED);
 	 	  plyrs.add(p);
	   }
 	   p = new Player(Color.WHITE);
 	   plyrs.add(p);
 	   p = new Player(Color.ORANGE);
 	   plyrs.add(p);
 	   return plyrs;
    }

    private void intialBoardSetup() {        
        // Coordinates
        ArrayList<Integer> wheatLocs = new ArrayList<Integer>(Arrays.asList(2, 3, 6, 8));
        ArrayList<Integer> wheatNums = new ArrayList<Integer>(Arrays.asList(9, 6, 12, 4));
        initilizeTiles(wheatLocs, wheatNums, TileType.wheat);
        
        ArrayList<Integer> woolLocs = new ArrayList<Integer>(Arrays.asList(7, 12, 14, 15));
        ArrayList<Integer> woolNums = new ArrayList<Integer>(Arrays.asList(11, 5, 4, 2));
        initilizeTiles(woolLocs, woolNums, TileType.wool);
        
        ArrayList<Integer> woodLocs = new ArrayList<Integer>(Arrays.asList(1, 5, 13, 18));
        ArrayList<Integer> woodNums = new ArrayList<Integer>(Arrays.asList(8, 11, 3, 9));
        initilizeTiles(woodLocs, woodNums, TileType.wood);
        
        ArrayList<Integer> oreLocs = new ArrayList<Integer>(Arrays.asList(4, 11, 16));
        ArrayList<Integer> oreNums = new ArrayList<Integer>(Arrays.asList(3, 10, 8));
        initilizeTiles(oreLocs, oreNums, TileType.ore);
        
        ArrayList<Integer> brickLocs = new ArrayList<Integer>(Arrays.asList(0, 10, 17));
        ArrayList<Integer> brickNums = new ArrayList<Integer>(Arrays.asList(5, 6, 10));
        initilizeTiles(brickLocs, brickNums, TileType.brick);
        
        Tile desertTile = new Tile(9, 7, TileType.desert);
        desertTile.setRobber();
        this.tiles.add(desertTile);
        
        this.tiles.sort(new SortTilesByNum());
        // Initial Player Settlement and Road setup
        
        for(Player player : this.players) {
        	if(player.getColor().equals(Color.BLUE)) {
        		// Settlement
        		this.tiles.get(0).addSettlement(3, new Settlement(player));
        		this.tiles.get(1).addSettlement(1, new Settlement(player));
        		this.tiles.get(4).addSettlement(5, new Settlement(player));
        		this.tiles.get(7).addSettlement(3, new Settlement(player));
        		this.tiles.get(8).addSettlement(1, new Settlement(player));
        		this.tiles.get(12).addSettlement(5, new Settlement(player));
        		//Road
        		createRoadObject(player, 0, 0, 4, 3, 2, 0, 5);
        		createRoadObject(player, 2, 8, 12, 2, 1, 5, 4);
				
        	}else if(player.getColor().equals(Color.RED)) {
        		// Settlement
        		this.tiles.get(2).addSettlement(1, new Settlement(player));
        		this.tiles.get(5).addSettlement(5, new Settlement(player));
        		this.tiles.get(1).addSettlement(3, new Settlement(player));
        		this.tiles.get(11).addSettlement(1, new Settlement(player));
        		this.tiles.get(15).addSettlement(5, new Settlement(player));
        		this.tiles.get(10).addSettlement(3, new Settlement(player));
        		//Road
        		createRoadObject(player, 0, 1, 5, 3, 2, 0, 5);
        		createRoadObject(player, 0, 10, 15, 3, 2, 0, 5);
        		
        	}else if(player.getColor().equals(Color.WHITE)) {
        		// Settlement
        		this.tiles.get(6).addSettlement(1, new Settlement(player));
        		this.tiles.get(10).addSettlement(5, new Settlement(player));
        		this.tiles.get(5).addSettlement(3, new Settlement(player));
        		this.tiles.get(13).addSettlement(1, new Settlement(player));
        		this.tiles.get(16).addSettlement(5, new Settlement(player));
        		this.tiles.get(12).addSettlement(3, new Settlement(player));
        		//Road
        		createRoadObject(player, 1, 5, 6, 4, 3, 1, 0);
        		createRoadObject(player, 2, 13, 16, 1, 2, 4, 5);
        		
        	}else if(player.getColor().equals(Color.ORANGE)) {
        		// Settlement
        		this.tiles.get(4).addSettlement(1, new Settlement(player));
        		this.tiles.get(8).addSettlement(5, new Settlement(player));
        		this.tiles.get(3).addSettlement(3, new Settlement(player));
        		this.tiles.get(18).addSettlement(0, new Settlement(player));
        		this.tiles.get(14).addSettlement(2, new Settlement(player));
        		this.tiles.get(17).addSettlement(4, new Settlement(player));
        		//Road
        		createRoadObject(player, 0, 3, 8, 3, 2, 0, 5);
        		createRoadObject(player, 0, 14, 18, 3, 2, 0, 5);
        	
        	}
        }
        guiCreator.createGUITiles(this.tiles);
    }
    
    private void createRoadObject(Player p, int angle, int tile1, int tile2, int corner1, int corner2, int corner3, int corner4) {
    	Road r = new Road(p);
		r.setAngle(angle);
		this.tiles.get(tile1).addRoad(corner1, corner2, r);
		this.tiles.get(tile2).addRoad(corner3, corner4, r);
    }
    
    private void initilizeTiles(List<Integer> positionNums, List<Integer> numbers, TileType type) {
    	if (positionNums.size() != numbers.size()) {
    		return;
    	}
    	for (int i = 0; i < positionNums.size(); i++) {
    		int p = positionNums.get(i);
    		this.tiles.add(new Tile(p, numbers.get(i), type));
    	}
    }
    
    class SortTilesByNum implements Comparator<Tile> {

		@Override
		public int compare(Tile arg0, Tile arg1) {
			return arg0.getBoardPosition() - arg1.getBoardPosition();
		}
    	
    }
   
}
