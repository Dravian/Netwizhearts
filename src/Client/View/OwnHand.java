/**
 * 
 */
package Client.View;

import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import Ruleset.Card;

/** 
 * OwnHand. Stellt die Karten dar, die der Spieler auf der Hand hat. 
 * Der Spieler kann eine Karte durch Anklicken auswaehlen 
 * und durch einen zweiten Klick ausspielen.
 */
public class OwnHand extends JPanel{
		
	private List<ViewCard> hand;
	
	public OwnHand() {
		hand = new LinkedList<ViewCard>();
		for (int i = 0; i < 10; i++) {
			hand.add(new ViewCard(null));
			ViewCard vc = hand.get(i);
			vc.setBounds(2+i*75, 0, vc.getWidth(), vc.getHeight());
			this.add(vc);
			this.setLayout(null);
		}
	}
	
	/**
	 * Setzt die Hand des Spielers
	 * 
	 * @param cards
	 */
	public void setHand(List<Card> cards) {
		for (int i = 0; i < hand.size(); i++) {
			if (i < cards.size()) {
			hand.get(i).setCard(cards.get(i));
			} else {
				hand.get(i).setCard(null);
			}
			 //TODO 
		}
		repaint();
	}
	
	/**
	 * Fuegt jeder Handkarte einen MouseListener hinzu
	 * 
	 * @param m ein MouseListener
	 */
	public void addCardMouseListener(MouseListener m) {
		for (ViewCard c : hand) {
			c.addMouseListener(m);
		}
	}
	
	/**
	 * Setzt alle Handkarten auf 'nicht angeklickt'.
	 * Ruft dazu setClicked(false) in allen ViewCards der Hand auf.
	 */
	public void unclickAll() {
		for (ViewCard c : hand) {
			c.setClicked(false);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}