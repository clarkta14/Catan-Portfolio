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

}
