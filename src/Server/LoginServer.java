package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ComObjects.*;

public class LoginServer extends Server {
	/**
	 * Ein Thread, der fuer das Annehmen neuer Clientverbindungen zustaendig ist
	 */
	private Thread clientListenerThread;

	/**
	 * Der LobbyServer
	 */
	private LobbyServer lobby;

	/**
	 * Konstruktor für den LoginServer. Erstellt und Startet den ClientListenerThread und
	 * setzt den LobbyServer auf den übergebenen Wert.
	 * @param server ist der LobbyServer
	 * @param port ist der Serverport
	 */
	public LoginServer(LobbyServer server, int port) {
		lobby = server;
		clientListenerThread = new Thread(new ClientListenerThread(this, port));
		clientListenerThread.start();
	}

	/**
	 * ClientListenerThread. Diese innere Klasse ist fuer das Zustandekommen von
	 * Clientverbindungen zustaendig. Der Thread wartet auf eingehende
	 * Clientverbindungen, stellt diese her und instanziiert fuer jede
	 * Verbindung eine Klasse Player. Dieser wird dann dem LobbyServer
	 * uebergeben.
	 * 
	 */
	public class ClientListenerThread extends Thread {

		/**
		 * Zeigt, ob der Tread auf verbindungen wartet
		 */
		private boolean waiting;
		/**
		 * Der Server Socket
		 */
		private ServerSocket socket;
		/**
		 * Der LoginServer
		 */
		private LoginServer server;


		/**
		 * Konstruktor des ClientListenerThreads. Erstellt einen neuen Serversocket
		 * mit dem übergebenen Port.
		 * @param server ist der LobbyServer
		 * @param port ist der Serverport
		 */
		public ClientListenerThread(LoginServer server, int port) {
			super("CLT");
			try {
				socket = new ServerSocket(port);
			} catch (IOException e) {
				System.err.println("Could not listen on port: "+port);
				e.printStackTrace();
				System.exit(0);
			}
			waiting = true;
			this.server = server;
		}

		/**
		 * Die run-Methode nimmt Clientverbinungen an, erstellt einen neuen
		 * Player und fuegt ihn in das noNames-Set ein Faengt IOExceptions ab
		 * und gibt Fehlermeldungen aus.
		 */
		@Override
		public void run() {
			while (waiting) {
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
	 * Diese ueberladene Methode ueberprueft, ob der Name im Set names des
	 * LobbyServers vorhanden ist, falls ja, wird ein ComWarning an den Client
	 * geschickt, dass der Name bereits vergeben ist, falls nein, wird im Player
	 * setName aufgerufen. Der Player wecheselt auf den LobbyServer, sein Name
	 * wird in das names Set eingefügt. Dem Client wird ein ComInitLobby
	 * geschickt.
	 * 
	 * @param player
	 *            ist der Thread der die Nachricht erhalten hat
	 * @param login
	 *            ist das ComObject, dass den Benutzernamen des Clients enthält
	 */
	@Override
	public synchronized void receiveMessage(Player player, ComLoginRequest login) {
		if (login.getPlayerName() != null) {
			String CheckName = login.getPlayerName();
			if (lobby.getNames().contains(CheckName)) {
				ComWarning warning = new ComWarning(WarningMsg.LoginError);
				player.send(warning);
				disconnectPlayer(player);
			} else {
				player.setPlayerName(CheckName);
				lobby.addName(CheckName);
				removePlayer(player);
				player.changeServer(lobby);
				ComInitLobby init = lobby.initLobby();
				player.send(init);
			}
		} else {
			ComWarning warning = new ComWarning(WarningMsg.LoginError);
			player.send(warning);
		}

	}

	/**
	 * Diese Methode schliesst die Verbindung zu einem Client. Der uebergebene
	 * Player wird aus dem playerSet sowie dem names Set im LobbyServer
	 * entfernt.
	 * 
	 * @param player
	 *            ist der Spieler der entfernt wird
	 */
	@Override
	public synchronized void disconnectPlayer(Player player) {
		player.closeConnection();
	}
}
