/**
 * 
 */
package Ruleset;

/** 
 * OtherData ist abstract und speichert die zus�tzlichen Informationen eines Spielers.
 */
public abstract class OtherData {
	/**
	 * Der Name des Spielers dem die Daten geh�ren
	 */
	private String name;
	
	/**
	 * Erzeugt die zus�tzlichen Daten eines Spielers
	 * @param name Der Name des Spielers dem die Daten geh�ren
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