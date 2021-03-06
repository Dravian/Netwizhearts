/**
 * 
 */
package Client.View;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

import Ruleset.Card;

/** 
 * DiscardPile. Stellt einen Ablagestapel dar, dieser kann sowohl f�r jeden Spieler einzeln 
 * oder f�r alle Spieler gemeinsam in der Mitte des Spielfeldes angezeigt werden.
 */
public class DiscardPile extends JPanel{
	
	private List<Card> playedCards;
	private ViewCard topCard;
	
	
	/**
	 * Erstellt einene neuen DiscardPile
	 */
	public DiscardPile() {
		playedCards = new LinkedList<Card>();
		topCard = new ViewCard(null);
		this.add(topCard);
		this.setLayout(null);
	}
	
	@Override
	public int getWidth() {
		return ViewCard.WIDTH;
	}
	@Override
	public int getHeight() {
		return ViewCard.HEIGHT;
	}
	
	/**
	 * Legt eine Karte auf den Ablagestapel
	 * 
	 * @param card die Karte
	 */
	public void addCard(Card card) {
		playedCards.add(topCard.getCard());
		topCard.setCard(card);
		repaint();
	}
	
	/**
	 * Leert den Ablagestapel
	 */
	public void clearPile() {
		playedCards = new LinkedList<Card>();
		topCard.setCard(null);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
    }
}