package Ruleset;

import java.util.List;
import java.util.Set;

import Server.GameServer;
import ComObjects.MsgMultiCards;
import ComObjects.MsgMultiCardsRequest;

/**
 * ServerHearts. Diese Klasse erstellt das Regelwerk zum Spiel Hearts. Sie enthaelt zudem weitere Methoden, 
 * welche für das Spiel Hearts spezifisch benoetigt werden, wie die Regelung zum Tausch von Karten 
 * und die Berechnung der Stichpunkten.
 */
public class ServerHearts extends ServerRuleset {
	/**
	 * Die Minimale Anzahl an Spielern im Spiel Hearts
	 */
	private final static int MIN_PLAYERS = 4;
	/**
	 * Die maximale Anzahl an Spielern im Spiel Wizard
	 */
	private final static int MAX_PLAYERS = 4;
	/**
	 * Der Typ des Ruleset
	 */
	private final static RulesetType RULESET = RulesetType.Hearts;
	/**
	 * Die Punktanzahl eines Spielers ab der das Spiel zu Ende ist
	 */
	private final static int ENDING_POINTS = 100;
	
	/**
	 * Erstellt das Regelwerk zum Spiel Hearts
	 */
	public ServerHearts(GameServer s) {
		super(RULESET, MIN_PLAYERS, MAX_PLAYERS, s);
	}
	
	@Override
	protected boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;
	}
	
	/**
	 * Holt die Anzahl der Punkte die ein Spieler haben kann ab der, das Spiel vorbei ist
	 * @return Anzahl der maximalen Punkte
	 */
	protected int getEndingPoints() {
		return ENDING_POINTS;
	}
		
	/**
	 * Verarbeitet die RulesetMessage dass mehrerer Karten vom Spieler uebergeben werden.
	 * Die wird dann in areValidChoosenCards ueberprueft, bei falsche Eingabe wird´
	 * eine MsgMultiCardsRequest an den selben Spieler gesendet. 
	 * Bei richtiger Eingabe geht das Spiel weiter.
	 * @param msgMultiCard Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgMultiCards msgMultiCard, String name) {
		
	}
	
	/**
	 * Ueberprueft ob eine uebergebenes Kartenset von einem Spieler gültig ist
	 * @param cards Ein Kartenset
	 * @return true falls das Kartenset gueltig ist, false wenn nicht
	 */
	private boolean areValidChoosenCards(Set<Card> cards, String name) {
		return false;
	}

	@Override
	protected void calculateRoundOutcome() {
		
	}

	@Override
	protected void calculateTricks() {
		
	}

	@Override
	protected List<String> getWinners() {
		return null;
	}

	@Override
	public void runGame() {
		
	}


}
