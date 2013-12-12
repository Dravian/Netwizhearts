package Client.View;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class OwnOtherData extends JPanel {

	private String ownData;
	
	/**
	 * Erstellt ein neues OwnScore-Panel
	 */
	public OwnOtherData() {
		ownData = "";
	}
	
	/**
	 * Setzt den Punktestand des Spielers
	 * 
	 * @param n Punktestand
	 */
	public void setData(String s) {
		ownData = s;
		repaint();
	}
	
//	/**
//	 * Gibt den Punktestand des Spielers zurueck
//	 * 
//	 * @return Punktestand
//	 */
//	public int getScore() {
//		return score;
//	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Font font = new Font("Arial",Font.BOLD,15);
		g.setFont(font);
		g.drawString(ownData, 0, 30);
	}

}
