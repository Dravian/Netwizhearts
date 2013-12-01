/**
 * 
 */
package Ruleset;

import java.util.List;
import java.util.Set;

import Client.ClientModel;
import ComObjects.*;

/** 
 * ClientHearts. Diese Klasse bildet das Regelwerk f�r den Clientmodel bei einer Partie Hearts
 */
public class ClientHearts extends ClientRuleset {
	/**
	 * Die Mindestanzahl an Spielern die Hearts spielen koennen
	 */
	private static final int MIN_PLAYERS = 4;
	
	/**
	 * Die Maximale Anzahl an Spielern die Hearts spielen koennen 
	 */
	private static final int MAX_PLAYERS = 4;
	
	/**
	 * Der RulesetTyp des Spiels
	 */
	private static final RulesetType RULESET = RulesetType.Hearts;
	
	/**
	 * Erzeugt ein ClientHearts
	 * @param client Das Model auf dem gespielt wird
	 */
	public ClientHearts(ClientModel client) {
		super(RulesetType.Hearts, MIN_PLAYERS, MAX_PLAYERS,client);
	}
	

	@Override
	public boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt mehrere Karten anzugeben
	 * @param msgMultiCardsRequest Die Nachricht vom Server
	 */
	public void resolveMessage(MsgMultiCardsRequest msgMultiCardsRequest) {
		setGamePhase(GamePhase.MultipleCardRequest);
	}
	
	/**
	 * Gibt zuueck ob die Karten die der Client tauschen will, gueltig sind
	 * @param cards Die zu tauschenden Karten
	 * @return true wenn Karten valide sind, false wenn nicht
	 */
	public boolean areValidChoosenCards(Set<Card> cards) {
		return false;
	}

}