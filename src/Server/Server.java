/**
 * 
 */
package Server;

import java.util.Iterator;
import java.util.Set;

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
	 * Client zu verschicken.
	 * @param player ist der Player, der die Nachricht an seinen Client 
	 * verschicken soll
	 * @param c ist das ComObject, dass verschickt werden soll
	 */
	public void sendToPlayer(Player player, ComObject com) {
		player.send(com);
	}

	/**
	 * Diese Methode fügt einen Player dem Set an Playern hinzu, welche der
	 * Server verwaltet.
	 * @param player ist der Player, der hinzugefoügt wird
	 */
	public void addPlayer(Player player) {
		playerSet.add(player);
	}

	/**
	 * Diese Methode entfernt einen Player aus dem Set an Playern, welche der
	 * Server verwaltet.
	 * @param player ist der Player, der entfernt wird
	 */
	public void removePlayer(Player player) {
		playerSet.remove(player);
	}

	/**
	 * Diese Methode wird genutzt, um ein ComObject an alle Clients,
	 * die vom Server verwaltet werden zu schicken.
	 * @param com ist das ComObject, dass verschickt werden soll
	 */
	public void broadcast(ComObject com) {
		Iterator<Player> i= playerSet.iterator();
		if(i.hasNext()){
			i.next().send(com);
		}
	}
}