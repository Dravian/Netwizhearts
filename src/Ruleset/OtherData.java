/**
 * 
 */
package Ruleset;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** 
 * OtherData. OtherData speichert alle Spielinformationen eines Spielers, 
 * au�er seiner eigenen Spielhand.
 */
public abstract class OtherData implements Serializable{
	/**
	 * Die Stiche die ein Spieler in einer Runde gemacht hat
	 */
	private Set<Card> madeTricks;
	
	/**
	 * Der Punktestand des Spielers
	 */
	private int points;
	
	/**
	 * Die Anzahl der gemachten Stiche
	 */
	private int numberOfTricks;
	
	/**
	 * Der Name vom Spieler
	 */
	private String name;
	
	/**
	 * Der Typ dieses OtherData
	 */
	private RulesetType ruleset;
	
	/**
	 * Erzeugt die OtherData eines Spielers
	 */
	protected OtherData(String name, RulesetType ruleset) {
		this.name = name;
		madeTricks = new HashSet<Card>();
		points = 0;
		numberOfTricks = 0;
		this.ruleset = ruleset;
	}
	
	/**
	 * Gibt den RulesetType zurück
	 * @return ruleset
	 */
	protected RulesetType getRuleset() {
		return ruleset;
	}
	
	/**
	 * Gibt dem Spieler die Stichkarten die er gemacht
	 * @param tricks Die Stiche
	 */
	protected void madeTrick(Set<Card> tricks) {
		madeTricks.addAll(tricks);
		numberOfTricks++;
		
		if(ruleset == RulesetType.Hearts) {
			setCurrentPoints();
		}
	}
	
	/**
	 * Entfernt die gemachten Stichkarten eines Spielers und fuegt sie wieder in den
	 * Kartenstapel
	 * @return Die Kartenstiche
	 */
	protected void removeTricks() {
		madeTricks = new HashSet<Card>();
		numberOfTricks = 0;
		
		if(ruleset == RulesetType.Hearts) {
			setCurrentPoints();
		}
	}
	
	/**
	 * Gibt die Anzahl der gemachten Stiche des Spielers zurueck
	 * @return Die Anzahl der gemachten Stiche
	 */
	public int getNumberOfTricks() {
		return numberOfTricks;
	}
	
	/**
	 * Setzt den Punktestand eines Spielers
	 * @param points Der neue Punktestand
	 */
	protected void setPoints(int points) {
		this.points = points;
	}
	/**
	 * Gibt den Punktestand eines Spielers zurück
	 * @return Der Punktestand
	 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * Gibt die Stringrepraesentation der OtherData zurück
	 */
	public abstract String toString();

	/**
	 * Gibt den Namen des Spielers zurück
	 * @return Der Name des Spielers
	 */
	public String getOtherDataName() {
		return name;
	}
	
	/**
	 * Setzt die gemachten Stiche
	 * @param madeTricks Die gemachten Stiche
	 */
	protected void setMadeTricks(Set<Card> madeTricks) {
		this.madeTricks = madeTricks;
		if(ruleset == RulesetType.Hearts) {
			setCurrentPoints();
		}
	}
	
	/**
	 * Holt die gemachten Stiche
	 * @return madeTricks Die gemachten Stiche
	 */
	protected Set<Card> getMadeTricks() {
		return madeTricks;
	}
	
	/**
	 * Setzt die Anzahl der Stiche
	 * @param number Anzahl der Stiche
	 */
	protected void setNumberOfTricks(int number) {
		numberOfTricks = number;
	}
	
	/**
	 * Kopiert diese OtherData
	 * @return Eine Kopie diese OtherData
	 */
	protected abstract OtherData clone();
	
	protected void setCurrentPoints(){
		throw new UnsupportedOperationException("Wird hier nicht verwendet");
	}
	
	public int getCurrentPoints() {
		throw new UnsupportedOperationException("Wird hier nicht verwendet");
	}
	
	/** 
	 * Holt die angesagten Stiche des Spielers
	 * @return announcedTricks Die angesagten Stiche
	 */
	public int getAnnouncedTricks() {
		throw new UnsupportedOperationException("Wird in diesem Ruleset nicht verwendet");
	}


	protected void setAnnouncedTricks(int tricks) {
		throw new UnsupportedOperationException("Wird hier nicht verwendet");
	}
	
}