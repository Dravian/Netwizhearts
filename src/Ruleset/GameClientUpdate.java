package Ruleset;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** 
 * GameClientUpdate. Das GameClientUpdate wird vom RuleSet ueber den GameServer an den Client geschickt 
 * und enthaelt alle Aenderungen des GameState, die f�r den Client relevant sind. 
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
	 * Der Spieler der grad am Zug ist
	 */
	private String currentPlayer;
	
	/**
	 * Die Anzahl der gespielten Runden
	 */
	private int roundNumber;
	
	/**
	 * Die aufgeckte Karte im Spiels. Ist EmptyCard falls es keine gibt.
	 */
	private Card uncoveredCard;
	
	/**
	 * Ob der Ablagestapel schon voll ist
	 */
	private boolean fullDiscardPile;
	
	/**
	 * Erstellt ein GameClientUpdate
	 * @param playerState Der Spielerzustand des Client
	 * @param discardPile Der Ablagestapel
	 * @param otherPlayerData Die Daten der anderen Spieler
	 * @param currentPlayer Der momentan aktive Spieler
	 * @param trumpCard Die Trumpffarbe
	 */
	protected GameClientUpdate(PlayerState playerState, List<DiscardedCard> discardPile,
			List<OtherData> otherPlayerData,  String currentPlayer,
			int roundNumber, Card trumpCard, boolean fullDiscardPile) {
		this.playerState = playerState;
		this.discardPile = discardPile;
		Collections.unmodifiableList(this.discardPile);
		this.otherPlayerData = otherPlayerData;
		Collections.unmodifiableList(this.otherPlayerData);
		this.currentPlayer = currentPlayer;
		this.roundNumber = roundNumber;
		this.uncoveredCard = trumpCard;
		this.fullDiscardPile = fullDiscardPile;
	}

	/** 
	 * Holt die Karten die der Client auf der Hand hat
	 * @return ownHand Die Hand des Clients
	 */
	public List<Card> getOwnHand() {
		List<Card> ownHand = playerState.getHand();
		Collections.unmodifiableList(ownHand);
		return ownHand;
	}

	/** 
	 * Holt die gespielten Karten auf dem Ablagestapel
	 * @return discardPile Die gespielten Karten
	 */
	public List<DiscardedCard> getPlayedCards() {
		return discardPile;
	}

	/**
	 * Holt die Otherdata des Client als String als Stringrepr�sentation
	 * @return ownData Die Otherdata des Clients
	 */
	public OtherData getOwnData() {
		return playerState.getOtherData();
	}
	
	/** 
	 * Holt die OtherData aller Spieler als List
	 * @param player Der Name des Spielers
	 * @return otherPlayerData Die OtherData der anderen Spieler
	 */
	public List<OtherData> getOtherPlayerData() {
		return otherPlayerData;
	}
	
	/**
	 * Gibt den Spieler der momentan am Zug ist zur�ck
	 * @return Der momentane Spieler
	 */
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Gibt die Rundenanzahl zurück
	 * @return Die Rundenanzahl
	 */
	public int getRoundNumber() {
		return roundNumber;
	}
	
	/**
	 * Holt die vom Deck aufgedeckte Karte, falls keine existiert wird eine EmptyCard zurückgegeben
	 * @return uncoveredCard Die aufgedeckte Karte
	 */
	public Card getUncoveredCard() {
		return uncoveredCard;
	}
	
	/**
	 * Gibt zurück ob der Ablagestapel voll ist
	 * @return true wenn er voll ist, false wenn nicht
	 */
	public boolean getFullDiscardPile() {
		return fullDiscardPile;
	}

}