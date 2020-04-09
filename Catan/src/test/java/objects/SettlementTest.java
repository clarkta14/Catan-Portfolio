package objects;

import static org.junit.Assert.*;

import java.awt.Color;
import org.junit.Test;

public class SettlementTest {

	@Test
	public void settlementConstructor() {
		Player plyr = new Player(Color.BLUE);
		Settlement s = new Settlement(plyr);
		assertEquals(plyr, s.getOwner());
	}
	
	@Test
	public void multipleSettlements() {
		Player plyr1 = new Player(Color.BLUE);
		Settlement s = new Settlement(plyr1);
		assertEquals(plyr1, s.getOwner());
		Player plyr2 = new Player(Color.RED);
		s = new Settlement(plyr2);
		assertEquals(plyr2, s.getOwner());
	}

}
