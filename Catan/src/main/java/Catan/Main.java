package Catan;

import gui.BoardWindow;
import gui.CatanBoard;
import gui.GUIObjectConstructor;
import gui.GameWindow;
import gui.SetupPrompt;

/**
*
* @author Indresh
*/
public class Main {
	
   @SuppressWarnings("unused")
   public static void main(String args[]){
	   
	   SetupPrompt catanSetup = new SetupPrompt();
	   try {
	       catanSetup.scanPlayerNum();
		   int numberOfPlayers = catanSetup.getNumPlayers();
		   BoardWindow bw = new BoardWindow();
	       GameWindow catan = new GameWindow(bw);
	       GUIObjectConstructor objConstructor = new GUIObjectConstructor(bw);
	       CatanBoard board = new CatanBoard(3, objConstructor);
	   }catch(IllegalArgumentException e) {
		   
	   }
   }
   
   
   
}