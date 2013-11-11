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
 * Diese Klasse bildet das Regelwerk für den Client bei einer Partie Wizard
 */
public class ClientWizard extends ClientRuleset {
	/**
	 * Die Mindestanzahl an Spielers die Wizard spielen können
	 */
	private static final int MIN_PLAYERS = 3;
	
	/**
	 * Die Maximale Anzahl an Spielern die Wizard spielen können
	 */
	private static final int MAX_PLAYERS = 6;
	
	/**
	 * Erzeugt ein ClientWizard
	 * @param client Das Model auf dem gespielt wird
	 */
	protected ClientWizard(ClientModel client) {
		super(RulesetType.Wizard, MIN_PLAYERS, MAX_PLAYERS, client);
	}

	/**
	 * Prüft ob ein gemachter Zug zum Spiel Wizard gültig ist
	 * @return isValid true falls Zug gültig, false wenn nicht
	 */
	public boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;		
	}
	
	/**
	 * Sendet die Anzahl der angesagten Stiche
	 * @param number Die Anzahl der angesagten Stiche
	 */
	public void send(int number) {
		send(new MsgNumber(number));
	}
	
	/**
	 * Sendet eine ausgewählte Trumpffarbe
	 * @param colour Die Trumpffarbe
	 */
	public void send(Colour colour) {
		send(new MsgSelection(colour));
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Stichanzahl anzugeben
	 * @param msgNumber Die Nachricht vom Server
	 */
	public void resolveMessage(MsgNumberRequest msgNumber) {
		
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Farbe auszuwählen
	 * @param msgSelection Die Nachricht vom Server
	 */
	public void resolveMessage(MsgSelectionRequest msgSelection) {
		
	}
}