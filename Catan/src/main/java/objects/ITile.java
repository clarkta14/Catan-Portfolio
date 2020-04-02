package objects;

import java.util.ArrayList;
import java.util.HashMap;

public interface ITile {

	public TileType getType();

	public int getBoardPosition();

	public int getNumber();

	public void setRobber();

	public Boolean isRobber();

	public void addSettlement(int corner, Settlement s);

	public HashMap<Integer, Settlement> getSettlements();

	public void addRoad(int corner1, int corner2, Road r);

	public HashMap<ArrayList<Integer>, Road> getRoads();
}
