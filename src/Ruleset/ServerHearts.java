package Ruleset;

import java.util.Set;

import Server.GameServer;
import ComObjects.MsgMultiCards;
import ComObjects.MsgMultiCardsRequest;

/**
 * Diese Klasse erstellt das Regelwerk zum Spiel Hearts. Sie enth�lt zudem weitere Methoden, 
 * welche f�r das Spiel Hearts spezifisch ben�tigt werden, wie die Regelung zum Tausch von Karten 
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
	 * Verarbeitet die RulesetMessage dass mehrerer Karten vom Spieler �bergeben werden.
	 * Die wird dann in isValidMove �berpr�ft, bei falsche Eingabe wird�
	 * generateMsgMultiCardRequest f�r den selben Spieler aufgerufen. 
	 * Bei richtiger Eingabe geht das Spiel weiter.
	 * @param msgMultiCard Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgMultiCards msgMultiCard, String name) {
		
	}
	
	/**
	 * �berpr�ft ob eine �bergebenes Kartenset von einem Spieler g�ltig ist
	 * @param cards Ein Kartenset
	 * @return true falls das Kartenset g�ltig ist, false wenn nicht
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
