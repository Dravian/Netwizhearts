package Server;


import java.util.*;

import ComObjects.*;
import Ruleset.RulesetType;

/**
 * LobbyServer. Diese Klasse ist fuer die Verwaltung der Spiellobby auf dem Server verantwortlich.
 * Sie erstellt neue Spiele und verwaltet laufende Spiele.
 * Auch wird der Chatverkehr ueber sie an die verbundenen Spieler weitergeleitet.
 * Die LobbyServer-Klasse erbt Methoden zur Kommunikation vom Server.
 */
public class LobbyServer extends Server {
	
	/** 
	 * Ein Set der Benutzernamen aller Spieler, die in der Lobby oder einem Spiel sind
	 */
	private Set<String> names = new HashSet<String>();
	
	/** 
	 * Ein Set an GameServern, die alle erstellten Spiele repraesentieren
	 */
	private Set<GameServer> gameServerSet = new HashSet<GameServer>();

	/**
	 * Konstruktor des Lobby Servers
	 */
	public LobbyServer(){
	}
	
	/**
	 * Fuegt einen neuen Benutzennamen in das Namensset ein.
	 * Es wird vorrausgesetzt, dass der Name noch nicht im Set
	 * vorhanden ist.
	 * @param name ist der Name der eingefuegt wird
	 */
	public synchronized void addName(String name) {
		getNames().add(name);
	}

	/**
	 * Loescht einen Benutzennamen aus dem Namensset.
	 * Es wird vorrausgesetzt, dass der Name im Set vorhanden ist.
	 * @param name ist der Name der geloescht wird
	 */
	public synchronized void removeName(String name) {
		getNames().remove(name);
	}

	/**
	 * Fuegt einen neuen GameServer in das gameServerSet ein.
	 * @param game ist der GameServer der eingefuegt wird
	 */
	public synchronized void addGameServer(GameServer game) {
		gameServerSet.add(game);
	}

