/**
 * 
 */
package Ruleset;

/** 
 * Diese Klasse modelliert eine Spielkarte. Jede Karte besitzt als Attribute einen Wert und eine Farbe.
 */
public class Card {
	/** 
	 * Repräsentiert den logischen Wert den die Spielkarte hat.
	 */
	private int value;
	
	/**
	 * Repräsentiert die Farbe der Karte
	 */
	private Colour colour;
	
	/**
	 * Erzeugt eine Karte
	 * @param value Der logische Wert der Karte
	 * @param colour Die Farbe der Karte
	 */
	Card(int value, Colour colour) {
		this.value = value;
		this.colour = colour;
	}
	
	/**
	 * Erstellt eine Karte mit der WizardID
	 * @param id WizardID
	 */
	Card(WizID id) {
		this.value = getIDValue(id);
		this.colour = getIDColour(id);
	}
	
	/**
	 * Erstellt eine Karte mit der HeartsID
	 * @param id HeartsID
	 */
	Card(HeartsID id) {
		this.value = getIDValue(id);
		this.colour = getIDColour(id);
	}

	/**
	 * Holt den Wert einer Wizardkarte aufgrund seiner ID zurück
	 * @param id Die WizardID
	 * @return Gibt den Wert zurück
	 */
	protected int getIDValue(WizID id) {
		return 0;
	}
	/**
	 * Holt den Wert einer Heartskarte aufgrund seiner ID zurück
	 * @param id Die HeartsID
	 * @return Gibt den Wert zurück
	 */
	protected int getIDValue(HeartsID id) {
		return 0;
	}
	/**
	 * Holt die Farbe einer Wizardkarte aufgrund seiner ID zurück
	 * @param id Die WizardID
	 * @return Gibt die Farbe zurück
	 */
	protected Colour getIDColour(WizID id) {
		return Colour.BLUE;
	}
	/**
	 * Holt die Farbe einer Heartskarte aufgrund seiner ID zurück
	 * @param id Die HeartsID
	 * @return Gibt die Farbe zurück
	 */
	protected Colour getIDColour(HeartsID id) {
		return Colour.RED;
	}
	
	
	/** 
	 * Holt den logischen Wert der Karte
	 * @return Der logische Wert der Karte
	 */
	public int getValue() {
		return this.value;
	}
	
	/** 
	 * Holt die Farbe der Karte
	 * @return Die Farbe der Karte
	 */
	public Colour getColour() {
		return this.colour;
	}
}