package Client.View;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import Ruleset.Colour;

public class TrumpColour extends JPanel {
		private Colour trumpColour;
		
		public TrumpColour() {
			trumpColour = null;
			this.setLayout(null);
		}
		
//		@Override
//		public int getWidth() {
//			return shownCard.getWidth();
//		}
//		@Override
//		public int getHeight() {
//			return shownCard.getHeight();
//		}
		
		public void setTrumpColour(Colour col) {
			trumpColour = col;
			repaint();
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
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
					break;
				}
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    }
}
