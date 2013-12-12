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
		otherData.append(Integer.toString(this.getNumberOfTricks()) + ", ");
		otherData.append(Integer.toString((this.getPoints())));
		
		return otherData.toString();
	}
}