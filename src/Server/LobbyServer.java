/**
 * 
 */
package Server;

import java.net.ServerSocket;
import java.util.Set;

import ComObjects.*;

/**
 * Diese Klasse ist f�r die Verwaltung der Spiellobby auf dem Server verantwortlich.
 * Sie erstellt neue Spiele und verwaltet laufende Spiele.
 * Auch wird der Chatverkehr �ber sie an die verbundenen Spieler weitergeleitet.
 * Die LobbyServer-Klasse erbt Methoden zur Kommunikation vom Server.
 * @author Viktoria
 *
 */
public class LobbyServer extends Server {
	/** 
	 * Ein Set der Benutzernamen aller Spieler, die in der Lobby oder einem Spiel sind
	 */
	private Set<String> names;
	
	/** 
	 * Ein Set der Spieler, die noch keinen Namen haben und noch nicht in der Lobby sind
	 */
	private Set<Player> noNames;
	
	/** 
	 * Ein Set an GameServern, die alle erstellten Spiele repr�sentieren
	 */
	private Set<GameServer> gameServerSet;
	
	/** 
	 * Ein Threat, der f�r das Annehmen neuer Clientverbindungen zust�ndig ist
	 */
	private ClientListenerThread clientListenerThread;
	
	/**
	 * Der Server Socket
	 */
	private ServerSocket socket;

	/**
	 * Erstellt und Startet den ClientListenerThread
	 */
	public LobbyServer(){
		
	}
	/**
	 * Diese Klasse ist f�r das Zustandekommen von Clientverbindungen zust�ndig. 
	 * Der Thread auf eingehende Clientverbindungen, stellt diese her und 
	 * instanziiert f�r jede Verbindung eine Klasse Player. 
	 * Dieser wird dann dem LobbyServer �bergeben.
	 * @author Viktoria
	 *
	 */
	public class ClientListenerThread implements Runnable {
		/**
		 * Die run-Methode nimmt Clientverbinungen an, erstellt einen neuen Player 
		 * und f�gt ihn in das noNames-Set ein
		 */
		public void run() {
			// begin-user-code
			// TODO Auto-generated method stub

			// end-user-code
		}
	}
	
	/**
	 * F�gt einen neuen Benutzennamen in das Namensset ein
	 * @param name ist der Name der eingef�gt wird
	 */
	private void addName(String name) {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}

	/**
	 * L�scht einen Benutzennamen aus dem Namensset
	 * @param name ist der Name der gel�scht wird
	 */
	private void removeName(String name) {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}

	/**
	 * F�gt einen neuen GameServer in das Gameserverset ein
	 * @param game ist der GameServer der eingef�gt wird
	 */
	private void addGameServer(GameServer game) {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}

	/**
	 * L�scht einen GameServer aus dem Gameserverset
	 * @param game ist der GameServer der gel�scht wird
	 */
	private void removeGameServer(GameServer game) {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}
	
	/**
	 * Diese Methode ist daf�r zust�ndig eine Chatnachricht an alle Clients im
	 * Spiel zu verschicken. Daf�r wird die ComChatMessage mit broadcast
	 * an alle Spieler im playerSet verteilt.
	 * @param player ist der Threat der die Nachricht erhalten hat
	 * @param chat ist das ComObject, das die Chatnachricht enth�lt
	 */
	public void receiveMessage(Player player, ComChatMessage chat){

	}
	/**
	 * Diese Methode schlie�t die Verbindung, der Player wird aus dem playerSet 
	 * (bzw. noNames Set) entfernt, der Name des Players wird aus dem Set names entfernt.
	 * War der Spieler im playerSet, wird ein ComUpdatePlayerlist mit broadcast an alle 
	 * Clients verschickt.
	 * @param player ist der Threat der die Nachricht erhalten hat
	 * @param quit ist das ComObject, welches angibt, dass der Spieler das 
	 * Spiel vollst�ndig verl�sst
	 */
	public void receiveMessage(Player player, ComClientQuit quit){

	}
	
	/**
	 * Diese Methode erstellt einen neuen GameServer f�gt ihm den  Player hinzu.
	 * Durch broadcast wird sowohl im LobbyServer als auch im GameServer ein 
	 * ComUpdatePlayerlist verschickt.
	 * Zus�tzlich wird dem Client mit sendToPlayer ein ComInitGameLobby geschickt.
	 * @param player ist der Threat der die Nachricht erhalten hat
	 * @param create ist das ComObject, welches angibt, dass der Player 
	 * ein neues Spiel erstellt hat
	 */
	public void receiveMessage(Player player, ComCreateGameRequest create){

	}
	
	/**
	 * Diese Methode f�gt einen Player dem entsprechenden GameServer hinzu.
	 * Falls das Passwort nicht leer ist wird gepr�ft, ob es mit dem Passwort
	 * des Spieles �bereinstimmt, wenn nicht, wird ein ComWarning an den Client 
	 * geschickt.
	 * Ansonsten wird und der Player dem, durch Namen des Spielleiters identifizierten,
	 * durch Aufruf von changeServer Gameserver �bergeben.
	 * Durch broadcast wird sowohl im LobbyServer als auch im GameServer ein 
	 * ComUpdatePlayerlist verschickt.
	 * Zus�tzlich wird dem joinendenClient mit sendToPlayer ein ComInitGameLobby geschickt.
	 * @param player ist der Threat der die Nachricht erhalten hat
	 * @param join ist das ComObject, welches angibt, dass der Player einem Spiel beitreten will
	 */
	public void receiveMessage(Player player, ComJoinRequest join){

	}
	/**
	 * Diese Methode �berpr�ft, ob der Name im Set names vorhanden ist, falls ja, wird ein 
	 * ComWarning an den Client geschickt, falls nein, wird im Player setName aufgerufen.
	 * Der Player wird aus dem noNames Set entfernt und in das playerSet eingef�gt.
	 * Der Name wird in das Set names eingef�gt. Dem Client wird ein 
	 * ComServerAcknowledgement geschickt.
	 * @param player ist der Threat der die Nachricht erhalten hat
	 * @param login ist das ComObject, dass den Benutzernamen des Clients enth�lt
	 */
	public void receiveMessage(Player player, ComLoginRequest login){

	}
	
	/**
	 * Baut ein neues ComInitLobby Objekt und gibt es zur�ck.
	 * @return Gibt das ComInitLobby Objekt zur�ck
	 */
	public ComInitLobby initLobby(){
		return null;
	}
}