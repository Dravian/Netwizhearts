/**
 * 
 */
package Ruleset;

import Client.ClientModel;
import ComObjects.MsgCard;
import ComObjects.MsgCardRequest;
import ComObjects.MsgMultipleCardsRequest;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelectionRequest;
import ComObjects.MsgUser;
import ComObjects.RulesetMessage;

/** 
 * ClientRuleset ist eine abstrakte Klasse und wird zur Regelvorauswertung im Client verwendet. 
 * Dazu benutzt es die isValidMove() Methode. Des Weiteren kann es vom ClientModel 
 * erhaltene RulesetMessages mit der resolveMessage() Methode behandeln.
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
		
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt mehrere Karten anzugeben
	 * @param msgMultiCardsRequest Die Nachricht vom Server
	 */
	public void resolveMessage(MsgMultipleCardsRequest msgMultiCardsRequest) {
		
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Stichanzahl anzugeben
	 * @param msgNumber Die Nachricht vom Server
	 */
	public void resolveMessage(MsgNumberRequest msgNumber) {
		
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Farbe auszuwählen
	 * @param msgSelection Die Nachricht vom Server
	 */
	public void resolveMessage(MsgSelectionRequest msgSelection) {
		
	}
	
	/**
	 * Schickt eine Nachricht übers Model an den Server
	 * @param message Die Nachricht
	 */
	protected void send(RulesetMessage message) {
		
	}
	
	/** 
	 * Prüft ob ein gemachter Zug in einem Spiel gültig war
	 */
	public abstract boolean isValidMove(Card card);
}