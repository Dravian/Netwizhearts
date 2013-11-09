/**
 * 
 */
package Client;

import Ruleset.ClientRuleset;
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
import java.io.IOException;
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
	 * String der den eindeutigen Spielernamen repr�sentiert.
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
	 * H�lt den f�r die Netzwerkkomunikation zust�ndigen Thread.
	 */
	private MessageListenerThread messageListenerThread;
	
	private List<String> playerList;

	private Set<GameServerRepresentation> gameList;

	/**
	 * 
	 */
	public ClientModel() {
		
	}
	
	/**
	 * Wird von dem Controller
	 * beim Verlassen eines Fensters
	 * ausgef�hrt.
	 */
	public void leaveWindow() {
		
	}

	/** 
	 * Bearbeitet eine eingehende Chatnachricht.
	 */
	private void processChatMessage(ComChatMessage msg) {
		
	}
	
	/** 
	 * Verarbeitet eine Liste von Spielern und Spielen,
	 * welche sich beim Betreten der ServerLobby bereits darin befinden.
	 */
	private void processInitServerLobby(ComInitLobby msg) {
		
	}
	
	/** 
	 * Verarbeitet eine Liste von Spielern,
	 * welche sich beim betreten der Spiellobby
	 * bereits darin befinden.
	 */
	private void processInitGameLobby(ComInitGameLobby msg) {
		
	}
	
	/** 
	 * Diese Methode wird von receiveMessage() aufgerufen,
	 * falls eine Nachricht f�r das Regelwerk ankommt.
	 */
	private void invokeRuleset(ComRuleset msg) {
		
	}
	
	/** 
	 * Diese Hilfsmethode wird von receiveMessage() aufgerufen,
	 * falls ein Server Acknowledgement auftritt.
	 * Dabei ist es von Bedeutung,
	 *  in welchem Zustand sich der Client befindet.
	 *  @param ack Eine Best�tigung durch den Server.
	 */
	private void processAck(ComServerAcknowledgement ack) {
		
	}
	
	/** 
	 * Diese Hilfmethode wird von receiveMessage() aufgerufen,
	 * falls der Spieler aus der Spiellobby durch einen Spielleiter
	 * entfernt wurde.
	 */
	private void processBeenKicked(ComBeenKicked msg) {
		
	}
	
	/**
	 * Verarbeitet ein Update, das einen einzelnen Spieler betrifft.
	 */
	private void processUpdatePlayerlist(ComUpdatePlayerlist update) {
		
	}
	
	/**
	 * Verarbeitet ein Update, das ein einzelnes Spiel betrifft.
	 */
	private void processUpdateGamelist(ComLobbyUpdateGamelist update) {
		
	}
	
	/**
	 * Diese Methode wird von dem ClientListenerThread aufgerufen
	 * und bestimmt welche Nachricht sich hinter dem ComObjekt genau
	 * verbirgt um weitere Verarbeitungsschritte einzuleiten.
	 * @param comObject Die empfangene Nachricht.
	 */
	private void receiveMessage(ComObject comObject){
		
	}
	
	/**
	 * Diese Methode wird von der View beim betreten der Spiellobby aufgerufen
	 * und liefert eine Liste von Spielern in der Spiellobby.
	 * @return List Eine Liste der Spieler in der Spiellobby.
	 */
	public List<String> getGameLobbyPlayerlist(){
		return playerList;
	}
	
	/**
	 * Diese Methode wird von der View beim betreten der Serverlobby aufgerufen
	 * und liefert eine Liste von Spielern und Spielen in der Serverlobby.
	 * @return Set Enth�lt alle Spiele in der ServerLobby.
	 */
	public Set<GameServerRepresentation> getFullServerLobbyGamelist(){
		return null;
	}
	
	/**
	 * Diese Methode wird von der View aufgerufen
	 * und aktualisiert einzelne Spiele.
	 * @return GameServerRepresentation Daten eines Spieles.
	 */
	public GameServerRepresentation getServerLobbyGamelistUpdate(){
		return null;
	}
	
	/**
	 * Diese Methode wird von der View aufgerufen um die Liste der Spieler
	 * zu aktualisieren.
	 * @return List Update f�r die aktuelle Spielerliste.
	 */
	public List<String> getPlayerlistUpdate(){
		return null;
	}
	
	/**
	 * Diese Methode wird von der View aufgerufen um eine neue Chatnachricht
	 * abzuholen.
	 * @return String die Chatnachricht.
	 */
	public String getChatMessage(){
		return null;
	}
	
	/**
	 * Gibt der View die gespielte Karte eines anderen Spielers zur�ck.
	 * @return enum CardID. Die Id der Karte
	 */
	public CardID getPlayedCard(){
		return null;
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
	private void sendMessage(ComObject object) {
		
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
	 * zuordnen zu k�nnen.
	 */
	public void makeMove(CardID id) {
		
	}

	/** 
	 * Hilfsmethode die alle verbundenen Observer der GUI kontaktiert.
	 * @param note Enum der die Art des Aufrufes bestimmt.
	 */
	private void informView(ViewNotification note) {
		
	}
	
	/** 
	 * Erstellt den MessageListenerThread und f�hrt den Benutzerlogin durch.
	 * @param username String der eindeutige Benutzername der f�r den Login verwendet wird.
	 * @param serverAdress String die Adresse des spielservers.
	 * @param port Integer der Port des Spielservers.
	 */
	public void createConnection(final String username, final String serverAdress, final int port) {
		
	}

	/** 
 * Diese Klasse implementiert die Netzwerkanbindung des Clients an den Server.
 * Sie ist eine innere Klasse des ClientModels und wird vom selbigen instanziert.
 * Sie enth�lt den dazu n�tigen Socket und ObjektStream Reader und Writer.
 */
class MessageListenerThread extends Thread{
	
	/** Der TCP Socket. */
	private Socket connection;

	/**
	 * Erstellt die initiale Verbindung zum Server.
	 * @param connection TCP Socket �ber den die Verbindung erstellt wird.
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
	 * �ber diese Methode k�nnen Nachrichten an den Server versendet werden.
	 */
	protected void send() {
		
	}
	
	@Override
	public void run() {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}
  }
}