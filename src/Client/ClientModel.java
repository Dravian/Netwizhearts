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
import ComObjects.ComObject;
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
import java.util.HashSet;
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
	
	private String gameMaster;
	
	/**
	 * Referenz auf das Regelwerk des Spieles.
	 */
	private ClientRuleset ruleset;
	
	/**
	 * 
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

	private List<String> playerList;

	private List<GameServerRepresentation> gameList;

	private StringBuffer warningText;

	private Thread netIOThread;
	
	private LanguageInterpreter screenOut;
	
	private String windowText;
	
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
		if (netIO == null) {
			throw new IllegalArgumentException();
		}
		this.netIO = netIO;
		state = ClientState.LOGIN;
		this.language = Language.English;
		screenOut = new LanguageInterpreter(language);
		playerName = new String();
		warningText = new StringBuffer();
		playerList = new LinkedList<String>();
		gameList = new LinkedList<GameServerRepresentation>();
		ruleset = null;
		prepRulesetList();
	}

	/**
	 * Wird aufgerufen, wenn der User in die ServerLobby zurückkehren möchte.
	 * 
	 */
	public void returnToLobby() {
		if (state != ClientState.LOGIN &&
				state != ClientState.SERVERLOBBY) {
			netIO.send(new ComClientLeave());
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Wird aufgerufen um die View zu beenden, wenn 
	 * der Server die Verbindung beendet,
	 * oder ein Netzwerkfehler auftritt.
	 */
	protected void closeView() {
		warningText.append(screenOut.resolveWarning(WarningMsg.ConnectionLost));
		informView(ViewNotification.openWarning);
		informView(ViewNotification.quitGame);
	}

	/**
	 * Wird aufgerufen, wenn der Spieler das Programm beendet.
	 * Leitet den Verbindungsabbau zum Server ein.
	 */
	public void closeProgram() {
		if (state == ClientState.SERVERLOBBY ||
				state == ClientState.ENTERGAMELOBBY) {
			netIO.send(new ComClientQuit());
			netIO.closeConnection();
			netIO = null;
			warningText = null;
			playerList = null;
			gameList = null;
		} else {
			throw new IllegalStateException();
		}	
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
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Diese Methode wird aufgerufen,
	 * falls der Server den Spieler erfolgreich in die Lobby hinzugefügt hat.
	 * Empfaengt die ComInitGameLobby Nachricht, die eine Liste aller
	 * Spieler enthaelt, die sich in der Lobby befinden.
	 *
	 * @param msg die ankommende ComInitLobby Nachricht
	 */
	public void receiveMessage(ComInitLobby msg) {	
		if (msg != null) {
			state = ClientState.SERVERLOBBY;
			gameMaster = new String();
			ruleset = null;
			if (msg.getPlayerList() != null) {
				playerList = msg.getPlayerList();
				if (playerList.isEmpty()) {
					throw new IllegalArgumentException();
				}
			}
			if (msg.getGameList() != null) {
				gameList = new LinkedList<GameServerRepresentation>(msg.getGameList());
			} else {
				throw new IllegalArgumentException();
			}
			informView(ViewNotification.windowChangeForced);
		} else {
			throw new IllegalArgumentException();
		}
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
		if (state == ClientState.ENTERGAMELOBBY) {
			if (msg != null) {
				state = ClientState.GAMELOBBY;
				if (msg.getPlayerList() != null) {
					playerList = msg.getPlayerList();
					if (!playerList.isEmpty()) {
						informView(ViewNotification.joinGameSuccessful);
					} else {
						throw new IllegalArgumentException();
					}
				} else {
					throw new IllegalArgumentException();
				}
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
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
	public void receiveMessage(ComRuleset msg) {
		if (state == ClientState.GAME) {
		   if (msg != null) {
			   if (msg.getRulesetMessage() != null) {
				   msg.getRulesetMessage().visit(ruleset);
			   } else {
					throw new IllegalArgumentException();
				}
		   } else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
		}
	}

	public void receiveMessage(ComWarning warning) {
		if (warning != null) {
			if (state == ClientState.LOGIN) {
				playerName = new String();
				netIO.closeConnection();
				netIOThread = null;
				warningText.append(screenOut.resolveWarning(WarningMsg.LoginError));
				informView(ViewNotification.openWarning);
			} else if (state == ClientState.ENTERGAMELOBBY) {
				state = ClientState.SERVERLOBBY;
				gameMaster = new String();
				gameType = null;
				warningText.append(screenOut.resolveWarning(warning.getWarning()));
				informView(ViewNotification.openWarning);
			} else {
				warningText.append(screenOut.resolveWarning(warning.getWarning()));
				informView(ViewNotification.openWarning);
			}
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 
	 */
	public void receiveMessage(ComStartGame msg) {
		if (state == ClientState.GAMELOBBY) {
			if (msg != null) {
				switch (gameType) {
					case Hearts: ruleset = new ClientHearts(this);
						 state = ClientState.GAME;
						 informView(ViewNotification.gameStarted);
						break;
					case Wizard: ruleset = new ClientWizard(this);
					 	 state = ClientState.GAME;
						 informView(ViewNotification.gameStarted);
						break;
					default: throw new IllegalStateException();
				}
			} else {
				throw new IllegalArgumentException();
			} 
		} else {
			throw new IllegalStateException();
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
	public void receiveMessage(ComUpdatePlayerlist update) {
		if (state == ClientState.SERVERLOBBY ||
				state == ClientState.GAMELOBBY) {
			if (update != null) {
				if (update.getPlayerName() != null) {
					if (update.isRemoveFlag()) {
						if (!playerList.remove(update.getPlayerName())) {
							throw new IllegalArgumentException();
						}
					} else {
						if (playerList.remove(update.getPlayerName())) {
							throw new IllegalArgumentException();
						}
						playerList.add(update.getPlayerName());
					}
					informView(ViewNotification.playerListUpdate);
				} else {
					throw new IllegalArgumentException();
				}
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
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
		if (state == ClientState.SERVERLOBBY ||
				state == ClientState.ENTERGAMELOBBY) {
			if (update != null) {
				GameServerRepresentation gameUpdate = update.getGameServer();
				String key;
				if (gameUpdate != null) {
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
					informView(ViewNotification.gameListUpdate);
				} else {
					throw new IllegalArgumentException();
				}
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
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
		} else {
			throw new IllegalArgumentException();
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
	
	public String getGameMaster() {
		return gameMaster;
	}

	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Setzt die Sprache der GUI.
	 *
	 * @param language Enumerator der die Spielsprache anzeigt.
	 */
	public void setLanguage(Language language) {
		if (language != null) {
		   this.language = language;
		   this.screenOut = new LanguageInterpreter(language);
		} else {
		   throw new IllegalArgumentException();
		}
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
	public void kickPlayer(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		if (name.isEmpty()) {
			throw new IllegalArgumentException();
		}
		if (state == ClientState.GAMELOBBY) {
			if(gameMaster.equals(playerName)) {
				netIO.send(new ComKickPlayerRequest(name));
			} else {
				throw new IllegalStateException();
			}
		} else {
			throw new IllegalStateException();
		}
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
				throw new IllegalArgumentException();
			} else {
				state = ClientState.ENTERGAMELOBBY;
				gameMaster = playerName;
				gameType = game;
				netIO.send(new ComCreateGameRequest(gameName, game, hasPassword, password));
			}
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Sendet eine RulesetMessage an den Server. Erstellt dazu eine
	 * ComRuleset, die die RulesetMessage enthaelt.
	 *
	 * @param msg die RulesetMessage, die an den Server geschickt werden soll
	 */
	public void send(RulesetMessage msg) {
		if (state == ClientState.GAME) {
			if (msg != null) {
				netIO.send(new ComRuleset(msg));
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Gibt den Text zurueck, der in einem Sonderfenster 
	 * (InputNumber, ChooseItem, ChooseCards) angezeigt werden soll.
	 *
	 * @return String Text der Bildschirmmeldung.
	 */
	public String getWindowText() {
		return windowText;
	}

	/**
	 * Gibt die Karten zurueck, aus denen gewaehlt werden soll.
	 * 
	 * @return List<Card> Karten, aus denen gewaehlt werden kann
	 */
	public List<Card> getCardsToChooseFrom() {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				if(ruleset.getClass().equals(ClientHearts.class)) {
					return ((ClientHearts) ruleset).getOwnHand();
				}
			} else {
				throw new IllegalStateException();
			}
		} else {
			throw new IllegalStateException();
		}
		return new LinkedList<Card>();
	}

	/**
	 * Uebergibt die Karten, die vom User gewahlt wurden. Diese
	 * werden dann dem Regelwerk weitergegeben. Akzeptiert dieses
	 * die gewaehlten Karten nicht, wird nochmal openChooseCards aufgerufen.
	 * Hearts
	 *
	 * @param cards Karten, die der User gewaehlt hat
	 */
	public void giveChosenCards(List<Card> cards) {
		if (state == ClientState.GAME) {
			if (!cards.isEmpty()) {
				if (ruleset != null) {
					if(ruleset.getClass().equals(ClientHearts.class)) {
						if (!((ClientHearts) ruleset).areValidChoosenCards(new HashSet<Card>(cards))) {
							informView(ViewNotification.openChooseCards);
						}
					} else {
						throw new IllegalStateException();
					}
				} else {
					throw new IllegalStateException();
				}
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Benachrichtigt die Observer mit der openChooseCards ViewNotification
	 * und speichert die Liste der Karten sowie den Anzeigetext des Regelwerks
	 * zwischen.
	 * Hearts
	 *
	 * @param cards Liste der Karten, von denen gewaehlt werden kann
	 * @param text Text, der dem User angezeigt werden soll
	 */
	public void openChooseCardsWindow(UserMessages msg) {
		if (state == ClientState.GAME) {
			if (msg != null) {
				windowText = screenOut.resolveWarning(msg);
				informView(ViewNotification.openChooseCards);
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Uebergibt die Trumpffarbe, das vom User gewahlt wurde. Diese
	 * wird dann dem Regelwerk weitergegeben. Akzeptiert dieses
	 * die gewaehlte Farbe nicht, wird nochmal openChooseColour aufgerufen.
	 * Wizard
	 * 
	 * @param colour Die Farbe, die der User gewahlt hat
	 */
	public void giveColourSelection(Colour colour) {
		if (state == ClientState.GAME) {
			if (colour != null) {
				if (ruleset != null) {
					if (ruleset.getClass().equals(ClientWizard.class)) {
						if(!((ClientWizard) ruleset).isValidColour(colour)) {
							informView(ViewNotification.openChooseItem);
						}
					} else {
						throw new IllegalStateException();
					} 
				} else {
					throw new IllegalArgumentException();
				}
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Benachrichtigt die Observer mit der openChooseItem ViewNotification
	 * und speichert die Liste der Farben, von denen eine gewaehlt werden soll,
	 * sowie den Anzeigtetext des Regelwerks zwischen.
	 *
	 * @param items Liste der Items, von denen eines gewaehlt werden soll
	 * @param text Text, der dem User angezeigt werden soll
	 */
	public void openChooseColourWindow(UserMessages msg) {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
			   if (ruleset.getClass().equals(ClientWizard.class)) {
				  windowText = screenOut.resolveWarning(msg);
			      informView(ViewNotification.openChooseItem);
			   } else {
					throw new IllegalStateException();
				}
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
		}
	}
	
	public List<Colour> getColoursToChooseFrom() {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				if (ruleset.getClass().equals(ClientWizard.class)) {
					return ((ClientWizard) ruleset).getColours();
				} else {
					throw new IllegalStateException();
				}
			} else {
				throw new IllegalStateException();
			}
		} else {
			throw new IllegalStateException();
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
	public void giveInputNumber(int number) {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				if (ruleset.getClass().equals(ClientWizard.class)) {
					if (!((ClientWizard) ruleset).isValidTrickNumber(number)) {
						informView(ViewNotification.openInputNumber);
					}
				}
			} else {
				throw new IllegalStateException();
			}
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Benachrichtigt die Observer mit der openInputNumber ViewNotification
	 * und speichert den Anzeigetext des Regelwerks zwischen.
	 * Wizard
	 *
	 * @param text Text, der dem User angezeigt werden soll
	 */
	public void openNumberInputWindow(UserMessages msg) {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
			   if (ruleset.getClass().equals(ClientWizard.class)) {
				  windowText = screenOut.resolveWarning(msg);
			      informView(ViewNotification.openInputNumber);
			   }
		   } else {
				throw new IllegalStateException();
			} 
     	} else {
			throw new IllegalStateException();
		}
	}
	
	public void updateTrumpColour(UserMessages msg) {
		if (state == ClientState.GAME) {
			if (msg != null) {
				windowText = screenOut.resolveWarning(msg);
				informView(ViewNotification.trumpUpdate);
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
		}
	}
	
	/**
	 * Gibt die aktuelle Trumpffarbe zurück.
	 * 
	 * @return Colour Die aktuelle Trumpffarbe.
	 */
	public Colour getTrumpColour() {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				if (ruleset.getClass().equals(ClientWizard.class)) {
					return ((ClientWizard) ruleset).getTrumpColour();
				}
			} else {
				throw new IllegalStateException();
			}
		} else {
			throw new IllegalStateException();
		}
		return null;
	}
	
	public void announceTurn(UserMessages msg) {
		if (state == ClientState.GAME) {
			if (msg != null) {
				windowText = screenOut.resolveWarning(msg);
				informView(ViewNotification.turnUpdate);
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
		}
	}
	
	public int getTurn() {
		if (state == ClientState.GAME) {
			return ruleset.getRoundNumber();
		} else {
			throw new IllegalStateException();
		}
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
				netIO.send(new ComChatMessage(msg));
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Versucht einem Spiel beizutreten. Sendet dazu eine ComJoinRequest Nachricht an
	 * den Server. Wird diese bestaetigt, gelangt der Client in die GameLobby. Wird die
	 * Nachricht nicht bestaetigt, wird eine Fehlermeldung ausgegeben und die Observer
	 * mit openWarning informiert.
	 *
	 * @param name String Der Name des Spielleiters.
	 * @param password String Passwort eines Spieles.
	 * @throws IllegalArgumentException Wird geworfen, wenn ein leerer
	 * oder null Wert in name uebergeben wird.
	 */
	public void joinGame(String name, String password) throws IllegalArgumentException {
		if (state == ClientState.SERVERLOBBY) {
			if (password == null) {
				password = new String();
			}
			if (name == null) {
				throw new IllegalArgumentException();
			} else if (name.isEmpty()) {
				throw new IllegalArgumentException();
			} else {
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
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Versucht das erstellte Spiel zu starten. Sendet dazu eine ComStartGame an den Server.
	 * Wenn der Client der Spielleiter des Spiels ist, gelangt er ins Spiel.
	 * Wenn der Client nicht der Spielleiter des Spiels ist, wird eine Fehlermeldung ausgegeben.
	 */
	public void startGame() {
		if (state == ClientState.GAMELOBBY) {
	      if (gameMaster != null) {
	         if (!gameMaster.isEmpty()) {
			   if (gameMaster.equals(playerName)) {
			      int playerCount = playerList.size();
				  if (playerCount >= gameType.getMinPlayer() &&
					     playerCount <= gameType.getMaxPlayer()) {
					  netIO.send(new ComStartGame());
				  } else {
					  /*
					  warningText.append("Warnung noch einbauen!");
					  informView(ViewNotification.openWarning); */
				  }
			  } else {
					throw new IllegalStateException();
				}

	        } else {
				throw new IllegalStateException();
			}

		  } else {
				throw new IllegalStateException();
			}

	   } else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Versucht eine Karte auszuspielen. Laesst dazu vom ClientRuleset ueberpruefen ob, die ausgewaehlte
	 * Karte gespielt werden darf. Wenn ja, wird sie im ClientRuleset weiterbehandelt. Wenn nein,
	 * wird eine Fehlermeldung ausgegeben und dazu die Observer mit openWarning informiert.
	 * 
	 * @param card Die gespielte Karte.
	 */
	public void makeMove(Card card) {
		if (state == ClientState.GAME) {
			if (card != null) {
				if (ruleset != null) {
					if (ruleset.getClass().equals(ClientWizard.class)) {
						((ClientWizard) ruleset).isValidMove(card);
					} else if (ruleset.getClass().equals(ClientHearts.class)) {
						((ClientHearts) ruleset).isValidMove(card);
					}
				} else {
					throw new IllegalStateException();
				}
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
		} 
	}
	
	public void updateGame() {
		if (state == ClientState.GAME) {
			informView(ViewNotification.gameUpdate);
		} else {
			throw new IllegalStateException();
		}
	}
	
	public GameClientUpdate getGameUpdate() {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				return ruleset.getGameState();
			} else {
				throw new IllegalStateException();
			}
		} else {
			throw new IllegalStateException();
		}
	}
	
	public void openWarning(WarningMsg msg) {
		if (state == ClientState.GAME) {
			if (msg != null) {
				warningText.append(screenOut.resolveWarning(msg));
				informView(ViewNotification.openWarning);
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
		}
	}
	
	/**
	 * Wird zwischen den Runden aufgerufen ?
	 */
	public void showScoreWindow() {
		if (state == ClientState.GAME) {
			if (ruleset != null) {
				informView(ViewNotification.showScore);
			} else {
				throw new IllegalStateException();
			}
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Wird aufgerufen wenn das Ende einer Partie erreicht ist.
	 * 
	 * @param winner String der Gewinner der Partie.
	 */
	public void announceWinner(UserMessages msg) {
		if (state == ClientState.GAME) {
			state = ClientState.ENDING;
			if (msg != null) {
				windowText = screenOut.resolveWarning(msg);
				informView(ViewNotification.showScore);
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Liefert den Gewinner einer Partie.
	 *
	 * @return List<String> der/die Gewinner.
	 */
	public List<String> getWinner() {
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
								 String host)
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
			warningText.append(screenOut.resolveWarning(WarningMsg.EmptyUsername));
		}
		if (host.isEmpty()) {
			fault = true;
			warningText.append(screenOut.resolveWarning(WarningMsg.EmptyAddress));
		} 
		if (!fault) {
			try {
				uri = new URI("http://" + host);
				port = uri.getPort();
				if (uri.getHost() == null) {
					fault = true;
					warningText.append(screenOut.resolveWarning(WarningMsg.WrongAddress));
				}
				if (port == -1) {
					//TODO standard port.
					port = 4567;
				} else if (port < 1025 || port > 49151) {
					fault = true;
					warningText.append(screenOut.resolveWarning(WarningMsg.Portnumber));
				}
			} catch (URISyntaxException e) {
				fault = true;
				warningText.append(screenOut.resolveWarning(WarningMsg.WrongAddress));
			}
		}
		if (!fault) {
			playerName = username;
			setupConnection(username, uri.getHost(), port);
		} else {
			informView(ViewNotification.openWarning);
		}
	}

	private void setupConnection(String username, String host, int port ) {
		try {
			netIO.startConnection(this, host, port);
			netIOThread = new Thread(netIO);
			netIOThread.start();
			netIO.send(new ComLoginRequest(username));
		} catch (ConnectException e) {
			warningText.append(screenOut.resolveWarning(WarningMsg.UnknownHost));
			informView(ViewNotification.openWarning);
		} catch (SocketException e) {
			System.err.println("ERROR: Network IO");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			warningText.append(screenOut.resolveWarning(WarningMsg.UnknownHost));
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
		return supportetGames;
	}
}