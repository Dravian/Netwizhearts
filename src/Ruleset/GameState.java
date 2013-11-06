/**
 * 
 */
package Ruleset;

import java.util.ArrayList;
import java.util.Map;

/** 
 * Das GameState modelliert einen aktuellen Spielzustand, es wird vom GameServer instanziert 
 * und vom RuleSet bearbeitet. Es enthält die einzelnen PlayerStates, sowie Informationen 
 * zum Ablage-, Aufnahmestapel, Rundenanzahl, den momentan aktiven Spieler sowie GamePhase.
 */
public class GameState {
	/** 
	 * Die Spieler die im Spiel sind
	 */
	private ArrayList<PlayerState> players;
	
	/** 
	 * Der Spieler der gerade am Zug ist
	 */
	private PlayerState currentPlayer;
	
	/** 
	 * Die momentane Spielrunde
	 */
	private int roundNumber;
	
	/** 
	 * Die Karten die gespielt wurden
	 */
	private Map<String,Card> discardPile;
	
	/** 
	 * Die Karten die noch im Aufnahmestapel sind
	 */
	private ArrayList<Card> cardsLeftInDeck;
	
	/** 
	 * Die momentane Spielphase
	 */
	private GamePhase gamePhase;
	
	/**
	 * Die Trumpffarbe im Spiel, diese wird nur im Spiel Wizard verwendet
	 */
	private Card trumpCard;
	
	/**
	 * Setzt einen neuen Spieler als currentPlayer
	 * @param player Der neue currentPlayer
	 */
	public void setCurrentPlayer(PlayerState player) {
		this.currentPlayer = player;	
	}

	/** 
	 * Holt den Spieler der momentan am Zug ist
	 * @return currentPlayer Der Spielzustand des Spielers der grad am Zug ist
	 */
	public PlayerState getCurrentPlayer() {
		return currentPlayer;
	}

	/** 
	 * Holt die Karten die noch im Aufnahmestapel sind
	 * @return cardsLeftInDeck Holt die Karten die noch im Aufnahmestapel sind
	 */
	public ArrayList<Card> getCardsLeftInDeck() {
		return this.cardsLeftInDeck;
	}

	/** 
	 *Holt die gespielten Karten im Ablagestapel
	 * @return 
	 *@return discardPile Die gespielten Karten
	 */
	public Map<String,Card> getPlayedCards() {
		return discardPile;
	}
	
	/** 
	 * Holt einen bestimmten Spieler
	 * @param name Der Name des Spielers
	 * @return player Der Spielzustand des Spielers
	 */
	public PlayerState getPlayer(String name) {
		return new PlayerState();
	}
	
	/**
	 * Setzt die Trumpfkarte
	 * @param trumpCard Die Trumpfkarte
	 */
	public void setTrumpCard(Card trumpCard){
		this.trumpCard = trumpCard;
	}

	/**
	 * Holt die momentane Trumpfkarte im Spiel
	 * @return trumpCard Die momentane Trumpfkarte
	 */
	public Card getTrumpCard(){
		return trumpCard;
	}
	
	/**
	 * Holt die Anzahl der gespielten Karten 
	 * @return Die Anzahl der gespielten Karten
	 */
	public int getNumberOfPlayedCards() {
		return discardPile.size();
	}
	
	/**
	 * Entfernt eine Karte aus der Hand des currentPlayer und legt sie auf dem Ablagestapel
	 * @param card Die gespielte Karte
	 * @return isInHand Gibt true zurück wenn die gespielte Karte auf der Hand vom
	 * Spieler liegt und false sonst
	 */
	public boolean playCard(Card card) {
		boolean isInHand;
		isInHand = currentPlayer.removeCard(card);
		
		if(isInHand == true) {
			discardPile.put(currentPlayer.getName(), card);
		} 
		
		return isInHand;
	}
}