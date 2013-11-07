/**
 * 
 */
package Ruleset;

import java.util.ArrayList;
import java.util.List;

/** 
 * Repräsentiert den Spielzustand eines Spielers, und wird unter anderem 
 * im GameState gespeichert. Sie enthält den Namen des Spielers, seine Handkarten und OtherData.
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
	 * Seine zusätzlichen Daten
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
		switch(ruleset) {
		case Wizard: this.otherData = new WizData(name);
					 break;
		case Hearts: this.otherData = new HeartsData(name);
					 break;
		default:
				break;
		}
	}

	/** 
	 * Holt den namen eines Spielers
	 * @return name Der Name des Spielers
	 */
	public String getName() {
		return this.name;
	}

	/** 
	 * Holt die Kartenhand des Spielers
	 * @return ownHand Die Kartenhand des Spielers
	 */
	public List<Card> getHand() {
		return this.ownHand;
	}
	
	/** 
	 * Holt die zusätzlichen Informationen des Spielers
	 * @return ownHand Die zusätzlichen Informationen des Spielers
	 */
	public OtherData getOtherData() {
		return this.otherData;
	}
	
	/**
	 * Gibt dem Spieler eine Karte 
	 * @param card Die Karte die dem Spieler gegeben wird
	 */
	public void addCard(Card card){
		this.ownHand.add(card);
	}
	
	/**
	 * Entfernt eine Karte aus der Hand des Spielers 
	 * @param card
	 * @return ownHand.remove(card) Gibt true zurück wenn die Karte in der Hand ist
	 * 								und false sonst
	 */
	public boolean removeCard(Card card) {
		 return ownHand.remove(card);
	}
	
}