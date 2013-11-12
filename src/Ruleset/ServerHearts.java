package Ruleset;

import java.util.Set;

import Server.GameServer;
import ComObjects.MsgMultiCards;
import ComObjects.MsgMultiCardsRequest;

/**
 * Diese Klasse erstellt das Regelwerk zum Spiel Hearts. Sie enthält zudem weitere Methoden, 
 * welche für das Spiel Hearts spezifisch benötigt werden, wie die Regelung zum Tausch von Karten 
 * und die Berechnung der Stichpunkten.
 */
public class ServerHearts extends ServerRuleset {
	private final static int MIN_PLAYERS = 4;
	private final static int MAX_PLAYERS = 4;
	
	/**
	 * Erstellt das Regelwerk zum Spiel Hearts
	 */
	public ServerHearts(GameServer s) {
		super(RulesetType.Hearts, MIN_PLAYERS, MAX_PLAYERS, s);
	}
	
	@Override
	protected boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;
	}
		
	/**
	 * Verarbeitet die RulesetMessage dass mehrerer Karten vom Spieler übergeben werden.
	 * Die wird dann in isValidMove überprüft, bei falsche Eingabe wird´
	 * generateMsgMultiCardRequest für den selben Spieler aufgerufen. 
	 * Bei richtiger Eingabe geht das Spiel weiter.
	 * @param msgMultiCard Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgMultiCards msgMultiCard, String name) {
		
	}
	
	/**
	 * Überprüft ob eine übergebenes Kartenset von einem Spieler gültig ist
	 * @param cards Ein Kartenset
	 * @return true falls das Kartenset gültig ist, false wenn nicht
	 */
	private boolean areValidChoosenCards(Set<Card> cards, String name) {
		return false;
	}
	
	/**
	 * Generiert eine MsgMultiCardRequest und ruft bei sich die send Methode auf
	 * @param name Der Name vom Spieler
	 */
	private void generateMsgMultiCardRequest(String name) {
		
	}

	@Override
	protected void calculateRoundOutcome() {
		
	}

	@Override
	protected void calculateTricks() {
		
	}

	@Override
	protected String getWinner() {
		return null;
	}

}
