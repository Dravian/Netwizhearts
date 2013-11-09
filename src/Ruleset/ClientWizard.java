/**
 * 
 */
package Ruleset;

import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelectionRequest;

/** 
 * Diese Klasse bildet das Regelwerk für den Client bei einer Partie Wizard
 */
public class ClientWizard extends ClientRuleset {
	private static final int MIN_PLAYERS = 3;
	private static final int MAX_PLAYERS = 6;
	
	protected ClientWizard() {
		super(RulesetType.Wizard, MIN_PLAYERS, MAX_PLAYERS);
	}

	/**
	 * Prüft ob ein gemachter Zug zum Spiel Wizard gültig ist
	 * @return isValid true falls Zug gültig, false wenn nicht
	 */
	public boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;		
	}
	
}