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

public class LanguageSelectPrompt {
	
	public boolean languageSelected;
	private JFrame frame;
	
	public LanguageSelectPrompt() {
		this.frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		createContentPane();
		
		frame.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true); 
        languageSelected = false;
	}

	private void createContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JLabel label = new JLabel(Messages.getString("Main.2"));
		label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton englishButton = new JButton(Messages.getString("Main.0"));
		englishButton.addActionListener(e -> selectLanguageAction("gui.messages_en_US"));
		englishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton spanishButton = new JButton(Messages.getString("Main.1"));
		spanishButton.addActionListener(e -> selectLanguageAction("gui.messages_ES"));
		spanishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel.add(label);
		panel.add(englishButton);
		panel.add(spanishButton);
		this.frame.add(panel);
	}
	
	public void selectLanguageAction(String language) {
		Messages.setBundle(language);
		languageSelected = true;
		this.frame.dispose();
	}
	
	public boolean isLanguageSelected() {
		return languageSelected;
	}
}