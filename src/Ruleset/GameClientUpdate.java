/**
 * 
 */
package Ruleset;

import java.util.ArrayList;
import java.util.Map;

/** 
 * Das GameClientUpdate wird vom RuleSet über den GameServer an den Client geschickt 
 * und enthält alle Änderungen des GameState, die für den Client relevant sind. 
 * Das wären seine Spielhand, der Ablagestapel sowie die Otherdata von allen Spielern.
 * Bei Wizard enthält es auch die momentane Trumpfkarte.
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
	private ArrayList<OtherData> otherPlayerData;
	
	/**
	 * Der Spieler der grad am Zug ist
	 */
	private PlayerState currentPlayer;
	
	/**
	 * Die Trumpffarbe des Spiels, diese wird nur beim Spiel Wizard verwendet
	 */
	private Card trumpCard;
	
	/**
	 * Erstellt ein GameClientUpdate zum Spiel Hearts
	 * @param playerState Der Spielerzustand des Client
	 * @param discardPile Die gespielten Karten
	 * @param otherPlayerData Die Daten der anderen Spieler
	 * @param currentPlayer Der momentan aktive Spieler
	 */
	GameClientUpdate(PlayerState playerState, Map<String,Card> discardPile, 
			ArrayList<OtherData> otherPlayerData, PlayerState currentPlayer) {
		this.playerState = playerState;
		this.discardPile = discardPile;
		this.otherPlayerData = otherPlayerData;
		this.currentPlayer = currentPlayer;
	}
	
	/**
	 * Erstellt ein GameClientUpdate zum Spiel Wizard
	 * @param playerState Der Spielerzustand des Client
	 * @param discardPile Der Ablagestapel
	 * @param otherPlayerData Die Daten der anderen Spieler
	 * @param currentPlayer Der momentan aktive Spieler
	 * @param trumpCard Die Trumpffarbe
	 */
	GameClientUpdate(PlayerState playerState, Map<String,Card> discardPile,
			ArrayList<OtherData> otherPlayerData,  PlayerState currentPlayer,
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
	public ArrayList<Card> getOwnHand() {
		ArrayList<Card> ownHand = playerState.getHand();
		return ownHand;
	}

	/** 
	 * Holt die gespielten Karten auf dem Ablagestapel
	 * @return discardPile Die gespielten Karten
	 */
	public Map<String, Card> getPlayedCards() {
		return discardPile;
	}

	/**
	 * Holt die zusätzlichen Spieldaten des Client
	 * @return ownData Die Spieldaten des Clients
	 */
	public OtherData getOwnData() {
		OtherData ownData = playerState.getOtherData();
		return ownData;
	}
	
	/** 
	 * Holt die Spieldaten der anderen Spieler
	 * @return otherPlayerData Die Spieldaten der anderen Spieler
	 */
	public ArrayList<OtherData> getOtherPlayerData() {
		return otherPlayerData;
	}
	
	/**
	 * Holt die aufgedeckte Trumpfkarte
	 */
	public Card getTrumpCard() {
		return trumpCard;
	}
}