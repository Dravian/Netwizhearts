package Ruleset;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Server.GameServer;
import ComObjects.*;

/**
 * ServerHearts. Diese Klasse erstellt das Regelwerk zum Spiel Hearts. Sie enthaelt zudem weitere Methoden, 
 * welche f�r das Spiel Hearts spezifisch benoetigt werden, wie die Regelung zum Tausch von Karten 
 * und die Berechnung der Stichpunkten.
 */
public class ServerHearts extends ServerRuleset {
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
	private Map<String,Set<Card>> swap;
	
	/**
	 * Erstellt das Regelwerk zum Spiel Hearts
	 */
	public ServerHearts(GameServer s) {
		super(RULESET, MIN_PLAYERS, MAX_PLAYERS, s);
		swap = new Hashtable<String,Set<Card>>();
	}
	
	@Override
	protected boolean isValidMove(Card card) {
		boolean isValid = false;
		return isValid;
	}
	
	/**
	 * Holt die Anzahl der Punkte die ein Spieler haben kann ab der, das Spiel vorbei ist
	 * @return Anzahl der maximalen Punkte
	 */
	protected int getEndingPoints() {
		return ENDING_POINTS;
	}
		
	@Override
	public void resolveMessage(MsgMultiCards msgMultiCard, String name) {
		Set<Card> cards = msgMultiCard.getCardList();
		
		 if (getGamePhase() != GamePhase.MultipleCardRequest) {
	            throw new RulesetException(
	                    "Es werden in dieser Phase werden keine Tauschkarten erwartet.");

	     } else if(!areValidChoosenCards(cards,name)) {
	        	throw new RulesetException("Die übergebenen Karten vom Spieler "+ name +" sind falsch");
	        
	     }  else {
	    	getPlayerState(name).getHand().removeAll(cards);
	    	swap.put(name, cards);
        	
        	if(swap.size() == 4) {
        		setGamePhase(GamePhase.Playing);
        		
        		int roundNumber = getRoundNumber();
        		
        		if(roundNumber % 4 == 1) {
        			swapLeft();
        		
        		} else if(roundNumber % 4 == 2) {
        			swapAcross();
        		
        		} else if(roundNumber % 4 == 3) {
        			swapRight();
        		} else {
        			throw new RulesetException("Beim Tauschen ein Fehler passiert.");
        		}
        		
        		updatePlayers(); 		
        		
        		for(PlayerState player : getPlayers()) {
        			if(player.getHand().contains(HeartsCard.Kreuz2)) {
        				setFirstPlayer(player);
        				break;
        			}
        		}
        		
        		setGamePhase(GamePhase.CardRequest);
        		send(new MsgCardRequest(), getFirstPlayer().getPlayerStateName());
        		
        	}
         }
	}
	
	/**
	 * Tauscht bei jedem Spieler die Karten nach links
	 */
	private void swapLeft() {
		
		for(int i = 0; i < getPlayers().size(); i++) {
			PlayerState giver = getPlayers().get(i);
			PlayerState taker;
			
			if(i == getPlayers().size() - 1) {
				taker = getPlayers().get(0);
			} else {
				taker = getPlayers().get(i+1);
			}
			
			taker.getHand().addAll(swap.get(giver.getPlayerStateName()));
		}
		
		swap = new Hashtable<String,Set<Card>>();
	}
	
	
	/**
	 * Tauscht bei jedem Spieler die Karten gegenüber
	 */
	private void swapAcross() {
		for(int i = 0; i < getPlayers().size(); i++) {
			PlayerState giver = getPlayers().get(i);
			PlayerState taker;
			
			if(i == getPlayers().size() - 2) {
				taker = getPlayers().get(0);
			} else if(i == getPlayers().size() - 1) {
				taker = getPlayers().get(1);
			} else {
				taker = getPlayers().get(i+2);
			}
			
			taker.getHand().addAll(swap.get(giver.getPlayerStateName()));
		}
		
		swap = new Hashtable<String,Set<Card>>();
	}
	
