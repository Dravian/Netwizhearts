/**
 * 
 */
package Ruleset;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Client.ClientModel;
import ComObjects.*;

/**
 * ClientRuleset. ClientRuleset ist eine abstrakte Klasse und wird zur
 * Regelvorauswertung im Client verwendet. Dazu benutzt es die isValidMove()
 * Methode. Des Weiteren kann es vom ClientModel erhaltene RulesetMessages mit
 * der resolveMessage() Methode behandeln und neue RulesetMessages senden.
 */
public abstract class ClientRuleset {
	/**
	 * Das Model in dem sich befindet
	 */
	private ClientModel client;

	/**
	 * Der Spielzustand aus der Sicht des Models
	 */
	private GameClientUpdate gameState;

	/**
	 * Der Spieltyp
	 */
	private final RulesetType RULESET;

	/**
	 * Die momentane Spielphase
	 */
	private GamePhase gamePhase;

	/**
	 * Die Gewinner des Spiels
	 */
	private List<String> winners;

	/**
	 * Erstellt eine ClientRuleset Klasse
	 * 
	 * @param ruleset
	 *            Das Ruleset zum Spiel
	 * @param client
	 *            Das ClientModel auf dem gespielt wird
	 */
	protected ClientRuleset(RulesetType ruleset, ClientModel client) {
		RULESET = ruleset;
		gamePhase = GamePhase.Start;
		this.client = client;
		gameState = null;
	}

	/**
	 * Gibt den Typ des Regelwerks zurueck
	 * 
	 * @return Der Typ vom Regelwerk
	 */
	public RulesetType getRulesetType() {
		return RULESET;
	}

	/**
	 * Setzt die Spielphase neu
	 * 
	 * @param phase
	 *            Die neue Spielphase
	 */
	protected void setGamePhase(GamePhase phase) {
		gamePhase = phase;
	}

	/**
	 * Gibt die momentane Spielphase zurück
	 * 
	 * @return gamePhase Die Spielphase
	 */
	public GamePhase getGamePhase() {
		return gamePhase;
	}

	/**
	 * Gibt den Spielzustand zurück
	 * 
	 * @return den Spielzustand
	 */
	public GameClientUpdate getGameState() {
		if (gamePhase != GamePhase.Start || gameState == null) {
			return gameState;
		} else {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		}
	}

