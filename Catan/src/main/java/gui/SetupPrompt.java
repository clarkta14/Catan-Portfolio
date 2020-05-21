package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SetupPrompt {
	
	private int numPlayers = 0;
	private JFrame frame;
	
	public SetupPrompt() {
		this.frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		createContentPane();
		
		frame.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true); 
	}

	private void createContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JLabel label = new JLabel(Messages.getString("SetupPrompt.0"));
		label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton threePlayersButton = new JButton(Messages.getString("SetupPrompt.1"));
		threePlayersButton.addActionListener(e -> selectPlayersAction("3"));
		threePlayersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton fourPlayersButton = new JButton(Messages.getString("SetupPrompt.2"));
		fourPlayersButton.addActionListener(e -> selectPlayersAction("4"));
		fourPlayersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel.add(label);
		panel.add(threePlayersButton);
		panel.add(fourPlayersButton);
		this.frame.add(panel);
	}
	
	public void selectPlayersAction(String numPlayersString) {
		setNumPlayers(numPlayersString);
		this.frame.dispose();
	}

	public void setNumPlayers(String numPlayersString) {
		if(numPlayersString.equals("3")) {
			this.numPlayers = 3;
		} else if(numPlayersString.equals("4")) {
			this.numPlayers = 4;
		} else {
			throw new IllegalArgumentException("numPlayers must either be intergers 3 or 4");
		}
	}
	
	public int getNumPlayers() {
		if(this.numPlayers == 0) {
			throw new IllegalArgumentException("numPlayers is not yet initialized");
		} else {
			return this.numPlayers;
		}
	}
}
