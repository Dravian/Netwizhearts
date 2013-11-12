package Ruleset;

/**
 * WizardCard. Modelliert eine Wizardkarte
 */
public enum WizardCard implements Card{
	Empty(0,Colour.NONE),NarrBlau(0,Colour.BLUE),ZaubererRot(14,Colour.RED),
	EinsGruen(1,Colour.GREEN),
	ZweiGruen(2,Colour.GREEN),
	DreiGruen(3,Colour.GREEN), ZweiRot(2,Colour.RED),
	DreiRot(3,Colour.RED), VierRot(4,Colour.RED);
	/**
	 * Der Wert der Wizardkarte
	 */
	private final int value;
	
	/**
	 * Die Farbe der Wizardkarte
	 */
	private final Colour colour;
	
	/**
	 * Erzeugt eine Wizardkarte
	 * @param value Der Wert der Karte
	 * @param colour Die Farbe der Karte
	 */
	private WizardCard(int value, Colour colour) {
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
