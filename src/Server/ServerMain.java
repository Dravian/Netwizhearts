/**
 * 
 */
package Server;

/**
 * Diese Klasse startet den Server und ist für die 
 * Konfigurationund Wartung des Servers verantwortlich.
 * @author Viktoria
 *
 */
public class ServerMain {
	/**
	 * LobbyServer, der beim Start erstellt wird
	 */
	private LobbyServer lobbyServer;
	
	/**
	 * Die main-Methode erstellt einen neuen LobbyServer	
	 * @param args
	 */
	public static void main(String[] args){
		new LobbyServer();
	}
}