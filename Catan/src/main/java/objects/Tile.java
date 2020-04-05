package objects;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;

public class Tile {
    private TileType type;
    private int number;
    private int posInArray;
    private Point pos;
    private ArrayList<Point> corners;
    private Boolean isRobber;
    private Polygon hexagon;
    private HashMap<Integer, Settlement> hexCornerToSettlement;
    private HashMap<ArrayList<Integer>, Road> hexEdgeToRoad;
    
    /**
     * Constructor, with given params for its fields
     * @param x the x coordinate
     * @param y the y coordinate
     * @param n the number of the Tile
     * @param typ the type of the Tile
    */
    public Tile(Point p, int posInArray, int n, TileType typ) {
        this.isRobber = false;
        this.pos = p;
        this.posInArray = posInArray;
        this.number = n;
        this.type = typ;
        this.hexCornerToSettlement = new HashMap<>();
        this.hexEdgeToRoad = new HashMap<>();
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
    public int getPositionInArray(){
        return this.posInArray;
    }
    
    public Point getLocation(){
        return this.pos;
    }
    
    /*
    * Retrieve the tile type
    */
    public TileType getType(){
        return this.type;
    }
    
    /*
    * Retrieve the tile number
    */
    public int getNumber(){
        return this.number;
    }
    
    public void setRobber(){
        this.isRobber = true;
    }
    
    public Boolean isRobber(){
        return this.isRobber;
    }
    
    public void setHexagon(Polygon poly) {
    	this.hexagon = poly;
    }
    
    public Polygon getHexagon(){
    	return this.hexagon;
    }
    
    public void addSettlement(int corner, Settlement s) {
    	if(!this.hexCornerToSettlement.containsKey(corner)) {
    		this.hexCornerToSettlement.put(corner, s);
    	}
    }
    
    public HashMap<Integer, Settlement> getSettlements() {
    	return this.hexCornerToSettlement;
    }
    
    public void addRoad(int corner1, int corner2, Road r) {
    	ArrayList<Integer> edge = new ArrayList<>();
    	edge.add(corner1);
    	edge.add(corner2);
    	ArrayList<Integer> edge2 = new ArrayList<>();
    	edge.add(corner2);
    	edge.add(corner1);
    	if(!this.hexEdgeToRoad.containsKey(edge)) {
    		this.hexEdgeToRoad.put(edge, r);
    		this.hexEdgeToRoad.put(edge2, r);
    	}
    }
    
    public HashMap<ArrayList<Integer>, Road> getRoads() {
    	return this.hexEdgeToRoad;
    }
}