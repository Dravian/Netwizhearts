/**
 * 
 */
package Ruleset;

import java.util.List;

import Client.ClientModel;
import ComObjects.*;

/** 
 * ClientRuleset. ClientRuleset ist eine abstrakte Klasse und wird zur Regelvorauswertung im Client verwendet. 
 * Dazu benutzt es die isValidMove() Methode. Des Weiteren kann es vom ClientModel 
 * erhaltene RulesetMessages mit der resolveMessage() Methode behandeln und neue 
 * RulesetMessages senden.
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
	 * @param ruleset Das Ruleset zum Spiel
	 * @param minPlayers Die minimale Spieleranzahl
	 * @param maxPlayers Die maximale Spieleranzahl
	 * @param client Das ClientModel auf dem gespielt wird
	 */
	public ClientRuleset(RulesetType ruleset, ClientModel client) {
		RULESET = ruleset;
		gamePhase = GamePhase.Start;
		this.client = client;
	}
	
	/**
	 * Gibt den Typ des Regelwerks zurueck
	 * @return Der Typ vom Regelwerk
	 */
	public RulesetType getRulesetType() {
		return RULESET;
	}
	
	/**
	 * Setzt die Spielphase neu
	 * @param phase Die neue Spielphase
	 */
	protected void setGamePhase(GamePhase phase) {
		gamePhase = phase;
	}
	/**
	 * Gibt die momentane Spielphase zur�ck
	 * @return gamePhase Die Spielphase
	 */
	public GamePhase getGamePhase() {
		return gamePhase;
	}
	
	/**
	 * Gibt den Spielzustand zurück
	 * @return den Spielzustand
	 */
	public GameClientUpdate getGameState() {
		return gameState;
	}

	/**
	 * Gibt die eigenen Handkarten zurueck
	 * @return Liste von Karten
	 */
	public List<Card> getOwnHand() {
		return gameState.getOwnHand();
	}
	
	/**
	 * Gibt die OtherData des Models zurueck
	 * @return Die Otherdata des Models
	 */
	public OtherData getOwnData() {
		return gameState.getOwnData();
	}
		
	/** 
	 * Holt die OtherData eines anderen Spielers 
	 * @param player Der Spielername
	 * @return otherPlayerData Die OtherData 
	 */
	public List<OtherData> getOtherPlayerData() {
		return gameState.getOtherPlayerData();
	}
	
	/**
	 * Gibt den Spieler der momentan am Zug ist zurueck
	 * @return Der momentane Spieler
	 */
	public PlayerState getCurrentPlayer() {
		return gameState.getCurrentPlayer();
	}
	
	/**
	 * Holt die aufgedeckte Trumpfkarte
	 * @return Eine Karte
	 */
	public Card getTrumpCard() {
		return gameState.getTrumpCard();
	}
	
	/**
	 * Holt die Anzahl der gespielten Runden
	 * @return Die Anzahl der Runden
	 */
	public int getRoundNumber() {
		return gameState.getRoundNumber();
	}
	
	/**
	 * Holt die gespielten Karten auf dem Ablagestapel als DiscardedCards
	 * @return Die gespielten Karten
	 */
	public List<DiscardedCard> getPlayedCards() {
		return gameState.getPlayedCards();
	}
	
	/** 
	 * Verarbeitet eine RulesetMessage vom Server
	 * @param clientUpdate Die Nachricht vom Server
	 */
	public void resolveMessage(RulesetMessage message) {
		throw new RulesetException("Das Comobject RulesetMessage wird hier nicht" +
				"gebraucht");
	}
	
	/** 
	 * Verarbeitet die RulesetMessage dass der Server ein Spielupdate an den Client schickt
	 * @param clientUpdate Die Nachricht vom Server
	 */
	public void resolveMessage(MsgUser clientUpdate) {
		this.gameState = clientUpdate.getGameClientUpdate();
	}
	
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Karte zu spielen
	 * @param msgCardRequest Die Nachricht vom Server
	 */
	public void resolveMessage(MsgCardRequest msgCardRequest) {
		throw new RulesetException("Das Comobject MsgCardRequest wird hier nicht" +
				"gebraucht");
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass das Spiel zu Ende ist und das es einen Sieger gibt
	 * @param msgCardRequest Die Nachricht vom Server
	 */
	public void resolveMessage(MsgGameEnd msgCardRequest) {
		throw new RulesetException("Das Comobject MsgGameEnd wird hier nicht" +
				"gebraucht");
	}
	
	public void resolveMessage(MsgCard card) {
		throw new RulesetException("Das Comobject MsgCard wird hier nicht" +
				"gebraucht");
	}
	
	public void resolveMessage(MsgNumber number) {
		throw new RulesetException("Das Comobject MsgNumber wird hier nicht" +
				"gebraucht");
	}
	
	public void resolveMessage(MsgSelection selection) {
		throw new RulesetException("Das Comobject MsgSelection wird hier nicht" +
				"gebraucht");
	}
	
	public void resolveMessage(MsgMultiCards mulit){
		throw new RulesetException("Das Comobject MsgMultiCards wird hier nicht" +
				"gebraucht");
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Stichanzahl anzugeben
	 * @param msgNumber Die Nachricht vom Server
	 */
	public void resolveMessage(MsgNumberRequest msgNumber) {
		throw new RulesetException("Das Comobject MsgNumberRequest wird hier nicht" +
				"gebraucht");
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Farbe auszuwaehlen
	 * @param msgSelection Die Nachricht vom Server
	 */
	public void resolveMessage(MsgSelectionRequest msgSelection) {
		throw new RulesetException("Das Comobject MsgSelection wird hier nicht" +
				"gebraucht");
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt mehrere Karten anzugeben
	 * @param msgMultiCardsRequest Die Nachricht vom Server
	 */
	public void resolveMessage(MsgMultiCardsRequest msgMultiCardsRequest) {		
		throw new RulesetException("Das Comobject MsgMultiCardsRequest wird hier nicht" +
				"gebraucht");
	}
	
	/**
	 * Ruft beim Model die Methode announceWinner, wenn es einem Gewinner gibt
	 * @param winner Der Gewinner
	 */
	protected void announceWinner(String winner) {
		client.announceWinner(winner);
	}
	
	/**
	 * Ruft beim Model die send Methode auf und verschickt eine 
	 * Rulesetmessage
	 * @param message Die Nachricht
	 */
	protected void send(RulesetMessage message) {
		client.send(message);
	}
	
	/** 
	 * Prueft ob ein gemachter Zug in einem Spiel gueltig war
	 * @param card Die Karte
	 * @return true falls die Karte gueltig ist, false wenn nicht
	 */
	protected abstract boolean isValidMove(Card card);
}