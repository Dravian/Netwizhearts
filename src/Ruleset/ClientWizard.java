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
 * ClientWizard. Diese Klasse bildet das Regelwerk fuer den Client bei einer Partie Wizard
 */
public class ClientWizard extends ClientRuleset {
	/**
	 * Die Mindestanzahl an Spielern die Wizard spielen koennen
	 */
	private static final int MIN_PLAYERS = 3;
	
	/**
	 * Die Maximale Anzahl an Spielern die Wizard spielen koennen
	 */
	private static final int MAX_PLAYERS = 6; 
	
	/**
	 * Der RulesetTyp des Spiels
	 */
	private static final RulesetType RULESET = RulesetType.Wizard;
	
	/**
	 * Erzeugt ein ClientWizard
	 * @param client Das Model auf dem gespielt wird
	 */
	public ClientWizard(ClientModel client) {
		super(RULESET, MIN_PLAYERS, MAX_PLAYERS, client);
	}

	@Override
	public boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;		
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Stichanzahl anzugeben
	 * @param msgNumber Die Nachricht vom Server
	 */
	public void resolveMessage(MsgNumberRequest msgNumber) {
		
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Farbe auszuwaehlen
	 * @param msgSelection Die Nachricht vom Server
	 */
	public void resolveMessage(MsgSelectionRequest msgSelection) {
		
	}
	
	/**
	 * Prüft ob die Anzahl der angesagten Stiche vom Spieler gueltig sind
	 * @param number Die Anzahl der angesagten Sticht
	 * @return true falls die Anzahl der Stiche passen, false wenn nicht
	 */
	public boolean isValidTrickNumber(int number) {
		return false;
	}
	
	/**
	 * Prüft ob die angesagte Trumpffarbe richtig
	 * @param colour Die angesagte Trumpffarbe
	 * @return true falls die Farbe in Ordnung ist, false wenn nicht
	 */
	public boolean isValidColour(Colour colour) {
		return false;
	}
}