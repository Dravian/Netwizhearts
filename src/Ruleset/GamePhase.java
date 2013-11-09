/**
 * 
 */
package Ruleset;

/** 
 * Die GamePhase modelliert die verschiedenen Zustände des Spiels im GameState
 */
public enum GamePhase {
	/** 
	 * Der Anfangszustand
	 */
	Start,
	/** 
	 * Der Zustand in dem gerade Berechnungen zum Spiel gemacht werden
	 */
	Playing,
	/** 
	 * Der Zustand in dem auf eine Eingabe eines Benutzers gewartet wird
	 */
	WaitingForInput,
	/** 
	 * Der Zustand bei Spielende
	 */
	Ending
}