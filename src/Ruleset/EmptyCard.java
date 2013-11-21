package Ruleset;

/**
 * Eine leere Karte die zu keinem Spiel geh�rt
 */
public enum EmptyCard implements Card{
	Empty(0,Colour.NONE);

	private int value;
	private Colour colour;
	private RulesetType ruleset;
	
	private EmptyCard(int value, Colour colour) {
		this.value = value;
		this.colour = colour;
		ruleset = RulesetType.NONE;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public Colour getColour() {
		return colour;
	}

	@Override
	public RulesetType getRuleset() {
		return ruleset;
	}

}