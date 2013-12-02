/**
 * 
 */
package Client.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
	
	public OwnHand() {
	}
	
	/**
	 * Setzt die Hand des Spielers
	 * 
	 * @param cards
	 */
	public void setHand(List<Card> cards) {
		hand.clear();
		for (int i = 0; i < cards.size(); i++) {
			Card c = cards.get(i);
			//hand.add(i, new ViewCard(c.getRuleset(), c.getID())); //TODO getID
		}
	}
	
	@Override
	public void paint(Graphics g) {
		for (ViewCard c : hand) {
			c.paint(g);
		}
        super.paint(g);
    }
}