package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import objects.Position;
import objects.Tile;
import objects.TileType;

/**
 *
 * @author Indresh
 */
public class CatanBoard extends JPanel{
    private final Tile[][] tiles;
    //private Road[][][] roads;
    //private Settlement[][][] settlements;
    //private City[][][] cities;
    private int boardHeight;
    private int heightMargin = 100;
    private int widthMargin;
    private final double sqrt3div2 = 0.86602540378;
    
    private int hexagonSide;
    
    public CatanBoard(){
        
        this.tiles = new Tile[7][7];
        intialBoardSetup();
        
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
    }

    private void intialBoardSetup() {        
        // Coordinates
        ArrayList<Position> positions = new ArrayList<>();
        for(int x = 1; x < 6; x++){
            int ylow = -1;
            int yhigh = -1;
            switch (x) {
                case 1:
                    ylow = 1;
                    yhigh = 4;
                    break;
                case 2:
                    ylow = 1;
                    yhigh = 5;
                    break;
                case 3:
                    ylow = 1;
                    yhigh = 6;
                    break;
                case 4:
                    ylow = 2;
                    yhigh = 6;
                    break;
                case 5:
                    ylow = 3;
                    yhigh = 6;
                    break;
            }
            for(int y = ylow; y < yhigh; y++){
                positions.add(new Position(x,y));
            }
        }
        
        ArrayList<Integer> wheatLocs = new ArrayList<Integer>(Arrays.asList(2, 3, 6, 8));
        ArrayList<Integer> wheatNums = new ArrayList<Integer>(Arrays.asList(9, 6, 12, 4));
        initilizeTiles(wheatLocs, wheatNums, TileType.wheat, positions);
        
        ArrayList<Integer> woolLocs = new ArrayList<Integer>(Arrays.asList(7, 12, 14, 15));
        ArrayList<Integer> woolNums = new ArrayList<Integer>(Arrays.asList(11, 5, 4, 2));
        initilizeTiles(woolLocs, woolNums, TileType.wool, positions);
        
        ArrayList<Integer> woodLocs = new ArrayList<Integer>(Arrays.asList(1, 5, 13, 18));
        ArrayList<Integer> woodNums = new ArrayList<Integer>(Arrays.asList(8, 11, 3, 9));
        initilizeTiles(woodLocs, woodNums, TileType.wood, positions);
        
        ArrayList<Integer> oreLocs = new ArrayList<Integer>(Arrays.asList(4, 11, 16));
        ArrayList<Integer> oreNums = new ArrayList<Integer>(Arrays.asList(3, 10, 8));
        initilizeTiles(oreLocs, oreNums, TileType.ore, positions);
        
        ArrayList<Integer> brickLocs = new ArrayList<Integer>(Arrays.asList(0, 10, 17));
        ArrayList<Integer> brickNums = new ArrayList<Integer>(Arrays.asList(5, 6, 10));
        initilizeTiles(brickLocs, brickNums, TileType.bricks, positions);
        
        Tile desertTile = new Tile(positions.get(9), 7, TileType.desert);
        desertTile.setRobber();
        this.tiles[positions.get(9).getX()][positions.get(9).getY()] = desertTile;
    }
    
    private void initilizeTiles(List<Integer> positionNums, List<Integer> numbers, TileType type, ArrayList<Position> positions) {
    	if (positionNums.size() != numbers.size()) {
    		return;
    	}
    	for (int i = 0; i < positionNums.size(); i++) {
    		Position p = positions.get(positionNums.get(i));
    		this.tiles[p.getX()][p.getY()] = new Tile(p, numbers.get(i), type);
    	}
    }
    
    @Override
    public void paintComponent(Graphics g){
        boardHeight = getHeight();
	hexagonSide = (boardHeight - 2 * heightMargin) / 8;
	widthMargin = (getWidth() - (int) (10 * hexagonSide * sqrt3div2)) / 2;
        Graphics2D g2 = (Graphics2D)g;
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g.setFont(new Font("TimesRoman", Font.BOLD, 20));
	super.paintComponent(g2);
        drawHexTilesWithNumbers(g2);
        drawPorts(g2);
    }

