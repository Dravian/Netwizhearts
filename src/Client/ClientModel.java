/**
 * 
 */
package Client;

import Ruleset.Card;
import Ruleset.ClientRuleset;
import Ruleset.OtherData;
import Ruleset.RulesetType;
import Server.GameServerRepresentation;
import Client.View.Language;
import ComObjects.ComBeenKicked;
import ComObjects.ComChatMessage;
import ComObjects.ComInitGameLobby;
import ComObjects.ComInitLobby;
import ComObjects.ComLobbyUpdateGamelist;
import ComObjects.ComObject;
import ComObjects.ComRuleset;
import ComObjects.ComServerAcknowledgement;
import ComObjects.ComUpdatePlayerlist;
import ComObjects.RulesetMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Observable;
import java.util.Set;

/** 
 * Implementiert das Client Model.
 * Das Model bedient den Server durch den ListenerThread
 * und leitet Daten an das Regelwerk und View weiter.
 */
public class ClientModel extends Observable{
	/** 
	 * String der den eindeutigen Spielernamen repräsentiert.
	 */
	private String playerName;
	
	/** 
	 * Referenz auf das Regelwerk des Spieles.
	 */
	private ClientRuleset ruleset;

	/** 
	 * Die aktuelle Sprache der GUI.
	 */
	private Language language;

	/** 
	 * Der Zustand indem sich der Client befindet.
	 */
	
	private ClientState state;
	
	/** 
	 * Hält den für die Netzwerkkomunikation zuständigen Thread.
	 */
	private Client.MessageListenerThread netIO;
	
	private List<String> playerList;

	private Set<GameServerRepresentation> gameList;
	
	private String chatMessage;

	/**
	 * 
	 */
	public ClientModel() {
		
	}
	
	/**
	 * Wird von dem ClientController
	 * beim Verlassen eines Fensters
	 * ausgeführt.
	 */
	public void leaveWindow() {
		
	}

	/** 
	 * Bearbeitet eine eingehende Chatnachricht.
	 */
	public void receiveMessage(ComChatMessage msg) {
		this.chatMessage = msg.getChatMessage();
	}
	
	/** 
	 * Verarbeitet eine Liste von Spielern und Spielen,
	 * welche sich beim Betreten der ServerLobby bereits darin befinden.
	 */
	public void receiveMessage(ComInitLobby msg) {
		
	}
	
	/** 
	 * Verarbeitet eine Liste von Spielern,
	 * welche sich beim betreten der Spiellobby
	 * bereits darin befinden.
	 */
	public void receiveMessage(ComInitGameLobby msg) {
		
	}
	
	/** 
	 * Diese Methode wird von receiveMessage() aufgerufen,
	 * falls eine Nachricht für das Regelwerk ankommt.
	 */
	public void receiveMessage(ComRuleset msg) {
		
	}
	
	/** 
	 * Diese Hilfsmethode wird von receiveMessage() aufgerufen,
	 * falls ein Server Acknowledgement auftritt.
	 * Dabei ist es von Bedeutung,
	 *  in welchem Zustand sich der Client befindet.
	 *  @param ack Eine Bestätigung durch den Server.
	 */
	public void receiveMessage(ComServerAcknowledgement ack) {
		
	}
	
	/** 
	 * Diese Hilfmethode wird von receiveMessage() aufgerufen,
	 * falls der Spieler aus der Spiellobby durch einen Spielleiter
	 * entfernt wurde.
	 */
	public void receiveMessage(ComBeenKicked msg) {
		
	}
	
	/**
	 * Verarbeitet ein Update, das einen einzelnen Spieler betrifft.
	 */
	public void receiveMessage(ComUpdatePlayerlist update) {
		
	}
	
	/**
	 * Verarbeitet ein Update, das ein einzelnes Spiel betrifft.
	 */
	public void receiveMessage(ComLobbyUpdateGamelist update) {
		
	}
	
