package Client.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * GUI Element, dass zusätzliche Informationen über den Spieler anzeigen kann, wie
 * z.B. einen Punktestand. Wird außerdem farblich umrandet, wenn der Spieler an der
 * Reihe ist.
 *
 */
public class OwnOtherData extends JPanel {

	private String ownData;
	
	/**
	 * Erstellt ein neues OwnScore-Panel
	 */
	public OwnOtherData() {
		ownData = "";
	}
	
	/**
	 * Setzt die zusätzlichen Informationen über den Spieler
	 * 
	 * @param s zusätzliche Informationen
	 */
	public void setData(String s) {
		ownData = s;
		repaint();
	}	
	
	/**
	 * Legt fest, ob der Spieler gerade an der Reihe ist und
	 * passt die Umrandung der Komponente dementsprechend an.
	 * 
	 * @param b true, die Komponente wird umrandet, false, der Rand fällt weg
	 */
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
		g.drawString(ownData, 2, 30);
	}

}
