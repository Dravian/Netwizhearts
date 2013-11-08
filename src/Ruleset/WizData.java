/**
 * 
 */
package Ruleset;

/** 
 * Die zusätzlichen Informationen eines Spielers zum Spiel Wizard
 */
public class WizData extends OtherData {
	/** 
	 * Die angesagten Stiche eines Spielers
	 */
	private int announcedTricks;
	
	/** 
	 * Die gemachten Stiche eines Spielers
	 */
	private int achievedTricks;
	
	/**
	 * Der Punktestand eines Spielers
	 */
	private int points;

	/**
	 * Erstellt die zusätzlichen Informationen eines Spielers zum Spiel Wizard
	 * @param name Der Name des Spielers
	 */
	WizData(String name) {
		super(name);
		this.announcedTricks = 0;
		this.achievedTricks = 0;
	}
	
	/** 
	 * Holt die angesagten Stiche des Spielers
	 * @return announcedTricks Die angesagten Stiche
	 */
	public int getAnnouncedTricks() {
		return this.announcedTricks;
	}

	/** 
	 * Holt die erreichten Stiche des Spielers
	 * @return achievedTricks Die gemachten Stiche eines Spielers
	 */
	public int getAchievedTricks() {
		return this.achievedTricks;
	}
	
	/** 
	 * Holt den Punktestand des Spielers
	 * @return points Der Punktestand des Spielers
	 */
	public int getPoints() {
		return this.points;
	}
	
	/**
	 * Beim Spielstart werden die vorausgesagten Stiche des Spieler gespeichert und die gemachten Stiche zurückgesetzt
	 * @param annouceTricks Die vorausgesagten Stiche des Spielers
	 */
	public void announceTricks(int annouceTricks) {
		this.announcedTricks = annouceTricks;
		achievedTricks = 0;
	}
	
	/**
	 * Wenn ein Spieler einen Stich macht, werden die gemachten Stiche um eins erhöht
	 */
	public void madeTrick(){
		this.achievedTricks++;
	}
	
	/**
	 * Setzt den Punktestand eines Spielers
	 * @param points Der Punktestand eines Spielers
	 */
	public void setPoints(int points){
		this.points = points;
	}
}