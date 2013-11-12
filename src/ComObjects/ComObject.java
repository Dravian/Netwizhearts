package ComObjects;

import java.io.Serializable;

import Server.Player;
import Server.Server;
import Client.ClientModel;

/**
 * ComObject.
 * Die Klasse ComObject ist ein Interface, welches ein Objekt darstellt,
 * das zur Kommunikation genutzt werden kann. Spezielle ComObject Klassen
 * implementieren diese grundlegenden Klasse.
 */
public interface ComObject {

    /**
     * Diese Methode ist noetig, damit der ClientListenerThread entscheiden kann
     * welche Message das Object enthaelt und wie diese verarbeitet werden soll.
	 * @param model ist das ClientModel, welches übergeben wird, damit
     *              die ueberladene Methode richtig gewaehlt wird.
	 */
	public void process(ClientModel model);

    /**
     * Diese Methode ist noetig, damit der Thread Player entscheiden kann
     * welche Message das Object enthaelt und wie diese verarbeitet werden soll.
	 * @param player    Der Client welcher den Aufruf startet.
	 * @param server    Der Server an den sich das ComObjekt weitergibt.
	 */
	public void process(Player player, Server server);
}