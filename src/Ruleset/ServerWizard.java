package Ruleset;

import Server.GameServer;
import ComObjects.MsgNumber;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelection;
import ComObjects.MsgSelectionRequest;

/**
 * ServerWizard. Diese Klasse erstellt das Regelwerk zum Spiel Wizard. Sie enthaelt zudem weitere Methoden,
 * welche für das Spiel Wizard spezifisch benoetigt werden, wie das Ansage von Stichen, 
 * der Bestimmung von Trumpffarben und die Bestimmung der Rundenanzahl.
 */
public class ServerWizard extends ServerRuleset {
	/**
	 * Die minimale Anzahl an Spielern in Wizard
	 */
	private static final int MIN_PLAYERS = 3;
	/**
	 * Die maximal Anzahl an Spielern in Hearts
	 */
	private static final int MAX_PLAYERS = 6;
	/**
	 * Der Ruleset Typ des Spiels
	 */
	private final static RulesetType RULESET = RulesetType.Wizard;
	
	/**
	 * Die Anzahl an Runden die gespielt wird. Ist abhaengig von der Spieleranzahl.
	 */
	private int playingRounds;
	
	/**
	 * Erstellt das Regelwerk zum Spiel Wizard
	 */
	public ServerWizard(GameServer s) {
		super(RULESET, MIN_PLAYERS, MAX_PLAYERS, s);
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
	 * Setzt die Anzahl an Runden die es in diesem Spiel gibt
	 * @param rounds Die Anzahl an Runden
	 */
	private void setPlayingRounds(int rounds) {
		playingRounds = rounds;
	}
	
	/**
	 * Holt die Anzahl der Runden die gespielt werden
	 * @return playingRounds Die Anzahl an Runden
	 */
	protected int getplayingRounds() {
		return playingRounds;
	}
	/**
	 * Verarbeitet die RulesetMessage dass der Spieler eine Stichansage macht.
	 * Die wird dann in isValidNumber überprüft, bei falsche Eingabe wird´
	 * eine MsgCardRequest an den selben Spieler geschickt. 
	 * Bei richtiger Eingabe geht das Spiel weiter.
	 * @param msgNumber Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgNumber msgNumber, String name) {
	}

	/**
	 * Ueberprueft ob eine eingegebene Stichangabe eines Spielers gueltig ist.
	 * @param number Die Stichangabe
	 * @param name Der Name vom Spieler
	 * @return true falls die Stichangabe gültig ist, false wenn nicht
	 */
	private boolean isValidNumber(int number, String name) {
		return false;
	}
	/**
	 * Verarbeitet die RulesetMessage dass mehrerer Karten vom Spieler uebergeben werden.
	 * Die wird dann in isValidColour überprüft, bei falsche Eingabe wird´
	 * MsgMultiCardRequest an den selben Spieler geschickt. 
	 * Bei richtiger Eingabe geht das Spiel weiter.
	 * @param msgSelection Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgSelection msgSelection, String name){
		
	}
	
	/**
	 * Ueberprueft ob eine eingebene Trumpffarbe eines Spielers gueltig ist
	 * @param colour Die Trumpffarbe
	 * @param name Der Name des Spielers
	 * @return true falls die Farbe gueltig ist, false wenn nicht
	 */
	private boolean isValidColour(Colour colour, String name) {
		return false;
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

	@Override
	protected GameClientUpdate generateGameClientUpdate(String player) {
		// TODO Automatisch erstellter Methoden-Stub
		return null;
	}

}
