package Ruleset;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Server.GameServer;
import ComObjects.*;

/**
 * ServerHearts. Diese Klasse erstellt das Regelwerk zum Spiel Hearts. Sie
 * enthaelt zudem weitere Methoden, welche für das Spiel Hearts spezifisch
 * benoetigt werden, wie die Regelung zum Tausch von Karten und die Berechnung
 * der Stichpunkten.
 */
public class ServerHearts extends ServerRuleset {

	/**
	 * Der Typ des Ruleset
	 */
	private final static RulesetType RULESET = RulesetType.Hearts;
	/**
	 * Die Punktanzahl eines Spielers ab der das Spiel zu Ende ist
	 */
	private final static int ENDING_POINTS = 100;

	/**
	 * Darin wird gespeichert wer welche Karten tauscht
	 */
	private Map<String, Set<Card>> swap;

	/**
	 * Gibt an, ob Herz schon ausgespielt werden darf.
	 */
	private boolean heartBroken;

	/**
	 * Erstellt das Regelwerk zum Spiel Hearts
	 */
	public ServerHearts(GameServer server) {
		super(RULESET, server);
		swap = new Hashtable<String, Set<Card>>();
		heartBroken = false;
	}

	@Override
	public void resolveMessage(MsgCard msgCard, String name) {
		Card card = msgCard.getCard();

		if (getGamePhase() != GamePhase.CardRequest) {
			send(WarningMsg.WrongPhase, name);
			throw new IllegalStateException(
					"Es wird in dieser Phase keine Karte erwartet "
							+ "vom Spieler " + name);

		} else if (getPlayerState(name) != getCurrentPlayer()) {
			send(WarningMsg.WrongPlayer, name);
			throw new IllegalArgumentException("Der Spieler " + name
					+ " ist nicht am Zug!");

		} else if (card.getRuleset() != RulesetType.Hearts
				|| card.getColour() == Colour.NONE) {
			send(WarningMsg.WrongCard, name);
			send(new MsgCardRequest(), name);
			throw new IllegalArgumentException("Die Karte " + card.getValue()
					+ card.getColour() + " gehört nicht zum Spiel");

		} else if (getPlayedCards().size() >= getPlayers().size()) {
			broadcast(WarningMsg.RulesetError);
			quitGame();
			throw new RulesetException(" Der Ablagestapel ist bereits voll.");

		} else if (!isValidMove(card)) {
			setGamePhase(GamePhase.CardRequest);
			send(WarningMsg.UnvalidMove, name);
			send(new MsgCardRequest(), name);
			throw new IllegalArgumentException("Der Spieler" + name
					+ "hat die Karte " + card.getValue() + card.getColour()
					+ " gespielt, obwohl sie kein gültiger " + "Zug ist.");

		} else if (!playCard(card)) {
			setGamePhase(GamePhase.CardRequest);
			send(WarningMsg.WrongCard, name);
			send(new MsgCardRequest(), name);
			throw new IllegalArgumentException("Der Spieler" + name
					+ "hat die Karte " + card.getValue() + card.getColour()
					+ " gespielt, obwohl er sie nicht hat.");

		} else {
			setGamePhase(GamePhase.Playing);

			if (getGameState().getPlayedCards().size() == getPlayers().size()) {
				updatePlayers();
				calculateTricks();
			} else {
				nextPlayer();
				updatePlayers();
				setGamePhase(GamePhase.CardRequest);
				send(new MsgCardRequest(), getCurrentPlayer()
						.getPlayerStateName());
			}
		}
	}

