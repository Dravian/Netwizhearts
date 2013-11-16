package Ruleset;

/**
 * Wird geworfen wenn eine falsche Farbe in Wizard übergeben wird.
 */
public class IllegalColourException extends RulesetException {
	Colour colour;
	
	public IllegalColourException(Colour colour){
		super();
		this.colour = colour;
	}
	
	public Colour getColour(){
		return colour;
	}
}
