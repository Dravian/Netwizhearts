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
		// TODO Automatisch erstellter Methoden-Stub

	}
	
	/**
	 * Erstellt ein neues GameState
	 */
	protected void createNewGameState() {
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
