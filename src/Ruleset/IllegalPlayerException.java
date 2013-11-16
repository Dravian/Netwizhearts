package Ruleset;

/**
 * Wird geworfen wenn ein falsche Spieler eine Aktion versucht.
 */
public class IllegalPlayerException extends RulesetException {
	String name;
	
	public IllegalPlayerException(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
