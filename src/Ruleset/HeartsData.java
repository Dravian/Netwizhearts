/**
 * 
 */
package Ruleset;

import java.io.Serializable;

/** 
 * HeartsData. Die Otherdata eines Spielers zum Spiel Hearts
 */
public class HeartsData extends OtherData implements Serializable {
	
	public HeartsData(String name) {
		super(name);
	}

	@Override
	public String toString() {
		StringBuilder otherData = new StringBuilder();
		otherData.append("Name :" + getOtherDataName() + "");
		otherData.append("Points: " + Integer.toString((this.getPoints())) + "");
		otherData.append("Number of Tricks: " + Integer.toString(this.getNumberOfTricks()));
		
		return otherData.toString();
	}
}