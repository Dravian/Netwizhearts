package Server;

/**
 * ServerMain. Diese Klasse startet den Server und ist fuer die Konfiguration
 * des Servers verantwortlich.
 */
public class ServerMain {
	/**
	 * LobbyServer, der beim Start erstellt wird
	 */
	private static LobbyServer lobbyServer;

	/**
	 * LoginServer, der beim Start erstellt wird
	 */
	private static LoginServer loginServer;

	/**
	 * Die main-Methode erstellt einen neuen LobbyServer
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		lobbyServer = new LobbyServer();
		loginServer = new LoginServer(lobbyServer);
	}
}