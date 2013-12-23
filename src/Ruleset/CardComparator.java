package Ruleset;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Sortiert die Spielkarten in einer angenehmen Reihenfolge
 */
public class CardComparator implements Comparator<Card>, Serializable {
	private Colour trumpColour;

	public CardComparator(Colour trumpColour) {
		super();
		this.trumpColour = trumpColour;
	}

	@Override
	public int compare(Card card1, Card card2) {

		// Sortiert Hearts
		if (card1.getRuleset() == RulesetType.Hearts
				&& card2.getRuleset() == RulesetType.Hearts) {
			if (card1.getColour() == Colour.CLUB) {
				if (card2.getColour() == Colour.CLUB) {
					if (card1.getValue() < card2.getValue()) {
						return -1;
					} else {
						return 1;
					}
				} else if (card2.getColour() == Colour.DIAMOND) {
					return -1;
				} else if (card2.getColour() == Colour.SPADE) {
					return -1;
				} else if (card2.getColour() == Colour.HEART) {
					return -1;
				}
			} else if (card1.getColour() == Colour.DIAMOND) {
				if (card2.getColour() == Colour.CLUB) {
					return 1;
				} else if (card2.getColour() == Colour.DIAMOND) {
					if (card1.getValue() < card2.getValue()) {
						return -1;
					} else {
						return 1;
					}
				} else if (card2.getColour() == Colour.SPADE) {
					return -1;
				} else if (card2.getColour() == Colour.HEART) {
					return -1;
				}
			} else if (card1.getColour() == Colour.SPADE) {
				if (card2.getColour() == Colour.CLUB) {
					return 1;
				} else if (card2.getColour() == Colour.DIAMOND) {
					return 1;
				} else if (card2.getColour() == Colour.SPADE) {
					if (card1.getValue() < card2.getValue()) {
						return -1;
					} else {
						return 1;
					}
				} else if (card2.getColour() == Colour.HEART) {
					return -1;
				}
			} else if (card1.getColour() == Colour.HEART) {
				if (card2.getColour() == Colour.CLUB) {
					return 1;
				} else if (card2.getColour() == Colour.DIAMOND) {
					return 1;
				} else if (card2.getColour() == Colour.SPADE) {
					return 1;
				} else if (card2.getColour() == Colour.HEART) {
					if (card1.getValue() < card2.getValue()) {
						return -1;
					} else {
						return 1;
					}
				}
			}
		} 

		
		
		// Sortiert Wizard
		if (card1.getRuleset() == RulesetType.Wizard
				&& card2.getRuleset() == RulesetType.Wizard) {

			if (card1.getValue() == 14 && card2.getValue() < 14) {
				return 1;
			} else if (card1.getValue() < 14 && card2.getValue() == 14) {
				return -1;
			} else if(card1.getValue() == 14 && card2.getValue() == 14) {
				return 0;
			}

			
			if (card1.getValue() == 0 && card2.getValue() > 0) {
				return -1;
			} else if (card1.getValue() > 0 && card2.getValue() == 0) {
				return 1;

			} else if(card1.getValue() == 0 && card2.getValue() == 0) {
				return 0;
			}
			
			
			if (trumpColour == Colour.BLUE
					|| trumpColour == Colour.GREEN || trumpColour == Colour.RED
					|| trumpColour == Colour.YELLOW) {

				if (card1.getColour() == trumpColour
						&& card2.getColour() == trumpColour) {
					if (card1.getValue() > card2.getValue()) {
						return 1;
					}  else if (card1.getValue() < card2.getValue()) {
						return -1;
					}

				} else if (card1.getColour() == trumpColour
						&& card2.getColour() != trumpColour) {
					return 1;

				} else if (card1.getColour() != trumpColour
						&& card2.getColour() == trumpColour) {
					return -1;

				}
			} 
			
			
			if (card1.getColour() == Colour.BLUE) {

				if (card2.getColour() != Colour.BLUE) {
					return -1;

				} else if (card1.getValue() > card2.getValue()) {
					return 1;
				} else if (card1.getValue() < card2.getValue()) {
					return -1;
				}

			} else if (card1.getColour() == Colour.GREEN) {

				if (card2.getColour() == Colour.BLUE) {
					return 1;
				} else if (card2.getColour() != Colour.GREEN) {
					return -1;

				} else if (card1.getValue() > card2.getValue()) {
					return 1;
				}  else if (card1.getValue() < card2.getValue()) {
					return -1;
				}

			} else if (card1.getColour() == Colour.RED) {

				if (card2.getColour() == Colour.YELLOW) {
					return -1;
				} else if (card2.getColour() != Colour.RED) {
					return 1;
				
				} else if (card1.getValue() > card2.getValue()) {
					return 1;
				} else if (card1.getValue() < card2.getValue()) {
					return -1;
				}
			
			} else if (card1.getColour() == Colour.YELLOW) {

				if (card2.getColour() != Colour.YELLOW) {
					return 1;
				} else if (card1.getValue() > card2.getValue()) {
					return 1;
				} else if (card1.getValue() < card2.getValue()) {
					return -1;
				}
			}
		}

		return 0;
	}

}
