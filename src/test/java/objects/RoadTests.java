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
		player1Road.setAngle(3);
		assertEquals(3, player1Road.getAngle());
	}
	
	@Test
	public void testAngle2() {
		Player player1 = new Player(Color.BLUE);
		Road player1Road = new Road(player1);
		player1Road.setAngle(1);
		assertEquals(1, player1Road.getAngle());
	}
	
	@Test
	public void testAngleBad1() {
		Player player1 = new Player(Color.BLUE);
		Road player1Road = new Road(player1);
		try {
			player1Road.setAngle(4);
			fail("Test should throw exception");
		} catch(IllegalArgumentException e) {
			assertEquals("Angle must be 1, 2, or 3.", e.getMessage());
		}
	}
	
	@Test
	public void testAngleBad2() {
		Player player1 = new Player(Color.BLUE);
		Road player1Road = new Road(player1);
		try {
			player1Road.setAngle(5);
			fail("Test should throw exception");
		} catch(IllegalArgumentException e) {
			assertEquals("Angle must be 1, 2, or 3.", e.getMessage());
		}
	}

}
