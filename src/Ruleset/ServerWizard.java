package Ruleset;

import java.awt.*;
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

            throw new RulesetException("Der Spieler" + name + "hat die Karte "
                    + card.getValue() + card.getColour()
                    + " gespielt, obwohl sie kein g�ltiger "
                    + "Zug ist. Es muss ein Fehler bei ClientWizard sein.");

        } else {
            updatePlayers();

            if (getGameState().getPlayedCards().size() == getPlayers().size()) {
                calculateTricks();
            } else {
                nextPlayer();
                setGamePhase(GamePhase.CardRequest);
                send(new MsgCardRequest(), getCurrentPlayer().getName());
            }
        }
    }

    @Override
    public void resolveMessage(MsgNumber msgNumber, String name) {
        if (getGamePhase() != GamePhase.TrickRequest) {
            throw new RulesetException("Es wird keine Zahl erwartet.");

        } else if (!getCurrentPlayer().getName().equals(name)) {
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

                send(new MsgCardRequest(), getCurrentPlayer().getName());
            } else {
                nextPlayer();

                send(new MsgNumberRequest(), getCurrentPlayer().getName());
            }
        }

    }

    @Override
    public void resolveMessage(MsgSelection msgSelection, String name) {
        if (getGamePhase() != GamePhase.SelectionRequest) {
            throw new RulesetException("Es wird keine Trumpffarbe erwartet vom"
                    + "Spieler " + name);

        } else if (!getFirstPlayer().getName().equals(name)) {
            throw new RulesetException("Der Spieler " + name + " darf keine "
                    + "Trumpfarbe ausw�hlen.");

        } else {
            Colour colour = msgSelection.getSelection();

            if (!isValidColour(colour)) {
                throw new RulesetException("Die Farbe " + colour
                        + "existiert in Wizard nicht");
            } else {
                /*Soll in isValidColour rein
				 * 
				 */
                ((WizardCard) getTrumpCard()).changeSorcererColour(colour);

                broadcast(new MsgSelection(colour));

                setGamePhase(GamePhase.TrickRequest);
                nextPlayer();
                send(new MsgNumberRequest(), getCurrentPlayer().getName());
            }
        }
    }

    @Override
    protected boolean isValidMove(Card card) {
        boolean isValid = false;
        Colour firstCardColour = getPlayedCards().get(0).getCard().getColour();
        Card firstCard = getPlayedCards().get(0).getCard();
        //erste Karte ist mussFarbe, Narr, Zauberer
        if (card == WizardCard.NarrBlau
                || card == WizardCard.NarrRot
                || card == WizardCard.NarrGelb
                || card == WizardCard.NarrGruen
                || card == WizardCard.ZaubererBlau
                || card == WizardCard.ZaubererGelb
                || card == WizardCard.ZaubererGruen
                || card == WizardCard.ZaubererRot) {
            isValid = true;
        } else {
            if (getPlayedCards().isEmpty()) {
                isValid = true;
            } else if (firstCard == WizardCard.NarrBlau
                    || firstCard == WizardCard.NarrRot
                    || firstCard == WizardCard.NarrGelb
                    || firstCard == WizardCard.NarrGruen) {
                //die nächste Karte bestimmt die Farbe
                if (getPlayedCards().get(1) == null) { // größe der liste < 2???
                    isValid = true;
                } else {
                    //laufe durch die liste auf der suche nach der ersten richtigen karte
                    findColour:
                    for (DiscardedCard card1 : getPlayedCards()) {
                        if (card1.getCard() == WizardCard.ZaubererBlau
                                || card1.getCard() == WizardCard.ZaubererGelb
                                || card1.getCard() == WizardCard.ZaubererGruen
                                || card1.getCard() == WizardCard.ZaubererRot) {
                            isValid = true;
                            break findColour;
                        } else if (card1.getCard() == WizardCard.NarrBlau
                                || card1.getCard() == WizardCard.NarrGruen
                                || card1.getCard() == WizardCard.NarrGelb
                                || card1.getCard() == WizardCard.NarrRot) {
                            //tue nichts und suche weiter
                        } else {
                            //wenn die farbe der zu spielenden entspricht
                            if (card.getColour() == card1.getCard().getColour()) {
                                isValid = true;
                                break findColour;
                            } else {
                                //wenn die farbe von card1 nicht mehr auf der hand ist
                                if (!getCurrentPlayer().getHand().contains(card1.getCard().getColour())) {
                                    isValid = true;
                                    break findColour;
                                }
                            }
                        }
                    }
                }
            } else if (firstCard == WizardCard.ZaubererBlau
                    || firstCard == WizardCard.ZaubererGelb
                    || firstCard == WizardCard.ZaubererGruen
                    || firstCard == WizardCard.ZaubererRot) {
                //alles darf gelegt werden
                isValid = true;
            } else {
                if (firstCardColour == Colour.BLUE) {
                    // wenn karte blau kein problem
                    if (card.getColour() == Colour.BLUE) {
                        isValid = true;
                    // wenn karte nicht blau dann nur wenn keine blauen mehr auf der hand
                    } else {
                        if (!getCurrentPlayer().getHand().contains(Colour.BLUE)) {
                            isValid = true;
                        }
                    }
                } else if (firstCardColour == Colour.RED) {
                    // wenn karte rot kein problem
                    if (card.getColour() == Colour.RED) {
                        isValid = true;
                        // wenn karte nicht rot dann nur wenn keine roten mehr auf der hand
                    } else {
                        if (!getCurrentPlayer().getHand().contains(Colour.RED)) {
                            isValid = true;
                        }
                    }
                } else if (firstCardColour == Colour.YELLOW) {
                    // wenn karte gelb kein problem
                    if (card.getColour() == Colour.YELLOW) {
                        isValid = true;
                        // wenn karte nicht gelb dann nur wenn keine gelben mehr auf der hand
                    } else {
                        if (!getCurrentPlayer().getHand().contains(Colour.YELLOW)) {
                            isValid = true;
                        }
                    }
                } else if (firstCardColour == Colour.GREEN) {
                    // wenn karte grün kein problem
                    if (card.getColour() == Colour.GREEN) {
                        isValid = true;
                        // wenn karte nicht grün dann nur wenn keine grünen mehr auf der hand
                    } else {
                        if (!getCurrentPlayer().getHand().contains(Colour.GREEN) {
                            isValid = true;
                        }
                    }
                }
            }
        }
        return isValid;
    }

    /**
     * Ueberprueft ob eine eingegebene Stichangabe eines Spielers gueltig ist
     * und setzt die Stichangabe f�r den aktuellen Spieler
     *
     * @param number Die Stichangabe
     * @return true falls die Stichangabe g�ltig ist, false wenn nicht
     */
    private boolean isValidNumber(int number) {
        return true;
    }

    /**
     * Ueberprueft ob eine eingebene Trumpffarbe eines Spielers gueltig ist
     *
     * @param colour Die Trumpffarbe
     * @return true falls die Farbe gueltig ist, false wenn nicht
     */
    private boolean isValidColour(Colour colour) {
        return true;
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

            send(new MsgCardRequest(), trickWinner.getName());
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
			 * Verteilt die Karten an Spieler. Wenn false zur�ck kommt wird ein
			 * neues Deck erstellt und alle Karten im Spiel gel�scht. Wenn
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

                send(new MsgSelectionRequest(), getFirstPlayer().getName());

            } else {
                setGamePhase(GamePhase.TrickRequest);
                nextPlayer();

                send(new MsgNumberRequest(), getCurrentPlayer().getName());
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
                // QuitGame
            } else {
                setCurrentPlayer(getFirstPlayer());

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
                leadingPlayers.add(player.getName());
                maxPoints = player.getOtherData().getPoints();

            } else if (player.getOtherData().getPoints() == maxPoints) {
                leadingPlayers.add(player.getName());
            }
        }

        return leadingPlayers;
    }
}
