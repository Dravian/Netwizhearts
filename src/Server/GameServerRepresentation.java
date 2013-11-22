/**
 * 
 */
package Server;

import java.io.Serializable;

import Ruleset.RulesetType;

/**
 * GameServerRepresentation. Dies ist eine Klasse, die Informationen ueber den Zustand 
 * eines Spielservers bereithaelt. 
 * Sie wird dem ComObjekt ComLobbyUpdateGameList angehaengt, um die Spielliste in der 
 * GameLobby aktualisieren zu koennen.
 */
public class GameServerRepresentation implements Serializable{
	/**
	 * Der Benutzername des Spielleiters, der das Spiel erstellt hat
	 */
	private String gameMasterName;
	
	/**
	 * Der Name des Spieles
	 */
	private String name;
	
	/** 
	 * Die maximale Anzahl an Spielern, die dem Spiel beitreten koennen, 
	 * vom Regelwerk abhaengig
	 */
	private int maxPlayers;
	
	/** 
	 * Die Anzahl der Spielern, die dem Spiel beigetreten sind
	 */
	private int currentPlayers;
	
	/**
	 * Welcher Typ von Ruleset in dem Spiel verwendet wird
	 */
	private RulesetType ruleset;
	/** 
	 * Zeigt an, ob ein Spiel Passwortgeschuezt ist
	 */
	private boolean hasPassword;
	
	/**
	 * Der Konstruktor der Klasse GameServerRepresentation initialisiert die 
	 * Attribute mit den vom GameServer übergebenen Werten.
	 * @param gameMaster der Name des Spielleiters
	 * @param gameName der Name des Spiels
	 * @param max Maximal moegliche Anzahl teilnehmender Spieler
	 * @param current Anzahl momentaner Spieler
	 * @param type Welches Ruleset verwendet wird
	 * @param password ob das Spiel ein Passwort hat
	 */
	public GameServerRepresentation(String gameMaster, String gameName, int max, 
			int current, RulesetType type, boolean password){
		gameMasterName = gameMaster;
		name = gameName;
		maxPlayers = max;
		currentPlayers = current;
		ruleset = type;
		hasPassword = password;
	}
	
	/**
	 * Getter-Methode fuer den Benutzernamen des Spielleiters
	 */
	public String getGameMasterName(){
		return gameMasterName;
	}
	
	/**
	 * Getter-Methode fuer den Namen des Spiels
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter-Methode fuer die maximale Anzahl an Spielern
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	/**
	 * Getter-Methode fuer die Momentane Anzahl an Spielern
	 */
	public int getCurrentPlayers() {
		return currentPlayers;
	}
	
	/**
	 * Getter-Methode fuer den Typ von Ruleset
	 */
	public RulesetType getRuleset() {
		return ruleset;
	}
	
	/**
	 * Getter-Methode die zurueckgibt, ob es ein Passwort gibt
	 */
	public boolean hasPassword() {
		return hasPassword;
	}	
}