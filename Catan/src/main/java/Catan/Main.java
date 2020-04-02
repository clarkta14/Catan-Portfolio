package Catan;

import java.awt.Color;
import java.util.ArrayList;

import gui.BoardWindow;
import gui.CatanBoard;
import gui.GameWindow;
import objects.Player;

/**
*
* @author Indresh
*/
public class Main {
	
   @SuppressWarnings("unused")
   public static void main(String args[]){
	   // Temp until the intro page
	   ArrayList<Player> plyrs = new ArrayList<>();
	   Player p = new Player(Color.BLUE);
	   plyrs.add(p);
	   p = new Player(Color.RED);
	   plyrs.add(p);
	   p = new Player(Color.WHITE);
	   plyrs.add(p);
	   p = new Player(Color.ORANGE);
	   plyrs.add(p);
	   
	   BoardWindow bw = new BoardWindow();
       GameWindow catan = new GameWindow(bw);
       CatanBoard board = new CatanBoard(plyrs, bw);
   }
}