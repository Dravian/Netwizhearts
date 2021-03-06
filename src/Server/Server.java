package Server;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ComObjects.*;

/**
 * Server. Ist eine abstrakte Klasse, von der die Klassen LobbyServer und
 * GameServer erben. Es stellt Methoden zur Nachrichtenversendung und
 * -verarbeitung bereit, sowie zur Verwaltung von Playern
 */
public abstract class Server {
	/**
	 * Ein Set an Spielern, welche momentan vom Server verwaltet werden
	 */
	protected Set<Player> playerSet = Collections.synchronizedSet(new HashSet<Player>());

	/**
	 * Diese Methode verarbeitet eine Nachricht, die von einem Client kommt
	 * 
	 * @param player
	 *            ist der Thread von dem die Nachricht kommt
	 * @param object
	 *            ist das zu behandelnde Object
	 */
	public synchronized void receiveMessage(Player player, ComObject object) {
		System.err.println("Das ist die falsche Methode");
		disconnectPlayer(player);
	}

	/**
	 * Diese Methode verarbeitet eine Nachricht, die von einem Client kommt
	 * 
	 * @param player
	 *            ist der Thread von dem die Nachricht kommt
	 * @param login
	 *            ist das zu behandelnde Object
	 */
	public synchronized void receiveMessage(Player player, ComLoginRequest login) {
		System.err.println("Das ist die falsche login Methode");
		disconnectPlayer(player);
	}

	/**
	 * Diese Methode verarbeitet eine Nachricht, die von einem Client kommt
	 * 
	 * @param player
	 *            ist der Thread von dem die Nachricht kommt
	 * @param chat
	 *            ist das zu behandelnde Object
	 */
	public void receiveMessage(Player player, ComChatMessage chat) {
		System.err.println("Das ist die falsche chat Methode");
		disconnectPlayer(player);
	}

	/**
	 * Diese Methode schliesst die Verbindung, indem disconnectPlayer mit dem
	 * entsprechenden Player �bergeben, aufgerufen wird.
	 * 
	 * @param player
	 *            ist der Thread von dem die Nachricht kommt
	 * @param quit
	 *            ist das ComObject, welches angibt, dass der Spieler das Spiel
	 *            vollstaendig verlaesst
	 */
	public  void receiveMessage(Player player, ComClientQuit quit) {
		if (playerSet.contains(player)) {
			disconnectPlayer(player);
		} else {
			System.err.println("Player not found!");
		}	
	}

	/**
	 * Diese Methode verarbeitet eine Nachricht, die von einem Client kommt
	 * 
	 * @param player
	 *            ist der Thread von dem die Nachricht kommt
	 * @param create
	 *            ist das zu behandelnde Object
	 */
	public void receiveMessage(Player player, ComCreateGameRequest create) {
		System.err.println("Das ist die falsche create Methode");
		disconnectPlayer(player);
	}

	/**
	 * Diese Methode verarbeitet eine Nachricht, die von einem Client kommt
	 * 
	 * @param player
	 *            ist der Thread von dem die Nachricht kommt
	 * @param join
	 *            ist das zu behandelnde Object
	 */
	public void receiveMessage(Player player, ComJoinRequest join) {
		System.err.println("Das ist die falsche join Methode");
		disconnectPlayer(player);
	}

	/**
	 * Diese Methode verarbeitet eine Nachricht, die von einem Client kommt
	 * 
	 * @param player
	 *            ist der Thread von dem die Nachricht kommt
	 * @param kickPlayer
	 *            ist das zu behandelnde Object
	 */
	public void receiveMessage(Player player, ComKickPlayerRequest kickPlayer) {
		System.err.println("Das ist die falsche kick Methode");
		disconnectPlayer(player);
	}

	/**
	 * Diese Methode verarbeitet eine Nachricht, die von einem Client kommt
	 * 
	 * @param player
	 *            ist der Thread von dem die Nachricht kommt
	 * @param leave
	 *            ist das zu behandelnde Object
	 */
	public void receiveMessage(Player player, ComClientLeave leave) {
		System.err.println("Das ist die falsche Methode");
		disconnectPlayer(player);
	}

	/**
	 * Diese Methode verarbeitet eine Nachricht, die von einem Client kommt
	 * 
	 * @param player
	 *            ist der Thread von dem die Nachricht kommt
	 * @param start
	 *            ist das zu behandelnde Object
	 */
	public void receiveMessage(Player player, ComStartGame start) {
		System.err.println("Das ist die falsche start Methode");
		disconnectPlayer(player);
	}

	/**
	 * Diese Methode verarbeitet eine Nachricht, die von einem Client kommt
	 * 
	 * @param player
	 *            ist der Thread von dem die Nachricht kommt
	 * @param ruleset
	 *            ist das zu behandelnde Object
	 */
	public void receiveMessage(Player player, ComRuleset ruleset) {
		System.err.println("Das ist die falsche ruleset Methode");
		disconnectPlayer(player);
	}

	public void receiveMessage(Player player, ComNewRound request) {
		System.err.println("Das ist die falsche ruleset Methode");
		disconnectPlayer(player);
	}
	/**
	 * Diese Methode wird genutzt, um ein ComObject an einen einzigen Client zu
	 * verschicken. Der Player der die Nachricht verschicken soll wird Anhand
	 * des uebergebenen Benutzernamens identifiziert. Es wird vorrausgesetzt,
	 * dass der Name und das ComObject gueltig sind.
	 * 
	 * @param name
	 *            ist der Name des Clients, an den der Player die Nachricht
	 *            verschicken soll
	 * @param c
	 *            ist das ComObject, dass verschickt werden soll
	 */
	public void sendToPlayer(String name, ComObject com) {
		synchronized(playerSet){
			for (Player player : playerSet) {
				if (player.getPlayerName().equals(name)) {
					player.send(com);
				}
			}
		}		
	}

	/**
	 * Diese Methode fuegt einen Player dem Set an Playern hinzu, welche der
	 * Server verwaltet. Es wird vorrausgesetzt, dass der Player gueltig und
	 * noch nicht im Set vorhanden ist.
	 * 
	 * @param player
	 *            ist der Player, der hinzugefuegt wird
	 */
	public void addPlayer(Player player) {
		synchronized(playerSet){
			playerSet.add(player);			
		}
	}

	/**
	 * Diese Methode entfernt einen Player aus dem Set an Playern, welche der
	 * Server verwaltet. Es wird vorrausgesetzt, dass der Player gueltig und im
	 * Set vorhanden ist.
	 * 
	 * @param player
	 *            ist der Player, der entfernt wird
	 */
	public void removePlayer(Player player) {
		synchronized(playerSet){
			playerSet.remove(player);			
		}
	}

	/**
	 * Diese Methode wird genutzt, um ein ComObject an alle Clients, die vom
	 * Server verwaltet werden, zu schicken. Es wird vorrausgesetzt, dass das
	 * ComObject gueltig ist.
	 * 
	 * @param com
	 *            ist das ComObject, dass verschickt werden soll
	 */
	public void broadcast(ComObject com) {
		if (com != null) {
			synchronized(playerSet){
				for (Player player : playerSet) {
					player.send(com);
				}	
			}		
		}
	}

	/**
	 * Diese Methode schliesst die Verbindung zu einem Client.
	 * 
	 * @param player
	 *            ist der Spieler der entfernt wird
	 */
	public void disconnectPlayer(Player player) {
		player.closeConnection();
	}

}