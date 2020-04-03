package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SetupPrompt {
	
	private int numPlayers = 0;
	private String[] numberStrings = { "3", "4"};
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
		
		JLabel label = new JLabel("How many players for this game?");
		label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JComboBox<String> dropDown = new JComboBox<String>(numberStrings);
		dropDown.setBorder(BorderFactory.createEmptyBorder(5, 0, 30, 0));
		dropDown.setMaximumSize(dropDown.getPreferredSize());
		dropDown.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton button = new JButton("Start the Game!");
		button.addActionListener(e -> {
			setNumPlayers(dropDown.getSelectedItem().toString());
			this.frame.dispose();
		});
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel.add(label);
		panel.add(dropDown);
		panel.add(button);
		this.frame.add(panel);
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