	@Override
	public synchronized void resolveMessage(MsgMultiCards msgMultiCard,
			String name) {
		Set<Card> cards = msgMultiCard.getCardList();

		if (getGamePhase() != GamePhase.MultipleCardRequest) {
			send(WarningMsg.WrongPhase, name);
			throw new IllegalStateException(
					"Es werden in dieser Phase werden keine Tauschkarten erwartet.");

		} else if (getPlayedCards().size() < 0
				|| getPlayedCards().size() >= getPlayers().size()) {
			broadcast(WarningMsg.RulesetError);
			quitGame();
			throw new RulesetException("Der Ablagestapel ist bereits voll.");

		} else if (!areValidChoosenCards(cards, name)) {
			setGamePhase(GamePhase.MultipleCardRequest);
			send(WarningMsg.WrongTradeCards, name);
			throw new IllegalArgumentException(
					"Die übergebenen Karten vom Spieler " + name
							+ " sind falsch");

		} else {
			getPlayerState(name).getHand().removeAll(cards);
			swap.put(name, cards);

			if (swap.size() == 4) {
				int roundNumber = getRoundNumber();

				if (roundNumber % 4 == 1) {
					swapLeft();

				} else if (roundNumber % 4 == 2) {
					swapAcross();

				} else if (roundNumber % 4 == 3) {
					swapRight();

				} else {
					throw new RulesetException("Fehler beim Tauschen.");
				}

				getGameState().sortHands(Colour.NONE);

				for (PlayerState player : getPlayers()) {
					if (player.getHand().contains(HeartsCard.Kreuz2)) {
						setCurrentPlayer(player);
						break;
					}
				}

				updatePlayers();

				setGamePhase(GamePhase.CardRequest);
				send(new MsgCardRequest(), getCurrentPlayer()
						.getPlayerStateName());

			} else {
				setGamePhase(GamePhase.MultipleCardRequest);
			}
		}
	}

	@Override
	protected boolean isValidMove(Card card) {
		setGamePhase(GamePhase.Playing);

		if (getCurrentPlayer().getHand().size() == 13) {
			// Noch kein Spieler hat eine Karte gespielt
			if (getPlayedCards().size() == 0) {
				// Die erste Karte jeder Runde muss die Kreuz2 sein.
				if (card != HeartsCard.Kreuz2) {
					return false;
				} else {
					return true;
				}
				// Es wurden bereits Karten gespielt
			} else {
				// In der ersten Runde darf kein Herz und nicht die PikDame
				// gespielt werden
				if (card.getColour() == Colour.HEART
						|| card == HeartsCard.PikDame) {
					// Wenn der Spieler nur Herz auf der Hand hat, oder nur Herz
					// und die PikDame
					List<Card> hand = getCurrentPlayer().getHand();

					for (Card handCard : hand) {
						// Wenn in der Spielerhand eine Karte weder Herz noch
						// PikDame ist
						if (handCard != HeartsCard.PikDame
								&& handCard.getColour() != Colour.HEART) {
							return false;
						}
					}
					heartBroken = true;
					return true;
				} else {
					return testOtherHandCards(card);
				}
			}
			// Die Spieler befinden sich nicht mehr im ersten Umlauf
		} else {
			// In diesem Umlauf wurde noch keine Karte gespielt
			if (getPlayedCards().size() == 0) {
				// Die Karte, die gespielt werden soll, hat die Farbe Herz
				if (card.getColour() == Colour.HEART) {
					// Wurde Herz schon einmal gespielt
					if (heartBroken == true) {
						return true;
					} else {
						for (Card handCard : getCurrentPlayer().getHand()) {
							if (handCard.getColour() != Colour.HEART) {
								return false;
							}
						}
						heartBroken = true;
						return true;
					}
					// Die Karte hat nicht die Farbe Herz
				} else {
					return true;
				}
				// Es wurden schon Karten gespielt
			} else {
				return testOtherHandCards(card);
			}
		}
	}

	/**
	 * Überprüft ob man eine Kartenfarbe zugeben muss
	 * 
	 * @param card
	 *            Die zu spielende Karte
	 * @return true falls Karte gültig, false wenn nicht
	 */
	private boolean testOtherHandCards(Card card) {
		Card firstCard = getPlayedCards().get(0).getCard();
		// Die Karte hat eine andere Farbe als die erste gespielte Karte der
		// Runde
		if (card.getColour() != firstCard.getColour()) {

			List<Card> hand = getCurrentPlayer().getHand();
			for (Card handCard : hand) {
				// Es gibt eine Karte auf der Hand, die die Farbe der
				// erstgepielten Karte hat
				if (handCard.getColour() == firstCard.getColour()) {
					return false;
				}
			}

			// Die zu spielende Karte hat die Farbe Herz
			if (card.getColour() == Colour.HEART) {
				heartBroken = true;
			}
			return true;
		}
		// Die Karte hat die selbe Farbe, wie die erste ausgespielte Karte der
		// Runde
		return true;
	}

