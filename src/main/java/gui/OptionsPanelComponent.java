package gui;

import java.awt.Rectangle;

import javax.swing.JComponent;

public class OptionsPanelComponent {
	private JComponent swingComponent;
	private Rectangle box;
	
	public OptionsPanelComponent(JComponent jc, Rectangle r) {
		this.swingComponent = jc;
		this.box = r;
	}
	
	public JComponent getSwingComponent() {
		return this.swingComponent;
	}
	
	public void setSwingComponent(JComponent jc) {
		this.swingComponent = jc;
	}
	
	public Rectangle getRectangle() {
		return this.box;
	}
}
