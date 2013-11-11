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
	 * @param ruleset Der Rulesettyp vom Server
	 * @param min Die minimale Anzahl an Spielern
	 * @param max Die maximale Anzahl an Spielern
	 * @param server Der Server auf dem gespielt wird
	 */
	protected ServerRuleset(RulesetType ruleset, int min, int max, 
			GameServer server) {
		RULESET = ruleset;
		MIN_PLAYERS = min;
		MAX_PLAYERS = max;
		gamePhase = GamePhase.Start;
		this.server = server;
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
	 * @return Gibt die momentan Spielphase zurück
	 */
	public GamePhase getGamePhase() {
		return gamePhase;
	}
	
	/**
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
	public void runGame() {
	}
	
	/** 
	 * Setzt den Spieler der als Erster am Zug ist, im Gamestate
	 * @param Der Spielerzustand des Spielers
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
	 * @param player Der Playerstate eines Spielers
	 * @return false wenn der selbe Spieler nochmal als currentPlayer gesetzt wird
	 */
	protected boolean setCurrentPlayer(PlayerState player) {
		return setCurrentPlayer(player);
	}
	
	/**
	 * Die OtherData eines Spielers
	 * @param player Der Spielerzustand
	 * @return Gibt OtherData zurück
	 */
	protected OtherData getOtherData(PlayerState player) {
		return player.getOtherData();
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
	 * Verarbeitet die RulesetMessage dass eine Karte vom Spieler gespielt
	 * @param msgCard Die Nachricht vom Client welche Karte gespielt wurde
	 * @param name Der Name des Spielers
	 */
	protected void resolveMessage(MsgCard msgCard, String name) {
		
	}
	
	/**
	 * Verteilt eine bestimmte Anzahl an Karten an die Spieler
	 * @param number Die Anzahl an Karten
	 * @return Gibt true zurück wenn ein Spieler keine Karten hat, false sonst
	 */
	protected boolean dealCards(int number) {
		return false;	
	}
	
	/**
	 * Gibt einem Spieler eine bestimmte Karte
	 * @param name Der Name eines Spielers
	 * @param card Eine Karte
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
		return gameState.playCard(card);
	}
	
	/**
	 * Setzt eine Karte als Trumpf
	 * @param card Eine karte
	 */
	protected void setTrumpCard(Card card) {
		gameState.setTrumpCard(card);
	}

	/** 
	 * Prüft ob ein gemachter Zug vom currentPlayer in einem Spiel gültig war,
	 * wenn nicht wird an den Spieler erneut eine MsgCardRequest gesendet
	 * @param card Die Karte die gespielt wurde
	 * @return true falls Zug gültig und false wenn nicht
	 */
	protected abstract boolean isValidMove(Card card);
	
	/**
	 * Berechnet und verteilt Stichpunkte an einzelne Spieler
	 */
	protected abstract void calculateTricks();

	/** 
	 * Berechnet das Ergebnis von der Rundenberechnung
	 */
	protected abstract void calculateRoundOutcome();

	/**
	 * Wird beim Spielende aufgerufen und gibt den Namen vom Sieger zurück
	 */
	protected abstract String getWinner();
}