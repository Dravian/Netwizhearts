/**
 * 
 */
package Server;

import Ruleset.RulesetType;

/**
 * Dies eine Klasse, die Informationen �ber den Zustand eines Spielservers bereith�lt. 
 * Sie wird dem ComObjekt ComLobbyUpdateGameList angeh�ngt, um die Spielliste in der 
 * GameLobby aktualisieren zu k�nnen
 * @author Viktoria
 *
 */
public class GameServerRepresentation {
	/**
	 * Der Benutzername des Spielleiters, der das Spiel erstellt hat
	 */
	private String gameMasterName;
	
	/**
	 * Der Name des Spieles
	 */
	private String name;
	
	/** 
	 * Die maximale Anzahl an Spielern, die dem Spiel beitreten k�nnen, 
	 * vom Regelwerk abh�ngig
	 */
	private int maxPlayers;
	
	/** 
	 * Die Anzahl der Spielern, die dem Spiel beitreten sind
	 */
	private int currentPlayers;
	
	/**
	 * Welcher Typ von Ruleset in dem Spiel verwendet wird
	 */
	private RulesetType ruleset;
	/** 
	 * Zeigt an, ob ein Spiel Passwortgesch�zt ist
	 */
	private boolean hasPassword;
	
	/**
	 * Der Konstruktor der Klasse GameServerRepresentation initialisiert die Attribute mit den
	 * vom GameServer �bergebenen Werten.
	 * @param gameMaster der Name des Spielleiters
	 * @param gameName der Name des Spiels
	 * @param max Maximal m�gliche Anzahl teilnehmender Spieler
	 * @param current Anzahl momentaner Spieler
	 * @param type Welches Ruleset verwendet wird
	 * @param password ob das Spiel ein Passwort hat
	 */
	public GameServerRepresentation(String gameMaster, String gameName, int max, int current, RulesetType type, boolean password){
		
	}
	
	/**
	 * Getter-Methode f�r den Benutzernamen des Spielleiters
	 */
	public String getGameMasterName(){
		return gameMasterName;
	}
	
	/**
	 * Setter-Methode f�r den Benutzernamen des Spielleiters
	 */
	public void setGameMasterName(String gameMasterName) {
		this.gameMasterName = gameMasterName;
	}
	
	/**
	 * Getter-Methode f�r den Namen des Spiels
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter-Methode f�r den Namen des Spiels
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter-Methode f�r die maximale Anzahl an Spielern
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	/**
	 * Setter-Methode f�r die maximale Anzahl an Spielern
	 */
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	
	/**
	 * Getter-Methode f�r die Momentane Anzahl an Spielern
	 */
	public int getCurrentPlayers() {
		return currentPlayers;
	}
	
	/**
	 * Setter-Methode f�r die Momentane Anzahl an Spielern
	 */
	public void setCurrentPlayers(int currentPlayers) {
		this.currentPlayers = currentPlayers;
	}
	
	/**
	 * Getter-Methode f�r den Typ von Ruleset
	 */
	public RulesetType getRuleset() {
		return ruleset;
	}
	
	/**
	 * Setter-Methode f�r den Typ von Ruleset
	 */
	public void setRuleset(RulesetType ruleset) {
		this.ruleset = ruleset;
	}
	
	/**
	 * Getter-Methode die zur�ckgibt, ob es ein Passwort gibt
	 */
	public boolean isHasPassword() {
		return hasPassword;
	}
	
	/**
	 * Setter-Methode die zur�ckgibt, ob es ein Passwort gibt
	 */
	public void setHasPassword(boolean hasPassword) {
		this.hasPassword = hasPassword;
	}

}