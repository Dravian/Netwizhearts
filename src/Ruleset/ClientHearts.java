/**
 * 
 */
package Ruleset;

/** 
 * Diese Klasse bildet das Regelwerk für den Client bei einer Partie Hearts. 
 */
public class ClientHearts extends ClientRuleset {
	private static final int MIN_PLAYERS = 4;
	private static final int MAX_PLAYERS = 4;
	
	protected ClientHearts() {
		super(RulesetType.Hearts, MIN_PLAYERS, MAX_PLAYERS);
	}
	
	/**
	 * Überprüft ob ein gemachter Zug zu dem Spiel Hearts gültig ist
	 * @return isValid true falls Zug gültig, false wenn nicht
	 */
	public boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;
	}

}