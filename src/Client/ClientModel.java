package Client;

import Ruleset.Card;
import Ruleset.ClientHearts;
import Ruleset.ClientRuleset;
import Ruleset.ClientWizard;
import Ruleset.Colour;
import Ruleset.GameClientUpdate;
import Ruleset.RulesetType;
import Ruleset.UserMessages;
import Server.GameServerRepresentation;
import Client.View.Language;
import ComObjects.ComChatMessage;
import ComObjects.ComClientLeave;
import ComObjects.ComClientQuit;
import ComObjects.ComCreateGameRequest;
import ComObjects.ComInitGameLobby;
import ComObjects.ComInitLobby;
import ComObjects.ComJoinRequest;
import ComObjects.ComKickPlayerRequest;
import ComObjects.ComLobbyUpdateGamelist;
import ComObjects.ComLoginRequest;
import ComObjects.ComRuleset;
import ComObjects.ComStartGame;
import ComObjects.ComUpdatePlayerlist;
import ComObjects.ComWarning;
import ComObjects.RulesetMessage;
import ComObjects.WarningMsg;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * ClientModel. Das ClientModel ist die Schnittstelle zwischen dem
 * MessageListenerThread, dem ClientRuleset und der View. Das Model
 * prüft Nachrichten, welche es vom MessageListenerThread über die
 * Methode receiveMessage() bekommt. RulesetMessages werden an das
 * ClientRuleset weitergeleitet. Weiterhin informiert es seine Observer
 * über Veraenderungen und stellt ihnen Methoden zu Verfügung um
 * spielrelevante Daten zu lesen. Weiterhin kann das ClientModel
 * ComMessages and den Server schicken, um Kommandos des ClientRulesets
 * oder Eingaben des Controllers weiterzugeben.
 */
public class ClientModel extends Observable {

	/**
	 * Standardport des Spielservers.
	 */
	public static final int PORT = 4567;

	/**
	 * kleinstmoeglicher port.
	 */
	public static final int MINPORT = 1025;

	/**
	 * Groessmoeglicher zulaessiger port.
	 */
	public static final int MAXPORT = 49151;

    /**
    * String der den eindeutigen Spielernamen repraesentiert.
    */
	private String playerName;

	/**
	 * Der GameMaster des aktuellen Spieles.
	 */
	private String gameMaster;

	/**
	 * Referenz auf das Regelwerk des Spieles.
	 */
	private ClientRuleset ruleset;

	/**
	 * Das zu erstellende Spiel.
	 */
	private RulesetType gameType;

	/**
	 * Die aktuelle Sprache der GUI.
	 */
	private Language language;

	/**
	 * Der Zustand indem sich der Client befindet.
	 */
	private ClientState state;

	/**
	 * Liste mit allen unterstuetzten Spieltypen.
	 */
	private List<RulesetType> supportetGames;

	/**
	 * Spieler in der Server- oder Gamelobby.
	 */
	private List<String> playerList;

	/**
	 * Spiele in der Serverlobby.
	 */
	private List<GameServerRepresentation> gameList;

	/**
	 * Enthaelt den Text für das Warnungsfenster.
	 */
	private StringBuffer warningText;

	/**
	 * Thread für die Netzwerkverbindung.
	 */
	private Thread netIOThread;

	/**
	 * Referenz auf die Textfabrik für Bildschirmmeldungen.
	 */
	private LanguageInterpreter screenOut;

	/**
	 * Text für die Sonderfenster.
	 */
	private String windowText;

	/**
	 * Haelt den für die Netzwerkkomunikation zustaendigen Thread.
	 */
	private MessageListenerThread netIO;

