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
		int port;
		try {
			if (args.length == 0 || args[0].isEmpty()){
				port = 4567;
				lobbyServer = new LobbyServer();
				loginServer = new LoginServer(lobbyServer, port);
			} else {
				port = Integer.parseInt(args[0]);
				if (port < 1025 || port > 49151) {
					System.err.println("Invalid Portnumber, try again.");	
				} else {
					lobbyServer = new LobbyServer();
					loginServer = new LoginServer(lobbyServer, port);
				}
			}		
		} catch (NumberFormatException e) {
			System.err.println("Please input a number!");
		}						
	}
}