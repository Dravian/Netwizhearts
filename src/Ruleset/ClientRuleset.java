/**
 * 
 */
package Ruleset;

import Client.ClientModel;
import ComObjects.MsgClientUpdate;
import ComObjects.MsgCardRequest;
import ComObjects.MsgMultipleCardsRequest;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelectionRequest;

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
	public void resolveMessage(MsgClientUpdate clientUpdate) {
		this.gameState = clientUpdate.getGameUpdate();
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
	 * Prüft ob ein gemachter Zug in einem Spiel gültig war
	 */
	public abstract boolean isValidMove(Card card);
}