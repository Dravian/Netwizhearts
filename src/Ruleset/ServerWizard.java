package Ruleset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Server.GameServer;
import ComObjects.MsgCard;
import ComObjects.MsgCardRequest;
import ComObjects.MsgGameEnd;
import ComObjects.MsgNumber;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelection;
import ComObjects.MsgSelectionRequest;
import ComObjects.MsgUser;

/**
 * ServerWizard. Diese Klasse erstellt das Regelwerk zum Spiel Wizard. Sie
 * enthaelt zudem weitere Methoden, welche für das Spiel Wizard spezifisch
 * benoetigt werden, wie das Ansage von Stichen, der Bestimmung von Trumpffarben
 * und die Bestimmung der Rundenanzahl.
 */
public class ServerWizard extends ServerRuleset {
	/**
	 * Die minimale Anzahl an Spielern in Wizard
	 */
	private static final int MIN_PLAYERS = 3;
	/**
	 * Die maximal Anzahl an Spielern in Hearts
	 */
	private static final int MAX_PLAYERS = 6;
	/**
	 * Der Ruleset Typ des Spiels
	 */
	private final static RulesetType RULESET = RulesetType.Wizard;

	/**
	 * Die Anzahl an Runden die gespielt wird. Ist abhaengig von der
	 * Spieleranzahl.
	 */
	private int playingRounds;

	/**
	 * Erstellt das Regelwerk zum Spiel Wizard
	 */
	public ServerWizard(GameServer s) {
		super(RULESET, MIN_PLAYERS, MAX_PLAYERS, s);
	}