	/**
	 * Loescht einen GameServer aus dem Gameserverset.
	 * @param game ist der GameServer der geloescht wird
	 */
	public synchronized void removeGameServer(GameServer game) {
		gameServerSet.remove(game);
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
	 * Diese ueberladene Methode erstellt einen neuen GameServer fuegt ihm den Player durch
	 * Aufruf von dessen changeServer Methode hinzu.
	 * Der neue GameServer wird in das gameServerSet eingefuegt.
	 * Dem Client wird ein ComInitGameLobby geschickt.
	 * Der Player wird aus der Playerlist entfernt.
	 * Durch broadcast wird im LobbyServer sowohl ein ComUpdatePlayerlist
	 * als auch ein ComLobbyUpdateGamelist verschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param create ist das ComObject, welches angibt, dass der Player 
	 * ein neues Spiel erstellt hat
	 */
	@Override
	public synchronized void receiveMessage(Player player, ComCreateGameRequest create){
		if (create.getGameName() != null){
			String name = create.getGameName();
			if(create.getRuleset() == RulesetType.Hearts || create.getRuleset() == RulesetType.Wizard){		
				RulesetType ruleset = create.getRuleset();
				if(create.getPassword() != null){
					String password = create.getPassword();
					if(password.isEmpty()){
						if(!create.hasPassword()){
							boolean hasPassword = create.hasPassword();
							GameServer game = new GameServer(this, player, name, ruleset, password, hasPassword);
							addGameServer(game);
							addPlayerToGame(player, game);
							broadcast(new ComLobbyUpdateGamelist(false, game.getRepresentation()));
						} else {
							System.err.println("Password inconsistent!");
							disconnectPlayer(player);
						}
					} else {		
						if(create.hasPassword()){
							boolean hasPassword = create.hasPassword();
							GameServer game = new GameServer(this, player, name, ruleset, password, hasPassword);
							addGameServer(game);
							addPlayerToGame(player, game);
							broadcast(new ComLobbyUpdateGamelist(false, game.getRepresentation()));
						} else {
							System.err.println("Password inconsistent!");
							disconnectPlayer(player);
						}
					}
				} else {
					System.err.println("Password null!");
					disconnectPlayer(player);
				}			
			} else {
				System.err.println("Unbekanntes Ruleset");
				disconnectPlayer(player);
			}
		} else {
			System.err.println("Game has no Name");
			disconnectPlayer(player);
		}
		
	}
	/**
	 * Hilfsmethode, die einen Spieler zu einem Spiel hinzufuegt
	 * @param player
	 * @param game
	 */
	private void addPlayerToGame(Player player, GameServer game) {
		player.changeServer(game);
		ComInitGameLobby comInit = game.initLobby();
		player.send(comInit);
		removePlayer(player);
		broadcast(new ComUpdatePlayerlist(player.getPlayerName(), true));	
	}

	/**
	 * Diese ueberladene Methode fuegt einen Player dem entsprechenden GameServer hinzu.
	 * Falls das Passwort nicht leer ist wird geprueft, ob es mit dem Passwort
	 * des Spieles uebereinstimmt, wenn nicht, wird ein ComWarning an den Client 
	 * geschickt.
	 * Ansonsten wird und der Player dem, durch Namen des Spielleiters identifizierten,
	 * Gameserver, durch Aufruf von changeServer uebergeben.
	 * Dem joinendenClient wird ein ComInitGameLobby geschickt.
	 * Durch broadcast wird im LobbyServer ein ComUpdatePlayerlist verschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param join ist das ComObject, welches angibt, dass der Player einem Spiel beitreten will
	 */
	@Override
	public synchronized void receiveMessage(Player player, ComJoinRequest join){
		if(join.getGameMasterName() != null){
			String master = join.getGameMasterName();
			GameServer joinGame = null;
			if(!gameServerSet.isEmpty()){
				for (GameServer game : gameServerSet) {
					if(game.getGameMasterName().equals(master)){
						joinGame = game;
					}
				}
			} else {
				System.err.println("Kein offenes Spiel!");
				ComWarning warning = new ComWarning("Game doesn't exist!");
				player.send(warning);
			}
			if(joinGame != null){
				if(joinGame.getRepresentation().hasPassword()){
					if(joinGame.getPassword().equals(join.getPassword())){
						if(joinGame.getRepresentation().getCurrentPlayers() < joinGame.getRepresentation().getMaxPlayers()){
							addPlayerToGame(player, joinGame);
						} else {
							ComWarning warning = new ComWarning("Game already full!");
							player.send(warning);
						}
					} else {
						ComWarning warning = new ComWarning("Wrong Password!");
						player.send(warning);
					}
				} else {
					if(joinGame.getRepresentation().getCurrentPlayers() < joinGame.getRepresentation().getMaxPlayers()){
						addPlayerToGame(player, joinGame);
					} else {
						ComWarning warning = new ComWarning("Game already full!");
						player.send(warning);
					}			
				}
			} else {
				System.err.println("Spiel nicht vorhanden!");
				ComWarning warning = new ComWarning("Game doesn't exist!");
				player.send(warning);
			}
		} else {
			System.err.println("Kein GameMaster!");
			disconnectPlayer(player);
		}
		
	}
	
	/**
	 * Diese Methode baut ein neues ComInitLobby Objekt und gibt es zurueck.
	 * @return Gibt das ComInitLobby Objekt zurueck
	 */
	public ComInitLobby initLobby(){
		List<String> playerList = new ArrayList<String>();
		Set<GameServerRepresentation> gameList = new HashSet<GameServerRepresentation>();
		if(!playerSet.isEmpty()){
			for (Player player : playerSet) {
				playerList.add(player.getPlayerName());
			}
		}
		if(!gameServerSet.isEmpty()){
			for (GameServer game : gameServerSet) {
				gameList.add(game.getRepresentation());
			}
		}
		ComInitLobby init = new ComInitLobby(playerList, gameList);
		return init;
	}
	
	/**
	 * Diese Methode schliesst die Verbindung zu einem Client.
	 * Der uebergebene Player wird aus dem playerSet sowie dem names Set 
	 * im LobbyServer entfernt.
	 * Es wird ein ComUpdatePlayerlist mit broadcast an alle Clients verschickt.
	 * @param player ist der Tread von dem die Exception kommt
	 */
	@Override
	public synchronized void disconnectPlayer(Player player) {		
		removePlayer(player);
		removeName(player.getPlayerName());
		player.closeConnection();
		broadcast(new ComUpdatePlayerlist(player.getPlayerName(), true));
	}

	/**
	 * Getter für das Set der Spielernamen
	 * @return Gibt das names Set zurück
	 */
	public Set<String> getNames() {
		return names;
	}

}