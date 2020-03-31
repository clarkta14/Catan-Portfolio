package objects;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class SettlementTest {

	@Test
	public void settlementConstructor() {
		Point p = new Point(1,1);
		Settlement s = new Settlement(p);
		assertEquals(s.getLocationOnBoard(), p);
	}
	
	@Test
	public void settlementLocation() {
		Point p1 = new Point(1,1);
		Settlement s1 = new Settlement(p1);
		assertEquals(s1.getLocationOnBoard(), p1);
		
		Point p2 = new Point(2,2);
		Settlement s2 = new Settlement(p2);
		assertEquals(s2.getLocationOnBoard(), p2);
	}

}
