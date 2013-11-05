/**
 * 
 */
package Ruleset;

/** 
 * Die zusätzlichen Informationen eines Spielers
 */
public abstract class OtherData {
	/**
	 * Der Name des Spielers dem die Daten gehören
	 */
	private String name;
	
	/**
	 * Erzeugt die zusätzlichen Daten eines Spielers
	 * @param name Der Name des Spielers dem die Daten gehören
	 */
	OtherData(String name) {
		this.name = name;
	}
	
	/**
	 * Holt den Namen des Spielers
	 * @return name Der Name des Spielers
	 */
	public String getname() {
		return this.name;
	}
}