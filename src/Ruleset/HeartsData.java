/**
 * 
 */
package Ruleset;

import java.io.Serializable;

/**
 * HeartsData. Die Otherdata eines Spielers zum Spiel Hearts
 */
public class HeartsData extends OtherData implements Serializable {
	/**
	 * Die Punkte die man in Hearts in dieser Spielrunde angesammelt hat
	 */
	private int currentPoints;

	/**
	 * Erzeugt ein HeartsData
	 * 
	 * @param name
	 *            Der name des zugehörigen Spielers
	 */
	public HeartsData(String name) {
		super(name, RulesetType.Hearts);
		currentPoints = 0;
	}

	@Override
	public String toString() {
		StringBuilder otherData = new StringBuilder();
		otherData.append(Integer.toString(currentPoints) + " P:");
		otherData.append(Integer.toString((this.getPoints())));

		return otherData.toString();
	}

	/**
	 * Setzt die angesammelten Punkte in dieser Runde
	 */
	protected void setCurrentPoints() {
		currentPoints = 0;
		for (Card card : getMadeTricks()) {
			if (card.getColour() == Colour.SPADE && card.getValue() == 12) {
				currentPoints = currentPoints + 13;

			} else if (card.getColour() == Colour.HEART) {
				currentPoints = currentPoints + 1;
			}
		}

		System.out.println(getOtherDataName() + " Data :" + currentPoints);
	}

	/**
	 * Holt die angesammelten Punkte in dieser Runde
	 * 
	 * @return currentPoints Die Punkte
	 */
	public int getCurrentPoints() {
		return currentPoints;
	}

	@Override
	protected OtherData clone() {
		HeartsData copy = new HeartsData(getOtherDataName());
		copy.setMadeTricks(getMadeTricks());
		copy.setNumberOfTricks(getNumberOfTricks());
		copy.setPoints(getPoints());
		return copy;
	}
}