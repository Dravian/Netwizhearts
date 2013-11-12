package Ruleset;

/**
 * Dieses Interface modelliert eine Spielkarte
 */
public interface Card {
	
	/**
	 * Gibt den Wert der Karte zur�ck
	 * @return Der Wert der Karte
	 */
	public int getValue();
	
	/**
	 * Gibt die Farbe der Karte zur�ck 
	 * @return Die Farbe der Karte
	 */
	public Colour getColour();
}