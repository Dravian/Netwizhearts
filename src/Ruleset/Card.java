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