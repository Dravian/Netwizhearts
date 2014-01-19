package Server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
 * Die GameServer-Klasse erbt Methoden zur Kommunikation vom Server. Der
 * GameServer tauscht Nachrichten zwischen Ruleset und Player aus, um so den
 * Spielablauf zu koordinieren.
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
	 * Das Passwort, das der Spielleiter beim erstellen gesetzt hat. Ist NULL,
	 * falls es kein Passwort gibt.
	 */
	private String password;

	/**
	 * Die maximale Anzahl an Spielern, die dem Spiel beitreten koennen, vom
	 * Regelwerk abhaengig
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
	 * Das Ruleset, das fuer den ordnungsgerechten Ablauf eines Spiels
	 * zustaendig ist
	 */
	private ServerRuleset ruleset;
	
	/**
	 * Zeigt ob das Spiel bereits gestartet ist
	 */
	private boolean hasStarted;

	private int reqPlayers;
	
	private boolean newRound;
	
	/**
	 * Konstruktor des GameServers. Setzt die Attribute lobbyServer, name,
	 * password, hasPassword und rulesetType auf die uebergebenen Werte. Setzt
	 * den gameMasterName auf den Namen des gameMaster und fuegt den gameMaster
	 * dem Set an Spielern hinzu. Setzt hasStarted auf false. Bestimmt mithilfe des Enums RulesetType das
	 * Ruleset und erstellt es. Setzt currentPlayers auf 0 und max- und minPlayers je
	 * nach Ruleset.
	 * 
	 * @param server
	 *            ist der LobbyServer der den GameServer erstellt hat.
	 * @param gameMaster
	 *            ist der Name des Spielleiters
	 * @param GameName
	 *            ist der Name des Spiels
	 * @param ruleset
	 *            gibt an, welches Ruleset verwendet wird
	 * @param password
	 *            speichert das Passwort des Spiels
	 * @param hasPassword
	 *            gibt an, ob das Spiel ein Passwort hat
	 */
	public GameServer(LobbyServer server, Player gameMaster, String GameName,
			RulesetType ruleset, String password, boolean hasPassword) {
		setHasStarted(false);
		lobbyServer = server;
		gameMasterName = gameMaster.getPlayerName();
		name = GameName;
		rulesetType = ruleset;
		this.password = password;
		this.hasPassword = hasPassword;
		currentPlayers = 0;
		newRound = true;
		reqPlayers = 0;
		if (rulesetType == RulesetType.Hearts) {
			this.ruleset = new ServerHearts(this);
			maxPlayers = 4;
		} else {
			if (rulesetType == RulesetType.Wizard) {
				this.ruleset = new ServerWizard(this);
				maxPlayers = 6;
			} else {
				System.err.println("Unknown Ruleset!");
				disconnectPlayer(gameMaster);
			}
		}
	}

	/**
	 * Erstellt eine neue GameServerRepresentation und gibt sie zurueck
	 * 
	 * @return Gibt die neue GameServerRepresentation zurueck
	 */
	public GameServerRepresentation getRepresentation() {
		return new GameServerRepresentation(getGameMasterName(), name, 
				maxPlayers , currentPlayers, rulesetType, hasPassword, isHasStarted());
	}

	/**
	 * Diese Methode fuegt einen Player dem Set an Playern hinzu, welche der
	 * Server verwaltet. Es wird vorrausgesetzt, dass der Player gueltig und
	 * noch nicht im Set vorhanden ist. Zusaetzlich wird die Zahl der
	 * currentPlayers um eins Erhoeht.
	 * 
	 * @param player
	 *            ist der Player, der hinzugefuegt wird
	 */
	@Override
	public void addPlayer(Player player) {
		synchronized(playerSet){
			playerSet.add(player);
			++currentPlayers;
		}	
	}

	/**
	 * Diese Methode entfernt einen Player aus dem Set an Playern, welche der
	 * Server verwaltet. Es wird vorrausgesetzt, dass der Player gueltig und im
	 * Set vorhanden ist. Zusaetzlich wird die Zahl der currentPlayers um eins
	 * Verringert.
	 * 
	 * @param player
	 *            ist der Player, der entfernt wird
	 */
	@Override
	public void removePlayer(Player player) {
		synchronized(playerSet){
			playerSet.remove(player);
			--currentPlayers;
		}
	}

	/**
	 * Diese Methode verschickt ComWarning mit der �bergebenen Warnung 
	 * mit broadcast() an alle Spieler.
	 * @param message
	 *            ist die Ruleset Nachricht, die in ein ComObject verpackt wird
	 */
	public void broadcastRulesetMessage(RulesetMessage message) {
		broadcast(new ComRuleset(message));
	}
	
	/**
	 * Diese Methode verpackt eine RulesetMessage in ein ComObject und
	 * verschickt es mit sendToPlayer() an einen bestimmten Spieler.
	 * @param player
	 *            ist der Name des Spielers an den die Nachricht verschickt wird
	 * @param message
	 *            ist die Ruleset Nachricht, die in ein ComObject verpackt wird
	 */
	public void sendRulesetMessage(String player, RulesetMessage message) {
		sendToPlayer(player, new ComRuleset(message));
	}

	/**
	 * Diese Methode verpackt eine RulesetMessage in ein ComObject und
	 * verschickt es mit broadcast() an alle Spieler.
	 * @param message
	 *            ist die Ruleset Nachricht, die in ein ComObject verpackt wird
	 */
	public void broadcastWarning(WarningMsg warning) {
		broadcast(new ComWarning(warning));
	}
	
	/**
	 * Diese Methode verschickt ein ComWarning mit sendToPlayer() an einen bestimmten Spieler.
	 * @param player
	 *            ist der Name des Spielers an den die Nachricht verschickt wird
	 * @param message
	 *            ist die Ruleset Nachricht, die in ein ComObject verpackt wird
	 */
	public void sendWarning(String player, WarningMsg warning) {
		sendToPlayer(player, new ComWarning(warning));
	}
	
	/**
	 * Diese Methode ist dafur zustaendig eine Chatnachricht an alle Clients des
	 * Servers zu verschicken. Dafuer wird die ComChatMessage mit broadcast an
	 * alle Spieler im playerSet verteilt.
	 * 
	 * @param player
	 *            ist der Thread der die Nachricht erhalten hat
	 * @param chat
	 *            ist das ComObject, das die Chatnachricht enthaelt
	 */
	public void receiveMessage(Player player, ComChatMessage chat) {
		if (chat.getChatMessage() != null) {
			if (!chat.getChatMessage().isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				String time = sdf.format(Calendar.getInstance().getTime());
				ComChatMessage chatBack = new ComChatMessage(player.getPlayerName()+ "(" + time + "): " +chat.getChatMessage()+ "\n");
				broadcast(chatBack);
			}
		}
	}

	/**
	 * Diese Methode ist dafuer zustaendig zu ermitteln, was passiert wenn ein
	 * Spieler aus der GameLobby geworfen wird. Der Player wird durch Aufruf von
	 * changeServer an die Lobby zurueckgegeben. An diesen Spieler wird ein
	 * ComInitLobby und ein ComWarning geschickt. Danach wird ein
	 * ComUpdatePlayerlist Objekt mit broadcast an alle Clients im Spiel
	 * verschickt.
	 * 
	 * @param player
	 *            ist der Thread der die Nachricht erhalten hat
	 * @param kicked
	 *            ist das ComObject, das verarbeitet wird
	 */
	@Override
	public void receiveMessage(Player player,
			ComKickPlayerRequest kickPlayer) {
		if (player.getPlayerName().equals(gameMasterName)){
			Player toBeKicked = null;
			if (!playerSet.isEmpty()) {
				synchronized(playerSet) {
					for (Player check : playerSet) {
						if (check.getPlayerName().equals(kickPlayer.getPlayerName())) {
							toBeKicked = check;
						}
					}
				}	
				if (toBeKicked != null) {
					if (!toBeKicked.getPlayerName().equals(gameMasterName)) {
						lobbyServer.broadcast(new ComLobbyUpdateGamelist(false,
								this.getRepresentation()));
						removePlayer(toBeKicked);
						toBeKicked.changeServer(lobbyServer);
						ComInitLobby comInit = lobbyServer.initLobby();
						toBeKicked.send(comInit);
						ComWarning warning = new ComWarning(WarningMsg.BeenKicked);
						toBeKicked.send(warning);
						ComUpdatePlayerlist update = new ComUpdatePlayerlist(
								toBeKicked.getPlayerName(), true);
						broadcast(update);
						lobbyServer.broadcast(new ComLobbyUpdateGamelist(false,
								getRepresentation()));
					} else {
						ComWarning warning = new ComWarning(WarningMsg.CouldntKick);
						player.send(warning);
					}
				} else {
					ComWarning warning = new ComWarning(WarningMsg.CouldntKick);
					player.send(warning);
				}
			} else {
				System.err.println("PlayerSet empty!");
				player.send(new ComClientQuit());
				disconnectPlayer(player);
			}
		} else {
			System.err.println("Not the GameMaster!");
			player.send(new ComClientQuit());
			disconnectPlayer(player);
		}		
	}

	/**
	 * Diese Methode gibt einen Player, der die GameLobby verlassen will, durch
	 * Aufruf von changeServer an die ServerLobby zurueck und schickt ihm ein
	 * ComInitLobby. Danach wird ein ComUpdatePlayerlist Objekt mit broadcast an
	 * alle Clients im Spiel verschickt.
	 * Diese Methode beendet ein laufendes Spiel, wenn ein Spieler dieses
	 * verlaesst. Dieser wird an den LobbyServer zurueckgegeben und erhaelt ein
	 * ComInitLobby. Die anderen Spieler werden ebenfalls an den LobbyServer
	 * zurueckgegeben und erhalten sowohl ein ComInitLobby als auch ein
	 * ComWarning. Der GameServer wird aus dem Set des LobbyServers entfernt und
	 * die Spieler in der Lobby erhalten ein Gamelist Update.
	 * 
	 * @param player
	 *            ist der Thread der die Nachricht erhalten hat
	 * @param leave
	 *            ist das ComObject, welches angibt, dass der Spieler in die
	 *            Lobby zurueckkehrt
	 */
	@Override
	public void receiveMessage(Player player, ComClientLeave leave) {
		Player leavingPlayer = player;
		if (!isHasStarted()) {
			if (!playerSet.isEmpty()) {
				if (playerSet.contains(leavingPlayer)) {
					if (leavingPlayer.getPlayerName().equals(gameMasterName)) {
						synchronized(playerSet){
							leavingPlayer.changeServer(lobbyServer);
							ComInitLobby comInitGM = lobbyServer.initLobby();
							leavingPlayer.send(comInitGM);
							removePlayer(leavingPlayer);
							for (Player back : playerSet) {
								back.changeServer(lobbyServer);
								ComInitLobby comInit = lobbyServer.initLobby();
								back.send(comInit);
								ComWarning warning = new ComWarning(
										WarningMsg.GameDisbanded);
								back.send(warning);
							}
						}
						playerSet.clear();
						lobbyServer.broadcast(new ComLobbyUpdateGamelist(true,
								this.getRepresentation()));
						lobbyServer.removeGameServer(this);
					} else {
						removePlayer(leavingPlayer);
						leavingPlayer.changeServer(lobbyServer);
						ComInitLobby comInit = lobbyServer.initLobby();
						leavingPlayer.send(comInit);
						ComUpdatePlayerlist update = new ComUpdatePlayerlist(
								leavingPlayer.getPlayerName(), true);
						broadcast(update);
						lobbyServer.broadcast(new ComLobbyUpdateGamelist(false,
								getRepresentation()));
					}
				} else {
					System.err.println("Player not in Game!");
				}
			} else {
				System.err.println("PlayerSet empty!");
				player.send(new ComClientQuit());
				disconnectPlayer(player);
			}
		} else {
			if (!playerSet.isEmpty()) {
				if (playerSet.contains(player)) {
					synchronized (playerSet) {
						for (Player back : playerSet) {
							back.changeServer(lobbyServer);
							ComInitLobby comInit = lobbyServer.initLobby();
							back.send(comInit);
							ComWarning warning = new ComWarning(
									WarningMsg.GameDisbanded);
							back.send(warning);
						}
					}
					playerSet.clear();
				} else {
					System.err.println("Player not in Game!");
					player.send(new ComClientQuit());
					disconnectPlayer(player);
				}
			} else {
				System.err.println("PlayerSet empty!");
				player.send(new ComClientQuit());
				disconnectPlayer(player);
			}
			lobbyServer.broadcast(new ComLobbyUpdateGamelist(true,
					getRepresentation()));
			lobbyServer.removeGameServer(this);
		}
	}

	/**
	 * Diese Methode sagt dem Ruleset, dass ein neues Spiel gestartet werden
	 * soll indem er dessen runGame Methode aufruft. Faengt eine
	 * IllegalNumberOfPlayersException vom Ruleset, falls die Anzahl der Spieler
	 * nicht stimmt und beendet in dem Fall das Spiel.
	 * 
	 * @param player
	 *            ist der Thread der die Nachricht erhalten hat
	 * @param start
	 *            ist das ComObject, dass angibt, dass das Spiel gestartet
	 *            werden soll
	 */
	@Override
	public void receiveMessage(Player player, ComStartGame start) {
		if (player.getPlayerName().equals(gameMasterName)) {
			if(!isHasStarted()){
				try {
					synchronized (playerSet) {
						for (Player back : playerSet) {
							ruleset.addPlayerToGame(back.getPlayerName());
						}	
					}		
					setHasStarted(true);
					lobbyServer.broadcast(new ComLobbyUpdateGamelist(false,
							getRepresentation()));
					broadcast(new ComStartGame());
					ruleset.runGame();
				} catch (IllegalNumberOfPlayersException e) {
					ComWarning warning = new ComWarning(WarningMsg.CouldntStart);
					broadcast(warning);
					endGame();
					e.printStackTrace();
				}
			} else{
				System.err.println("Game has already started!");
				player.send(new ComClientQuit());
				disconnectPlayer(player);
			}
		} else {
			System.err.println("Not the GameMaster!");
			player.send(new ComClientQuit());
			disconnectPlayer(player);
		}	
	}

	/**
	 * Diese Methode gibt das erhaltene ComRuleset durch einen Aufruf von
	 * resolveMessage an das Ruleset weiter.
	 * 
	 * @param player
	 *            ist der Thread der die Nachricht erhalten hat
	 * @param ruleset
	 *            ist das ComObject, das zeigt, dass das Object vom Ruleset
	 *            bearbeitet werden muss
	 */
	@Override
	public void receiveMessage(Player player, ComRuleset ruleset) {
		ruleset.getRulesetMessage().visit(this.ruleset, player.getPlayerName());			
	}

	/**
	 * Baut ein neues ComInitGameLobby Objekt und gibt es zurueck.
	 * 
	 * @return Gibt das ComInitGameLobby Objekt zurueck
	 */
	public ComInitGameLobby initLobby() {
		List<String> playerList = new ArrayList<String>();
		if (!playerSet.isEmpty()) {
			synchronized(playerSet){
				for (Player player : playerSet) {
					playerList.add(player.getPlayerName());
				}
			}	
		}
		ComInitGameLobby init = new ComInitGameLobby(playerList);
		return init;
	}

	/**
	 * Diese Methode beendet das Spiel und gibt die Player an den LobbyServer
	 * zurueck.Der GameServer wird aus dem Set des LobbyServers entfernt und die
	 * Spieler in der Lobby erhalten ein Gamelist Update.
	 */
	public void quitGame() {
		hasStarted = false;
		if (rulesetType == RulesetType.Hearts) {
			this.ruleset = new ServerHearts(this);
			maxPlayers = 4;
		} else {
			if (rulesetType == RulesetType.Wizard) {
				this.ruleset = new ServerWizard(this);
				maxPlayers = 6;
			} 
		}
	}
	
	public void receiveMessage(Player player, ComNewRound request) {
		++reqPlayers;
		if(!request.getResult()){
			newRound = false;
		}
		if(reqPlayers == currentPlayers){
			if(newRound){
				reqPlayers = 0;
				newRound = true;
				if(!isHasStarted()){
					try {
						synchronized (playerSet) {
							for (Player back : playerSet) {
								ruleset.addPlayerToGame(back.getPlayerName());
							}	
						}		
						setHasStarted(true);
						lobbyServer.broadcast(new ComLobbyUpdateGamelist(false,
								getRepresentation()));
						broadcast(new ComStartGame());
						ruleset.runGame();
					} catch (IllegalNumberOfPlayersException e) {
						ComWarning warning = new ComWarning(WarningMsg.CouldntStart);
						broadcast(warning);
						endGame();
						e.printStackTrace();
					}
				} else{
					System.err.println("Game has already started!");
					player.send(new ComClientQuit());
					disconnectPlayer(player);
				}
			} else {
				endGame();
			}
		}
	}

	private void endGame() {
		synchronized(playerSet){
			for (Player players : playerSet) {
				players.changeServer(lobbyServer);
				ComInitLobby comInit = lobbyServer.initLobby();
				players.send(comInit);
			}	
		}	
		playerSet.clear();
		lobbyServer.removeGameServer(this);
		lobbyServer.broadcast(new ComLobbyUpdateGamelist(true,
				getRepresentation()));
	}
	
	/**
	 * Diese Methode schliesst die Verbindung zu einem Client. Der uebergebene
	 * Player wird aus dem playerSet im GameServer, sowie dem names Set im
	 * LobbyServer entfernt. Alle Spieler, die sich im Spiel befinden werden
	 * durch Aufruf von changeServer an die Lobby zurueckgegeben und bekommen
	 * ein ComInitLobby und ein ComWarning. Das Spiel wird aus dem gameServerSet
	 * des LobbyServers entfernt.
	 * 
	 * @param player
	 *            ist der Spieler der entfernt wird
	 */
	@Override
	public void disconnectPlayer(Player player) {
		if(isHasStarted() || player.getPlayerName().equals(gameMasterName)){
			if (!playerSet.isEmpty()) {
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
			if (!playerSet.isEmpty()) {
				synchronized(playerSet) {
					for (Player back : playerSet) {
						back.changeServer(lobbyServer);
						ComInitLobby comInit = lobbyServer.initLobby();
						back.send(comInit);
						ComWarning warning = new ComWarning(WarningMsg.GameDisbanded);
						back.send(warning);
					}	
				}
				playerSet.clear();
			}
			lobbyServer.removeGameServer(this);
			lobbyServer.broadcast(new ComLobbyUpdateGamelist(true,
					getRepresentation()));
		} else {
			if (!playerSet.isEmpty()) {
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
			lobbyServer.broadcast(new ComLobbyUpdateGamelist(false,
					getRepresentation()));
		}
		
	}

	/**
	 * Getter fuer den Namen des Spielleiters
	 * 
	 * @return Gibt den gameMasterName zurueck
	 */
	public String getGameMasterName() {
		return gameMasterName;
	}

	/**
	 * Getter fuer das Passwort
	 * 
	 * @return Gibt das Passwort des Spieles zurueck
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gibt zur�ck, ob das Spiel bereits gestartet ist
	 * @return den Wert von hasStarted
	 */
	public boolean isHasStarted() {
		return hasStarted;
	}
	/**
	 * Setter f�r has started
	 * @param hasStarted wird auf den �bergebenen Wert gesetzt
	 */
	public void setHasStarted(boolean hasStarted) {
		this.hasStarted = hasStarted;
	}
}