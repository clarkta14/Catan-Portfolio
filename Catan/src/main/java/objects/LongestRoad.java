package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class LongestRoad {

	private HashMap<Integer, ArrayList<LinkedList<Integer>>> roads;

	public LongestRoad(int numOfPlayers) {
		this.roads = new HashMap<>();
		for (int i = 0; i < numOfPlayers; i++) {
			this.roads.put(i, new ArrayList<>());
		}
	}

	public void addRoadForPlayer(int playerNum, ArrayList<Integer> tiles, ArrayList<Integer> corners) {
		int vertex1 = Vertex.getVertex(tiles.get(0), corners.get(0));
		int vertex2 = Vertex.getVertex(tiles.get(0), corners.get(1));
		boolean added = false;
		ArrayList<LinkedList<Integer>> pathList = this.roads.get(playerNum);
		for (int index = pathList.size() - 1; index > -1; index--) {
			LinkedList<Integer> path = pathList.get(index);
			
			if (path.getFirst() == vertex1) {
				merge(path, vertex1, vertex2, playerNum);
				path.addFirst(vertex2);
				added = true;
			} else if (path.getFirst() == vertex2) {
				merge(path, vertex2, vertex1, playerNum);
				path.addFirst(vertex1);
				added = true;
			} else if (path.getLast() == vertex1) {
				merge(path, vertex1, vertex2, playerNum);
				path.addLast(vertex2);
				added = true;
				break;
			} else if (path.getLast() == vertex2) {
				merge(path, vertex2, vertex1, playerNum);
				path.addLast(vertex1);
				added = true;
			} else {
				boolean inCircle = false;
				if(path.getFirst() == path.getLast()) {
					inCircle = true;
				}
				for (int vertex : path) {
					if (vertex == vertex1) {
						branchCondition(path,vertex1,vertex2,playerNum,inCircle);
						added = true;
						break;
					} else if (vertex == vertex2) {
						branchCondition(path,vertex2,vertex1,playerNum,inCircle);
						added = true;
						break;
					}
				}
			}
		}

		if (!added) {
			LinkedList<Integer> newPath = new LinkedList<>();
			newPath.add(vertex1);
			newPath.add(vertex2);
			ArrayList<LinkedList<Integer>> newPathList = this.roads.get(playerNum);
			newPathList.add(newPath);
			this.roads.put(playerNum, newPathList);
		}
		
		System.out.println(this.roads.get(playerNum));
	}

	@SuppressWarnings("unchecked")
	private void merge(LinkedList<Integer> path, int vertex1, int vertex2, int playerNum) {
		ArrayList<LinkedList<Integer>> pathList = this.roads.get(playerNum);
		for(int index = pathList.size() - 1; index > -1; index--) {
			LinkedList<Integer> otherPath = pathList.get(index);
			if(otherPath.getFirst() == vertex2) {
				LinkedList<Integer> newPath = (LinkedList<Integer>) path.clone();
				for(int elementFromSecondPath : otherPath) {
					newPath.addFirst(elementFromSecondPath);
				}
				ArrayList<LinkedList<Integer>> newPathList = this.roads.get(playerNum);
				newPathList.add(newPath);
				this.roads.put(playerNum, newPathList);
				break;
			}else if(otherPath.getLast() == vertex2) {
				LinkedList<Integer> newPath = (LinkedList<Integer>) path.clone();
				boolean first = false;
				if(path.getFirst() == vertex1) {
					first = true;
				}
				for(int i = otherPath.size() - 1; i > -1; i--) {
					int elementFromSecondPath = otherPath.get(i);
					if(first) {
						newPath.addFirst(elementFromSecondPath);
					}else {
						newPath.addLast(elementFromSecondPath);
					}
				}
				ArrayList<LinkedList<Integer>> newPathList = this.roads.get(playerNum);
				if(!moreThanOneDuplicates(newPath)) {
					newPathList.add(newPath);
				}
				this.roads.put(playerNum, newPathList);
				break;
			}
		}
	}

	private boolean moreThanOneDuplicates(LinkedList<Integer> newPath) {
		int c = 0;
		HashSet<Integer> seen = new HashSet<>();
		for(int i : newPath) {
			if(!seen.add(i)) {
				c++;
			}
		}
		return (c > 1) ? true : false;
	}

	private void branchCondition(LinkedList<Integer> path, int vertex1, int vertex2, int playerNum, boolean inCircle) {
		LinkedList<Integer> bottomUp = new LinkedList<>();
		LinkedList<Integer> topDown = new LinkedList<>();
		boolean hitVertexForTop = false;
		boolean hitVertexForBottom = false;
		for (int i = 0; i < path.size(); i++) {
			if (path.get(i) != vertex1 && !hitVertexForBottom) {
				bottomUp.add(path.get(i));
			} else if(!hitVertexForBottom){
				bottomUp.add(path.get(i));
				hitVertexForBottom = true;
			}
			if (path.get(path.size() - i - 1) != vertex1 && !hitVertexForTop) {
				topDown.add(path.get(path.size() - i - 1));
			} else if(!hitVertexForTop){
				topDown.add(path.get(path.size() - i - 1));
				hitVertexForTop = true;
			}
			if (hitVertexForBottom && hitVertexForTop) {
				break;
			}
		}
		bottomUp.add(vertex2);
		topDown.add(vertex2);
		ArrayList<LinkedList<Integer>> newPathList = this.roads.get(playerNum);
		if(inCircle) {
			bottomUp.addFirst(path.get(path.size()-2));
			topDown.addFirst(path.get(1));
		}
		if (hitVertexForBottom) {
			newPathList.add(bottomUp);
			newPathList.add(topDown);
		}
		this.roads.put(playerNum, newPathList);
	}

	public int getLongestRoadForPlayer(int playerNum) {
		int longestRoad = -1;
		for (LinkedList<Integer> path : this.roads.get(playerNum)) {
			if (longestRoad < path.size() - 1) {
				longestRoad = path.size() - 1;
			}
		}
		return longestRoad;
	}
}
