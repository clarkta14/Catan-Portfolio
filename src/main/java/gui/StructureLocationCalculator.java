package gui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class StructureLocationCalculator {
	
	private double sqrt3div2;
	private int heightMargin;
	private int settlementSize;
	private int hexagonSide;
	private int widthMargin;
	
	public StructureLocationCalculator(int heightMargin, double sqrt3div2) {
		this.heightMargin = heightMargin;
		this.sqrt3div2 = sqrt3div2;
	}

	public ArrayList<ArrayList<Integer>> getStructureLocation(Point p, int settlementSize, int hexagonSide, int widthMargin) {
		double x = p.getX();
		double y = p.getY();
		ArrayList<Integer> tile = new ArrayList<>();
		ArrayList<Integer> corner = new ArrayList<>();
		
		this.settlementSize = settlementSize;
		this.hexagonSide = hexagonSide;
		this.widthMargin = widthMargin;
		
		if (checkIfWithinColumn(x, 0)) {
			if (heightMargin + 7 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 7 * hexagonSide / 2 + settlementSize) {
				tile.add(2);
				corner.add(4);
			} else if (heightMargin + 9 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 9 * hexagonSide / 2 + settlementSize) {
				tile.add(2);
				corner.add(5);
			}
		} else if (checkIfWithinColumn(x, 1)) {
			if (heightMargin + 2 * hexagonSide - settlementSize < y
					&& y < heightMargin + 2 * hexagonSide + settlementSize) {
				tile.add(6);
				corner.add(4);
			} else if (heightMargin + 3 * hexagonSide - settlementSize < y
					&& y < heightMargin + 3 * hexagonSide + settlementSize) {
				tile.add(6);
				corner.add(5);
				tile.add(2);
				corner.add(3);
			} else if (heightMargin + 5 * hexagonSide - settlementSize < y
					&& y < heightMargin + 5 * hexagonSide + settlementSize) {
				tile.add(1);
				corner.add(4);
				tile.add(2);
				corner.add(0);
			} else if (heightMargin + 6 * hexagonSide - settlementSize < y
					&& y < heightMargin + 6 * hexagonSide + settlementSize) {
				tile.add(1);
				corner.add(5);
			}
		} else if (checkIfWithinColumn(x, 2)) {
			if (heightMargin + hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + hexagonSide / 2 + settlementSize) {
				tile.add(11);
				corner.add(4);
			} else if (heightMargin + 3 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 3 * hexagonSide / 2 + settlementSize) {
				tile.add(11);
				corner.add(5);
				tile.add(6);
				corner.add(3);
			} else if (heightMargin + 7 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 7 * hexagonSide / 2 + settlementSize) {
				tile.add(6);
				corner.add(0);
				tile.add(2);
				corner.add(2);
				tile.add(5);
				corner.add(4);
			} else if (heightMargin + 9 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 9 * hexagonSide / 2 + settlementSize) {
				tile.add(2);
				corner.add(1);
				tile.add(5);
				corner.add(5);
				tile.add(1);
				corner.add(3);
			} else if (heightMargin + 13 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 13 * hexagonSide / 2 + settlementSize) {
				tile.add(1);
				corner.add(0);
				tile.add(0);
				corner.add(4);
			} else if (heightMargin + 15 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 15 * hexagonSide / 2 + settlementSize) {
				tile.add(0);
				corner.add(5);
			}
		} else if (checkIfWithinColumn(x, 3)) {
			if (heightMargin - settlementSize < y && y < heightMargin + settlementSize) {
				tile.add(11);
				corner.add(3);
			} else if (heightMargin + 2 * hexagonSide - settlementSize < y
					&& y < heightMargin + 2 * hexagonSide + settlementSize) {
				tile.add(11);
				corner.add(0);
				tile.add(6);
				corner.add(2);
				tile.add(10);
				corner.add(4);
			} else if (heightMargin + 3 * hexagonSide - settlementSize < y
					&& y < heightMargin + 3 * hexagonSide + settlementSize) {
				tile.add(6);
				corner.add(1);
				tile.add(10);
				corner.add(5);
				tile.add(5);
				corner.add(3);
			} else if (heightMargin + 5 * hexagonSide - settlementSize < y
					&& y < heightMargin + 5 * hexagonSide + settlementSize) {
				tile.add(5);
				corner.add(0);
				tile.add(1);
				corner.add(2);
				tile.add(4);
				corner.add(4);
			} else if (heightMargin + 6 * hexagonSide - settlementSize < y
					&& y < heightMargin + 6 * hexagonSide + settlementSize) {
				tile.add(1);
				corner.add(1);
				tile.add(4);
				corner.add(5);
				tile.add(0);
				corner.add(3);
			} else if (heightMargin + 8 * hexagonSide - settlementSize < y
					&& y < heightMargin + 8 * hexagonSide + settlementSize) {
				tile.add(0);
				corner.add(0);
			}
		} else if (checkIfWithinColumn(x, 4)) {
			if (heightMargin + hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + hexagonSide / 2 + settlementSize) {
				tile.add(11);
				corner.add(2);
				tile.add(15);
				corner.add(4);
			} else if (heightMargin + 3 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 3 * hexagonSide / 2 + settlementSize) {
				tile.add(11);
				corner.add(1);
				tile.add(15);
				corner.add(5);
				tile.add(10);
				corner.add(3);
			} else if (heightMargin + 7 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 7 * hexagonSide / 2 + settlementSize) {
				tile.add(10);
				corner.add(0);
				tile.add(5);
				corner.add(2);
				tile.add(9);
				corner.add(4);
			} else if (heightMargin + 9 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 9 * hexagonSide / 2 + settlementSize) {
				tile.add(5);
				corner.add(1);
				tile.add(9);
				corner.add(5);
				tile.add(4);
				corner.add(3);
			} else if (heightMargin + 13 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 13 * hexagonSide / 2 + settlementSize) {
				tile.add(4);
				corner.add(0);
				tile.add(0);
				corner.add(2);
				tile.add(3);
				corner.add(4);
			} else if (heightMargin + 15 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 15 * hexagonSide / 2 + settlementSize) {
				tile.add(0);
				corner.add(1);
				tile.add(3);
				corner.add(5);
			}
		} else if (checkIfWithinColumn(x, 5)) {
			if (heightMargin - settlementSize < y && y < heightMargin + settlementSize) {
				tile.add(15);
				corner.add(3);
			} else if (heightMargin + 2 * hexagonSide - settlementSize < y
					&& y < heightMargin + 2 * hexagonSide + settlementSize) {
				tile.add(15);
				corner.add(0);
				tile.add(10);
				corner.add(2);
				tile.add(14);
				corner.add(4);
			} else if (heightMargin + 3 * hexagonSide - settlementSize < y
					&& y < heightMargin + 3 * hexagonSide + settlementSize) {
				tile.add(10);
				corner.add(1);
				tile.add(14);
				corner.add(5);
				tile.add(9);
				corner.add(3);
			} else if (heightMargin + 5 * hexagonSide - settlementSize < y
					&& y < heightMargin + 5 * hexagonSide + settlementSize) {
				tile.add(9);
				corner.add(0);
				tile.add(4);
				corner.add(2);
				tile.add(8);
				corner.add(4);
			} else if (heightMargin + 6 * hexagonSide - settlementSize < y
					&& y < heightMargin + 6 * hexagonSide + settlementSize) {
				tile.add(4);
				corner.add(1);
				tile.add(8);
				corner.add(5);
				tile.add(3);
				corner.add(3);
			} else if (heightMargin + 8 * hexagonSide - settlementSize < y
					&& y < heightMargin + 8 * hexagonSide + settlementSize) {
				tile.add(3);
				corner.add(0);
			}
		} else if (checkIfWithinColumn(x, 6)) {
			if (heightMargin + hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + hexagonSide / 2 + settlementSize) {
				tile.add(15);
				corner.add(2);
				tile.add(18);
				corner.add(4);
			} else if (heightMargin + 3 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 3 * hexagonSide / 2 + settlementSize) {
				tile.add(15);
				corner.add(1);
				tile.add(18);
				corner.add(5);
				tile.add(14);
				corner.add(3);
			} else if (heightMargin + 7 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 7 * hexagonSide / 2 + settlementSize) {
				tile.add(14);
				corner.add(0);
				tile.add(9);
				corner.add(2);
				tile.add(13);
				corner.add(4);
			} else if (heightMargin + 9 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 9 * hexagonSide / 2 + settlementSize) {
				tile.add(9);
				corner.add(1);
				tile.add(13);
				corner.add(5);
				tile.add(8);
				corner.add(3);
			} else if (heightMargin + 13 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 13 * hexagonSide / 2 + settlementSize) {
				tile.add(8);
				corner.add(0);
				tile.add(3);
				corner.add(2);
				tile.add(7);
				corner.add(4);
			} else if (heightMargin + 15 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 15 * hexagonSide / 2 + settlementSize) {
				tile.add(3);
				corner.add(1);
				tile.add(7);
				corner.add(5);
			}
		} else if (checkIfWithinColumn(x, 7)) {
			if (heightMargin - settlementSize < y && y < heightMargin + settlementSize) {
				tile.add(18);
				corner.add(3);
			} else if (heightMargin + 2 * hexagonSide - settlementSize < y
					&& y < heightMargin + 2 * hexagonSide + settlementSize) {
				tile.add(18);
				corner.add(0);
				tile.add(14);
				corner.add(2);
				tile.add(17);
				corner.add(4);
			} else if (heightMargin + 3 * hexagonSide - settlementSize < y
					&& y < heightMargin + 3 * hexagonSide + settlementSize) {
				tile.add(14);
				corner.add(1);
				tile.add(17);
				corner.add(5);
				tile.add(13);
				corner.add(3);
			} else if (heightMargin + 5 * hexagonSide - settlementSize < y
					&& y < heightMargin + 5 * hexagonSide + settlementSize) {
				tile.add(13);
				corner.add(0);
				tile.add(8);
				corner.add(2);
				tile.add(12);
				corner.add(4);
			} else if (heightMargin + 6 * hexagonSide - settlementSize < y
					&& y < heightMargin + 6 * hexagonSide + settlementSize) {
				tile.add(8);
				corner.add(1);
				tile.add(12);
				corner.add(5);
				tile.add(7);
				corner.add(3);
			} else if (heightMargin + 8 * hexagonSide - settlementSize < y
					&& y < heightMargin + 8 * hexagonSide + settlementSize) {
				tile.add(7);
				corner.add(0);
			}
		} else if (checkIfWithinColumn(x, 8)) {
			if (heightMargin + hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + hexagonSide / 2 + settlementSize) {
				tile.add(18);
				corner.add(2);
			} else if (heightMargin + 3 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 3 * hexagonSide / 2 + settlementSize) {
				tile.add(18);
				corner.add(1);
				tile.add(17);
				corner.add(3);
			} else if (heightMargin + 7 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 7 * hexagonSide / 2 + settlementSize) {
				tile.add(17);
				corner.add(0);
				tile.add(13);
				corner.add(2);
				tile.add(16);
				corner.add(4);
			} else if (heightMargin + 9 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 9 * hexagonSide / 2 + settlementSize) {
				tile.add(13);
				corner.add(1);
				tile.add(16);
				corner.add(5);
				tile.add(12);
				corner.add(3);
			} else if (heightMargin + 13 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 13 * hexagonSide / 2 + settlementSize) {
				tile.add(12);
				corner.add(0);
				tile.add(7);
				corner.add(2);
			} else if (heightMargin + 15 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 15 * hexagonSide / 2 + settlementSize) {
				tile.add(7);
				corner.add(1);
			}
		} else if (checkIfWithinColumn(x, 9)) {
			if (heightMargin + 2 * hexagonSide - settlementSize < y
					&& y < heightMargin + 2 * hexagonSide + settlementSize) {
				tile.add(17);
				corner.add(2);
			} else if (heightMargin + 3 * hexagonSide - settlementSize < y
					&& y < heightMargin + 3 * hexagonSide + settlementSize) {
				tile.add(17);
				corner.add(1);
				tile.add(16);
				corner.add(3);
			} else if (heightMargin + 5 * hexagonSide - settlementSize < y
					&& y < heightMargin + 5 * hexagonSide + settlementSize) {
				tile.add(16);
				corner.add(0);
				tile.add(12);
				corner.add(2);
			} else if (heightMargin + 6 * hexagonSide - settlementSize < y
					&& y < heightMargin + 6 * hexagonSide + settlementSize) {
				tile.add(12);
				corner.add(1);
			}
		} else if (checkIfWithinColumn(x, 10)) {
			if (heightMargin + 7 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 7 * hexagonSide / 2 + settlementSize) {
				tile.add(16);
				corner.add(2);
			} else if (heightMargin + 9 * hexagonSide / 2 - settlementSize < y
					&& y < heightMargin + 9 * hexagonSide / 2 + settlementSize) {
				tile.add(16);
				corner.add(1);
			}
		}
		if (!tile.isEmpty()) {
			ArrayList<ArrayList<Integer>> toReturn = new ArrayList<>();
			toReturn.add(tile);
			toReturn.add(corner);
			return toReturn;
		}
		return null;
	}
	
	private boolean checkIfWithinColumn(double x, int col) {
		return widthMargin + col * sqrt3div2 * hexagonSide - settlementSize < x
				&& x < widthMargin + col * sqrt3div2 * hexagonSide + settlementSize;
	}
	
	public HashMap<Integer, ArrayList<Integer>> getRoadLocation(ArrayList<ArrayList<Integer>> loc1,
			ArrayList<ArrayList<Integer>> loc2) {
		HashMap<Integer, ArrayList<Integer>> tileToCorners = new HashMap<>();
		for (int i = 0; i < loc1.get(0).size(); i++) {
			ArrayList<Integer> updatedCorners = new ArrayList<>();
			if (tileToCorners.containsKey(loc1.get(0).get(i))) {
				updatedCorners = tileToCorners.get(loc1.get(0).get(i));
				updatedCorners.add(loc1.get(1).get(i));
				tileToCorners.replace(loc1.get(0).get(i), updatedCorners);
			} else {
				updatedCorners.add(loc1.get(1).get(i));
				tileToCorners.put(loc1.get(0).get(i), updatedCorners);
			}
		}
		for (int i = 0; i < loc2.get(0).size(); i++) {
			ArrayList<Integer> updatedCorners = new ArrayList<>();
			if (tileToCorners.containsKey(loc2.get(0).get(i))) {
				updatedCorners = tileToCorners.get(loc2.get(0).get(i));
				updatedCorners.add(loc2.get(1).get(i));
				tileToCorners.replace(loc2.get(0).get(i), updatedCorners);
			} else {
				updatedCorners.add(loc2.get(1).get(i));
				tileToCorners.put(loc2.get(0).get(i), updatedCorners);
			}
		}

		tileToCorners.entrySet().removeIf(entry -> entry.getValue().size() != 2);
		return tileToCorners;
	}

	public HashMap<Integer, Integer> getRoadOrientations(HashMap<Integer, ArrayList<Integer>> tileToCorners) {
		HashMap<Integer, Integer> tileToRoadOrientation = new HashMap<>();
		for (int key : tileToCorners.keySet()) {
			tileToRoadOrientation.put(key, getRoadOrientation(tileToCorners.get(key)));
		}
		return tileToRoadOrientation;
	}

	private int getRoadOrientation(ArrayList<Integer> corners) {
		int p1 = corners.get(0);
		int p2 = corners.get(1);
		if (p1 > p2) {
			int temp = p1;
			p1 = p2;
			p2 = temp;
		}
		if (p1 == 0 && p2 == 5 || p1 == 2 && p2 == 3) {
			return 0;
		} else if (p1 == 0 && p2 == 1 || p1 == 3 && p2 == 4) {
			return 1;
		} else if (p1 == 1 && p2 == 2 || p1 == 4 && p2 == 5) {
			return 2;
		}
		return -1;
	}
}