	@Override
	protected boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;
	}

	/**
	 * Setzt die Anzahl an Runden die es in diesem Spiel gibt
	 * 
	 * @param rounds
	 *            Die Anzahl an Runden
	 */
	private void setPlayingRounds(int rounds) {
		playingRounds = rounds;
	}

	/**
	 * Holt die Anzahl der Runden die gespielt werden
	 * 
	 * @return playingRounds Die Anzahl an Runden
	 */
	protected int getPlayingRounds() {
		return playingRounds;
	}

	@Override
	public void resolveMessage(MsgCard msgCard, String name)
			throws IllegalArgumentException {
		Card card = msgCard.getCard();

		if (getPlayerState(name) != getCurrentPlayer()) {
			throw new IllegalArgumentException("Der Spieler " + name
					+ " ist nicht am Zug!");

		} else if (card.getRuleset() != RulesetType.Wizard
				|| card.getColour() == Colour.NONE) {
			throw new IllegalArgumentException("Die Karte " + card.getValue()
					+ card.getColour() + " gehört nicht zum Spiel");

		} else if (!isValidMove(card)) {
			send(new MsgCardRequest(), name);

			throw new RulesetException("Der Spieler" + name + "hat die Karte "
					+ card.getValue() + card.getColour()
					+ " gespielt, obwohl sie kein gültiger "
					+ "Zug ist. Es muss ein Fehler bei ClientWizard sein.");

		} else {
			if (getGameState().getPlayedCards().size() == getPlayers().size()) {
				calculateTricks();
			} else {
				nextPlayer();
				setGamePhase(GamePhase.CardRequest);
				send(new MsgCardRequest(), getCurrentPlayer().getName());
			}
		}
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Spieler eine Stichansage macht.
	 * Die wird dann in isValidNumber überprüft, bei falsche Eingabe wird´ eine
	 * MsgCardRequest an den selben Spieler geschickt. Bei richtiger Eingabe
	 * geht das Spiel weiter.
	 * 
	 * @param msgNumber
	 *            Die Nachricht vom Client
	 * @param name
	 *            Der Name des Spielers
	 */
	public void resolveMessage(MsgNumber msgNumber, String name) {
	}

	/**
	 * Ueberprueft ob eine eingegebene Stichangabe eines Spielers gueltig ist.
	 * 
	 * @param number
	 *            Die Stichangabe
	 * @param name
	 *            Der Name vom Spieler
	 * @return true falls die Stichangabe gültig ist, false wenn nicht
	 */
	private boolean isValidNumber(int number, String name) {
		return false;
	}

	/**
	 * Verarbeitet die RulesetMessage dass mehrerer Karten vom Spieler
	 * uebergeben werden. Die wird dann in isValidColour überprüft, bei falsche
	 * Eingabe wird´ MsgMultiCardRequest an den selben Spieler geschickt. Bei
	 * richtiger Eingabe geht das Spiel weiter.
	 * 
	 * @param msgSelection
	 *            Die Nachricht vom Client
	 * @param name
	 *            Der Name des Spielers
	 */
	public void resolveMessage(MsgSelection msgSelection, String name) {

	}

	/**
	 * Ueberprueft ob eine eingebene Trumpffarbe eines Spielers gueltig ist
	 * 
	 * @param colour
	 *            Die Trumpffarbe
	 * @param name
	 *            Der Name des Spielers
	 * @return true falls die Farbe gueltig ist, false wenn nicht
	 */
	private boolean isValidColour(Colour colour, String name) {
		return false;
	}

	@Override
	protected void calculateTricks() {
		int valueOfFool = 0;
		int valueOfSorcerer = 14;

		DiscardedCard strongestCard = getPlayedCards().get(0);

		
		for(int i = 0; i < getPlayedCards().size(); i++) {
			DiscardedCard nextCard = getPlayedCards().get(i);

			if (strongestCard.getCard().getValue() == valueOfSorcerer) {

			} else {
				if (nextCard.getCard().getValue() == valueOfSorcerer) {		
				strongestCard = nextCard;
			
				} else if (strongestCard.getCard().getValue() == valueOfFool
					&& nextCard.getCard().getValue() > valueOfFool) {
					strongestCard = nextCard;

				} else if (nextCard.getCard().getValue() > strongestCard.getCard()
					.getValue()
					&& nextCard.getCard().getColour() == strongestCard
							.getCard().getColour()) {
				strongestCard = nextCard;

				} else if (nextCard.getCard().getColour() == getTrumpCard()
					.getColour()
					&& (nextCard.getCard().getValue() != valueOfFool)
					&& strongestCard.getCard().getColour() != getTrumpCard()
							.getColour()) {
				strongestCard = nextCard;
				}
			}
		}
			

		PlayerState trickWinner = getPlayerState(strongestCard.getName());
		getGameState().madeTrick(trickWinner);
		
		for (PlayerState player : getPlayers()) {
			send(new MsgUser(generateGameClientUpdate(player)),
					player.getName());
		}
		
		setGamePhase(GamePhase.CardRequest);
		setCurrentPlayer(trickWinner);
		
		send(new MsgCardRequest(), trickWinner.getName());
	}

	/**
	 * Holt die Trumpfkarte
	 * 
	 * @return Gibt die Trumpffarbe zurueck
	 */
	protected Card getTrumpCard() {
		return getGameState().getTrumpCard();
	}

	@Override
	public void runGame() throws IllegalNumberOfPlayersException {
		List<PlayerState> players = getPlayers();
		int deckSize = WizardCard.values().length;

		if ((players.size() < MIN_PLAYERS) || (players.size() > MAX_PLAYERS)
				|| (players.size() == 0)) {
			throw new IllegalNumberOfPlayersException(
					"The number of players are: " + players.size());
		} else {
			int numberOfRounds = deckSize / players.size();
			setPlayingRounds(numberOfRounds);
		}

		setFirstPlayer(players.get(0));

		setGamePhase(GamePhase.RoundStart);
		startWizardRound();
	}

	/**
	 * Wird am Anfang jeder Runde aufgerufen. Mischt und verteilt Karten und
	 * schickt Comobject mit Updates und Request für den Rundenanfang.
	 */
	private void startWizardRound() {
		int valueOfSorcerer = 14;
		List<PlayerState> players = getPlayers();

		getGameState().newRound();
		getGameState().shuffleDeck();
		getGameState().dealCards(getGameState().getRoundNumber());

		Card trumpCard = getGameState().getTopCard();
		getGameState().setTrumpCard(trumpCard);

		for (PlayerState player : players) {
			send(new MsgUser(generateGameClientUpdate(player)),
					player.getName());
		}

		/*
		 * Falls ein Zauberer aufgedeckt wird, darf der Spieler vor dem
		 * firstPlayer entscheiden welche Farbe Trumpf ist.
		 */
		if (trumpCard.getValue() == valueOfSorcerer) {
			setGamePhase(GamePhase.SelectionRequest);
			PlayerState choosingPlayer;
			int index;

			if (players.indexOf(getFirstPlayer()) == 0) {
				index = players.size() - 1;
			} else {
				index = players.indexOf(getFirstPlayer()) - 1;
			}

			choosingPlayer = players.get(index);

			send(new MsgSelectionRequest(), choosingPlayer.getName());

		} else {
			setGamePhase(GamePhase.TrickRequest);

			broadcast(new MsgNumberRequest());
		}
	}

	@Override
	protected void calculateRoundOutcome() {
		List<PlayerState> players = getPlayers();

		/*
		 * Verrechnet die Punkte für jeden Spieler
		 */
		for (PlayerState player : players) {
			int announcedTricks = ((WizData) player.getOtherData())
					.getAnnouncedTricks();
			int madeTricks = ((WizData) player.getOtherData())
					.getNumberOfTricks() / players.size();
			int points = player.getOtherData().getPoints();

			getGameState().putTricksBackInDeck(player);

			if (announcedTricks == madeTricks) {
				points = points + 20 + 10 * madeTricks;
			} else if (announcedTricks < madeTricks) {
				points = points - 10 * (madeTricks - announcedTricks);
			} else {
				points = points - 10 * (announcedTricks - madeTricks);
			}
			player.getOtherData().setPoints(points);
		}

		for (PlayerState player : players) {
			send(new MsgUser(generateGameClientUpdate(player)),
					player.getName());
		}

		if (getGamePhase() == GamePhase.Ending) {
			List<String> winners = getWinners();
			broadcast(new MsgGameEnd(winners));
		} else {
			setCurrentPlayer(getFirstPlayer());

			setFirstPlayer(getCurrentPlayer());

			setGamePhase(GamePhase.RoundStart);
			getGameState().newRound();
			startWizardRound();
		}

	}

	@Override
	protected List<String> getWinners() {
		int maxPoints = -99999;
		List<String> leadingPlayers = new ArrayList<String>();

		for (PlayerState player : getPlayers()) {
			if (player.getOtherData().getPoints() > maxPoints) {
				leadingPlayers = new ArrayList<String>();
				leadingPlayers.add(player.getName());
				maxPoints = player.getOtherData().getPoints();

			} else if (player.getOtherData().getPoints() == maxPoints) {
				leadingPlayers.add(player.getName());
			}
		}

		return leadingPlayers;
	}
}
