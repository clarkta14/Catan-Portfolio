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

}
