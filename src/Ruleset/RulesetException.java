package Ruleset;

/**
 * Wird geworfen wenn im Ruleset selbst ein Fehler passiert
 */
public class RulesetException extends RuntimeException {

	public RulesetException(String string) {
		super(string);
	}

}
