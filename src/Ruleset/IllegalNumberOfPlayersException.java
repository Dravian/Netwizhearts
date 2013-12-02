package Ruleset;

/**
 * Wird geworfen wenn die Anzahl der Spieler in einem Spiel nicht regelkonform sind beim Starten
 */
@SuppressWarnings("serial")
public class IllegalNumberOfPlayersException extends Exception {
	
	public IllegalNumberOfPlayersException(String string) {
		super(string);
	}
	
}
