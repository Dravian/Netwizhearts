/**
 * 
 */
package Client.View;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import Ruleset.Card;
import Ruleset.HeartsCard;

/** 
 * DiscardPile. Stellt einen Ablagestapel dar, dieser kann sowohl für jeden Spieler einzeln 
 * oder für alle Spieler gemeinsam in der Mitte des Spielfeldes angezeigt werden.
 */
public class DiscardPile extends JPanel{
	
	private List<Card> playedCards;
	private ViewCard topCard;
	
	
	public DiscardPile() {
		playedCards = new LinkedList<Card>();
		topCard = new ViewCard(null);
		this.add(topCard);
		this.setLayout(null);
	}
	
	@Override
	public int getWidth() {
		return topCard.getWidth();
	}
	@Override
	public int getHeight() {
		return topCard.getHeight();
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