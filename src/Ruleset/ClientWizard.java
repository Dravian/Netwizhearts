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
	/**
	 * Die Mindestanzahl an Spielern die Wizard spielen k�nnen
	 */
	private static final int MIN_PLAYERS = 3;
	
	/**
	 * Die Maximale Anzahl an Spielern die Wizard spielen k�nnen
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
	 * Pr�ft ob ein gemachter Zug zum Spiel Wizard g�ltig ist
	 * @param card Eine gespielte Karte
	 * @return isValid true falls Zug g�ltig, false wenn nicht
	 */
	public boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;		
	}
	
	/**
	 * 
	 * @param number Die Anzahl der angesagten Stiche
	 */
	private void generateMsgNumber(int number) {
		send(new MsgNumber(number));
	}
	
	/**
	 * Generiert eine MsgSelection aus einer Farbe und ruft bei sich 
	 * die send Methode auf
	 * @param colour Die Trumpffarbe
	 */
	private void generateMsgSelection(Colour colour) {
		send(new MsgSelection(colour));
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Stichanzahl anzugeben
	 * @param msgNumber Die Nachricht vom Server
	 */
	public void resolveMessage(MsgNumberRequest msgNumber) {
		
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Farbe auszuw�hlen
	 * @param msgSelection Die Nachricht vom Server
	 */
	public void resolveMessage(MsgSelectionRequest msgSelection) {
		
	}
	
	/**
	 * Pr�ft ob die Anzahl der angesagten Stiche vom Spieler g�ltig sind
	 * @param number Die Anzahl der angesagten Sticht
	 * @return true falls die Anzahl der Stiche passen, false wenn nicht
	 */
	public boolean isValidTrickNumber(int number) {
		return false;
	}
	
	/**
	 * Pr�ft ob die angesagte Trumpffarbe richtig
	 * @param colour Die angesagte Trumpffarbe
	 * @return true falls die Farbe in Ordnung ist, false wenn nicht
	 */
	public boolean isValidColour(Colour colour) {
		return false;
	}
}