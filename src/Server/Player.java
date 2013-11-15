package Server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ComObjects.*;

/**
 * Player. Die Player-Klasse wird zum Versenden von Java Serializable Objects, sowie zum
 * Annehmen solcher verwendet.
 * Sie verwaltet fuer die Dauer einer Serververbindung die Verbindung zu einem Client.
 */
public class Player implements Runnable{
	/**
	 * Der eindeutige Benutzername des Spielers.
	 */
	private String name;
	/**
	 * Der Server, an den eingehende ComObjects uebergeben werden sollen
	 */
	private Server server;
	/**
	 * ObjectOutputStream, um fuer Nachrichten an den Client zu Senden
	 */
	private ObjectOutputStream comOut;
	/**
	 * ObjectInputStream, um fuer Nachrichten vom Client entgegenzunehmen
	 */
	private ObjectInputStream comIn;

	/**
	 * Konstruktor des Players, in ihm werden die Attribute server, comOut und comIn mit
	 * vom ClientListererThread uebergebenen Werten Instanziiert.
	 * @param lobbyServer ist der LobbyServer, der zu Beginn den Player verwaltet.
	 * @param output ist der ObjectOutputStream an den entsprechenden Client
	 * @param input ist der ObjectInputStream vom entsprechenden Client
	 */
	public Player(Server lobbyServer, ObjectOutputStream output, ObjectInputStream input){
		server = lobbyServer;
		comOut = output;
		comIn = input;
		name = null;
	}
	
	/**
	 * Die run-Methode des Thread nimmt eingehende Nachrichten des Client
	 * entgegen und uebergibt diese an den Server durch Aufruf der Methode 
	 * resolveMessage()
	 * Faengt eine ClassNotFoundException ab, falls die Klasse nicht gefunden
	 * werden kann und gibt einen Fehler aus.
	 * Faengt eine IOException ab und ruft im jeweiligen Server, dem er zugeteilt
	 * ist die handleIOException Methode auf.
	 */
	@Override
	public void run(){
		boolean run = true;
		ComObject object;
		while(run) {
			try {
				object = (ComObject) comIn.readObject();
				object.process(this, server);
			} catch (ClassNotFoundException e) {
				System.out.println("Classpath was not found!");
				System.exit(0);
				e.printStackTrace();
			} catch (IOException e) {
				server.handleIOException(this);
				e.printStackTrace();
			}			
		}
	}

	/**
	 * Diese Methode schickt ein ComObjekt an den Client.
	 * Sie faengt eine IOException ab und ruft im jeweiligen Server, dem er zugeteilt
	 * ist die handleIOException Methode auf.
	 * @param com ist das ComObject das verschickt wird
	 */
	public void send(ComObject com){
		try {
			comOut.writeObject(com);
		} catch (IOException e) {
			// TODO Automatisch erstellter Catch-Block
			e.printStackTrace();
		}
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}

	/**
	 * Diese Methode wechselt beim Player den Server an den er comObjects
	 * weiterleiten soll. Dabei wird er aus dem playerSet des alten Servers
	 * entfernt und in das playerSet des neuen Players eingefuegt. 
	 * Danach wird vom neuen Server ein ComUpdatePlayerlist Objekt mit broadcast 
	 * an alle Clients, die vom Server verwaltet werden, verschickt.
	 * @param newServer ist der neue Server
	 */
	public void changeServer(Server newServer){
		server.removePlayer(this);
		server = newServer;
		server.addPlayer(this);
		server.broadcast(new ComUpdatePlayerlist(this.getName(), false));
		// begin-user-code
		// TODO Auto-generated method stub
		// end-user-code	
	}
	
	/**
	 * Getter-Methode fuer den Benutzernamen.
	 * @return gibt den Benutzernamen des Spielers zurueck
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Setter-Methode fuer den Benutzernamen.
	 * @param newName ist der neue Name
	 */
	public void setName(String newName){
		name = newName;
	}
}