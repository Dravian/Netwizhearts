package Ruleset;

/**
 * Modelliert eine Heartskarte
 */
public enum WizardCard implements Card{
	NarrBlau(2,Colour.BLUE),ZauberRot(14,Colour.RED);
	private final int value;
	private final Colour colour;
	
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
