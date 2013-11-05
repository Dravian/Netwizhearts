package Ruleset;

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

}
