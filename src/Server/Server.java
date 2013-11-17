/**
 * 
 */
package Server;

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
	protected Set<Player> playerSet = new HashSet<Player>();
	
	
	//test
	public synchronized void receiveMessage(Player player, ComLoginRequest login){
		System.out.println("Das ist die falsche Methode");		
	}
	
	/**
	 * Diese Methode ist dafur zustaendig eine Chatnachricht an alle Clients
	 * des Servers zu verschicken. Dafuer wird die ComChatMessage mit broadcast
	 * an alle Spieler im playerSet verteilt.
	 * @param player ist der Thread der die Nachricht erhalten hat
	 * @param chat ist das ComObject, das die Chatnachricht enthaelt
	 */
	public void receiveMessage(Player player, ComChatMessage chat) {
		if(playerSet.contains(player)){
			broadcast(chat);
		} else {
			System.out.println("Der Spieler wurde nicht ekannt!");
		}			
	}

	public void receiveMessage(Player player, ComClientQuit quit) {
		System.out.println("Das ist die falsche Methode");		
	}

	public void receiveMessage(Player player, ComCreateGameRequest create) {
		System.out.println("Das ist die falsche Methode");			
	}

	public void receiveMessage(Player player, ComJoinRequest join) {
		System.out.println("Das ist die falsche Methode");			
	}

	public void receiveMessage(Player player, ComKickPlayerRequest kickPlayer) {
		System.out.println("Das ist die falsche Methode");			
	}

	public void receiveMessage(Player player, ComClientLeave leave) {
		System.out.println("Das ist die falsche Methode");			
	}

	public void receiveMessage(Player player, ComStartGame start) {
		System.out.println("Das ist die falsche Methode");			
	}

	public void receiveMessage(Player player, ComRuleset ruleset) {
		System.out.println("Das ist die falsche Methode");			
	}
	
	/**
	 * Diese Methode wird genutzt, um ein ComObject an einen einzigen
	 * Client zu verschicken. Der Player der die Nachricht verschicken soll
	 * wird Anhand des uebergebenen Benutzernamens identifiziert. 
	 * Es wird vorrausgesetzt, dass der Name und das ComObject gueltig sind.
	 * @param name ist der Name des Clients, an den der Player die Nachricht  
	 * verschicken soll
	 * @param c ist das ComObject, dass verschickt werden soll
	 */
	public synchronized void sendToPlayer(String name, ComObject com){
		for (Player player : playerSet) {
			if(player.getName() == name){
				player.send(com);
			}
		}
	}

	/**
	 * Diese Methode fuegt einen Player dem Set an Playern hinzu, welche der
	 * Server verwaltet. Es wird vorrausgesetzt, dass der Player gueltig und 
	 * noch nicht im Set vorhanden ist.
	 * @param player ist der Player, der hinzugefuegt wird
	 */
	public synchronized void  addPlayer(Player player) {
		playerSet.add(player);
	}

	/**
	 * Diese Methode entfernt einen Player aus dem Set an Playern, welche der
	 * Server verwaltet. Es wird vorrausgesetzt, dass der Player gueltig und 
	 * im Set vorhanden ist.
	 * @param player ist der Player, der entfernt wird
	 */
	public synchronized void removePlayer(Player player) {
		playerSet.remove(player);
	}

	/**
	 * Diese Methode wird genutzt, um ein ComObject an alle Clients,
	 * die vom Server verwaltet werden, zu schicken.
	 * Es wird vorrausgesetzt, dass das ComObject gueltig ist.
	 * @param com ist das ComObject, dass verschickt werden soll
	 */
	public synchronized void broadcast(ComObject com){
		if(com != null){
			for (Player player : playerSet) {
				player.send(com);
			}
		}
	}
	
	/**
	 * Diese Methode legt den Ablauf fest, was passiert, falls
	 * die Verbindung zu einem Client verloren gegangen ist.
	 * @param player ist der Tread von dem die Exception kommt
	 */
	public void handleException(Player player) {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

	

}