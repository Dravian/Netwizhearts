/**
 * 
 */
package Server;

import static Server.GameServerRepresentation.*;
import ComObjects.*;
import Ruleset.RulesetType;
import Ruleset.ServerRuleset;

/**
 * Diese Klasse ist f�r die Spielverwaltung zust�ndig. Sie verwaltet die Kommunikation zwischen den 
 * Clients w�hrend eines Spieles. Die GameServer-Klasse erbt Methoden zur Kommunikation vom Server. 
 * Der GameServer tauscht Nachrichten zwischen Ruleset und Player aus, um so den Spielablauf zu koordinieren.
 * @author Viktoria
 *
 */
public class GameServer extends Server {
	/**
	 * Der Benutzername des Spielleiters, der das Spiel erstellt hat
	 */
	private String gameMasterName;
	
	/**
	 * Der Name des Spieles
	 */
	private String name;
	
	/**
	 * Das Passwort, das der Spielleiter beim erstellen gesetzt hat.
	 * Ist NULL, falls es kein Passwort gibt.
	 */
	private String password;
	
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
	 * Zeigt an, ob ein Spiel Passwortgesch�zt ist
	 */
	private boolean hasPassword;
	
	/** 
	 * Zeigt, welches Ruleset verwendet wird
	 */
	private RulesetType rulesetType;
	
	/** 
	 * Das Ruleset, das f�r den ordnungsgerechten Ablauf eines Spiels ust�ndig ist
	 */
	private ServerRuleset ruleset;
	
	/**
	 * Konstruktor des GameServers. Setzt die Attribute name,password,hasPasword und rulesetType auf die
	 * �bergebenen Werte. Setzt den gameMasterName auf den Namen des gameMaster und 
	 * f�gt den gameMaster dem Set an Spielern hinzu.  
	 * Bestimmt mithilfe des Enums RulesetType das Ruleset und erstellt es.
	 * Setzt currentPlayers auf eins und maxPlayers je nach Ruleset.
	 * @param gameMaster ist der Name des Spielleiters
	 * @param GameName ist der Name des Spiels
	 * @param ruleset gibt an, welches Ruleset verwendet wird
	 * @param password speichert das Passwort des Spiels
	 * @param hasPassword gibt an,ob das Spiel ein Passwort hat
	 */
	public GameServer(Player gameMaster, String GameName, RulesetType ruleset, String password, boolean hasPassword) {
		// begin-user-code
		// TODO Auto-generated constructor stub
		// end-user-code
	}
	
	/** 
	 * Erstellt eine neue GameServerRepresentation und gibt sie zur�ck
	 */
	public GameServerRepresentation getRepresentation() {
		return null;
		// TODO Auto-generated method stub

		// end-user-code
	}
	
	/**
	 * Diese Methode wird vom abstrakten Server vererbt.
	 * Zus�tzlich wird die Zahl der currentPlayers um eins Erh�ht.
	 * @param player ist der Player, der hinzugefo�gt wird
	 */
	public void addPlayer(Player player) {

	}
	/**
	 * Diese Methode wird vom abstrakten Server vererbt.
	 * Zus�tzlich wird die Zahl der currentPlayers um eins Verringert.
	 * @param player ist der Player, der entfernt wird
	 */
	public void removePlayer(Player player) {

	}
	
	/**
	 * Diese Methode wird vom abstrakten Server vererbt.
	 * Sie ist dafur zust�ndig zu ermitteln, was passiert wenn 
	 * ein Spieler aus der GameLobby geworfen wird.
	 * An diesen Spieler wird ein ComWarning geschickt und er wird an die Lobby zur�ckgegeben.
	 * ---------
	 * ?! Problem !? Wie wird dem LobbyServer gesagt, dass er ein 
	 * UpdatePlayerlist schicken muss?? Wie sagt man dem Player, dass er
	 * auf den LobbyServer wechseln soll?
	 * ---------
	 * Danach wird er aus dem playerSet entfernt und es wird ein 
	 * ComUpdatePlayerlist Objekt mit broadcast an alle Client im Spiel verschickt.
	 * @param player ist der Threat der die Nachricht erhalten hat
	 * @param kicked ist das ComObject, das verarbeitet wird
	 */
	public void receiveMessage(Player player, ComKickPlayerRequest kickPlayer){

	}
	
	/**
	 * Diese Methode wird vom abstrakten Server vererbt.
	 * Sie ist dafur zust�ndig eine Chatnachricht an alle Clients im
	 * Spiel zu verschicken. Daf�r wird die ComChatMessage mit broadcast
	 * an alle Spieler im playerSet verteilt.
	 * @param player ist der Threat der die Nachricht erhalten hat
	 * @param chat ist das ComObject, das die Chatnachricht enth�lt
	 */
	public void receiveMessage(Player player, ComChatMessage chat){

	}
	/**
	 * Diese Methode wird vom abstrakten Server vererbt.
	 * Sie gibt einen Player, der die GameLobby verlassen will, an die ServerLobby zur�ck.
	 * !?Problem!?
	 * Danach wird er aus dem playerSet entfernt und es wird ein 
	 * ComUpdatePlayerlist Objekt mit broadcast an alle Client im Spiel verschickt.
	 * @param player ist der Threat der die Nachricht erhalten hat
	 * @param leave ist das ComObject, welches angibt, dass der Spieler in die Lobby 
	 * zur�ckkehrt
	 */
	public void receiveMessage(Player player, ComClientLeave leave){

	}
	/**
	 * Diese Methode wird vom abstrakten Server vererbt.
	 * Sie behandelt den Fall, dass ein Spieler das laufende Spiel verl�sst.
	 * Sie gibt einen Player, der das Spiel verlassen will, an die ServerLobby zur�ck.
	 * Alle Spieler, die sich im Spiel befinden bekommen ein ComWarning und
	 * werden an die Lobby zur�ckgegeben.
	 * !?Problem!?
	 * Das Spiel wird aufgel�st.
	 * @param player ist der Threat der die Nachricht erhalten hat
	 * @param quit ist das ComObject, welches angibt, dass der Spieler das Spiel verl�sst
	 */
	public void receiveMessage(Player player, ComClientQuit quit){

	}
	/**
	 * Diese Methode wird vom abstrakten Server vererbt.
	 * Sie sagt dem Ruleset, dass ein neues Spiel gestartet werden soll.
	 * @param player ist der Threat der die Nachricht erhalten hat
	 * @param start ist das ComObject, dass angibt, dass das Spiel gestartet werden soll
	 */
	public void receiveMessage(Player player, ComStartGame start){

	}
	/**
	 * Diese Methode wird vom abstrakten Server vererbt.
	 * Sie gibt das erhaltene ComRuleset durch einen Aufruf von resolveMessage
	 * an das Ruleset weiter.
	 * @param player ist der Threat der die Nachricht erhalten hat
	 * @param ruleset ist das ComObject, das zeigt, dass das Object
	 * vom Ruleset bearbeitet werden muss
	 */
	public void receiveMessage(Player player, ComRuleset ruleset){

	}
	
}