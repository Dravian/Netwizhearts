/**
 * 
 */
package Ruleset;

/** 
 * Repräsentiert eine Heartskarte
 */
public class HeartsCard extends Card {
	/** 
	 * Die eindeutige Id der Karte
	 */
	private HeartsID id;
	
	/** 
	 * Die Punkte die eine Karte wert ist im Spiel Hearts
	 */
	private int points;

	/**
	 * Erstellt eine Heartskarte
	 * @param value Der Wert der Karte
	 * @param colour Die Farbe der Karte
	 */
	HeartsCard(int value, Colour colour) {
		super(value, colour);
		setID(value,colour);
		setPoints(value,colour);
	}
	
	/**
	 * Bestimmt die id der Karte aus seinem Wert und seiner Farbe 
	 * @param value Der Wert der Karte
	 * @param colour Die Farbe der Karte
	 */
	private void setID(int value, Colour colour) {
		
	}
	
	/**
	 * Bestimmt die Punkte der Karte aus seinem Wert und seiner Farbe
	 * @param value Der Wert der Karte 
	 * @param colour Die Farbe der Karte
	 */
	private void setPoints(int value, Colour colour) {
		int queen = 13;
		if((value == queen) && (colour == Colour.SPADE)) {
			this.points = 13;
		} else if(colour == Colour.HEART) {
			this.points = 1;
		} else {
			this.points = 0;
		}
	}
	/** 
	 * Holt die Punkte der Karte
	 * @return points Die Punkte der Karte
	 */
	public int getPoints() {
		return this.points;
	}
	
	/** 
	 * Holt die ID der Karte
	 * @return points Die ID der Karte
	 */
	public HeartsID getID() {
		return this.id;
	}
}