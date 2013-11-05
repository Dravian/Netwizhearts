/**
 * 
 */
package Ruleset;

import java.util.Set;
import Server.GameServer;

/** 
 * Die abstrakte Klasse für das Regelwerk auf Serverseite
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
	 * Started das Spiel
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
	 * Verarbeitet eine RulesetMessage
	 */
	public void resolveMessage() {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
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