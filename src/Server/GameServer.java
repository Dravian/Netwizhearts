package Server;

import java.util.ArrayList;
import java.util.List;
import ComObjects.*;
import Ruleset.IllegalNumberOfPlayersException;
import Ruleset.RulesetType;
import Ruleset.ServerHearts;
import Ruleset.ServerRuleset;
import Ruleset.ServerWizard;

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
	 * Konstruktor des GameServers. Setzt die Attribute lobbyServer, name, password, hasPassword 
	 * und rulesetType auf die uebergebenen Werte. Setzt den gameMasterName auf den Namen des 
	 * gameMaster und fuegt den gameMaster dem Set an Spielern hinzu.  
	 * Bestimmt mithilfe des Enums RulesetType das Ruleset und erstellt es.
	 * Setzt currentPlayers auf 0 und maxPlayers je nach Ruleset.
	 * @param server ist der LobbyServer der den GameServer erstellt hat.
	 * @param gameMaster ist der Name des Spielleiters
	 * @param GameName ist der Name des Spiels
	 * @param ruleset gibt an, welches Ruleset verwendet wird
	 * @param password speichert das Passwort des Spiels
	 * @param hasPassword gibt an, ob das Spiel ein Passwort hat
	 */
	public GameServer(LobbyServer server, Player gameMaster, String GameName, 
			RulesetType ruleset, String password, boolean hasPassword) {
		lobbyServer = server;
		gameMasterName = gameMaster.getPlayerName();
		name = GameName;
		rulesetType = ruleset;
		this.password = password;
		this.hasPassword = hasPassword;
		currentPlayers = 0;
		if (rulesetType == RulesetType.Hearts) {
			this.ruleset = new ServerHearts(this);
			maxPlayers = 4;
		} else {
			if (rulesetType == RulesetType.Wizard) {
				this.ruleset = new ServerWizard(this) ;
				maxPlayers = 6;
			} else {
				System.err.println("Unknown Ruleset!");
				disconnectPlayer(gameMaster);
			}
		}	
	}

	/**
	 * Erstellt eine neue GameServerRepresentation und gibt sie zurueck
	 * @return Gibt die neue GameServerRepresentation zurueck
	 */
	public synchronized GameServerRepresentation getRepresentation() {
		return new GameServerRepresentation(getGameMasterName(), name, maxPlayers, 
				currentPlayers, rulesetType, hasPassword);
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
		Player toBeKicked = null;
		if(!playerSet.isEmpty()){
			for (Player check : playerSet) {
				if (check.getPlayerName().equals(kickPlayer.getPlayerName())){
					toBeKicked = check;				
				}
			}
			if (toBeKicked != null){
				if(!toBeKicked.getPlayerName().equals(gameMasterName)){
					lobbyServer.broadcast(new ComLobbyUpdateGamelist(false, this.getRepresentation()));
					removePlayer(toBeKicked);
					toBeKicked.changeServer(lobbyServer);
					ComInitLobby comInit = lobbyServer.initLobby();			
					toBeKicked.send(comInit);
					ComWarning warning = new ComWarning("Kicked out of Game!");
					toBeKicked.send(warning);
					ComUpdatePlayerlist update = new ComUpdatePlayerlist(toBeKicked.getPlayerName(), true);
					broadcast(update);					
				} else {
					ComWarning warning = new ComWarning("Couldn't kick player!");
					player.send(warning);	
				}
				
			} else {
				System.err.println("Player not in Game!");
				ComWarning warning = new ComWarning("Couldn't kick player!");
				player.send(warning);	
			}
		} else {
			System.err.println("PlayerSet empty!");
			player.closeConnection();
		}		
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
		Player leavingPlayer = player;
		if(!playerSet.isEmpty()){
			if (playerSet.contains(leavingPlayer)){
				if(leavingPlayer.getPlayerName().equals(gameMasterName)){			
					for (Player back : playerSet) {	
							back.changeServer(lobbyServer);
							ComInitLobby comInit = lobbyServer.initLobby();			
							back.send(comInit);
							ComWarning warning = new ComWarning("Game has been disbanded!");
							back.send(warning);	
					}
					playerSet.clear();
					lobbyServer.broadcast(new ComLobbyUpdateGamelist(false, this.getRepresentation()));
					lobbyServer.removeGameServer(this);
				} else {
					removePlayer(leavingPlayer);
					leavingPlayer.changeServer(lobbyServer);
					ComInitLobby comInit = lobbyServer.initLobby();			
					leavingPlayer.send(comInit);
					ComUpdatePlayerlist update = new ComUpdatePlayerlist(leavingPlayer.getPlayerName(), true);
					broadcast(update);
				}				
			} else {
				System.err.println("Player not in Game!");
			}
		} else {
			System.err.println("PlayerSet empty!");
			player.send(new ComClientQuit());
			player.closeConnection();
		}	
	}
	
	/**
	 * Diese Methode beendet ein laufendes Spiel, wenn ein Spieler dieses
	 * verlaesst. Dieser wird an den LobbyServer zurueckgegeben und erhaelt
	 * ein ComInitLobby. Die anderen Spieler werden ebenfalls an den LobbyServer 
	 * zurueckgegeben und erhalten sowohl ein ComInitLobby als auch ein ComWarning.
	 * Der GameServer wird aus dem Set des LobbyServers entfernt und die Spieler in
	 * der Lobby erhalten ein Gamelist Update.
	 * @param quit ist das ComObject, welches angibt, dass der Spieler das 
	 * laufende Spiel verlaesst
	 */
	@Override
	public synchronized void receiveMessage(Player player, ComClientQuit quit){
		if(!playerSet.isEmpty()){
			if (playerSet.contains(player)) {
				removePlayer(player);
				player.changeServer(lobbyServer);
				ComInitLobby comInit = lobbyServer.initLobby();			
				player.send(comInit);
			} else {
				System.err.println("Player not in Game!");
			}				
		} else {
			System.err.println("PlayerSet empty!");
			player.send(new ComClientQuit());
			player.closeConnection();
		}
		if(!playerSet.isEmpty()){
			for (Player back : playerSet) {
				back.changeServer(lobbyServer);
				ComInitLobby comInit = lobbyServer.initLobby();			
				back.send(comInit);
				ComWarning warning = new ComWarning(player.getPlayerName() + " left the Game");
				back.send(warning);	
			}	
			playerSet.clear();
		} else {
			System.err.println("PlayerSet empty!");
			player.send(new ComClientQuit());
			player.closeConnection();
		}
		lobbyServer.removeGameServer(this);
		lobbyServer.broadcast(new ComLobbyUpdateGamelist(true, getRepresentation()));
	}
	
	/**
	 * Diese Methode sagt dem Ruleset, dass ein neues Spiel gestartet werden soll
	 * indem er dessen runGame Methode aufruft. Faengt eine IllegalNumberOfPlayersException
	 * vom Ruleset, falls die Anzahl der Spieler nicht stimmt und beendet in dem
	 * Fall das Spiel.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param start ist das ComObject, dass angibt, dass das Spiel gestartet werden soll
	 */
	@Override
	public synchronized void receiveMessage(Player player, ComStartGame start){
		try {
			ruleset.runGame();			
		} catch (IllegalNumberOfPlayersException e) {
			System.out.println("Number of Players not matching!");
			ComWarning warning = new ComWarning("Couldn't start game!");
			broadcast(warning);
			quitGame();
			e.printStackTrace();
		}
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
		ruleset.getRulesetMessage().visit(this.ruleset, player.getPlayerName());
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
	 * Diese Methode beendet das Spiel und gibt die Player an den LobbyServer
	 * zurueck.Der GameServer wird aus dem Set des LobbyServers entfernt
	 * und die Spieler in der Lobby erhalten ein Gamelist Update.
	 */
	public void quitGame(){
		for (Player player : playerSet) {
			player.changeServer(lobbyServer);
			ComInitLobby comInit = lobbyServer.initLobby();			
			player.send(comInit);
		}
		playerSet.clear();
		lobbyServer.removeGameServer(this);
		lobbyServer.broadcast(new ComLobbyUpdateGamelist(true, getRepresentation()));
	}
	
	/**
	 * Diese Methode schliesst die Verbindung zu einem Client.
	 * Der uebergebene Player wird aus dem playerSet im GameServer, sowie 
	 * dem names Set im LobbyServer entfernt.
	 * Alle Spieler, die sich im Spiel befinden werden durch Aufruf von changeServer an 
	 * die Lobby zurueckgegeben und bekommen ein ComInitLobby und ein ComWarning.
	 * Das Spiel wird aus dem gameServerSet des LobbyServers entfernt.
	 * @param player ist der Spieler der entfernt wird 
	 */
	@Override
	public synchronized void disconnectPlayer(Player player) {
		if(!playerSet.isEmpty()){
			if (playerSet.contains(player)) {
				removePlayer(player);						
			} else {
				System.err.println("Player not in Game!");
			}
			lobbyServer.removeName(player.getPlayerName());	
			player.closeConnection();
		} else {
			System.err.println("PlayerSet empty!");
		}
		if(!playerSet.isEmpty()){
			for (Player back : playerSet) {
				back.changeServer(lobbyServer);
				ComInitLobby comInit = lobbyServer.initLobby();			
				back.send(comInit);
				ComWarning warning = new ComWarning("Game Disbanded!");
				back.send(warning);	
			}
			playerSet.clear();
		} else {
			System.err.println("PlayerSet empty!");
		}
		lobbyServer.removeGameServer(this);
		lobbyServer.broadcast(new ComLobbyUpdateGamelist(true, getRepresentation()));
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