/**
 * 
 */
package Ruleset;

import java.util.List;
import java.util.Set;

import Client.ClientModel;
import ComObjects.MsgMultiCards;

/** 
 * Diese Klasse bildet das Regelwerk f�r den Client bei einer Partie Hearts. 
 */
public class ClientHearts extends ClientRuleset {
	private static final int MIN_PLAYERS = 4;
	private static final int MAX_PLAYERS = 4;
	private int inputNumber;
	
	/**
	 * Erzeugt ein ClientHearts
	 * @param client Das Model auf dem gespielt wird
	 */
	protected ClientHearts(ClientModel client) {
		super(RulesetType.Hearts, MIN_PLAYERS, MAX_PLAYERS,client);
	}
	
	/**
	 * �berpr�ft ob ein gemachter Zug zu dem Spiel Hearts g�ltig ist
	 * @return isValid true falls Zug g�ltig, false wenn nicht
	 */
	public boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;
	}
	
	/**
	 * 
	 * @param cards
	 */
	public void send(Set<Card> cards) {
		send(new MsgMultiCards(cards));
	}

}