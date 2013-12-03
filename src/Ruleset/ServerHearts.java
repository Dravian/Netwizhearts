package Ruleset;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Ruleset.Colour;
import Ruleset.HeartsCard;
import Server.GameServer;
import ComObjects.*;

/**
 * ServerHearts. Diese Klasse erstellt das Regelwerk zum Spiel Hearts. Sie
<<<<<<< HEAD
 * enthaelt zudem weitere Methoden, welche f?r das Spiel Hearts spezifisch
=======
 * enthaelt zudem weitere Methoden, welche f�r das Spiel Hearts spezifisch
>>>>>>> Restliche Methoden + Test
 * benoetigt werden, wie die Regelung zum Tausch von Karten und die Berechnung
 * der Stichpunkten.
 */
public class ServerHearts extends ServerRuleset {
<<<<<<< HEAD
    /**
     * Die Minimale Anzahl an Spielern im Spiel Hearts
     */
    private final static int MIN_PLAYERS = 4;
    /**
     * Die maximale Anzahl an Spielern im Spiel Wizard
     */
    private final static int MAX_PLAYERS = 4;
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
    private boolean heartBroken = false;

    /**
     * Erstellt das Regelwerk zum Spiel Hearts
     */
    public ServerHearts(GameServer s) {
        super(RULESET, MIN_PLAYERS, MAX_PLAYERS, s);
        swap = new Hashtable<String, Set<Card>>();
    }

