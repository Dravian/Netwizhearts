/**
 * 
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

import ComObjects.*;

/**
 * Diese Klasse ist für die Verwaltung der Spiellobby auf dem Server verantwortlich.
 * Sie erstellt neue Spiele und verwaltet laufende Spiele.
 * Auch wird der Chatverkehr über sie an die verbundenen Spieler weitergeleitet.
 * Die LobbyServer-Klasse erbt Methoden zur Kommunikation vom Server.
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
	 * Ein Set an GameServern, die alle erstellten Spiele repräsentieren
	 */
	private Set<GameServer> gameServerSet;
	
	/** 
	 * Ein Thread, der für das Annehmen neuer Clientverbindungen zuständig ist
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
	 * Diese innere Klasse ist für das Zustandekommen von Clientverbindungen zuständig. 
	 * Der Thread auf eingehende Clientverbindungen, stellt diese her und 
	 * instanziiert für jede Verbindung eine Klasse Player. 
	 * Dieser wird dann dem LobbyServer übergeben.
	 * @author Viktoria
	 *
	 */
	public class ClientListenerThread implements Runnable {
		/**
		 * Die run-Methode nimmt Clientverbinungen an, erstellt einen neuen Player 
		 * und fügt ihn in das noNames-Set ein
		 * Fängt eine IOException ab und gibt eine Fehlermeldung aus.
		 */
		public void run() {
			// begin-user-code
			// TODO Auto-generated method stub

			// end-user-code
		}
	}
	
	/**
	 * Fügt einen neuen Benutzennamen in das Namensset ein.
	 * Es wird vorrausgesetzt, dass der Name noch nicht im Set
	 * vorhanden ist.
	 * @param name ist der Name der eingefügt wird
	 */
	public void addName(String name) {
		names.add(name);
	}

	/**
	 * Löscht einen Benutzennamen aus dem Namensset.
	 * Es wird vorrausgesetzt, dass der Name im Set vorhanden ist.
	 * @param name ist der Name der gelöscht wird
	 */
	public void removeName(String name) {
		names.remove(name);
	}

	/**
	 * Fügt einen neuen GameServer in das gameServerSet ein.
	 * @param game ist der GameServer der eingefügt wird
	 */
	public void addGameServer(GameServer game) {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}

	/**
	 * Löscht einen GameServer aus dem Gameserverset.
	 * @param game ist der GameServer der gelöscht wird
	 */
	public void removeGameServer(GameServer game) {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}
	
	/**
	 * Diese überladene Methode ist dafür zuständig eine Chatnachricht an alle Clients im
	 * Spiel zu verschicken. Dafür wird die ComChatMessage mit broadcast
	 * an alle Spieler im playerSet verteilt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param chat ist das ComObject, das die Chatnachricht enthält
	 */
	public void receiveMessage(Player player, ComChatMessage chat){
		broadcast(chat);
	}
	/**
	 * Diese überladene Methode schließt die Verbindung, der Player wird aus dem playerSet 
	 * (bzw. noNames Set) entfernt, der Name des Players wird aus dem Set names entfernt.
	 * War der Spieler im playerSet, wird ein ComUpdatePlayerlist mit broadcast an alle 
	 * Clients verschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param quit ist das ComObject, welches angibt, dass der Spieler das 
	 * Spiel vollständig verlässt
	 */
	public void receiveMessage(Player player, ComClientQuit quit){
	}
	
	/**
	 * Diese überladene Methode erstellt einen neuen GameServer fügt ihm den Player durch
	 * aufruf von dessen changeServer Methode hinzu.
	 * Der neue GameServer wird in das gameServerSet eingefügt.
	 * Durch broadcast wird im LobbyServer sowohl ComUpdatePlayerlist als auch
	 * ein ComLobbyUpdateGamelist verschickt.
	 * Zusätzlich wird dem Client mit sendToPlayer ein ComInitGameLobby geschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param create ist das ComObject, welches angibt, dass der Player 
	 * ein neues Spiel erstellt hat
	 */
	public void receiveMessage(Player player, ComCreateGameRequest create){
	}
	
	/**
	 * Diese überladene Methode fügt einen Player dem entsprechenden GameServer hinzu.
	 * Falls das Passwort nicht leer ist wird geprüft, ob es mit dem Passwort
	 * des Spieles übereinstimmt, wenn nicht, wird ein ComWarning an den Client 
	 * geschickt.
	 * Ansonsten wird und der Player dem, durch Namen des Spielleiters identifizierten,
	 * durch Aufruf von changeServer Gameserver übergeben.
	 * Dem joinendenClient wird mit sendToPlayer ein ComInitGameLobby geschickt.
	 * Durch broadcast wird sowohl im LobbyServer ein ComUpdatePlayerlist verschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param join ist das ComObject, welches angibt, dass der Player einem Spiel beitreten will
	 */
	public void receiveMessage(Player player, ComJoinRequest join){
		
	}
	
	/**
	 * Diese überladene Methode überprüft, ob der Name im Set names vorhanden ist, 
	 * falls ja, wird ein ComWarning an den Client geschickt, dass der Name bereits 
	 * vergeben ist, falls nein, wird im Player setName aufgerufen.
	 * Der Player wird aus dem noNames Set entfernt und in das playerSet eingefügt.
	 * Der Name wird in das Set names eingefügt. Dem Client wird ein 
	 * ComServerAcknowledgement geschickt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param login ist das ComObject, dass den Benutzernamen des Clients enthält
	 */
	public void receiveMessage(Player player, ComLoginRequest login){
		
	}
	
	/**
	 * Diese Methode baut ein neues ComInitLobby Objekt und gibt es zurück.
	 * @return Gibt das ComInitLobby Objekt zurück
	 */
	public ComInitLobby initLobby(){
		return null;
	}
	
	/**
	 * Diese Methode legt den Ablauf fest, was passiert, falls
	 * die Verbindung zu einem Client verloren gegangen ist.
	 * Der übergebene Player wird aus dem playerSet sowie dem names Set 
	 * im LobbyServer entfernt.
	 * @param player ist der Tread von dem die IOException kommt
	 */
	public void handleIOException(Player player) {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

}