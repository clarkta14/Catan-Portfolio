package objects;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;

public class Tile {
	private TileType type;
	private int number;
	private Point pos;
	private ArrayList<Point> corners;
	private Boolean isRobber;
	private Polygon hexagon;
	private HashMap<Integer, Settlement> hexCornerToSettlement;
	private HashMap<ArrayList<Integer>, Road> hexEdgeToRoad;

	public Tile(Point p, int number, TileType typ) {
		this.isRobber = false;
		this.pos = p;
		this.number = number;
		this.type = typ;
		this.hexCornerToSettlement = new HashMap<>();
		this.hexEdgeToRoad = new HashMap<>();
	}

	/*
	 * Hold the polygon this tile belongs to. For retrieving coordinates of polygon
	 * corners to draw settlements
	 */
	public void setHexCorners(ArrayList<Point> poly) {
		this.corners = poly;
	}

	public ArrayList<Point> getHexCorners() {
		return this.corners;
	}

	public void setLocation(Point location) {
		this.pos = location;
	}
	
	public Point getLocation() {
		return this.pos;
	}

	/*
	 * Retrieve the tile type
	 */
	public TileType getType() {
		return this.type;
	}

	/*
	 * Retrieve the tile number
	 */
	public int getNumber() {
		return this.number;
	}

	public void setRobber(boolean isRobber) {
		this.isRobber = isRobber;
	}

	public Boolean isRobber() {
		return this.isRobber;
	}

	public void setHexagon(Polygon poly) {
		this.hexagon = poly;
	}

	public Polygon getHexagon() {
		return this.hexagon;
	}
	
	public boolean checkValidSettlementPlacement(int corner, Settlement settlement) {
		if (this.hexCornerToSettlement.containsKey(corner)) {
			return false;
		}
		if (this.hexCornerToSettlement.containsKey(corner+1)) {
			return false;
		}
		if (this.hexCornerToSettlement.containsKey((corner+5)%6)) {
			return false;
		}
		return true;
	}

	public void addSettlement(int corner, Settlement s) {
			this.hexCornerToSettlement.put(corner, s);
	}

	public HashMap<Integer, Settlement> getSettlements() {
		return this.hexCornerToSettlement;
	}

	public void addRoad(int corner1, int corner2, Road r) {
		ArrayList<Integer> edge = new ArrayList<>();
		edge.add(corner1);
		edge.add(corner2);
		ArrayList<Integer> edge2 = new ArrayList<>();
		edge2.add(corner2);
		edge2.add(corner1);
		if (!this.hexEdgeToRoad.containsKey(edge)) {
			this.hexEdgeToRoad.put(edge, r);
			this.hexEdgeToRoad.put(edge2, r);
		}
	}
	
	public HashMap<ArrayList<Integer>, Road> getRoads() {
		return this.hexEdgeToRoad;
	}

	public boolean checkValidRoadPlacement(int corner1, int corner2, Road roadToCheck) {
		ArrayList<Integer> newEdge = new ArrayList<Integer>();
		newEdge.add(corner1); newEdge.add(corner2);
		if (this.hexEdgeToRoad.containsKey(newEdge)) {
			return false;
		}
		for (ArrayList<Integer> edgeOfRoad : hexEdgeToRoad.keySet()) {
			Road road = hexEdgeToRoad.get(edgeOfRoad);
			if (road.getOwner() != roadToCheck.getOwner()) {
				continue;
			}
			for (int corner : edgeOfRoad) {
				if (corner == corner1 || corner == corner2) {
					return true;
				}
			}
		}
		for (int cornerOfSettlement : hexCornerToSettlement.keySet()) {
			Settlement settlement = hexCornerToSettlement.get(cornerOfSettlement);
			if (settlement.getOwner() != roadToCheck.getOwner()) {
				continue;
			}
			if (corner1 == cornerOfSettlement || corner2 == cornerOfSettlement) {
				return true;
			}
		}

		return false;
	}
}