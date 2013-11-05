package Ruleset;

import ComObjects.MsgMultipleCardsRequest;

/**
 * Diese Klasse erstellt das Regelwerk zum Spiel Hearts. Sie enth�lt zudem weitere Methoden, 
 * welche f�r das Spiel Hearts spezifisch ben�tigt werden, wie die Regelung zum Tausch von Karten 
 * und die Berechnung der Stichpunkten.
 */
public class ServerHearts extends ServerRuleset {

	/**
	 * Pr�ft ob ein gemachter Zug in einem Hearts Spiel g�ltig ist
	 * @return isValid true falls Zug g�ltig, false wenn nicht
	 */
	public boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;
	}

	/** 
	 * Berechnet das Ergebnis von der Berechnung eines Befehls in Hearts
	 */
	public void calculateOutcome() {
		// TODO Automatisch erstellter Methoden-Stub

	}
	
	/**
	 * Schickt einem Spieler die Aufforderung Karten zu geben
	 * @param name Name des Spielers
	 * @return new MsgMultipleCardsRequest() Eine Nachricht an den Spieler
	 */
	public MsgMultipleCardsRequest sendMultiCardRequest(String name) {
		return new MsgMultipleCardsRequest();
	}

}
