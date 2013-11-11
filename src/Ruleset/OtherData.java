/**
 * 
 */
package Ruleset;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** 
 * OtherData speichert alle Spielinformationen eines Spielers, 
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
	 * Erzeugt die OtherData eines Spielers
	 */
	protected OtherData() {
	}
	
	/**
	 * Gibt dem Spieler die Stichkarten die er gemacht
	 * @param tricks Die Stiche
	 */
	protected void madeTrick(Set<Card> tricks) {
		
	}
	
	/**
	 * Entfernt die gemachten Stichkarten eines Spielers und fügt sie wieder in den
	 * Kartenstapel
	 * @return Die Kartenstiche
	 */
	protected List<Card> removeTricks() {
		List<Card> returnedCards = new LinkedList<Card>(madeTricks);
		return returnedCards;
	}
	
	/**
	 * Gibt die Anzahl der gemachten Stiche des Spielers zurück
	 * @return Die Anzahl der gemachten Stiche
	 */
	protected int getNumberOfTricks() {
		return 0;
	}
	
	/**
	 * Setzt den Punktestand eines Spielers
	 */
	protected void setPoints() {
		
	}
	/**
	 * Gibt den Punktestand eines Spielers zurück
	 * @return Der Punktestand
	 */
	protected int getPoints() {
		return points;
	}
	
	/**
	 * Gibt die Stringrepräsentation der OtherData zurück
	 */
	public abstract String toString();
}