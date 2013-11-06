/**
 * 
 */
package Server;

import java.util.Iterator;
import java.util.Set;

import javax.activation.CommandObject;

import ComObjects.*;

/**
 * Ist ein abstrakte Klasse, von der die Klassen LobbyServer und
 * GameServer erben. Es stellt Methoden zur Nachrichtenversendung und
 * -verarbeitung bereit, sowie zur Verwaltung von Playern
 * @author Viktoria
 *
 */
public abstract class Server {
	/**
	 * Ein Set an Spielern, welche momentan vom Server verwaltet werden
	 */
	protected Set<Player> playerSet;

	
	/**
	 * Diese Methode dient zur Verarbeitung von eingehenden ComObjects 
	 * @param player ist der Player von dem die Nachricht kommt
	 * @param com ist das ComObjekt vom Client verschickt wurde
	 */
	public void receiveMessage(Player player, ComObject com) {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}

	/**
	 * Diese Methode wird genutzt, um ein ComObject an einen einzigen
	 * Client zu verschicken. Der Player der die Nachricht verschicken soll
	 * wird Anhand des �bergebenen Benutzernamens identifiziert.
	 * @param name ist der Name des Clients, an den der Player die Nachricht  
	 * verschicken soll
	 * @param c ist das ComObject, dass verschickt werden soll
	 */
	public synchronized void sendToPlayer(String name, ComObject com) {
		// begin-user-code
				// TODO Auto-generated method stub

				// end-user-code
	}

	/**
	 * Diese Methode f�gt einen Player dem Set an Playern hinzu, welche der
	 * Server verwaltet.
	 * @param player ist der Player, der hinzugefo�gt wird
	 */
	public synchronized void  addPlayer(Player player) {
		playerSet.add(player);
	}

	/**
	 * Diese Methode entfernt einen Player aus dem Set an Playern, welche der
	 * Server verwaltet.
	 * @param player ist der Player, der entfernt wird
	 */
	public synchronized void removePlayer(Player player) {
		playerSet.remove(player);
	}

	/**
	 * Diese Methode wird genutzt, um ein ComObject an alle Clients,
	 * die vom Server verwaltet werden, zu schicken.
	 * @param com ist das ComObject, dass verschickt werden soll
	 */
	public synchronized void broadcast(ComObject com) {
		
	}

}