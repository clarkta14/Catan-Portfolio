package objects;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class SettlementTest {

	@Test
	public void settlementConstructor() {
		Settlement s = new Settlement(1,1);
		assertEquals(s.getLocationOnBoard().getX(), 1);
		assertEquals(s.getLocationOnBoard().getY(), 1);
	}
	
	@Test
	public void settlementLocation() {
		Settlement s = new Settlement(1,1);
		assertEquals(s.getLocationOnBoard().getX(), 1);
		assertEquals(s.getLocationOnBoard().getY(), 1);
		
		Settlement s2 = new Settlement(2,2);
		assertEquals(s2.getLocationOnBoard().getX(), 2);
		assertEquals(s2.getLocationOnBoard().getY(), 2);
	}

}
