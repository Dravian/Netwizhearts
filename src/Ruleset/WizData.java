/**
 * 
 */
package Ruleset;

/** 
 * Die Otherdata eines Spielers zum Spiel Wizard
 */
public class WizData extends OtherData {
	/** 
	 * Die angesagten Stiche eines Spielers
	 */
	private int announcedTricks;

	/**
	 * Erstellt die OtherData eines Spielers zum Spiel Wizard
	 * @param name Der Name des Spielers
	 */
	protected WizData() {
		super();
		this.announcedTricks = 0;
	}
	
	/** 
	 * Holt die angesagten Stiche des Spielers
	 * @return announcedTricks Die angesagten Stiche
	 */
	protected int getAnnouncedTricks() {
		return this.announcedTricks;
	}
	
	/**
	 * Beim Spielstart werden die vorausgesagten Stiche des Spieler gespeichert
	 * @param annouceTricks Die vorausgesagten Stiche des Spielers
	 */
	protected void setAnnounceTricks(int annouceTricks) {
		this.announcedTricks = annouceTricks;
	}

	@Override
	public String toString() {
		StringBuilder otherData = new StringBuilder();
		otherData.append("Points: " + Integer.toString((this.getPoints())));
		otherData.append("Number of Tricks: " + Integer.toString(this.getNumberOfTricks()));
		otherData.append("Announced Tricks: " + Integer.toString(this.getAnnouncedTricks()));
		
		return otherData.toString();
	}
}