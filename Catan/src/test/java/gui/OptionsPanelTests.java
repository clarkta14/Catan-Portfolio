package gui;

import static org.junit.Assert.*;
import org.easymock.EasyMock;

import org.junit.Test;

import gui.OptionsPanel.EndTurnListener;
import objects.PlayersController;

public class OptionsPanelTests {

	@Test
	public void testEndTurnListenerNonSetup3Players() {
		BoardWindow bw = EasyMock.partialMockBuilder(BoardWindow.class)
				.addMockedMethod("getState")
				.createMock();
		GameWindow gw = EasyMock.partialMockBuilder(GameWindow.class)
				.addMockedMethod("getPlayersController")
				.addMockedMethod("getBoardWindow")
				.createMock();
		
		PlayersController pc = new PlayersController(3);
		
		// put it in not the setup phase
		for(int i = 0; i < 6; i++) {
			pc.nextPlayer();
		}

		EasyMock.expect(gw.getPlayersController()).andStubReturn(pc);
		EasyMock.expect(gw.getBoardWindow()).andStubReturn(bw);
		EasyMock.expect(bw.getState()).andStubReturn(GUIStates.idle);
		
		EasyMock.replay(bw, gw);
		
		OptionsPanel op = new OptionsPanel(gw);
		EndTurnListener etl = op.new EndTurnListener();
		
		
		assertEquals(0, pc.getCurrentPlayerNum());
		etl.actionPerformed(null);
		assertEquals(1, pc.getCurrentPlayerNum());
		etl.actionPerformed(null);
		assertEquals(2, pc.getCurrentPlayerNum());
		etl.actionPerformed(null);
		assertEquals(0, pc.getCurrentPlayerNum());
		
		EasyMock.verify(bw, gw);
	}

}
