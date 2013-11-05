/**
 * 
 */
package Ruleset;

/** 
 * Repräsentiert eine Wizardkarte
 */
public class WizardCard extends Card {
	/** 
	 *Die eindeutige ID der Karte
	 */
	private WizID id;
	
	/**
	 * Erstellt eine Wizardkarte
	 * @param value Der Wert der Karte
	 * @param colour Die Farbe der Karte
	 */
	WizardCard(int value, Colour colour) {
		super(value, colour);
		setID(value,colour);
	}
	
	/**
	 * Bestimmt die id der Karte aus seinem Wert und seiner Farbe 
	 * @param value Der Wert der Karte
	 * @param colour Die Farbe der Karte
	 */
	private void setID(int value, Colour colour) {
		
	}
	
}