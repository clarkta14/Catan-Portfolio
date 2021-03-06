package objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

public class Player {
	private Color color;
	private HashMap<TileType, Integer> resources;
	protected HashMap<DevelopmentCardType, Stack<DevelopmentCard>> developmentCards;
	private ArrayList<PortType> validTrades = new ArrayList<PortType>();
	private int victoryPoints = 0;
	public int numSettlements = 2;
	public int numCities = 0;
	public int knightsPlayed = 0;
		
	@SuppressWarnings("serial")
	public Player(Color color) {
		this.color = color;
		this.resources = new HashMap<TileType, Integer>() {{
			for(TileType type : TileType.values()) {
				put(type, 0);
			}
		}};
		this.resources.remove(TileType.desert);
		this.developmentCards = new HashMap<DevelopmentCardType, Stack<DevelopmentCard>>() {{
			for(DevelopmentCardType type : DevelopmentCardType.values()) {
				Stack<DevelopmentCard> s = new Stack<>();
				put(type, s);
			}
		}};
	}

	public Color getColor() {
		return this.color;
	}

	public void addResource(TileType type, int numberOfResource) {
		if(type == TileType.desert) {
			return;
		}
		if(numberOfResource < 0 || this.resources.get(type) == Integer.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		int prevNumResource = this.resources.get(type);
		this.resources.replace(type, prevNumResource + numberOfResource);
	}

	public void removeResource(TileType type, int numberOfResource) {
		if(type == TileType.desert) return;
		if(numberOfResource < 0) {
			throw new IllegalArgumentException();
		}
		int totalNumResource = this.resources.get(type) - numberOfResource;
		if(totalNumResource < 0) {
			throw new IndexOutOfBoundsException();
		}
		this.resources.replace(type, totalNumResource);
	}

	public boolean canBuyRoad() {
		return this.resources.get(TileType.brick) > 0 && this.resources.get(TileType.wood) > 0;
	}
	
	public boolean canBuySettlement() {
		if (this.numSettlements >= 5) {
			return false;
		} else {
			return this.resources.get(TileType.brick) > 0 && this.resources.get(TileType.wood) > 0 && this.resources.get(TileType.wool) > 0 && this.resources.get(TileType.wheat) > 0;
		}
	}

	public boolean canBuyDevelopmentCard() {
		return this.resources.get(TileType.ore) > 0 && this.resources.get(TileType.wool) > 0 && this.resources.get(TileType.wheat) > 0;
	}

	public void addDevelopmentCard(DevelopmentCard card) {
		DevelopmentCardType cardType = card.getDevelopmentCardType();
		Stack<DevelopmentCard> cards = this.developmentCards.get(cardType);
		cards.push(card);
		if(DevelopmentCardType.victory_point == cardType) {
			alterVictoryPoints(VictoryPoints.devolopment_card);
		}
		this.developmentCards.replace(cardType, cards);
	}

	public DevelopmentCard removeDevelopmentCard(DevelopmentCardType cardType) {
		return this.developmentCards.get(cardType).pop();
	}
	
	public int getDevelopmentCardCount(DevelopmentCardType cardType) {
		return this.developmentCards.get(cardType).size();
	}
	
	public boolean hasAnyResources() {
		for(Entry<TileType, Integer> resourceSet: resources.entrySet()) {
	        if (resourceSet.getValue() != 0) return true;
	    }
		return false;
	}
	
	public boolean canAffordTrade(HashMap<TileType, Integer> payment) { 
		for(TileType tt : payment.keySet()) {
			if(this.resources.get(tt) < payment.get(tt)) {
				return false;
			}
		}
		return true;
	}
	
	public int getResourceCount(TileType type) {
		if(type == TileType.desert) return 0;
		return this.resources.get(type);
	}
	
	public int getTotalResourceCount() {
		int total = 0;
		for (Integer i: this.resources.values()) {
			total += i;
		}
		return total;
	}
	
	public boolean hasSufficentResource(TileType type, int hasAtLeast) {
		if(type == TileType.desert) {
			return true;
		}
		if(this.resources.get(type) < hasAtLeast) {
			return false;
		}
		return true;
	}
	
	public int getNumberOfVictoryPoints() {
		return this.victoryPoints;
	}
	
	public void alterVictoryPoints(VictoryPoints reason) {
		this.victoryPoints += reason.getNumVal();
	}
	
	public boolean isVictor() {
		return this.victoryPoints >= 10;
	}
	
	public boolean buyRoad() {
		if(this.canBuyRoad()) {
			this.removeResource(TileType.brick, 1);
			this.removeResource(TileType.wood, 1);
			return true;
		}
		return false;
	}
	
	public boolean buySettlement() {
		if(this.canBuySettlement()) {
			this.removeResource(TileType.brick, 1);
			this.removeResource(TileType.wood, 1);
			this.removeResource(TileType.wool, 1);
			this.removeResource(TileType.wheat, 1);
			this.numSettlements++;
			return true;
		}
		return false;
	}
	
	public boolean buyCity() {
		if (this.canBuyCity()) {
			this.removeResource(TileType.ore, 3);
			this.removeResource(TileType.wheat, 2);
			this.numSettlements--;
			this.numCities++;
			return true;
		}
		return false;
	}

	public boolean canBuyCity() {
		if (numCities >= 5 || numSettlements == 0) {
			return false;
		}
		return resources.get(TileType.wheat) >= 2 && resources.get(TileType.ore) >= 3;
	}

	public void addTrade(PortType tradeType) {
		if (!this.validTrades.contains(tradeType)) {
			this.validTrades.add(tradeType);
		}
		
	}

	public boolean canPortTrade(PortType tradeType) {
		return this.validTrades.contains(tradeType);
	}
	
	public void stealResourceFromOpposingPlayer(TileType resource, Player opposingPlayer) {
		if (opposingPlayer.getResourceCount(resource) == 0) {
			throw new IndexOutOfBoundsException();
		}
		opposingPlayer.removeResource(resource, 1);
		this.addResource(resource, 1);
	}
	
	public boolean discardForRobber(HashMap<TileType, Integer> resourcesToDiscard) {
		resourcesToDiscard.remove(TileType.desert);
		
		if(this.getTotalResourceCount() < 8) {
			return true;
		}
		
		for(TileType resource: resourcesToDiscard.keySet()) {
			if (!this.hasSufficentResource(resource, resourcesToDiscard.get(resource)))
				return false;
		}
		
		int totalToDiscard = 0;
		for(int i: resourcesToDiscard.values()) {
			totalToDiscard += i;
		}
		
		if(totalToDiscard == Math.floor(this.getTotalResourceCount() / 2)) {
			for(TileType resource: resourcesToDiscard.keySet()) {
				this.removeResource(resource, resourcesToDiscard.get(resource));
			}
			return true;
		}
		
		return false;
	}

	public void removeResourcesForDevCard() {
		this.removeResource(TileType.ore, 1);
		this.removeResource(TileType.wool, 1);
		this.removeResource(TileType.wheat, 1);
	}
}
