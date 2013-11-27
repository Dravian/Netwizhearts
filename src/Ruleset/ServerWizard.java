package Ruleset;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Server.GameServer;
import ComObjects.*;

/**
 * ServerWizard. Diese Klasse erstellt das Regelwerk zum Spiel Wizard. Sie
 * enthaelt zudem weitere Methoden, welche f�r das Spiel Wizard spezifisch
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

    /**
     * Setzt die Anzahl an Runden die es in diesem Spiel gibt
     *
     * @param rounds Die Anzahl an Runden
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

        if (getGamePhase() != GamePhase.CardRequest) {
            throw new RulesetException(
                    "Es wird in dieser Phase keine Karte erwartet "
                            + "vom Spieler " + name);

        } else if (getPlayerState(name) != getCurrentPlayer()) {
            throw new IllegalArgumentException("Der Spieler " + name
                    + " ist nicht am Zug!");

        } else if (card.getRuleset() != RulesetType.Wizard
                || card.getColour() == Colour.NONE) {
            throw new IllegalArgumentException("Die Karte " + card.getValue()
                    + card.getColour() + " geh�rt nicht zum Spiel");

        } else if (!isValidMove(card)) {
        	setGamePhase(GamePhase.CardRequest);
            throw new RulesetException("Der Spieler" + name + "hat die Karte "
                    + card.getValue() + card.getColour()
                    + " gespielt, obwohl sie kein gültiger "
                    + "Zug ist. Es muss ein Fehler bei ClientWizard sein.");

        } else {
            updatePlayers();
            setGamePhase(GamePhase.Playing);
            playCard(card);
            
            if (getGameState().getPlayedCards().size() == getPlayers().size()) {
                calculateTricks();
            } else {
                nextPlayer();
                setGamePhase(GamePhase.CardRequest);
                send(new MsgCardRequest(), getCurrentPlayer().getPlayerStateName());
            }
        }
    }

    @Override
    public void resolveMessage(MsgNumber msgNumber, String name) {
        if (getGamePhase() != GamePhase.TrickRequest) {
            throw new RulesetException("Es wird keine Zahl erwartet.");

        } else if (!getCurrentPlayer().getPlayerStateName().equals(name)) {
            throw new RulesetException("Es wird keine Zahl von dem Spieler " +
                    name + " erwartet.");

        } else if (!isValidNumber(msgNumber.getNumber())) {
            throw new RulesetException("Die Zahl " + msgNumber.getNumber() +
                    " vom Spieler " + name + " ist nicht erlaubt.");

        } else {
            updatePlayers();

            if (getCurrentPlayer() == getFirstPlayer()) {
                nextPlayer();
                setGamePhase(GamePhase.CardRequest);

                send(new MsgCardRequest(), getCurrentPlayer().getPlayerStateName());
            } else {
                nextPlayer();

                send(new MsgNumberRequest(), getCurrentPlayer().getPlayerStateName());
            }
        }

    }

    @Override
    public void resolveMessage(MsgSelection msgSelection, String name) {
        if (getGamePhase() != GamePhase.SelectionRequest) {
            throw new RulesetException("Es wird keine Trumpffarbe erwartet vom"
                    + "Spieler " + name);

        } else if (!getFirstPlayer().getPlayerStateName().equals(name)) {
            throw new RulesetException("Der Spieler " + name + " darf keine "
                    + "Trumpfarbe auswählen.");

        } else {
            Colour colour = msgSelection.getSelection();

            if (!isValidColour(colour)) {
                throw new RulesetException("Die Farbe " + colour
                        + "existiert in Wizard nicht");
            } else {
                ((WizardCard) getTrumpCard()).changeSorcererColour(colour);

                broadcast(new MsgSelection(colour));

                setGamePhase(GamePhase.TrickRequest);
                nextPlayer();
                send(new MsgNumberRequest(), getCurrentPlayer().getPlayerStateName());
            }
        }
    }

    @Override
    protected boolean isValidMove(Card card) {
        int valueOfFool = 0;
    	int valueOfSorcerer = 14;
    	setGamePhase(GamePhase.Playing);
    	
    	if(getPlayedCards().size() == getPlayers().size()) {
    		return false;
    	
    	}else if(getPlayedCards().size() == 0) {
    		return true;
    	
    	} else  if(card.getValue() == valueOfFool) {
    		return true;
    	
    	} else if(card.getValue() == valueOfSorcerer) {
    		return true;
    	}
    	
    	Card firstCard = getPlayedCards().get(0).getCard();
    	
    	if(firstCard.getValue() == valueOfSorcerer) {
    		return true;
    	}
    	
    	/* Falls die n�chste Karte Narr ist, wird die als n�chstgespielte
    	* Karte als erste Karte gesetzt, au�er es liegen keine Karten mehr
    	* im Ablagestapel
    	*/
    	for(int i = 1; i < getPlayedCards().size(); i++) {
    		if(firstCard.getValue() == valueOfFool) {
    			firstCard = getPlayedCards().get(i).getCard();
    		} else {
    			break;
    		}  		
    	}
    	
    	if(firstCard.getValue() == valueOfFool) {
    		return true;
    	
    	} else if(card.getColour() == firstCard.getColour()) {
    		return true;
    	}
    	
    	List<Card> hand = getCurrentPlayer().getHand();
    	
    	for(Card handCard : hand) {
    		if(handCard.getColour() == firstCard.getColour() && 
    				handCard.getValue() != valueOfFool &&
    				handCard.getValue() != valueOfSorcerer) {
    			return false;
    		}
    	}
    	
    	return true;
    }

    /**
     * Ueberprueft ob eine eingegebene Stichangabe eines Spielers gueltig ist
     * und setzt die Stichangabe f�r den aktuellen Spieler
     *
     * @param number Die Stichangabe
     * @return true falls die Stichangabe g�ltig ist, false wenn nicht
     */
    private boolean isValidNumber(int number) {
    	if(number < 0 || number > getRoundNumber()) {
    		return false;
    	}else {
    		return true;
    	}
    }

    /**
     * Ueberprueft ob eine eingebene Trumpffarbe eines Spielers gueltig ist
     *
     * @param colour Die Trumpffarbe
     * @return true falls die Farbe gueltig ist, false wenn nicht
     */
    private boolean isValidColour(Colour colour) {
    	if(colour == Colour.RED){
    		return true;
    	
    	} else if(colour == Colour.GREEN) {
    		return true;
    	
    	} else if(colour == Colour.BLUE) {
    		return true;
    	
    	} else if(colour == Colour.YELLOW) {
    		return true;
    	
    	} else {
    		return false;
    	}
    }

    @Override
    protected void calculateTricks() {
        int valueOfFool = 0;
        int valueOfSorcerer = 14;
        DiscardedCard strongestCard = getPlayedCards().get(0);

        for (int i = 0; i < getPlayedCards().size(); i++) {
            DiscardedCard nextCard = getPlayedCards().get(i);

            if (strongestCard.getCard().getValue() == valueOfSorcerer) {
                break;

            } else if (nextCard.getCard().getValue() == valueOfSorcerer) {
                strongestCard = nextCard;
                break;

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
        startRound();
    }

    @Override
    protected void startRound() {

        if (getGamePhase() == GamePhase.RoundStart) {
            int valueOfSorcerer = 14;
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

            Card trumpCard = getGameState().getTopCard();
            getGameState().setTrumpCard(trumpCard);

            updatePlayers();

			/*
			 * Falls ein Zauberer aufgedeckt wird, darf der Spieler vor dem
			 * firstPlayer entscheiden welche Farbe Trumpf ist.
			 */
            if (trumpCard.getValue() == valueOfSorcerer) {
                setGamePhase(GamePhase.SelectionRequest);

                send(new MsgSelectionRequest(), getFirstPlayer().getPlayerStateName());

            } else {
                setGamePhase(GamePhase.TrickRequest);
                nextPlayer();

                send(new MsgNumberRequest(), getCurrentPlayer().getPlayerStateName());
            }

        } else {
            throw new RulesetException("Das Spiel ist noch nicht am "
                    + "Rundenanfang");
        }
    }

    @Override
    protected void calculateRoundOutcome() {
        if (getGamePhase() != GamePhase.RoundEnd
                || getGamePhase() != GamePhase.Ending) {
            List<PlayerState> players = getPlayers();

			/*
			 * Verrechnet die Punkte f�r jeden Spieler
			 */
            for (PlayerState player : players) {
                int announcedTricks = ((WizData) player.getOtherData())
                        .getAnnouncedTricks();

                int madeTricks = ((WizData) player.getOtherData())
                        .getNumberOfTricks();

                int points = player.getOtherData().getPoints();

                if (announcedTricks == madeTricks) {
                    points = points + 20 + 10 * madeTricks;

                } else if (announcedTricks < madeTricks) {
                    points = points - 10 * (madeTricks - announcedTricks);

                } else {
                    points = points - 10 * (announcedTricks - madeTricks);
                }

                player.getOtherData().setPoints(points);
            }

            getGameState().restartDeck(createDeck());

            updatePlayers();


            if (getGameState().getRoundNumber() == playingRounds) {
                setGamePhase(GamePhase.Ending);
            }

            if (getGamePhase() == GamePhase.Ending) {
                List<String> winners = getWinners();
                broadcast(new MsgGameEnd(winners));
                quitGame();
            } else {
                setCurrentPlayer(getFirstPlayer());
                nextPlayer();
                setFirstPlayer(getCurrentPlayer());

                setGamePhase(GamePhase.RoundStart);
                startRound();
            }

        } else {
            throw new RulesetException(
                    "Das Spiel ist noch nicht am Rundenende.");
        }
    }

    @Override
    protected List<String> getWinners() {
        int maxPoints = -99999;
        List<String> leadingPlayers = new ArrayList<String>();

        for (PlayerState player : getPlayers()) {
            if (player.getOtherData().getPoints() > maxPoints) {
                leadingPlayers = new ArrayList<String>();
                leadingPlayers.add(player.getPlayerStateName());
                maxPoints = player.getOtherData().getPoints();

            } else if (player.getOtherData().getPoints() == maxPoints) {
                leadingPlayers.add(player.getPlayerStateName());
            }
        }

        return leadingPlayers;
    }
}
