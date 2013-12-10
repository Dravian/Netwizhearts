/**
 * 
 */
package Client.View;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import Ruleset.Card;
import Ruleset.Colour;

/** 
 * DrawDeck. Stellt einen Aufnahmestapel dar.
 */
public class DrawDeck extends JPanel{
	
	private ViewCard shownCard;
	private boolean isTrump;
	private Colour trumpColour;
	
	public DrawDeck() {
		shownCard = new ViewCard(null);
		isTrump = false;
		trumpColour = null;
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
	
	public void setTrumpColour(Colour col) {
		isTrump = true;
		trumpColour = col;
	}
	
	/**
	 * Legt fest was für eine Karte auf dem Deck gezeigt werden soll.
	 * 
	 * @param card die Karte, bei NULL wird die Rueckseite der Karten gezeigt
	 */
	public void setShownCard(Card card) {
		shownCard.setCard(card);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
    }
}