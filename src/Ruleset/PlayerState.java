/**
 * 
 */
package Ruleset;

import java.util.ArrayList;
import java.util.List;

/** 
 * Repräsentiert den Spielzustand eines Spielers, und wird unter anderem 
 * im GameState gespeichert. Sie enthält den Namen des Spielers, seine Handkarten
 * und OtherData.
 */
public class PlayerState {
	/** 
	 * Der Name des Spielers
	 */
	private String name;
	/** 
	 * Seine aktuelle Kartenhand
	 */
	private List<Card> ownHand;
	/** 
	 * Seine OtherData
	 */
	private OtherData otherData;
	
	/**
	 * Erstellt einen PlayerState 
	 * @param name Der Name des Spielers
	 * @param ruleset Der Typ des Spiels
	 */
	public PlayerState(String name,RulesetType ruleset) {
		this.name = name;
		
		this.ownHand = new ArrayList<Card>();
		
		if(ruleset == RulesetType.Hearts) {
			otherData = new HeartsData();
		} else {
			otherData = new WizData();
		}
	}

	/** 
	 * Holt den Namen eines Spielers
	 * @return name Der Name des Spielers
	 */
	protected String getName() {
		return this.name;
	}

	/** 
	 * Holt die Kartenhand des Spielers
	 * @return ownHand Die Kartenhand des Spielers
	 */
	protected List<Card> getHand() {
		return this.ownHand;
	}
	
	/**
	 * Gibt die OtherData des Spielers zurück
	 * @return otherData Die OtherData eines Spielers
	 */
	protected OtherData getOtherData() {
		return otherData;
	}
	
	/**
	 * Gibt dem Spieler eine Karte 
	 * @param card Die Karte die dem Spieler gegeben wird
	 */
	protected void addCard(Card card){
		this.ownHand.add(card);
	}
	
	/**
	 * Entfernt eine Karte aus der Hand des Spielers 
	 * @param card
	 * @return ownHand.remove(card) Gibt true zurück wenn die Karte in der Hand ist
	 * 								und false sonst
	 */
	protected boolean removeCard(Card card) {
		 return ownHand.remove(card);
	}
	
}