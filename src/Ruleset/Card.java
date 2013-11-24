package Ruleset;

import java.io.Serializable;

/**
 * Card. Dieses Interface modelliert eine Spielkarte
 */
public interface Card {
	
	/**
	 * Gibt den Wert der Karte zurück
	 * @return Der Wert der Karte
	 */
	public int getValue();
	
	/**
	 * Gibt die Farbe der Karte zurück 
	 * @return Die Farbe der Karte
	 */
	public Colour getColour();
	
	/**
	 * Gibt den Spieltyp der Karte zurück
	 * @return
	 */
	public RulesetType getRuleset();
}
