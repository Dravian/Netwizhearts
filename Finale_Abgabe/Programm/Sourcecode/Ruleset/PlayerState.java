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
	 * Der Spieltyp in dem sich der Spieler befindet
	 */
	private RulesetType ruleset;
	
	/**
	 * Erstellt einen Spielerzustand
	 * @param name Der Name vom Spieler
	 * @param ruleset Das Spiel das er spielt
	 * @throws IllegalArgumentException falls der Regelwerk Typ nicht existiert
	 */
	public PlayerState(String name,RulesetType ruleset) throws IllegalArgumentException{
		this.name = name;
		this.ruleset = ruleset;
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
	 * Leer die Kartenhand
	 */
	protected void emptyHand() {
		ownHand = new ArrayList<Card>();
	}
	
	/**
	 * Gibt die OtherData des Spielers zurück
	 * @return otherData Die OtherData eines Spielers
	 */
	protected OtherData getOtherData() {
		return otherData;
	}
	
	/**
	 * Setzt einen neuen OtherData
	 * @param data Der neue Otherdata
	 */
	private void setOtherData(OtherData data) {
		otherData = data;
	}
	
	/**
	 * Kopiert den aktuellen PlayerState
	 * @return Den aktuellen PlayerState
	 */
	protected PlayerState clone() {
		PlayerState copy = new PlayerState(name, ruleset);
		copy.getHand().addAll(ownHand);
		copy.setOtherData(otherData.clone());
		return copy;
	}
	
}