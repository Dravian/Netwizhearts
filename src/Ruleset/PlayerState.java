/**
 * 
 */
package Ruleset;

import java.util.Set;

/** 
 * Modelliert den Zustand eines Spielers
 */
public class PlayerState {
	/** 
	 * Der Name des Spielers
	 */
	private String name;
	/** 
	 * Seine aktuelle Kartenhand
	 */
	private Set<Card> ownHand;
	/** 
	 * Seine zusätzlichen Daten
	 */
	private OtherData otherData;

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
	public Set<Card> getHand() {
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
	 */
	public void removeCard(Card card) {
		ownHand.remove(card);
	}
	
}