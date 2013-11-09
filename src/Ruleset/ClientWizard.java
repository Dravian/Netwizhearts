/**
 * 
 */
package Ruleset;

import Client.ClientModel;
import ComObjects.MsgNumber;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelection;
import ComObjects.MsgSelectionRequest;

/** 
 * Diese Klasse bildet das Regelwerk f�r den Client bei einer Partie Wizard
 */
public class ClientWizard extends ClientRuleset {
	private static final int MIN_PLAYERS = 3;
	private static final int MAX_PLAYERS = 6;
	
	/**
	 * Erzeugt ein ClientWizard
	 * @param client Das Model auf dem gespielt wird
	 */
	protected ClientWizard(ClientModel client) {
		super(RulesetType.Wizard, MIN_PLAYERS, MAX_PLAYERS, client);
	}

	/**
	 * Pr�ft ob ein gemachter Zug zum Spiel Wizard g�ltig ist
	 * @return isValid true falls Zug g�ltig, false wenn nicht
	 */
	public boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;		
	}
	
	public void send(int i) {
		send(new MsgNumber(i));
	}
	
	public void send(Colour colour) {
		send(new MsgSelection(colour));
	}
}