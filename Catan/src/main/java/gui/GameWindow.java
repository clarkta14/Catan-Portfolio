package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JFrame;

import lib.GraphPaperLayout;
import objects.Player;

public class GameWindow {
    
	private BoardWindow boardWindow;
    //private SideBar sideBar; // To hold buttons for game options
    //private GameState gameState; // Hold all player information
    
    public GameWindow(BoardWindow bw){
    	this.boardWindow = bw;
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
        //content.add(sideBar,new Rectangle(0,0,1,5));
        content.add(boardWindow,new Rectangle(1,0,4,4));
	//content.add(gameState,new Rectangle(1,4,4,2));
        
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true); 
        boardWindow.repaint();
    }
}

