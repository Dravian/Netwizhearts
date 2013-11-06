/**
 * 
 */
package Client;

import static Client.View.Warning.*;
import Ruleset.ClientRuleset;
import Client.View.Language;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;

/** 
 * Implementiert das Client Model 
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
	private MessageListenerThread messageListenerThread;

	/**
	 * 
	 */
	public ClientModel() {
		
	}

	/** 
	 * Überladene Methode die ComObjekte von der inneren Klasse
	 * messageListenerThread übergeben bekommt und auswertet.
	 */
	private void receiveMessage() {
		
	}
	
	/**
	 * Diese Methode wird von der View beim betreten der Spiellobby aufgerufen
	 * und liefert eine Liste von Spielern in der Spiellobby.
	 */
	public void getGameLobbyInit(){
		
	}
	
	/**
	 * Diese Methode wird von der View beim betreten der Serverlobby aufgerufen
	 * und liefert eine Liste von Spielern und Spielen in der Serverlobby.
	 */
	public void getServerLobbyInit(){
		
	}
	
	/**
	 * Diese Methode wird von der View aufgerufen und aktualisiert einzelne
	 * kommende und abgehende Spieler in den Listen der View.
	 */
	public void getServerLobbyGamelistUpdate(){
		
	}
	
	/**
	 * Diese Methode wird von der View aufgerufen um die Liste der Spieler
	 * zu aktualisieren.
	 */
	public void getPlayerlistUpdate(){
		
	}
	
	/**
	 * Diese Methode wird von der View aufgerufen um eine neue Chatnachricht
	 * abzuholen.
	 */
	public String getChatMessage(){

	}
	
	/**
	 * Gibt der View die gespielte Karte eines anderen Spielers zurück.
	 */
	public void getPlayedCard(){
		
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
	 * Wird vom Regelwerk aufgerufen und
	 * kontaktiert die Observer um das InputNumber Fenster der View zu öffnen.
	 */
	private void openInputNumber() {
		
	}

	/** 
	 * Wird vom Regelwerk aufgerufen und
	 * kontaktiert die Observer um das ScoreWindow der View zu öffnen.
	 */
	private void openScoreWindow() {

	}

	/** 
	 * Wird vom Controller aufgerufen um einen Spieler
	 * aus der Spiellobby zu entfernen.
	 * @param name des Spielerst welcher enfernt werden soll.
	 */
	public void kickPlayer(final String name) {
	
	}

	/** 
	 * Wird vom Regelwerk aufgerufen und kontaktiert die Observer
	 * des Models um das openChooseCards Fenster zu öffnen.
	 */
	private void openChooseCards() {
		
	}

	/** 
	 * Wird vom ClientController aufgerufen und erstellt ein neues Spiel
	 * auf dem Server.
	 */
	public void hostGame() {
		
	}

	/** 
	 * Hilfsmethode des Models um erstellte Nachrichten an den Netzwerkthread weiter
	 * zuleiten.
	 */
	private void sendMessage() {
		
	}

	/** 
	 * Diese Methode wird von dem ClientController aufgerufen um
	 * einem bereits erstelltem Spiel beizutreten.
	 * @param name String Der Name des Spiels.
	 */
	public void joinGame(final String name) {
	
	}

	/** 
	 * Wird vom Regelwerk aufgerufen und kontaktiert die Observer
	 * um das openChooseColour Fenster der View zu öffnen.
	 */
	private void openChooseColour() {
		
	}

	/** 
	 * Der ClientController ruft diese Methode auf um ein bereits erstelltes Spiel zu starten.
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
	 */
	public void makeMove() {
		
	}

	/** 
	 * Hilfsmethode die alle verbundenen Observer der GUI kontaktiert.
	 */
	private void informView() {
		
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
 * Diese Klasse implementiert die Netzwerkanbindung des Clients an den Server.
 * Sie ist eine innere Klasse des ClientModels und wird vom selbigen instanziert.
 * Sie enthält den dazu nötigen Socket und ObjektStream Reader und Writer.
 */
class MessageListenerThread extends Thread{
	
	/** Der TCP Socket. */
	private Socket connection;

	/**
	 * Erstellt die initiale Verbindung zum Server.
	 * @param connection TCP Socket über den die Verbindung erstellt wird.
	 * @throws IOException Diese Exception wird an den Aufrufenden weitergeleitet.
	 */
	protected MessageListenerThread(final Socket connection) throws IOException{
		this.connection = connection;
	}
	
	/**
	 * Hilfsmethode um den Benutzerlogin durchzuführen.
	 * @param request Die Loginnachricht welche den Benutzernamen enthält.
	 * @return boolean Zeigt an ob der Login erfolgreich war.
	 */
	protected boolean loginUser(final ComLoginRequest request){
		
	}
	
	/**
	 * Hilfsmethode die eine bestehende Verbindung abbaut.
	 */
	protected void closeConnection() {
		
	}
	
	/**
	 * Über diese Methode können Nachrichten an den Server versendet werden.
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