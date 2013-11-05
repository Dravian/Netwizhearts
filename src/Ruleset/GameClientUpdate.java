/**
 * 
 */
package Ruleset;

import java.util.Set;

/** 
 * Der momentane Spielzustand beschr�nkt aus der Sicht eines Clients
 */
public class GameClientUpdate {
	/** 
	 * Der Spielerzustand vom Client
	 */
	private PlayerState playerState;
	
	/** 
	 * Die gespielten Karten auf dem Ablagestapel
	 */
	private Set<Card> playedCards;
	
	/** 
	 * Die Spieldaten der anderen Spieler
	 */
	private Set<OtherData> otherPlayerData;
	
	/**
	 * Die Trumpffarbe des Spiels, diese wird nur beim Spiel Wizard verwendet
	 */
	private Card trumpCard;
	
	/**
	 * Erstellt ein GameClientUpdate
	 * @param playerState
	 * @param playedCards
	 * @param otherPlayerData
	 */
	GameClientUpdate(PlayerState playerState, Set<Card> playedCards, Set<OtherData> otherPlayerData) {
		this.playerState = playerState;
		this.playedCards = playedCards;
		this.otherPlayerData = otherPlayerData;
	}
	
	/**
	 * Erstellt ein GameClientUpdate mit der Trumpfkarte
	 * @param playerState
	 * @param playedCards
	 * @param otherPlayerData
	 * @param trumpCard
	 */
	GameClientUpdate(PlayerState playerState, Set<Card> playedCards, Set<OtherData> otherPlayerData, Card trumpCard) {
		this.playerState = playerState;
		this.playedCards = playedCards;
		this.otherPlayerData = otherPlayerData;
		this.trumpCard = trumpCard;
	}

	/** 
	 * Holt die Karten die der Client auf der Hand hat
	 * @return ownHand Die Hand des Clients
	 */
	public Set<Card> getOwnHand() {
		Set<Card> ownHand = playerState.getHand();
		return ownHand;
	}

	/** 
	 * Holt die gespielten Karten auf dem Ablagestapel
	 * @return playedCards Die gespielten Karten
	 */
	public Set<Card> getPlayedCards() {
		return playedCards;
	}

	/**
	 * Holt die zus�tzlichen Spieldaten des Client
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
	public Set<OtherData> getOtherPlayerData() {
		return otherPlayerData;
	}
	
	/**
	 * Holt die aufgedeckte Trumpfkarte
	 */
	public Card getTrumpCard() {
		return trumpCard;
	}
}