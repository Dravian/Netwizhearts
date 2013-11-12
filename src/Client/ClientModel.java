package Client;

import Ruleset.Card;
import Ruleset.ClientRuleset;
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

import java.util.List;
import java.util.Observable;
import java.util.Set;

/** 
 * ClientModel. Das ClientModel ist die Schnittstelle zwischen dem MessageListenerThread, 
 * dem ClientRuleset und der View. Das Model prüft Nachrichten, welche es vom
 * MessageListenerThread über die Methode receiveMessage() bekommt. RulesetMessages
 * werden an das ClientRuleset weitergeleitet. Weiterhin informiert es seine Observer über
 * Veraenderungen und stellt ihnen Methoden zu Verfügung um spielrelevante Daten zu lesen.
 * Weiterhin kann das ClientModel ComMessages and den Server schicken, um Kommandos des
 * ClientRulesets oder Eingaben des Controllers weiterzugeben.
 */
public class ClientModel extends Observable{
	/** 
	 * String der den eindeutigen Spielernamen repraesentiert.
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
	
	private List<String> playerList;
	
	private String chatMessage;

	private Set<GameServerRepresentation> gameList;
	
	/** 
	 * Haelt den für die Netzwerkkomunikation zustaendigen Thread.
	 */
	private MessageListenerThread netIO;

	/**
	 * Erstellt ein ClientModel und erwartet als
	 * Argument einen MessageListenerThread fuer
	 * die Netzwerkanbindung.
	 * @param netIO MessageListenerThread fuer die Netzwerkverbindung.
	 * @throws IllegalArgumentException Wird geworfen bei fehlerhaftem
	 * MessageListenerThread Argument.
	 */
	public ClientModel(MessageListenerThread netIO) throws IllegalArgumentException {
		this.netIO = netIO;
	}
	
	/**
	 * Wird aufgerufen, wenn der User die GameLobby verlaesst. 
	 * Der Client gelangt zurueck in die Lobby.
	 */
	public void leaveWindow() {
		
	}
	
	/**
	 * Wird aufgerufen, wenn ein Fehler bei der Verbindung
	 * zum Server auftritt und die korrekte Ausfuehrung des Programs
	 * deswegen nicht mehr gewaerleistet werden kann.
	 */
	public void terminateProgram() {
		
	}

	/** 
	 * Sendet eine eingehende Chatnachricht direkt an alle Observer weiter.
	 * 
	 * @param msg die ankommende ComChatMessage Nachricht
	 */
	public void receiveMessage(ComChatMessage msg) {
		this.chatMessage = msg.getChatMessage();
		setChanged();
		notifyObservers(chatMessage);
	}
	
	/** 
	 * Diese Methode wird aufgerufen,
	 * falls der Server den Spieler erfolgreich in die Lobby hinzugefügt hat.
	 * Empfaengt die ComInitGameLobby Nachricht, die eine Liste aller
	 * Spieler enthaelt, die sich in der Lobby befinden. Speichert
	 * diese Liste und benachrichtigt die Observer mit der loginSuccesful
	 * ViewNotification.
	 * 
	 * @param msg die ankommende ComInitLobby Nachricht
	 */
	public void receiveMessage(ComInitLobby msg) {
		
	}
	
	/** 
	 * Diese Methode wird aufgerufen,
	 * falls der Server den Spieler erfolgreich in die GameLobby hinzugefuegt hat.
	 * Empfaengt die ComInitGameLobby Nachricht, die eine Liste aller
	 * Spieler enthaelt, die sich in der GameLobby befinden. Speichert
	 * diese Liste und benachrichtigt die Observer mit der joinGameSuccesful
	 * ViewNotification.
	 * 
	 * @param msg die ankommende ComInitGameLobby Nachricht
	 */
	public void receiveMessage(ComInitGameLobby msg) {
		
	}
	
	/** 
	 * Diese Methode wird aufgerufen,
	 * falls eine Nachricht für das Regelwerk ankommt. Die
	 * darin enthaltene RulesetMessage wird dem ClientRuleset
	 * zur Verarbeitung uebergeben.
	 * 
	 * @param die ankommende ComRuleset Nachricht
	 */
	public void receiveMessage(ComRuleset msg) {
		
	}
	
