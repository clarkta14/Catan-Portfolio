package objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import gui.GameStates;

public class CatanBoard {
    private ArrayList<Tile> tiles;
    private PlayersController turnController;
    private Stack<DevelopmentCardType> developmentCards;
    
    @SuppressWarnings("serial")
	public CatanBoard(PlayersController turnController){
    	this.turnController = turnController;
        this.tiles = new ArrayList<Tile>();
        this.developmentCards = new Stack<DevelopmentCardType>() {{
        	for(int i = 0; i < 14; i++) {
        		push(DevelopmentCardType.knight);
        	}
			for(int i = 0; i < 6; i++) {
				push(DevelopmentCardType.progress);    		
			}
			for(int i = 0; i < 5; i++) {
				push(DevelopmentCardType.victory_point);
			}
        }};
        Collections.shuffle(this.developmentCards);
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
	
	public void settlementLocationClick(ArrayList<Integer> tiles, ArrayList<Integer> corners, GameStates gameState) {
		addSettlementToTiles(tiles, corners, gameState);	

	}
	
	public boolean roadLocationClick(HashMap<Integer, ArrayList<Integer>> tilesToCorners, HashMap<Integer, Integer> tileToRoadOrientation, GameStates gameState) {
		return placeRoad(tilesToCorners, tileToRoadOrientation, gameState);
	}

	public boolean addSettlementToTiles(ArrayList<Integer> selectedTiles, ArrayList<Integer> corners, GameStates gameState) {
		Player currentPlayer = this.turnController.getCurrentPlayer();
		Settlement newlyAddedSettlement = new Settlement(currentPlayer);
		boolean settlementLocationIsValid = true;
		settlementLocationIsValid = checkForValidSettlementPlacementDistanceRule(selectedTiles, corners, settlementLocationIsValid);
		
		if(!settlementLocationIsValid) return false;
		
		if(gameState != GameStates.drop_settlement_setup && gameState != GameStates.drop_settlement_setup_final) {
			settlementLocationIsValid = checkForValidSettlementPlacementConnectedToRoad(selectedTiles, corners, currentPlayer);
			if(!settlementLocationIsValid) {
				return false;
			} 
			if (!buySettlement()) {
				return false;
			}
		}
		
		for(int i = 0; i < selectedTiles.size(); i++) {
			this.tiles.get(selectedTiles.get(i)).addSettlement(corners.get(i), newlyAddedSettlement);
		}
		if (gameState == GameStates.drop_settlement_setup_final) {
			distributeSetupResources(selectedTiles, currentPlayer);
		}
		currentPlayer.alterVictoryPoints(VictoryPoints.settlement);
		return true;
	}

	private boolean checkForValidSettlementPlacementConnectedToRoad(ArrayList<Integer> selectedTiles, ArrayList<Integer> corners, Player currentPlayer) {
		boolean settlementLocationIsValid = false;
		for(int i = 0; i < selectedTiles.size(); i++) {
			settlementLocationIsValid = settlementLocationIsValid ||
				this.tiles.get(selectedTiles.get(i)).checkRoadAtCornerForGivenPlayer(corners.get(i), currentPlayer);
		}
		return settlementLocationIsValid;
	}

	private boolean checkForValidSettlementPlacementDistanceRule(ArrayList<Integer> selectedTiles, ArrayList<Integer> corners, boolean settlementLocationIsValid) {
		for(int i = 0; i < selectedTiles.size(); i++) {
			settlementLocationIsValid = settlementLocationIsValid &&
					this.tiles.get(selectedTiles.get(i)).checkValidSettlementPlacement(corners.get(i));
    	}
		return settlementLocationIsValid;
	}

	private void distributeSetupResources(ArrayList<Integer> selectedTiles, Player currentPlayer) {
		for (int i = 0; i < selectedTiles.size(); i++) {
			Tile currentTile = this.tiles.get(selectedTiles.get(i));
			currentPlayer.addResource(currentTile.getType(), 1);
		}
	}

	private boolean placeRoad(HashMap<Integer, ArrayList<Integer>> tilesToCorners, HashMap<Integer, Integer> tileToRoadOrientation, GameStates gameState) {
		Road newRoad = new Road(this.turnController.getCurrentPlayer());
		
		Boolean validPlacement = false;
		for (int tileNum : tilesToCorners.keySet()) {
			Tile tileToCheck = this.tiles.get(tileNum);
			ArrayList<Integer> edge = tilesToCorners.get(tileNum);
			if (tileToCheck.checkValidRoadPlacement(edge, newRoad, gameState) != tileToCheck.checkValidRoadPlacement(edge, newRoad, GameStates.drop_road)) {
				validPlacement = false;
				break;
			}
			validPlacement = validPlacement || tileToCheck.checkValidRoadPlacement(edge, newRoad, gameState);
		}
		if (validPlacement) {
			if (gameState == GameStates.drop_road && !buyRoad()) {
					return false;
			}
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
	
	public int endTurnAndRoll() {
		Random random = new Random();
		int rolled = random.nextInt(6) + random.nextInt(6) + 2;
		distributeResources(rolled);
		return rolled;
	}

	public void distributeResources(int number) {
		for (Tile t : this.tiles) {
			if(t.getNumber() == number) {
				HashMap<Integer, Settlement> settlementsOnTile = t.getSettlements();
				for(Settlement settlement : settlementsOnTile.values()) {
					settlement.getOwner().addResource(t.getType(), 1);
				}
			}
		}
	}

	boolean buyRoad() {
		Player currentPlayer = this.turnController.getCurrentPlayer();
		if(currentPlayer.canBuyRoad()) {
			currentPlayer.removeResource(TileType.brick, 1);
			currentPlayer.removeResource(TileType.wood, 1);
			return true;
		}
		return false;
	}

	boolean buySettlement() {
		Player currentPlayer = this.turnController.getCurrentPlayer();
		if(currentPlayer.canBuySettlement()) {
			currentPlayer.removeResource(TileType.brick, 1);
			currentPlayer.removeResource(TileType.wood, 1);
			currentPlayer.removeResource(TileType.wool, 1);
			currentPlayer.removeResource(TileType.wheat, 1);
			currentPlayer.numSettlements++;
			return true;
		}
		return false;
	}

	public boolean buyDevelopmentCard() {
		Player currentPlayer = this.turnController.getCurrentPlayer();
		if(currentPlayer.canBuyDevelopmentCard()) {
			currentPlayer.removeResource(TileType.ore, 1);
			currentPlayer.removeResource(TileType.wool, 1);
			currentPlayer.removeResource(TileType.wheat, 1);
			currentPlayer.addDevelopmentCard(developmentCards.pop());
			return true;
		}
		return false;
	}
	
	public boolean tradeWithBank(HashMap<TileType, Integer> payment, TileType forThisType) {
		if(!payment.keySet().contains(forThisType) && this.turnController.getCurrentPlayer().canAffordTrade(payment)) {
			if(payment.values().stream().mapToInt(a -> a).sum() != 4) {
				return false;
			}
			for(TileType tt : payment.keySet()) {
				this.turnController.getCurrentPlayer().removeResource(tt, payment.get(tt));
			}
			this.turnController.getCurrentPlayer().addResource(forThisType, 1);
			return true;
		}
		return false;
	}

	public boolean buyCity() {
		Player currentPlayer = this.turnController.getCurrentPlayer();
		if (currentPlayer.canBuyCity()) {
			currentPlayer.removeResource(TileType.ore, 3);
			currentPlayer.removeResource(TileType.wheat, 2);
			currentPlayer.numSettlements--;
			currentPlayer.numCities++;
			return true;
		}
		return false;
	}

	public boolean addCityToTiles(ArrayList<Integer> tileNums, ArrayList<Integer> corners) {
		Player currentPlayer = this.turnController.getCurrentPlayer();
		ArrayList<Settlement> settlementsToConvert = new ArrayList<Settlement>();
		boolean canPlace = true;
		for (int i = 0; i < tileNums.size(); i++) {
			Tile tile = this.tiles.get(tileNums.get(i));
			Settlement settlement = tile.getSettlements().get(corners.get(i));
			settlementsToConvert.add(settlement);
			if (settlement == null || settlement.getOwner() != currentPlayer) {
				canPlace = false;
			}
		}
		if (canPlace) {
			buyCity();
			currentPlayer.alterVictoryPoints(VictoryPoints.city);
			for (Settlement s : settlementsToConvert) {
				s.upgradeToCity();
			}
		}
		return canPlace;
	}
}
