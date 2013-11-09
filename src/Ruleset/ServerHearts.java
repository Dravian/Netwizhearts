package Ruleset;

import ComObjects.MsgMultiCards;
import ComObjects.MsgMultipleCardsRequest;

/**
 * Diese Klasse erstellt das Regelwerk zum Spiel Hearts. Sie enthält zudem weitere Methoden, 
 * welche für das Spiel Hearts spezifisch benötigt werden, wie die Regelung zum Tausch von Karten 
 * und die Berechnung der Stichpunkten.
 */
public class ServerHearts extends ServerRuleset {
	private final static int MIN_PLAYERS = 4;
	private final static int MAX_PLAYERS = 4;
	
	/**
	 * Erstellt das Regelwerk zum Spiel Hearts
	 */
	public ServerHearts() {
		super(RulesetType.Hearts, MIN_PLAYERS, MAX_PLAYERS);
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass mehrere Karten von einem Spieler übergeben wurden
	 * @param msgMultiCard Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	protected void processMessage(MsgMultiCards msgMultiCard, String name) {
	
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
	 * Erstellt die Karten zum Spiel Hearts
	 */
	protected void createCardDeck() {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

}
