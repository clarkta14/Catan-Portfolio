/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catan.csse376.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JFrame;
import com.catan.csse376.lib.GraphPaperLayout;

/**
 *
 * @author Indresh
 */
public class GameWindow {
    
    private CatanBoard catanBoard;
    //private SideBar sideBar; // To hold buttons for game options
    //private GameState gameState; // Hold all player information
    
    public GameWindow(){
        this.catanBoard = new CatanBoard();
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
        content.add(catanBoard,new Rectangle(1,0,4,4));
	//content.add(gameState,new Rectangle(1,4,4,2));
        
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true); 
        catanBoard.repaint();
    }
}
