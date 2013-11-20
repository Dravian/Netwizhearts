/**
 * 
 */
package Ruleset;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** 
 * OtherData. OtherData speichert alle Spielinformationen eines Spielers, 
 * außer seiner eigenen Spielhand.
 */
public abstract class OtherData {
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
	 * Erzeugt die OtherData eines Spielers
	 */
	protected OtherData(String name) {
		this.name = name;
		madeTricks = new HashSet<Card>();
		points = 0;
		numberOfTricks = 0;
	}
	
	
	/**
	 * Gibt dem Spieler die Stichkarten die er gemacht
	 * @param tricks Die Stiche
	 */
	protected void madeTrick(Set<Card> tricks) {
		madeTricks.addAll(tricks);
		numberOfTricks++;
	}
	
	/**
	 * Entfernt die gemachten Stichkarten eines Spielers und fuegt sie wieder in den
	 * Kartenstapel
	 * @return Die Kartenstiche
	 */
	protected List<Card> removeTricks() {
		List<Card> returnedCards = new LinkedList<Card>(madeTricks);
		madeTricks = new HashSet<Card>();
		numberOfTricks = 0;
		return returnedCards;
	}
	
	/**
	 * Gibt die Anzahl der gemachten Stiche des Spielers zurueck
	 * @return Die Anzahl der gemachten Stiche
	 */
	protected int getNumberOfTricks() {
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
	protected int getPoints() {
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
	public String getName() {
		return name;
	}
}