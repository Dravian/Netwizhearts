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
 * Das ClientModel ist die Schnittstelle zwischen dem MessageListenerThread, 
 * dem ClientRuleset und der View. Das Model prüft Nachrichten, welche es vom
 * MessageListenerThread über die Methode receiveMessage() bekommt. RulesetMessages
 * werden an das ClientRuleset weitergeleitet. Weiterhin informiert es seine Observer über
 * Veränderungen und stellt ihnen Methoden zu Verfügung um spielrelevante Daten zu lesen.
 * Weiterhin kann das ClientModel ComMessages and den Server schicken, um Kommandos des
 * ClientRulesets oder Eingaben des Controllers weiterzugeben.
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
	 * Erstellt ein ClientModel
	 */
	public ClientModel() {
		
	}
	
	/**
	 * Wird aufgerufen, wenn der User die GameLobby verlässt. 
	 * Der Client gelangt zurück in die Lobby.
	 */
	public void leaveWindow() {
		
	}

	/** 
	 * Sendet eine eingehende Chatnachricht direkt an alle Observer weiter.
	 * 
	 * @param msg die ankommende ComChatMessage Nachricht
	 */
	public void receiveMessage(ComChatMessage msg) {
		this.chatMessage = msg.getChatMessage();
	}
	
	/** 
	 * Diese Methode wird von receiveMessage() aufgerufen,
	 * falls der Server den Spieler erfolgreich in die Lobby hinzugefügt hat.
	 * Empfängt die ComInitGameLobby Nachricht, die eine Liste aller
	 * Spieler enthält, die sich in der Lobby befinden. Speichert
	 * diese Liste und benachrichtigt die Observer mit der loginSuccesful
	 * ViewNotification.
	 * 
	 * @param msg die ankommende ComInitLobby Nachricht
	 */
	public void receiveMessage(ComInitLobby msg) {
		
	}
	
	/** 
	 * Diese Methode wird von receiveMessage() aufgerufen,
	 * falls der Server den Spieler erfolgreich in die GameLobby hinzugefügt hat.
	 * Empfängt die ComInitGameLobby Nachricht, die eine Liste aller
	 * Spieler enthält, die sich in der GameLobby befinden. Speichert
	 * diese Liste und benachrichtigt die Observer mit der joinGameSuccesful
	 * ViewNotification.
	 * 
	 * @param msg die ankommende ComInitGameLobby Nachricht
	 */
	public void receiveMessage(ComInitGameLobby msg) {
		
	}
	
	/** 
	 * Diese Methode wird von receiveMessage() aufgerufen,
	 * falls eine Nachricht für das Regelwerk ankommt. Die
	 * darin enthaltene RulesetMessage wird dem ClientRuleset
	 * zur Verarbeitung übergeben.
	 * 
	 * @param die ankommende ComRuleset Nachricht
	 */
	public void receiveMessage(ComRuleset msg) {
		
	}
	
	/** 
	 * Diese Methode wird von receiveMessage() aufgerufen,
	 * falls ein Server Acknowledgement auftritt.
	 * Dabei ist es von Bedeutung,
	 *  in welchem Zustand sich der Client befindet.
	 *  
	 *  @param ack Eine Bestätigung durch den Server.
	 */
	public void receiveMessage(ComServerAcknowledgement ack) {
		
	}
	
	/** 
	 * Diese Methode wird von receiveMessage() aufgerufen,
	 * falls der Spieler aus der Spiellobby durch einen Spielleiter
	 * entfernt wurde. Der Client gelangt zurück in die Lobby,
	 * die Observer werden mit windowChangeForced benachrichtigt.
	 * 
	 * @param msg die ankommende ComBeenKicked Nachricht
	 */
	public void receiveMessage(ComBeenKicked msg) {
		
	}
	
	/**
	 * Diese Methode wird von receiveMessage() aufgerufen,
	 * falls auf dem Server ein neuer Spieler die Lobby/GameLobby
	 * betreten hat oder sie von einem Spieler verlassen wurde.
	 * Empfängt die ComUpdatePlayerlist Nachricht, die die Information
	 * enthält, ob und welcher Spieler hinzugefügt oder entfernt werden muss.
	 * Die Spielerliste wird dementsprechend bearbeitet und die Observer mit
	 * playerListUpdate informiert.
	 * 
	 * @param update die ankommende ComLobbyUpdatePlayerlist Nachricht
	 */
	public void receiveMessage(ComUpdatePlayerlist update) {
		
	}
	
	/**
	 * Diese Methode wird von receiveMessage() aufgerufen,
	 * falls auf dem Server ein neues Spiel erstellt wurde oder
	 * ein Spiel geschlossen/beendet wurde.
	 * Empfängt die ComLobbyUpdateGamelist Nachricht, die die Information
	 * enthält, ob und welches Spiel hinzugefügt oder entfernt werden muss.
	 * Die Spielliste wird dementsprechend bearbeitet und die Observer mit
	 * gameListUpdate informiert.
	 * 
	 * @param update die ankommende ComLobbyUpdateGamelist Nachricht
	 */
	public void receiveMessage(ComLobbyUpdateGamelist update) {
		
	}
	
	/**
	 * Diese Methode wird von dem ClientListenerThread aufgerufen
	 * und bestimmt welche Nachricht sich hinter dem ComObjekt genau
	 * verbirgt um weitere Verarbeitungsschritte einzuleiten.
	 * 
	 * @param comObject Die empfangene Nachricht.
	 */
	public void receiveMessage(ComObject comObject){
		chatMessage = ((ComChatMessage) comObject).getChatMessage();
		System.out.println("moo");
	} 
	
	/**
	 * Liefert eine Liste der Namen der Spieler in der Lobby oder GameLobby.
	 * 
	 * @return Liste von Spielernamen
	 */
	public List<String> getPlayerlist(){
		return playerList;
	}
	
	/**
	 * Liefert eine Liste der Spiele, die aktuell auf dem Server offen sind
	 * oder gerade gespielt werden.
	 * 
	 * @return Liste aller Spiele der Lobby.
	 */
	public List<GameServerRepresentation> getLobbyGamelist(){
		return null;
	}
	
	/**
	 * Gibt eine Liste aller bereits ausgespielten Karten zurück.
	 * 
	 * @return enum CardID. Die Ids der Karten
	 */
	public List<CardID> getPlayedCards(){
		return null;
	}
	
	/**
	 * Gibt eine Liste der Handkarten des Spielers zurück.
	 * 
	 * @param Liste aller Handkarten des Spielers
	 */
	public List<CardID> getOwnHand() {
		return null;
	}
	
	/**
	 * Liefert zusätzliche Daten der anderen Spieler zurück.
	 * 
	 * @return Liste der Stringrepräsentationen der OtherData der Spieler
	 */
	public List<String> getOtherPlayerData() {
		return null;
		
	}
	
	/**
	 * Gibt den Punktestand des Spielers zurück.
	 * 
	 * @return der eigene Punktestand.
	 */
	public int getOwnScore() {
		return 0;
	}
	
	/**
	 * Setzt die Sprache der GUI.
	 * 
	 * @param language Enumerator der die Spielsprache anzeigt.
	 */
	public void setLanguage(final Language language) {
		this.language = language;
	}
	
	/**
	 * Liefert die Sprache der GUI.
	 * 
	 * @return language Enumerator der die Spielsprache anzeigt.
	 */
	public Language getLanguage() {
		return language;
	}

	/** 
	 * Entfernt einen Spieler aus der GameLobby.
	 * 
	 * @param Name des Spielers, der enfernt werden soll
	 */
	public void kickPlayer(final String name) {
	
	}

	/** 
	 * Erstellt ein neues Spiel. Sendet dazu eine ComCreateGameRequest Nachricht
	 * an den Server.
	 * 
	 * @param gameName String Name des Spieles.
	 * @param hasPassword true, wenn das Spiel ein Passwort hat
	 * @param password String Passwort zum sichern des Spieles.
	 * @param ruleset das zu verwendende Regelwerk
	 */
	public void hostGame(String gameName, boolean hasPassword, String password, RulesetType ruleset) {

	}

	/** 
	 * Sendete erstellte ComObjects an den Server.
	 * 
	 * @param object ComObject, das verschickt wird
	 */
	private void sendMessage(ComObject object) {
		netIO.send(object);
	}
	
	/**
	 * Sendet eine RulesetMessage an den Server. Erstellt dazu eine
	 * ComRuleset, die die RulesetMessage enthält.
	 * 
	 * @param msg die RulesetMessage, die an den Server geschickt werden soll
	 */
	public void send(RulesetMessage msg) {
		ComObject com = new ComRuleset(msg);
		sendMessage(com);
	}
	
	/**
	 * Nimmt vom ClientController eine Chatnachricht entgegen
	 * und sendet diese an den Server.
	 * 
	 * @param msg die Chatnachricht, die an den Server geschickt werden soll
	 */
	public void sendChatMessage(final String msg) {
		sendMessage(new ComChatMessage(msg));
	}

	/** 
	 * Versucht einem Spiel beizutreten. Sendet dazu eine ComJoinRequest Nachricht an
	 * den Server. Wird diese bestätigt, gelangt der Client in die GameLobby. Wird die
	 * Nachricht nicht bestätigt, wird eine Fehlermeldung ausgegeben und die Observer
	 * mit openWarning informiert.
	 * 
	 * @param name String Der Name des Spiels.
	 * @param password String Passwort eines Spieles.
	 */
	public void joinGame(final String name, final String password) {
	
	}

	/** 
	 * Versucht das erstellte Spiel zu starten. Sendet dazu eine ComStartGame an den Server.
	 * Wenn der Client der Spielleiter des Spiels ist, gelangt er ins Spiel.
	 * Wenn der Client nicht der Spielleiter des Spiels ist, wird eine Fehlermeldung ausgegeben.
	 */
	public void startGame() {
	
	}

	/** 
	 * Diese Methode wird innerhalb des ClientModels aufgerufen wenn ein Spiel 
	 * vom Spielleiter gestartet wurde. Der Client gelangt ins Spiel
	 * Die Observer werden über die gameStarted ViewNotification benachrichtigt.
	 */
	private void initGame() {
		
	}

	/** 
	 * Versucht eine Karte auszuspielen. Lässt dazu vom ClientRuleset überpüfen ob, die ausgewählte
	 * Karte gespielt werden darf. Wenn ja, wird sie im ClientRuleset weiterbehandelt. Wenn nein,
	 * wird eine Fehlermeldung ausgegeben und dazu die Observer mit openWarning informiert.
	 * 
	 * @param die ID der gespielten Karte
	 */
	public void makeMove(CardID card) {
		
	}

	/** 
	 * Hilfsmethode die alle verbundenen Observer der GUI kontaktiert.
	 * 
	 * @param note Enum der die Art des Aufrufes bestimmt.
	 */
	private void informView(ViewNotification note) {
		
	}
	
	public void setNetIO(Client.MessageListenerThread netIO) {
		this.netIO = netIO;
	}
	
	/** 
	 * Erstellt den MessageListenerThread und führt den Benutzerlogin durch.
	 * 
	 * @param username String der eindeutige Benutzername der für den Login verwendet wird.
	 * @param serverAdress String die Adresse des spielservers.
	 * @param port Integer der Port des Spielservers.
	 */
	public void createConnection(final String username, final String serverAdress, final int port) {
		
	}
			
	/**
	 * Gibt den Text aus der bei einer Spielwarnung
	 * angezeigt wird.
	 * 
	 * @return String Text der Warnung.
	 */
	public String getWarningText() {
		return null;
	}
	
	/**
	 * Liefert ein Array mit allen implementierten Regelwerken.
	 * 
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