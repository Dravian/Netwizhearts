package Client;

import Ruleset.Card;
import Ruleset.ClientRuleset;
import Ruleset.RulesetType;
import Server.GameServerRepresentation;
import Client.View.Language;
import ComObjects.ComBeenKicked;
import ComObjects.ComChatMessage;
import ComObjects.ComClientLeave;
import ComObjects.ComClientQuit;
import ComObjects.ComCreateGameRequest;
import ComObjects.ComInitGameLobby;
import ComObjects.ComInitLobby;
import ComObjects.ComJoinRequest;
import ComObjects.ComLobbyUpdateGamelist;
import ComObjects.ComLoginRequest;
import ComObjects.ComObject;
import ComObjects.ComRuleset;
import ComObjects.ComServerAcknowledgement;
import ComObjects.ComUpdatePlayerlist;
import ComObjects.ComWarning;
import ComObjects.RulesetMessage;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

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

	private List<RulesetType> supportetGames;

	private List<String> playerList = new LinkedList<String>();

	private List<GameServerRepresentation> gameList = new LinkedList<GameServerRepresentation>();

	private StringBuilder warningText = new StringBuilder();

	private Thread netIOThread;

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
	public ClientModel(final MessageListenerThread netIO) throws IllegalArgumentException {
		if (netIO == null) {
			throw new IllegalArgumentException();
		}
		this.netIO = netIO;
		state = ClientState.LOGIN;
	}

	/**
	 * Wird aufgerufen, wenn der User die GameLobby verlaesst.
	 * Der Client gelangt zurueck in die Lobby.
	 */
	public void leaveWindow() {
		if (state == ClientState.GAMELOBBY) {
			netIO.send(new ComClientLeave());
		} else if (state == ClientState.GAME) {
			netIO.send(new ComClientLeave());
		}
	}

	/**
	 * Wird aufgerufen um die View zu beenden, wenn 
	 * der Server die Verbindung beendet,
	 * oder ein Netzwerkfehler auftritt.
	 */
	protected void closeView() {
		//TODO oder mit Dialog.
		warningText.append("<" + new Date() + "> " + "Error: Connection lost.\n");
		informView(ViewNotification.openWarning);
		informView(ViewNotification.quitGame);
	}

	/**
	 * Wird aufgerufen, wenn der Spieler das Programm beendet.
	 * Leitet den Verbindungsabbau zum Server ein.
	 */
	public void closeProgram() {
		netIO.send(new ComClientQuit());
		netIO.closeConnection();
		netIO = null;
		warningText = null;
		playerList = null;
		gameList = null;
		System.exit(0);
	}

	/**
	 * Sendet eine eingehende Chatnachricht direkt an alle Observer weiter.
	 *
	 * @param msg die ankommende ComChatMessage Nachricht
	 */
	public void receiveMessage(ComChatMessage msg) {
		if (msg != null) {
			if (!msg.getChatMessage().isEmpty()) {
				setChanged();
				notifyObservers(msg.getChatMessage());
			}
		}
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
		if (msg != null) {
			if (state == ClientState.GAMELOBBY) {
				state = ClientState.SERVERLOBBY;
				if (msg.getPlayerList() != null) {
					playerList = msg.getPlayerList();
				}
				if (msg.getGameList() != null) {
					gameList = new LinkedList<GameServerRepresentation>(msg.getGameList());
				}
				//TODO Oder evtl. den Dialog.
				warningText.append("<" + new Date() + "> " + "Game master has left the game.\n");
				informView(ViewNotification.openWarning);
				informView(ViewNotification.windowChangeForced);
			} else if (state == ClientState.GAME) {
				state = ClientState.SERVERLOBBY;
				if (msg.getPlayerList() != null) {
					playerList = msg.getPlayerList();
				}
				if (msg.getGameList() != null) {
					gameList = new LinkedList<GameServerRepresentation>(msg.getGameList());
				}
				//TODO Oder evtl. den Dialog.
				warningText.append("<" + new Date() + "> " + "Game has been closed unexpectedly.\n");
				informView(ViewNotification.openWarning);
				informView(ViewNotification.windowChangeForced);
			} else if (state == ClientState.LOGIN) {
				state = ClientState.SERVERLOBBY;
				if (msg.getPlayerList() != null) {
					playerList = msg.getPlayerList();
				}
				if (msg.getGameList() != null) {
					gameList = new LinkedList<GameServerRepresentation>(msg.getGameList());
				}
				informView(ViewNotification.loginSuccessful);
			}
		}
	}

	//TODO wird evtl für den Übergang der Fenster benötigt ?
	public ClientState getClientState() {
		return state;
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
		if (msg != null) {
			if (state == ClientState.JOINREQUEST) {
				state = ClientState.GAMELOBBY;
				if (msg.getPlayerList() != null) {
					playerList = msg.getPlayerList();
				}
				informView(ViewNotification.joinGameSuccessful);
			} else if (state == ClientState.GAMECREATION) {
				state = ClientState.GAMELOBBY;
				if (msg.getPlayerList() != null) {
					playerList = msg.getPlayerList();
				}
				informView(ViewNotification.joinGameSuccessful);
			}
		}
	}

	/**
	 * Diese Methode wird aufgerufen,
	 * falls eine Nachricht für das Regelwerk ankommt. Die
	 * darin enthaltene RulesetMessage wird dem ClientRuleset
	 * zur Verarbeitung uebergeben.
	 * @param msg Die ankommende ComRuleset Nachricht
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

	public void receiveMessage(ComWarning warning) {
		if (warning != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String time = sdf.format(Calendar.getInstance().getTime());
			if (state == ClientState.LOGIN) {
				netIO.closeConnection();
				netIOThread = null;
				warningText.append("<" + time + "> " + "Username already in use.\n");
				informView(ViewNotification.openWarning);
			} else if (state == ClientState.JOINREQUEST) {
				//TODO Eindeutigkeit ob Spiel voll oder Passwort falsch nur mit
				//String aus der Warnung abprüftbar ---> fehler enum einbauen?
				state = ClientState.SERVERLOBBY;
				warningText.append("<" + time + "> " + "Wrong password or Game full.\n");
				informView(ViewNotification.openWarning);
			} else if (state == ClientState.GAMECREATION) {
				state = ClientState.SERVERLOBBY;
				warningText.append("<" + time + "> " + "Cannot create game.\n");
				informView(ViewNotification.openWarning);
			}
		} else {
			//TODO Fehler in Kommunikation
		}
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
		if (update != null) {
			if (update.getPlayerName() != null) {
				playerList.remove(update.getPlayerName());
				if (!update.isRemoveFlag()) {
					playerList.add(update.getPlayerName());
				} 
				informView(ViewNotification.playerListUpdate);
			}
		}
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
		if (update != null) {
			GameServerRepresentation gameUpdate = update.getGameServer();
			String gameMaster;
			if (gameUpdate != null) {
				for (GameServerRepresentation gameInList : gameList ) {
					gameMaster = gameInList.getGameMasterName();
					if (gameMaster.equals(gameUpdate.getGameMasterName())) {
						gameList.remove(gameInList);
						break;
					}
				}
				if (!update.isRemoveFlag()) {
					gameList.add(update.getGameServer());
				}
				informView(ViewNotification.gameListUpdate);
			}
		}
	}
	
	public void receiveMessage(ComClientQuit quit) {
		if (quit != null) {
			if (state == ClientState.LOGIN) {
				netIO.closeConnection();
			} else {
				netIO.closeConnection();
				closeView();
			}	
		}
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
	 * @return Liste von Spielernamen oder null wenn leer.
	 */
	public List<String> getPlayerlist() {
		return playerList;
	}

	/**
	 * Liefert eine Liste der Spiele, die aktuell auf dem Server offen sind
	 * oder gerade gespielt werden.
	 *
	 * @return Liste aller Spiele der Lobby oder null wenn leer.
	 */
	public List<GameServerRepresentation> getLobbyGamelist() {
		return gameList;
	}

	/**
	 * Gibt eine Liste aller bereits ausgespielten Karten zurueck.
	 *
	 * @return List<Card>. Eine Liste der gespielten Karten.
	 */
	public List<Card> getPlayedCards() {
		return null;
	}

	/**
	 * Gibt eine Liste der Handkarten des Spielers zurueck.
	 *
	 * @return List<Card> aller Handkarten des Spielers
	 */
	public List<Card> getOwnHand() {
		return null;
	}

	/**
	 * Gibt zusaetzliche Daten der anderen Spieler zurueck.
	 *
	 * @return List<String> der Stringrepraesentationen der OtherData der Spieler
	 */
	public List<String> getOtherPlayerData() {
		return null;

	}

	/**
	 * Gibt den Punktestand des Spielers zurueck.
	 *
	 * @return int Der eigene Punktestand.
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
	 * @param game das zu verwendende Regelwerk
	 * @throws IllegalArgumentException Wenn RulesetType null.
	 */
	public void hostGame(String gameName,
						 boolean hasPassword, String password,
						 RulesetType game) throws IllegalArgumentException {
		if (gameName == null) {
			gameName = new String();
		}
		if (hasPassword) {
			if (password == null) {
				hasPassword = false;
				password = new String();
			} else if (password.isEmpty()) {
				hasPassword = false;
			}
		} else {
			password = new String();
		}
		if (game == null) {
			throw new IllegalArgumentException();
		} else {
			state = ClientState.GAMECREATION;
			netIO.send(new ComCreateGameRequest(gameName, game, hasPassword, password));
		}
	}

	/**
	 * Sendet eine RulesetMessage an den Server. Erstellt dazu eine
	 * ComRuleset, die die RulesetMessage enthaelt.
	 *
	 * @param msg die RulesetMessage, die an den Server geschickt werden soll
	 */
	public void send(RulesetMessage msg) {
		netIO.send(new ComRuleset(msg));
	}

	/**
	 * Gibt die Anzahl der Spieler eines Spieles zurueck.
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
	 * @return String Text der Bildschirmmeldung.
	 */
	public String getWindowText() {
		return null;
	}

	/**
	 * Gibt die Karten zurueck, aus denen gewaehlt werden soll.
	 * 
	 * @return List<Card> Karten, aus denen gewaehlt werden kann
	 */
	public List<Card> getChooseCards() {
		return null;
	}

	/**
	 * Uebergibt die Karten, die vom User gewahlt wurden. Diese
	 * werden dann dem Regelwerk weitergegeben. Akzeptiert dieses
	 * die gewaehlten Karten nicht, wird nochmal openChooseCards aufgerufen.
	 *
	 * @param cards Karten, die der User gewaehlt hat
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
	 * das gewaehlte Item nicht, wird nochmal openChooseItem aufgerufen.
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
		if (msg != null) {
			if (!msg.isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				String time = sdf.format(Calendar.getInstance().getTime());
				netIO.send(new ComChatMessage(playerName + "(" + time + "): " + msg + "\n"));
			}
		}
	}

	/**
	 * Versucht einem Spiel beizutreten. Sendet dazu eine ComJoinRequest Nachricht an
	 * den Server. Wird diese bestaetigt, gelangt der Client in die GameLobby. Wird die
	 * Nachricht nicht bestaetigt, wird eine Fehlermeldung ausgegeben und die Observer
	 * mit openWarning informiert.
	 *
	 * @param name String Der Name des Spiels.
	 * @param password String Passwort eines Spieles.
	 * @throws IllegalArgumentException Wird geworfen, wenn ein leerer
	 * oder null Wert in name uebergeben wird.
	 */
	public void joinGame(String name, String password) throws IllegalArgumentException {
		if (password == null) {
			password = new String();
		}
		if (name == null) {
			throw new IllegalArgumentException();
		} else if (name.isEmpty()) {
			throw new IllegalArgumentException();
		} else {
			state = ClientState.JOINREQUEST;
			netIO.send(new ComJoinRequest(name, password));
		}
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
	 * @param card Die gespielte Karte.
	 */
	public void makeMove(Card card) {

	}

	/**
	 * Wird aufgerufen wenn das Ende einer Partie erreicht ist.
	 * 
	 * @param winner String der Gewinner der Partie.
	 */
	public void announceWinner(final String winner) {

	}

	/**
	 * Liefert den Gewinner einer Partie.
	 *
	 * @return String der Gewinner.
	 */
	public String getWinner() {
		return null;
	}

	/**
	 * Hilfsmethode die alle verbundenen Observer der GUI kontaktiert.
	 *
	 * @param note Enum der die Art des Aufrufes bestimmt.
	 */
	private void informView(ViewNotification note) {
		setChanged();
		notifyObservers(note);
	}

	/**
	 * Erstellt den MessageListenerThread und fuehrt den Benutzerlogin durch.
	 *
	 * @param username String der eindeutige Benutzername der für den Login verwendet wird.
	 * @param host String die Adresse des spielservers.
	 * @param port Integer der Port des Spielservers.
	 */
	public void createConnection(String username,
								 String host,
								 int por)
										 throws IllegalArgumentException {
		state = ClientState.LOGIN;
		URI uri = null;
		int port = 4567;
		boolean fault = false;
		if (username == null) {
			throw new IllegalArgumentException();
		}
		if (host == null) {
			throw new IllegalArgumentException();
		}
		if (username.isEmpty()) {
			fault = true;
			warningText.append("<" + new Date() + "> " + "Error: Empty Username.\n");
		}
		if (host.isEmpty()) {
			fault = true;
			warningText.append("<" + new Date() + "> " + "Error: Empty Host Address.\n");
		} else {
			try {
				uri = new URI("http://" + host);
				port = uri.getPort();
				if (uri.getHost() == null) {
					fault = true;
					warningText.append("<" + new Date() + "> " + "Error: Unrecognizable Host Address.\n");
				}
				if (port == -1) {
					//TODO standard port.
					port = 4567;
				} else if (port < 1025 || port > 49151) {
					fault = true;
					warningText.append("<" + new Date() + "> " + "Error: Portnumber out of Range.\n");
				}
			} catch (URISyntaxException e) {
				fault = true;
				warningText.append("<" + new Date() + "> " + "Error: Unrecognizable Host Address.\n");
			}
		}
		if (fault) {
			informView(ViewNotification.openWarning);
		} else {
			playerName = username;
			setupConnection(username, uri.getHost(), port);
		}
	}

	private void setupConnection(String username, String host, int port ) {
		try {
			netIO.startConnection(this, host, port);
			netIOThread = new Thread(netIO);
			netIOThread.start();
			netIO.send(new ComLoginRequest(username));
		} catch (ConnectException e) {
			warningText.append("<" + new Date() + "> " + "Error: Unknown Host.\n");
			informView(ViewNotification.openWarning);
		} catch (SocketException e) {
			System.err.println("<" + new Date() + "> " + "Error: At Socket.\n");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			warningText.append("<" + new Date() + "> " + "Error: Unknown Host.\n");
			informView(ViewNotification.openWarning);
		} catch (IOException e) {
			System.err.println("<" + new Date() + "> " + "Error: At IO.\n");
			e.printStackTrace();
		}
	}

	/**
	 * Gibt den Text aus der bei einer Spielwarnung
	 * angezeigt wird.
	 *
	 * @return String Text der Warnung.
	 */
	public String getWarningText() {
		String text = warningText.toString();
		warningText.delete(0, warningText.length());
		return text;
	}

	private void prepRulesetList() {
		supportetGames = new LinkedList<RulesetType>();
		supportetGames.add(RulesetType.Wizard);
		supportetGames.add(RulesetType.Hearts);
	}

	/**
	 * Liefert eine Liste mit allen implementierten Regelwerken.
	 *
	 * @param List<RulesetType> Liste von unterstuetzten Regelwerken.
	 */
	public List<RulesetType> getRulesets() {
		prepRulesetList();
		return supportetGames;
	}
}