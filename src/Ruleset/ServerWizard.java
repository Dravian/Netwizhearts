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
     * Der Ruleset Typ des Spiels
     */
    private final static RulesetType RULESET = RulesetType.Wizard;

    /**
     * Die Anzahl an Runden die gespielt wird. Ist abhaengig von der
     * Spieleranzahl.
     */
    private int playingRounds;
    
    /**
     * Die Trumpffarbe
     */
    private Colour trumpColour;

    /**
     * Erstellt das Regelwerk zum Spiel Wizard
     */
    public ServerWizard(GameServer server) {
        super(RULESET, server);
        trumpColour = Colour.NONE;
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

        } else if (card.getRuleset() != RulesetType.Wizard
                || card.getColour() == Colour.NONE) {
        	send(WarningMsg.WrongCard, name);
            throw new IllegalArgumentException("Die Karte " + card.getValue()
                    + card.getColour() + " gehört nicht zum Spiel");

        } else if (!isValidMove(card)) {
        	setGamePhase(GamePhase.CardRequest);
        	send(WarningMsg.UnvalidMove, name);
            throw new IllegalArgumentException("Der Spieler" + name + "hat die Karte "
                    + card.getValue() + card.getColour()
                    + " gespielt, obwohl sie kein gültiger "
                    + "Zug ist.");

        } else if(!playCard(card)) {
        	send(WarningMsg.WrongCard,name);
        	throw new IllegalArgumentException("Der Spieler" + name + "hat die Karte "
                    + card.getValue() + card.getColour()
                    + " gespielt, obwohl er sie nicht hat.");
        }
        else {
            updatePlayers();      
            
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
        	send(WarningMsg.WrongPhase, name);
            throw new IllegalStateException("Es wird keine Zahl erwartet.");

        } else if (!getCurrentPlayer().getPlayerStateName().equals(name)) {
        	send(WarningMsg.WrongPlayer, name);
            throw new IllegalArgumentException("Es wird keine Zahl von dem Spieler " +
                    name + " erwartet.");

        } else if (!isValidNumber(msgNumber.getNumber())) {
        	setGamePhase(GamePhase.SelectionRequest);
        	send(WarningMsg.WrongNumber, name);
            throw new IllegalArgumentException("Die Zahl " + msgNumber.getNumber() +
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
        	send(WarningMsg.WrongPhase, name);
            throw new IllegalStateException("Es wird keine Trumpffarbe erwartet vom"
                    + "Spieler " + name);

        } else if (!getFirstPlayer().getPlayerStateName().equals(name)) {
        	send(WarningMsg.WrongPlayer, name);
            throw new IllegalArgumentException("Der Spieler " + name + " darf keine "
                    + "Trumpfarbe auswählen.");

        } else {
            Colour colour = msgSelection.getSelection();

            if (!isValidColour(colour)) {
            	setGamePhase(GamePhase.SelectionRequest);
            	send(WarningMsg.WrongColour, name);
                throw new IllegalArgumentException("Die Farbe " + colour
                        + "existiert in Wizard nicht");
            } else {
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
    	
    	if(getPlayedCards().size() == getPlayers().size()) {
    		broadcast(WarningMsg.RulesetError);
        	quitGame();
    		throw new RulesetException(" Der Ablagestapel ist bereits voll.");
    	
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
    	
    	/* Falls die nächste Karte Narr ist, wird die als nächstgespielte
    	* Karte als erste Karte gesetzt, außer es liegen keine Karten mehr
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
     * Überprüft ob eine eingegebene Stichangabe eines Spielers gültig ist
     * und setzt die Stichangabe für den aktuellen Spieler
     * @param number Die Stichangabe
     * @return true falls die Stichangabe gültig ist, false wenn nicht
     */
    private boolean isValidNumber(int number) {
    	if(number < 0 || number > getRoundNumber()) {
    		return false;
    	}else {
    		((WizData) getCurrentPlayer().getOtherData()).setAnnouncedTricks(number);
    		return true;
    	}
    }

    /**
     * Überprüft ob eine eingebene Trumpffarbe eines Spielers gültig ist
     *
     * @param colour Die Trumpffarbe
     * @return true falls die Farbe gültig ist, false wenn nicht
     */
    private boolean isValidColour(Colour colour) {
    	
    	if(colour == Colour.RED || colour == Colour.GREEN || 
    			colour == Colour.BLUE || colour == Colour.YELLOW){
    		trumpColour = colour;
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

        for (int i = 1; i < getPlayedCards().size(); i++) {
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

            } else if (nextCard.getCard().getColour() == trumpColour
                    && (nextCard.getCard().getValue() != valueOfFool)
                    && strongestCard.getCard().getColour() != trumpColour) {
                strongestCard = nextCard;
            }

        }

        PlayerState trickWinner = getPlayerState(strongestCard.getOwnerName());
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
    public void runGame() throws IllegalNumberOfPlayersException {
        List<PlayerState> players = getPlayers();
        int deckSize = WizardCard.values().length;

        if ((players.size() < RULESET.getMinPlayer()) || (players.size() > RULESET.getMaxPlayer())
                || (players.size() == 0)) {
            throw new IllegalNumberOfPlayersException(
                    "Die Anzahl der Spieler ist:  " + players.size());
       
        } else if(getGamePhase() != GamePhase.Start) {
        	throw new IllegalStateException("Das Spiel läuft bereits!");
        
        } else {
            int numberOfRounds = deckSize / players.size();
            setPlayingRounds(numberOfRounds);
            setFirstPlayer(players.get(0));

            setGamePhase(GamePhase.RoundStart);
            startRound();
        }
    }

    @Override
    protected void startRound() {

        if (getGamePhase() == GamePhase.RoundStart) {
            int valueOfSorcerer = 14;
            int valueOfFool = 0;
            
            getGameState().shuffleDeck();

			/*
			 * Verteilt die Karten an Spieler. Wenn false zurück kommt wirft es eine RulesetException
			 */
            if (!getGameState().dealCards(getGameState().getRoundNumber())) {
            	broadcast(WarningMsg.RulesetError);
            	quitGame();
                throw new RulesetException(
                        "Probleme beim Verteilen der Karten!");

            }

            Card uncoveredCard = getGameState().getTopCard();
            getGameState().setUncoveredCard(uncoveredCard);

            updatePlayers();
            
            for(int i = 0; i < getPlayers().size(); i++) {
            	System.out.println(getPlayers().get(i).getPlayerStateName());
            }
            
			/*
			 * Falls ein Zauberer aufgedeckt wird, darf der Spieler vor dem
			 * firstPlayer entscheiden welche Farbe Trumpf ist.
			 */
            if (uncoveredCard.getValue() == valueOfSorcerer) {
            	setGamePhase(GamePhase.SelectionRequest);
                send(new MsgSelectionRequest(), getFirstPlayer().getPlayerStateName());

            } else {
            	
            	if(uncoveredCard.getValue() != valueOfFool) {
            		trumpColour = uncoveredCard.getColour();
            	} else {
            		trumpColour = Colour.NONE;
            	}
            	
            	broadcast(new MsgSelection(trumpColour));
            	
            	setGamePhase(GamePhase.TrickRequest);
                nextPlayer();

                send(new MsgNumberRequest(), getCurrentPlayer().getPlayerStateName());
            }

        } else {
            throw new IllegalStateException("Das Spiel ist noch nicht am "
                    + "Rundenanfang");
        }
    }

    @Override
    protected void calculateRoundOutcome() {
        if (getGamePhase() != GamePhase.RoundEnd
                || getGamePhase() != GamePhase.Ending) {
            List<PlayerState> players = getPlayers();

			/*
			 * Verrechnet die Punkte für jeden Spieler
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
                ((WizData)player.getOtherData()).setAnnouncedTricks(0);
            }


            updatePlayers();
            
            if (getGameState().getRoundNumber() == playingRounds) {
                setGamePhase(GamePhase.Ending);
            }

            if (getGamePhase() == GamePhase.Ending) {
                List<String> winners = getWinners();
                broadcast(new MsgGameEnd(winners));
                quitGame();
           
            } else {
            	trumpColour = Colour.NONE;
            	getGameState().restartDeck(createDeck());
               
            	setCurrentPlayer(getFirstPlayer());             
            	nextPlayer();
            	setFirstPlayer(getCurrentPlayer());

                setGamePhase(GamePhase.RoundStart);
                getGameState().nextRound();
                startRound();
            }

        } else {
            throw new IllegalStateException(
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