	/*
	 * Diese Methode wird von dem ClientListenerThread aufgerufen
	 * und bestimmt welche Nachricht sich hinter dem ComObjekt genau
	 * verbirgt um weitere Verarbeitungsschritte einzuleiten.
	 * @param comObject Die empfangene Nachricht.
	 */
	public void receiveMessage(ComObject comObject){
		chatMessage = ((ComChatMessage) comObject).getChatMessage();
		System.out.println("moo");
	} 
	
	/**
	 * Diese Methode wird von der View beim betreten der Spiellobby aufgerufen
	 * und liefert eine Liste von Spielern in der Spiellobby.
	 * @return List Eine Liste der Spieler in der Spiellobby.
	 */
	public List<String> getPlayerlist(){
		return playerList;
	}
	
	/**
	 * Diese Methode wird von der View beim betreten der Serverlobby aufgerufen
	 * und liefert eine Liste von Spielern und Spielen in der Serverlobby.
	 * @return Set Enthält alle Spiele in der ServerLobby.
	 */
	public Set<GameServerRepresentation> getLobbyGamelist(){
		return null;
	}
	
	/**
	 * Diese Methode wird von der View aufgerufen um eine neue Chatnachricht
	 * abzuholen.
	 * @return String die Chatnachricht.
	 */
	public String getChatMessage(){
		return chatMessage;
	}
	
	/**
	 * Gibt der View die gespielte Karte eines anderen Spielers zurück.
	 * @return enum CardID. Die Id der Karte
	 */
	public Card getPlayedCard(){
		return null;
	}
	
	/**
	 * Gibt der View die eigenen Spielkarten zurück.
	 * @param Card[] Ein Array mit allen Karten,
	 * die man auf der Hand hat.
	 */
	public Card[] getOwnHand() {
		return null;
	}
	
	/**
	 * Liefert zusätzliche Daten anderer Spieler zurück.
	 * @return List<OtherData> Liste mit gespielten Karten.
	 */
	public List<OtherData> getOtherPlayerData() {
		return null;
		
	}
	
	/**
	 * Gibt den Punktestand des Spielers aus.
	 * @return int Der eigene Punktestand.
	 */
	public int getOwnScore() {
		return 0;
	}
	
	/**
	 * Setzt die Sprache der GUI.
	 * @param language Enumerator der die Spielsprache anzeigt.
	 */
	public void setLanguage(final Language language) {
		this.language = language;
	}
	
	/**
	 * Liefert die Sprache der GUI.
	 * @return language Enumerator der die Spielsprache anzeigt.
	 */
	public Language getLanguage() {
		return language;
	}

	/** 
	 * Wird vom Controller aufgerufen um einen Spieler
	 * aus der Spiellobby zu entfernen.
	 * @param name des Spielers welcher enfernt werden soll.
	 */
	public void kickPlayer(final String name) {
	
	}

	/** 
	 * Wird vom ClientController aufgerufen und erstellt ein neues Spiel
	 * auf dem Server.
	 * @param gameName String Name des Spieles.
	 * @param password String Passwort zum sichern des Spieles.
	 */
	public void hostGame(String gameName, String password) {

	}

	/** 
	 * Hilfsmethode des Models um erstellte Nachrichten an den Netzwerkthread weiter
	 * zuleiten.
	 */
	public void sendMessage(ComObject object) {
		netIO.send(object);
	}
	
	/**
	 * Methode über die das clientRegelwerk
	 * Nachrichten versenden kann.
	 */
	public void send(RulesetMessage msg) {
		ComObject com = new ComRuleset(msg);
		sendMessage(com);
	}
	
	/**
	 * Nimmt vom ClientController eine Chatnachricht entgegen
	 * und sendet diese an den Server.
	 */
	public void sendChatMessage(final String msg) {
		sendMessage(new ComChatMessage(msg));
	}

