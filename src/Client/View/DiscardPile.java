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
	
	private List<ViewCard> playedCards;
	
	
	public DiscardPile() {
		playedCards = new LinkedList<ViewCard>();
		playedCards.add(new ViewCard(HeartsCard.HerzAss));//FIXME
	}
	
	@Override
	public int getWidth() {
		return playedCards.get(0).getWidth();
	}
	@Override
	public int getHeight() {
		return playedCards.get(0).getHeight();
	}
	
	/**
	 * Setzt die Hand des Spielers
	 * 
	 * @param cards
	 */
	public void setCards(List<Card> cards) {
		playedCards.clear();
		for (int i = 0; i < cards.size(); i++) {
			Card c = cards.get(i);
			ViewCard vc = new ViewCard(c);
			//vc.setBounds(0, 0, vc.getWidth(), vc.getHeight());
			this.add(vc);
			playedCards.add(i, vc); //TODO 
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (ViewCard c : playedCards) {
			//c.paintComponent(g);//TODO
		}
    }
}