/**
 * 
 */
package ComObjects;

/** 
 * Die Klasse ComObject ist eine Klasse, die ein Objekt darstellt,
 * das zur Kommunikation genutzt werden kann. Spezielle ComObject Klassen
 * erben von dieser grundlegenden Klasse.
 */

import java.io.Serializable;

import Server.Player;
import Server.Server;
import Client.ClientModel;

public interface ComObject {

	/**
	 * Überladene Methode die von dem ClientListenerThread nach
	 * empfang einer Nachricht aufgerufen wird.
	 * @param model Das Client Model,
	 * an das sich das ComObjekt
	 * weitergibt.
	 */
	public void process(ClientModel model);
		//model.receiveMessage(this);

	
	/**
	 * Überladene Methode die von einem PlayerThread nach
	 * empfang einer Nachricht aufgerufen wird.
	 * @param player Der Client welcher den Aufruf startet.
	 * @param server Der Server an den sich das ComObjekt 
	 * weitergibt.
	 */
	public void process(Player player, Server server);
		//server.receiveMessage(player,this);

}