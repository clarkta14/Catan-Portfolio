package objects;

public enum VictoryPoints {
	settlement(1),
	city(1),
	devolopment_card(1),
	largest_army_add(2),
	largest_army_remove(-2),
	longest_road_add(2),
	longest_road_remove(-2);

	private int numVal;

	VictoryPoints(int numVal) {
		this.numVal = numVal;
	}
	
	public int getNumVal() {
		return this.numVal;
	}
}
