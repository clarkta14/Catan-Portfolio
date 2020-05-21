package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import lib.GraphPaperLayout;
import objects.CatanBoard;
import objects.PlayersController;

public class GameWindow {
    
	private PlayersController turnController;
	private CatanBoard catanBoard;
	private BoardWindow boardWindow;
    private OptionsPanel options;
    private PlayerInfo playerInfo;
    
    public GameWindow(int numOfPlayers){
    	this.turnController = new PlayersController(numOfPlayers);
    	this.catanBoard = new CatanBoard(this.turnController);
    	this.boardWindow = new BoardWindow(this.catanBoard);
    	this.options = new OptionsPanel(this, catanBoard);
    	this.playerInfo = new PlayerInfo(this.turnController, numOfPlayers);
        showGUI();
    }

    private void showGUI() {
    	JFrame frame = new JFrame(Messages.getString("GameWindow.GAME_NAME"));
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	
        Dimension dim = new Dimension(5,6);
        Container content = frame.getContentPane();
        content.setLayout(new GraphPaperLayout(dim));
        
        content.add(options,new Rectangle(0,0,1,5));
        content.add(boardWindow,new Rectangle(1,0,4,4));
        content.add(new JScrollPane(playerInfo),new Rectangle(1,4,4,2));
        
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
	
	public void refreshPlayerStats() {
		playerInfo.updateAllPlayerInfo();
	}
}

