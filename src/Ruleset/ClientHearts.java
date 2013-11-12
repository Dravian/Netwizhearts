/**
 * 
 */
package Ruleset;

import java.util.List;
import java.util.Set;

import Client.ClientModel;
import ComObjects.MsgMultiCards;
import ComObjects.MsgMultiCardsRequest;

/** 
 * Diese Klasse bildet das Regelwerk für den Clientmodel bei einer Partie Hearts
 */
public class ClientHearts extends ClientRuleset {
	/**
	 * Die Mindestanzahl an Spielern die Hearts spielen können
	 */
	private static final int MIN_PLAYERS = 4;
	
	/**
	 * Die Maximale Anzahl an Spielern die Hearts spielen können 
	 */
	private static final int MAX_PLAYERS = 4;
	
	/**
	 * Erzeugt ein ClientHearts
	 * @param client Das Model auf dem gespielt wird
	 */
	public ClientHearts(ClientModel client) {
		super(RulesetType.Hearts, MIN_PLAYERS, MAX_PLAYERS,client);
	}
	
	/**
	 * Überprüft ob ein gemachter Zug zu dem Spiel Hearts gültig ist
	 * @return isValid true falls Zug gültig, false wenn nicht
	 */
	public boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;
	}
	
	/**
	 * Generiert eine MsgMultiCards aus den Karten und ruft bei sich 
	 * die send Methode auf
	 * @param cards Das Set an Karten
	 */
	private void generateMsgMultiCards(Set<Card> cards) {
		send(new MsgMultiCards(cards));
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt mehrere Karten anzugeben
	 * @param msgMultiCardsRequest Die Nachricht vom Server
	 */
	public void resolveMessage(MsgMultiCardsRequest msgMultiCardsRequest) {
		
		
	}
	
	/**
	 * Gibt zurück ob die Karten die der Client tauschen will, gültig sind
	 * @param cards Die zu tauschenden Karten
	 * @return true wenn Karten valide sind, false wenn nicht
	 */
	public boolean areValidChoosenCards(Set<Card> cards) {
		return false;
	}

}