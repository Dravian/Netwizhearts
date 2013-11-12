package Ruleset;

import java.util.List;
import java.util.Map;

/** 
 * GameClientUpdate. Das GameClientUpdate wird vom RuleSet ueber den GameServer an den Client geschickt 
 * und enthaelt alle Aenderungen des GameState, die für den Client relevant sind. 
 * Das waeren seine Spielhand, der Ablagestapel sowie die Otherdata von allen Spielern
 * und die Trumpfkarte.
 */
public class GameClientUpdate {
	/** 
	 * Der Spielerzustand vom Client
	 */
	private PlayerState playerState;
	
	/** 
	 * Die gespielten Karten auf dem Ablagestapel
	 */
	private Map<String,Card> discardPile;
	
	/** 
	 * Die Spieldaten der anderen Spieler
	 */
	private Map<String,OtherData> otherPlayerData;
	
	/**
	 * Der Spieler der grad am Zug ist
	 */
	private PlayerState currentPlayer;
	
	/**
	 * Die Trumpfkarte des Spiels
	 */
	private Card trumpCard;
	
	/**
	 * Erstellt ein GameClientUpdate
	 * @param playerState Der Spielerzustand des Client
	 * @param discardPile Der Ablagestapel
	 * @param otherPlayerData Die Daten der anderen Spieler
	 * @param currentPlayer Der momentan aktive Spieler
	 * @param trumpCard Die Trumpffarbe
	 */
	protected GameClientUpdate(PlayerState playerState, Map<String,Card> discardPile,
			Map<String,OtherData> otherPlayerData,  PlayerState currentPlayer,
			Card trumpCard) {
		this.playerState = playerState;
		this.discardPile = discardPile;
		this.otherPlayerData = otherPlayerData;
		this.currentPlayer = currentPlayer;
		this.trumpCard = trumpCard;
	}

	/** 
	 * Holt die Karten die der Client auf der Hand hat
	 * @return ownHand Die Hand des Clients
	 */
	protected List<Card> getOwnHand() {
		List<Card> ownHand = playerState.getHand();
		return ownHand;
	}

	/** 
	 * Holt die gespielten Karten auf dem Ablagestapel
	 * @return discardPile Die gespielten Karten
	 */
	protected Map<String, Card> getPlayedCards() {
		return discardPile;
	}

	/**
	 * Holt die Otherdata des Client als String als Stringrepräsentation
	 * @return ownData Die Otherdata des Clients
	 */
	protected OtherData getOwnData() {
		return playerState.getOtherData();
	}
	
	/** 
	 * Holt die OtherData eines anderen Spielers als Stringrepräsentation
	 * @param player Der Name des Spielers
	 * @return otherPlayerData Die OtherData der anderen Spieler
	 */
	protected OtherData getOtherPlayerData(String player) {
		return otherPlayerData.get(player);
	}
	
	/**
	 * Gibt den Spieler der momentan am Zug ist zurück
	 * @return Der momentane Spieler
	 */
	protected PlayerState getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Holt die aufgedeckte Trumpfkarte
	 * @return trumpCard Die Trumpfkarte
	 */
	protected Card getTrumpCard() {
		return trumpCard;
	}
}