/**
 * 
 */
package Ruleset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * PlayerState. Repraesentiert den Spielzustand eines Spielers, und wird unter anderem 
 * im GameState gespeichert. Sie enth�lt den Namen des Spielers, seine Handkarten
 * und OtherData.
 */
public class PlayerState implements Serializable{
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
	 * Erstellt einen Spielerzustand
	 * @param name Der Name vom Spieler
	 * @param ruleset Das Spiel das er spielt
	 * @throws IllegalArgumentException falls der Regelwerk Typ nicht existiert
	 */
	public PlayerState(String name,RulesetType ruleset) throws IllegalArgumentException{
		this.name = name;
		
		this.ownHand = new ArrayList<Card>();
		
		if(ruleset == RulesetType.Hearts) {
			otherData = new HeartsData(name);
		} else if(ruleset == RulesetType.Wizard){
			otherData = new WizData(name);
		} else {
			throw new IllegalArgumentException("Regelwerk " + 
		ruleset + "existiert nicht.");
		}
	}

	/** 
	 * Holt den Namen eines Spielers
	 * @return name Der Name des Spielers
	 */
	protected String getPlayerStateName() {
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
		ownHand.add(card);
	}
	
	/**
	 * Entfernt eine Karte aus der Hand des Spielers 
	 * @param card
	 * @return Gibt true zurueck wenn die Karte in der Hand ist
	 * 								und false sonst
	 */
	protected boolean removeCard(Card card) {
		 return ownHand.remove(card);
	}
	
}