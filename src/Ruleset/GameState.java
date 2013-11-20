/**
 * 
 */
package Ruleset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/** 
 * GameState. Das GameState modelliert einen aktuellen Spielzustand, es wird vom GameServer instanziert und vom RuleSet bearbeitet. Es enthält die einzelnen PlayerStates, sowie Informationen 
 * zum Ablage-, Aufnahmestapel, Rundenanzahl, den momentan aktiven Spieler sowie GamePhase.
 */
public class GameState {
	/** 
	 * Die Spieler die im Spiel sind
	 */
	private List<PlayerState> players;
	
	/**
	 * Der Regelwerktyp vom Spiel
	 */
	private RulesetType ruleset;
	
	/**
	 * Der Spieler der am Anfang einer Runde anfängt
	 */
	private PlayerState firstPlayer;
	
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
	private List<DiscardedCard> discardPile;
	
	/** 
	 * Die Karten die noch im Aufnahmestapel sind
	 */
	private List<Card> deck;
	
	/**
	 * Die Trumpffarbe im Spiel
	 */
	private Card trumpCard;
	
	/**
	 * Erstellt eine GameStateklasse und trumpCard wird als WizardCard.Empty instanziert
	 * @param ruleset Der Regelwerktyp des Spiels
	 * @param deck Das Kartendeck im Spiel
	 */
	protected GameState(RulesetType ruleset, List<Card> deck) {
		this.ruleset = ruleset;
		players = new LinkedList<PlayerState>();
		discardPile = new ArrayList<DiscardedCard>();
		this.deck = deck;
		roundNumber = 1;
		trumpCard = EmptyCard.Empty;
	}
	
	/**
	 * Fügt den Spieler ins Spiel hinein, falls er nicht schon im Spiel ist
	 * @param name Der Name eines Spielers
	 * @return true falls der Spieler noch nicht im Spiel ist, und false sonst
	 */
	protected boolean addPlayerToGame(String name) {
		PlayerState player = new PlayerState(name,ruleset);
		
		if(players.contains(player)) {
			return false;
		} else {
			players.add(player);
			return true;
		}
	}

	/**
	 * Gibt die List von Spielern zurück
	 * @return Die Liste von Spielern
	 */
	protected List<PlayerState> getPlayers() {
		return players;
	}
	
	/**
	 * Setzt einen neuen Spieler als firstPlayer und als currentPlayer
	 * und erhöht die Rundennummer um eins
	 * @param player Der neue firstPlayer
	 */
	protected void setFirstPlayer(PlayerState player) {
		firstPlayer = player;
		currentPlayer = player;
	}
	
	/**
	 * Macht eine neue Runde, wird aufgerufen wenn ein neuer Spieler als firstPlayer
	 * gesetzt wird.
	 */
	protected void newRound() {
		roundNumber++;
	}

	/** 
	 * Holt den Spieler der als erster am Zug war
	 * @return firstPlayer Der Spielzustand des Spielers der als erster am Zug war
	 */
	protected PlayerState getFirstPlayer() {
		return firstPlayer;
	}
	
	/**
	 * Setzt einen neuen Spieler als currentPlayer
	 * @param player Der neue currentPlayer
	 */
	protected void setCurrentPlayer(PlayerState player) {
		this.currentPlayer = player;
	}

	/** 
	 * Holt den Spieler der momentan am Zug ist
	 * @return currentPlayer Der Spielzustand des Spielers der grad am Zug ist
	 */
	protected PlayerState getCurrentPlayer() {
		return currentPlayer;
	}

	/** 
	 * Holt die Karten die noch im Aufnahmestapel sind
	 * @return deck Holt die Karten die noch im Aufnahmestapel sind
	 */
	protected List<Card> getCardsLeftInDeck() {
		return this.deck;
	}
	
	/**
	 * Stellt die Stiche die ein Spieler gemacht hat zurück in den Kartenstapel
	 * @param player Der Spieler
	 */
	protected void putTricksBackInDeck(PlayerState player) {
		deck.addAll(player.getOtherData().removeTricks());
	}

	/** 
	 *Holt die gespielten Karten im Ablagestapel
	 *@return discardPile Die gespielten Karten
	 */
	protected List<DiscardedCard> getPlayedCards() {
		return discardPile;
	}
	
	/**
	 * Holt einen bestimmten Spielerzustand
	 * @param name Der Name des Spielers
	 * @return Der Spielzustand des Spielers
	 * @throws IllegalArgumentException falls der Spieler nicht existiert
	 */
	protected PlayerState getPlayerState(String name) 
			throws IllegalArgumentException{
		for(PlayerState p : players) {
			if(p.getName().equals(name)) {
				return p;
			} 
		}
		
		throw new IllegalArgumentException("Spieler " + name + 
				"existiert im GameState nicht");
	}
	
	/**
	 * Setzt die Trumpfkarte
	 * @param trumpCard Die Trumpfkarte
	 */
	protected void setTrumpCard(Card trumpCard){
		this.trumpCard = trumpCard;
	}

	/**
	 * Holt die momentane Trumpfkarte im Spiel
	 * @return trumpCard Die momentane Trumpfkarte
	 */
	protected Card getTrumpCard(){
		return trumpCard;
	}
	
	/**
	 * Holt die Anzahl an Runden
	 * @return Die Anzahl der Runden
	 */
	protected int getRoundNumber() {
		return roundNumber;
	}
	
	/**
	 * Holt die Anzahl der gespielten Karten 
	 * @return Die Anzahl der gespielten Karten
	 */
	protected int getNumberOfPlayedCards() {
		return discardPile.size();
	}
	
	/**
	 * Mischt das Deck
	 */
	protected void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
	/**
	 * Verteilt eine bestimmte Anzahl an Karten an die Spieler
	 * @param number Die Anzahl an Karten
	 * @return True falls ein Spieler keine Karten hat, false sonst
	 */
	protected boolean dealCards(int number) {
		for(PlayerState player : players) {
			if(!player.getHand().isEmpty()) {
				return false;
			}
		}
		
		for(PlayerState player : players) {
			for(int i = 0; i < number; i++) {
				player.addCard(getTopCard());
			}
		}
		return true;
	}
	
	/**
	 * Holt die oberste Karte aus dem Kartendeck raus
	 * @return Gibt die oberste Karte zurück
	 */
	protected Card getTopCard() {
		return ((LinkedList<Card>) deck).pop();
	}
	
	/**
	 * Setzt den naechsten Spieler
	 */
	protected void nextPlayer() {
		ListIterator<PlayerState> i = players.listIterator(
				players.indexOf(currentPlayer));
		currentPlayer = i.next();
    }
	
	/**
	 * Gibt eine bestimmte Karte einem Spieler
	 * @param name Der Name des Spielers
	 * @param card Die Karte
	 * @return true falls die Karte im Stapel ist, false wenn nicht
	 */
	protected boolean giveACard(PlayerState player, Card card) {
		return false;	
	}
	
	/**
	 * Entfernt eine Karte aus der Hand des currentPlayer und legt sie auf dem Ablagestapel
	 * @param card Die gespielte Karte
	 * @return isInHand Gibt true zurück wenn die gespielte Karte auf der Hand vom
	 * Spieler liegt und false sonst
	 */
	protected boolean playCard(Card card) {
		boolean isInHand;
		isInHand = currentPlayer.removeCard(card);
		
		if(isInHand == true) {
			discardPile.add(new DiscardedCard(currentPlayer.getName(), card));
		} 
		
		return isInHand;
	}
}