/**
 * 
 */
package Ruleset;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
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
	private List<PlayerState> players;
	
	/**
	 * Der Regelwerktyp vom Spiel
	 */
	private RulesetType ruleset;
	
	/**
	 * Der Spieler der als erste die Karte spielt
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
	private Map<String,Card> discardPile;
	
	/** 
	 * Die Karten die noch im Aufnahmestapel sind
	 */
	private List<Card> deck;
	
	/** 
	 * Die momentane Spielphase
	 */
	private GamePhase gamePhase;
	
	/**
	 * Die Trumpffarbe im Spiel, diese wird nur im Spiel Wizard verwendet
	 */
	private Card trumpCard;
	
	/**
	 * Erstellt eine GameStateklasse
	 * @param ruleset Der Regelwerktyp des Spiels
	 * @param deck Das Kartendeck im Spiel
	 */
	public GameState(RulesetType ruleset, List<Card> deck) {
		this.ruleset = ruleset;
		players = new ArrayList<PlayerState>();
		discardPile = new HashMap<String,Card>();
		this.deck = deck;
		gamePhase = GamePhase.Start;
		
	}
	
	/**
	 * Fügt den Spieler ins Spiel hinein
	 * @param name
	 */
	public void addPlayerToGame(String name) {
		PlayerState player = new PlayerState(name,ruleset);
		players.add(player);
	}
	
	/**
	 * Setzt einen neuen Spieler als firstPlayer
	 * @param player Der neue firstPlayer
	 */
	public boolean setFirstPlayer(PlayerState player) {
		if(firstPlayer == player) {
			this.firstPlayer = player;	
			return true;
		} else {
			return false;
		}
		
	}

	/** 
	 * Holt den Spieler der als erster am Zug war
	 * @return firstPlayer Der Spielzustand des Spielers der als erster am Zug war
	 */
	public PlayerState getFirstPlayer() {
		return firstPlayer;
	}
	
	/**
	 * Setzt einen neuen Spieler als currentPlayer
	 * @param player Der neue currentPlayer
	 */
	public boolean setCurrentPlayer(PlayerState player) {
		if(currentPlayer == player) {
			this.currentPlayer = player;	
			return true;
		} else {
			return false;
		}
		
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
	 * @return deck Holt die Karten die noch im Aufnahmestapel sind
	 */
	public List<Card> getCardsLeftInDeck() {
		return this.deck;
	}

	/** 
	 *Holt die gespielten Karten im Ablagestapel
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
	public PlayerState getPlayerState(String name) {
		for(PlayerState p : players) {
			if(p.getName().equals(name)) {
				return p;
			} 
		}
		return null;
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
	
	public int getRoundNumber() {
		return roundNumber;
	}
	
	private void newRound() {
		roundNumber++;
	}
	
	/**
	 * Holt die Anzahl der gespielten Karten 
	 * @return Die Anzahl der gespielten Karten
	 */
	public int getNumberOfPlayedCards() {
		return discardPile.size();
	}
	
	/**
	 * Holt die Karten eines Spielers
	 * @param name Der Name vom Spieler 
	 * @return Karten
	 */
	public List<Card> getPlayerCards(String name) {
		for(PlayerState p : players) {
			if(p.getName().equals(name)) {
				return p.getHand();
			}
		}
		return null;
	}
	
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
	/**
	 * Verteilt eine bestimmte Anzahl an Karten an die Spieler
	 * @param number Die Anzahl an Karten
	 * @return True falls ein Spieler keine Karten hat
	 */
	public boolean dealCards(int number) {
		for(PlayerState player : players) {
			if(!player.getHand().isEmpty()) {
				return false;
			}
		}
		
		for(PlayerState player : players) {
			for(int i = 0; i < number; i++) {
				player.addCard(((LinkedList<Card>) deck).pop());
			}
		}
		return true;
	}
	
	/**
	 * Setzt den nächsten Spieler
	 */
	public void nextPlayer() {
		ListIterator i = players.listIterator(players.indexOf(currentPlayer));
		currentPlayer = (PlayerState)i.next();
    }
	
	/**
	 * Gibt eine bestimmte Karte einem Spieler
	 * @param name Der Name des Spielers
	 * @return true falls die Karte im Stapel ist, false wenn nicht
	 */
	public boolean giveACard(String name, Card card) {
		return false;	
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