package gui;

import java.awt.Rectangle;

import javax.swing.JComponent;

public class PlayerComponent {
	private JComponent swingComponent;
	private Rectangle box;
	
	public PlayerComponent(JComponent jc, Rectangle r) {
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
