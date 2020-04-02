package objects;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Indresh
 */
public class Tile implements ITile{
    private TileType type;
    private int number;
    private int boardPos;
    private Boolean isRobber;
    private HashMap<Integer, Settlement> hexCornerToSettlement;
    private HashMap<ArrayList<Integer>, Road> hexEdgeToRoad;
    
    /**
     * Constructor, with given params for its fields
     * @param x the x coordinate
     * @param y the y coordinate
     * @param n the number of the Tile
     * @param typ the type of the Tile
    */
    public Tile(int position, int n, TileType typ) {
        this.isRobber = false;    
        this.boardPos = position;
        this.number = n;
        this.type = typ;
        this.hexCornerToSettlement = new HashMap<>();
        this.hexEdgeToRoad = new HashMap<>();
    }
    
    /*
    * Retrieve the tile type
    */
    public TileType getType(){
        return this.type;
    }
    
    public int getBoardPosition() {
    	return this.boardPos;
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
    	this.hexEdgeToRoad.put(edge, r);
    }
    
    public HashMap<ArrayList<Integer>, Road> getRoads() {
    	return this.hexEdgeToRoad;
    }
}

