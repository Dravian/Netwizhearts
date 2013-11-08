package Ruleset;

import ComObjects.MsgNumber;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelection;
import ComObjects.MsgSelectionRequest;

/**
 * Diese Klasse erstellt das Regelwerk zum Spiel Wizard. Sie enthält zudem weitere Methoden,
 * welche für das Spiel Wizard spezifisch benötigt werden, wie das Bestimmen einer Trumpffarbe 
 * und die Bestimmung der Rundenanzahl.
 */
public class ServerWizard extends ServerRuleset {
	private static final int MIN_PLAYERS = 3;
	private static final int MAX_PLAYERS = 6;
	
	/**
	 * Erstellt das Regelwerk zum Spiel Wizard
	 */
	public ServerWizard() {
		super(RulesetType.Wizard, MIN_PLAYERS, MAX_PLAYERS);
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass ein Spieler eine Stichangabe gemacht hat
	 * @param msgNumber Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgNumber msgNumber, String name) {
	}

	/**
	 * Verarbeitet die RulesetMessage dass ein Spieler eine Farbe ausgewählt hat
	 * @param msgSelection Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgSelection msgSelection, String name){
		
	}

	@Override
	protected boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;
	}

	@Override
	protected void calculateOutcome() {
		// TODO Automatisch erstellter Methoden-Stub

	}
	
	/**
	 * Erstellt ein neues GameState
	 */
	protected void createNewGameState() {
	}

	/**
	 * Erstellt die Karten zum Spiel Wizard
	 */
	protected void createCardDeck() {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

}