    @Override
    protected boolean isValidMove(Card card) {
        setGamePhase(GamePhase.Playing);

        // Jeder Spieler hat bereits eine Karte gespielt
        if (getPlayedCards().size() == getPlayers().size()) {
            return false;
            // Noch kein Spieler hat eine Karte gespielt
        } else if (getPlayedCards().size() == 0) {
            // Die erste Karte jeder Runde muss die Kreu2 sein.
            if (card != HeartsCard.Kreuz2) {
                return false;
            } else {
                return true;
            }
        // Die Spieler befinden sich in der ersten Runde
        } else if (getRoundNumber() == 1) {
            // In der ersten Runde darf kein Herz und nicht die Kreuz2 gespielt werden
            if (card.getColour() == Colour.HEART || card == HeartsCard.PikDame) {
                return false;
            } else {
                return true;
            }
            // Die Spieler befinden sich nicht mehr in der ersten Runde
        } else if (getRoundNumber() > 1) {
            // In dieser Runde wurde noch keine Karte gespielt
            if (getPlayedCards().size() == 0) {
                // Die Karte, die gespielt werden soll, hat die Farbe Herz
                if (card.getColour() == Colour.HEART) {
                    // Wurde Herz schoneinmal gespielt
                    if (heartBroken == true) {
                        return true;
                    } else {
                        return false;
                    }
                // Die Karte hat nicht die Farbe Herz
                } else {
                    return true;
                }
            // Es wurden schon Karten gespielt
            } else {
                Card firstCard = getPlayedCards().get(0).getCard();
                // Die Karte hat die selbe Farbe, wie die erste ausgespielte Karte der Runde
                if (card.getColour() == firstCard.getColour()) {
                    return true;
                // Die Karte hat eine andere Farbe als die erste gespielte Karte der Runde
                } else {
                    List<Card> hand = getCurrentPlayer().getHand();
                    // Ist die Karte zu diesem Zeitpunkt schon nicht mehr in der Hand?? --> sonst falsch
                    for (Card handCard : hand) {
                        // Es gibt eine Karte auf der Hand, die die Farbe der erstgepielten Karte hat
                        if (handCard.getColour() == firstCard.getColour()) {
                            return false;
                        // Die Hand enthält keine Karte der Farbe der erstgespielten Karte
                        } else {
                            // Die zu spielende Karte hat die Farbe Herz
                            if (card.getColour() = Colour.HEART) {
                                heartBroken = true;
                            }
                            return true;
                        }
                    }
                }
            }
        }
    }
}


    @Override
    public void resolveMessage(MsgMultiCards msgMultiCard, String name) {
        Set<Card> cards = msgMultiCard.getCardList();

        if (getGamePhase() != GamePhase.MultipleCardRequest) {
            send(WarningMsg.WrongPhase, name);
            throw new IllegalStateException(
                    "Es werden in dieser Phase werden keine Tauschkarten erwartet.");

        } else if (!areValidChoosenCards(cards, name)) {
            send(WarningMsg.WrongTradeCards, name);
            throw new IllegalArgumentException("Die übergebenen Karten vom Spieler "
                    + name + " sind falsch");

        } else {
            getPlayerState(name).getHand().removeAll(cards);
            swap.put(name, cards);

            if (swap.size() == 4) {
                setGamePhase(GamePhase.Playing);

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

                updatePlayers();

                for (PlayerState player : getPlayers()) {
                    if (player.getHand().contains(HeartsCard.Kreuz2)) {
                        setFirstPlayer(player);
                        break;
                    }
                }

                setGamePhase(GamePhase.CardRequest);
                send(new MsgCardRequest(), getFirstPlayer()
                        .getPlayerStateName());

            }
        }
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
     * @param cards Ein Kartenset
     * @return true falls das Kartenset gültig ist, false wenn nicht
     */
    private boolean areValidChoosenCards(Set<Card> cards, String name) {
        int numberOfSwappingCards = 3;

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
        DiscardedCard strongestCard = getPlayedCards().get(0);

        for (int i = 1; i < getPlayedCards().size(); i++) {
            DiscardedCard nextCard = getPlayedCards().get(i);

            if (nextCard.getCard().getColour() == strongestCard.getCard()
                    .getColour()
                    && nextCard.getCard().getValue() > strongestCard.getCard()
                    .getValue()) {
                nextCard = strongestCard;
            }
        }

        PlayerState trickWinner = getPlayerState(strongestCard.getName());
        getGameState().madeTrick(trickWinner);

        updatePlayers();

        boolean noOneHasACard = true;

        for (PlayerState player : getPlayers()) {
            if (!player.getHand().isEmpty()) {
                noOneHasACard = false;
            }
        }

        if (noOneHasACard) {
            setGamePhase(GamePhase.RoundEnd);
            calculateRoundOutcome();
        } else {
            setGamePhase(GamePhase.CardRequest);
            setCurrentPlayer(trickWinner);

            send(new MsgCardRequest(), trickWinner.getPlayerStateName());
        }
    }

    @Override
    protected void calculateRoundOutcome() {
        if (getGamePhase() == GamePhase.RoundEnd
                || getGamePhase() == GamePhase.Ending) {

            for (PlayerState player : getPlayers()) {
                int points = player.getOtherData().getPoints();
                ;

                for (Card card : player.getOtherData().removeTricks()) {
                    if (card.getColour() == Colour.SPADE
                            && card.getValue() == 12) {
                        points = points + 13;

                    } else if (card.getColour() == Colour.HEART) {
                        points = points + 1;
                    }
                }

                if (points == 26) {
                    for (PlayerState playerGetsPoints : getPlayers()) {

                        if (!playerGetsPoints.getPlayerStateName().equals(
                                player.getPlayerStateName())) {
                            playerGetsPoints.getOtherData().setPoints(points);
                        }

                    }
                    break;
                } else {
                    player.getOtherData().setPoints(points);
                }
            }

            updatePlayers();

            for (PlayerState player : getPlayers()) {
                if (player.getOtherData().getPoints() >= ENDING_POINTS) {
                    setGamePhase(GamePhase.Ending);
                    break;
                }
            }

            if (getGamePhase() == GamePhase.Ending) {
                List<String> winners = getWinners();
                broadcast(new MsgGameEnd(winners));
                quitGame();

            } else {
                getGameState().restartDeck(createDeck());

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

        if ((players.size() < MIN_PLAYERS) || (players.size() > MAX_PLAYERS)
                || (players.size() == 0)) {
            throw new IllegalNumberOfPlayersException(
                    "The number of players are: " + players.size());
        }

        setGamePhase(GamePhase.RoundStart);
        startRound();
    }

=======
	/**
	 * Die Minimale Anzahl an Spielern im Spiel Hearts
	 */
	private final static int MIN_PLAYERS = 4;
	/**
	 * Die maximale Anzahl an Spielern im Spiel Wizard
	 */
	private final static int MAX_PLAYERS = 4;
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
    private boolean heartBroken = false;

	/**
	 * Erstellt das Regelwerk zum Spiel Hearts
	 */
	public ServerHearts(GameServer s) {
		super(RULESET, MIN_PLAYERS, MAX_PLAYERS, s);
		swap = new Hashtable<String, Set<Card>>();
	}

    @Override
    protected boolean isValidMove(Card card) {
        setGamePhase(GamePhase.Playing);

        // Jeder Spieler hat bereits eine Karte gespielt
        if (getPlayedCards().size() == getPlayers().size()) {
            return false;
            // Noch kein Spieler hat eine Karte gespielt
        } else if (getPlayedCards().size() == 0) {
            // Die erste Karte jeder Runde muss die Kreu2 sein.
            if (card != HeartsCard.Kreuz2) {
                return false;
            } else {
                return true;
            }
            // Die Spieler befinden sich in der ersten Runde
        } else if (getRoundNumber() == 1) {
            // In der ersten Runde darf kein Herz und nicht die Kreuz2 gespielt werden
            if (card.getColour() == Colour.HEART || card == HeartsCard.PikDame) {
                return false;
            } else {
                return true;
            }
            // Die Spieler befinden sich nicht mehr in der ersten Runde
        } else if (getRoundNumber() > 1) {
            // In dieser Runde wurde noch keine Karte gespielt
            if (getPlayedCards().size() == 0) {
                // Die Karte, die gespielt werden soll, hat die Farbe Herz
                if (card.getColour() == Colour.HEART) {
                    // Wurde Herz schoneinmal gespielt
                    if (heartBroken == true) {
                        return true;
                    } else {
                        return false;
                    }
                    // Die Karte hat nicht die Farbe Herz
                } else {
                    return true;
                }
                // Es wurden schon Karten gespielt
            } else {
                Card firstCard = getPlayedCards().get(0).getCard();
                // Die Karte hat die selbe Farbe, wie die erste ausgespielte Karte der Runde
                if (card.getColour() == firstCard.getColour()) {
                    return true;
                    // Die Karte hat eine andere Farbe als die erste gespielte Karte der Runde
                } else {
                    List<Card> hand = getCurrentPlayer().getHand();
                    // Ist die Karte zu diesem Zeitpunkt schon nicht mehr in der Hand?? --> sonst falsch
                    for (Card handCard : hand) {
                        // Es gibt eine Karte auf der Hand, die die Farbe der erstgepielten Karte hat
                        if (handCard.getColour() == firstCard.getColour()) {
                            return false;
                            // Die Hand enthält keine Karte der Farbe der erstgespielten Karte
                        } else {
                            // Die zu spielende Karte hat die Farbe Herz
                            if (card.getColour() == Colour.HEART) {
                                heartBroken = true;
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }


	@Override
	public void resolveMessage(MsgMultiCards msgMultiCard, String name) {
		Set<Card> cards = msgMultiCard.getCardList();

		if (getGamePhase() != GamePhase.MultipleCardRequest) {
			send(WarningMsg.WrongPhase, name);
			throw new IllegalStateException(
					"Es werden in dieser Phase werden keine Tauschkarten erwartet.");

		} else if (!areValidChoosenCards(cards, name)) {
			send(WarningMsg.WrongTradeCards, name);
			throw new IllegalArgumentException("Die übergebenen Karten vom Spieler "
					+ name + " sind falsch");

		} else {
			getPlayerState(name).getHand().removeAll(cards);
			swap.put(name, cards);

			if (swap.size() == 4) {
				setGamePhase(GamePhase.Playing);

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

				updatePlayers();

				for (PlayerState player : getPlayers()) {
					if (player.getHand().contains(HeartsCard.Kreuz2)) {
						setFirstPlayer(player);
						break;
					}
				}

				setGamePhase(GamePhase.CardRequest);
				send(new MsgCardRequest(), getFirstPlayer()
						.getPlayerStateName());

			}
		}
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
		DiscardedCard strongestCard = getPlayedCards().get(0);

		for (int i = 1; i < getPlayedCards().size(); i++) {
			DiscardedCard nextCard = getPlayedCards().get(i);

			if (nextCard.getCard().getColour() == strongestCard.getCard()
					.getColour()
					&& nextCard.getCard().getValue() > strongestCard.getCard()
							.getValue()) {
				nextCard = strongestCard;
			}
		}

		PlayerState trickWinner = getPlayerState(strongestCard.getName());
		getGameState().madeTrick(trickWinner);

		updatePlayers();

		boolean noOneHasACard = true;

		for (PlayerState player : getPlayers()) {
			if (!player.getHand().isEmpty()) {
				noOneHasACard = false;
			}
		}

		if (noOneHasACard) {
			setGamePhase(GamePhase.RoundEnd);
			calculateRoundOutcome();
		} else {
			setGamePhase(GamePhase.CardRequest);
			setCurrentPlayer(trickWinner);

			send(new MsgCardRequest(), trickWinner.getPlayerStateName());
		}
	}

	@Override
	protected void calculateRoundOutcome() {
		if (getGamePhase() == GamePhase.RoundEnd
				|| getGamePhase() == GamePhase.Ending) {
	
			for (PlayerState player : getPlayers()) {
				int points = player.getOtherData().getPoints();;
				
				for (Card card : player.getOtherData().removeTricks()) {
					if (card.getColour() == Colour.SPADE
							&& card.getValue() == 12) {
						points = points + 13;
	
					} else if (card.getColour() == Colour.HEART) {
						points = points + 1;
					}
				}
	
				if (points == 26) {
					for (PlayerState playerGetsPoints : getPlayers()) {
	
						if (!playerGetsPoints.getPlayerStateName().equals(
								player.getPlayerStateName())) {
							playerGetsPoints.getOtherData().setPoints(points);
						}
	
					}
					break;
				} else {
					player.getOtherData().setPoints(points);
				}
			}
	
			updatePlayers();
	
			for (PlayerState player : getPlayers())  {
				if (player.getOtherData().getPoints() >= ENDING_POINTS) {
					setGamePhase(GamePhase.Ending);
					break;
				}
			}
	
			if (getGamePhase() == GamePhase.Ending) {
				List<String> winners = getWinners();
				broadcast(new MsgGameEnd(winners));
				quitGame();
	
			} else {
				getGameState().restartDeck(createDeck());
	
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

		if ((players.size() < MIN_PLAYERS) || (players.size() > MAX_PLAYERS)
				|| (players.size() == 0)) {
			throw new IllegalNumberOfPlayersException(
					"The number of players are: " + players.size());
		}

		setGamePhase(GamePhase.RoundStart);
		startRound();
	}

>>>>>>> Restliche Methoden + Test
    @Override
    protected void startRound() {
        if (getGamePhase() == GamePhase.RoundStart) {
            getGameState().shuffleDeck();

			/*
             * Verteilt die Karten an Spieler. Wenn false zurück kommt wird ein
			 * neues Deck erstellt und alle Karten im Spiel gelöscht. Wenn
			 * nochmal ein Fehler kommt wirft es eine Exception
			 */
            if (!getGameState().dealCards(getGameState().getRoundNumber())) {
                throw new RulesetException(
                        "Probleme beim Verteilen der Karten!");
            }
            updatePlayers();
            setGamePhase(GamePhase.MultipleCardRequest);
            //sendet nachricht an alle Spieler
            broadcast(new MsgMultiCardsRequest(3));
        } else {
            throw new RulesetException("Das Spiel ist noch nicht am "
                    + "Rundenanfang");
        }
    }

}
