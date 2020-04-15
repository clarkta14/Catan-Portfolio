package objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import gui.GameStates;

public class CatanBoard {
    private ArrayList<Tile> tiles;
    private PlayersController turnController;
    
    public CatanBoard(PlayersController turnController){
    	this.turnController = turnController;
        this.tiles = new ArrayList<Tile>();
        shuffleTiles();
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
    	desertTile.setRobber(true);
    	this.tiles.add(desertTile);
    	
    	swapTileForRobberPlacement();
    }
    
    public void swapTileForRobberPlacement() {
    	int tileIndexToSwapWith = (int) (Math.random() * 17);    	
    	Point temp = this.tiles.get(18).getLocation();
    	this.tiles.get(18).setLocation(this.tiles.get(tileIndexToSwapWith).getLocation());
    	this.tiles.get(tileIndexToSwapWith).setLocation(temp);
    	Collections.swap(this.tiles, tileIndexToSwapWith, 18);
    }
    
	public ArrayList<Tile> getTiles() {
		return this.tiles;
	}
	
	public void locationClicked(ArrayList<Integer> tiles, ArrayList<Integer> corners, GameStates guistate) {
		addSettlementToTiles(tiles, corners, guistate);	
	}
	
	public boolean locationClicked(HashMap<Integer, ArrayList<Integer>> tilesToCorners, HashMap<Integer, Integer> tileToRoadOrientation) {
		return placeRoad(tilesToCorners, tileToRoadOrientation);
	}

	public boolean addSettlementToTiles(ArrayList<Integer> selectedTiles, ArrayList<Integer> corners, GameStates guistate) {
		Settlement newlyAddedSettlement = new Settlement(this.turnController.getCurrentPlayer());
		boolean settlementLocationIsValid = true;
		for(int i = 0; i < selectedTiles.size(); i++) {
			settlementLocationIsValid = settlementLocationIsValid &&
					this.tiles.get(selectedTiles.get(i)).checkValidSettlementPlacement(corners.get(i));
    	}
		
		if(guistate != GameStates.drop_settlement_setup) {
			for(int i = 0; i < selectedTiles.size(); i++) {
				settlementLocationIsValid = settlementLocationIsValid &&
					this.tiles.get(selectedTiles.get(i)).checkRoadAtCornerForGivenPlayer(corners.get(i), this.turnController.getCurrentPlayer());
    		}
		}
		
		if(!settlementLocationIsValid) return false;
		
		for(int i = 0; i < selectedTiles.size(); i++) {
			this.tiles.get(selectedTiles.get(i)).addSettlement(corners.get(i), newlyAddedSettlement);
		}
		return true;
	}

	private boolean placeRoad(HashMap<Integer, ArrayList<Integer>> tilesToCorners, HashMap<Integer, Integer> tileToRoadOrientation) {
		Road newRoad = new Road(this.turnController.getCurrentPlayer());
		Boolean validPlacement = false;
		for (int tileNum : tilesToCorners.keySet()) {
			Tile tileToCheck = this.tiles.get(tileNum);
			ArrayList<Integer> edge = tilesToCorners.get(tileNum);
			validPlacement = validPlacement || tileToCheck.checkValidRoadPlacement(edge.get(0), edge.get(1), newRoad);
		}
		if (validPlacement) {
			addRoadToTiles(newRoad, tilesToCorners, tileToRoadOrientation);
			return true;
		} else {
			return false;
		}
	}

	private void addRoadToTiles(Road newRoad, HashMap<Integer, ArrayList<Integer>> tilesToCorners, HashMap<Integer, Integer> tileToRoadOrientation) {
		for (int tileNum : tilesToCorners.keySet()) {
			ArrayList<Integer> corners = tilesToCorners.get(tileNum);
			int angle = tileToRoadOrientation.get(tileNum);
			newRoad.setAngle(angle);
			this.tiles.get(tileNum).addRoad(corners.get(0), corners.get(1), newRoad);
		}
	}
}
