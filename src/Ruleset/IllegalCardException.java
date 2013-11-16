package Ruleset;

/**
 * Diese Exception wird geworfen wenn eine Karte gespielt wird, die nicht zum Spiel
 * gehört.
 */
public class IllegalCardException extends RulesetException {
	private Card card;
	
	public IllegalCardException(Card card) {
		super();
		this.card = card;
	}
	
	public Card getCard() {
		return card;
	}
}
