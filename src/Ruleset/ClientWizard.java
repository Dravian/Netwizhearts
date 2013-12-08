/**
 * 
 */
package Ruleset;

import java.util.ArrayList;
import java.util.List;

import Client.ClientModel;
import ComObjects.*;

/** 
 * ClientWizard. Diese Klasse bildet das Regelwerk fuer den Client bei einer Partie Wizard
 */
public class ClientWizard extends ClientRuleset {
	/**
	 * Der RulesetTyp des Spiels
	 */
	private static final RulesetType RULESET = RulesetType.Wizard;
	
	/**
	 * Die Trumpffarbe
	 */
	private Colour trumpColour;
	
	/**
	 * Erzeugt ein ClientWizard
	 * @param client Das Model auf dem gespielt wird
	 */
	public ClientWizard(ClientModel client) {
		super(RULESET, client);
	}

	@Override
	public void resolveMessage(MsgCardRequest card) {
		setGamePhase(GamePhase.CardRequest);
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Stichanzahl anzugeben
	 * @param msgNumber Die Nachricht vom Server
	 */
	public void resolveMessage(MsgNumberRequest msgNumber) {
		setGamePhase(GamePhase.TrickRequest);
		getModel().openInputNumber("A Zahl eingeben");
	}
	
	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt eine Farbe auszuwaehlen
	 * @param msgSelection Die Nachricht vom Server
	 */
	public void resolveMessage(MsgSelectionRequest msgSelection) {
		setGamePhase(GamePhase.SelectionRequest);
		List<Object> colours = new ArrayList<Object>();
		colours.add(Colour.RED);
		colours.add(Colour.BLUE);
		colours.add(Colour.GREEN);
		colours.add(Colour.YELLOW);
		
		getModel().openChooseItem(colours, "Choose one of thoose Colours");
	}
	
	@Override
	public void resolveMessage(MsgSelection msgSelection) {
		Card trumpCard = getUncoveredCard();
		int valueOfSorcerer = 14;
		
		if(trumpCard.getValue() == valueOfSorcerer && 
				trumpCard.getRuleset() == RulesetType.Wizard) {
			trumpColour = msgSelection.getSelection();
		} else {
			throw new IllegalArgumentException("Die vom Server geschickte Kartenfarbe" +
					"ist falsch.");
		}
		
		//TODO Update model
	}
	
	@Override
	public void resolveMessage(MsgUser gameUpdate) {
		int valueOfSorcerer = 14;
		int valueOfFool = 0;
		
		setGameState(gameUpdate.getGameClientUpdate());
		
		if(getGameState().getUncoveredCard().getValue() == valueOfSorcerer ||
				getGameState().getUncoveredCard().getValue() == valueOfFool) {
			trumpColour = Colour.NONE;
		} else {
			trumpColour = getGameState().getUncoveredCard().getColour();
		}
		
		//TODO Update model
	}
	
	@Override
	public boolean isValidMove(Card card) {
		int valueOfFool = 0;
		int valueOfSorcerer = 14;
		setGamePhase(GamePhase.Playing);
		
		if(getPlayedCards().size() == getOtherPlayerData().size() + 1) {
			return false;
		
		}else if(getPlayedCards().size() == 0) {
			send(new MsgCard(card));
			return true;
		
		} else  if(card.getValue() == valueOfFool) {
			send(new MsgCard(card));
			return true;
		
		} else if(card.getValue() == valueOfSorcerer) {
			send(new MsgCard(card));
			return true;
		}
		
		Card firstCard = getPlayedCards().get(0).getCard();
		
		if(firstCard.getValue() == valueOfSorcerer) {
			send(new MsgCard(card));
			return true;
		}
		
		/* 
		 * Falls die nächste Karte Narr ist, wird die als nächstgespielte
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
			send(new MsgCard(card));
			return true;
		
		} else if(card.getColour() == firstCard.getColour()) {
			send(new MsgCard(card));
			return true;
		}
		
		List<Card> hand = getCurrentPlayer().getHand();
		
		for(Card handCard : hand) {
			if(handCard.getColour() == firstCard.getColour() && 
					handCard.getValue() != valueOfFool &&
					handCard.getValue() != valueOfSorcerer) {
				setGamePhase(GamePhase.CardRequest);
				return false;
			}
		}
		send(new MsgCard(card));
		return true;
	}

	/**
	 * Prüft ob die Anzahl der angesagten Stiche vom Spieler gültig sind
	 * @param number Die Anzahl der angesagten Stichen
	 * @return true falls die Anzahl der Stiche passen, false wenn nicht
	 */
	public boolean isValidTrickNumber(int number) {
		setGamePhase(GamePhase.Playing);
		if(number < 0 || number > getRoundNumber()) {
			setGamePhase(GamePhase.TrickRequest);
			return false;
		} else {
			send(new MsgNumber(number));
			return true;
		}
	}
	
	/**
	 * Prüft ob die angesagte Trumpffarbe richtig ist
	 * @param colour Die angesagte Trumpffarbe
	 * @return true falls die Farbe in Ordnung ist, false wenn nicht
	 */
	public boolean isValidColour(Colour colour) {
		setGamePhase(GamePhase.Playing);
		
		if(colour == Colour.RED){
			send(new MsgSelection(colour));
    		return true;
    	
    	} else if(colour == Colour.GREEN) {
    		send(new MsgSelection(colour));
    		return true;
    	
    	} else if(colour == Colour.BLUE) {
    		send(new MsgSelection(colour));
    		return true;
    	
    	} else if(colour == Colour.YELLOW) {
    		send(new MsgSelection(colour));
    		return true;
    	
    	} else {
    		setGamePhase(GamePhase.SelectionRequest);
    		return false;
    	}
	}
	
	/**
	 * Holt die Trumpffarbe des Spiels
	 * @return Gibt die Trumpffarbe zurück
	 */
	public Colour getTrumpColour() {
		return trumpColour;
	}
}