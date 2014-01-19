package Client.View;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import Ruleset.Colour;

public class TrumpColour extends JPanel {
		private Colour trumpColour;
		
		/**
		 * Erstellt ein neues TrumpColour-Panel
		 */
		public TrumpColour() {
			trumpColour = null;
			this.setLayout(null);
		}
		
		/**
		 * Setzt die Farbe des Panels
		 * 
		 * @param col Trumpffarbe
		 */
		public void setTrumpColour(Colour col) {
			trumpColour = col;
			repaint();
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (trumpColour != null) {
				switch (trumpColour) {
				case RED:
					g.setColor(Color.RED);
					break;
				case BLUE:
					g.setColor(Color.BLUE);
					break;
				case GREEN:
					g.setColor(Color.GREEN);
					break;
				case YELLOW:
					g.setColor(Color.YELLOW);
					break;
				default:
					g.setColor(Color.WHITE);
					break;
				}
			} else {
				g.setColor(Color.WHITE);
			}
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    }
}
