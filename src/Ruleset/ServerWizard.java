package Ruleset;

/**
 * Diese Klasse erstellt das Regelwerk zum Spiel Wizard. Sie enth�lt zudem weitere Methoden,
 * welche f�r das Spiel Wizard spezifisch ben�tigt werden, wie das Bestimmen einer Trumpffarbe 
 * und die Bestimmung der Rundenanzahl.
 */
public class ServerWizard extends ServerRuleset {

	/**
	 * Pr�ft ob ein gemachter Zug in einem Wizard Spiel g�ltig ist
	 * @return isValid true falls Zug g�ltig, false wenn nicht
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