	/**
	 * Tauscht bei jedem Spieler die Karten nach links
	 */
	private void swapLeft() {

		for (int i = 0; i < getPlayers().size(); i++) {
			PlayerState giver = getPlayers().get(i);
			PlayerState taker;

			if (i == getPlayers().size() - 1) {
				taker = getPlayers().get(0);
			} else {
				taker = getPlayers().get(i + 1);
			}

			taker.getHand().addAll(swap.get(giver.getPlayerStateName()));
		}

		swap = new Hashtable<String, Set<Card>>();
	}

	/**
	 * Tauscht bei jedem Spieler die Karten gegenüber
	 */
	private void swapAcross() {
		for (int i = 0; i < getPlayers().size(); i++) {
			PlayerState giver = getPlayers().get(i);
			PlayerState taker;

			if (i == getPlayers().size() - 2) {
				taker = getPlayers().get(0);
			} else if (i == getPlayers().size() - 1) {
				taker = getPlayers().get(1);
			} else {
				taker = getPlayers().get(i + 2);
			}

			taker.getHand().addAll(swap.get(giver.getPlayerStateName()));
		}

		swap = new Hashtable<String, Set<Card>>();
	}

	/**
	 * Tauscht bei jedem Spieler die Karten nach rechts
	 */
	private void swapRight() {
		for (int i = 0; i < getPlayers().size(); i++) {
			PlayerState giver = getPlayers().get(i);
			PlayerState taker;

			if (i == 0) {
				taker = getPlayers().get(getPlayers().size() - 1);
			} else {
				taker = getPlayers().get(i - 1);
			}

			taker.getHand().addAll(swap.get(giver.getPlayerStateName()));
		}

		swap = new Hashtable<String, Set<Card>>();
	}

	/**
	 * Überprüft ob eine übergebenes Kartenset von einem Spieler gültig ist
	 * 
	 * @param cards
	 *            Ein Kartenset
	 * @return true falls das Kartenset gültig ist, false wenn nicht
	 */
	private boolean areValidChoosenCards(Set<Card> cards, String name) {
		int numberOfSwappingCards = 3;
		setGamePhase(GamePhase.Playing);

		if (cards.size() != numberOfSwappingCards) {
			return false;

		} else if (swap.containsKey(name)) {
			return false;

		} else if (!getPlayerState(name).getHand().containsAll(cards)) {
			return false;

		} else {
			return true;
		}
	}

	@Override
	protected void calculateTricks() {
		if (getPlayedCards().size() == getPlayers().size()) {
			DiscardedCard strongestCard = getPlayedCards().get(0);

			for (int i = 1; i < getPlayedCards().size(); i++) {
				DiscardedCard nextCard = getPlayedCards().get(i);

				if (nextCard.getCard().getColour() == strongestCard.getCard()
						.getColour()
						&& nextCard.getCard().getValue() > strongestCard
								.getCard().getValue()) {
					strongestCard = nextCard;
				}

			}

			PlayerState trickWinner = getPlayerState(strongestCard
					.getOwnerName());
			getGameState().madeTrick(trickWinner);
			trickWinner.getOtherData().setCurrentPoints();

			boolean noOneHasACard = true;

			for (PlayerState player : getPlayers()) {
				if (!player.getHand().isEmpty()) {
					noOneHasACard = false;
				}
			}

			broadcast(new MsgBoolean(heartBroken));

			if (noOneHasACard) {
				setGamePhase(GamePhase.RoundEnd);
				calculateRoundOutcome();
			} else {
				setGamePhase(GamePhase.CardRequest);
				setCurrentPlayer(trickWinner);
				updatePlayers();
				send(new MsgCardRequest(), trickWinner.getPlayerStateName());
			}

		} else {
			throw new RulesetException("Es sind nicht alle Karten von "
					+ "jedem Spieler auf dem Ablagestapel");
		}
	}

