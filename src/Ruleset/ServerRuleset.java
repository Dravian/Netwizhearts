/**
 * 
 */
package Ruleset;

import java.util.Set;
import Server.GameServer;
import ComObjects.MsgCard;
import ComObjects.MsgMultiCards;
import ComObjects.MsgMultipleCardsRequest;
import ComObjects.MsgNumber;
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
	 * Die Karten die es im Spiel gibt
	 */
	private Set<Card> cardDeck;
	
	/** 
	 * Den momentane Spielzustand
	 */
	private GameState gameState;
	/** 
	 * Der GameServer auf dem gespielt wird
	 */
	private GameServer gameServer;

	/** 
	 * Erstellt ein Spiel und einen GameState
	 */
	protected void initGame() {
		// begin-user-code
		// TODO Auto-generated method stub
	
		// end-user-code
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
	 * Setzt den Spieler der am Nächsten am Zug ist, im Gamestate
	 */
	protected void setCurrentPlayer(PlayerState player) {
		this.gameState.setCurrentPlayer(player);
	}

	/**
	 * Holt den Spieler der gerade am Zug ist
	 * @return currentPlayer Der Spielzustand des Spielers der grad am Zug ist
	 */
	public PlayerState getCurrentPlayer() {
		PlayerState currentPlayer = gameState.getCurrentPlayer();
		return currentPlayer;
	}

	/** 
	 * Holt den Spielerzustand eines Spielers
	 * @param name Der Name des Spielers
	 * @return playerState Spielzustand eines Spielers
	 */
	public PlayerState getPlayerState(String name) {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}

	/** 
	 * Verarbeitet die RulesetMessage dass eine Karte vom Spieler gespielt
	 * @param msgCard Die Nachricht vom Client welche Karte gespielt wurde
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgCard msgCard, String name) {
		
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass mehrere Karten von einem Spieler übergeben wurden
	 * @param msgMultiCard Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgMultiCards msgMultiCard, String name) {
	
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass ein Spieler eine Stichangabe gemacht hat
	 * @param msgNumber Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgNumber msgNumber, String name) {
	}

	/**
	 * Verarbeitet die RulesetMessage dass ein Spieler eine Farbe ausgewählt hat
	 * @param msgSelection Die Nachricht vom Client
	 * @param name
	 */
	public void resolveMessage(MsgSelection msgSelection, String name){
		
	}
	
	/**
	 * Schickt einem Spieler die Aufforderung eine Karte zu spielen
	 * @param name Name des Spielers
	 * @return new MsgCardRequest() Eine Nachricht an den Spieler
	 */
	public MsgCardRequest sendCardRequest(String name) {
		return new MsgCardRequest();
	}
	
	/** 
	 * Prüft ob ein gemachter Zug in einem Spiel gültig war
	 */
	public abstract boolean isValidMove(Card card);

	/** 
	 * Berechnet das Ergebnis von der Berechnung eines Befehls
	 */
	public abstract void calculateOutcome();
}