	/**
	 * Tauscht bei jedem Spieler die Karten nach rechts
	 */
	private void swapRight() {
		for(int i = 0; i < getPlayers().size(); i++) {
			PlayerState giver = getPlayers().get(i);
			PlayerState taker;
			
			if(i == 0) {
				taker = getPlayers().get(getPlayers().size() - 1);
			} else {
				taker = getPlayers().get(i - 1);
			}
			
			taker.getHand().addAll(swap.get(giver.getPlayerStateName()));
		}
		
		swap = new Hashtable<String,Set<Card>>();
	}
	
	/**
	 * Ueberprueft ob eine uebergebenes Kartenset von einem Spieler gültig ist
	 * @param cards Ein Kartenset
	 * @return true falls das Kartenset gueltig ist, false wenn nicht
	 */
	private boolean areValidChoosenCards(Set<Card> cards, String name) {
		int numberOfSwappingCards = 3;
		
		if(cards.size() != numberOfSwappingCards) {
			return false;
		
		} else if(swap.containsKey(name)) {
			return false;
		
		} else if(!getPlayerState(name).getHand().containsAll(cards)) {
			return false;
		
		} else {
			return true;
		}
	}

	@Override
	protected void calculateRoundOutcome() {
		if(getGamePhase() == GamePhase.RoundEnd  ||
				getGamePhase() == GamePhase.Ending) {
			
			for(PlayerState player : getPlayers()) {
				int points = 0;
				
				for(Card card : player.getHand()) {
					if(card.getColour() == Colour.SPADE && card.getValue() == 12) {
						points = points + 13;
					
					} else if(card.getColour() == Colour.HEART) {
						points = points + 1;
					}
				}
				
				if(points == 26) {
					for(PlayerState playerGetsPoints : getPlayers()) {
						
						if(!playerGetsPoints.getPlayerStateName().equals(player.getPlayerStateName())) {
							playerGetsPoints.getOtherData().setPoints(points);
						}
				
					}
					break;
				} else {
					player.getOtherData().setPoints(points);
				}
			}
			
			updatePlayers();
			
			for(PlayerState player : getPlayers()) {
				if(player.getOtherData().getPoints() >= 100) {
					setGamePhase(GamePhase.Ending);
					break;
				}
			}
			
			if(getGamePhase() == GamePhase.Ending) {
				List<String> winners = getWinners();
                broadcast(new MsgGameEnd(winners));
                quitGame();
			
			} else {
				getGameState().restartDeck(createDeck());

                setGamePhase(GamePhase.RoundStart);
                getGameState().nextRound();
                startRound();
			}
		
		} else {
			throw new RulesetException(
                    "Das Spiel ist noch nicht am Rundenende.");
		}
	}

	@Override
	protected void calculateTricks() {
		DiscardedCard strongestCard = getPlayedCards().get(0);
		
		for(int i = 1; i < getPlayedCards().size(); i++) {
			DiscardedCard nextCard = getPlayedCards().get(i);
			
			if(nextCard.getCard().getColour() == strongestCard.getCard().getColour() &&
					nextCard.getCard().getValue() > strongestCard.getCard().getValue()) {
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
	protected List<String> getWinners() {
		return null;
	}

	@Override
	public void runGame() throws IllegalNumberOfPlayersException{
		  List<PlayerState> players = getPlayers();
	        int deckSize = HeartsCard.values().length;

	        if ((players.size() < MIN_PLAYERS) || (players.size() > MAX_PLAYERS)
	                || (players.size() == 0)) {
	            throw new IllegalNumberOfPlayersException(
	                    "The number of players are: " + players.size());
	        } 

	        setGamePhase(GamePhase.RoundStart);
	        startRound();
	}

	
	@Override
	protected void startRound() {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

	
}
