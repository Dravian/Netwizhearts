package Ruleset;

import Server.GameServer;
import ComObjects.MsgNumber;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelection;
import ComObjects.MsgSelectionRequest;

/**
 * Diese Klasse erstellt das Regelwerk zum Spiel Wizard. Sie enthält zudem weitere Methoden,
 * welche für das Spiel Wizard spezifisch benötigt werden, wie das Ansage von Stichen, 
 * der Bestimmung von Trumpffarben und die Bestimmung der Rundenanzahl.
 */
public class ServerWizard extends ServerRuleset {
	private static final int MIN_PLAYERS = 3;
	private static final int MAX_PLAYERS = 6;
	
	/**
	 * Erstellt das Regelwerk zum Spiel Wizard
	 */
	public ServerWizard(GameServer s) {
		super(RulesetType.Wizard, MIN_PLAYERS, MAX_PLAYERS, s);
	}

	@Override
	protected boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;
	}

	@Override
	protected void calculateRoundOutcome() {

	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Spieler eine Stichansage macht.
	 * Die wird dann in isValidNumber überprüft, bei falsche Eingabe wird´
	 * generateMsgCardRequest für den selben Spieler aufgerufen. 
	 * Bei richtiger Eingabe geht das Spiel weiter.
	 * @param msgNumber Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgNumber msgNumber, String name) {
	}

	/**
	 * Überprüft ob eine eingegebene Stichangabe eines Spielers gültig ist
	 * @param number Die Stichangabe
	 * @param name Der Name vom Spieler
	 * @return true falls die Stichangabe gültig ist, false wenn nicht
	 */
	private boolean isValidNumber(int number, String name) {
		return false;
	}
	/**
	 * Verarbeitet die RulesetMessage dass mehrerer Karten vom Spieler übergeben werden.
	 * Die wird dann in isValidColour überprüft, bei falsche Eingabe wird´
	 * generateMsgMultiCardRequest für den selben Spieler aufgerufen. 
	 * Bei richtiger Eingabe geht das Spiel weiter.
	 * @param msgSelection Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgSelection msgSelection, String name){
		
	}
	
	/**
	 * Überprüft ob eine eingebene Trumpffarbe eines Spielers gültig ist
	 * @param colour Die Trumpffarbe
	 * @param name Der Name des Spielers
	 * @return true falls die Farbe gültig ist, false wenn nicht
	 */
	private boolean isValidColour(Colour colour, String name) {
		return false;
	}
	/**
	 * Generiert eine MsgNumberRequest und ruft bei sich die send Methode auf
	 * @param name Der Name vom Spieler
	 */
	private void generateNumberRequest(String name) {
		
	}
	
	/**
	 * Generiert eine MsgMultiCardRequest und ruft bei sich die send Methode auf
	 * @param name Der Name vom Spieler
	 */
	private void generateMsgSelectionRequest(String name) {
		
	}

	@Override
	protected void calculateTricks() {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

	@Override
	protected String getWinner() {
		// TODO Automatisch erstellter Methoden-Stub
		return null;
	}

}
