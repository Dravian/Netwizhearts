package Ruleset;

/**
 * Modelliert eine Heartskarte,
 */
public enum HeartsCard implements Card{
	Empty(0,Colour.NONE),Herz2(0,Colour.HEART),Caro3(3,Colour.DIAMOND);
	private final int value;
	private final Colour colour;
	
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
