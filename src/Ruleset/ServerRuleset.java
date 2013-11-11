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
	 * Die momentane Spielphase
	 */
	private GamePhase gamePhase;
	
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
	protected ServerRuleset(RulesetType ruleset, int min, int max, GameServer s) {
		RULESET = ruleset;
		MIN_PLAYERS = min;
		MAX_PLAYERS = max;
		gamePhase = GamePhase.Start;
		this.server = s;
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
	 * Gibt den momentanen Spielzustand zurück
	 */
	public GamePhase getGamePhase() {
		return gamePhase;
	}
	
	/** 
	 * Erstellt ein Spiel und einen GameState
	 */
	protected void initGame() {
		// begin-user-code
		// TODO Auto-generated method stub
	
		// end-user-code
	}/**
	 * Erzeugt ein Kartendeck, abhängig von dem RulesetType
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
	 */
	protected void setFirstPlayer(PlayerState player) {
		gameState.setFirstPlayer(player);
	}

	/**
	 * Holt den Spieler der als erster am Zug war
	 * @return firstPlayer Der Spielzustand des Spielers der als erster am Zug war
	 */
	protected PlayerState getFirstPlayer() {
		PlayerState firstPlayer = gameState.getFirstPlayer();
		return firstPlayer;
	}
	
	/**
	 * Setzt den nächsten Spieler in der List als currentPlayer
	 * @return true falls es ein anderer Spieler ist und false wenn es derselbe ist.
	 */
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
	protected void addPlayerToGame(String name) {
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
	 * Verarbeitet eine RulesetMessage die von einem Spieler kommt
	 * @param message Die Nachricht
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(RulesetMessage message, String name) {
		
	}
	
	/** 
	 * Verarbeitet die RulesetMessage dass eine Karte vom Spieler gespielt
	 * @param msgCard Die Nachricht vom Client welche Karte gespielt wurde
	 * @param name Der Name des Spielers
	 */
	protected void processMessage(MsgCard msgCard, String name) {
		
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass mehrere Karten von einem Spieler übergeben wurden
	 * @param msgMultiCard Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	protected void processMessage(MsgMultiCards msgMultiCard, String name) {
	
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass ein Spieler eine Stichangabe gemacht hat
	 * @param msgNumber Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	protected void processMessage(MsgNumber msgNumber, String name) {
	}

	/**
	 * Verarbeitet die RulesetMessage dass ein Spieler eine Farbe ausgewählt hat
	 * @param msgSelection Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	protected void processMessage(MsgSelection msgSelection, String name){
		
	}
	
	/**
	 * Verteil eine bestimmte Anzahl an Karten an die Spieler
	 * @param number Die Anzahl an Karten
	 * @return Gibt true zurück wenn ein Spieler keine Karten hat, false sonst
	 */
	protected boolean dealCards(int number) {
		return false;	
	}
	
	/**
	 * Gibt einem Spieler eine bestimmte Karte
	 * @param name Der Name eines Spielers
	 * @return Gibt true zurück wenn die Karte im Deck ist, false sonst
	 */
	protected boolean giveACard(String name, Card card) {
		return false;	
	}
	
	/**
	 * Der momentane Spieler spielt eine Karte
	 * @param card Die gespielte Karte
	 * @return true falls der Spieler die Karte hat
	 */
	protected boolean playCard(Card card) {
		gameState.playCard(card);
		return false;
	}
	
	/**
	 * Setzt eine Karte als Trumpf
	 * @param card Eine karte
	 */
	protected void setTrumpCard(Card card) {
		gameState.setTrumpCard(card);
	}

	/** 
	 * Prüft ob ein gemachter Zug in einem Spiel gültig war, wenn nicht wird an
	 * den Spieler erneut eine MsgCardRequest gesendet
	 * @param card Die Karte die gespielt wurde
	 * @param name Der Name des Spielers
	 * @return true falls Zug gültig und false wenn nicht
	 */
	protected abstract boolean isValidMove(Card card);
	
	/**
	 * Berechnet und verteilt Stichpunkte an einzelne Spieler
	 */
	protected abstract void calculateTricks() ;

	/** 
	 * Berechnet das Ergebnis von der Berechnung eines Befehls
	 */
	protected abstract void calculateRoundOutcome();

	/**
	 * Wird beim Spielende aufgerufen und gibt den Namen vom Sieger zurück
	 */
	protected abstract String getWinner();
}