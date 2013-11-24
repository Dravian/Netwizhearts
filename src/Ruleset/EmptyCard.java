package Ruleset;

import java.io.Serializable;

/**
 * Eine leere Karte die zu keinem Spiel gehört
 */
public enum EmptyCard implements Card, Serializable{
	Empty(-1,Colour.NONE);

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
