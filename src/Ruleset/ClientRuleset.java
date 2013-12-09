/**
 * 
 */
package Ruleset;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import Client.ClientModel;
import ComObjects.*;

/**
 * ClientRuleset. ClientRuleset ist eine abstrakte Klasse und wird zur
 * Regelvorauswertung im Client verwendet. Dazu benutzt es die isValidMove()
 * Methode. Des Weiteren kann es vom ClientModel erhaltene RulesetMessages mit
 * der resolveMessage() Methode behandeln und neue RulesetMessages senden.
 */
public abstract class ClientRuleset {
	/**
	 * Das Model in dem sich befindet
	 */
	private ClientModel client;

	/**
	 * Der Spielzustand aus der Sicht des Models
	 */
	private GameClientUpdate gameState;

	/**
	 * Der Spieltyp
	 */
	private final RulesetType RULESET;

	/**
	 * Die momentane Spielphase
	 */
	private GamePhase gamePhase;

	/**
	 * Erstellt eine ClientRuleset Klasse
	 * 
	 * @param ruleset
	 *            Das Ruleset zum Spiel
	 * @param minPlayers
	 *            Die minimale Spieleranzahl
	 * @param maxPlayers
	 *            Die maximale Spieleranzahl
	 * @param client
	 *            Das ClientModel auf dem gespielt wird
	 */
	protected ClientRuleset(RulesetType ruleset, ClientModel client) {
		RULESET = ruleset;
		gamePhase = GamePhase.Start;
		this.client = client;
		gameState = null;
	}

	/**
	 * Gibt den Typ des Regelwerks zurueck
	 * 
	 * @return Der Typ vom Regelwerk
	 */
	public RulesetType getRulesetType() {
		return RULESET;
	}

	/**
	 * Setzt die Spielphase neu
	 * 
	 * @param phase
	 *            Die neue Spielphase
	 */
	protected void setGamePhase(GamePhase phase) {
		gamePhase = phase;
	}

	/**
	 * Gibt die momentane Spielphase zurück
	 * @return gamePhase Die Spielphase
	 */
	public GamePhase getGamePhase() {
		return gamePhase;
	}

	/**
	 * Gibt den Spielzustand zurück
	 * 
	 * @return den Spielzustand
	 */
	public GameClientUpdate getGameState() {
		if (gamePhase != GamePhase.Start) {
			return gameState;
		} else {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		}
	}

	/**
	 * Gibt die eigenen Handkarten zurück
	 * 
	 * @return Liste von Karten
	 */
	public List<Card> getOwnHand() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getOwnHand();
		}
	}

	/**
	 * Gibt die OtherData des Models zurück
	 * 
	 * @return Die Otherdata des Models
	 */
	public OtherData getOwnData() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getOwnData();
		}
	}

	/**
	 * Holt die OtherData eines anderen Spielers
	 * 
	 * @param player
	 *            Der Spielername
	 * @return otherPlayerData Die OtherData
	 */
	public List<OtherData> getOtherPlayerData() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getOtherPlayerData();
		}
	}

	/**
	 * Gibt den Spieler der momentan am Zug ist zurueck
	 * 
	 * @return Der momentane Spieler
	 */
	public PlayerState getCurrentPlayer() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getCurrentPlayer();
		}
	}

	/**
	 * Holt die aufgedeckte Trumpfkarte
	 * 
	 * @return Eine Karte
	 */
	public Card getUncoveredCard() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getUncoveredCard();
		}
	}

	/**
	 * Holt die Anzahl der gespielten Runden
	 * 
	 * @return Die Anzahl der Runden
	 */
	public int getRoundNumber() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getRoundNumber();
		}
	}

	/**
	 * Holt die gespielten Karten auf dem Ablagestapel als DiscardedCards
	 * @return Die gespielten Karten
	 */
	public List<DiscardedCard> getPlayedCards() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getPlayedCards();
		}
	}
	
	/**
	 * Verarbeitet eine RulesetMessage vom Server
	 * 
	 * @param clientUpdate
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(RulesetMessage message) {
		throw new RulesetException(
				"Das Comobject RulesetMessage wird hier nicht" + "gebraucht");
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server ein Spielupdate an den
	 * Client schickt
	 * 
	 * @param clientUpdate
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgUser clientUpdate) {
		setGamePhase(GamePhase.Playing);
		this.gameState = clientUpdate.getGameClientUpdate();
	}

	/**
	 * Setzt ein neues GameClientUpdate
	 * 
	 * @param clientUpdate
	 *            Das Update des Spiels
	 */
	protected void setGameState(GameClientUpdate clientUpdate) {
		this.gameState = clientUpdate;
	}

	/**
	 * Holt das Model
	 * 
	 * @return Das Model
	 */
	protected ClientModel getModel() {
		return client;
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt
	 * eine Karte zu spielen
	 * 
	 * @param msgCardRequest
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgCardRequest msgCardRequest) {
		throw new IllegalArgumentException(
				"Das Comobject MsgCardRequest wird hier nicht" + "gebraucht");
	}

	/**
	 * Verarbeitet die RulesetMessage dass das Spiel zu Ende ist und das es
	 * einen Sieger gibt
	 * 
	 * @param msgCardRequest
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgGameEnd gameEnd) {
		setGamePhase(GamePhase.Ending);
		getModel().announceWinner(gameEnd.getWinnerName());

	}

	public void resolveMessage(MsgCard card) {
		throw new IllegalArgumentException(
				"Das Comobject MsgCard wird hier nicht" + "gebraucht");
	}

	public void resolveMessage(MsgNumber number) {
		throw new IllegalArgumentException(
				"Das Comobject MsgNumber wird hier nicht" + "gebraucht");
	}

	public void resolveMessage(MsgSelection selection) {
		throw new IllegalArgumentException(
				"Das Comobject MsgSelection wird hier nicht" + "gebraucht");
	}

	public void resolveMessage(MsgMultiCards mulit) {
		throw new IllegalArgumentException(
				"Das Comobject MsgMultiCards wird hier nicht" + "gebraucht");
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt
	 * eine Stichanzahl anzugeben
	 * 
	 * @param msgNumber
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgNumberRequest msgNumber) {
		throw new IllegalArgumentException(
				"Das Comobject MsgNumberRequest wird hier nicht" + "gebraucht");
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt
	 * eine Farbe auszuwaehlen
	 * 
	 * @param msgSelection
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgSelectionRequest msgSelection) {
		throw new IllegalArgumentException(
				"Das Comobject MsgSelection wird hier dnicht" + "gebraucht");
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt
	 * mehrere Karten anzugeben
	 * 
	 * @param msgMultiCardsRequest
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgMultiCardsRequest msgMultiCardsRequest) {
		throw new IllegalArgumentException(
				"Das Comobject MsgMultiCardsRequest wird hier nicht"
						+ "gebraucht");
	}

	/**
	 * Ruft beim Model die Methode announceWinner, wenn es einem Gewinner gibt
	 * 
	 * @param winner
	 *            Der Gewinner
	 */
	protected void announceWinner(String winner) {
		// TODO client.announceWinner(winner);
	}

	/**
	 * Ruft beim Model die send Methode auf und verschickt eine Rulesetmessage
	 * 
	 * @param message
	 *            Die Nachricht
	 */
	protected void send(RulesetMessage message) {
		client.send(message);
	}

	/**
	 * Prueft ob ein gemachter Zug in einem Spiel gueltig war
	 * 
	 * @param card
	 *            Die Karte
	 * @return true falls die Karte gueltig ist, false wenn nicht
	 */
	protected abstract boolean isValidMove(Card card);
}