	/**
	 * Gibt die eigenen Handkarten zurück
	 * 
	 * @return Liste von Karten
	 */
	public List<Card> getOwnHand() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getOwnHand();
		}
	}

	/**
	 * Gibt die OtherData des Models zurück
	 * 
	 * @return Die Otherdata des Models
	 */
	public OtherData getOwnData() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getOwnData();
		}
	}

	/**
	 * Holt die OtherData eines anderen Spielers
	 * 
	 * @return otherPlayerData Die OtherData
	 */
	public List<OtherData> getOtherPlayerData() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getOtherPlayerData();
		}
	}

	/**
	 * Gibt den Spieler der momentan am Zug ist zurueck
	 * 
	 * @return Der momentane Spieler
	 */
	public String getCurrentPlayer() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getCurrentPlayer();
		}
	}

	/**
	 * Holt die aufgedeckte Trumpfkarte
	 * 
	 * @return Eine Karte
	 */
	public Card getUncoveredCard() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getUncoveredCard();
		}
	}

	/**
	 * Holt die Anzahl der gespielten Runden
	 * 
	 * @return Die Anzahl der Runden
	 */
	public int getRoundNumber() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getRoundNumber();
		}
	}

	/**
	 * Holt die gespielten Karten auf dem Ablagestapel als DiscardedCards
	 * 
	 * @return Die gespielten Karten
	 */
	public List<DiscardedCard> getPlayedCards() {
		if (gamePhase == GamePhase.Start) {
			throw new IllegalStateException("Spiel hat noch nicht begonnen.");
		} else {
			return gameState.getPlayedCards();
		}
	}

	public List<String> getWinners() {
		if (gamePhase == GamePhase.Ending) {
			return winners;
		} else {
			getModel().openWarning(WarningMsg.WrongPhase);
			throw new IllegalStateException(
					"Jetzt ist das Spiel noch nicht zu Ende.");
		}
	}

	/**
	 * Verarbeitet eine RulesetMessage vom Server
	 * 
	 * @param message
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(RulesetMessage message) {
		throw new RulesetException(
				"Das Comobject RulesetMessage wird hier nicht" + "gebraucht");
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server ein Spielupdate an den
	 * Client schickt
	 * 
	 * @param clientUpdate
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgUser clientUpdate) {
		setGamePhase(GamePhase.Playing);
		this.gameState = clientUpdate.getGameClientUpdate();
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt
	 * eine Karte zu spielen
	 * 
	 * @param msgCardRequest
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgCardRequest msgCardRequest) {
		setGamePhase(GamePhase.CardRequest);
		getModel().announceTurn(UserMessages.PlayCard);
	}

	/**
	 * Verarbeitet die RulesetMessage dass das Spiel zu Ende ist und das es
	 * einen Sieger gibt
	 * 
	 * @param gameEnd
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgGameEnd gameEnd) {
		setGamePhase(GamePhase.Ending);
		winners = gameEnd.getWinnerName();
		getModel().announceWinner(UserMessages.GameEnd);
	}

	/**
	 * Setzt ein neues GameClientUpdate
	 * 
	 * @param clientUpdate
	 *            Das Update des Spiels
	 */
	protected void setGameState(GameClientUpdate clientUpdate) {
		this.gameState = clientUpdate;
	}

	/**
	 * Holt das Model
	 * 
	 * @return Das Model
	 */
	protected ClientModel getModel() {
		return client;
	}

	/**
	 * Ruft beim Model die send Methode auf und verschickt eine Rulesetmessage
	 * 
	 * @param message
	 *            Die Nachricht
	 */
	protected void send(RulesetMessage message) {
		gamePhase = GamePhase.Playing;
		client.send(message);
	}

	/**
	 * Prueft ob ein gemachter Zug in einem Spiel gueltig war
	 * 
	 * @param card
	 *            Die Karte
	 * @return true falls die Karte gueltig ist, false wenn nicht
	 */
	protected abstract boolean isValidMove(Card card);

	/**
	 * Wird nicht verwendet
	 * 
	 * @param card
	 */
	public void resolveMessage(MsgCard card) {
		throw new IllegalArgumentException(
				"Das Comobject MsgCard wird hier nicht" + "gebraucht");
	}

	/**
	 * Wird nicht verwendet
	 * 
	 * @param card
	 */
	public void resolveMessage(MsgNumber number) {
		throw new IllegalArgumentException(
				"Das Comobject MsgNumber wird hier nicht" + "gebraucht");
	}

	/**
	 * Wird nicht verwendet
	 * 
	 * @param card
	 */
	public void resolveMessage(MsgSelection selection) {
		throw new IllegalArgumentException(
				"Das Comobject MsgSelection wird hier nicht" + "gebraucht");
	}

	/**
	 * Wird nicht verwendet
	 * 
	 * @param card
	 */
	public void resolveMessage(MsgMultiCards mulit) {
		throw new IllegalArgumentException(
				"Das Comobject MsgMultiCards wird hier nicht" + "gebraucht");
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt
	 * eine Stichanzahl anzugeben
	 * 
	 * @param msgNumber
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgNumberRequest msgNumber) {
		throw new IllegalArgumentException(
				"Das Comobject MsgNumberRequest wird hier nicht" + "gebraucht");
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt
	 * eine Farbe auszuwaehlen
	 * 
	 * @param msgSelection
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgSelectionRequest msgSelection) {
		throw new IllegalArgumentException(
				"Das Comobject MsgSelection wird hier dnicht" + "gebraucht");
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt
	 * mehrere Karten anzugeben
	 * 
	 * @param msgMultiCardsRequest
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgMultiCardsRequest msgMultiCardsRequest) {
		throw new IllegalArgumentException(
				"Das Comobject MsgMultiCardsRequest wird hier nicht"
						+ "gebraucht");
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server dem Spieler ein Boolean
	 * schickt
	 * 
	 * @param msgBool
	 *            RulesetMessage mit Boolean
	 */
	public void resolveMessage(MsgBoolean msgBool) {
		throw new IllegalArgumentException("Das ComObjekt MsgBoolean findet "
				+ "keine Verwendung in diesem Spiel");
	}

	/**
	 * Prüft ob die Anzahl der angesagten Stiche vom Spieler gültig sind
	 * 
	 * @param number
	 *            Die Anzahl der angesagten Stichen
	 * @return true falls die Anzahl der Stiche passen, false wenn nicht
	 */
	protected boolean isValidTrickNumber(int number) {
		throw new UnsupportedOperationException(
				"Wird in diesem Ruleset nicht verwendet");
	}

	/**
	 * Prüft ob die angesagte Trumpffarbe richtig ist
	 * 
	 * @param colour
	 *            Die angesagte Trumpffarbe
	 * @return true falls die Farbe in Ordnung ist, false wenn nicht
	 */
	protected boolean isValidColour(Colour colour) {
		throw new UnsupportedOperationException(
				"Wird in diesem Ruleset nicht verwendet");
	}

	/**
	 * Gibt zurück ob die Karten die der Client tauschen will, gültig sind
	 * 
	 * @param cards
	 *            Die zu tauschenden Karten
	 * @return true wenn Karten valide sind, false wenn nicht
	 */
	protected boolean areValidChoosenCards(Set<Card> cards) {
		throw new UnsupportedOperationException(
				"Wird in diesem Ruleset nicht verwendet");
	}

	/**
	 * Holt die Trumpffarbe des Spiels
	 * 
	 * @return Gibt die Trumpffarbe zurück
	 */
	public Colour getTrumpColour() {
		throw new UnsupportedOperationException(
				"Wird in diesem Ruleset nicht verwendet");

	}

	/**
	 * Gibt die Farben die es im Spiel gibt zurück
	 * 
	 * @return Die Farben des Spiels
	 */
	public List<Colour> getColours() {
		throw new UnsupportedOperationException(
				"Wird in diesem Ruleset nicht verwendet");

	}

	/**
	 * Wird vom Model aufgerufen um eine Karte zu spielen
	 * 
	 * @param card
	 *            Die zu spielende Karte
	 */
	public void playCard(Card card) {
		if (getGamePhase() == GamePhase.CardRequest) {

			if (getPlayedCards().size() >= getOtherPlayerData().size() + 1
					|| getPlayedCards().size() < 0) {
				getModel().openWarning(WarningMsg.RulesetError);
				throw new RulesetException("Der Ablagestapel ist bereits voll.");

			} else if (!gameState.getOwnHand().contains(card)) {
				getModel().openWarning(WarningMsg.WrongCard);
				getModel().announceTurn(UserMessages.PlayCard);		
		
			} else if (isValidMove(card)) {
				send(new MsgCard(card));
				
			} else {
				getModel().openWarning(WarningMsg.UnvalidMove);
				getModel().announceTurn(UserMessages.PlayCard);
			}
		} else {
			getModel().openWarning(WarningMsg.WrongPhase);
			throw new IllegalStateException(
					"Jetzt darf keine Karte gespielt werden.");
		}
	}

	/**
	 * Wird vom Model aufgerufen um Karten zu tauschen
	 * 
	 * @param cards
	 *            Die zu tauschenden Karten
	 */
	public void chooseCards(List<Card> cards) {
		if (RULESET == RulesetType.Hearts) {
			if (getGamePhase() == GamePhase.MultipleCardRequest) {
				Set chooseCards = new HashSet(cards);
				
				if (areValidChoosenCards(new HashSet(cards))) {
					send(new MsgMultiCards(chooseCards));
				} else {
					getModel().openWarning(WarningMsg.WrongTradeCards);
					getModel().openChooseCardsWindow(UserMessages.ChooseCards);
				}

			} else {
				getModel().openWarning(WarningMsg.WrongPhase);
				throw new IllegalStateException(
						"Jetzt werden keine Karten getauscht");
			}
		} else {
			throw new UnsupportedOperationException(
					"Wird in diesem Ruleset nicht verwendet");
		}
	}

	/**
	 * Wird vom Model aufgerufen um eine Nummer auszuwählen
	 * 
	 * @param number
	 *            Die ausgewählte Nummer
	 */
	public void chooseTrickNumber(int number) {
		if (RULESET == RulesetType.Wizard) {
			if (getGamePhase() == GamePhase.TrickRequest) {

				if (isValidTrickNumber(number)) {
					send(new MsgNumber(number));
				} else {
					setGamePhase(GamePhase.TrickRequest);
					getModel().openWarning(WarningMsg.WrongNumber);
					getModel().openNumberInputWindow(UserMessages.ChooseNumber);
				}

			} else {
				getModel().openWarning(WarningMsg.WrongPhase);
				throw new IllegalStateException(
						"Jetzt darf keine Zahl angesagt werden.");
			}
		} else {
			throw new UnsupportedOperationException(
					"Wird in diesem Spiel nicht verwendet");
		}
	}

	/**
	 * Wird vom Model aufgerufen um eine Farbe auszuwählen
	 * 
	 * @param colour
	 *            Die zu wählende Farbe
	 */
	public void chooseColour(Colour colour) {
		if (RULESET == RulesetType.Wizard) {
			if (getGamePhase() == GamePhase.SelectionRequest) {
				if (isValidColour(colour)) {
					send(new MsgSelection(colour));
				} else {
					getModel().openWarning(WarningMsg.WrongColour);
					getModel()
							.openChooseColourWindow(UserMessages.ChooseColour);
				}
			} else {
				getModel().openWarning(WarningMsg.WrongPhase);
				throw new IllegalStateException(
						"Jetzt darf keine Farbe ausgewählt werden.");
			}
		} else {
			throw new UnsupportedOperationException(
					"Wird in diesem Ruleset nicht verwendet");
		}
	}
}