/**
 * 
 */
package Ruleset;

import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelectionRequest;

/** 
 * Diese Klasse bildet das Regelwerk f�r den Client bei einer Partie Wizard
 */
public class ClientWizard extends ClientRuleset {

	/**
	 * Pr�ft ob ein gemachter Zug zum Spiel Wizard g�ltig ist
	 * @return isValid true falls Zug g�ltig, false wenn nicht
	 */
	public boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;		
	}
	
}