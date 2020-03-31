package objects;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;

import org.junit.Test;

public class PlayerTests {

	@Test
	public void constructPlayer() {
		Player player = new Player(Color.orange);
		assertEquals(Color.orange, player.getColor());
	}
	
	@Test
	public void construct2PlayerDifferentColors() {
		Player player1 = new Player(Color.orange);
		assertEquals(Color.orange, player1.getColor());
		
		Player player2 = new Player(Color.blue);
		assertEquals(Color.blue, player2.getColor());
	}
	
	@Test
	public void addSettlement() {
		Player player1 = new Player(Color.orange);
		Point loc = new Point(1,1);
		player1.addSettlement(loc);
		assertEquals(loc, player1.getSettlements().get(0).getLocationOnBoard());
	}
	
	@Test
	public void addMultipleSettlements() {
		Player player1 = new Player(Color.orange);
		Point loc = new Point(1,1);
		player1.addSettlement(loc);
		assertEquals(loc, player1.getSettlements().get(0).getLocationOnBoard());
		Point loc2 = new Point(2,2);
		player1.addSettlement(loc2);
		assertEquals(loc2, player1.getSettlements().get(1).getLocationOnBoard());
	}
}
