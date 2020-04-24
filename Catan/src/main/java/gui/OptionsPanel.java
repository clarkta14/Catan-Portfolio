package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import lib.GraphPaperLayout;
import objects.CatanBoard;
import objects.Player;
import objects.PlayersController;
import objects.TileType;

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
	private ArrayList<OptionsPanelComponent> tradeWithBankPanel;
	private ArrayList<OptionsPanelComponent> tradeWithBankPaymentPanel;
	private ArrayList<OptionsPanelComponent> playersPanel;
	private ArrayList<OptionsPanelComponent> tradeWithPlayerPanel;
	private ArrayList<OptionsPanelComponent> tradeWithPlayerPaymentPanel;
	private ArrayList<OptionsPanelComponent> acceptTradeWithPlayerPanel;
	private Timer timer;
	private OptionsPanelComponent lastRolled;
	private TileType selectedResource;
	private int selectedPlayer;

	public OptionsPanel(GameWindow gameWindow, CatanBoard catanBoard) {
		this.gameWindow = gameWindow;
		this.catanBoard = catanBoard;
		this.playerController = this.gameWindow.getPlayersController();
		this.boardGUI = this.gameWindow.getBoardWindow();
		this.setupPanel = new ArrayList<>();
		this.infoPanel = new ArrayList<>();
		this.tradeWithBankPanel = new ArrayList<>();
		this.tradeWithBankPaymentPanel = new ArrayList<>();
		this.playersPanel = new ArrayList<>();
		this.tradeWithPlayerPanel = new ArrayList<>();
		this.tradeWithPlayerPaymentPanel = new ArrayList<>();
		this.acceptTradeWithPlayerPanel = new ArrayList<>();
		this.selectedResource = null;
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
		
		JButton placeCityButton = new JButton(new PlaceCityListener());
		placeCityButton.setText("Upgrade to City");
		actionPanel.add(new OptionsPanelComponent(placeCityButton, new Rectangle(4,6,6,2)));
		
		JButton placeRoadButton = new JButton(new PlaceRoadListener());
		placeRoadButton.setText("Place Road");
		actionPanel.add(new OptionsPanelComponent(placeRoadButton, new Rectangle(4,8,6,2)));
		
		JButton tradeWithBankButton = new JButton(new TradeWithBankButtonListener());
		tradeWithBankButton.setText("Trade Bank");
		actionPanel.add(new OptionsPanelComponent(tradeWithBankButton, new Rectangle(4,10,6,2)));
		
		JButton tradeWithPlayerButton = new JButton(new TradeWithPlayerButtonListener());
		tradeWithPlayerButton.setText("Trade Player");
		actionPanel.add(new OptionsPanelComponent(tradeWithPlayerButton, new Rectangle(4,12,6,2)));
		
		JButton buyDevCardButton = new JButton(new BuyDevCardListener());
		buyDevCardButton.setText("Buy Dev. Card");
		actionPanel.add(new OptionsPanelComponent(buyDevCardButton, new Rectangle(4,14,6,2)));
		
		JButton endTurnButton = new JButton(new EndTurnListener());
		endTurnButton.setText("End Turn");
		actionPanel.add(new OptionsPanelComponent(endTurnButton, new Rectangle(4,16,6,2)));
		
		this.lastRolled = new OptionsPanelComponent(new JLabel(""), new Rectangle(4, 20, 6, 2));
		this.lastRolled.getSwingComponent().setFont(font);
		this.lastRolled.getSwingComponent().setForeground(Color.BLACK);
		actionPanel.add(this.lastRolled);
	}
	
	private ArrayList<OptionsPanelComponent> createVictoryPanel() {
		ArrayList<OptionsPanelComponent> victoryPanel = new ArrayList<>();
		int playerNum = this.playerController.getCurrentPlayerNum() + 1;
		JLabel victoryMessage = new JLabel("Player " + playerNum + " WINS!!");
		victoryMessage.setFont(font);
		victoryPanel.add(new OptionsPanelComponent(victoryMessage, new Rectangle(2,3,10,2)));
		return victoryPanel;
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
	
	class PlaceCityListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle) && playerController.getCurrentPlayer().canBuyCity()) {
				placeInfoPanel("Upgrade a City");
				boardGUI.setState(GameStates.drop_city);
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
	
	class TradeWithBankButtonListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle)) {
				boardGUI.setState(GameStates.trade);
				tradeWithBankPanel();
			}
		}
	}
	
	class TradeWithPlayerButtonListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle)) {
				boardGUI.setState(GameStates.trade);
				playersPanelForTrade();
				//tradeWithPlayerPanel();
			}
		}
	}
	
	class ResetStateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle)) {
				Player currentPlayer = playerController.getCurrentPlayer();
				if(currentPlayer.isVictor()) {
					setOnOptionsPanel(createVictoryPanel());
				} else {
					gameWindow.refreshPlayerStats();
					setOnOptionsPanel(actionPanel);
				}
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
	
	public void tradeWithBankPanel() {
		this.tradeWithBankPanel = new ArrayList<>();
		JLabel instuctionLabel = new JLabel("What resource do you want?");
		this.tradeWithBankPanel.add(new OptionsPanelComponent(instuctionLabel, new Rectangle(2,2,6,2)));
		this.tradeWithBankPanel.add(new OptionsPanelComponent(selectItemToTradeFor(TileType.wool), new Rectangle(4,6,6,2)));
		this.tradeWithBankPanel.add(new OptionsPanelComponent(selectItemToTradeFor(TileType.wheat), new Rectangle(4,8,6,2)));
		this.tradeWithBankPanel.add(new OptionsPanelComponent(selectItemToTradeFor(TileType.wood), new Rectangle(4,10,6,2)));
		this.tradeWithBankPanel.add(new OptionsPanelComponent(selectItemToTradeFor(TileType.ore), new Rectangle(4,12,6,2)));
		this.tradeWithBankPanel.add(new OptionsPanelComponent(selectItemToTradeFor(TileType.brick), new Rectangle(4,14,6,2)));
		JButton cancelButton = new JButton(new CancelAction());
		cancelButton.setText("Cancel");
		tradeWithBankPanel.add(new OptionsPanelComponent(cancelButton, new Rectangle(4,17,6,2)));
		setOnOptionsPanel(tradeWithBankPanel);
	}
	
	public JButton selectItemToTradeFor(TileType t) {
		JButton resourceType = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				if(boardGUI.getState().equals(GameStates.trade)) {
					selectedResource = t;
					tradeWithBankPaymentPanel();
				}
			}
		});
		resourceType.setText(t.name());
		return resourceType;
	}
	
	public void tradeWithBankPaymentPanel() {
		this.tradeWithBankPaymentPanel = new ArrayList<>();
		JLabel instuctionLabel = new JLabel("Choose a resource as Payment");
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(instuctionLabel, new Rectangle(2,2,6,2)));
		JLabel instuctionLabel2 = new JLabel("Cost = Selected Resource X 4");
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(instuctionLabel2, new Rectangle(2,3,6,2)));
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(selectItemAsPayment(TileType.wool), new Rectangle(4,6,6,2)));
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(selectItemAsPayment(TileType.wheat), new Rectangle(4,8,6,2)));
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(selectItemAsPayment(TileType.wood), new Rectangle(4,10,6,2)));
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(selectItemAsPayment(TileType.ore), new Rectangle(4,12,6,2)));
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(selectItemAsPayment(TileType.brick), new Rectangle(4,14,6,2)));
		JButton cancelButton = new JButton(new CancelAction());
		cancelButton.setText("Cancel");
		tradeWithBankPaymentPanel.add(new OptionsPanelComponent(cancelButton, new Rectangle(4,17,6,2)));
		setOnOptionsPanel(tradeWithBankPaymentPanel);
	}
	
	public JButton selectItemAsPayment(TileType t) {
		JButton resourceType = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				if(boardGUI.getState().equals(GameStates.trade)) {
					HashMap<TileType, Integer> payment = new HashMap<>();
					payment.put(t, 4);
					boolean success = catanBoard.tradeWithBank(payment, selectedResource);
					if(success) {
						boardGUI.setState(GameStates.idle);
						gameWindow.refreshPlayerStats();
						setOnOptionsPanel(actionPanel);
					}
				}
			}
		});
		resourceType.setText(t.name());
		return resourceType;
	}
	
	public void playersPanelForTrade() {
		this.playersPanel = new ArrayList<>();
		JLabel instuctionLabel = new JLabel("Choose a player to trade with");
		this.playersPanel.add(new OptionsPanelComponent(instuctionLabel, new Rectangle(2,2,6,2)));
		int spacing = 0;
		
		for(int i = 0; i < this.playerController.getTotalNumOfPlayers(); i++) {
			int playerNum = i + 1;
			if(this.playerController.getCurrentPlayerNum() + 1 != playerNum) {
				this.playersPanel.add(new OptionsPanelComponent(selectPlayerToTradeWith(playerNum), new Rectangle(4,6+(spacing*2),6,2)));
				spacing++;
			}
		}

		JButton cancelButton = new JButton(new CancelAction());
		cancelButton.setText("Cancel");
		playersPanel.add(new OptionsPanelComponent(cancelButton, new Rectangle(4,16,6,2)));
		setOnOptionsPanel(playersPanel);
	}
	
	public JButton selectPlayerToTradeWith(int playerNum) {
		JButton playerButton = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				if(boardGUI.getState().equals(GameStates.trade)) {
					selectedPlayer = playerNum - 1;
					tradeWithPlayerPanel();
				}
			}
		});
		playerButton.setText("Player " + playerNum);
		return playerButton;
	}
	
	public void tradeWithPlayerPanel() {
		this.tradeWithPlayerPanel = new ArrayList<>();
	
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getPlayer(selectedPlayer), TileType.brick), new Rectangle(2,6,3,1)));
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getPlayer(selectedPlayer), TileType.wool), new Rectangle(6,6,3,1)));
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getPlayer(selectedPlayer), TileType.ore), new Rectangle(10,6,3,1)));
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getPlayer(selectedPlayer), TileType.wheat), new Rectangle(4,10,3,1)));
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getPlayer(selectedPlayer), TileType.wood), new Rectangle(8,10,3,1)));
		
		JLabel brickLabel = new JLabel("Brick");
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(brickLabel, new Rectangle(2,5,4,1)));
		
		JLabel woolLabel = new JLabel("Wool");
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(woolLabel, new Rectangle(6,5,4,1)));
		
		JLabel oreLabel = new JLabel("Ore");
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(oreLabel, new Rectangle(10,5,4,1)));

		JLabel wheatLabel = new JLabel("Wheat");
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(wheatLabel, new Rectangle(4,9,4,1)));

		JLabel woodLabel = new JLabel("Wood");
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(woodLabel, new Rectangle(8,9,4,1)));
		
		JButton submitResources = new JButton(new TradeWithPlayerForListener());
		submitResources.setText("Submit");
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(submitResources, new Rectangle(3,15,9,2)));
		
		JButton cancelButton = new JButton(new CancelAction());
		cancelButton.setText("Cancel");
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(cancelButton, new Rectangle(3,18,9,2)));
		
		JLabel instuctionLabel = new JLabel("Select resources you want");
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(instuctionLabel, new Rectangle(2,3,18,1)));
		
		setOnOptionsPanel(tradeWithPlayerPanel);
	}
	
	class TradeWithPlayerForListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.trade)) {
				tradeWithPlayerPaymentPanel();
			}
		}
	}
	
	public void tradeWithPlayerPaymentPanel() {
		this.tradeWithPlayerPaymentPanel = new ArrayList<>();
		
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getCurrentPlayer(), TileType.brick), new Rectangle(2,6,3,1)));
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getCurrentPlayer(), TileType.wool), new Rectangle(6,6,3,1)));
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getCurrentPlayer(), TileType.ore), new Rectangle(10,6,3,1)));
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getCurrentPlayer(), TileType.wheat), new Rectangle(4,10,3,1)));
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getCurrentPlayer(), TileType.wood), new Rectangle(8,10,3,1)));
		
		JLabel brickLabel = new JLabel("Brick");
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(brickLabel, new Rectangle(2,5,4,1)));
		
		JLabel woolLabel = new JLabel("Wool");
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(woolLabel, new Rectangle(6,5,4,1)));
		
		JLabel oreLabel = new JLabel("Ore");
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(oreLabel, new Rectangle(10,5,4,1)));

		JLabel wheatLabel = new JLabel("Wheat");
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(wheatLabel, new Rectangle(4,9,4,1)));

		JLabel woodLabel = new JLabel("Wood");
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(woodLabel, new Rectangle(8,9,4,1)));
		
		JButton submitResources = new JButton(new TradeWithPlayerListener());
		submitResources.setText("Submit Trade");
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(submitResources, new Rectangle(3,15,9,2)));
		
		JButton cancelButton = new JButton(new CancelAction());
		cancelButton.setText("Cancel");
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(cancelButton, new Rectangle(3,18,9,2)));
		
		JLabel instuctionLabel = new JLabel("Select payment");
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(instuctionLabel, new Rectangle(2,3,15,1)));
		
		setOnOptionsPanel(tradeWithPlayerPaymentPanel);
	}
	
	class TradeWithPlayerListener extends AbstractAction {
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.trade)) {
				HashMap<TileType, Integer> cost = new HashMap<>();
				cost.put(TileType.brick, ((JComboBox<Integer>) tradeWithPlayerPanel.get(0).getSwingComponent()).getSelectedIndex());
				cost.put(TileType.wool, ((JComboBox<Integer>) tradeWithPlayerPanel.get(1).getSwingComponent()).getSelectedIndex());
				cost.put(TileType.ore, ((JComboBox<Integer>) tradeWithPlayerPanel.get(2).getSwingComponent()).getSelectedIndex());
				cost.put(TileType.wheat, ((JComboBox<Integer>) tradeWithPlayerPanel.get(3).getSwingComponent()).getSelectedIndex());
				cost.put(TileType.wood, ((JComboBox<Integer>) tradeWithPlayerPanel.get(4).getSwingComponent()).getSelectedIndex());
				
				HashMap<TileType, Integer> payment = new HashMap<>();
				payment.put(TileType.brick, ((JComboBox<Integer>) tradeWithPlayerPaymentPanel.get(0).getSwingComponent()).getSelectedIndex());
				payment.put(TileType.wool, ((JComboBox<Integer>) tradeWithPlayerPaymentPanel.get(1).getSwingComponent()).getSelectedIndex());
				payment.put(TileType.ore, ((JComboBox<Integer>) tradeWithPlayerPaymentPanel.get(2).getSwingComponent()).getSelectedIndex());
				payment.put(TileType.wheat, ((JComboBox<Integer>) tradeWithPlayerPaymentPanel.get(3).getSwingComponent()).getSelectedIndex());
				payment.put(TileType.wood, ((JComboBox<Integer>) tradeWithPlayerPaymentPanel.get(4).getSwingComponent()).getSelectedIndex());
				
				acceptTradeWithPlayerPanel(cost, payment);
			}
		}
	}
	
	public void acceptTradeWithPlayerPanel(HashMap<TileType, Integer> cost, HashMap<TileType, Integer> payment) {
		this.acceptTradeWithPlayerPanel = new ArrayList<>();
		
		JLabel playerLabel = new JLabel("Player " + (selectedPlayer + 1));
		this.acceptTradeWithPlayerPanel.add(new OptionsPanelComponent(playerLabel, new Rectangle(2,3,15,1)));
		
		JLabel instuctionLabel = new JLabel("Accept this trade?");
		this.acceptTradeWithPlayerPanel.add(new OptionsPanelComponent(instuctionLabel, new Rectangle(2,6,15,1)));
		
		JLabel paymentLabel = new JLabel("Offered: " + payment);
		this.acceptTradeWithPlayerPanel.add(new OptionsPanelComponent(paymentLabel, new Rectangle(2,8,15,1)));
		
		JLabel costLabel = new JLabel("For: " + cost);
		this.acceptTradeWithPlayerPanel.add(new OptionsPanelComponent(costLabel, new Rectangle(2,10,15,1)));
		
		JButton yesButton = new JButton(new TradeWithPlayerApprovedListener(cost, payment));
		yesButton.setText("Yes");
		this.acceptTradeWithPlayerPanel.add(new OptionsPanelComponent(yesButton, new Rectangle(3,18,9,2)));
		
		JButton noButton = new JButton(new CancelAction());
		noButton.setText("No");
		this.acceptTradeWithPlayerPanel.add(new OptionsPanelComponent(noButton, new Rectangle(3,20,9,2)));
		
		setOnOptionsPanel(acceptTradeWithPlayerPanel);
	}
	
	class TradeWithPlayerApprovedListener extends AbstractAction {
		
		@SuppressWarnings("unused")
		private HashMap<TileType, Integer> cost;
		@SuppressWarnings("unused")
		private HashMap<TileType, Integer> payment;
		
		public TradeWithPlayerApprovedListener(HashMap<TileType, Integer> c, HashMap<TileType, Integer> p) {
			this.cost = c;
			this.payment = p;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.trade)) {		
				//boolean success = catanBoard.tradeWithPlayer(cost, payment);
				boolean success = true;
				if(success) {
					boardGUI.setState(GameStates.idle);
					gameWindow.refreshPlayerStats();
					setOnOptionsPanel(actionPanel);
				}
			}
		}
	}
	
	public JComboBox<Integer> makeDropDownResourceSelector(Player p, TileType t) {
		JComboBox<Integer> comboBox = new JComboBox<Integer>();
		comboBox.setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
			}
		});
		for(int i = 0; i <= p.getResourceCount(t); i++) {
			comboBox.addItem(i);
		}
		return comboBox;
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
