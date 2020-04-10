package objects;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class RoadTests {

	@Test
	public void testConstructor1() {
		Player player1 = new Player(Color.BLUE);
		Road player1Road = new Road(player1);
		assertEquals(player1, player1Road.getOwner());
	}
	
	@Test
	public void testConstructor2() {
		Player player2 = new Player(Color.RED);
		Road player2Road = new Road(player2);
		assertEquals(player2, player2Road.getOwner());
	}
	
	@Test
	public void testAngle1() {
		Player player1 = new Player(Color.BLUE);
		Road player1Road = new Road(player1);
		player1Road.setAngle(2);
		assertEquals(2, player1Road.getAngle());
	}

}
