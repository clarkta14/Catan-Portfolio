package gui;

import static org.junit.Assert.*;
import org.easymock.EasyMock;

import org.junit.Test;

import gui.OptionsPanel.EndTurnListener;
import objects.CatanBoard;
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
				.addMockedMethod("refreshPlayerStats")
				.createMock();
		
		PlayersController pc = new PlayersController(3);
		CatanBoard cb = new CatanBoard(pc);
		
		// put it in not the setup phase
		for(int i = 0; i < 6; i++) {
			pc.nextPlayer();
		}

		EasyMock.expect(gw.getPlayersController()).andStubReturn(pc);
		EasyMock.expect(gw.getBoardWindow()).andStubReturn(bw);
		EasyMock.expect(bw.getState()).andStubReturn(GameStates.idle);
		for(int i = 0; i < 3; i++) 
			gw.refreshPlayerStats();
		
		EasyMock.replay(bw, gw);
		
		OptionsPanel op = new OptionsPanel(gw, cb);
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
	
	@Test
	public void testEndTurnListenerNonSetup4Players() {
		BoardWindow bw = EasyMock.partialMockBuilder(BoardWindow.class)
				.addMockedMethod("getState")
				.createMock();
		GameWindow gw = EasyMock.partialMockBuilder(GameWindow.class)
				.addMockedMethod("getPlayersController")
				.addMockedMethod("getBoardWindow")
				.addMockedMethod("refreshPlayerStats")
				.createMock();
		
		PlayersController pc = new PlayersController(4);
		CatanBoard cb = new CatanBoard(pc);
		
		// put it in not the setup phase
		for(int i = 0; i < 8; i++) {
			pc.nextPlayer();
		}

		EasyMock.expect(gw.getPlayersController()).andStubReturn(pc);
		EasyMock.expect(gw.getBoardWindow()).andStubReturn(bw);
		EasyMock.expect(bw.getState()).andStubReturn(GameStates.idle);
		for(int i = 0; i < 4; i++) 
			gw.refreshPlayerStats();
		
		EasyMock.replay(bw, gw);
		
		OptionsPanel op = new OptionsPanel(gw, cb);
		EndTurnListener etl = op.new EndTurnListener();
		
		
		assertEquals(0, pc.getCurrentPlayerNum());
		etl.actionPerformed(null);
		assertEquals(1, pc.getCurrentPlayerNum());
		etl.actionPerformed(null);
		assertEquals(2, pc.getCurrentPlayerNum());
		etl.actionPerformed(null);
		assertEquals(3, pc.getCurrentPlayerNum());
		etl.actionPerformed(null);
		assertEquals(0, pc.getCurrentPlayerNum());
		
		EasyMock.verify(bw, gw);
	}
	
	@Test
	public void testEndTurnListenerSetup() {
		BoardWindow bw = EasyMock.partialMockBuilder(BoardWindow.class)
				.addMockedMethod("getState")
				.createMock();
		GameWindow gw = EasyMock.partialMockBuilder(GameWindow.class)
				.addMockedMethod("getPlayersController")
				.addMockedMethod("getBoardWindow")
				.createMock();
		
		PlayersController pc = new PlayersController(4);
		CatanBoard cb = new CatanBoard(pc);

		EasyMock.expect(gw.getPlayersController()).andStubReturn(pc);
		EasyMock.expect(gw.getBoardWindow()).andStubReturn(bw);
		EasyMock.expect(bw.getState()).andStubReturn(GameStates.setup);
		
		EasyMock.replay(bw, gw);
		
		OptionsPanel op = new OptionsPanel(gw, cb);
		EndTurnListener etl = op.new EndTurnListener();
		
		
		assertEquals(0, pc.getCurrentPlayerNum());
		etl.actionPerformed(null);
		assertEquals(0, pc.getCurrentPlayerNum());
		etl.actionPerformed(null);
		assertEquals(0, pc.getCurrentPlayerNum());
		
		EasyMock.verify(bw, gw);
	}

}
