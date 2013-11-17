package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;

import ComObjects.*;

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
	 * Ein Thread, der fuer das Annehmen neuer Clientverbindungen zustaendig ist
	 */
	private Thread clientListenerThread;
	
	/**
	 * Der Server Socket
	 */
	private ServerSocket socket;

	/**
	 * Erstellt und Startet den ClientListenerThread.
	 * Faengt eine IOException und gibt eine Fehlermeldung aus
	 */
	public LobbyServer(){
		try {
			socket = new ServerSocket(4567);
		} catch (IOException e) {
			System.out.println("Could not listen on port: 4567.");
			e.printStackTrace();
			System.exit(1);			
		}
		clientListenerThread = new Thread(new ClientListenerThread(this));
		clientListenerThread.start();
	}
	
	/**
	 * ClientListenerThread. Diese innere Klasse ist fuer das Zustandekommen 
	 * von Clientverbindungen zustaendig. 
	 * Der Thread wartet auf eingehende Clientverbindungen, stellt diese her und 
	 * instanziiert fuer jede Verbindung eine Klasse Player. 
	 * Dieser wird dann dem LobbyServer uebergeben.
	 * 
	 */
	public class ClientListenerThread extends Thread {
		
		/**
		 * Zeigt, ob der Tread auf verbindungen wartet
		 */
		private boolean waiting;
		
		private LobbyServer server;
		
		/**
		 * Konstruktor des ClientListenerThreads
		 */
		public ClientListenerThread(LobbyServer server){
			super("CLT");
			waiting = true;
			this.server = server;
		}
		
		/**
		 * Die run-Methode nimmt Clientverbinungen an, erstellt einen neuen Player 
		 * und fuegt ihn in das noNames-Set ein
		 * Faengt IOExceptions ab und gibt Fehlermeldungen aus.
		 */
		@Override
		public void run() {
			while(waiting){
				Socket cSocket;
	        	try {
					cSocket = socket.accept();
					Player player = new Player(server, cSocket);
					player.start();
				} catch (IOException e) {
					System.err.println("Couldn't connect with Socket");
					e.printStackTrace();
				}        	
	        }
		}
	}
	
	/**
	 * Fuegt einen neuen Benutzennamen in das Namensset ein.
	 * Es wird vorrausgesetzt, dass der Name noch nicht im Set
	 * vorhanden ist.
	 * @param name ist der Name der eingefuegt wird
	 */
	public synchronized void addName(String name) {
		names.add(name);
	}

	/**
	 * Loescht einen Benutzennamen aus dem Namensset.
	 * Es wird vorrausgesetzt, dass der Name im Set vorhanden ist.
	 * @param name ist der Name der geloescht wird
	 */
	public synchronized void removeName(String name) {
		names.remove(name);
	}

	/**
	 * Fuegt einen neuen GameServer in das gameServerSet ein.
	 * @param game ist der GameServer der eingefuegt wird
	 */
	public synchronized void addGameServer(GameServer game) {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}

	/**
	 * Loescht einen GameServer aus dem Gameserverset.
	 * @param game ist der GameServer der geloescht wird
	 */
	public synchronized void removeGameServer(GameServer game) {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}
	
	/**
	 * Diese ueberladene Methode schliesst die Verbindung, der Player wird aus dem playerSet 
	 * (bzw. noNames Set) entfernt, der Name des Players wird aus dem Set names entfernt.
	 * War der Spieler im playerSet, wird ein ComUpdatePlayerlist mit broadcast an alle 
	 * Clients verschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param quit ist das ComObject, welches angibt, dass der Spieler das 
	 * Spiel vollstaendig verlaesst
	 */
	@Override
	public synchronized void receiveMessage(Player player, ComClientQuit quit){
		if(playerSet.contains(player)){
		
		} else {
			System.out.println("Der Spieler wurde nicht ekannt!");
		}
		// TODO Auto-generated method stub
	}
	
	/**
	 * Diese ueberladene Methode erstellt einen neuen GameServer fuegt ihm den Player durch
	 * Aufruf von dessen changeServer Methode hinzu.
	 * Der neue GameServer wird in das gameServerSet eingefuegt.
	 * Durch broadcast wird im LobbyServer sowohl ComUpdatePlayerlist als auch
	 * ein ComLobbyUpdateGamelist verschickt.
	 * Zusaetzlich wird dem Client mit sendToPlayer ein ComInitGameLobby geschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param create ist das ComObject, welches angibt, dass der Player 
	 * ein neues Spiel erstellt hat
	 */
	@Override
	public synchronized void receiveMessage(Player player, ComCreateGameRequest create){
		if(playerSet.contains(player)){
			
		} else {
			System.out.println("Der Spieler wurde nicht ekannt!");
		}
		// TODO Auto-generated method stub
	}
	
	/**
	 * Diese ueberladene Methode fuegt einen Player dem entsprechenden GameServer hinzu.
	 * Falls das Passwort nicht leer ist wird geprueft, ob es mit dem Passwort
	 * des Spieles uebereinstimmt, wenn nicht, wird ein ComWarning an den Client 
	 * geschickt.
	 * Ansonsten wird und der Player dem, durch Namen des Spielleiters identifizierten,
	 * Gameserver, durch Aufruf von changeServer uebergeben.
	 * Dem joinendenClient wird mit sendToPlayer ein ComInitGameLobby geschickt.
	 * Durch broadcast wird sowohl im LobbyServer ein ComUpdatePlayerlist verschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param join ist das ComObject, welches angibt, dass der Player einem Spiel beitreten will
	 */
	@Override
	public synchronized void receiveMessage(Player player, ComJoinRequest join){
		if(playerSet.contains(player)){
			
		} else {
			System.out.println("Der Spieler wurde nicht ekannt!");
		}
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Diese ueberladene Methode ueberprueft, ob der Name im Set names vorhanden ist, 
	 * falls ja, wird ein ComWarning an den Client geschickt, dass der Name bereits 
	 * vergeben ist, falls nein, wird im Player setName aufgerufen.
	 * Der Player wird in das playerSet eingefuegt.
	 * Der Name wird in das Set names eingefuegt. Dem Client wird ein 
	 * ComServerAcknowledgement geschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param login ist das ComObject, dass den Benutzernamen des Clients enthält
	 */
	@Override
	public synchronized void receiveMessage(Player player, ComLoginRequest login){
		System.out.println("login");
		String CheckName = login.getPlayerName();
		if(names.contains(CheckName)){
			ComWarning warning = new ComWarning("Login Fehler!");
			player.send(warning);
		} else {
			player.setName(CheckName);
			addPlayer(player);
			addName(CheckName);
			ComInitLobby init = initLobby();
			player.send(init);
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
			playerList = new ArrayList<String>();
			for (Player player : playerSet) {
				playerList.add(player.getName());
			}
		} else {
			playerList = null;
		}
		if(!gameServerSet.isEmpty()){
			gameList = new HashSet<GameServerRepresentation>();
			for (GameServer game : gameServerSet) {
				gameList.add(game.getRepresentation());
			}
		} else {
			gameList = null;
		}
		ComInitLobby init = new ComInitLobby(playerList, gameList);
		return init;
	}
	
	/**
	 * Diese Methode legt den Ablauf fest, was passiert, falls
	 * die Verbindung zu einem Client verloren gegangen ist.
	 * Der uebergebene Player wird aus dem playerSet sowie dem names Set 
	 * im LobbyServer entfernt.
	 * @param player ist der Tread von dem die Exception kommt
	 */
	@Override
	public synchronized void handleException(Player player) {
		removePlayer(player);
		removeName(player.getPlayerName());	
	}

}