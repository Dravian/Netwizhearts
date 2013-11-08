/**
 * 
 */
package Ruleset;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Server.GameServer;
import ComObjects.MsgCard;
import ComObjects.MsgMultiCards;
import ComObjects.MsgMultipleCardsRequest;
import ComObjects.MsgNumber;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelection;
import ComObjects.RulesetMessage;
/** 
 * Das ServerRuleset ist eine akstrakte Klasse und für den Ablauf und die Einhaltung der Regeln eines Spiels zuständig (/L280/). 
 * Das ServerRuleset wird im GameServer instanziert und verwaltet die Zustände des GameStates im Server. 
 * Mit der Methode isValidMove() wird eine Eingabe eines Clients auf Regelkonformität überprüft und dann 
 * im GameServer  das GameState verändert. Über resolveMessage() kann eine GameServerinstanz eine RulesetMessage 
 * vom Player an das Ruleset weiterleiten.
 */
public abstract class ServerRuleset {
	/**
	 * Der GameServer auf den gespielt wird
	 */
	private GameServer server;
	
	/** 
	 * Den momentane Spielzustand
	 */
	private GameState gameState;
	
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
	 * Erstellt ein ServerRuleset
	 * @param ruleset Der Spieltyp
	 */
	public ServerRuleset(RulesetType ruleset, int min, int max) {
		RULESET = ruleset;
		MIN_PLAYERS = min;
		MAX_PLAYERS = max;
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
	 * Gibt die Maximale anzahl an Spielern zurück
	 * @return Die maximale Anzahl an Spielern
	 */
	public int getMaxPlayers() {
		return MAX_PLAYERS;
	}
	
	/** 
	 * Erstellt ein Spiel und einen GameState
	 */
	protected void initGame() {
		// begin-user-code
		// TODO Auto-generated method stub
	
		// end-user-code
	}/**
	 * Erzeugt ein Kartendeck
	 * @return Gibt ein Kartendeck zurück
	 */
	private List<Card> createDeck() {
		List<Card> deck = new LinkedList<Card>();
		switch(RULESET) {
		case Wizard: 
			for(WizardCard wiz : WizardCard.values()) {
				deck.add(wiz);
			}
			break;
		case Hearts:
			for(HeartsCard hearts : HeartsCard.values()) {
				deck.add(hearts);
			}
			break;
		default:
		}
		return deck;
	}

	/** 
	 * Startet das Spiel
	 */
	protected void runGame() {
		// begin-user-code
		// TODO Auto-generated method stub
	
		// end-user-code
	}
	
	/** 
	 * Setzt den Spieler der als Erster am Zug ist, im Gamestate
	 * @return false wenn der selbe Spieler nochmal als firstPlayer gesetzt wird
	 */
	protected boolean setFirstPlayer(PlayerState player) {
		return setFirstPlayer(player);
	}

	/**
	 * Holt den Spieler der als erster am Zug war
	 * @return firstPlayer Der Spielzustand des Spielers der als erster am Zug war
	 */
	protected PlayerState getFirstPlayer() {
		PlayerState firstPlayer = gameState.getFirstPlayer();
		return firstPlayer;
	}
	
	protected boolean nextPlayer() {
		return false;	
	}

	/** 
	 * Setzt den Spieler der am Nächsten am Zug ist, im Gamestate
	 * @return false wenn der selbe Spieler nochmal als currentPlayer gesetzt wird
	 */
	protected boolean setCurrentPlayer(PlayerState player) {
		return setCurrentPlayer(player);
	}

	/**
	 * Holt den Spieler der gerade am Zug ist
	 * @return currentPlayer Der Spielzustand des Spielers der grad am Zug ist
	 */
	protected PlayerState getCurrentPlayer() {
		PlayerState currentPlayer = gameState.getCurrentPlayer();
		return currentPlayer;
	}
	
	
	/**
	 * Fügt einen Spieler ins Spiel ein
	 * @param name Der name vom Spieler
	 */
	public void addPlayerToGame(String name) {
		gameState.addPlayerToGame(name);
	}

	/** 
	 * Holt den Spielerzustand eines Spielers
	 * @param name Der Name des Spielers
	 * @return playerState Spielzustand eines Spielers
	 */
	protected PlayerState getPlayerState(String name) {
		return gameState.getPlayerState(name);
	}
	
	/**
	 * Holt die Spielkarten eines Spielers
	 * @param name Der Name eines Spielers
	 * @return Die Spielkarten des Spielers
	 */
	protected List<Card> getPlayerCards(String name) {
		return gameState.getPlayerCards(name);
	}
	/**
	 * Schickt eine Nachricht an einen Spieler
	 * @param message Die Nachricht vom Typ RulesetMessage
	 * @param name Der Name vom Spieler
	 */
	protected void send(RulesetMessage message, String name) {
		
	}
	
	/**
	 * Schickt eine Nachricht an alle Spieler
	 * @param message Die Nachricht
	 */
	protected void broadcast(RulesetMessage message) {
		
	}
	/** 
	 * Verarbeitet die RulesetMessage dass eine Karte vom Spieler gespielt
	 * @param msgCard Die Nachricht vom Client welche Karte gespielt wurde
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgCard msgCard, String name) {
		
	}
	
	/**
	 * Deals a number of cards from the top of the deck
	 * @param name Name of the Player who gets the cards
	 * @param number The number of cards
	 * @return True if a player has no cards, false if he does
	 */
	protected boolean dealCards(String name, int number) {
		return false;	
	}
	
	/**
	 * Gives one specific card of the deck to a Player
	 * @param name The name of the Player
	 * @return true if the card is in the deck
	 */
	protected boolean giveACard(String name, Card card) {
		return false;	
	}
	
	protected boolean playCard(Card card) {
		gameState.playCard(card);
		return false;
	}
	
	protected void setTrumpCard(Card card) {
		gameState.setTrumpCard(card);
	}

	/** 
	 * Prüft ob ein gemachter Zug in einem Spiel gültig war, wenn nicht wird an
	 * den Spieler erneut eine MsgCardRequest
	 * @param card Die Karte die gespielt wurde
	 * @param name Der Name des Spielers
	 * @return true falls Zug gültig und false wenn nicht
	 */
	protected abstract boolean isValidMove(Card card);

	/** 
	 * Berechnet das Ergebnis von der Berechnung eines Befehls
	 */
	protected abstract void calculateOutcome();

	
}