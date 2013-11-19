/**
 * 
 */
package Server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ComObjects.*;
import Ruleset.RulesetType;
import Ruleset.ServerRuleset;

/**
 * GameServer. Diese Klasse ist fuer die Verwaltung eines Spieles zustaendig. 
 * Sie verwaltet die Kommunikation zwischen den Clients waehrend eines Spieles. 
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
	 * Die maximale Anzahl an Spielern, die dem Spiel beitreten koennen, 
	 * vom Regelwerk abhaengig
	 */
	private int maxPlayers;
	
	/** 
	 * Die Anzahl der Spielern, die dem Spiel beitreten sind
	 */
	private int currentPlayers;
	
	/** 
	 * Zeigt an, ob ein Spiel Passwortgeschuezt ist
	 */
	private boolean hasPassword;
	
	/** 
	 * Zeigt, welches Ruleset verwendet wird
	 */
	private RulesetType rulesetType;
	
	/** 
	 * Das Ruleset, das fuer den ordnungsgerechten Ablauf eines Spiels zustaendig ist
	 */
	private ServerRuleset ruleset;
	
	/**
	 * Konstruktor des GameServers. Setzt die Attribute lobbyServer, name, password, hasPasword 
	 * und rulesetType auf die uebergebenen Werte. Setzt den gameMasterName auf den Namen des 
	 * gameMaster und fuegt den gameMaster dem Set an Spielern hinzu.  
	 * Bestimmt mithilfe des Enums RulesetType das Ruleset und erstellt es.
	 * Setzt currentPlayers auf eins und maxPlayers je nach Ruleset.
	 * @param server ist der LobbyServer der den GameServer erstellt hat.
	 * @param gameMaster ist der Name des Spielleiters
	 * @param GameName ist der Name des Spiels
	 * @param ruleset gibt an, welches Ruleset verwendet wird
	 * @param password speichert das Passwort des Spiels
	 * @param hasPassword gibt an, ob das Spiel ein Passwort hat
	 */
	public GameServer(LobbyServer server, String gameMaster, String GameName, 
			RulesetType ruleset, String password, boolean hasPassword) {
		// begin-user-code
		// TODO Auto-generated constructor stub
		// end-user-code
	}

	/**
	 * Erstellt eine neue GameServerRepresentation und gibt sie zurueck
	 * @return Gibt die neue GameServerRepresentation zurueck
	 */
	public synchronized GameServerRepresentation getRepresentation() {
		return new GameServerRepresentation(getGameMasterName(), name, maxPlayers, currentPlayers, rulesetType, hasPassword);
	}
	
	/**
	 * Diese Methode fuegt einen Player dem Set an Playern hinzu, welche der
	 * Server verwaltet. Es wird vorrausgesetzt, dass der Player gueltig und 
	 * noch nicht im Set vorhanden ist.
	 * Zusaetzlich wird die Zahl der currentPlayers um eins Erhoeht.
	 * @param player ist der Player, der hinzugefuegt wird
	 */
	@Override
	public synchronized void addPlayer(Player player) {
		playerSet.add(player);
		++currentPlayers;
	}
	/**
	 * Diese Methode entfernt einen Player aus dem Set an Playern, welche der
	 * Server verwaltet. Es wird vorrausgesetzt, dass der Player gueltig und 
	 * im Set vorhanden ist.
	 * Zusaetzlich wird die Zahl der currentPlayers um eins Verringert.
	 * @param player ist der Player, der entfernt wird
	 */
	@Override
	public synchronized void removePlayer(Player player) {
		playerSet.remove(player);
		--currentPlayers;
	}
	
	/**
	 * Diese Methode verpackt eine RulesetMessage in ein ComObject und
	 * verschickt es mit sendToPlayer() an einen bestimmten Spieler.
	 * @param player ist der Name des Spielers an den die Nachricht verschickt wird
	 * @param message ist die Ruleset Nachricht, die in ein ComObject verpackt wird
	 */
	public void sendRulesetMessage(String player, RulesetMessage message){
		sendToPlayer(player, new ComRuleset(message));
	}
	
	/**
	 * Diese Methode verpackt eine RulesetMessage in ein ComObject und
	 * verschickt es mit broadcast() an alle Spieler.
	 * @param message ist die Ruleset Nachricht, die in ein ComObject verpackt wird
	 */
	public void broadcastRulesetMessage(RulesetMessage message){
		broadcast(new ComRuleset(message));
	}
	
	/**
	 * Diese Methode ist dafur zustaendig eine Chatnachricht an alle Clients
	 * des Servers zu verschicken. Dafuer wird die ComChatMessage mit broadcast
	 * an alle Spieler im playerSet verteilt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param chat ist das ComObject, das die Chatnachricht enthaelt
	 */
	public void receiveMessage(Player player, ComChatMessage chat) {
			broadcast(chat);			
	}
	
	/**
	 * Diese Methode ist dafuer zustaendig zu ermitteln, was passiert wenn 
	 * ein Spieler aus der GameLobby geworfen wird.
	 * Der Player wird durch Aufruf von changeServer an die Lobby zurueckgegeben.
	 * An diesen Spieler wird ein ComInitLobby und ein ComWarning geschickt.
	 * Danach wird ein ComUpdatePlayerlist Objekt mit broadcast an
	 * alle Clients im Spiel verschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param kicked ist das ComObject, das verarbeitet wird
	 */
	@Override
	public synchronized void receiveMessage(Player player, ComKickPlayerRequest kickPlayer) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Diese Methode gibt einen Player, der die GameLobby verlassen will, 
	 * durch Aufruf von changeServer an die ServerLobby zurueck und schickt ihm ein ComInitLobby.
	 * Danach wird ein ComUpdatePlayerlist Objekt mit broadcast 
	 * an alle Clients im Spiel verschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param leave ist das ComObject, welches angibt, dass der Spieler in die Lobby 
	 * zurueckkehrt
	 */
	@Override
	public synchronized void receiveMessage(Player player, ComClientLeave leave){
		// TODO Auto-generated method stub
	}
	
	/**
	 * Diese Methode sagt dem Ruleset, dass ein neues Spiel gestartet werden soll
	 * indem er dessen runGame Methode aufruft.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param start ist das ComObject, dass angibt, dass das Spiel gestartet werden soll
	 */
	@Override
	public synchronized void receiveMessage(Player player, ComStartGame start){
		// TODO Auto-generated method stub
	}
	
	/**
	 * Diese Methode gibt das erhaltene ComRuleset durch einen Aufruf von resolveMessage
	 * an das Ruleset weiter.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param ruleset ist das ComObject, das zeigt, dass das Object
	 * vom Ruleset bearbeitet werden muss
	 */
	@Override
	public synchronized void receiveMessage(Player player, ComRuleset ruleset){
		// TODO Auto-generated method stub
	}
	
	/**
	 * Baut ein neues ComInitGameLobby Objekt und gibt es zurueck.
	 * @return Gibt das ComInitGameLobby Objekt zurueck
	 */
	public ComInitGameLobby initLobby(){
		List<String> playerList = new ArrayList<String>();
		if(!playerSet.isEmpty()){
			for (Player player : playerSet) {
				playerList.add(player.getPlayerName());
			}
		}
		ComInitGameLobby init = new ComInitGameLobby(playerList);
		return init;
	}
	
	/**
	 * Diese Methode schliesst die Verbindung zu einem Client.
	 * Der uebergebene Player wird aus dem playerSet im GameServer, sowie 
	 * dem names Set im LobbyServer entfernt.
	 * Alle Spieler, die sich im Spiel befinden werden durch Aufruf von changeServer an 
	 * die Lobby zurueckgegeben und bekommen ein ComInitLobby und ein ComWarning.
	 * Das Spiel wird aus dem gameServerSet des LobbyServers entfernt.
	 * @param player ist der Tread von dem die Exception kommt
	 */
	@Override
	public synchronized void disconnectPlayer(Player player) {
		// TODO Automatisch erstellter Methoden-Stub
		
	}
	
	/**
	 * Getter fuer den Namen des Spielleiters
	 * @return Gibt den gameMasterName zurueck
	 */
	public String getGameMasterName() {
		return gameMasterName;
	}
	
	/**
	 * Getter fuer das Passwort
	 * @return Gibt das Passwort des Spieles zurueck
	 */
	public String getPassword() {
		return password;
	}

}