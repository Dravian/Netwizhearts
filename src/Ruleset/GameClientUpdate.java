package Ruleset;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/** 
 * GameClientUpdate. Das GameClientUpdate wird vom RuleSet ueber den GameServer an den Client geschickt 
 * und enthaelt alle Aenderungen des GameState, die für den Client relevant sind. 
 * Das waeren seine Spielhand, der Ablagestapel sowie die Otherdata von allen Spielern
 * und die Trumpfkarte.
 */
public class GameClientUpdate implements Serializable{
	/** 
	 * Der Spielerzustand vom Client
	 */
	private PlayerState playerState;
	
	/** 
	 * Die gespielten Karten auf dem Ablagestapel
	 */
	private List<DiscardedCard> discardPile;
	
	/** 
	 * Die Spieldaten der anderen Spieler
	 */
	private List<OtherData> otherPlayerData;
	
	/**
	 * Der Spieler der zuerst gespielt hat
	 */
	private PlayerState firstPlayer;
	
	/**
	 * Der Spieler der grad am Zug ist
	 */
	private PlayerState currentPlayer;
	
	/**
	 * Die Anzahl der gespielten Runden
	 */
	private int roundNumber;
	
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
	protected GameClientUpdate(PlayerState playerState, List<DiscardedCard> discardPile,
			List<OtherData> otherPlayerData,  PlayerState firstPlayer, PlayerState currentPlayer,
			int roundNumber, Card trumpCard) {
		this.playerState = playerState;
		this.discardPile = discardPile;
		this.otherPlayerData = otherPlayerData;
		this.currentPlayer = currentPlayer;
		this.roundNumber = roundNumber;
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
	protected List<DiscardedCard> getPlayedCards() {
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
	 * Holt die OtherData aller Spieler als List
	 * @param player Der Name des Spielers
	 * @return otherPlayerData Die OtherData der anderen Spieler
	 */
	protected List<OtherData> getOtherPlayerData() {
		return otherPlayerData;
	}
	
	/**
	 * Gibt den Spieler der momentan am Zug ist zurück
	 * @return Der momentane Spieler
	 */
	protected PlayerState getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Gibt die Rundenanzahl zurück
	 * @return Die Rundenanzahl
	 */
	protected int getRoundNumber() {
		return roundNumber;
	}
	
	/**
	 * Holt die aufgedeckte Trumpfkarte
	 * @return trumpCard Die Trumpfkarte
	 */
	protected Card getTrumpCard() {
		return trumpCard;
	}

	/**
	 * Holt den Spieler der zuerst eine Karte hingelegt hat
	 * @return Den ersten Spieler
	 */
	protected PlayerState getFirstPlayer() {
		return firstPlayer;
	}
}