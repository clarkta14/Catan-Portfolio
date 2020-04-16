package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import lib.GraphPaperLayout;
import objects.CatanBoard;
import objects.Player;
import objects.PlayersController;

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel {
	private final Font font = new Font("Arial", 1, 16);
	private OptionsPanelComponent currentPlayerNameBox;
	private GameWindow gameWindow;
	private PlayersController playerController;
	private BoardWindow boardGUI;
	private CatanBoard catanBoard;
	private ArrayList<OptionsPanelComponent> setupPanel;
	private ArrayList<OptionsPanelComponent> actionPanel;
	private ArrayList<OptionsPanelComponent> infoPanel;
	private Timer timer;
	private OptionsPanelComponent lastRolled;

	public OptionsPanel(GameWindow gameWindow, CatanBoard catanBoard) {
		this.gameWindow = gameWindow;
		this.catanBoard = catanBoard;
		this.playerController = this.gameWindow.getPlayersController();
		this.boardGUI = this.gameWindow.getBoardWindow();
		this.setupPanel = new ArrayList<>();
		this.infoPanel = new ArrayList<>();
		this.setLayout(new GraphPaperLayout(new Dimension(14, 24)));
		this.currentPlayerNameBox = new OptionsPanelComponent(new JLabel(""), new Rectangle(1, 1, 12, 1));
		this.currentPlayerNameBox.getSwingComponent().setFont(font);
		this.currentPlayerNameBox.getSwingComponent().setForeground(Color.CYAN);
		setCurrentPlayer(this.playerController.getCurrentPlayer(), this.playerController.getCurrentPlayerNum());
		add(this.currentPlayerNameBox.getSwingComponent(), this.currentPlayerNameBox.getRectangle());
		createActionPanel();
		setupPhase();
	}

	public void setupPhase() {
		if(boardGUI.getState().equals(GameStates.setup)) {
			final JLabel start = new JLabel("Setup phase: ");
			start.setFont(font);
			setupPanel.add(new OptionsPanelComponent(start, new Rectangle(2,3,10,2)));
			JButton begin = new JButton(new DropSettlementSetupListener());
			begin.setText("Place");
			setupPanel.add(new OptionsPanelComponent(begin, new Rectangle(4,6,6,2)));
			setupPanel();
		}
	}
	
	class DropSettlementSetupListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.setup)) {
				if (!playerController.isBackwardsSetup()) {
					boardGUI.setState(GameStates.drop_settlement_setup);
				} else {
					boardGUI.setState(GameStates.drop_settlement_setup_final);
				}
				placeInfoPanel("Place a settlement");
				timer = new Timer(50, new PlaceRoadSetupListener());
				timer.start();
			}
		}
	}
	
	class PlaceRoadSetupListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!boardGUI.getState().equals(GameStates.drop_settlement_setup)
					&& !boardGUI.getState().equals(GameStates.drop_settlement_setup_final)) {
				timer.stop();
				gameWindow.refreshPlayerStats();
				boardGUI.setState(GameStates.drop_road_setup);
				placeInfoPanel("Place a road");
				timer = new Timer(50, new ResetStateListenerSetup());
				timer.start();
			}
		}
	}
	
	class ResetStateListenerSetup implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!boardGUI.getState().equals(GameStates.drop_road_setup)) {
				timer.stop();
				playerController.nextPlayer();
				setCurrentPlayer(playerController.getCurrentPlayer(), playerController.getCurrentPlayerNum());
				if(playerController.isInitialSetup()) {
					boardGUI.setState(GameStates.setup);
					setupPanel();
				} else {
					boardGUI.setState(GameStates.idle);
					setOnOptionsPanel(actionPanel);
					addCancelButtonToInfoPanel();
					int rolled = catanBoard.endTurnAndRoll();
					setLastRolled(rolled);
					gameWindow.refreshPlayerStats();
				}
			}	
		}

		private void addCancelButtonToInfoPanel() {
			JButton cancelButton = new JButton(new CancelAction());
			cancelButton.setText("Cancel");
			infoPanel.add(new OptionsPanelComponent(cancelButton, new Rectangle(4,6,6,2)));			
		}	
	}
	
	private void createActionPanel() {
		actionPanel = new ArrayList<>();
		JButton placeSettlementButton = new JButton(new PlaceSettlementListener());
		placeSettlementButton.setText("Place Settlement");
		actionPanel.add(new OptionsPanelComponent(placeSettlementButton, new Rectangle(4,4,6,2)));
		
		JButton placeRoadButton = new JButton(new PlaceRoadListener());
		placeRoadButton.setText("Place Road");
		actionPanel.add(new OptionsPanelComponent(placeRoadButton, new Rectangle(4,6,6,2)));
		
		JButton tradeWithBankButton = new JButton();
		tradeWithBankButton.setText("Trade Bank");
		actionPanel.add(new OptionsPanelComponent(tradeWithBankButton, new Rectangle(4,8,6,2)));
		
		JButton tradeWithPlayerButton = new JButton();
		tradeWithPlayerButton.setText("Trade Player");
		actionPanel.add(new OptionsPanelComponent(tradeWithPlayerButton, new Rectangle(4,10,6,2)));
		
		JButton buyDevCardButton = new JButton(new BuyDevCardListener());
		buyDevCardButton.setText("Buy Dev. Card");
		actionPanel.add(new OptionsPanelComponent(buyDevCardButton, new Rectangle(4,12,6,2)));
		
		JButton endTurnButton = new JButton(new EndTurnListener());
		endTurnButton.setText("End Turn");
		actionPanel.add(new OptionsPanelComponent(endTurnButton, new Rectangle(4,14,6,2)));
		
		this.lastRolled = new OptionsPanelComponent(new JLabel(""), new Rectangle(4, 18, 6, 2));
		this.lastRolled.getSwingComponent().setFont(font);
		this.lastRolled.getSwingComponent().setForeground(Color.BLACK);
		actionPanel.add(this.lastRolled);
	}
	
	class CancelAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			boardGUI.setState(GameStates.idle);
			setOnOptionsPanel(actionPanel);
		}
	}
	
	class BuyDevCardListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle) && catanBoard.buyDevelopmentCard()) {
				gameWindow.refreshPlayerStats();
			}
		}
	}
	
	class PlaceSettlementListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle) && playerController.getCurrentPlayer().canBuySettlement()) {
				placeInfoPanel("Place a settlement");
				boardGUI.setState(GameStates.drop_settlement);
				timer = new Timer(50, new ResetStateListener());
				timer.start();
			}
		}
	}
	
	class PlaceRoadListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle) && playerController.getCurrentPlayer().canBuyRoad()) {
				placeInfoPanel("Place a road");
				boardGUI.setState(GameStates.drop_road);
				timer = new Timer(50, new ResetStateListener());
				timer.start();
			}
		}
	}
	
	class ResetStateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle)) {
				gameWindow.refreshPlayerStats();
				setOnOptionsPanel(actionPanel);
			}	
		}	
	}
	
	class EndTurnListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle)) {
				playerController.nextPlayer();
				setCurrentPlayer(playerController.getCurrentPlayer(), playerController.getCurrentPlayerNum());
				
				//TODO: roll the dice and allocate resources (beware 7)
				int rolled = catanBoard.endTurnAndRoll();
				setLastRolled(rolled);
				gameWindow.refreshPlayerStats();
			}
		}
	}
	

	public void setCurrentPlayer(Player p, int num) {
		JLabel label = (JLabel) currentPlayerNameBox.getSwingComponent();
		label.setText("    Player " + (num + 1));
		label.setOpaque(true);
		label.setBackground(p.getColor());
	}
	
	public void setLastRolled(int lastRolledNum) {
		JLabel label = (JLabel) this.lastRolled.getSwingComponent();
		label.setText("Last Rolled: " + lastRolledNum);
		label.setOpaque(true);
	}
	
	private void placeInfoPanel(String string) {
		if(this.infoPanel.size() < 1) {
			JLabel content = new JLabel();
			content.setFont(font);
			OptionsPanelComponent opTemp = new OptionsPanelComponent(content, new Rectangle(2,3,10,2));
			this.infoPanel.add(opTemp);
		}
		
		((JLabel) infoPanel.get(0).getSwingComponent()).setText(string);
		setOnOptionsPanel(infoPanel);
	}
	
	public void setupPanel() {
		setOnOptionsPanel(setupPanel);
	}

	private void setOnOptionsPanel(ArrayList<OptionsPanelComponent> panel) {
		this.removeAll();
		add(this.currentPlayerNameBox.getSwingComponent(), this.currentPlayerNameBox.getRectangle());
		for (OptionsPanelComponent opc : panel) {
			this.add(opc.getSwingComponent(), opc.getRectangle());
		}
		repaint();
		validate();
	}
}