    private void drawHexTilesWithNumbers(Graphics2D g2) {
        for (int x = 1; x <= 3; x++) {
            drawHexTile(tiles[x][1],g2);
            drawNumber(tiles[x][1],g2);
            drawRobber(tiles[x][1],g2);
	}
        for (int x = 1; x <= 4; x++) {
            drawHexTile(tiles[x][2],g2);
            drawNumber(tiles[x][2],g2);
            drawRobber(tiles[x][2],g2);
        }
        for (int x = 1; x <= 5; x++) {
            drawHexTile(tiles[x][3],g2);
            drawNumber(tiles[x][3],g2);
            drawRobber(tiles[x][3],g2);
        }
        for (int x = 2; x <= 5; x++) {
            drawHexTile(tiles[x][4],g2);
            drawNumber(tiles[x][4],g2);
            drawRobber(tiles[x][4],g2);
        }
        for (int x = 3; x <= 5; x++) {
            drawHexTile(tiles[x][5],g2);
            drawNumber(tiles[x][5],g2);
            drawRobber(tiles[x][5],g2);
        }
    }
    
    public void drawHexTile(Tile tile, Graphics2D g2) {
        int x = tile.getPosition().getX();
        int y = tile.getPosition().getY();
        Polygon poly = makeHex(findCenter(x,y));
        TileType type = tile.getType();
        
        if(null == type) {
            g2.setColor(Color.WHITE);
        }else switch (type) {
            case desert:
                g2.setColor(new Color(0xFF, 0xFF, 0xA9));
                break;
            case bricks:
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

        g2.fillPolygon(poly);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(poly);
    }
    
    public void drawNumber(Tile tile, Graphics2D g2) {
        if (tile.getNumber() == 0 || tile.getNumber() == 7) {
                return;
        }
        int x = tile.getPosition().getX();
        int y = tile.getPosition().getY();
        Point p = findCenter(x,y);

        g2.setColor(Color.WHITE);
        Shape circle = new Ellipse2D.Double((int)p.getX() - 25, (int)p.getY() - 25, 50, 50);
        g2.fill(circle);
        
        g2.setColor(Color.BLACK);
        if (tile.getNumber() == 6 || tile.getNumber() == 8)
                g2.setColor(Color.RED);
        g2.drawString("" + tile.getNumber(), (int)p.getX() - 5, (int)p.getY() + 5);
    }
    
    public Point findCenter(int x, int y){
        int xCenter = widthMargin + (int) (3 * hexagonSide * sqrt3div2)
                        + (int) ((x - 1) * 2 * hexagonSide * sqrt3div2)
                        - (int) ((y - 1) * hexagonSide * sqrt3div2);
        int yCenter = boardHeight - (heightMargin + hexagonSide
                        + (int) ((y - 1) * hexagonSide * 1.5));

        return new Point(xCenter,yCenter);
    }
    
    public Polygon makeHex(Point center) {
        int xCenter = (int) center.getX();
        int yCenter = (int) center.getY();

        Polygon output = new Polygon();
        output.addPoint(xCenter + 1, yCenter + hexagonSide + 1);
        output.addPoint(xCenter + (int) (hexagonSide * sqrt3div2) + 1, yCenter + (int) (.5 * hexagonSide) + 1);
        output.addPoint(xCenter + (int) (hexagonSide * sqrt3div2) + 1, yCenter - (int) (.5 * hexagonSide) - 1);
        output.addPoint(xCenter + 1, yCenter - hexagonSide - 1);
        output.addPoint(xCenter - (int) (hexagonSide * sqrt3div2) - 1, yCenter - (int) (.5 * hexagonSide) - 1);
        output.addPoint(xCenter - (int) (hexagonSide * sqrt3div2) - 1, yCenter + (int) (.5 * hexagonSide) + 1);

        return output;
    }

    private void drawRobber(Tile tile, Graphics2D g2) {
        if(!tile.isRobber()){
            return;
        }
        int x = tile.getPosition().getX();
        int y = tile.getPosition().getY();
        Point p = findCenter(x,y);
        
        g2.setColor(Color.PINK);
        Shape circle = new Ellipse2D.Double((int)p.getX() - 35, (int)p.getY() - 35, 75, 75);
        g2.fill(circle);
    
        try {
        	String filename = "images" + File.separator + "robber.png";
			BufferedImage img = ImageIO.read(new File(filename));
            if(img != null){
                g2.drawImage(img, (int)p.getX() - 35, (int)p.getY() - 35, 75, 75, null);
            }
        } catch (IOException e) {
            System.out.println("Could not find image for the Robber.");
        }
        
    }

    private void drawPorts(Graphics2D g2) {
        ArrayList<String> ports = getPortnames();
        ArrayList<ArrayList<AffineTransform>> transforms = getTransformationsForPorts();
        
        for(int i = 0; i < ports.size(); i++){
            drawPort((Graphics2D) g2.create(),transforms.get(0).get(i),transforms.get(1).get(i),ports.get(i));
        }
    }
    
    public void drawPort(Graphics2D g2, AffineTransform transformer, AffineTransform rotate, String portDesc){
        g2.setColor(Color.BLACK);
        g2.transform(transformer);
        g2.transform(rotate);
        g2.drawString(portDesc,0, 0);
    }
    
    public ArrayList<String> getPortnames(){
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

    public ArrayList<ArrayList<AffineTransform>> getTransformationsForPorts(){
        ArrayList<AffineTransform> transformations = new ArrayList<>();
        ArrayList<AffineTransform> rotations = new ArrayList<>();

        AffineTransform transformer = new AffineTransform();
        AffineTransform rotate = new AffineTransform();
        
        transformer.translate(widthMargin + (int)(hexagonSide * 4.2), heightMargin - (int)(hexagonSide * 0.25));
        rotate.rotate(Math.toRadians(30));
        transformations.add(transformer);
        rotations.add(rotate);
        
        transformer = new AffineTransform();
        rotate = new AffineTransform();
        transformer.translate(widthMargin + (int)(hexagonSide * 7), heightMargin + (int)(hexagonSide * 1.4));
        rotate.rotate(Math.toRadians(30));
        transformations.add(transformer);
        rotations.add(rotate);
        
        transformer = new AffineTransform();
        rotate = new AffineTransform();
        transformer.translate(widthMargin + (int)(hexagonSide * 8.8), heightMargin + (int)(hexagonSide * 3.35));
        rotate.rotate(Math.toRadians(90));
        transformations.add(transformer);
        rotations.add(rotate);
       
        transformer = new AffineTransform();
        rotate = new AffineTransform();
        transformer.translate(widthMargin + (int)(hexagonSide * 7.1), heightMargin + (int)(hexagonSide * 6.85));
        rotate.rotate(Math.toRadians(-30));
        transformations.add(transformer);
        rotations.add(rotate);

        transformer = new AffineTransform();
        rotate = new AffineTransform();
        transformer.translate(widthMargin + (int)(hexagonSide * 4.2), heightMargin + (int)(hexagonSide * 8.5));
        rotate.rotate(Math.toRadians(-30));
        transformations.add(transformer);
        rotations.add(rotate);

        transformer = new AffineTransform();
        rotate = new AffineTransform();
        transformer.translate(widthMargin + (int)(hexagonSide * 1.1), heightMargin + (int)(hexagonSide * 7.6));
        rotate.rotate(Math.toRadians(30));
        transformations.add(transformer);
        rotations.add(rotate);
 
        transformer = new AffineTransform();
        rotate = new AffineTransform();
        transformer.translate(widthMargin + (int)(hexagonSide* 0.7), heightMargin + (int)(hexagonSide * 6.5));
        rotate.rotate(Math.toRadians(270));
        transformations.add(transformer);
        rotations.add(rotate);

        transformer = new AffineTransform();
        rotate = new AffineTransform();
        transformer.translate(widthMargin + (int)(hexagonSide* 0.7), heightMargin + (int)(hexagonSide * 3.2));
        rotate.rotate(Math.toRadians(270));
        transformations.add(transformer);
        rotations.add(rotate);

        transformer = new AffineTransform();
        rotate = new AffineTransform();
        transformer.translate(widthMargin + (int)(hexagonSide* 1.6), heightMargin + (int)(hexagonSide * 0.4));
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
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
}