	/** 
	 * Diese Methode wird aufgerufen,
	 * falls ein Server Acknowledgement auftritt.
	 * Dabei ist es von Bedeutung, in welchem Zustand sich der Client befindet.
	 *  
	 *  @param ack Eine Bestätigung durch den Server.
	 */
	public void receiveMessage(ComServerAcknowledgement ack) {
		
	}
	
	/** 
	 * Diese Methode wird aufgerufen,
	 * falls der Spieler aus der Spiellobby durch einen Spielleiter
	 * entfernt wurde. Der Client gelangt zurueck in die Lobby,
	 * die Observer werden mit windowChangeForced benachrichtigt.
	 * 
	 * @param msg die ankommende ComBeenKicked Nachricht
	 */
	public void receiveMessage(ComBeenKicked msg) {
		
	}
	
	/**
	 * Diese Methode wird aufgerufen,
	 * falls auf dem Server ein neuer Spieler die Lobby/GameLobby
	 * betreten hat oder sie von einem Spieler verlassen wurde.
	 * Empfaengt die ComUpdatePlayerlist Nachricht, die die Information
	 * enthaelt, ob und welcher Spieler hinzugefuegt oder entfernt werden muss.
	 * Die Spielerliste wird dementsprechend bearbeitet und die Observer mit
	 * playerListUpdate informiert.
	 * 
	 * @param update die ankommende ComLobbyUpdatePlayerlist Nachricht
	 */
	public void receiveMessage(ComUpdatePlayerlist update) {
		
	}
	
	/**
	 * Diese Methode wird aufgerufen,
	 * falls auf dem Server ein neues Spiel erstellt wurde oder
	 * ein Spiel geschlossen/beendet wurde.
	 * Empfaengt die ComLobbyUpdateGamelist Nachricht, die die Information
	 * enthaelt, ob und welches Spiel hinzugefuegt oder entfernt werden muss.
	 * Die Spielliste wird dementsprechend bearbeitet und die Observer mit
	 * gameListUpdate informiert.
	 * 
	 * @param update die ankommende ComLobbyUpdateGamelist Nachricht
	 */
	public void receiveMessage(ComLobbyUpdateGamelist update) {
		
	}
	
