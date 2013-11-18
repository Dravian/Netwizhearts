package Ruleset;

/**
 * Wird geworfen wenn es im Regelwerk zu einem Fehler kommt
 */
@SuppressWarnings("serial")
public class RulesetException extends RuntimeException {

	public RulesetException(){
		super();
	}
	
	public RulesetException(String s){
		super(s);
	}
}