	@Override
	protected void calculateRoundOutcome() {
		if (getGamePhase() == GamePhase.RoundEnd
				|| getGamePhase() == GamePhase.Ending) {

			for (PlayerState player : getPlayers()) {
				int points = player.getOtherData().getPoints();
				int currentPoints = ((HeartsData) player.getOtherData())
						.getCurrentPoints();

				if (currentPoints == 26) {
					for (PlayerState playerGetsPoints : getPlayers()) {

						if (!playerGetsPoints.getPlayerStateName().equals(
								player.getPlayerStateName())) {
							playerGetsPoints.getOtherData().setPoints(
									points + currentPoints);
						}

					}
					break;
				} else {
					player.getOtherData().setPoints(points + currentPoints);
				}
			}

			for (PlayerState player : getPlayers()) {
				if (player.getOtherData().getPoints() >= ENDING_POINTS) {
					setGamePhase(GamePhase.Ending);
					break;
				}
			}

			if (getGamePhase() == GamePhase.Ending) {
				updatePlayers();
				List<String> winners = getWinners();
				broadcast(new MsgGameEnd(winners));
				quitGame();

			} else {
				getGameState().restartDeck(createDeck());

				for (PlayerState player : getPlayers()) {
					player.getOtherData().setCurrentPoints();
				}

				setGamePhase(GamePhase.RoundStart);
				getGameState().nextRound();
				startRound();
			}

		}
	}

	@Override
	protected List<String> getWinners() {
		int minPoints = 1000;
		List<String> leadingPlayers = new ArrayList<String>();

		for (PlayerState player : getPlayers()) {
			if (player.getOtherData().getPoints() < minPoints) {
				leadingPlayers = new ArrayList<String>();
				leadingPlayers.add(player.getPlayerStateName());
				minPoints = player.getOtherData().getPoints();

			} else if (player.getOtherData().getPoints() == minPoints) {
				leadingPlayers.add(player.getPlayerStateName());
			}
		}

		return leadingPlayers;
	}

	@Override
	public void runGame() throws IllegalNumberOfPlayersException {
		List<PlayerState> players = getPlayers();

		if ((players.size() < RULESET.getMinPlayer())
				|| (players.size() > RULESET.getMaxPlayer())
				|| (players.size() == 0)) {
			throw new IllegalNumberOfPlayersException(
					"The number of players are: " + players.size());
		}
		setFirstPlayer(players.get(0));
		setGamePhase(GamePhase.RoundStart);
		startRound();
	}

	@Override
	protected void startRound() {
		if (getGamePhase() == GamePhase.RoundStart) {
			getGameState().shuffleDeck();
			heartBroken = false;

			/*
			 * Verteilt die Karten an Spieler. Wenn false zurück kommt wird ein
			 * neues Deck erstellt und alle Karten im Spiel gelöscht. Wenn
			 * nochmal ein Fehler kommt wirft es eine Exception
			 */
			if (!getGameState().dealCards(13)) {
				broadcast(WarningMsg.RulesetError);
				quitGame();
				throw new RulesetException(
						"Probleme beim Verteilen der Karten!");
			}

			getGameState().sortHands(Colour.NONE);

			if (getRoundNumber() % 4 == 0) {

				for (PlayerState player : getPlayers()) {
					if (player.getHand().contains(HeartsCard.Kreuz2)) {
						setCurrentPlayer(player);
						break;
					}
				}
				updatePlayers();
				setGamePhase(GamePhase.CardRequest);
				send(new MsgCardRequest(), getCurrentPlayer()
						.getPlayerStateName());
			} else {
				updatePlayers();
				setGamePhase(GamePhase.MultipleCardRequest);
				broadcast(new MsgMultiCardsRequest(3));
			}

		} else {
			throw new RulesetException("Das Spiel ist noch nicht am "
					+ "Rundenanfang");
		}
	}

	/**
	 * Setzt ob Herz gebrochen wurde
	 * 
	 * @param heartBroken
	 */
	protected void setHeartBroken(boolean heartBroken) {
		this.heartBroken = heartBroken;
	}
}
