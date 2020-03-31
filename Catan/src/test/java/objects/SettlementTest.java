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

}