	/**
	 * Standard receiveMessage Methode, die ComObjekte
	 * zur Weiterverarbeitung identifiziert.
	 * @param com Das auflaufende ComObjekt.
	 */
	public void receiveMessage(ComObject com) {
		
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
	 * Gibt eine Liste aller bereits ausgespielten Karten zurueck.
	 * 
	 * @return Card. Die Ids der Karten
	 */
	public List<Card> getPlayedCards(){
		return null;
	}
	
	/**
	 * Gibt eine Liste der Handkarten des Spielers zurueck.
	 * 
	 * @param Liste aller Handkarten des Spielers
	 */
	public List<Card> getOwnHand() {
		return null;
	}
	
	/**
	 * Gibt zusaetzliche Daten der anderen Spieler zurueck.
	 * 
	 * @return Liste der Stringrepräsentationen der OtherData der Spieler
	 */
	public List<String> getOtherPlayerData() {
		return null;
		
	}
	
	/**
	 * Gibt den Punktestand des Spielers zurueck.
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
	 * Sendet erstellte ComObjects an den Server.
	 * 
	 * @param object ComObject, das verschickt wird
	 */
	public void send(ComObject object) {
		netIO.send(object);
	}
	
	/**
	 * Sendet eine RulesetMessage an den Server. Erstellt dazu eine
	 * ComRuleset, die die RulesetMessage enthaelt.
	 * 
	 * @param msg die RulesetMessage, die an den Server geschickt werden soll
	 */
	public void send(RulesetMessage msg) {
		ComObject com = new ComRuleset(msg);
		send(com);
	}
	
	/**
	 * Die die Anzahl der Spieler eines Spieles zurueck.
	 * 
	 * @return int Die Spielerzahl eines Spieles.
	 */
	public int getPlayerCount() {
		return 0;
		
	}
	
	/**
	 * Gibt den Text zurueck, der in einem Sonderfenster 
	 * (InputNumber, ChooseItem, ChooseCards) angezeigt werden soll.
	 * 
	 * @return String 
	 */
	public String getWindowText() {
		return null;
	}
	
	/**
	 * Gibt die Karten zurueck, aus denen gewaehlt werden soll.
	 * 
	 * @return Karten, aus denen gewahlt werden kann
	 */
	public List<Card> getChooseCards() {
		return null;
	}
	
	/**
	 * Uebergibt die Karten, die vom User gewahlt wurden. Diese
	 * werden dann dem Regelwerk weitergegeben. Akzeptiert dieses
	 * die gewahlten Karten nicht, wird nochmal openChooseCards aufgerufen.
	 * 
	 * @param cards Karten, die der User gewahlt hat
	 */
	public void giveChosenCards(List<Card> cards) {
		
	}
	
	/**
	 * Benachrichtigt die Observer mit der openChooseCards ViewNotification
	 * und speichert die Liste der Karten sowie den Anzeigetext des Regelwerks
	 * zwischen.
	 * 
	 * @param cards Liste der Karten, von denen gewaehlt werden kann
	 * @param text Text, der dem User angezeigt werden soll
	 */
	public void openChooseCards(List<Card> cards, String text) {
		
	}
	
	/**
	 * Gibt die Items zurueck, aus denen eines gewaehlt werden soll.
	 * 
	 * @return Items, aus denen gewahlt werden kann
	 */
	public List<Object> getChooseItems() {
		return null;
	}
	
	/**
	 * Uebergibt das Item, das vom User gewahlt wurden. Dieses
	 * wird dann dem Regelwerk weitergegeben. Akzeptiert dieses
	 * das gewahlte Item nicht, wird nochmal openChooseItem aufgerufen.
	 * 
	 * @param item Item, das der User gewahlt hat
	 */
	public void giveChosenItem(Object item) {
		
	}
	
	/**
	 * Benachrichtigt die Observer mit der openChooseItem ViewNotification
	 * und speichert die Liste der Items, von denen eines gewaehlt werden soll,
	 * sowie den Anzeigtetext des Regelwerks zwischen.
	 * 
	 * @param items Liste der Items, von denen eines gewaehlt werden soll
	 * @param text Text, der dem User angezeigt werden soll
	 */
	public void openChooseItem(List<Object> items, String text) {
		
	}
	
	/**
	 * Uebergibt die Zahl, die vom User gewahlt wurde. Diese 
	 * wird dann dem Regelwerk weitergegeben. Akzeptiert dieses
	 * die gewaehlte Zahl nicht, wird nochmal openInputNumber
	 * aufgerufen.
	 * 
	 * @param number Zahl, die vom User gewahlt wurde
	 */
	public void giveInputNumber(int number) {
		
	}
	
	/**
	 * Benachrichtigt die Observer mit der openInputNumber ViewNotification
	 * und speichert den Anzeigetext des Regelwerks zwischen.
	 * 
	 * @param text Text, der dem User angezeigt werden soll
	 */
	public void openInputNumber(String text) {
		
	}
	
	/**
	 * Nimmt vom ClientController eine Chatnachricht entgegen
	 * und sendet diese an den Server.
	 * 
	 * @param msg die Chatnachricht, die an den Server geschickt werden soll
	 */
	public void sendChatMessage(final String msg) {
		send(new ComChatMessage(msg));
	}

	/** 
	 * Versucht einem Spiel beizutreten. Sendet dazu eine ComJoinRequest Nachricht an
	 * den Server. Wird diese bestaetigt, gelangt der Client in die GameLobby. Wird die
	 * Nachricht nicht bestaetigt, wird eine Fehlermeldung ausgegeben und die Observer
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
	 * Versucht eine Karte auszuspielen. Laesst dazu vom ClientRuleset ueberpruefen ob, die ausgewaehlte
	 * Karte gespielt werden darf. Wenn ja, wird sie im ClientRuleset weiterbehandelt. Wenn nein,
	 * wird eine Fehlermeldung ausgegeben und dazu die Observer mit openWarning informiert.
	 * 
	 * @param die ID der gespielten Karte
	 */
	public void makeMove(Card card) {
		
	}

	/** 
	 * Hilfsmethode die alle verbundenen Observer der GUI kontaktiert.
	 * 
	 * @param note Enum der die Art des Aufrufes bestimmt.
	 */
	private void informView(ViewNotification note) {
		
	}
	
	/** 
	 * Erstellt den MessageListenerThread und fuehrt den Benutzerlogin durch.
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
}