/**
 * 
 */
package Ruleset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Client.ClientModel;
import ComObjects.*;

/**
 * ClientWizard. Diese Klasse bildet das Regelwerk fuer den Client bei einer
 * Partie Wizard
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
	 * Die Farben die es in diesem Spiel gibt
	 */
	private final List<Colour> colours;;

	/**
	 * Erzeugt ein ClientWizard
	 * 
	 * @param client
	 *            Das Model auf dem gespielt wird
	 */
	public ClientWizard(ClientModel client) {
		super(RULESET, client);
		trumpColour = Colour.NONE;
		colours = new ArrayList<Colour>();
		colours.add(Colour.BLUE);
		colours.add(Colour.GREEN);
		colours.add(Colour.RED);
		colours.add(Colour.YELLOW);
		Collections.unmodifiableList(colours);
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt
	 * eine Stichanzahl anzugeben
	 * 
	 * @param msgNumber
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgNumberRequest msgNumber) {
		setGamePhase(GamePhase.TrickRequest);
		getModel().openNumberInputWindow(UserMessages.ChooseNumber);
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt
	 * eine Farbe auszuwaehlen
	 * 
	 * @param msgSelection
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgSelectionRequest msgSelection) {
		setGamePhase(GamePhase.SelectionRequest);
		getModel().openChooseColourWindow(UserMessages.ChooseColour);
	}

	@Override
	public void resolveMessage(MsgSelection msgSelection) {
		trumpColour = msgSelection.getSelection();
		getModel().updateTrumpColour(UserMessages.TrumpColour);
	}

	@Override
	public void resolveMessage(MsgUser gameUpdate) {
		setGamePhase(GamePhase.Playing);
		setGameState(gameUpdate.getGameClientUpdate());
		getModel().updateGame();
	}

	@Override
	protected boolean isValidMove(Card card) {
		int valueOfFool = 0;
		int valueOfSorcerer = 14;

		if (getPlayedCards().size() == 0) {
			return true;

		} else if (card.getValue() == valueOfFool) {
			return true;

		} else if (card.getValue() == valueOfSorcerer) {
			return true;
		}

		Card firstCard = getPlayedCards().get(0).getCard();

		if (firstCard.getValue() == valueOfSorcerer) {
			return true;
		}

		/*
		 * Falls die nächste Karte Narr ist, wird die als nächstgespielte Karte
		 * als erste Karte gesetzt, außer es liegen keine Karten mehr im
		 * Ablagestapel
		 */
		for (int i = 1; i < getPlayedCards().size(); i++) {
			if (firstCard.getValue() == valueOfFool) {
				firstCard = getPlayedCards().get(i).getCard();
			} else {
				break;
			}
		}

		if (firstCard.getValue() == valueOfFool) {
			return true;

		} else if (card.getColour() == firstCard.getColour()) {
			return true;
		}

		List<Card> hand = getGameState().getOwnHand();

		for (Card handCard : hand) {
			if (handCard.getColour() == firstCard.getColour()
					&& handCard.getValue() != valueOfFool
					&& handCard.getValue() != valueOfSorcerer) {
				setGamePhase(GamePhase.CardRequest);
				return false;
			}
		}
		return true;

	}

	@Override
	protected boolean isValidTrickNumber(int number) {
		if (number >= 0 && number <= getGameState().getRoundNumber()) {
			send(new MsgNumber(number));
			return true;
		} else {
			return false;
		}

	}

	@Override
	protected boolean isValidColour(Colour colour) {

		if (colour == Colour.RED) {
			return true;

		} else if (colour == Colour.GREEN) {
			return true;

		} else if (colour == Colour.BLUE) {
			return true;

		} else if (colour == Colour.YELLOW) {
			return true;

		} else {
			return false;
		}

	}

	@Override
	public Colour getTrumpColour() {
		return trumpColour;
	}

	@Override
	public List<Colour> getColours() {
		return colours;
	}

}