/**
 * 
 */
package Ruleset;

/** 
 * Die Otherdata eines Spielers zum Spiel Hearts
 */
public class HeartsData extends OtherData {

	/**
	 * Erstellt die OtherData eines Spielers zum Spiel Wizard
	 */
	protected HeartsData() {
		super();
	}
	
	@Override
	public String toString() {
		StringBuilder otherData = new StringBuilder();
		otherData.append("Points: " + Integer.toString((this.getPoints())));
		otherData.append("Number of Tricks: " + Integer.toString(this.getNumberOfTricks()));
		
		return otherData.toString();
	}
}