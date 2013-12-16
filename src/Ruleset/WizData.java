/**
 * 
 */
package Ruleset;

import java.io.Serializable;

/** 
 * WizData. Die Otherdata eines Spielers zum Spiel Wizard
 */
public class WizData extends OtherData implements Serializable{
	/** 
	 * Die angesagten Stiche eines Spielers
	 */
	private int announcedTricks;

	/**
	 * Erstellt die OtherData eines Spielers zum Spiel Wizard
	 */
	protected WizData(String name) {
		super(name);
		this.announcedTricks = 0;
	}
	
	@Override
	public int getAnnouncedTricks() {
		return this.announcedTricks;
	}
	
	/**
	 * Beim Spielstart werden die vorausgesagten Stiche des Spieler gespeichert
	 * @param annouceTricks Die vorausgesagten Stiche des Spielers
	 */
	protected void setAnnouncedTricks(int annouceTricks) {
		this.announcedTricks = annouceTricks;
	}

	
	@Override
	public String toString() {
		StringBuilder otherData = new StringBuilder();
		otherData.append(Integer.toString(this.getNumberOfTricks()) + "/");
		otherData.append(Integer.toString(this.getAnnouncedTricks()) + " P:");
		otherData.append(Integer.toString((this.getPoints())));
		
		return otherData.toString();
	}
	
	@Override
	protected OtherData clone() {
		WizData copy = new WizData(getOtherDataName());
		copy.setMadeTricks(getMadeTricks());
		copy.setNumberOfTricks(getNumberOfTricks());
		copy.setPoints(getPoints());
		copy.setAnnouncedTricks(announcedTricks);
		return copy;
	}
}