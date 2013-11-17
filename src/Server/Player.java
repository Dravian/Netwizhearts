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
public class Player extends Thread{
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
	 * Zeigt an, ob der Thread läuft
	 */
	private boolean run = false;

	/**
	 * Konstruktor des Players, in ihm werden die Attribute server, comOut und comIn mit
	 * vom ClientListererThread uebergebenen Werten Instanziiert.
	 * @param lobbyServer ist der LobbyServer, der zu Beginn den Player verwaltet.
	 * @param output ist der ObjectOutputStream an den entsprechenden Client
	 * @param input ist der ObjectInputStream vom entsprechenden Client
	 */
	public Player(Server lobbyServer, ObjectOutputStream output, ObjectInputStream input){
		super("Player");
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
		run = true;
		ComObject object;
		while(run) {
			try {
				object = (ComObject) comIn.readObject();
				object.process(this, server);
			} catch (ClassNotFoundException e) {
				run = false;
				System.out.println("Classpath was not found!");
				server.handleException(this);
				try {
					comIn.close();
					comOut.close();
				} catch (IOException e1) {
					// TODO Automatisch erstellter Catch-Block
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (IOException e) {
				run = false;
				server.handleException(this);
				try {
					comIn.close();
					comOut.close();
				} catch (IOException e1) {
					// TODO Automatisch erstellter Catch-Block
					e1.printStackTrace();
				}
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
			run = false;
			server.handleException(this);
			try {
				comIn.close();
				comOut.close();
			} catch (IOException e1) {
				// TODO Automatisch erstellter Catch-Block
				e1.printStackTrace();
			}
			e.printStackTrace();
			e.printStackTrace();
		}
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
	}
	
	/**
	 * Getter-Methode fuer den Benutzernamen.
	 * @return gibt den Benutzernamen des Spielers zurueck
	 */
	public String getPlayerName(){
		return name;
	}
	
	/**
	 * Setter-Methode fuer den Benutzernamen.
	 * @param newName ist der neue Name
	 */
	public void setPlayerName(String newName){
		name = newName;
	}
}