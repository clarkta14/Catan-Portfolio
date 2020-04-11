package objects;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import gui.OptionsPanel;

public class CatanBoard {
    private ArrayList<Tile> tiles;
    ArrayList<Player> players;
    boolean initialSetup;
    int currentPlayer;
    int numPlayers;
    int turnCount;
    
    public CatanBoard(int numberOfPlayers){
    	this.numPlayers = numberOfPlayers;
    	this.players = createPlayers(numPlayers);
        this.tiles = new ArrayList<Tile>();
        this.initialSetup = true;
        this.turnCount = 0;
        this.currentPlayer = 0;
        shuffleTiles();
    }
    
    private ArrayList<Player> createPlayers(int num) {
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
    
    private void shuffleTiles() {
        // Tile Types
        ArrayList<TileType> types = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            types.add(TileType.wool);
            types.add(TileType.wheat);
            types.add(TileType.wood);
        }
        for(int i = 0; i < 3; i++) {
            types.add(TileType.brick);
            types.add(TileType.ore);
        }
        
        // Tile Numbers
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            numbers.add(3);
            numbers.add(4);
            numbers.add(5);
            numbers.add(6);
            numbers.add(8);
            numbers.add(9);
            numbers.add(10);
            numbers.add(11);
        }
        numbers.add(2);
        numbers.add(12);
        
        // Coordinates
        ArrayList<Point> positions = new ArrayList<>();
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
                positions.add(new Point(x,y));
            }
        }
        
        // Shuffle List
        for(int i = 0; i < 3; i++){
            Collections.shuffle(types);
            Collections.shuffle(numbers);
        }
        
        for(int i = 0; i < 18; i++){
            this.tiles.add(new Tile(positions.get(i), i, numbers.get(i), types.get(i)));
        }
        
        // Placing the desert tile with robber on the board
        Tile desertTile = new Tile(positions.get(18), 18, 7, TileType.desert);
        desertTile.setRobber();
        this.tiles.add(desertTile);
    }
    
	public ArrayList<Tile> getTiles() {
		return this.tiles;
	}

	public void locationClicked(ArrayList<Integer> tiles, ArrayList<Integer> corners) {
		if (this.initialSetup) {
			placeSettlement(tiles, corners);
		}		
	}
	
	public void locationClicked(HashMap<Integer, ArrayList<Integer>> tilesToCorners, HashMap<Integer, Integer> tileToRoadOrientation) {
		if (this.initialSetup) {
			placeRoad(tilesToCorners, tileToRoadOrientation);
			incrementPlayerInit();
			this.turnCount++;
		}
	}
	
	private void placeSettlement(ArrayList<Integer> tiles, ArrayList<Integer> corners) {
		Settlement newlyAddedSettlement = new Settlement(getCurrentPlayer());
    	addSettlementToTiles(tiles, corners, newlyAddedSettlement);
	}

	private void addSettlementToTiles(ArrayList<Integer> tiles, ArrayList<Integer> corners,
			Settlement newlyAddedSettlement) {
		for(int i = 0; i < tiles.size(); i++) {
			this.tiles.get(tiles.get(i)).addSettlement(corners.get(i), newlyAddedSettlement);
    	}
	}

	private void placeRoad(HashMap<Integer, ArrayList<Integer>> tilesToCorners, HashMap<Integer, Integer> tileToRoadOrientation) {
		Road newRoad = new Road(getCurrentPlayer());
		addRoadToTiles(newRoad, tilesToCorners, tileToRoadOrientation);
	}

	private void addRoadToTiles(Road newRoad, HashMap<Integer, ArrayList<Integer>> tilesToCorners, HashMap<Integer, Integer> tileToRoadOrientation) {
		for (int tileNum : tilesToCorners.keySet()) {
			ArrayList<Integer> corners = tilesToCorners.get(tileNum);
			int angle = tileToRoadOrientation.get(tileNum);
			newRoad.setAngle(angle);
			this.tiles.get(tileNum).addRoad(corners.get(0), corners.get(1), newRoad);
		}
	}

	public Player getCurrentPlayer() {
		return this.players.get(this.currentPlayer);
	}
	
	public int getCurrentPlayerNum() {
		return this.currentPlayer;
	}
	
	private void incrementPlayerInit() {
		if (this.turnCount > this.numPlayers - 1) {
			this.currentPlayer--;
		} else if (this.turnCount < this.numPlayers - 1) {
			this.currentPlayer++;
		}
	}
	
	private void nextPlayer() {
		this.currentPlayer = (this.currentPlayer + 1) % this.numPlayers;
	}
}
