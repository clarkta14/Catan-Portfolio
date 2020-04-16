package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JFrame;

import lib.GraphPaperLayout;
import objects.CatanBoard;
import objects.PlayersController;

public class GameWindow {
    
	private PlayersController turnController;
	private CatanBoard catanBoard;
	private BoardWindow boardWindow;
    private OptionsPanel options; // To hold buttons for game options
    private PlayerInfo playerInfo; // Hold all player information
    
    public GameWindow(int numOfPlayers){
    	this.turnController = new PlayersController(numOfPlayers);
    	this.catanBoard = new CatanBoard(this.turnController);
    	this.boardWindow = new BoardWindow(this.catanBoard);
    	this.options = new OptionsPanel(this, catanBoard);
    	this.playerInfo = new PlayerInfo(this.turnController, numOfPlayers);
        showGUI();
    }

    private void showGUI() {
        JFrame frame = new JFrame("Catan");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	
        // Dimensions for adding the different components to the screen - CatanBoard, SideBar, etc.
        Dimension dim = new Dimension(5,6);
        Container content = frame.getContentPane();
        content.setLayout(new GraphPaperLayout(dim));
        
        //Adding components to the window
        content.add(options,new Rectangle(0,0,1,5));
        content.add(boardWindow,new Rectangle(1,0,4,4));
        content.add(playerInfo,new Rectangle(1,4,4,2));
        
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true); 
        boardWindow.repaint();
    }
    
    public PlayersController getPlayersController() {
    	return this.turnController;
    }

	public BoardWindow getBoardWindow() {
		return this.boardWindow;
	}
}

