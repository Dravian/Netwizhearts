package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
	protected Server server;
	
	/**
	 * Socket des Clients
	 */
	private Socket connection;
	
	/**
	 * ObjectOutputStream, um fuer Nachrichten an den Client zu Senden
	 */
	private ObjectOutputStream comOut;
	
	/**
	 * ObjectInputStream, um fuer Nachrichten vom Client entgegenzunehmen
	 */
	private ObjectInputStream comIn;
	
	/**
	 * Zeigt an, ob der Thread l‰uft
	 */
	private boolean run = false;
	
	/**
	 * (Konstruktor fuer Tests)
	 */
	public Player(Server lobbyServer) {
		super("Player");
		server = lobbyServer;
	}
	
	/**
	 * Konstruktor des Players, in ihm werden die Attribute server und connection mit
	 * vom ClientListererThread uebergebenen Werten Instanziiert.
	 * ComIn und comOut initialisiert.
	 * @param lobbyServer ist der LobbyServer, der zu Beginn den Player verwaltet.
	 * @param socket ist der Socket des Clients
	 */
	public Player(Server loginServer, Socket socket){
		super("Player");
		server = loginServer;
		connection = socket;
		try {
			comOut = new ObjectOutputStream(connection.getOutputStream());
			comOut.flush();
		} catch (IOException e) {
			System.err.println("Couldn't getOutputStream");
			try {
				connection.close();
			} catch (IOException e1) {
				System.err.println("Closing Failed");
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		try {
			comIn = new ObjectInputStream(connection.getInputStream());
		} catch (IOException e) {
			try {
				connection.close();
			} catch (IOException e1) {
				System.err.println("Closing Failed");
				e1.printStackTrace();
			}
			System.err.println("Couldn't getInputStream");
			e.printStackTrace();
		}
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
				System.out.println(object);
				object.process(this, server);
			} catch (ClassNotFoundException e) {
				run = false;
				System.err.println("Classpath was not found!");
				server.disconnectPlayer(this);
				e.printStackTrace();
			} catch (IOException e) {
				run = false;
				System.err.println("Couldn't find Input/Output!");
				server.disconnectPlayer(this);
				e.printStackTrace();
			}			
		}
	}
	
	/**
	 * Diese Methode schlieﬂt den Socket, sowie comIn und comOut
	 */
	public void closeConnection(){
		try {
			send(new ComClientQuit());
			run = false;
			comIn.close();
			comOut.close();
			connection.close();
		} catch (IOException e) {
			System.err.println("Closing Failed");
			e.printStackTrace();
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
			comOut.flush();
		} catch (IOException e) {
			run = false;
			server.disconnectPlayer(this);
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode wechselt beim Player den Server an den er comObjects
	 * weiterleiten soll. Dabei wird er aus dem playerSet des alten Servers
	 * entfernt und in das playerSet des neuen Players eingefuegt. 
	 * Vom neuen Server ein ComUpdatePlayerlist Objekt mit broadcast 
	 * an alle Clients, die vom Server verwaltet werden, verschickt.
	 * @param newServer ist der neue Server
	 */
	public void  changeServer(Server newServer){
		//server.removePlayer(this);
		server = newServer;
		server.broadcast(new ComUpdatePlayerlist(this.getPlayerName(), false));	
		server.addPlayer(this);		
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