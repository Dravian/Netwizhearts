/**
 * 
 */
package Ruleset;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import Server.GameServer;
import ComObjects.MsgCard;
import ComObjects.MsgCardRequest;
import ComObjects.MsgGameEnd;
import ComObjects.MsgMultiCards;
import ComObjects.MsgMultiCardsRequest;
import ComObjects.MsgNumber;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelection;
import ComObjects.MsgSelectionRequest;
import ComObjects.MsgUser;
import ComObjects.RulesetMessage;
/** 
 * ServerRuleset. Das ServerRuleset ist eine akstrakte Klasse und fuer den Ablauf und die Einhaltung der Regeln eines Spiels zustaendig. 
 * Das ServerRuleset wird im GameServer instanziert und verwaltet die Zustaende des GameStates im Server. 
 * Mit der Methode isValidMove() wird eine Eingabe eines Clients auf Regelkonformität ueberprueft und dann
 * das GameState veraendert. Ueber resolveMessage() kann eine GameServerinstanz eine RulesetMessage 
 * vom Player an das Ruleset weiterleiten.
 */
public abstract class ServerRuleset {
	/**
	 * Der GameServer auf den gespielt wird
	 */
	private GameServer server;
	
	/** 
	 * Den momentane Spielzustand
	 */
	private GameState gameState;
	
	/** 
	 * Die momentane Spielphase
	 */
	private GamePhase gamePhase;
	
	/**
	 * Der Spieltyp
	 */
	private final RulesetType RULESET;
	
	/**
	 * Die Mindestanzahl an Spielern
	 */
	private final int MIN_PLAYERS;
	
	/**
	 * Die Maximale Anzahl an Spielern
	 */
	private final int MAX_PLAYERS;
	
	/**
	 * Erstellt ein ServerRuleset und erzeugt ein GameState
	 * @param ruleset Der Rulesettyp vom Server
	 * @param min Die minimale Anzahl an Spielern
	 * @param max Die maximale Anzahl an Spielern
	 * @param server Der Server auf dem gespielt wird
	 */
	public ServerRuleset(RulesetType ruleset, int min, int max, 
			GameServer server) {
		RULESET = ruleset;
		MIN_PLAYERS = min;
		MAX_PLAYERS = max;
		List<Card> deck = createDeck();
		gameState = new GameState(ruleset,deck);
		gamePhase = GamePhase.Start;
		this.server = server;
	}
	
	/**
	 * Holt die aktuelle Rundenanzahl zurueck
	 * @return Die aktuelle Rundenanzahl
	 */
	protected int getRoundNumber() {
		return gameState.getRoundNumber();
	}
	
	/**
	 * Holt den aktuellen Spielzustand
	 * @return Der Spielzustand
	 */
	protected GameState getGameState() {
		return gameState;
	}
	/**
	 * Gibt den Typ des Regelwerks zurueck
	 * @return Der Typ vom Regelwerk
	 */
	public RulesetType getRulesetType() {
		return RULESET;
	}
	
	/**
	 * Gibt die Mindestanzahl an Spielern zurueck für dieses Spiel
	 * @return Die Mindestanzahl an Spielern
	 */
	public int getMinPlayers() {
		return MIN_PLAYERS;
	}
	
	/**
	 * Gibt die Maximale Anzahl an Spielern zurueck
	 * @return Die maximale Anzahl an Spielern
	 */
	public int getMaxPlayers() {
		return MAX_PLAYERS;
	}
	
	/**
	 * Aendert den momentanen Spielphase
	 * @param phase Die neue Spielphase
	 */
	protected void setGamePhase(GamePhase phase) {
		gamePhase = phase;
	}

	/**
	 * Gibt den momentanen Spielzustand zurueck
	 * @return Gibt die momentan Spielphase zurueck
	 */
	public GamePhase getGamePhase() {
		return gamePhase;
	}
	
	/**
	 * Erzeugt ein Kartendeck, abhängig von dem RulesetType
	 * @return Gibt ein Kartendeck zurueck
	 */
	private List<Card> createDeck() {
		List<Card> deck = new LinkedList<Card>();
		switch(RULESET) {
		case Wizard: 
			for(WizardCard wiz : WizardCard.values()) {
				deck.add(wiz);
			}
			break;
		case Hearts:
			for(HeartsCard hearts : HeartsCard.values()) {
				deck.add(hearts);
			}
			break;
		default:
		}
		return deck;
	}
	
	protected List<PlayerState> getPlayers() {
		return gameState.getPlayers();
	}

	/**
	 * Startet das Spiel
	 * @throws IllegalNumberOfPlayersException Wird geworfen wenn die Spieleranzahl nicht stimmt
	 */
	public abstract void runGame() throws IllegalNumberOfPlayersException;
	
	/** 
	 * Setzt den Spieler der als Erster am Zug ist, im Gamestate
	 * @param Der Spielerzustand des Spielers
	 */
	protected void setFirstPlayer(PlayerState player) {
		gameState.setFirstPlayer(player);
	}
	
