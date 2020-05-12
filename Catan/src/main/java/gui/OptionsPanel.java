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
import objects.DevelopmentCardType;
import objects.MonopolyCard;
import objects.Player;
import objects.PlayersController;
import objects.TileType;
import objects.YearOfPlentyCard;

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel {
	private final Font font = new Font("Arial", 1, 16);
	private OptionsPanelComponent currentPlayerNameBox;
	private GameWindow gameWindow;
	private PlayersController playerController;
	private BoardWindow boardGUI;
	private CatanBoard catanBoard;
	private TileType yearOfPlentyResource;
	private ArrayList<OptionsPanelComponent> setupPanel;
	private ArrayList<OptionsPanelComponent> actionPanel;
	private ArrayList<OptionsPanelComponent> infoPanel;
	private ArrayList<OptionsPanelComponent> tradeWithBankPanel;
	private ArrayList<OptionsPanelComponent> tradeWithBankPaymentPanel;
	private ArrayList<OptionsPanelComponent> playersPanel;
	private ArrayList<OptionsPanelComponent> tradeWithPlayerPanel;
	private ArrayList<OptionsPanelComponent> tradeWithPlayerPaymentPanel;
	private ArrayList<OptionsPanelComponent> acceptTradeWithPlayerPanel;
	private ArrayList<OptionsPanelComponent> stealFromPlayerPanel;
	private ArrayList<OptionsPanelComponent> moveRobberInfoPanel;
	private ArrayList<OptionsPanelComponent> discardPanel;
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
		this.stealFromPlayerPanel = new ArrayList<>();
		this.moveRobberInfoPanel = new ArrayList<>();
		JLabel moveRobberInstuctionLabel = new JLabel(Messages.getString("OptionsPanel.45")); //$NON-NLS-1$
		this.moveRobberInfoPanel.add(new OptionsPanelComponent(moveRobberInstuctionLabel, new Rectangle(2,3,18,1)));
		this.selectedResource = null;
		this.yearOfPlentyResource = null;
		this.setLayout(new GraphPaperLayout(new Dimension(14, 24)));
		this.currentPlayerNameBox = new OptionsPanelComponent(new JLabel(Messages.getString("OptionsPanel.0")), new Rectangle(1, 1, 12, 1)); //$NON-NLS-1$
		this.currentPlayerNameBox.getSwingComponent().setFont(font);
		this.currentPlayerNameBox.getSwingComponent().setForeground(Color.CYAN);
		setCurrentPlayer(this.playerController.getCurrentPlayer(), this.playerController.getCurrentPlayerNum());
		add(this.currentPlayerNameBox.getSwingComponent(), this.currentPlayerNameBox.getRectangle());
		createActionPanel();
		setupPhase();
	}

	public void setupPhase() {
		if(boardGUI.getState().equals(GameStates.setup)) {
			final JLabel start = new JLabel(Messages.getString("OptionsPanel.1")); //$NON-NLS-1$
			start.setFont(font);
			setupPanel.add(new OptionsPanelComponent(start, new Rectangle(2,3,10,2)));
			JButton begin = new JButton(new DropSettlementSetupListener());
			begin.setText(Messages.getString("OptionsPanel.2")); //$NON-NLS-1$
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
				placeInfoPanel(Messages.getString("OptionsPanel.3")); //$NON-NLS-1$
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
				placeInfoPanel(Messages.getString("OptionsPanel.4")); //$NON-NLS-1$
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
			cancelButton.setText(Messages.getString("OptionsPanel.5")); //$NON-NLS-1$
			infoPanel.add(new OptionsPanelComponent(cancelButton, new Rectangle(4,6,6,2)));			
		}	
	}
	
	private void createActionPanel() {
		actionPanel = new ArrayList<>();
		JButton placeSettlementButton = new JButton(new PlaceSettlementListener());
		placeSettlementButton.setText(Messages.getString("OptionsPanel.6")); //$NON-NLS-1$
		actionPanel.add(new OptionsPanelComponent(placeSettlementButton, new Rectangle(4,4,6,2)));
		
		JButton placeCityButton = new JButton(new PlaceCityListener());
		placeCityButton.setText(Messages.getString("OptionsPanel.7")); //$NON-NLS-1$
		actionPanel.add(new OptionsPanelComponent(placeCityButton, new Rectangle(4,6,6,2)));
		
		JButton placeRoadButton = new JButton(new PlaceRoadListener());
		placeRoadButton.setText(Messages.getString("OptionsPanel.8")); //$NON-NLS-1$
		actionPanel.add(new OptionsPanelComponent(placeRoadButton, new Rectangle(4,8,6,2)));
		
		JButton tradeWithBankButton = new JButton(new TradeWithBankButtonListener());
		tradeWithBankButton.setText(Messages.getString("OptionsPanel.9")); //$NON-NLS-1$
		actionPanel.add(new OptionsPanelComponent(tradeWithBankButton, new Rectangle(4,10,6,2)));
		
		JButton tradeWithPlayerButton = new JButton(new TradeWithPlayerButtonListener());
		tradeWithPlayerButton.setText(Messages.getString("OptionsPanel.10")); //$NON-NLS-1$
		actionPanel.add(new OptionsPanelComponent(tradeWithPlayerButton, new Rectangle(4,12,6,2)));
		
		JButton buyDevCardButton = new JButton(new BuyDevCardListener());
		buyDevCardButton.setText(Messages.getString("OptionsPanel.11")); //$NON-NLS-1$
		actionPanel.add(new OptionsPanelComponent(buyDevCardButton, new Rectangle(4,14,6,2)));
		
		JButton playDevCardButton = new JButton(new PlayDevCardListener());
		playDevCardButton.setText(Messages.getString("OptionsPanel.40")); //$NON-NLS-1$
		actionPanel.add(new OptionsPanelComponent(playDevCardButton, new Rectangle(4,16,6,2)));

		JButton endTurnButton = new JButton(new EndTurnListener());
		endTurnButton.setText(Messages.getString("OptionsPanel.12")); //$NON-NLS-1$
		actionPanel.add(new OptionsPanelComponent(endTurnButton, new Rectangle(4,20,6,2)));
		
		this.lastRolled = new OptionsPanelComponent(new JLabel(Messages.getString("OptionsPanel.0")), new Rectangle(4, 22, 6, 2)); //$NON-NLS-1$
		this.lastRolled.getSwingComponent().setFont(font);
		this.lastRolled.getSwingComponent().setForeground(Color.BLACK);
		actionPanel.add(this.lastRolled);
	}
	
	private void developmentCardPanel() {
		ArrayList<OptionsPanelComponent> devCardButtons = new ArrayList<>();
		int spacing = 0;
		
		if(this.playerController.getCurrentPlayer().getDevelopmentCardCount(DevelopmentCardType.year_of_plenty_card) > 0) {
			JButton playYearOfPlentyCard = new JButton(new YearOfPlentyCardButton());
			playYearOfPlentyCard.setText(Messages.getString("OptionsPanel.38")); //$NON-NLS-1$
			devCardButtons.add(new OptionsPanelComponent(playYearOfPlentyCard, new Rectangle(4, 4 + spacing ,6,2)));
			spacing+=2;
		}
		if(this.playerController.getCurrentPlayer().getDevelopmentCardCount(DevelopmentCardType.monopoly_card) > 0) {
			JButton monopolyCardButton = new JButton(new MonopolyCardButton());
			monopolyCardButton.setText(Messages.getString("OptionsPanel.39")); //$NON-NLS-1$
			devCardButtons.add(new OptionsPanelComponent(monopolyCardButton, new Rectangle(4, 4 + spacing ,6,2)));
			spacing+=2;
		}
		if(this.playerController.getCurrentPlayer().getDevelopmentCardCount(DevelopmentCardType.road_building_card) > 0) {
			JButton roadBuildingCardButton = new JButton(new RoadBuildingCardButton());
			roadBuildingCardButton.setText(Messages.getString("OptionsPanel.41")); //$NON-NLS-1$
			devCardButtons.add(new OptionsPanelComponent(roadBuildingCardButton, new Rectangle(4, 4 + spacing ,6,2)));
			spacing+=2;
		}
		if(this.playerController.getCurrentPlayer().getDevelopmentCardCount(DevelopmentCardType.knight) > 0) {
			JButton knightCardButton = new JButton(new KnightCardButton());
			knightCardButton.setText(Messages.getString("OptionsPanel.46")); //$NON-NLS-1$
			devCardButtons.add(new OptionsPanelComponent(knightCardButton, new Rectangle(4, 4 + spacing ,6,2)));
			spacing+=2;
		}
		
		JButton cancelButton = new JButton(new CancelAction());
		cancelButton.setText(Messages.getString("OptionsPanel.5"));
		devCardButtons.add(new OptionsPanelComponent(cancelButton, new Rectangle(4,14,6,2)));
		setOnOptionsPanel(devCardButtons);
	}
	
	private ArrayList<OptionsPanelComponent> createVictoryPanel() {
		ArrayList<OptionsPanelComponent> victoryPanel = new ArrayList<>();
		int playerNum = this.playerController.getCurrentPlayerNum() + 1;
		JLabel victoryMessage = new JLabel(Messages.getString("OptionsPanel.13") + playerNum + Messages.getString("OptionsPanel.14")); //$NON-NLS-1$
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
	
	class PlayDevCardListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle)) {
				timer.stop();
				developmentCardPanel();
			}
		}
	}
	
	class RoadBuildingCardButton extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			Player currentPlayer = playerController.getCurrentPlayer();
			if(boardGUI.getState().equals(GameStates.idle) && currentPlayer.getDevelopmentCardCount(DevelopmentCardType.road_building_card) >= 1) {
				currentPlayer.removeDevelopmentCard(DevelopmentCardType.road_building_card);
				placeInfoPanel(Messages.getString("OptionsPanel.42")); //$NON-NLS-1$
				boardGUI.setState(GameStates.drop_road_card);
				createTimer(new RoadBuildingCardFinal());
			}
		}

	}
	
	class KnightCardButton extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			Player currentPlayer = playerController.getCurrentPlayer();
			if(boardGUI.getState().equals(GameStates.idle) && currentPlayer.getDevelopmentCardCount(DevelopmentCardType.knight) >= 1) {
				currentPlayer.removeDevelopmentCard(DevelopmentCardType.knight);
				setOnOptionsPanel(moveRobberInfoPanel); //$NON-NLS-1$
				boardGUI.setState(GameStates.move_robber);
				createTimer(new MoveToStealPhaseStateListener(-1));
			}
		}

	}
	
	private void createTimer(ActionListener action) {
		timer.stop();
		timer = new Timer(50, action);
		timer.start();
	}
	
	class RoadBuildingCardFinal extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle)) {
				boardGUI.setState(GameStates.drop_road_card);
				placeInfoPanel(Messages.getString("OptionsPanel.43")); //$NON-NLS-1$
				createTimer(new ResetStateListener());
			}
		}
	}
	
	class YearOfPlentyCardButton extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			Player currentPlayer = playerController.getCurrentPlayer();
			if(boardGUI.getState().equals(GameStates.idle) && currentPlayer.getDevelopmentCardCount(DevelopmentCardType.year_of_plenty_card) >= 1) {
				boardGUI.setState(GameStates.play_card);
				yearOfPlentyPanel(currentPlayer);
			}
		}
	}
	
	public void yearOfPlentyPanel(Player currentPlayer) {
		ArrayList<OptionsPanelComponent> buttons = new ArrayList<>();
		yearOfPlentyResource = null;
		JLabel instuctionLabel = new JLabel("Select Desired Resources (two choices)"); 
		buttons.add(new OptionsPanelComponent(instuctionLabel, new Rectangle(2,2,6,2)));
		buttons.add(new OptionsPanelComponent(yearOfPlentyButton(currentPlayer, TileType.wool), new Rectangle(4,6,6,2)));
		buttons.add(new OptionsPanelComponent(yearOfPlentyButton(currentPlayer, TileType.wheat), new Rectangle(4,8,6,2)));
		buttons.add(new OptionsPanelComponent(yearOfPlentyButton(currentPlayer, TileType.wood), new Rectangle(4,10,6,2)));
		buttons.add(new OptionsPanelComponent(yearOfPlentyButton(currentPlayer, TileType.ore), new Rectangle(4,12,6,2)));
		buttons.add(new OptionsPanelComponent(yearOfPlentyButton(currentPlayer, TileType.brick), new Rectangle(4,14,6,2)));
		setOnOptionsPanel(buttons);
	}
	
	public JButton yearOfPlentyButton(Player currentPlayer, TileType resourceType) {
		JButton resourceButton = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				if(boardGUI.getState().equals(GameStates.play_card)) {
					if (yearOfPlentyResource == null) {
						yearOfPlentyResource = resourceType;
					} else if (yearOfPlentyResource != null) {
						YearOfPlentyCard card = (YearOfPlentyCard) currentPlayer.removeDevelopmentCard(DevelopmentCardType.year_of_plenty_card);
						card.playCard(yearOfPlentyResource, resourceType);
						boardGUI.setState(GameStates.idle);
						setOnOptionsPanel(actionPanel);
						gameWindow.refreshPlayerStats();
					}
				}
			}
		});
		resourceButton.setText(resourceType.name());
		return resourceButton;
	}
	
	class MonopolyCardButton extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			Player currentPlayer = playerController.getCurrentPlayer();
			if(boardGUI.getState().equals(GameStates.idle) && currentPlayer.getDevelopmentCardCount(DevelopmentCardType.monopoly_card) >= 1) {
				boardGUI.setState(GameStates.play_card);
				monopolyPanel(currentPlayer);
			}
		}
	}
	
	public void monopolyPanel(Player currentPlayer) {
		ArrayList<OptionsPanelComponent> buttons = new ArrayList<>();
		JLabel instuctionLabel = new JLabel("Select Desired Resource"); 
		buttons.add(new OptionsPanelComponent(instuctionLabel, new Rectangle(2,2,6,2)));
		buttons.add(new OptionsPanelComponent(monopolyButton(currentPlayer, TileType.wool), new Rectangle(4,6,6,2)));
		buttons.add(new OptionsPanelComponent(monopolyButton(currentPlayer, TileType.wheat), new Rectangle(4,8,6,2)));
		buttons.add(new OptionsPanelComponent(monopolyButton(currentPlayer, TileType.wood), new Rectangle(4,10,6,2)));
		buttons.add(new OptionsPanelComponent(monopolyButton(currentPlayer, TileType.ore), new Rectangle(4,12,6,2)));
		buttons.add(new OptionsPanelComponent(monopolyButton(currentPlayer, TileType.brick), new Rectangle(4,14,6,2)));
		setOnOptionsPanel(buttons);
	}
	
	public JButton monopolyButton(Player currentPlayer, TileType resourceType) {
		JButton resourceButton = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				if(boardGUI.getState().equals(GameStates.play_card)) {
					MonopolyCard card = (MonopolyCard) currentPlayer.removeDevelopmentCard(DevelopmentCardType.monopoly_card);
					card.playCard(resourceType);
					boardGUI.setState(GameStates.idle);
					setOnOptionsPanel(actionPanel);
					gameWindow.refreshPlayerStats();
				}
			}
		});
		resourceButton.setText(resourceType.name());
		return resourceButton;
	}
	
	class PlaceSettlementListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle) && playerController.getCurrentPlayer().canBuySettlement()) {
				placeInfoPanel(Messages.getString("OptionsPanel.15")); //$NON-NLS-1$
				boardGUI.setState(GameStates.drop_settlement);
				createTimer(new ResetStateListener());
			}
		}
	}
	
	class PlaceCityListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle) && playerController.getCurrentPlayer().canBuyCity()) {
				placeInfoPanel(Messages.getString("OptionsPanel.16")); //$NON-NLS-1$
				boardGUI.setState(GameStates.drop_city);
				createTimer(new ResetStateListener());
			}
		}
	}
	
	class PlaceRoadListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.idle) && playerController.getCurrentPlayer().canBuyRoad()) {
				placeInfoPanel(Messages.getString("OptionsPanel.17")); //$NON-NLS-1$
				boardGUI.setState(GameStates.drop_road);
				createTimer(new ResetStateListener());
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
				
				int rolled = catanBoard.endTurnAndRoll();
				if(rolled == 7) {
					boardGUI.setState(GameStates.discard_robber);
					discardForRobber(0);
				} else {
					setLastRolled(rolled);
					gameWindow.refreshPlayerStats();
				}
			}
		}
	}
	
	private void discardForRobber(int playerNum) {
		
		gameWindow.refreshPlayerStats();
		
		if (playerNum >= playerController.getTotalNumOfPlayers()) {
			setCurrentPlayer(this.playerController.getCurrentPlayer(), this.playerController.getCurrentPlayerNum());
			setOnOptionsPanel(moveRobberInfoPanel); //$NON-NLS-1$
			boardGUI.setState(GameStates.move_robber);
			createTimer(new MoveToStealPhaseStateListener(7));
			return;
		}
		
		Player p = this.playerController.getPlayer(playerNum);
		
		if (p.discardForRobber(new HashMap<TileType, Integer>())) {
			discardForRobber(playerNum+1);
			return;
		}
		
		setCurrentPlayer(p, playerNum);
			
		this.discardPanel = new ArrayList<>();
			
		this.discardPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(p, TileType.brick), new Rectangle(2,6,3,1)));
		this.discardPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(p, TileType.wool), new Rectangle(6,6,3,1)));
		this.discardPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(p, TileType.ore), new Rectangle(10,6,3,1)));
		this.discardPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(p, TileType.wheat), new Rectangle(4,10,3,1)));
		this.discardPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(p, TileType.wood), new Rectangle(8,10,3,1)));
			
		JLabel brickLabel = new JLabel(Messages.getString("OptionsPanel.24")); //$NON-NLS-1$
		this.discardPanel.add(new OptionsPanelComponent(brickLabel, new Rectangle(2,5,4,1)));
			
		JLabel woolLabel = new JLabel(Messages.getString("OptionsPanel.25")); //$NON-NLS-1$
		this.discardPanel.add(new OptionsPanelComponent(woolLabel, new Rectangle(6,5,4,1)));
			
		JLabel oreLabel = new JLabel(Messages.getString("OptionsPanel.26")); //$NON-NLS-1$
		this.discardPanel.add(new OptionsPanelComponent(oreLabel, new Rectangle(10,5,4,1)));
			
		JLabel wheatLabel = new JLabel(Messages.getString("OptionsPanel.27")); //$NON-NLS-1$
		this.discardPanel.add(new OptionsPanelComponent(wheatLabel, new Rectangle(4,9,4,1)));
			
		JLabel woodLabel = new JLabel(Messages.getString("OptionsPanel.28")); //$NON-NLS-1$
		this.discardPanel.add(new OptionsPanelComponent(woodLabel, new Rectangle(8, 9, 4, 1)));

		JButton submitResources = new JButton(new DiscardConfirmListener(playerNum));
		submitResources.setText(Messages.getString("OptionsPanel.29")); //$NON-NLS-1$
		this.discardPanel.add(new OptionsPanelComponent(submitResources, new Rectangle(3, 15, 9, 2)));

		JLabel instuctionLabel = new JLabel(Messages.getString("OptionsPanel.47")); //$NON-NLS-1$
		this.discardPanel.add(new OptionsPanelComponent(instuctionLabel, new Rectangle(2, 3, 18, 1)));

		setOnOptionsPanel(this.discardPanel);
	}
	
	class DiscardConfirmListener extends AbstractAction {
		
		int playerNum;
		
		public DiscardConfirmListener (int playerNum) {
			this.playerNum = playerNum;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.discard_robber)) {
				Player p = playerController.getPlayer(this.playerNum);
				HashMap<TileType, Integer> toDiscard = new HashMap<>();
				toDiscard.put(TileType.brick, ((JComboBox<Integer>) discardPanel.get(0).getSwingComponent()).getSelectedIndex());
				toDiscard.put(TileType.wool, ((JComboBox<Integer>) discardPanel.get(1).getSwingComponent()).getSelectedIndex());
				toDiscard.put(TileType.ore, ((JComboBox<Integer>) discardPanel.get(2).getSwingComponent()).getSelectedIndex());
				toDiscard.put(TileType.wheat, ((JComboBox<Integer>) discardPanel.get(3).getSwingComponent()).getSelectedIndex());
				toDiscard.put(TileType.wood, ((JComboBox<Integer>) discardPanel.get(4).getSwingComponent()).getSelectedIndex());
			
				boolean successfulDiscard = p.discardForRobber(toDiscard);
				if(successfulDiscard) {
					discardForRobber(playerNum+1);
				}
			}
		}
	}
	
	class MoveToStealPhaseStateListener implements ActionListener {
		private int numRolled = -1;
		
		public MoveToStealPhaseStateListener(int rolled) {
			this.numRolled = rolled;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.steal)) {
				ArrayList<Player> playersWithSettlementsOnTile = catanBoard.getPlayersWithSettlementOnRobberTile();
				if(playersWithSettlementsOnTile.size() < 1 || (playersWithSettlementsOnTile.size() == 1 && playersWithSettlementsOnTile.get(0) == playerController.getCurrentPlayer())) {
					if(numRolled != -1) {
						setLastRolled(numRolled);
					}
					gameWindow.refreshPlayerStats();
					boardGUI.setState(GameStates.idle);
					setOnOptionsPanel(actionPanel);
				} else {
					playersPanelForSteal(numRolled, playersWithSettlementsOnTile);
				}
			}	
		}	
	}

	public void setCurrentPlayer(Player p, int num) {
		JLabel label = (JLabel) currentPlayerNameBox.getSwingComponent();
		label.setText(Messages.getString("OptionsPanel.18") + (num + 1)); //$NON-NLS-1$
		label.setOpaque(true);
		label.setBackground(p.getColor());
	}
	
	public void setLastRolled(int lastRolledNum) {
		JLabel label = (JLabel) this.lastRolled.getSwingComponent();
		label.setText(Messages.getString("OptionsPanel.19") + lastRolledNum); //$NON-NLS-1$
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
		JLabel instuctionLabel = new JLabel(Messages.getString("OptionsPanel.20")); //$NON-NLS-1$
		this.tradeWithBankPanel.add(new OptionsPanelComponent(instuctionLabel, new Rectangle(2,2,6,2)));
		this.tradeWithBankPanel.add(new OptionsPanelComponent(selectItemToTradeFor(TileType.wool), new Rectangle(4,6,6,2)));
		this.tradeWithBankPanel.add(new OptionsPanelComponent(selectItemToTradeFor(TileType.wheat), new Rectangle(4,8,6,2)));
		this.tradeWithBankPanel.add(new OptionsPanelComponent(selectItemToTradeFor(TileType.wood), new Rectangle(4,10,6,2)));
		this.tradeWithBankPanel.add(new OptionsPanelComponent(selectItemToTradeFor(TileType.ore), new Rectangle(4,12,6,2)));
		this.tradeWithBankPanel.add(new OptionsPanelComponent(selectItemToTradeFor(TileType.brick), new Rectangle(4,14,6,2)));
		JButton cancelButton = new JButton(new CancelAction());
		cancelButton.setText(Messages.getString("OptionsPanel.5")); //$NON-NLS-1$
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
		JLabel instuctionLabel = new JLabel(Messages.getString("OptionsPanel.21")); //$NON-NLS-1$
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(instuctionLabel, new Rectangle(2,2,6,2)));
		JLabel instuctionLabel2 = new JLabel(Messages.getString("OptionsPanel.22")); //$NON-NLS-1$
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(instuctionLabel2, new Rectangle(2,3,6,2)));
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(selectItemAsPayment(TileType.wool), new Rectangle(4,6,6,2)));
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(selectItemAsPayment(TileType.wheat), new Rectangle(4,8,6,2)));
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(selectItemAsPayment(TileType.wood), new Rectangle(4,10,6,2)));
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(selectItemAsPayment(TileType.ore), new Rectangle(4,12,6,2)));
		this.tradeWithBankPaymentPanel.add(new OptionsPanelComponent(selectItemAsPayment(TileType.brick), new Rectangle(4,14,6,2)));
		JButton cancelButton = new JButton(new CancelAction());
		cancelButton.setText(Messages.getString("OptionsPanel.5")); //$NON-NLS-1$
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
		JLabel instuctionLabel = new JLabel(Messages.getString("OptionsPanel.23")); //$NON-NLS-1$
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
		cancelButton.setText(Messages.getString("OptionsPanel.5")); //$NON-NLS-1$
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
		playerButton.setText(Messages.getString("OptionsPanel.13") + playerNum); //$NON-NLS-1$
		return playerButton;
	}
	
	public void tradeWithPlayerPanel() {
		this.tradeWithPlayerPanel = new ArrayList<>();
	
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getPlayer(selectedPlayer), TileType.brick), new Rectangle(2,6,3,1)));
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getPlayer(selectedPlayer), TileType.wool), new Rectangle(6,6,3,1)));
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getPlayer(selectedPlayer), TileType.ore), new Rectangle(10,6,3,1)));
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getPlayer(selectedPlayer), TileType.wheat), new Rectangle(4,10,3,1)));
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(makeDropDownResourceSelector(this.playerController.getPlayer(selectedPlayer), TileType.wood), new Rectangle(8,10,3,1)));
		
		JLabel brickLabel = new JLabel(Messages.getString("OptionsPanel.24")); //$NON-NLS-1$
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(brickLabel, new Rectangle(2,5,4,1)));
		
		JLabel woolLabel = new JLabel(Messages.getString("OptionsPanel.25")); //$NON-NLS-1$
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(woolLabel, new Rectangle(6,5,4,1)));
		
		JLabel oreLabel = new JLabel(Messages.getString("OptionsPanel.26")); //$NON-NLS-1$
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(oreLabel, new Rectangle(10,5,4,1)));

		JLabel wheatLabel = new JLabel(Messages.getString("OptionsPanel.27")); //$NON-NLS-1$
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(wheatLabel, new Rectangle(4,9,4,1)));

		JLabel woodLabel = new JLabel(Messages.getString("OptionsPanel.28")); //$NON-NLS-1$
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(woodLabel, new Rectangle(8,9,4,1)));
		
		JButton submitResources = new JButton(new TradeWithPlayerForListener());
		submitResources.setText(Messages.getString("OptionsPanel.29")); //$NON-NLS-1$
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(submitResources, new Rectangle(3,15,9,2)));
		
		JButton cancelButton = new JButton(new CancelAction());
		cancelButton.setText(Messages.getString("OptionsPanel.5")); //$NON-NLS-1$
		this.tradeWithPlayerPanel.add(new OptionsPanelComponent(cancelButton, new Rectangle(3,18,9,2)));
		
		JLabel instuctionLabel = new JLabel(Messages.getString("OptionsPanel.30")); //$NON-NLS-1$
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
		
		JLabel brickLabel = new JLabel(Messages.getString("OptionsPanel.24")); //$NON-NLS-1$
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(brickLabel, new Rectangle(2,5,4,1)));
		
		JLabel woolLabel = new JLabel(Messages.getString("OptionsPanel.25")); //$NON-NLS-1$
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(woolLabel, new Rectangle(6,5,4,1)));
		
		JLabel oreLabel = new JLabel(Messages.getString("OptionsPanel.26")); //$NON-NLS-1$
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(oreLabel, new Rectangle(10,5,4,1)));

		JLabel wheatLabel = new JLabel(Messages.getString("OptionsPanel.27")); //$NON-NLS-1$
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(wheatLabel, new Rectangle(4,9,4,1)));

		JLabel woodLabel = new JLabel(Messages.getString("OptionsPanel.28")); //$NON-NLS-1$
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(woodLabel, new Rectangle(8,9,4,1)));
		
		JButton submitResources = new JButton(new TradeWithPlayerListener());
		submitResources.setText(Messages.getString("OptionsPanel.31")); //$NON-NLS-1$
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(submitResources, new Rectangle(3,15,9,2)));
		
		JButton cancelButton = new JButton(new CancelAction());
		cancelButton.setText(Messages.getString("OptionsPanel.5")); //$NON-NLS-1$
		this.tradeWithPlayerPaymentPanel.add(new OptionsPanelComponent(cancelButton, new Rectangle(3,18,9,2)));
		
		JLabel instuctionLabel = new JLabel(Messages.getString("OptionsPanel.32")); //$NON-NLS-1$
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
		
		JLabel playerLabel = new JLabel(Messages.getString("OptionsPanel.13") + (selectedPlayer + 1)); //$NON-NLS-1$
		this.acceptTradeWithPlayerPanel.add(new OptionsPanelComponent(playerLabel, new Rectangle(2,3,15,1)));
		
		JLabel instuctionLabel = new JLabel(Messages.getString("OptionsPanel.33")); //$NON-NLS-1$
		this.acceptTradeWithPlayerPanel.add(new OptionsPanelComponent(instuctionLabel, new Rectangle(2,6,15,1)));
		
		JLabel paymentLabel = new JLabel(Messages.getString("OptionsPanel.34") + payment); //$NON-NLS-1$
		this.acceptTradeWithPlayerPanel.add(new OptionsPanelComponent(paymentLabel, new Rectangle(2,8,15,1)));
		
		JLabel costLabel = new JLabel(Messages.getString("OptionsPanel.35") + cost); //$NON-NLS-1$
		this.acceptTradeWithPlayerPanel.add(new OptionsPanelComponent(costLabel, new Rectangle(2,10,15,1)));
		
		JButton yesButton = new JButton(new TradeWithPlayerApprovedListener(cost, payment));
		yesButton.setText(Messages.getString("OptionsPanel.36")); //$NON-NLS-1$
		this.acceptTradeWithPlayerPanel.add(new OptionsPanelComponent(yesButton, new Rectangle(3,18,9,2)));
		
		JButton noButton = new JButton(new CancelAction());
		noButton.setText(Messages.getString("OptionsPanel.37")); //$NON-NLS-1$
		this.acceptTradeWithPlayerPanel.add(new OptionsPanelComponent(noButton, new Rectangle(3,20,9,2)));
		
		setOnOptionsPanel(acceptTradeWithPlayerPanel);
	}
	
	class TradeWithPlayerApprovedListener extends AbstractAction {
		
		private HashMap<TileType, Integer> cost;
		private HashMap<TileType, Integer> payment;
		
		public TradeWithPlayerApprovedListener(HashMap<TileType, Integer> c, HashMap<TileType, Integer> p) {
			this.cost = c;
			this.payment = p;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(boardGUI.getState().equals(GameStates.trade)) {		
				boolean success = catanBoard.tradeWithPlayer(selectedPlayer, payment, cost);
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
	
	public void playersPanelForSteal(int numRolled, ArrayList<Player> playersWithSettlementsOnTile) {
		this.stealFromPlayerPanel = new ArrayList<>();
		JLabel instuctionLabel = new JLabel(Messages.getString("OptionsPanel.44")); //$NON-NLS-1$
		this.stealFromPlayerPanel.add(new OptionsPanelComponent(instuctionLabel, new Rectangle(2,2,6,2)));
		int spacing = 0;
		
		for(int i = 0; i < this.playerController.getTotalNumOfPlayers(); i++) {
			for(Player p : playersWithSettlementsOnTile) {
				if(this.playerController.getPlayer(i) == p && p != this.playerController.getCurrentPlayer()) {
					this.stealFromPlayerPanel.add(new OptionsPanelComponent(selectPlayerToStealFrom(i+1, numRolled), new Rectangle(4,6+(spacing*2),6,2)));
					spacing++;
				}
			}
		}
		
		setOnOptionsPanel(stealFromPlayerPanel);
	}
	
	public JButton selectPlayerToStealFrom(int playerNum, int numRolled) {
		JButton playerButton = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				if(boardGUI.getState().equals(GameStates.steal)) {
					selectedPlayer = playerNum - 1;
					catanBoard.stealRandomResourceFromOpposingPlayer(playerController.getCurrentPlayer(), playerController.getPlayer(selectedPlayer));
					finishStealing(numRolled);
				}
			}
		});
		playerButton.setText(Messages.getString("OptionsPanel.13") + playerNum); //$NON-NLS-1$
		return playerButton;
	}
	
	protected void finishStealing(int numRolled) {
		if(numRolled != -1) {
			setLastRolled(numRolled);
		}
		gameWindow.refreshPlayerStats();
		boardGUI.setState(GameStates.idle);
		setOnOptionsPanel(actionPanel);
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
