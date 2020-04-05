package gui;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import objects.Player;
import objects.Settlement;
import objects.Tile;
import objects.TileType;

public class CatanBoard {
    private ArrayList<Tile> tiles;
    private ArrayList<Player> players;
    
    public CatanBoard(int numberOfPlayers){
    	this.players = createPlayers(numberOfPlayers);
        this.tiles = new ArrayList<Tile>();
        shuffleTiles();
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
            Collections.shuffle(positions);
        }
        
        for(int i = 0; i < 18; i++){
            this.tiles.add(new Tile(positions.get(i), i, numbers.get(i), types.get(i)));
        }
        
        // Placing the desert tile with robber on the board
        Tile desertTile = new Tile(positions.get(18), 18, 7, TileType.desert);
        desertTile.setRobber();
        this.tiles.add(desertTile);
    }
    
    public void addSettlement(ArrayList<Integer> tiles, ArrayList<Integer> corners, Player plyr) {
    	Settlement newlyAddedSettlement = new Settlement(plyr);
    	for(int tile : tiles) {
    		for(int corner : corners) {
    			this.tiles.get(tile).addSettlement(corner, newlyAddedSettlement);
    		}
    	}
    }

	public ArrayList<Tile> getTiles() {
		return this.tiles;
	}
}
