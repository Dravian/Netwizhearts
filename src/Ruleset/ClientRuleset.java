/**
 * 
 */
package Ruleset;

import java.util.List;

import Client.ClientModel;
import ComObjects.MsgCard;
import ComObjects.MsgCardRequest;
import ComObjects.MsgMultipleCardsRequest;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelectionRequest;
import ComObjects.MsgUser;
import ComObjects.RulesetMessage;

/** 
 * ClientRuleset ist eine abstrakte Klasse und wird zur Regelvorauswertung im Client verwendet. 
 * Dazu benutzt es die isValidMove() Methode. Des Weiteren kann es vom ClientModel 
 * erhaltene RulesetMessages mit der resolveMessage() Methode behandeln.
 */
public abstract class ClientRuleset {
	/** 
	 * Das Model in dem sich befindet
	 */
	private ClientModel client;
	
	/** 
	 * Der Spielzustand aus der Sicht des Models
	 */
	private GameClientUpdate gameState;
	
	/**
	 * Der Spieltyp
	 */
	private final RulesetType RULESET;
	
	/**
	 * Die Mindestanzahl an Spielern
	 */
	private final int MIN_PLAYERS;
	
	/**
	 * Die Maximale Anzahl an Spielern
	 */
	private final int MAX_PLAYERS;
	
	/** 
	 * Die momentane Spielphase
	 */
	private GamePhase gamePhase;
	
	/**
	 * Erstellt eine ClientRuleset Klasse
	 * @param ruleset Das Ruleset zum Spiel
	 * @param minPlayers Die minimale Spieleranzahl
	 * @param maxPlayers Die maximale Spieleranzahl
	 */
	protected ClientRuleset(RulesetType ruleset, int minPlayers, int maxPlayers) {
		RULESET = ruleset;
		MIN_PLAYERS = minPlayers;
		MAX_PLAYERS = maxPlayers;
		gamePhase = GamePhase.Start;
	}
	
	/**
	 * Gibt den Typ des Regelwerks zur�ck
	 * @return Der Typ vom Regelwerk
	 */
	public RulesetType getRulesetType() {
		return RULESET;
	}
	
	/**
	 * Gibt die Mindestanzahl an Spielern zur�ck f�r dieses Spiel
	 * @return Die Mindestanzahl an Spielern
	 */
	public int getMinPlayers() {
		return MIN_PLAYERS;
	}
	
	/**
	 * Gibt die Maximale anzahl an Spielern zur�ck
	 * @return Die maximale Anzahl an Spielern
	 */
	public int getMaxPlayers() {
		return MAX_PLAYERS;
	}

	public List<Card> getOwnHand() {
		return gameState.getOwnHand();
	}
	
	public OtherData getOwnData() {
		return gameState.getOwnData();
	}
		
	/** 
	 * Holt die Spieldaten der anderen Spieler
	 * @return otherPlayerData Die Spieldaten der anderen Spieler
	 */
	public List<OtherData> getOtherPlayerData() {
		return gameState.getOtherPlayerData();
	}
	
	/**
	 * Gibt den Spieler der momentan am Zug ist zur�ck
	 * @return Der momentane Spieler
	 */
	public PlayerState getCurrentPlayer() {
		return gameState.getCurrentPlayer();
	}
	
	/**
	 * Holt die aufgedeckte Trumpfkarte
	 */
	public Card getTrumpCard() {
		return gameState.getTrumpCard();
	}
	
	/** 
	 * Verarbeitet eine RulesetMessage vom Server
	 * @param clientUpdate Die Nachricht vom Server
	 */
	public void resolveMessage(RulesetMessage message) {
	}
	
	/** 
	 * Verarbeitet die RulesetMessage dass der Server ein Spielupdate an den Client schickt
	 * @param clientUpdate Die Nachricht vom Server
	 */
	protected void processMessage(MsgUser clientUpdate) {
		this.gameState = clientUpdate.getGameClientUpdate();
	}
	
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Karte zu spielen
	 * @param msgCardRequest Die Nachricht vom Server
	 */
	protected void processMessage(MsgCardRequest msgCardRequest) {
		
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt mehrere Karten anzugeben
	 * @param msgMultiCardsRequest Die Nachricht vom Server
	 */
	protected void processMessage(MsgMultipleCardsRequest msgMultiCardsRequest) {
		
		
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Stichanzahl anzugeben
	 * @param msgNumber Die Nachricht vom Server
	 */
	protected void processMessage(MsgNumberRequest msgNumber) {
		
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Farbe auszuw�hlen
	 * @param msgSelection Die Nachricht vom Server
	 */
	protected void processMessage(MsgSelectionRequest msgSelection) {
		
	}
	
	/**
	 * Schickt eine Nachricht �bers Model an den Server
	 * @param message Die Nachricht
	 */
	protected void send(RulesetMessage message) {
		
	}
	
	/** 
	 * Pr�ft ob ein gemachter Zug in einem Spiel g�ltig war
	 */
	public abstract boolean isValidMove(Card card);
}