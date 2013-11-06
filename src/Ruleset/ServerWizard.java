package Ruleset;

import ComObjects.MsgMultipleCardsRequest;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelectionRequest;

/**
 * Diese Klasse erstellt das Regelwerk zum Spiel Wizard. Sie enthält zudem weitere Methoden,
 * welche für das Spiel Wizard spezifisch benötigt werden, wie das Bestimmen einer Trumpffarbe 
 * und die Bestimmung der Rundenanzahl.
 */
public class ServerWizard extends ServerRuleset {

	/**
	 * Prüft ob ein gemachter Zug in einem Wizard Spiel gültig ist
	 * @return isValid true falls Zug gültig, false wenn nicht
	 */
	public boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;
	}

	/** 
	 * Berechnet das Ergebnis von der Berechnung eines Befehls in Wizard
	 */
	public void calculateOutcome() {
		// TODO Automatisch erstellter Methoden-Stub

	}
	
	/**
	 * Schickt einem Spieler die Aufforderung eine Stichanzahl anzugeben
	 * @param name Name des Spielers
	 * @return new MsgNumberRequest() Eine Nachricht an den Spieler
	 */
	public MsgNumberRequest sendNumberRequest(String name) {
		return new MsgNumberRequest();
	}
	
	/**
	 * Schickt einem Spieler die Aufforderung eine Farbe zu wählen
	 * @param name Name des Spielers
	 * @return new MsgSelectionRequest() Eine Nachricht an den Spieler
	 */
	public MsgSelectionRequest sendSelectionRequest(String name) {
		return new MsgSelectionRequest();
	}

}