	/**
	 * Erstellt ein ClientModel und erwartet als
	 * Argument einen MessageListenerThread fuer
	 * die Netzwerkanbindung.
	 * @param net MessageListenerThread fuer die Netzwerkverbindung.
	 * MessageListenerThread Argument.
	 */
	public ClientModel(final MessageListenerThread net) {
		if (net == null) {
			throw new IllegalArgumentException();
		}
		this.netIO = net;
		state = ClientState.LOGIN;
		this.language = Language.English;
		screenOut = new LanguageInterpreter(language);
		playerName = new String();
		warningText = new StringBuffer();
		playerList = Collections.synchronizedList(new LinkedList<String>());
		gameList = Collections.synchronizedList(
				new LinkedList<GameServerRepresentation>());
		ruleset = null;
		prepRulesetList();
	}

	/**
	 * Wird aufgerufen,
	 * wenn der User in die ServerLobby zurückkehren möchte.
	 */
	public final void returnToLobby() {
		if (state != ClientState.LOGIN
				&& state != ClientState.SERVERLOBBY) {
			netIO.send(new ComClientLeave());
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Wird aufgerufen um die View zu beenden, wenn
	 * ein Netzwerkfehler auftritt.
	 */
	protected final void closeView() {
		warningText.append(screenOut.resolveWarning(
				WarningMsg.ConnectionLost));
		informView(ViewNotification.openWarning);
		informView(ViewNotification.quitGame);
	}

	/**
	 * Wird aufgerufen, wenn der Spieler das Programm beendet.
	 * Leitet den Verbindungsabbau zum Server ein.
	 */
	public final void closeProgram() {
		netIO.send(new ComClientQuit());
		netIO.closeConnection();
		netIO = null;
		warningText = null;
		playerList = null;
		gameList = null;
	}

	/**
	 * Sendet eine eingehende Chatnachricht direkt an alle Observer weiter.
	 *
	 * @param msg die ankommende ComChatMessage Nachricht
	 */
	public final void receiveMessage(final ComChatMessage msg) {
		if (msg != null) {
			if (!msg.getChatMessage().isEmpty()) {
				setChanged();
				notifyObservers(msg.getChatMessage());
			} else {
					throw new IllegalArgumentException("Leerer String");
			}
		} else {
			throw new IllegalArgumentException("Argument ist null");
		}
	}

	/**
	 * Diese Methode wird aufgerufen, falls
	 * der Server den Spieler erfolgreich in die Lobby hinzugefügt hat.
	 * Empfaengt die ComInitGameLobby Nachricht, die eine Liste aller
	 * Spieler enthaelt, die sich in der Lobby befinden.
	 *
	 * @param msg die ankommende ComInitLobby Nachricht
	 */
	public final void receiveMessage(final ComInitLobby msg) {
		if (msg != null) {
			state = ClientState.SERVERLOBBY;
			gameMaster = new String();
			ruleset = null;
			if (msg.getPlayerList() != null) {
				playerList = Collections.synchronizedList(msg.getPlayerList());
				if (playerList.isEmpty()) {
					throw new IllegalArgumentException("Leere Spielerliste");
				}
			}
			if (msg.getGameList() != null) {
				gameList = Collections.synchronizedList(
				  new LinkedList<GameServerRepresentation>(msg.getGameList()));
			} else {
				throw new IllegalArgumentException("Spielliste ist null");
			}
			informView(ViewNotification.windowChangeForced);
		} else {
			throw new IllegalArgumentException("Argument ist null");
		}
	}

	/**
	 * Diese Methode wird aufgerufen, falls der Server den Spieler
	 * erfolgreich in die GameLobby hinzugefuegt hat.
	 * Empfaengt die ComInitGameLobby Nachricht, die eine Liste aller
	 * Spieler enthaelt, die sich in der GameLobby befinden. Speichert
	 * diese Liste und benachrichtigt die Observer mit der joinGameSuccesful
	 * ViewNotification.
	 *
	 * @param msg die ankommende ComInitGameLobby Nachricht
	 */
	public final void receiveMessage(final ComInitGameLobby msg) {
		if (state == ClientState.ENTERGAMELOBBY) {
			if (msg != null) {
				state = ClientState.GAMELOBBY;
				if (msg.getPlayerList() != null) {
					playerList = Collections.synchronizedList(
							msg.getPlayerList());
					if (!playerList.isEmpty()) {
						informView(ViewNotification.joinGameSuccessful);
					} else {
						throw new IllegalArgumentException("Leere"
								+ " Spielerliste");
					}
				} else {
					throw new IllegalArgumentException("Spielerliste"
							+ " ist null");
				}
			} else {
				throw new IllegalArgumentException("Argument value is null");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Diese Methode wird aufgerufen,
	 * falls eine Nachricht für das Regelwerk ankommt. Die
	 * darin enthaltene RulesetMessage wird dem ClientRuleset
	 * zur Verarbeitung uebergeben.
	 *
	 * @param msg Die ankommende ComRuleset Nachricht
	 */
	public final void receiveMessage(final ComRuleset msg) {
		if (state == ClientState.GAME) {
		   if (msg != null) {
			   if (msg.getRulesetMessage() != null) {
				   msg.getRulesetMessage().visit(ruleset);
			   } else {
					throw new IllegalArgumentException("Regelwernachricht"
							+ " ist null");
				}
		   } else {
				throw new IllegalArgumentException("Argument ist null");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Verarbeitet Warnungen die von den Server an den Client
	 * gesendet werden. ComWarning kann als negatives ACK
	 * betrachtet werden.
	 *
	 * @param warning Die Warnung vom Server.
	 */
	public final void receiveMessage(final ComWarning warning) {
		if (warning != null) {
			if (state == ClientState.LOGIN) {
				playerName = new String();
				netIO.closeConnection();
				netIOThread = null;
				warningText.append(screenOut.resolveWarning(
						WarningMsg.LoginError));
				informView(ViewNotification.openWarning);
			} else if (state == ClientState.ENTERGAMELOBBY) {
				state = ClientState.SERVERLOBBY;
				gameMaster = new String();
				gameType = null;
				warningText.append(screenOut.resolveWarning(
						warning.getWarning()));
				informView(ViewNotification.openWarning);
			} else {
				warningText.append(screenOut.resolveWarning(
						warning.getWarning()));
				informView(ViewNotification.openWarning);
			}
		} else {
			throw new IllegalArgumentException("Argument ist null");
		}
	}

	/**
	 * Startet das Spiel am Client und instanziert ein neues Regelwerk.
	 *
	 * @param msg Die Servernachricht.
	 */
	public final void receiveMessage(final ComStartGame msg) {
		if (state == ClientState.GAMELOBBY) {
			if (msg != null) {
				switch (gameType) {
					case Hearts:
						ruleset = new ClientHearts(this);
						state = ClientState.GAME;
						informView(ViewNotification.gameStarted);
						break;
					case Wizard:
						ruleset = new ClientWizard(this);
					 	state = ClientState.GAME;
						informView(ViewNotification.gameStarted);
						break;
					default:
						throw new IllegalArgumentException("Regelwerk"
								+ " nicht verfuegbar");
				}
			} else {
				throw new IllegalArgumentException("Argument ist null");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
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
	public final void receiveMessage(final ComUpdatePlayerlist update) {
		if (state == ClientState.SERVERLOBBY
				|| state == ClientState.GAMELOBBY) {
			if (update != null) {
				if (update.getPlayerName() != null) {
					synchronized (playerList) {
						if (update.isRemoveFlag()) {
							if (!playerList.remove(update.getPlayerName())) {
								throw new IllegalArgumentException("Spieler"
									+ " nicht in der Liste gefunden");
							}
						} else {
							if (playerList.remove(update.getPlayerName())) {
								throw new IllegalArgumentException("Spieler"
									+ " bereits in der Liste");
							}
							playerList.add(update.getPlayerName());
						}
					}
					informView(ViewNotification.playerListUpdate);
				} else {
					throw new IllegalArgumentException("Spieler"
							+ " name ist null");
				}
			} else {
				throw new IllegalArgumentException("Argument ist null");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
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
	public final void receiveMessage(final ComLobbyUpdateGamelist update) {
		if (state == ClientState.SERVERLOBBY
				|| state == ClientState.ENTERGAMELOBBY) {
			if (update != null) {
				GameServerRepresentation gameUpdate = update.getGameServer();
				String key;
				if (gameUpdate != null) {
					synchronized (gameList) {
						for (GameServerRepresentation gameInList : gameList ) {
							key = gameInList.getGameMasterName();
							if (key.equals(gameUpdate.getGameMasterName())) {
								gameList.remove(gameInList);
								break;
							}
						}
						if (!update.isRemoveFlag()) {
							gameList.add(update.getGameServer());
						}
					}
					informView(ViewNotification.gameListUpdate);
				} else {
					throw new IllegalArgumentException("Spielupdate"
							+ " ist null");
				}
			} else {
				throw new IllegalArgumentException("Argument ist null");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}
	/**
	 * Wird aufgerufen, wenn der Server die Verbindung beendet.
	 *
	 * @param quit Die Nachricht zum beenden der Verbindung.
	 */
	public final void receiveMessage(final ComClientQuit quit) {
		if (quit != null) {
			if (state == ClientState.LOGIN) {
				netIO.closeConnection();
			} else {
				netIO.closeConnection();
				closeView();
			}
		} else {
			throw new IllegalArgumentException("Argument ist null");
		}
	}

	/**
	 * Liefert eine Liste der Namen der Spieler in der Lobby oder GameLobby.
	 *
	 * @return Liste von Spielernamen oder die leere Liste.
	 */
	public final List<String> getPlayerlist() {
		return playerList;
	}

	/**
	 * Liefert eine Liste der Spiele, die aktuell auf dem Server offen sind
	 * oder gerade gespielt werden.
	 *
	 * @return Liste aller Spiele der Lobby oder null wenn leer.
	 */
	public final List<GameServerRepresentation> getLobbyGamelist() {
		return gameList;
	}

	/**
	 * Liefert den GameMaster der aktuellen Spiellobby,
	 * oder des Spieles zurueck.
	 *
	 * @return gameMaster String des Spielleiters.
	 */
	public final String getGameMaster() {
		return gameMaster;
	}

	/**
	 * Gibt den eindeutigen Namen des Spielers
	 * zurueck mit dem der Client am Server verbunden ist.
	 *
	 * @return playerName Der Spielername.
	 */
	public final String getPlayerName() {
		return playerName;
	}

	/**
	 * Setzt die Spielsprache von Warnungen und Nachrichten im Spiel.
	 *
	 * @param lang Enumerator der die Spielsprache anzeigt.
	 */
	public final void setLanguage(final Language lang) {
		if (lang != null) {
		   this.language = lang;
		   this.screenOut = new LanguageInterpreter(language);
		} else {
		   throw new IllegalArgumentException("Argument ist null");
		}
	}

	/**
	 * Liefert die Sprache der GUI.
	 *
	 * @return language Enumerator der die Spielsprache anzeigt.
	 */
	public final Language getLanguage() {
		return language;
	}

	/**
	 * Entfernt einen Spieler aus der GameLobby.
	 *
	 * @param name des Spielers, der enfernt werden soll
	 */
	public final void kickPlayer(final String name) {
		if (name == null) {
			throw new IllegalArgumentException("Argument ist null");
		}
		if (name.isEmpty()) {
			throw new IllegalArgumentException("Argument ist leer");
		}
		if (state == ClientState.GAMELOBBY) {
			if (gameMaster.equals(playerName)) {
				netIO.send(new ComKickPlayerRequest(name));
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Erstellt ein neues Spiel.
	 * Sendet dazu eine ComCreateGameRequest Nachricht
	 * an den Server.
	 *
	 * @param gameName String Name des Spieles.
	 * @param hasPassword true, wenn das Spiel ein Passwort hat
	 * @param password String Passwort zum sichern des Spieles.
	 * @param game das zu verwendende Regelwerk
	 */
	public final void hostGame(String gameName,
						 boolean hasPassword, String password,
						 RulesetType game) {
		if (state == ClientState.SERVERLOBBY) {
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
				throw new IllegalArgumentException("Argument ist null");
			} else {
				state = ClientState.ENTERGAMELOBBY;
				gameMaster = playerName;
				gameType = game;
				netIO.send(new ComCreateGameRequest(gameName, game,
						hasPassword, password));
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Sendet eine RulesetMessage an den Server. Erstellt dazu eine
	 * ComRuleset, die die RulesetMessage enthaelt.
	 *
	 * @param msg die RulesetMessage, die an den Server geschickt werden soll.
	 */
	public final void send(final RulesetMessage msg) {
		if (state == ClientState.GAME) {
			if (msg != null) {
				netIO.send(new ComRuleset(msg));
			} else {
				throw new IllegalArgumentException("Argument ist null");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Gibt den Text zurueck, der in einem Sonderfenster
	 * (InputNumber, ChooseItem, ChooseCards) angezeigt werden soll.
	 *
	 * @return String Text der Bildschirmmeldung.
	 */
	public final String getWindowText() {
		return windowText;
	}

	/**
	 * Gibt die Karten zurueck, aus denen gewaehlt werden soll.
	 *
	 * @return List<Card> Karten, aus denen gewaehlt werden kann.
	 */
	public final List<Card> getCardsToChooseFrom() {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				return ruleset.getOwnHand();
			} else {
				throw new IllegalStateException("Kein Regelwerk instanziert");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Uebergibt die Karten, die vom User gewahlt wurden. Diese
	 * werden dann dem Regelwerk weitergegeben. Akzeptiert dieses
	 * die gewaehlten Karten nicht, wird nochmal openChooseCards aufgerufen.
	 * Hearts
	 *
	 * @param cards Karten, die der User gewaehlt hat
	 */
	public final void giveChosenCards(final List<Card> cards) {
		if (state == ClientState.GAME) {
			if (cards != null) {
				if (!cards.isEmpty()) {
					if (ruleset != null) {
						if (!ruleset.areValidChoosenCards(
								new HashSet<Card>(cards))) {
							informView(ViewNotification.openChooseCards);
						}
					} else {
						throw new IllegalStateException("Kein"
								+ " Regelwerk instanziert");
					}
				} else {
					throw new IllegalArgumentException("Argument ist leer");
				}
			} else {
				throw new IllegalArgumentException("Argument ist null");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Benachrichtigt die Observer mit der openChooseCards ViewNotification.
	 * Speichert den Anzeigetext des Regelwerks zwischen.
	 *
	 * @param msg Enumerator der Spielnachricht.
	 */
	public final void openChooseCardsWindow(final UserMessages msg) {
		if (state == ClientState.GAME) {
			if (msg != null) {
				windowText = screenOut.resolveWarning(msg);
				informView(ViewNotification.openChooseCards);
			} else {
				throw new IllegalArgumentException("Argument ist null");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Uebergibt die Trumpffarbe, das vom User gewahlt wurde. Diese
	 * wird dann dem Regelwerk weitergegeben. Akzeptiert dieses
	 * die gewaehlte Farbe nicht, wird nochmal openChooseColour aufgerufen.
	 * Wizard
	 *
	 * @param colour Die Farbe, die der User gewaehlt hat
	 */
	public final void giveColourSelection(final Colour colour) {
		if (state == ClientState.GAME) {
			if (colour != null) {
				if (ruleset != null) {
					if (!ruleset.isValidColour(colour)) {
						informView(ViewNotification.openChooseItem);
					}
				} else {
					throw new IllegalStateException("Kein"
							+ " Regelwerk instanziert");
				}
			} else {
				throw new IllegalArgumentException("Argument ist null");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Benachrichtigt die Observer mit der openChooseItem ViewNotification
	 * und speichert den Anzeigtetext des Regelwerks zwischen.
	 *
	 * @param msg Der Enumerator von Spielnachrichten.
	 */
	public final void openChooseColourWindow(final UserMessages msg) {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				  windowText = screenOut.resolveWarning(msg);
			      informView(ViewNotification.openChooseItem);
			} else {
				throw new IllegalStateException("Kein Regelwerk instanziert");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Gibt vom Regelwerk eine Menge von Farben zurueck,
	 * aus denen der Benutzer eine oder mehrere waehlen kann.
	 *
	 * @return List<Colour> Eine Liste mit Farben eines Spiels.
	 */
	public final List<Colour> getColoursToChooseFrom() {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				return ruleset.getColours();
			} else {
				throw new IllegalStateException("Kein Regelwerk instanziert");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Uebergibt die Zahl, die vom User gewahlt wurde. Diese
	 * wird dann dem Regelwerk weitergegeben. Akzeptiert dieses
	 * die gewaehlte Zahl nicht, wird nochmal openInputNumber
	 * aufgerufen.
	 * Wizard
	 *
	 * @param number Zahl, die vom User gewahlt wurde
	 */
	public final void giveInputNumber(final int number) {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				if (!ruleset.isValidTrickNumber(number)) {
					informView(ViewNotification.openInputNumber);
				}
			} else {
				throw new IllegalStateException("Kein Regelwerk instanziert");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Benachrichtigt die Observer mit der openInputNumber ViewNotification
	 * und speichert den Anzeigetext des Regelwerks zwischen.
	 * Wizard
	 *
	 * @param msg Enumerator fuer den Fenstertext.
	 */
	public final void openNumberInputWindow(final UserMessages msg) {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				  windowText = screenOut.resolveWarning(msg);
			      informView(ViewNotification.openInputNumber);
		   } else {
				throw new IllegalStateException("Kein Regelwerk instanziert");
			}
     	} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Informiert die Observer, dass sich die Trumpffarbe
	 * des aktuellen Spieles geaendert hat.
	 *
	 * @param msg Enumerator fuer den Fenstertext
	 */
	public final void updateTrumpColour(final UserMessages msg) {
		if (state == ClientState.GAME) {
			if (msg != null) {
				windowText = screenOut.resolveWarning(msg);
				informView(ViewNotification.trumpUpdate);
			} else {
				throw new IllegalArgumentException("Argument ist null");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Gibt die aktuelle Trumpffarbe zurück.
	 *
	 * @return Colour Die aktuelle Trumpffarbe.
	 */
	public final Colour getTrumpColour() {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				return ruleset.getTrumpColour();
			} else {
				throw new IllegalStateException("Kein Regelwerk instanziert");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Informiert die Observer um dem Spieler seinen
	 * Zug anzuzeigen.
	 *
	 * @param msg Enum fuer den Fenstertext.
	 */
	public final void announceTurn(final UserMessages msg) {
		if (state == ClientState.GAME) {
			if (msg != null) {
				windowText = screenOut.resolveWarning(msg);
				informView(ViewNotification.turnUpdate);
			} else {
				throw new IllegalArgumentException("Argmuent ist null");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Nimmt vom ClientController eine Chatnachricht entgegen
	 * und sendet diese an den Server.
	 *
	 * @param msg die Chatnachricht, die an den Server geschickt werden soll
	 */
	public final void sendChatMessage(final String msg) {
		if (msg != null) {
			if (!msg.isEmpty()) {
				netIO.send(new ComChatMessage(msg));
			} else {
				throw new IllegalArgumentException("Leerer String");
			}
		} else {
			throw new IllegalArgumentException("Argument ist null");
		}
	}

	/**
	 * Versucht einem Spiel beizutreten. Sendet dazu eine ComJoinRequest
	 * Nachricht an den Server. Wird diese bestaetigt, gelangt der Client
	 * in die GameLobby. Wird die Nachricht nicht bestaetigt, wird eine
	 * Fehlermeldung ausgegeben und die Observer
	 * mit openWarning informiert.
	 *
	 * @param name String Der Name des Spielleiters.
	 * @param password String Passwort eines Spieles.
	 */
	public final void joinGame(final String name, String password) {
		if (state == ClientState.SERVERLOBBY) {
			if (password == null) {
				password = new String();
			}
			if (name == null) {
				throw new IllegalArgumentException("Argument ist null");
			} else if (name.isEmpty()) {
				throw new IllegalArgumentException("Argument ist leer");
			} else {
				synchronized (gameList) {
					for (GameServerRepresentation game : gameList) {
						if (name.equals(game.getGameMasterName())) {
							state = ClientState.ENTERGAMELOBBY;
							gameMaster = name;
							gameType = game.getRuleset();
							netIO.send(new ComJoinRequest(name, password));
							break;
						}
					}
				}
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Versucht das erstellte Spiel zu starten. Sendet dazu eine ComStartGame
	 * an den Server. Wenn der Client der Spielleiter des Spiels ist, gelangt
	 * er ins Spiel. Wenn der Client nicht der Spielleiter des Spiels ist,
	 * wird eine Fehlermeldung ausgegeben.
	 */
	public final void startGame() {
		if (state == ClientState.GAMELOBBY) {
	      if (gameMaster != null) {
	         if (!gameMaster.isEmpty()) {
			   if (gameMaster.equals(playerName)) {
			      int playerCount = playerList.size();
				  if (playerCount >= gameType.getMinPlayer()
						  && playerCount <= gameType.getMaxPlayer()) {
					  netIO.send(new ComStartGame());
				  }
			   }
	         } else {
				throw new IllegalStateException("Spielleiter ist leer");
	         }
		  } else {
				throw new IllegalStateException("Spielleiter ist null");
		  }
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Versucht eine Karte auszuspielen. Laesst dazu vom ClientRuleset
	 * ueberpruefen ob, die ausgewaehlte Karte gespielt werden darf.
	 * Wenn ja, wird sie im ClientRuleset weiterbehandelt. Wenn nein,
	 * wird eine Fehlermeldung ausgegeben und dazu die Observer mit
	 * openWarning informiert.
	 *
	 * @param card Die gespielte Karte.
	 */
	public final void makeMove(final Card card) {
		if (state == ClientState.GAME) {
			if (card != null) {
				if (ruleset != null) {
					ruleset.isValidMove(card);
				} else {
					throw new IllegalStateException("Kein"
							+ " Regelwerk instanziert");
				}
			} else {
				throw new IllegalArgumentException("Argument ist null");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Informiert die Observer,
	 * dass ein neues Spielupdate vorhanden ist.
	 *
	 */
	public final void updateGame() {
		if (state == ClientState.GAME) {
			informView(ViewNotification.gameUpdate);
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Gibt das Update fuer den Spielzustand zurueck.
	 *
	 * @return GameClientUpdate Die Daten der laufenden Sitzung.
	 */
	public final GameClientUpdate getGameUpdate() {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				return ruleset.getGameState();
			} else {
				throw new IllegalStateException("Kein Regelwerk instanziert");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Informiert die Oberserver, dass eine neue
	 * Warnung ausgegeben werden muss.
	 *
	 * @param msg Enum der Warnungen.
	 */
	public final void openWarning(final WarningMsg msg) {
		if (msg != null) {
			warningText.append(screenOut.resolveWarning(msg));
			informView(ViewNotification.openWarning);
		} else {
			throw new IllegalArgumentException("Argument ist null");
		}
	}

	/**
	 * Informiert die Oberserver, dass der aktuelle
	 * Spielstand ausgegeben werden muss.
	 *
	 */
	public final void showScoreWindow() {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				informView(ViewNotification.showScore);
			} else {
				throw new IllegalStateException("Kein Regelwerk instanziert");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Wird aufgerufen wenn das Ende einer Partie erreicht ist.
	 *
	 * @param msg Enum der Spielnachrichten.
	 */
	public final void announceWinner(final UserMessages msg) {
		if (state == ClientState.GAME) {
			state = ClientState.ENDING;
			if (msg != null) {
				windowText = screenOut.resolveWarning(msg);
				informView(ViewNotification.showScore);
			} else {
				throw new IllegalArgumentException("Argument ist null");
			}
		} else {
			throw new IllegalStateException("Falscher Zustand des Clients");
		}
	}

	/**
	 * Liefert den Gewinner einer Partie.
	 *
	 * @return List<String> der/die Gewinner.
	 */
	public final List<String> getWinner() {
		if (state == ClientState.ENDING) {
			return ruleset.getWinners();
		}
		return new LinkedList<String>();
	}

	/**
	 * Hilfsmethode die alle verbundenen Observer der GUI kontaktiert.
	 *
	 * @param note Enum der die Art des Aufrufes bestimmt.
	 */
	private void informView(final ViewNotification note) {
		setChanged();
		notifyObservers(note);
	}

	/**
	 * Ueberprueft die Daten die zum Serverlogin noetig sind.
	 *
	 * @param username String der eindeutige Benutzername,
	 * der für den Login verwendet wird.
	 * @param host String die Adresse des spielservers.
	 */
	public void createConnection(String username,
								 String host) {
		state = ClientState.LOGIN;
		URI uri = null;
		int port = PORT;
		boolean fault = false;
		if (username == null) {
			throw new IllegalArgumentException();
		}
		if (host == null) {
			throw new IllegalArgumentException();
		}
		if (username.isEmpty()) {
			fault = true;
			warningText.append(screenOut.resolveWarning(
					WarningMsg.EmptyUsername));
		}
		if (host.isEmpty()) {
			fault = true;
			warningText.append(screenOut.resolveWarning(
					WarningMsg.EmptyAddress));
		}
		if (!fault) {
			try {
				uri = new URI("http://" + host);
				port = uri.getPort();
				if (uri.getHost() == null) {
					fault = true;
					warningText.append(screenOut.resolveWarning(
							WarningMsg.WrongAddress));
				}
				if (port == -1) {
					port = PORT;
				} else if (port < MINPORT
							|| port > MAXPORT) {
					fault = true;
					warningText.append(screenOut.resolveWarning(
							WarningMsg.Portnumber));
				}
			} catch (URISyntaxException e) {
				fault = true;
				warningText.append(screenOut.resolveWarning(
						WarningMsg.WrongAddress));
			}
		}
		if (!fault) {
			playerName = username;
			setupConnection(username, uri.getHost(), port);
		} else {
			informView(ViewNotification.openWarning);
		}
	}

	/**
	 * Baut eine Verbindung zum Server auf.
	 *
	 * @param username Benutzername
	 * @param host Serveraddresse
	 * @param port Anschluss
	 */
	private void setupConnection(final String username,
			final String host, final int port) {
		try {
			netIO.startConnection(this, host, port);
			netIOThread = new Thread(netIO);
			netIOThread.start();
			netIO.send(new ComLoginRequest(username));
		} catch (ConnectException e) {
			warningText.append(screenOut.resolveWarning(
					WarningMsg.UnknownHost));
			informView(ViewNotification.openWarning);
		} catch (SocketException e) {
			System.err.println("ERROR: Network IO");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			warningText.append(screenOut.resolveWarning(
					WarningMsg.UnknownHost));
			informView(ViewNotification.openWarning);
		} catch (IOException e) {
			System.err.println("ERROR: Network IO");
			e.printStackTrace();
		}
	}

	/**
	 * Gibt den Text aus der bei einer Spielwarnung
	 * angezeigt wird.
	 *
	 * @return String Text der Warnung.
	 */
	public final String getWarningText() {
		String text = warningText.toString();
		warningText.delete(0, warningText.length());
		return text;
	}

	/**
	 * Bereitet eine Liste aller verfügbaren Spieltypen vor.
	 */
	private void prepRulesetList() {
		supportetGames = new LinkedList<RulesetType>();
		supportetGames.add(RulesetType.Wizard);
		supportetGames.add(RulesetType.Hearts);
	}

	/**
	 * Liefert eine Liste mit allen implementierten Regelwerken.
	 *
	 * @return List<RulesetType> Liste von unterstuetzten Regelwerken.
	 */
	public final List<RulesetType> getRulesets() {
		return supportetGames;
	}
}