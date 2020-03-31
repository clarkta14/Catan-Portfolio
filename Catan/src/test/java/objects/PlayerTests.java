package objects;

import static org.junit.Assert.*;

import java.awt.Color;
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
		StructurePosition loc = new StructurePosition(1,1,0);
		player1.addSettlement(1,1);
		assertEquals(loc.getX(), player1.getSettlements().get(0).getLocationOnBoard().getX());
		assertEquals(loc.getY(), player1.getSettlements().get(0).getLocationOnBoard().getY());
	}
	
	@Test
	public void addMultipleSettlements() {
		Player player1 = new Player(Color.orange);
		StructurePosition loc = new StructurePosition(1,1,0);
		player1.addSettlement(1,1);
		assertEquals(loc.getX(), player1.getSettlements().get(0).getLocationOnBoard().getX());
		assertEquals(loc.getY(), player1.getSettlements().get(0).getLocationOnBoard().getY());
		StructurePosition loc2 = new StructurePosition(2,2,0);
		player1.addSettlement(2,2);
		assertEquals(loc2.getX(), player1.getSettlements().get(1).getLocationOnBoard().getX());
		assertEquals(loc2.getY(), player1.getSettlements().get(1).getLocationOnBoard().getY());
	}
}
