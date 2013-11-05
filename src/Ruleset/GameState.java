/**
 * 
 */
package Ruleset;

import java.util.Set;

/** 
 * Beschreibt den Spielzustand eines Spieles gerade
 */
public class GameState {
	/** 
	 * Die Spieler die im Spiel sind
	 */
	private Set<PlayerState> players;
	
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
	private Set<Card> playedCards;
	
	/** 
	 * Die Karten die noch im Aufnahmestapel sind
	 */
	private Set<Card> cardsLeftInDeck;
	
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
	public Set<Card> getCardsLeftInDeck() {
		return this.cardsLeftInDeck;
	}

	/** 
	 *Holt die gespielten Karten im Ablagestapel
	 *@return playedCards Die gespielten Karten
	 */
	public void getPlayedCards() {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}

	/** 
	 * Holt einen bestimmten Spieler
	 * @param name Der Name des Spielers
	 * @return player Der Spielzustand des Spielers
	 */
	public PlayerState getPlayer(String name) {
		
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
	 * Entfernt eine Karte aus der Hand des currentPlayer und legt sie auf dem Ablagestapel
	 * @param card Die gespielte Karte
	 */
	public void playCard(Card card) {
		currentPlayer.removeCard(card);
		playedCards.add(card);
	}
}