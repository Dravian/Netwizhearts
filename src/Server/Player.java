/**
 * 
 */
package Server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ComObjects.ComObject;
import ComObjects.ComUpdatePlayerlist;

/**
 * Die Player-Klasse wird zum Versenden von Java Serializable Objects verwendet.
 * Sie verwaltet für die Dauer einer Serververbindung die Verbindung zum Client
 * @author Viktoria
 *
 */
public class Player implements Runnable{
	/**
	 * Der eindeutige Benutzername des Spielers.
	 */
	private String name;
	/**
	 * Der Server, an den eingehende ComObjects übergeben werden sollen
	 */
	private Server server;
	/**
	 * ObjectOutputStream, um für Nachrichten an den Client zu Senden
	 */
	private ObjectOutputStream comOut;
	/**
	 * ObjectInputStream, um für Nachrichten vom Client entgegenzunehmen
	 */
	private ObjectInputStream comIn;

	/**
	 * Konstruktor des Players, in ihm werden die Attribute server, comOut und ComIn mit
	 * vom ClientListererThread übergebenen werten Instanziiert.
	 * @param lobbyServer ist der LobbyServer, der zu Beginn den Player verwaltet.
	 * @param output ist der ObjectOutputStream an den entsprechenden Client
	 * @param input ist der ObjectInputStream vom entsprechenden Client
	 */
	public Player(Server lobbyServer, ObjectOutputStream output, ObjectInputStream input){
		server = lobbyServer;
		comOut = output;
		comIn = input;
	}
	
	/**
	 * Die run-Methode des Thread nimmt eingehende Nachrichten des Client
	 * entgegen und übergibt diese an den Server durch Aufruf der Methode 
	 * resolveMessage()
	 * Fängt eine IOException ab.
	 */
	public void run(){
		// begin-user-code
		// TODO Auto-generated method stub
		// end-user-code
	}

	/**
	 * Diese Methode schickt ein ComObjekt an den Client
	 * @param com ist das ComObject das verschickt wird
	 * @throws IOException wenn der Output nicht funktioniert
	 */
	public void send(ComObject com) throws IOException{
		comOut.writeObject(com);
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}

	/**
	 * Diese Methode wechselt beim Player den Server an den er comObjects
	 * weiterleiten soll. Dabei wird er aus dem playerSet des alten Servers
	 * entfernt und in das playerSet des neuen Players eingefugt. 
	 * Danach wird vom neuen Server ein ComUpdatePlayerlist Objekt mit broadcast 
	 * an alle Clients, die vom Server verwaltet werden, verschickt.
	 * @param newServer ist der neue Server
	 * @throws IOException wenn der Output nicht funktioniert
	 */
	public void changeServer(Server newServer) throws IOException {
		server.removePlayer(this);
		server = newServer;
		server.addPlayer(this);
		server.broadcast(new ComUpdatePlayerlist(this.getName(),false));
		// begin-user-code
		// TODO Auto-generated method stub
		// end-user-code	
	}
	
	/**
	 * Getter-Methode für den Benutzernamen
	 * @return gibt den Benutzernamen des Spielers zurück
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Setter-Methode für den Benutzernamen
	 * @param newName ist der neue Name
	 */
	public void setName(String newName){
		name = newName;
	}
}