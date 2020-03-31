package objects;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Player {
	private Color color;
	private ArrayList<Settlement> settlements;
	
	public Player(Color color) {
		this.color = color;
		this.settlements = new ArrayList<>();
	}

	public Object getColor() {
		return this.color;
	}
	
	public void addSettlement(Point loc) {
		this.settlements.add(new Settlement(loc));
	}

	public ArrayList<Settlement> getSettlements() {
		return this.settlements;
	}

}
