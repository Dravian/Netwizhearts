/**
 * 
 */
package Client.View;

import java.awt.Graphics;

import javax.swing.JPanel;

import Ruleset.Card;

/** 
 * DrawDeck. Stellt einen Aufnahmestapel dar.
 */
public class DrawDeck extends JPanel{
	
	private ViewCard shownCard;
	
	public DrawDeck() {
		shownCard = new ViewCard(null);
		this.add(shownCard);
		this.setLayout(null);
	}
	
	@Override
	public int getWidth() {
		return shownCard.getWidth();
	}
	@Override
	public int getHeight() {
		return shownCard.getHeight();
	}
	
	/**
	 * Legt fest was für eine Karte auf dem Deck gezeigt werden soll.
	 * 
	 * @param card die Karte, bei NULL wird die Rueckseite der Karten gezeigt
	 */
	public void setShownCard(Card card) {
		shownCard.setCard(card);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
    }
}