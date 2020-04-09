package objects;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

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
            this.tiles.add(new Tile(positions.get(i), numbers.get(i), types.get(i)));
        }
        
        // Placing the desert tile with robber on the board
        Tile desertTile = new Tile(positions.get(18), 7, TileType.desert);
        desertTile.setRobber();
        this.tiles.add(desertTile);
    }
    
	public ArrayList<Tile> getTiles() {
		return this.tiles;
	}

	public void locationClicked(ArrayList<Integer> tileNums, ArrayList<Integer> cornerNums) {
		if (this.initialSetup) {
			handleInitialSetup(tileNums, cornerNums);
		}		
	}

	private void handleInitialSetup(ArrayList<Integer> tileNums, ArrayList<Integer> cornerNums) {
		if (tileNums.contains(-1)) {
			placeRoad(tileNums, cornerNums);
			incrementPlayerInit();
	    	this.turnCount++;
		} else {
			placeSettlement(tileNums, cornerNums);
		}
	}
	
	private void placeSettlement(ArrayList<Integer> tileNums, ArrayList<Integer> cornerNums) {
		Settlement newlyAddedSettlement = new Settlement(getCurrentPlayer());
    	for(int i = 0; i < tileNums.size(); i++) {
    		Tile selectedTile = this.tiles.get(tileNums.get(i));
    		Integer selectedCornerNum = cornerNums.get(i);
    		selectedTile.addSettlement(selectedCornerNum, newlyAddedSettlement);
    	}
	}

	private void placeRoad(ArrayList<Integer> tiles, ArrayList<Integer> corners) {
		ArrayList<Integer> tiles2 = new ArrayList<Integer>();
		ArrayList<Integer> corners2 = new ArrayList<Integer>();
		splitCornerTileLists(tiles, corners, tiles2, corners2);
		
		ArrayList<Integer> edges = getEdgesFromCorners(tiles, corners, tiles2, corners2);
		
		Road newRoad = new Road(getCurrentPlayer());
		addRoadToTiles(newRoad, tiles, tiles2, edges);
	}

	private void addRoadToTiles(Road newRoad, ArrayList<Integer> tiles, ArrayList<Integer> tiles2,
			ArrayList<Integer> edges) {
		int count = 0;
		for (int i = 0; i < tiles.size(); i++) {
			if (tiles.get(i) == tiles2.get(i)) {
				setRoadAngle(newRoad, edges.get(count*2), edges.get((count*2) + 1));
				this.tiles.get(tiles.get(i)).addRoad(edges.get(count*2), edges.get((count*2) + 1), newRoad);
				this.tiles.get(tiles2.get(i)).addRoad(edges.get(count*2), edges.get((count*2) + 1), newRoad);
				count++;
			}
		}
	}

	private void setRoadAngle(Road newRoad, int p1, int p2) {
		if (p1 > p2) {
			int temp = p1;
			p1 = p2;
			p2 = temp;
		}
		if (p1 == 0 && p2 == 1 || p1 == 3 && p2 == 4) {
			newRoad.setAngle(1);
		} else if (p1 == 0 && p2 == 5 || p1 == 2 && p2 == 3) {
			newRoad.setAngle(0);
		} else if (p1 == 1 && p2 == 2 || p1 == 4 && p2 == 5) {
			newRoad.setAngle(2);
		}
	}

	private ArrayList<Integer> getEdgesFromCorners(ArrayList<Integer> tiles, ArrayList<Integer> corners, ArrayList<Integer> tiles2, ArrayList<Integer> corners2) {
		ArrayList<Integer> edges = new ArrayList<Integer>();
		for (int i = 0; i < tiles.size(); i++) {
			if (tiles.get(i) == tiles2.get(i)) {
				edges.add(corners.get(i));
				edges.add(corners2.get(i));
			}
		}
		return edges;
	}

	private void splitCornerTileLists(ArrayList<Integer> tiles, ArrayList<Integer> corners, ArrayList<Integer> tiles2, ArrayList<Integer> corners2) {
		for (int i = 0; i < tiles.size(); i ++) {
			if (tiles.get(i) == -1) {
				tiles2.addAll(tiles.subList(i+1, tiles.size()));
				tiles.subList(i, tiles.size()).clear();
				
				corners2.addAll(corners.subList(i+1, corners.size()));
				corners.subList(i, corners.size()).clear();
			}
		}
	}

	private Player getCurrentPlayer() {
		return this.players.get(this.currentPlayer);
	}
	
	private void incrementPlayerInit() {
		if (this.turnCount > this.numPlayers - 1) {
			this.currentPlayer--;
		} else if (this.turnCount < this.numPlayers - 1) {
			this.currentPlayer++;
		}
	};
	

}
