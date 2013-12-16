package Ruleset;

/**
 * Wird geworfen wenn im Ruleset selbst ein Fehler passiert
 */
public class RulesetException extends RuntimeException {

	/**
	 * Erstellt eine RulesetException
	 * @param string Die Fehlermeldung
	 */
	public RulesetException(String string) {
		super(string);
	}

}