	/**
	 * Holt den Spieler der als erster am Zug war
	 * @return firstPlayer Der Spielzustand des Spielers der als erster am Zug war
	 */
	protected PlayerState getFirstPlayer() {
		PlayerState firstPlayer = gameState.getFirstPlayer();
		return firstPlayer;
	}
	
	/**
	 * Setzt den nächsten Spieler
	 */
	protected void nextPlayer() {
		int iterator = getPlayers().indexOf(getCurrentPlayer());
		
		if(iterator == getPlayers().size()-1) {
			iterator = 0;
		} else {
			iterator++;
		}
		
		gameState.setCurrentPlayer(getPlayers().get(iterator));
	}

	/** 
	 * Setzt den Spieler der am Naechsten am Zug ist, im Gamestate
	 * @param player Der Playerstate eines Spielers
	 */
	protected void setCurrentPlayer(PlayerState player) {
		 gameState.setCurrentPlayer(player);
	}
	
	/**
	 * Die OtherData eines Spielers
	 * @param player Der Spielerzustand
	 * @return Gibt OtherData zurück
	 */
	protected OtherData getOtherData(PlayerState player) {
		return player.getOtherData();
	}
	/**
	 * Holt den Spieler der gerade am Zug ist
	 * @return currentPlayer Der Spielzustand des Spielers der grad am Zug ist
	 */
	protected PlayerState getCurrentPlayer() {
		PlayerState currentPlayer = gameState.getCurrentPlayer();
		return currentPlayer;
	}
	
	
	/**
	 * Fuegt einen Spieler ins Spiel ein
	 * @param name Der name vom Spieler
	 */
	protected void addPlayerToGame(String name) {
		gameState.addPlayerToGame(name);
	}

	/** 
	 * Holt den Spielerzustand eines Spielers
	 * @param name Der Name des Spielers
	 * @return playerState Spielzustand eines Spielers
	 */
	protected PlayerState getPlayerState(String name) {
		return gameState.getPlayerState(name);
	}
	
	/**
	 * Holt die Spielkarten eines Spielers
	 * @param name Der Name eines Spielers
	 * @return Die Spielkarten des Spielers
	 */
	protected List<Card> getPlayerCards(PlayerState player) {
		return player.getHand();
	}
	
	/**
	 * Holt die gespielten Karten auf den Ablagestapel
	 * @return Die Karten auf dem AblageStapel
	 */
	protected List<DiscardedCard> getPlayedCards() {
		return gameState.getPlayedCards();
	}
	/**
	 * Schickt eine Nachricht an einen Spieler, über den Gameserver
	 * @param message Die Nachricht vom Typ RulesetMessage
	 * @param name Der Name vom Spieler
	 */
	protected void send(RulesetMessage message, String name) {
		server.sendRulesetMessage(name,message);
	}
	
	
	/**
	 * Schickt eine Nachricht an alle Spieler
	 * @param message Die Nachricht
	 */
	protected void broadcast(RulesetMessage message) {
		server.broadcastRulesetMessage(message);
	}
	
	/** 
	 * Verarbeitet die RulesetMessage dass eine Karte vom Spieler gespielt.
	 * Die wird dann in isValidMove überprüft, bei falsche Eingabe wird´
	 * eine MsgCardRequest an den selben Spieler geschickt. 
	 * Bei richtiger Eingabe geht das Spiel weiter.
	 * @param msgCard Die Nachricht vom Client welche Karte gespielt wurde
	 * @param name Der Name des Spielers
	 */
	public void resolveMessage(MsgCard msgCard, String name) {
		
	}
	
	/**
	 * Bekommt eine MsgMultiCards Nachricht
	 * @param msgMultiCard Die Nachricht vom Client
	 * @param name Der Name vom Spieler
	 * @throws IllegalArgumentException falls das ComObject im falschen Spiel benutzt wird
	 */
	public void resolveMessage(MsgMultiCards msgMultiCard, String name) throws IllegalArgumentException{
		throw new IllegalArgumentException("Das ComObjekt MsgMultiCards findet " +
				"keine Verwendung in diesem Spiel");
	}
	
	/**
	 * Bekommt eine MsgNumber Nachricht
	 * @param msgNumber Die Nachricht vom Client
	 * @param name Der Name des Spielers
	 * @throws IllegalArgumentException wenn das ComObjekt im falschen Spiel benutzt wird
	 */
	public void resolveMessage(MsgNumber msgNumber, String name) throws IllegalArgumentException{
		throw new IllegalArgumentException("Das ComObjekt MsgNumber findet " +
				"keine Verwendung in diesem Spiel");
	}
	
	/**
	 * Bekommt eine MsgSelection Nachricht
	 * @param msgSelection Die Nachricht vom Client
	 * @param name Der Name vom Spieler
	 * @throws IllegalArgumentException falls das ComObject im falschen Spiel benutz wird
	 */
	public void resolveMessage(MsgSelection msgSelection, String name) throws IllegalArgumentException{
		throw new IllegalArgumentException("Das ComObjekt MsgSelection findet " +
				"keine Verwendung in diesem Spiel");
	}
	
	/**
	 * Setzt den Punktestand eines Spielers
	 * @param player Der Spieler 
	 * @param i Punktestand
	 */
	protected void setPoints(PlayerState player, int i) {
		OtherData otherData = player.getOtherData();
		otherData.setPoints(i);
	}
	
