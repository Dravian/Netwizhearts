/**
 * 
 */
package Server;

import static Server.GameServerRepresentation.*;

import java.io.IOException;

import ComObjects.*;
import Ruleset.RulesetType;
import Ruleset.ServerRuleset;

/**
 * Diese Klasse ist f�r die Verwaltung eines Spieles zust�ndig. 
 * Sie verwaltet die Kommunikation zwischen den Clients w�hrend eines Spieles. 
 * Die GameServer-Klasse erbt Methoden zur Kommunikation vom Server. 
 * Der GameServer tauscht Nachrichten zwischen Ruleset und Player aus, 
 * um so den Spielablauf zu koordinieren.
 */
public class GameServer extends Server {
	/**
	 * Der LobbyServer 
	 */
	private LobbyServer lobbyServer;
	/**
	 * Der Benutzername des Spielleiters, der das Spiel erstellt hat
	 */
	private String gameMasterName;
	
	/**
	 * Der Name des Spiels
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
	 * Das Ruleset, das f�r den ordnungsgerechten Ablauf eines Spiels zust�ndig ist
	 */
	private ServerRuleset ruleset;
	
	/**
	 * Konstruktor des GameServers. Setzt die Attribute lobbyServer, name, password, hasPasword 
	 * und rulesetType auf die �bergebenen Werte. Setzt den gameMasterName auf den Namen des 
	 * gameMaster und f�gt den gameMaster dem Set an Spielern hinzu.  
	 * Bestimmt mithilfe des Enums RulesetType das Ruleset und erstellt es.
	 * Setzt currentPlayers auf eins und maxPlayers je nach Ruleset.
	 * @param server ist der LobbyServer der den GameServer erstellt hat.
	 * @param gameMaster ist der Name des Spielleiters
	 * @param GameName ist der Name des Spiels
	 * @param ruleset gibt an, welches Ruleset verwendet wird
	 * @param password speichert das Passwort des Spiels
	 * @param hasPassword gibt an,ob das Spiel ein Passwort hat
	 */
	public GameServer(LobbyServer server, Player gameMaster, String GameName, 
			RulesetType ruleset, String password, boolean hasPassword) {
		// begin-user-code
		// TODO Auto-generated constructor stub
		// end-user-code
	}
	
	/** 
	 * Erstellt eine neue GameServerRepresentation und gibt sie zur�ck
	 */
	public GameServerRepresentation getRepresentation() {
		return new GameServerRepresentation(gameMasterName, name, maxPlayers, currentPlayers, rulesetType, hasPassword);
	}
	
	/**
	 * Diese Methode f�gt einen Player dem Set an Playern hinzu, welche der
	 * Server verwaltet. Es wird vorrausgesetzt, dass der Player g�ltig und 
	 * noch nicht im Set vorhanden ist.
	 * Zus�tzlich wird die Zahl der currentPlayers um eins Erh�ht.
	 * @param player ist der Player, der hinzugefo�gt wird
	 */
	public synchronized void addPlayer(Player player) {
		playerSet.add(player);
		++currentPlayers;
	}
	/**
	 * Diese Methode entfernt einen Player aus dem Set an Playern, welche der
	 * Server verwaltet. Es wird vorrausgesetzt, dass der Player g�ltig und 
	 * im Set vorhanden ist.
	 * Zus�tzlich wird die Zahl der currentPlayers um eins Verringert.
	 * @param player ist der Player, der entfernt wird
	 */
	public synchronized void removePlayer(Player player) {
		playerSet.remove(player);
		--currentPlayers;
	}
	
	/**
	 * Diese Methode ist dafur zust�ndig zu ermitteln, was passiert wenn 
	 * ein Spieler aus der GameLobby geworfen wird.
	 * Der Player wird durch Aufruf von changeServer an die Lobby zur�ckgegeben.
	 * An diesen Spieler wird ein ComInitLobby und ein ComWarning geschickt.
	 * Danach wird ein ComUpdatePlayerlist Objekt mit broadcast an
	 * alle Clients im Spiel verschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param kicked ist das ComObject, das verarbeitet wird
	 */
	public void receiveMessage(Player player, ComKickPlayerRequest kickPlayer) {
	}
	
	/**
	 * Diese Methode ist dafur zust�ndig eine Chatnachricht an alle Clients im
	 * Spiel zu verschicken. Daf�r wird die ComChatMessage mit broadcast
	 * an alle Spieler im playerSet verteilt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param chat ist das ComObject, das die Chatnachricht enth�lt
	 */
	public void receiveMessage(Player player, ComChatMessage chat){
		broadcast(chat);
	}
	/**
	 * Diese Methode gibt einen Player, der die GameLobby verlassen will, 
	 * durch Aufruf von changeServer an die ServerLobby zur�ck und schickt ihm ein ComInitLobby.
	 * Danach wird ein ComUpdatePlayerlist Objekt mit broadcast 
	 * an alle Clients im Spiel verschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param leave ist das ComObject, welches angibt, dass der Spieler in die Lobby 
	 * zur�ckkehrt
	 */
	public void receiveMessage(Player player, ComClientLeave leave){
	}
	/**
	 * Diese Methode behandelt den Fall, dass ein Spieler das laufende Spiel verl�sst.
	 * Sie gibt einen Player, der das Spiel verlassen will, Aufruf von changeServer an die 
	 * ServerLobby zur�ck und schickt ihm ein ComInitLobby.
	 * Alle Spieler, die sich im Spiel befinden werden durch Aufruf von changeServer an 
	 * die Lobby zur�ckgegeben und bekommen ein ComInitLobby und ein ComWarning.
	 * Das Spiel wird aus dem gameServerSet des LobbyServers entfernt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param quit ist das ComObject, welches angibt, dass der Spieler das Spiel verl�sst
	 */
	public void receiveMessage(Player player, ComClientQuit quit){

	}
	/**
	 * Diese Methode sagt dem Ruleset, dass ein neues Spiel gestartet werden soll
	 * indem er dessen runGame Methode aufruft.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param start ist das ComObject, dass angibt, dass das Spiel gestartet werden soll
	 */
	public void receiveMessage(Player player, ComStartGame start){
		
	}
	/**
	 * Diese Methode gibt das erhaltene ComRuleset durch einen Aufruf von resolveMessage
	 * an das Ruleset weiter.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param ruleset ist das ComObject, das zeigt, dass das Object
	 * vom Ruleset bearbeitet werden muss
	 */
	public void receiveMessage(Player player, ComRuleset ruleset){

	}
	
	/**
	 * Baut ein neues ComInitGameLobby Objekt und gibt es zur�ck.
	 * @return Gibt das ComInitGameLobby Objekt zur�ck
	 */
	public ComInitGameLobby initLobby(){
		return null;
	}
	
	/**
	 * Diese Methode legt den Ablauf fest, was passiert, falls
	 * die Verbindung zu einem Client verloren gegangen ist.
	 * Der �bergebene Player wird aus dem playerSet im GameServer, sowie 
	 * dem names Set im LobbyServer entfernt.
	 * Alle Spieler, die sich im Spiel befinden werden durch Aufruf von changeServer an 
	 * die Lobby zur�ckgegeben und bekommen ein ComInitLobby und ein ComWarning.
	 * Das Spiel wird aus dem gameServerSet des LobbyServers entfernt.
	 * @param player ist der Tread von dem die IOException kommt
	 */
	public void handleIOException(Player player) {
		// TODO Automatisch erstellter Methoden-Stub
		
	}
}