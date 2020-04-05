package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import objects.Road;
import objects.Settlement;
import objects.TileType;

public class BoardWindow extends JPanel {

	private int boardHeight;
	private int heightMargin = 100;
	private int widthMargin;
	private final double sqrt3div2 = 0.86602540378;
	private int hexagonSide;
	private GUITile[][] tiles;
	private int settlementSize = 12;
	
	public BoardWindow() {
		setBackground(new Color(164,200,218));
        //Handle Resizing Window
        this.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
    		boardHeight = getHeight();
    		hexagonSide = (boardHeight - 2 * heightMargin) / 8;
    		widthMargin = (getWidth() - (int) (10 * hexagonSide * sqrt3div2)) / 2;
            }
            public void componentHidden(ComponentEvent e) {}
            public void componentMoved(ComponentEvent e) {}
            public void componentShown(ComponentEvent e) {}
        });
        MouseListener m = new BoardMouseListener();
		addMouseListener(m);
    }
	
	public void updateBoard(GUITile[][] tiles) {
		this.tiles = tiles;
		assignPolygonsToTiles();
	}

	private void assignPolygonsToTiles() {
		for (int x = 1; x <= 3; x++) {
			makeHex(x, 1, findCenter((int) tiles[x][1].getPosition().getX(), (int) tiles[x][1].getPosition().getY()));
		}
		for (int x = 1; x <= 4; x++) {
			makeHex(x, 2, findCenter((int) tiles[x][2].getPosition().getX(), (int) tiles[x][2].getPosition().getY()));
		}
		for (int x = 1; x <= 5; x++) {
			makeHex(x, 3, findCenter((int) tiles[x][3].getPosition().getX(), (int) tiles[x][3].getPosition().getY()));
		}
		for (int x = 2; x <= 5; x++) {
			makeHex(x, 4, findCenter((int) tiles[x][4].getPosition().getX(), (int) tiles[x][4].getPosition().getY()));
		}
		for (int x = 3; x <= 5; x++) {
			makeHex(x, 5, findCenter((int) tiles[x][5].getPosition().getX(), (int) tiles[x][5].getPosition().getY()));
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
		
		/*
		Point p = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(p,this);
		System.out.println(p);
		*/
	}

	private void drawHexTiles(Graphics2D g2) {
		for (int x = 1; x <= 3; x++) {
			drawHexTile(x, 1, g2);
			drawNumber(tiles[x][1], g2);
			drawRobber(tiles[x][1], g2);
			drawSettlements(tiles[x][1], g2);
			drawRoads(tiles[x][1], g2);
		}
		for (int x = 1; x <= 4; x++) {
			drawHexTile(x, 2, g2);
			drawNumber(tiles[x][2], g2);
			drawRobber(tiles[x][2], g2);
			drawSettlements(tiles[x][2], g2);
			drawRoads(tiles[x][2], g2);
		}
		for (int x = 1; x <= 5; x++) {
			drawHexTile(x, 3, g2);
			drawNumber(tiles[x][3], g2);
			drawRobber(tiles[x][3], g2);
			drawSettlements(tiles[x][3], g2);
			drawRoads(tiles[x][3], g2);
		}
		for (int x = 2; x <= 5; x++) {
			drawHexTile(x, 4, g2);
			drawNumber(tiles[x][4], g2);
			drawRobber(tiles[x][4], g2);
			drawSettlements(tiles[x][4], g2);
			drawRoads(tiles[x][4], g2);
		}
		for (int x = 3; x <= 5; x++) {
			drawHexTile(x, 5, g2);
			drawNumber(tiles[x][5], g2);
			drawRobber(tiles[x][5], g2);
			drawSettlements(tiles[x][5], g2);
			drawRoads(tiles[x][5], g2);
		}
	}

	private void drawRoads(GUITile tile, Graphics2D g2) {
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

	private void drawSettlements(GUITile tile, Graphics2D g2) {
		HashMap<Integer, Settlement> settlementLoc = tile.getSettlements();
		for (int corner : settlementLoc.keySet()) {
			Point p = tile.getHexCorners().get(corner);
			Shape circle = new Ellipse2D.Double((int) p.getX() - this.settlementSize, (int) p.getY() - this.settlementSize, this.settlementSize * 2, this.settlementSize * 2);
			g2.setColor((Color) settlementLoc.get(corner).getOwner().getColor());
			g2.fill(circle);
			g2.setColor(Color.BLACK);
			g2.draw(circle);
		}
	}

	public void drawHexTile(int tileX, int tileY, Graphics2D g2) {
		TileType type = this.tiles[tileX][tileY].getType();
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

		g2.fillPolygon(this.tiles[tileX][tileY].getHexagon());
		g2.setColor(Color.BLACK);
		g2.drawPolygon(this.tiles[tileX][tileY].getHexagon());
	}

	public void drawNumber(GUITile tile, Graphics2D g2) {
		if (tile.getNumber() == 0 || tile.getNumber() == 7) {
			return;
		}
		int x = (int) tile.getPosition().getX();
		int y = (int) tile.getPosition().getY();
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

	public void makeHex(int tileX, int tileY, Point center) {
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
		this.tiles[tileX][tileY].setHexCorners(tileCorners);
		this.tiles[tileX][tileY].setHexagon(output);
	}

	private void drawRobber(GUITile tile, Graphics2D g2) {
		if (!tile.isRobber()) {
			return;
		}
		int x = (int) tile.getPosition().getX();
		int y = (int) tile.getPosition().getY();
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
		ports.add("Wheat 2:1");
		ports.add("Ore 2:1");
		ports.add("? 3:1");
		ports.add("Wool 2:1");
		ports.add("? 3:1");
		ports.add("? 3:1");
		ports.add("Bricks 2:1");
		ports.add("Lumber 2:1");
		ports.add("? 3:1");
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

		// first check if we need to scale width
		if (original_width > bound_width) {
			// scale width to fit
			new_width = bound_width;
			// scale height to maintain aspect ratio
			new_height = (new_width * original_height) / original_width;
		}

		// then check if we need to scale even with the new height
		if (new_height > bound_height) {
			// scale height to fit instead
			new_height = bound_height;
			// scale width to maintain aspect ratio
			new_width = (new_height * original_width) / original_height;
		}

		return new Dimension(new_width, new_height);
	}
	
	public ArrayList<ArrayList<Integer>> getStructureLocation(Point p) {
		double x = p.getX();
		double y = p.getY();
		ArrayList<Integer> tile = new ArrayList<>();
		ArrayList<Integer> corner = new ArrayList<>();
		// Column search to see if the point clicked belongs to a location that could possibly hold a settlement
		if (checkIfWithinColumn(x, 0)) {
			if (heightMargin + 7 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 7 * hexagonSide / 2 + this.settlementSize ) {
				tile.add(2);
				corner.add(4);
			}
			else if (heightMargin + 9 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 9 * hexagonSide / 2 + this.settlementSize ) {
				tile.add(2);
				corner.add(5);
			}
		} else if (checkIfWithinColumn(x, 1)) {
			if (heightMargin + 2 * hexagonSide - this.settlementSize < y && y < heightMargin + 2 * hexagonSide + this.settlementSize) {
				tile.add(6);
				corner.add(4);
			}
			else if (heightMargin + 3 * hexagonSide - this.settlementSize < y && y < heightMargin + 3 * hexagonSide + this.settlementSize) {
				tile.add(6);
				corner.add(5);
				tile.add(2);
				corner.add(3);
			}
			else if (heightMargin + 5 * hexagonSide - this.settlementSize < y && y < heightMargin + 5 * hexagonSide + this.settlementSize) {
				tile.add(1);
				corner.add(4);
				tile.add(2);
				corner.add(0);
			}
			else if (heightMargin + 6 * hexagonSide - this.settlementSize < y && y < heightMargin + 6 * hexagonSide + this.settlementSize) {
				tile.add(1);
				corner.add(5);
			}
		} else if (checkIfWithinColumn(x, 2)) {
			if (heightMargin + hexagonSide / 2 - this.settlementSize < y && y < heightMargin + hexagonSide / 2 + this.settlementSize) {
				tile.add(11);
				corner.add(4);
			}
			else if (heightMargin + 3 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 3 * hexagonSide / 2 + this.settlementSize) {
				tile.add(11);
				corner.add(5);
				tile.add(6);
				corner.add(3);
			}
			else if (heightMargin + 7 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 7 * hexagonSide / 2 + this.settlementSize) {
				tile.add(6);
				corner.add(0);
				tile.add(2);
				corner.add(2);
				tile.add(5);
				corner.add(4);
			}
			else if (heightMargin + 9 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 9 * hexagonSide / 2 + this.settlementSize) {
				tile.add(2);
				corner.add(1);
				tile.add(5);
				corner.add(5);
				tile.add(1);
				corner.add(3);
			}
			else if (heightMargin + 13 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 13 * hexagonSide / 2 + this.settlementSize) {
				tile.add(1);
				corner.add(0);
				tile.add(0);
				corner.add(3);
			}
			else if (heightMargin + 15 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 15 * hexagonSide / 2 + this.settlementSize) {
				tile.add(0);
				corner.add(5);
			}
		} else if (checkIfWithinColumn(x, 3)) {
			if (heightMargin - this.settlementSize < y && y < heightMargin + this.settlementSize) {
				tile.add(11);
				corner.add(3);
			}
			else if (heightMargin + 2 * hexagonSide - this.settlementSize < y && y < heightMargin + 2 * hexagonSide + this.settlementSize) {
				tile.add(11);
				corner.add(0);
				tile.add(6);
				corner.add(2);
				tile.add(10);
				corner.add(4);
			}
			else if (heightMargin + 3 * hexagonSide - this.settlementSize < y && y < heightMargin + 3 * hexagonSide + this.settlementSize) {
				tile.add(6);
				corner.add(1);
				tile.add(10);
				corner.add(5);
				tile.add(5);
				corner.add(3);
			}
			else if (heightMargin + 5 * hexagonSide - this.settlementSize < y && y < heightMargin + 5 * hexagonSide + this.settlementSize) {
				tile.add(5);
				corner.add(0);
				tile.add(1);
				corner.add(2);
				tile.add(4);
				corner.add(4);
			}
			else if (heightMargin + 6 * hexagonSide - this.settlementSize < y && y < heightMargin + 6 * hexagonSide + this.settlementSize) {
				tile.add(1);
				corner.add(1);
				tile.add(4);
				corner.add(5);
				tile.add(0);
				corner.add(3);
			}
			else if (heightMargin + 8 * hexagonSide - this.settlementSize < y && y < heightMargin + 8 * hexagonSide + this.settlementSize) {
				tile.add(0);
				corner.add(0);
			}
		} else if (checkIfWithinColumn(x, 4)) {
			if (heightMargin + hexagonSide / 2 - this.settlementSize < y && y < heightMargin + hexagonSide / 2 + this.settlementSize) {
				tile.add(11);
				corner.add(2);
				tile.add(15);
				corner.add(4);
			}
			else if (heightMargin + 3 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 3 * hexagonSide / 2 + this.settlementSize) {
				tile.add(11);
				corner.add(1);
				tile.add(15);
				corner.add(5);
				tile.add(10);
				corner.add(3);
			}
			else if (heightMargin + 7 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 7 * hexagonSide / 2 + this.settlementSize) {
				tile.add(10);
				corner.add(0);
				tile.add(5);
				corner.add(2);
				tile.add(9);
				corner.add(4);
			}
			else if (heightMargin + 9 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 9 * hexagonSide / 2 + this.settlementSize) {
				tile.add(5);
				corner.add(1);
				tile.add(9);
				corner.add(5);
				tile.add(4);
				corner.add(3);
			}
			else if (heightMargin + 13 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 13 * hexagonSide / 2 + this.settlementSize) {
				tile.add(4);
				corner.add(0);
				tile.add(0);
				corner.add(2);
				tile.add(3);
				corner.add(4);
			}
			else if (heightMargin + 15 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 15 * hexagonSide / 2 + this.settlementSize) {
				tile.add(0);
				corner.add(1);
				tile.add(3);
				corner.add(5);
			}
		} else if (checkIfWithinColumn(x, 5)) {
			if (heightMargin - this.settlementSize < y && y < heightMargin + this.settlementSize) {
				tile.add(15);
				corner.add(3);
			}
			else if (heightMargin + 2 * hexagonSide - this.settlementSize < y && y < heightMargin + 2 * hexagonSide + this.settlementSize) {
				tile.add(15);
				corner.add(0);
				tile.add(10);
				corner.add(2);
				tile.add(14);
				corner.add(4);
			}
			else if (heightMargin + 3 * hexagonSide - this.settlementSize < y && y < heightMargin + 3 * hexagonSide + this.settlementSize) {
				tile.add(10);
				corner.add(1);
				tile.add(14);
				corner.add(5);
				tile.add(9);
				corner.add(3);
			}
			else if (heightMargin + 5 * hexagonSide - this.settlementSize < y && y < heightMargin + 5 * hexagonSide + this.settlementSize) {
				tile.add(9);
				corner.add(0);
				tile.add(4);
				corner.add(2);
				tile.add(8);
				corner.add(4);
			}
			else if (heightMargin + 6 * hexagonSide - this.settlementSize < y && y < heightMargin + 6 * hexagonSide + this.settlementSize) {
				tile.add(4);
				corner.add(1);
				tile.add(8);
				corner.add(5);
				tile.add(3);
				corner.add(3);
			}
			else if (heightMargin + 8 * hexagonSide - this.settlementSize < y && y < heightMargin + 8 * hexagonSide + this.settlementSize) {
				tile.add(3);
				corner.add(0);
			}
		} else if (checkIfWithinColumn(x, 6)) {
			if (heightMargin + hexagonSide / 2 - this.settlementSize < y && y < heightMargin + hexagonSide / 2 + this.settlementSize) {
				tile.add(15);
				corner.add(2);
				tile.add(18);
				corner.add(4);
			}
			else if (heightMargin + 3 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 3 * hexagonSide / 2 + this.settlementSize) {
				tile.add(15);
				corner.add(1);
				tile.add(18);
				corner.add(5);
				tile.add(14);
				corner.add(3);
			}
			else if (heightMargin + 7 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 7 * hexagonSide / 2 + this.settlementSize) {
				tile.add(14);
				corner.add(0);
				tile.add(9);
				corner.add(2);
				tile.add(13);
				corner.add(4);
			}
			else if (heightMargin + 9 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 9 * hexagonSide / 2 + this.settlementSize) {
				tile.add(9);
				corner.add(1);
				tile.add(13);
				corner.add(5);
				tile.add(8);
				corner.add(3);
			}
			else if (heightMargin + 13 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 13 * hexagonSide / 2 + this.settlementSize) {
				tile.add(8);
				corner.add(0);
				tile.add(3);
				corner.add(2);
				tile.add(7);
				corner.add(4);
			}
			else if (heightMargin + 15 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 15 * hexagonSide / 2 + this.settlementSize) {
				tile.add(3);
				corner.add(1);
				tile.add(7);
				corner.add(5);
			}
		} else if (checkIfWithinColumn(x, 7)) {
			if (heightMargin - this.settlementSize < y && y < heightMargin + this.settlementSize) {
				tile.add(18);
				corner.add(3);
			}
			else if (heightMargin + 2 * hexagonSide - this.settlementSize < y && y < heightMargin + 2 * hexagonSide + this.settlementSize) {
				tile.add(18);
				corner.add(0);
				tile.add(14);
				corner.add(2);
				tile.add(17);
				corner.add(4);
			}
			else if (heightMargin + 3 * hexagonSide - this.settlementSize < y && y < heightMargin + 3 * hexagonSide + this.settlementSize) {
				tile.add(14);
				corner.add(1);
				tile.add(17);
				corner.add(5);
				tile.add(13);
				corner.add(3);
			}
			else if (heightMargin + 5 * hexagonSide - this.settlementSize < y && y < heightMargin + 5 * hexagonSide + this.settlementSize) {
				tile.add(13);
				corner.add(0);
				tile.add(8);
				corner.add(2);
				tile.add(12);
				corner.add(4);
			}
			else if (heightMargin + 6 * hexagonSide - this.settlementSize < y && y < heightMargin + 6 * hexagonSide + this.settlementSize) {
				tile.add(8);
				corner.add(1);
				tile.add(12);
				corner.add(5);
				tile.add(7);
				corner.add(3);
			}
			else if (heightMargin + 8 * hexagonSide - this.settlementSize < y && y < heightMargin + 8 * hexagonSide + this.settlementSize) {
				tile.add(7);
				corner.add(0);
			}
		} else if (checkIfWithinColumn(x, 8)) {
			if (heightMargin + hexagonSide / 2 - this.settlementSize < y && y < heightMargin + hexagonSide / 2 + this.settlementSize) {
				tile.add(18);
				corner.add(2);
			}
			else if (heightMargin + 3 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 3 * hexagonSide / 2 + this.settlementSize) {
				tile.add(18);
				corner.add(1);
				tile.add(17);
				corner.add(3);
			}
			else if (heightMargin + 7 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 7 * hexagonSide / 2 + this.settlementSize) {
				tile.add(17);
				corner.add(0);
				tile.add(13);
				corner.add(2);
				tile.add(16);
				corner.add(4);
			}
			else if (heightMargin + 9 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 9 * hexagonSide / 2 + this.settlementSize) {
				tile.add(13);
				corner.add(1);
				tile.add(16);
				corner.add(5);
				tile.add(12);
				corner.add(3);
			}
			else if (heightMargin + 13 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 13 * hexagonSide / 2 + this.settlementSize) {
				tile.add(12);
				corner.add(0);
				tile.add(7);
				corner.add(2);
			}
			else if (heightMargin + 15 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 15 * hexagonSide / 2 + this.settlementSize) {
				tile.add(7);
				corner.add(0);
			}
		} else if (checkIfWithinColumn(x, 9)) {
			if (heightMargin + 2 * hexagonSide - this.settlementSize < y && y < heightMargin + 2 * hexagonSide + this.settlementSize) {
				tile.add(17);
				corner.add(2);
			}
			else if (heightMargin + 3 * hexagonSide - this.settlementSize < y && y < heightMargin + 3 * hexagonSide + this.settlementSize) {
				tile.add(17);
				corner.add(1);
				tile.add(16);
				corner.add(3);
			}
			else if (heightMargin + 5 * hexagonSide - this.settlementSize < y && y < heightMargin + 5 * hexagonSide + this.settlementSize) {
				tile.add(16);
				corner.add(3);
				tile.add(12);
				corner.add(0);
			}
			else if (heightMargin + 6 * hexagonSide - this.settlementSize < y && y < heightMargin + 6 * hexagonSide + this.settlementSize) {
				tile.add(12);
				corner.add(1);
			}
		} else if (checkIfWithinColumn(x, 10)) {
			if (heightMargin + 7 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 7 * hexagonSide / 2 + this.settlementSize) {
				tile.add(16);
				corner.add(2);
			}
			else if (heightMargin + 9 * hexagonSide / 2 - this.settlementSize < y && y < heightMargin + 9 * hexagonSide / 2 + this.settlementSize) {
				tile.add(16);
				corner.add(1);
			}
		}
		if(!tile.isEmpty()) {
			System.out.println("Tile: " + tile + " Corner: " + corner);
			ArrayList<ArrayList<Integer>> toReturn = new ArrayList<>();
			toReturn.add(tile);
			toReturn.add(corner);
			return toReturn;
		}
		return null;
	}
	
	public boolean checkIfWithinColumn(double x, int col) {
		return widthMargin + col * sqrt3div2 * hexagonSide - this.settlementSize < x && x < widthMargin + col * sqrt3div2 * hexagonSide + this.settlementSize;
	}
	
	class BoardMouseListener extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			Point p = new Point(e.getX(), e.getY());
			if(p != null) {
				ArrayList<ArrayList<Integer>> settlementLoc = getStructureLocation(p);
				if(settlementLoc != null) {
					ArrayList<Integer> tiles = settlementLoc.get(0);
					ArrayList<Integer> corners = settlementLoc.get(1);
				}
				
			}
			repaint();
		}
	}
}
