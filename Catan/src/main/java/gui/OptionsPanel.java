package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import lib.GraphPaperLayout;
import objects.CatanBoard;
import objects.Player;

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel{
	private final Font font = new Font("Arial", 1, 16);
	private PlayerComponent currentPlayerNameBox;
	private CatanBoard catanBoard;
	
	public OptionsPanel(CatanBoard catanBoard) {
		this.catanBoard = catanBoard;
		this.setLayout(new GraphPaperLayout(new Dimension(14, 24)));
		this.currentPlayerNameBox = new PlayerComponent(new JLabel(""), new Rectangle(2,0,10,1));
		this.currentPlayerNameBox.getSwingComponent().setFont(font);
		this.currentPlayerNameBox.getSwingComponent().setForeground(Color.WHITE);
		setCurrentPlayer(this.catanBoard.getCurrentPlayer(), this.catanBoard.getCurrentPlayerNum());
		add(this.currentPlayerNameBox.getSwingComponent(), this.currentPlayerNameBox.getRectangle());
	}
		
	public void setCurrentPlayer(Player p, int num) {
		JLabel label = (JLabel) currentPlayerNameBox.getSwingComponent();
		label.setText("Player: " + (num+1));
		label.setOpaque(true);
		label.setBackground(p.getColor());
	}
}
