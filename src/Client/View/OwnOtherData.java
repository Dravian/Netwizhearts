package Client.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

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
	
	
	public void setMyTurn(boolean b) {
		if (b) {
			this.setBorder(new LineBorder(Color.RED, 3));
		} else {
			this.setBorder(null);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Font font = new Font("Arial",Font.BOLD,15);
		g.setFont(font);
		g.drawString(ownData, 0, 30);
	}

}
