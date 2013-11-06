package Ruleset;

import ComObjects.MsgMultipleCardsRequest;

/**
 * Diese Klasse erstellt das Regelwerk zum Spiel Hearts. Sie enthält zudem weitere Methoden, 
 * welche für das Spiel Hearts spezifisch benötigt werden, wie die Regelung zum Tausch von Karten 
 * und die Berechnung der Stichpunkten.
 */
public class ServerHearts extends ServerRuleset {

	/**
	 * Prüft ob ein gemachter Zug in einem Hearts Spiel gültig ist
	 * @return isValid true falls Zug gültig, false wenn nicht
	 */
	protected boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;
	}

	/** 
	 * Berechnet das Ergebnis von der Berechnung eines Befehls in Hearts
	 */
	protected void calculateOutcome() {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

	/**
	 * Erstellt die Karten zum Spiel Hearts
	 */
	protected void createCardDeck() {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

}
