/**
 * 
 */
package Client.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import Ruleset.Card;
import Ruleset.Colour;

/** 
 * OwnHand. Stellt die Karten dar, die der Spieler auf der Hand hat. 
 * Der Spieler kann eine Karte durch Anklicken auswaehlen 
 * und durch einen zweiten Klick ausspielen.
 */
public class OwnHand extends JPanel{
		
	private List<ViewCard> hand;
	private JPanel contentPane;
	
	public OwnHand(JPanel cP) {
		hand = new LinkedList<ViewCard>();
		contentPane = cP;
	}
	
	/**
	 * Setzt die Hand des Spielers
	 * 
	 * @param cards
	 */
	public void setHand(List<Card> cards, MouseListener m) {
		for (int i = 0; i < cards.size(); i++) {
			Card c = cards.get(i);
			if (i < hand.size() && hand.get(i) != null) {
				this.remove(hand.get(i));
				hand.remove(i);
				ViewCard vc = new ViewCard(c);
				//vc.setBounds(10+i*75, 0, vc.getWidth(), vc.getHeight());
				vc.addMouseListener(m);
				hand.add(i, vc);
				this.add(vc);
			} else {
				ViewCard vc = new ViewCard(c);
				//vc.setBounds(10+i*75, 0, vc.getWidth(), vc.getHeight());
				vc.addMouseListener(m);
				hand.add(i, vc);
				this.add(vc);
			}
			 //TODO 
		}
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
        for (ViewCard c : hand) {
			//c.paintComponent(g);
		}
    }
}