/**
 * 
 */
package Ruleset;

/** 
 * Die zusätzlichen Informationen eines Spielers zum Spiel Hearts
 */
public class HeartsData extends OtherData {

	/** 
	 * Der Punktestand eines Spielers im gesamten Spiel
	 */
	private int completePoints;
	
	/** 
	 * Der Punktestand eines Spielers in der momentanen Runde
	 */
	private int currentPoints;

	/**
	 * Erstellt die zusätzlichen Informatione eines Spielers zum Spiel Hearts
	 * @param name Der Name des Spielers
	 */
	HeartsData(String name) {
		super(name);
	}
	
	/** 
	 * Holt den gesamten Punktestand eines Spielers
	 * @return Der Gesamtpunktstand eines Spielers
	 */
	public int getCompletePoints() {
		return this.completePoints;
	}

	/** 
	 * Holt den momentanen Punktestand eines Spielers
	 * @return Der momentane Punktestand eines Spielers
	 */
	public int getCurrentPoints() {
		return this.currentPoints;
	}
}