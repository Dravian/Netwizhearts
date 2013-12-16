package Ruleset;

import java.io.Serializable;

/**
 * WizardCard. Modelliert eine Wizardkarte
 */
public enum WizardCard implements Card, Serializable{

	NarrBlau(0,Colour.BLUE), EinsBlau(1,Colour.BLUE), ZweiBlau(2,Colour.BLUE),
	DreiBlau(3,Colour.BLUE), VierBlau(4,Colour.BLUE), FuenfBlau(5,Colour.BLUE),
	SechsBlau(6,Colour.BLUE), SiebenBlau(7,Colour.BLUE), AchtBlau(8,Colour.BLUE),
	NeunBlau(9,Colour.BLUE), ZehnBlau(10,Colour.BLUE), ElfBlau(11,Colour.BLUE),
	ZwoelfBlau(12,Colour.BLUE), DreizehnBlau(13,Colour.BLUE), 
	ZaubererBlau(14,Colour.BLUE),
	
	NarrRot(0,Colour.RED), EinsRot(1,Colour.RED), ZweiRot(2,Colour.RED),
	DreiRot(3,Colour.RED), VierRot(4,Colour.RED), FuenfRot(5,Colour.RED),
	SechsRot(6,Colour.RED), SiebenRot(7,Colour.RED), AchtRot(8,Colour.RED),
	NeunRot(9,Colour.RED), ZehnRot(10,Colour.RED), ElfRot(11,Colour.RED),
	ZwoelfRot(12,Colour.RED), DreizehnRot(13,Colour.RED), 
	ZaubererRot(14,Colour.RED),
	
	NarrGelb(0,Colour.YELLOW), EinsGelb(1,Colour.YELLOW), ZweiGelb(2,Colour.YELLOW),
	DreiGelb(3,Colour.YELLOW), VierGelb(4,Colour.YELLOW), FuenfGelb(5,Colour.YELLOW),
	SechsGelb(6,Colour.YELLOW), SiebenGelb(7,Colour.YELLOW), AchtGelb(8,Colour.YELLOW),
	NeunGelb(9,Colour.YELLOW), ZehnGelb(10,Colour.YELLOW), ElfGelb(11,Colour.YELLOW),
	ZwoelfGelb(12,Colour.YELLOW), DreizehnGelb(13,Colour.YELLOW), 
	ZaubererGelb(14,Colour.YELLOW),
	
	NarrGruen(0,Colour.GREEN), EinsGruen(1,Colour.GREEN), ZweiGruen(2,Colour.GREEN),
	DreiGruen(3,Colour.GREEN), VierGruen(4,Colour.GREEN), FuenfGruen(5,Colour.GREEN),
	SechsGruen(6,Colour.GREEN), SiebenGruen(7,Colour.GREEN), AchtGruen(8,Colour.GREEN),
	NeunGruen(9,Colour.GREEN), ZehnGruen(10,Colour.GREEN), ElfGruen(11,Colour.GREEN),
	ZwoelfGruen(12,Colour.GREEN), DreizehnGruen(13,Colour.GREEN), 
	ZaubererGruen(14,Colour.GREEN);
	
	/**
	 * Der Wert der Wizardkarte
	 */
	private final int value;
	
	/**
	 * Die Farbe der Wizardkarte
	 */
	private Colour colour;
	
	/**
	 * Das Spiel zu dem die Karte geh�rt
	 */
	private final RulesetType ruleset;
	
	/**
	 * Erzeugt eine Wizardkarte
	 * @param value Der Wert der Karte
	 * @param colour Die Farbe der Karte
	 */
	private WizardCard(int value, Colour colour) {
		this.value = value;
		this.colour = colour;
		this.ruleset = RulesetType.Wizard;
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