	/** 
	 * Diese Methode wird von dem ClientController aufgerufen um
	 * einem bereits erstelltem Spiel beizutreten.
	 * @param name String Der Name des Spiels.
	 * @param password String Passwort eines Spieles.
	 */
	public void joinGame(final String name, final String password) {
	
	}

	/** 
	 * Der ClientController ruft diese Methode auf,
	 * um ein bereits erstelltes Spiel zu starten.
	 */
	public void startGame() {
	
	}

	/** 
	 * Diese Methode wird innerhalb des ClientModels aufgerufen wenn ein Spiel 
	 * vom Spielleiter gestartet wurde.
	 * Es werden die Observer benachrichtigt und die View wechselt in die Spielansicht.
	 */
	private void initGame() {
		
	}

	/** 
	 * Wird vom ClientConroller aufgerufen um eine Karte auszuspielen.
	 * @param id Die id der gespielten Karte um sie einer logischen Karte
	 * zuordnen zu können.
	 */
	public void makeMove(Card card) {
		
	}

	/** 
	 * Hilfsmethode die alle verbundenen Observer der GUI kontaktiert.
	 * @param note Enum der die Art des Aufrufes bestimmt.
	 */
	private void informView(ViewNotification note) {
		
	}
	
	public void setNetIO(Client.MessageListenerThread netIO) {
		this.netIO = netIO;
	}
	
	/** 
	 * Erstellt den MessageListenerThread und führt den Benutzerlogin durch.
	 * @param username String der eindeutige Benutzername der für den Login verwendet wird.
	 * @param serverAdress String die Adresse des spielservers.
	 * @param port Integer der Port des Spielservers.
	 */
	public void createConnection(final String username, final String serverAdress, final int port) {
		
	}
	
	/**
	 * Gibt den Fehler beim Loginversuch zurück.
	 */
	public LoginError getLoginError(){
		return null;	
	}
	
	/**
	 * Gibt den Text aus der bei einer Spielwarnung
	 * angezeigt wird.
	 * @return String Text der Warnung.
	 */
	public String getWarningText() {
		return null;
	}
	
	/**
	 * Liefert ein Array mit allen implementierten Regelwerken.
	 * @param RulesetType[] Array von unterstützten Regelwerken.
	 */
	public RulesetType[] getRulesets() {
		return null;
	}

/** 
 * Diese Klasse implementiert die Netzwerkanbindung des Clients an den Server.
 * Sie ist eine innere Klasse des ClientModels und wird vom selbigen instanziert.
 * Sie enthält den dazu nötigen Socket und ObjektStream Reader und Writer.
 */
class MessageListenerThread extends Thread{
	
	/** Der TCP Socket. */
	private Socket connection;
	
	private ObjectInputStream in;
	
	private ObjectOutputStream out;
	
	private boolean run = false;

	/**
	 * Erstellt die initiale Verbindung zum Server.
	 * @param connection TCP Socket über den die Verbindung erstellt wird.
	 * @throws IOException Diese Exception wird an den Aufrufenden weitergeleitet.
	 */
	protected MessageListenerThread(final Socket connection) throws IOException{
		this.connection = connection;
	}
	
	/**
	 * Hilfsmethode die eine bestehende Verbindung abbaut
	 * und deren Ressourcen freigibt.
	 */
	protected void closeConnection() {
		
	}
	
	/**
	 * Über diese Methode können Nachrichten an den Server versendet werden.
	 */
	protected void send(ComObject object) {
		try {
			if (run) {
				out.writeObject(object);
				out.flush();
			}
		} catch (IOException e) {
			if (run) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Initialisiert den In- und OutputStream
	 * und liest ComObjekte solange der Thread
	 * lebt von seinem InputStream.
	 */
	public void run() {
		try {
			run = true;
			ComObject object;
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			while(run) {
				object = (ComObject) in.readObject();
				object.process(ClientModel.this);
			}
		} catch (ClassNotFoundException e) {
			
		} catch (IOException e) {
			if(run) {
				e.printStackTrace();
			}
		}
	}
  }
}