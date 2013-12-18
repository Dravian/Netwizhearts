package Ruleset;

import java.io.Serializable;

/**
 * HeartsCard. Modelliert eine Heartskarte.
 */
public enum HeartsCard implements Card,Comparable<HeartsCard>, Serializable{

	Herz2(2,Colour.HEART),Herz3(3,Colour.HEART),Herz4(4,Colour.HEART),
	Herz5(5,Colour.HEART), Herz6(6,Colour.HEART),Herz7(7,Colour.HEART),
	Herz8(8,Colour.HEART), Herz9(9,Colour.HEART), Herz10(10,Colour.HEART),
	HerzBube(11,Colour.HEART), HerzDame(12,Colour.HEART), 
	HerzKoenig(13,Colour.HEART), HerzAss(14,Colour.HEART),
	
	Caro2(2,Colour.DIAMOND),Caro3(3,Colour.DIAMOND),Caro4(4,Colour.DIAMOND),
	Caro5(5,Colour.DIAMOND), Caro6(6,Colour.DIAMOND),Caro7(7,Colour.DIAMOND),
	Caro8(8,Colour.DIAMOND), Caro9(9,Colour.DIAMOND), Caro10(10,Colour.DIAMOND),
	CaroBube(11,Colour.DIAMOND), CaroDame(12,Colour.DIAMOND), 
	CaroKoenig(13,Colour.DIAMOND), CaroAss(14,Colour.DIAMOND),

	Pik2(2,Colour.SPADE),Pik3(3,Colour.SPADE), Pik4(4,Colour.SPADE),
	Pik5(5,Colour.SPADE), Pik6(6,Colour.SPADE), Pik7(7,Colour.SPADE),
	Pik8(8,Colour.SPADE), Pik9(9,Colour.SPADE), Pik10(10,Colour.SPADE),
	PikBube(11,Colour.SPADE), PikDame(12,Colour.SPADE), PikKoenig(13,Colour.SPADE),
	PikAss(14,Colour.SPADE),
	
	Kreuz2(2,Colour.CLUB),Kreuz3(3,Colour.CLUB),Kreuz4(4,Colour.CLUB),
	Kreuz5(5,Colour.CLUB), Kreuz6(6,Colour.CLUB),Kreuz7(7,Colour.CLUB),
	Kreuz8(8,Colour.CLUB), Kreuz9(9,Colour.CLUB), Kreuz10(10,Colour.CLUB),
	KreuzBube(11,Colour.CLUB), KreuzDame(12,Colour.CLUB), 
	KreuzKoenig(13,Colour.CLUB), KreuzAss(14,Colour.CLUB);
	
	/**
	 * Der Wert einer Karte
	 */
	private final int value;
	
	/**
	 * Die Farbe einer Karte
	 */
	private final Colour colour;
	
	/**
	 * Das Spiel zu dem die Karte gehört
	 */
	private final RulesetType ruleset;
	
	/**
	 * Erzeugt eine Heartskarte mit einem Wert und einer Farbe
	 * @param value Der Wert der Karte
	 * @param colour Die Farbe der Karte
	 */
	private HeartsCard(int value, Colour colour) {
		this.value = value;
		this.colour = colour;
		this.ruleset = RulesetType.Hearts;
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

	public int compareTo(Card card) {
		if(card.getRuleset() != RulesetType.Hearts) {
			throw new IllegalArgumentException("Falscher Kartenvergleich");
		
		} else if(colour == Colour.CLUB) {
			if(card.getColour() == Colour.CLUB) {
				if(value < card.getValue()) {
					return -1;
				} else {
					return 1;
				}
			} else if(card.getColour() == Colour.DIAMOND) {
				return -1;
			} else if(card.getColour() == Colour.SPADE) {
				return -1;
			} else if(card.getColour() == Colour.HEART) {
				return -1;
			}
		} else if(colour == Colour.DIAMOND) {
			if(card.getColour() == Colour.CLUB) {
				return 1;
			} else if(card.getColour() == Colour.DIAMOND) {
				if(value < card.getValue()) {
					return -1;
				} else {
					return 1;
				}
			} else if(card.getColour() == Colour.SPADE) {
				return -1;
			} else if(card.getColour() == Colour.HEART) {
				return -1;
			}
		} else if(colour == Colour.SPADE) {
			if(card.getColour() == Colour.CLUB) {
				return 1;
			} else if(card.getColour() == Colour.DIAMOND) {
				return 1;
			} else if(card.getColour() == Colour.SPADE) {
				if(value < card.getValue()) {
					return -1;
				} else {
					return 1;
				}
			} else if(card.getColour() == Colour.HEART) {
				return -1;
			}
		} else if(colour == Colour.HEART) {
			if(card.getColour() == Colour.CLUB) {
				return 1;
			} else if(card.getColour() == Colour.DIAMOND) {
				return 1;
			} else if(card.getColour() == Colour.SPADE) {
				return 1;
			} else if(card.getColour() == Colour.HEART) {
				if(value < card.getValue()) {
					return -1;
				} else {
					return 1;
				}
			}
		}
		
		return 0;
	}
}
