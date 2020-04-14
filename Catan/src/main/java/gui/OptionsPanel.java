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
import objects.Player;
import objects.PlayersController;

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel {
	private final Font font = new Font("Arial", 1, 16);
	private OptionsPanelComponent currentPlayerNameBox;
	private GameWindow gameWindow;
	private PlayersController turnController;
	private BoardWindow boardGUI;
	private ArrayList<OptionsPanelComponent> setupPanel;
	private ArrayList<OptionsPanelComponent> infoPanel;
	private Timer timer;

	public OptionsPanel(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
		this.turnController = this.gameWindow.getPlayersController();
		this.boardGUI = this.gameWindow.getBoardWindow();
		this.setupPanel = new ArrayList<>();
		this.infoPanel = new ArrayList<>();
		this.setLayout(new GraphPaperLayout(new Dimension(14, 24)));
		this.currentPlayerNameBox = new OptionsPanelComponent(new JLabel(""), new Rectangle(1, 1, 12, 1));
		this.currentPlayerNameBox.getSwingComponent().setFont(font);
		this.currentPlayerNameBox.getSwingComponent().setForeground(Color.CYAN);
		setCurrentPlayer(this.turnController.getCurrentPlayer(), this.turnController.getCurrentPlayerNum());
		add(this.currentPlayerNameBox.getSwingComponent(), this.currentPlayerNameBox.getRectangle());
		setupPhase();
	}

	public void setupPhase() {
		if(boardGUI.getState().equals(GUIStates.setup)) {
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
			if(boardGUI.getState().equals(GUIStates.setup)) {
				boardGUI.setState(GUIStates.drop_settlement_setup);
				placeInfoPanel("Place a settlement");
				timer = new Timer(50, new PlaceRoadSetupListener());
				timer.start();
			}
		}
	}
	
	class PlaceRoadSetupListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!boardGUI.getState().equals(GUIStates.drop_settlement_setup)) {
				timer.stop();
				boardGUI.setState(GUIStates.drop_road);
				placeInfoPanel("Place a road");
				timer = new Timer(50, new ResetStateListener());
				timer.start();
			}
		}
	}
	
	class ResetStateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!boardGUI.getState().equals(GUIStates.drop_road)) {
				timer.stop();
				turnController.nextPlayer();
				setCurrentPlayer(turnController.getCurrentPlayer(), turnController.getCurrentPlayerNum());
				if(turnController.isInitialSetup()) {
					boardGUI.setState(GUIStates.setup);
					setupPanel();
				} else {
					boardGUI.setState(GUIStates.idle);
					setNonSetupOptions();
				}
			}	
		}	
	}
	
	private void setNonSetupOptions() {
		ArrayList<OptionsPanelComponent> content = new ArrayList<OptionsPanelComponent>();
		JLabel label = new JLabel("Player 1's turn: ");
		label.setFont(font);
		content.add(new OptionsPanelComponent(label, new Rectangle(2,3,10,2)));
		
		JButton placeSettlementButton = new JButton();
		placeSettlementButton.setText("Place Settlement");
		content.add(new OptionsPanelComponent(placeSettlementButton, new Rectangle(4,6,6,2)));
		
		JButton placeRoadButton = new JButton();
		placeRoadButton.setText("Place Road");
		content.add(new OptionsPanelComponent(placeRoadButton, new Rectangle(4,10,6,2)));
		
		JButton endTurnButton = new JButton();
		endTurnButton.setText("End Turn");
		content.add(new OptionsPanelComponent(endTurnButton, new Rectangle(4,14,6,2)));
		
		setOnOptionsPanel(content);
	}

	public void setCurrentPlayer(Player p, int num) {
		JLabel label = (JLabel) currentPlayerNameBox.getSwingComponent();
		label.setText("    Player " + (num + 1));
		label.setOpaque(true);
		label.setBackground(p.getColor());
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
