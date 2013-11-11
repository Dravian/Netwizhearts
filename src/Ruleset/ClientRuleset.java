/**
 * 
 */
package Ruleset;

import java.util.List;
import java.util.Map;

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
 * erhaltene RulesetMessages mit der resolveMessage() Methode behandeln und neue 
 * RulesetMessages senden.
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
	 * @param client Das ClientModel auf dem gespielt wird
	 */
	protected ClientRuleset(RulesetType ruleset, int minPlayers, 
			int maxPlayers,ClientModel client) {
		RULESET = ruleset;
		MIN_PLAYERS = minPlayers;
		MAX_PLAYERS = maxPlayers;
		gamePhase = GamePhase.Start;
		this.client = client;
	}
	
	/**
	 * Gibt den Typ des Regelwerks zurück
	 * @return Der Typ vom Regelwerk
	 */
	public RulesetType getRulesetType() {
		return RULESET;
	}
	
	/**
	 * Gibt die Mindestanzahl an Spielern zurück für dieses Spiel
	 * @return Die Mindestanzahl an Spielern
	 */
	public int getMinPlayers() {
		return MIN_PLAYERS;
	}
	
	/**
	 * Gibt die Maximale Anzahl an Spielern zurück
	 * @return Die maximale Anzahl an Spielern
	 */
	public int getMaxPlayers() {
		return MAX_PLAYERS;
	}
	
	/**
	 * Gibt die momentanen Spielphase zurück
	 * @return gamePhase Die Spielphase
	 */
	public GamePhase getGamePhase() {
		return gamePhase;
	}

	/**
	 * Gibt die eigenen Handkarten zurück
	 * @return Liste von Karten
	 */
	public List<Card> getOwnHand() {
		return gameState.getOwnHand();
	}
	
	/**
	 * Gibt die OtherData des Models zurück
	 * @return Die Otherdata des Models
	 */
	public OtherData getOwnData() {
		return gameState.getOwnData();
	}
		
	/** 
	 * Holt die OtherData eines anderen Spielers 
	 * @param Der Spielername
	 * @return otherPlayerData Die OtherData 
	 */
	public OtherData getOtherPlayerData(String player) {
		return gameState.getOtherPlayerData(player);
	}
	
	/**
	 * Gibt den Spieler der momentan am Zug ist zurück
	 * @return Der momentane Spieler
	 */
	public PlayerState getCurrentPlayer() {
		return gameState.getCurrentPlayer();
	}
	
	/**
	 * Holt die aufgedeckte Trumpfkarte
	 * @return Eine Karte
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
	public void resolveMessage(MsgUser clientUpdate) {
		this.gameState = clientUpdate.getGameClientUpdate();
	}
	
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Karte zu spielen
	 * @param msgCardRequest Die Nachricht vom Server
	 */
	public void resolveMessage(MsgCardRequest msgCardRequest) {
		
	}
	
	/**
	 * Verpackt eine Karte in eine Rulesetmessage und schickt sie an den Server
	 * @param card Die karte
	 */
	public void send(Card card) {
		send(new MsgCard(card));
	}
	
	/**
	 * Schickt eine RulesetMessage übers Model an den Server
	 * @param message Die Nachricht
	 */
	protected void send(RulesetMessage message) {
		client.send(message);
	}
	
	/** 
	 * Prüft ob ein gemachter Zug in einem Spiel gültig war
	 * @param card Die Karte
	 */
	public abstract boolean isValidMove(Card card);
}