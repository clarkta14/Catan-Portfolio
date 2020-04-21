package objects;

import java.awt.Color;
import java.util.HashMap;
import java.util.Stack;

public class Player {
	private Color color;
	private HashMap<TileType, Integer> resources;
	protected HashMap<DevelopmentCardType, Stack<DevelopmentCard>> developmentCards;
	private int victoryPoints = 0;
		
	@SuppressWarnings("serial")
	public Player(Color color) {
		this.color = color;
		this.resources = new HashMap<TileType, Integer>() {{
			for(TileType type : TileType.values()) {
				put(type, 0);
			}
		}};
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
		return this.resources.get(TileType.brick) > 0 && this.resources.get(TileType.wood) > 0 && this.resources.get(TileType.wool) > 0 && this.resources.get(TileType.wheat) > 0;
	}

	public boolean canBuyDevelopmentCard() {
		return this.resources.get(TileType.ore) > 0 && this.resources.get(TileType.wool) > 0 && this.resources.get(TileType.wheat) > 0;
	}

	public void addDevelopmentCard(DevelopmentCardType cardType) {
		Stack<DevelopmentCard> cards = this.developmentCards.get(cardType);
		if(DevelopmentCardType.knight == cardType) {
			cards.push(new KnightDevelopmentCard());
		} else if(DevelopmentCardType.progress == cardType) {
			cards.push(new ProgressDevelopmentCard());
		} else if(DevelopmentCardType.victory_point == cardType) {
			cards.push(new VictoryPointDevelopmentCard());
		}
		this.developmentCards.replace(cardType, cards);
	}

	public DevelopmentCard removeDevelopmentCard(DevelopmentCardType cardType) {
		return this.developmentCards.get(cardType).pop();
	}
	
	public int getDevelopmentCardCount(DevelopmentCardType cardType) {
		return this.developmentCards.get(cardType).size();
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
		return this.resources.get(type);
	}
	
	public int getNumberOfVictoryPoints() {
		return this.victoryPoints;
	}
	
	public void alterVictoryPoints(VictoryPoints reason) {
		switch (reason) {
			case settlement:
				this.victoryPoints++;
				break;
			case city:
				this.victoryPoints++;
				break;
			case devolopment_card:
				this.victoryPoints++;
		}
	}
}
