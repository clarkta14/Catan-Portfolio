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
    protected Stack<DevelopmentCard> developmentCards;
    public ArrayList<PortType> portTypes;
    public HashMap<TileType, PortType> resourceToPorts;
    private int[] portTiles = new int[] {0, 1, 6, 11, 15, 17, 16, 12, 3};
	private int[][] portCorners = new int[][] {{0,5}, {4,5}, {4,5}, {3,4}, {2,3}, {2,3}, {1,2}, {0,1}, {0,1}};
    protected int robber = -1;
	private ArrayList<DevelopmentCard> developmentCardsBoughtThisTurn;
    
    @SuppressWarnings("serial")
	public CatanBoard(PlayersController turnController){
    	this.turnController = turnController;
        this.tiles = new ArrayList<Tile>();
        this.developmentCardsBoughtThisTurn = new ArrayList<DevelopmentCard>();
        this.developmentCards = new Stack<DevelopmentCard>() {{
        	for(int i = 0; i < 14; i++) {
        		push(new KnightDevelopmentCard());
        	}
			for(int i = 0; i < 2; i++) {
				push(new MonopolyCard(turnController));
				push(new YearOfPlentyCard(turnController));
				push(new RoadBuildingCard());    		
			}
			for(int i = 0; i < 5; i++) {
				push(new VictoryPointDevelopmentCard());
			}
        }};
        createAndShufflePortTypes();
        Collections.shuffle(this.developmentCards);
        shuffleTiles();
    }
    
    private void createAndShufflePortTypes() {
    	this.portTypes = new ArrayList<PortType>();
    	this.resourceToPorts = new HashMap<TileType, PortType>();
    	for (int i = 0; i < 4; i++) {
    		portTypes.add(PortType.three);
    	}
    	portTypes.add(PortType.brick);
    	portTypes.add(PortType.wool);
    	portTypes.add(PortType.wood);
    	portTypes.add(PortType.ore);
    	portTypes.add(PortType.wheat);
    	
    	resourceToPorts.put(TileType.brick, PortType.brick);
    	resourceToPorts.put(TileType.wool, PortType.wool);
    	resourceToPorts.put(TileType.wood, PortType.wood);
    	resourceToPorts.put(TileType.ore, PortType.ore);
    	resourceToPorts.put(TileType.wheat, PortType.wheat);
    	Collections.shuffle(portTypes);
	}

	private void shuffleTiles() {
        ArrayList<TileType> types = createTileTypes();
        ArrayList<Integer> numbers = createTileNumbers();
        ArrayList<Point> positions = createTilePositions();
        
        Collections.shuffle(types);
        Collections.shuffle(numbers);
        
        for(int i = 0; i < 18; i++){
            this.tiles.add(new Tile(positions.get(i), numbers.get(i), types.get(i)));
        }
        
    	Tile desertTile = new Tile(positions.get(18), 7, TileType.desert);
    	desertTile.setRobber(true);
    	this.tiles.add(desertTile);
    	
    	swapTileForRobberPlacement();
    }

	private ArrayList<Point> createTilePositions() {
		ArrayList<Point> positions = new ArrayList<>();
        for(int x = 1; x < 6; x++){
            int ylow = -1;
            int yhigh = -1;
            if (x == 1) {
            	ylow = 1;
            	yhigh = 4;
            } else if (x == 2) {
            	ylow = 1;
            	yhigh = 5;
            } else if (x == 3) {
            	ylow = 1;
            	yhigh = 6;
            } else if (x == 4) {
            	ylow = 2;
            	yhigh = 6;
            } else {
            	ylow = 3;
            	yhigh = 6;
            } 
            
            for(int y = ylow; y < yhigh; y++){
                positions.add(new Point(x,y));
            }
        }
		return positions;
	}

	private ArrayList<Integer> createTileNumbers() {
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
		return numbers;
	}

	private ArrayList<TileType> createTileTypes() {
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
		return types;
	}
    
    public void swapTileForRobberPlacement() {
    	int tileIndexToSwapWith = new Random().nextInt(17);    	
    	Point temp = this.tiles.get(18).getLocation();
    	this.tiles.get(18).setLocation(this.tiles.get(tileIndexToSwapWith).getLocation());
    	this.tiles.get(tileIndexToSwapWith).setLocation(temp);
    	Collections.swap(this.tiles, tileIndexToSwapWith, 18);
    	this.robber = tileIndexToSwapWith;
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
			if (!currentPlayer.buySettlement()) {
				return false;
			}
		}
		
		for(int i = 0; i < selectedTiles.size(); i++) {
			this.tiles.get(selectedTiles.get(i)).addSettlement(corners.get(i), newlyAddedSettlement);
			checkForAndAddPort(selectedTiles.get(i), corners.get(i), newlyAddedSettlement);
		}
		if (gameState == GameStates.drop_settlement_setup_final) {
			distributeSetupResources(selectedTiles, currentPlayer);
		}
		currentPlayer.alterVictoryPoints(VictoryPoints.settlement);
		return true;
	}

	private void checkForAndAddPort(int tileNum, int cornerNum, Settlement settlement) {
		for (int i = 0; i < portTiles.length; i++) {
			for (int j = 0; j < portCorners[i].length; j++) {
				if (portTiles[i] == tileNum && portCorners[i][j] == cornerNum) {
					settlement.portType = portTypes.get(i);
					this.turnController.getCurrentPlayer().addTrade(portTypes.get(i));
				}
			}
		}
		
	}

	private boolean checkForValidSettlementPlacementConnectedToRoad(ArrayList<Integer> selectedTiles, ArrayList<Integer> corners, Player currentPlayer) {
		for(int i = 0; i < selectedTiles.size(); i++) {
			if(checkRoadAtCornerForGivenPlayerForGivenTile(currentPlayer, selectedTiles.get(i), corners.get(i))) {
				return true;
			}
		}
		return false;
	}

	private boolean checkRoadAtCornerForGivenPlayerForGivenTile(Player currentPlayer, int selectedTile, int corner) {
		return this.tiles.get(selectedTile).checkRoadAtCornerForGivenPlayer(corner, currentPlayer);
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
		
		boolean validPlacement = false;
		for (int tileNum : tilesToCorners.keySet()) {
			Tile tileToCheck = this.tiles.get(tileNum);
			ArrayList<Integer> edge = tilesToCorners.get(tileNum);
			if (tileToCheck.checkValidRoadPlacement(edge, newRoad, gameState) != tileToCheck.checkValidRoadPlacement(edge, newRoad, GameStates.drop_road)) {
				validPlacement = false;
				break;
			}
			if (tileToCheck.checkValidRoadPlacement(edge, newRoad, gameState)) {
				validPlacement = true;
			}
		}
		if (validPlacement) {
			if (gameState == GameStates.drop_road && !this.turnController.getCurrentPlayer().buyRoad()) {
				return false;
			}
			addRoadToTiles(newRoad, tilesToCorners, tileToRoadOrientation);
			this.turnController.addRoadForLongestRoad((int) tilesToCorners.keySet().toArray()[0], tilesToCorners.get(tilesToCorners.keySet().toArray()[0]));
			return true;
		}
		return false;
	}

	private void addRoadToTiles(Road newRoad, HashMap<Integer, ArrayList<Integer>> tilesToCorners, HashMap<Integer, Integer> tileToRoadOrientation) {
		for (int tileNum : tilesToCorners.keySet()) {
			ArrayList<Integer> corners = tilesToCorners.get(tileNum);
			int angle = tileToRoadOrientation.get(tileNum);
			newRoad.setAngle(angle);
			this.tiles.get(tileNum).addRoad(corners.get(0), corners.get(1), newRoad);
		}
	}
	
	public int endTurnAndRoll(Random random) {
		distributeDevelopmentCards();
		int rolled = random.nextInt(6) + random.nextInt(6) + 2;
		if(rolled != 7) {
			distributeResources(rolled);
		}
		distributeDevelopmentCards();
		return rolled;
	}

	public void distributeDevelopmentCards() {
		Player currentPlayer = this.turnController.getCurrentPlayer();
		for (DevelopmentCard card : this.developmentCardsBoughtThisTurn) {
			currentPlayer.addDevelopmentCard(card);
		}
		this.developmentCardsBoughtThisTurn.clear();
	}

	public void distributeResources(int number) {
		for (Tile t : this.tiles) {
			if(t.getNumber() == number && !t.isRobber()) {
				HashMap<Integer, Settlement> settlementsOnTile = t.getSettlements();
				for(Settlement settlement : settlementsOnTile.values()) {
					if (settlement.isCity()) {
						settlement.getOwner().addResource(t.getType(), 2);
					} else {
						settlement.getOwner().addResource(t.getType(), 1);
					}
				}
			}
		}
	}

	public boolean buyDevelopmentCard() {
		Player currentPlayer = this.turnController.getCurrentPlayer();
		if(currentPlayer.canBuyDevelopmentCard()) {
			currentPlayer.removeResourcesForDevCard();
			DevelopmentCard boughtCard = developmentCards.pop();
			if (boughtCard.getDevelopmentCardType() == DevelopmentCardType.victory_point) {
				currentPlayer.addDevelopmentCard(boughtCard);
			} else {
				this.developmentCardsBoughtThisTurn.add(boughtCard);
			}
			return true;
		}
		return false;
	}
	
	public boolean tradeWithBank(HashMap<TileType, Integer> payment, TileType forThisType) {
		if(!payment.keySet().contains(forThisType) && this.turnController.getCurrentPlayer().canAffordTrade(payment)) {
			Player currentPlayer = this.turnController.getCurrentPlayer();
			boolean canThreeTrade = currentPlayer.canPortTrade(PortType.three) && payment.values().stream().mapToInt(a -> a).sum() == 3;
			if(payment.values().stream().mapToInt(a -> a).sum() != 4 && !canThreeTrade) {
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

	public boolean addCityToTiles(ArrayList<Integer> tileNums, ArrayList<Integer> corners) {
		Player currentPlayer = this.turnController.getCurrentPlayer();
		ArrayList<Settlement> settlementsToConvert = new ArrayList<Settlement>();
		boolean canPlace = true;
		for (int i = 0; i < tileNums.size(); i++) {
			Tile tile = this.tiles.get(tileNums.get(i));
			Settlement settlement = tile.getSettlements().get(corners.get(i));
			settlementsToConvert.add(settlement);
			if (settlement == null || settlement.getOwner() != currentPlayer || settlement.isCity()) {
				canPlace = false;
			}
		}
		if (canPlace && currentPlayer.buyCity()) {
			currentPlayer.alterVictoryPoints(VictoryPoints.city);
			for (Settlement s : settlementsToConvert) {
				s.upgradeToCity();
			}
			return true;
		}
		return false;
	}

	public boolean tradeWithPlayer(int i, HashMap<TileType, Integer> giveThese, HashMap<TileType, Integer> forThese) {
		try {
			Player toTradeWith = this.turnController.getPlayer(i);
			if(turnController.getCurrentPlayer().canAffordTrade(giveThese) && toTradeWith.canAffordTrade(forThese)) {
				for(TileType tt : giveThese.keySet()) {
					this.turnController.getCurrentPlayer().removeResource(tt, giveThese.get(tt));
					toTradeWith.addResource(tt, giveThese.get(tt));
				}
				for(TileType tt : forThese.keySet()) {
					this.turnController.getCurrentPlayer().addResource(tt, forThese.get(tt));
					toTradeWith.removeResource(tt, forThese.get(tt));
				}
				return true;
			}
			return false;
		}catch(IndexOutOfBoundsException oobe) {
			return false;
		}
	}

	public boolean portTrade(TileType payment, TileType wants) {
		Player currentPlayer = this.turnController.getCurrentPlayer();
		if (!currentPlayer.canPortTrade(this.resourceToPorts.get(payment)) || currentPlayer.getResourceCount(payment) < 2) {
			return false;
		}
		currentPlayer.removeResource(payment, 2);
		currentPlayer.addResource(wants, 1);
		return true;
	}
	
	public void moveRobber(Tile clicked) {
		this.tiles.get(robber).setRobber(false);
		this.robber = this.tiles.indexOf(clicked);
		this.tiles.get(this.tiles.indexOf(clicked)).setRobber(true);
	}
	
	public ArrayList<Player> getPlayersWithSettlementOnTile(Tile t){
		ArrayList<Player> playersWithSettlements = new ArrayList<>();
		for(Settlement s : t.getSettlements().values()) {
			if(!playersWithSettlements.contains(s.getOwner())) {
				playersWithSettlements.add(s.getOwner());
			}
		}
		return playersWithSettlements;
	}

	public ArrayList<Player> getPlayersWithSettlementOnRobberTile() {
		return getPlayersWithSettlementOnTile(this.tiles.get(robber));
	}
	
	public void stealRandomResourceFromOpposingPlayer(Player currentPlayer, Player opposingPlayer) {
		while(opposingPlayer.hasAnyResources()) {
			try {
				int randomInteger = new Random().nextInt(TileType.values().length);
				TileType randomResource = TileType.values()[randomInteger];
				currentPlayer.stealResourceFromOpposingPlayer(randomResource, opposingPlayer);
				break;
			} catch (IndexOutOfBoundsException e) { }
		}
	}
}
