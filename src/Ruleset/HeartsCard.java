package Ruleset;

/**
 * HeartsCard. Modelliert eine Heartskarte.
 */
public enum HeartsCard implements Card{
	Empty(0,Colour.NONE),Herz2(0,Colour.HEART),Caro3(3,Colour.DIAMOND);
	
	/**
	 * Der Wert einer Karte
	 */
	private final int value;
	
	/**
	 * Die Farbe einer Karte
	 */
	private final Colour colour;
	
	/**
	 * Erzeugt eine Heartskarte mit einem Wert und einer Farbe
	 * @param value Der Wert der Karte
	 * @param colour Die Farbe der Karte
	 */
	private HeartsCard(int value, Colour colour) {
		this.value = value;
		this.colour = colour;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public Colour getColour() {
		return colour;
	}

}
