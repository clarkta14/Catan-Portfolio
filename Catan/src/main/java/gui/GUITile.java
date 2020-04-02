package gui;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;

import objects.ITile;
import objects.Road;
import objects.Settlement;
import objects.Tile;
import objects.TileType;

/**
 *
 * @author Indresh
 */
public class GUITile implements ITile{
    private ArrayList<Point> corners;
    private Polygon hexagon;
    private Point point;
    private Tile tile;

    
    public GUITile(Point p, Tile tile) {
    	this.point = p;
    	this.tile = tile;
    }
    
    /*
    * Hold the polygon this tile belongs to. 
    * For retrieving coordinates of polygon corners to draw settlements
    */
    public void setHexCorners(ArrayList<Point> poly){
        this.corners = poly;
    }
    
     public ArrayList<Point> getHexCorners(){
         return this.corners;
     }
    
    /*
    * Hold the coordinates to the center of the tile for calculations.
    */
    public Point getPosition(){
        return this.point;
    }
    
    
    public void setHexagon(Polygon poly) {
    	this.hexagon = poly;
    }
    
    public Polygon getHexagon(){
    	return this.hexagon;
    }

	@Override
	public TileType getType() {
		return tile.getType();
	}

	@Override
	public int getBoardPosition() {
		return tile.getBoardPosition();
	}

	@Override
	public int getNumber() {
		return tile.getNumber();
	}

	@Override
	public void setRobber() {
		tile.setRobber();
	}

	@Override
	public Boolean isRobber() {
		return tile.isRobber();
	}

	@Override
	public void addSettlement(int corner, Settlement s) {
		tile.addSettlement(corner, s);
	}

	@Override
	public HashMap<Integer, Settlement> getSettlements() {
		return tile.getSettlements();
	}

	@Override
	public void addRoad(int corner1, int corner2, Road r) {
		tile.addRoad(corner1, corner2, r);
		
	}

	@Override
	public HashMap<ArrayList<Integer>, Road> getRoads() {
		return tile.getRoads();
	}
    
}