	/**
	 * Holt den Punktestand eines Spielers
	 * @param player Der Spieler
	 * @return Die Punkte des Spielers
	 */
	protected int getPoints(PlayerState player) {
		return (player.getOtherData()).getPoints();
	}
	
	/**
	 * Gibt einem Spieler eine bestimmte Karte
	 * @param player Der Name eines Spielers
	 * @param card Eine Karte
	 * @return Gibt true zurück wenn die Karte im Deck ist, false sonst
	 */
	protected boolean giveACard(PlayerState player, Card card) {
		return gameState.giveACard(player,card);	
	}
	
	/**
	 * Der momentane Spieler spielt eine Karte
	 * @param card Die gespielte Karte
	 * @return true falls der Spieler die Karte hat, false wenn nicht
	 */
	protected boolean playCard(Card card) {
		return gameState.playCard(card);
	}
	
	/**
	 * Setzt eine Karte als Trumpf
	 * @param card Eine Karte
	 */
	protected void setTrumpCard(Card card) {
		gameState.setTrumpCard(card);
	}

	/** 
	 * Prueft ob ein gemachter Zug vom currentPlayer in einem Spiel gueltig war,
	 * wenn nicht wird an den Spieler erneut eine MsgCardRequest gesendet
	 * @param card Die Karte die gespielt wurde
	 * @return true falls Zug gueltig und false wenn nicht
	 */
	protected abstract boolean isValidMove(Card card);
	
	/**
	 * Berechnet und verteilt Stichpunkte an einzelne Spieler
	 */
	protected abstract void calculateTricks();

	/** 
	 * Berechnet das Ergebnis einer Rundenberechnung und ist verantwortlich 
	 * fuer das Setzten von Punkten beim Rundenende, sowie zur Überprüfung auf
	 * Spielende. 
	 */
	protected abstract void calculateRoundOutcome();

	/**
	 * Wird bei Spielende aufgerufen und gibt die Namen der Sieger zurück,
	 * nur mehr als einer falls es unentschieden steht.
	 * @return den Namen vom Spieler
	 */
	protected abstract List<String> getWinners();
	
	/**
	 * Erzeugt ein GameClientUpdate welches individuell für jeden Benutzer ist
	 * @param player Dem Spieler 
	 */
	protected GameClientUpdate generateGameClientUpdate(PlayerState player) {
		return new GameClientUpdate(player,
				gameState.getPlayedCards(),
				viewOfOtherPlayers(player), 
				getFirstPlayer(),
				getCurrentPlayer(),
				gameState.getTrumpCard());
		
	}
	
	/**
	 * Beendet das Spiel
	 */
	protected void quitGame() {
		server.quitGame();
	}
	
	/**
	 * Erzeugt eine Liste von OtherData mit der von einem bestimmten Spieler 
	 * ausgesehen richtigen Reihenfolge von den anderen Spielern
	 * @param player Der Spieler aus dessen Sicht die Liste gemacht wird
	 * @return Gibt eine Liste von OtherData in einer bestimmten Reihenfolge zurück
	 */
	private List<OtherData> viewOfOtherPlayers(PlayerState player) {
		List<PlayerState> players = getPlayers();
		List<OtherData> enemyData = new ArrayList<OtherData>();
		int iterator = players.indexOf(player);
		
		if(iterator == players.size()-1) {
			iterator = 0;
		} else {
			iterator++;
		}
		
		while(iterator != players.indexOf(player)) {
			enemyData.add((players.get(iterator)).getOtherData());
			if(iterator == players.size()-1) {
				iterator = 0;
			} else {
				iterator++;
			}
		}
		
		return enemyData;
	}

	public void resolveMessage(MsgCardRequest msgCardRequest, String name) {
		throw new IllegalArgumentException("Das ComObjekt MsgCardRequest findet " +
				"keine Verwendung in diesem Spiel");
	}

	public void resolveMessage(MsgGameEnd msgGameEnd, String name) {
		throw new IllegalArgumentException("Das ComObjekt MsgGameEnd findet " +
				"keine Verwendung in diesem Spiel");		
	}

	public void resolveMessage(MsgMultiCardsRequest msgMultiCardsRequest,
			String name) {
		throw new IllegalArgumentException("Das ComObjekt MsgMultiCardsRequest findet " +
				"keine Verwendung in diesem Spiel");		
	}

	public void resolveMessage(MsgNumberRequest msgNumberRequest, String name) {
		throw new IllegalArgumentException("Das ComObjekt MsgNumberRequest findet " +
				"keine Verwendung in diesem Spiel");		
	}

	public void resolveMessage(MsgSelectionRequest msgSelectionRequest,
			String name) {
		throw new IllegalArgumentException("Das ComObjekt MsgSelectionRequest findet " +
				"keine Verwendung in diesem Spiel");		
	}

	public void resolveMessage(MsgUser msgUser, String name) {
		throw new IllegalArgumentException("Das ComObjekt MsgUser findet " +
				"keine Verwendung in diesem Spiel");		
	}

	
}