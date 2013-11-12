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
	 * Verarbeitet die RulesetMessage dass ein Spieler eine Stichangabe gemacht hat
	 * @param msgNumber Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	protected void resolveMessage(MsgNumber msgNumber, String name) {
	}

	/**
	 * Verarbeitet die RulesetMessage dass ein Spieler eine Farbe ausgewählt hat
	 * @param msgSelection Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	protected void resolveMessage(MsgSelection msgSelection, String name){
		
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
