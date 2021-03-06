package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import objects.CatanBoard;
import objects.PortType;
import objects.Road;
import objects.Settlement;
import objects.Tile;
import objects.TileType;

@SuppressWarnings("serial")
public class BoardWindow extends JPanel {

	private int boardHeight;
	private int heightMargin = 100;
	private int widthMargin;
	private final double sqrt3div2 = 0.86602540378;
	private int hexagonSide;
	private CatanBoard catanBoard;
	private int settlementSize = 12;
	private GameStates state = GameStates.setup;
	private StructureLocationCalculator structureLocation;

	public BoardWindow(CatanBoard catanBoard) {
		this.catanBoard = catanBoard;
		setBackground(new Color(164, 200, 218));

		this.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				boardHeight = getHeight();
				hexagonSide = (boardHeight - 2 * heightMargin) / 8;
				widthMargin = (getWidth() - (int) (10 * hexagonSide * sqrt3div2)) / 2;
			}

			public void componentHidden(ComponentEvent e) {
			}

			public void componentMoved(ComponentEvent e) {
			}

			public void componentShown(ComponentEvent e) {
			}
		});

		BoardMouseListener m = new BoardMouseListener();
		this.structureLocation = new StructureLocationCalculator(heightMargin, sqrt3div2);
		addMouseListener(m);
		addMouseMotionListener(m);
	}

	private void assignPolygonsToTiles() {
		for (int x = 0; x <= 18; x++) {
			makeHex(x, findCenter((int) this.catanBoard.getTiles().get(x).getLocation().getX(),
					(int) this.catanBoard.getTiles().get(x).getLocation().getY()));
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		boardHeight = getHeight();
		hexagonSide = (boardHeight - 2 * heightMargin) / 8;
		widthMargin = (getWidth() - (int) (10 * hexagonSide * sqrt3div2)) / 2;
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		super.paintComponent(g2);
		drawHexTiles(g2);
		drawPorts(g2);
	}

	private void drawHexTiles(Graphics2D g2) {
		for (int x = 0; x <= 18; x++) {
			drawHexTile(this.catanBoard.getTiles().get(x), g2);
			drawNumber(this.catanBoard.getTiles().get(x), g2);
			drawRobber(this.catanBoard.getTiles().get(x), g2);
			drawSettlements(this.catanBoard.getTiles().get(x), g2);
			drawRoads(this.catanBoard.getTiles().get(x), g2);
		}
	}

	private void drawRoads(Tile tile, Graphics2D g2) {
		HashMap<ArrayList<Integer>, Road> roadLoc = tile.getRoads();
		for (ArrayList<Integer> corners : roadLoc.keySet()) {
			Graphics2D g2c = (Graphics2D) g2.create();

			Point corner1 = tile.getHexCorners().get(corners.get(0));
			Point corner2 = tile.getHexCorners().get(corners.get(1));
			AffineTransform transformer = new AffineTransform();
			Road r = roadLoc.get(corners);
			int orientation = r.getAngle();
			int height = hexagonSide / 10;
			int x = (int) (corner1.getX() + corner2.getX()) / 2;
			int y = (int) (corner1.getY() + corner2.getY()) / 2;
			Rectangle rect = new Rectangle(x, y, (int) (0.7 * hexagonSide), height);
			if (orientation == 0) {
				transformer.rotate(Math.toRadians(30), x, y);
				transformer.translate(-(int) (0.34 * hexagonSide), 0);

			} else if (orientation == 1) {
				transformer.rotate(Math.toRadians(150), x, y);
				transformer.translate(-(int) (0.35 * hexagonSide), -2);
			} else if (orientation == 2) {
				transformer.rotate(Math.toRadians(90), x, y);
				transformer.translate(-(int) (0.4 * hexagonSide), -2);
			}
			Color color = (Color) r.getOwner().getColor();
			g2c.setColor(color);
			g2c.transform(transformer);
			g2c.fill(rect);
			g2c.setColor(Color.BLACK);
			g2c.draw(rect);
		}
	}

	private void drawSettlements(Tile tile, Graphics2D g2) {
		HashMap<Integer, Settlement> settlementLoc = tile.getSettlements();
		for (int corner : settlementLoc.keySet()) {
			boolean isCity = settlementLoc.get(corner).isCity();
			Point p = tile.getHexCorners().get(corner);
			if (isCity) {
				Shape square = new Rectangle2D.Double((int) p.getX() - this.settlementSize,
						(int) p.getY() - this.settlementSize, this.settlementSize * 2, this.settlementSize * 2);
				g2.setColor((Color) settlementLoc.get(corner).getOwner().getColor());
				g2.fill(square);
				g2.setColor(Color.BLACK);
				g2.draw(square);
			} else {
				Shape circle = new Ellipse2D.Double((int) p.getX() - this.settlementSize,
						(int) p.getY() - this.settlementSize, this.settlementSize * 2, this.settlementSize * 2);
				g2.setColor((Color) settlementLoc.get(corner).getOwner().getColor());
				g2.fill(circle);
				g2.setColor(Color.BLACK);
				g2.draw(circle);
			}
		}
	}

	public void drawHexTile(Tile tile, Graphics2D g2) {
		TileType type = tile.getType();
		assignPolygonsToTiles();

		if (null == type) {
			g2.setColor(Color.WHITE);
		} else
			switch (type) {
			case desert:
				g2.setColor(new Color(0xFF, 0xFF, 0xA9));
				break;
			case brick:
				g2.setColor(new Color(0xAD, 0x33, 0x33));
				break;
			case wool:
				g2.setColor(Color.GREEN);
				break;
			case wood:
				g2.setColor(new Color(0x99, 0x66, 0x33));
				break;
			case wheat:
				g2.setColor(Color.YELLOW);
				break;
			case ore:
				g2.setColor(Color.LIGHT_GRAY);
				break;
			default:
				g2.setColor(Color.WHITE);
				break;
			}

		g2.fillPolygon(tile.getHexagon());
		g2.setColor(Color.BLACK);
		g2.drawPolygon(tile.getHexagon());
	}

	public void drawNumber(Tile tile, Graphics2D g2) {
		if (tile.getNumber() == 0 || tile.getNumber() == 7) {
			return;
		}
		int x = (int) tile.getLocation().getX();
		int y = (int) tile.getLocation().getY();
		Point p = findCenter(x, y);

		g2.setColor(Color.WHITE);
		Shape circle = new Ellipse2D.Double((int) p.getX() - 25, (int) p.getY() - 25, 50, 50);
		g2.fill(circle);

		g2.setColor(Color.BLACK);
		if (tile.getNumber() == 6 || tile.getNumber() == 8)
			g2.setColor(Color.RED);
		g2.drawString("" + tile.getNumber(), (int) p.getX() - 5, (int) p.getY() + 5);
	}

	public Point findCenter(int x, int y) {
		double xCenter = widthMargin + (3 * hexagonSide * sqrt3div2) + ((x - 1) * 2 * hexagonSide * sqrt3div2)
				- ((y - 1) * hexagonSide * sqrt3div2);
		double yCenter = boardHeight - (heightMargin + hexagonSide + ((y - 1) * hexagonSide * 1.5));

		return doublePoint(xCenter, yCenter);
	}

	public Point doublePoint(double x, double y) {
		Point p = new Point();
		p.setLocation(x, y);
		return p;
	}

	public void makeHex(int tilePosInArray, Point center) {
		int xCenter = (int) center.getX();
		int yCenter = (int) center.getY();
		ArrayList<Point> tileCorners = new ArrayList<>();

		Polygon output = new Polygon();
		output.addPoint(xCenter + 1, yCenter + hexagonSide + 1);
		tileCorners.add(doublePoint(center.getX() + 1, center.getY() + hexagonSide + 1));
		output.addPoint(xCenter + (int) (hexagonSide * sqrt3div2) + 1, yCenter + (int) (.5 * hexagonSide) + 1);
		tileCorners.add(
				doublePoint(xCenter + (int) (hexagonSide * sqrt3div2) + 1, yCenter + (int) (.5 * hexagonSide) + 1));
		output.addPoint(xCenter + (int) (hexagonSide * sqrt3div2) + 1, yCenter - (int) (.5 * hexagonSide) - 1);
		tileCorners.add(
				doublePoint(xCenter + (int) (hexagonSide * sqrt3div2) + 1, yCenter - (int) (.5 * hexagonSide) - 1));
		output.addPoint(xCenter + 1, yCenter - hexagonSide - 1);
		tileCorners.add(doublePoint(xCenter + 1, yCenter - hexagonSide - 1));
		output.addPoint(xCenter - (int) (hexagonSide * sqrt3div2) - 1, yCenter - (int) (.5 * hexagonSide) - 1);
		tileCorners.add(
				doublePoint(xCenter - (int) (hexagonSide * sqrt3div2) - 1, yCenter - (int) (.5 * hexagonSide) - 1));
		output.addPoint(xCenter - (int) (hexagonSide * sqrt3div2) - 1, yCenter + (int) (.5 * hexagonSide) + 1);
		tileCorners.add(
				doublePoint(xCenter - (int) (hexagonSide * sqrt3div2) - 1, yCenter + (int) (.5 * hexagonSide) + 1));
		this.catanBoard.getTiles().get(tilePosInArray).setHexCorners(tileCorners);
		this.catanBoard.getTiles().get(tilePosInArray).setHexagon(output);
	}

	private void drawRobber(Tile tile, Graphics2D g2) {
		if (!tile.isRobber()) {
			return;
		}
		int x = (int) tile.getLocation().getX();
		int y = (int) tile.getLocation().getY();
		Point p = findCenter(x, y);

		g2.setColor(Color.PINK);
		Shape circle = new Ellipse2D.Double((int) p.getX() - 35, (int) p.getY() - 35, 75, 75);
		g2.fill(circle);

		try {
			String filename = "images" + File.separator + "robber.png";
			BufferedImage img = ImageIO.read(new File(filename));
			if (img != null) {
				g2.drawImage(img, (int) p.getX() - 35, (int) p.getY() - 35, 75, 75, null);
			}
		} catch (IOException e) {
			System.out.println("Could not find image for the Robber.");
		}

	}

	private void drawPorts(Graphics2D g2) {
		ArrayList<String> ports = getPortnames();
		ArrayList<ArrayList<AffineTransform>> transforms = getTransformationsForPorts();

		for (int i = 0; i < ports.size(); i++) {
			drawPort((Graphics2D) g2.create(), transforms.get(0).get(i), transforms.get(1).get(i), ports.get(i));
		}
	}

	public void drawPort(Graphics2D g2, AffineTransform transformer, AffineTransform rotate, String portDesc) {
		g2.setColor(Color.BLACK);
		g2.transform(transformer);
		g2.transform(rotate);
		g2.drawString(portDesc, 0, 0);
	}

	public ArrayList<String> getPortnames() {
		ArrayList<String> ports = new ArrayList<>();
		int index = 3;
		for (int i = 0; i < catanBoard.portTypes.size(); i++) {
			index++;
			if (index == catanBoard.portTypes.size()) {
				index -= catanBoard.portTypes.size();
			}
			PortType type = catanBoard.portTypes.get(index);
			if (type == PortType.brick) {
				ports.add(Messages.getString("BoardWindow.4"));
			} else if (type == PortType.wheat) {
				ports.add(Messages.getString("BoardWindow.0"));
			} else if (type == PortType.ore) {
				ports.add(Messages.getString("BoardWindow.1"));
			} else if (type == PortType.three) {
				ports.add(Messages.getString("BoardWindow.2"));
			} else if (type == PortType.wool) {
				ports.add(Messages.getString("BoardWindow.3"));
			} else {
				ports.add(Messages.getString("BoardWindow.5"));
			}
		}
		return ports;
	}

	public ArrayList<ArrayList<AffineTransform>> getTransformationsForPorts() {
		ArrayList<AffineTransform> transformations = new ArrayList<>();
		ArrayList<AffineTransform> rotations = new ArrayList<>();

		AffineTransform transformer = new AffineTransform();
		AffineTransform rotate = new AffineTransform();

		transformer.translate(widthMargin + (int) (hexagonSide * 4.2), heightMargin - (int) (hexagonSide * 0.25));
		rotate.rotate(Math.toRadians(30));
		transformations.add(transformer);
		rotations.add(rotate);

		transformer = new AffineTransform();
		rotate = new AffineTransform();
		transformer.translate(widthMargin + (int) (hexagonSide * 7), heightMargin + (int) (hexagonSide * 1.4));
		rotate.rotate(Math.toRadians(30));
		transformations.add(transformer);
		rotations.add(rotate);

		transformer = new AffineTransform();
		rotate = new AffineTransform();
		transformer.translate(widthMargin + (int) (hexagonSide * 8.8), heightMargin + (int) (hexagonSide * 3.35));
		rotate.rotate(Math.toRadians(90));
		transformations.add(transformer);
		rotations.add(rotate);

		transformer = new AffineTransform();
		rotate = new AffineTransform();
		transformer.translate(widthMargin + (int) (hexagonSide * 7.1), heightMargin + (int) (hexagonSide * 6.85));
		rotate.rotate(Math.toRadians(-30));
		transformations.add(transformer);
		rotations.add(rotate);

		transformer = new AffineTransform();
		rotate = new AffineTransform();
		transformer.translate(widthMargin + (int) (hexagonSide * 4.2), heightMargin + (int) (hexagonSide * 8.5));
		rotate.rotate(Math.toRadians(-30));
		transformations.add(transformer);
		rotations.add(rotate);

		transformer = new AffineTransform();
		rotate = new AffineTransform();
		transformer.translate(widthMargin + (int) (hexagonSide * 1.1), heightMargin + (int) (hexagonSide * 7.6));
		rotate.rotate(Math.toRadians(30));
		transformations.add(transformer);
		rotations.add(rotate);

		transformer = new AffineTransform();
		rotate = new AffineTransform();
		transformer.translate(widthMargin + (int) (hexagonSide * 0.7), heightMargin + (int) (hexagonSide * 6.5));
		rotate.rotate(Math.toRadians(270));
		transformations.add(transformer);
		rotations.add(rotate);

		transformer = new AffineTransform();
		rotate = new AffineTransform();
		transformer.translate(widthMargin + (int) (hexagonSide * 0.7), heightMargin + (int) (hexagonSide * 3.2));
		rotate.rotate(Math.toRadians(270));
		transformations.add(transformer);
		rotations.add(rotate);

		transformer = new AffineTransform();
		rotate = new AffineTransform();
		transformer.translate(widthMargin + (int) (hexagonSide * 1.6), heightMargin + (int) (hexagonSide * 0.4));
		rotate.rotate(Math.toRadians(-30));
		transformations.add(transformer);
		rotations.add(rotate);

		ArrayList<ArrayList<AffineTransform>> tfm = new ArrayList<>();
		tfm.add(transformations);
		tfm.add(rotations);
		return tfm;
	}

	public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

		int original_width = imgSize.width;
		int original_height = imgSize.height;
		int bound_width = boundary.width;
		int bound_height = boundary.height;
		int new_width = original_width;
		int new_height = original_height;

		if (original_width > bound_width) {
			new_width = bound_width;
			new_height = (new_width * original_height) / original_width;
		}

		if (new_height > bound_height) {
			new_height = bound_height;
			new_width = (new_height * original_width) / original_height;
		}

		return new Dimension(new_width, new_height);
	}

	public ArrayList<ArrayList<Integer>> getStructureLocation(Point p) {
		return this.structureLocation.getStructureLocation(p, settlementSize, hexagonSide, widthMargin);
	}

	public void setState(GameStates s) {
		this.state = s;
	}

	public GameStates getState() {
		return this.state;
	}

	class BoardMouseListener extends MouseAdapter {
		private Point lastClicked;

		public void mouseClicked(MouseEvent e) {
			if (state.equals(GameStates.drop_settlement) || state.equals(GameStates.drop_settlement_setup)
					|| state.equals(GameStates.drop_settlement_setup_final) || state.equals(GameStates.drop_city)) {
				boolean structurePlaced = false;
				Point p = new Point(e.getX(), e.getY());
				if (p != null) {
					ArrayList<ArrayList<Integer>> settlementLoc = getStructureLocation(p);
					if (settlementLoc != null) {
						ArrayList<Integer> tiles = settlementLoc.get(0);
						ArrayList<Integer> corners = settlementLoc.get(1);

						if (state.equals(GameStates.drop_city)) {
							structurePlaced = catanBoard.addCityToTiles(tiles, corners);
						} else {
							structurePlaced = catanBoard.addSettlementToTiles(tiles, corners, state);
						}
					}
				}
				if (structurePlaced) {
					setState(GameStates.idle);
					repaint();
				}
			} else if (state.equals(GameStates.move_robber)) {
				Point p = new Point(e.getX(), e.getY());
				if (p != null) {
					Tile clicked = null;
					for (Tile t : catanBoard.getTiles()) {
						if (t.getHexagon().contains(p)) {
							clicked = t;
						}
					}
					if (clicked != null && !clicked.isRobber()) {
						catanBoard.moveRobber(clicked);
						setState(GameStates.steal);
						repaint();
					}
				}
			}
		}

		public void mousePressed(MouseEvent e) {
			if (state.equals(GameStates.drop_road) || state.equals(GameStates.drop_road_setup)
					|| state.equals(GameStates.drop_road_card)) {
				Point prevClicked = new Point(e.getX(), e.getY());
				lastClicked = prevClicked;
			}

		}

		public void mouseReleased(MouseEvent e) {
			if (state.equals(GameStates.drop_road) || state.equals(GameStates.drop_road_setup)
					|| state.equals(GameStates.drop_road_card)) {
				Point p = new Point(e.getX(), e.getY());
				if (Math.abs(p.x - lastClicked.x) > 5 || Math.abs(p.y - lastClicked.y) > 5) {
					ArrayList<ArrayList<Integer>> loc1 = getStructureLocation(lastClicked);
					ArrayList<ArrayList<Integer>> loc2 = getStructureLocation(p);
					if (loc1 != null && loc2 != null) {
						HashMap<Integer, ArrayList<Integer>> tileToCorners = structureLocation.getRoadLocation(loc1,
								loc2);
						HashMap<Integer, Integer> tileToRoadOrientation = structureLocation
								.getRoadOrientations(tileToCorners);

						if (catanBoard.roadLocationClick(tileToCorners, tileToRoadOrientation, state)) {
							setState(GameStates.idle);
						}
					}
				}
				repaint();
			}
		}
	}

}
