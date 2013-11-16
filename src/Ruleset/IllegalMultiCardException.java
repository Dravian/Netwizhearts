package Ruleset;

import java.util.Set;

public class IllegalMultiCardException extends RulesetException{
	private Set<Card> cards;
	
	public IllegalMultiCardException(Set<Card> cards) {
		super();
		this.cards = cards;
	}
	
	public Set<Card> getCards() {
		return cards;
	}
}
