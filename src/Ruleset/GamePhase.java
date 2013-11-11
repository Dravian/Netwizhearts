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
	 * Der Zustand in dem auf eine Karteneingabe des Benutzers gewartet wird
	 */
	CardRequest,
	/**
	 * Der Zustand in dem auf eine Kartenvergabe des Benutzers gewartet wird
	 */
	MultipleCardRequest,
	/**
	 * Der Zustand in dem auf die Stichanzahl des Benutzers gewartet wird
	 */
	TrickRequest,
	/**
	 * Der Zustand in dem auf eine Farbauswahl vom Benutzer gewartet wird
	 */
	SelectionRequest,
	/** 
	 * Der Zustand bei Spielende
	 */
	Ending
}