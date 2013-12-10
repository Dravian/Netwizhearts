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
		setGamePhase(GamePhase.SelectionRequest);
		Card trumpCard = getUncoveredCard();
		int valueOfSorcerer = 14;

		if (trumpCard.getValue() == valueOfSorcerer
				&& trumpCard.getRuleset() == RulesetType.Wizard) {
			trumpColour = msgSelection.getSelection();
			getModel().updateTrumpColour(UserMessages.TrumpColour);
		} else {
			throw new IllegalArgumentException(
					"Die vom Server geschickte Kartenfarbe" + "ist falsch.");
		}

	}

	@Override
	public void resolveMessage(MsgUser gameUpdate) {
		setGamePhase(GamePhase.Playing);
		setGameState(gameUpdate.getGameClientUpdate());

		getModel().updateGame();
	}

	@Override
	public boolean isValidMove(Card card) {
		if (getGamePhase() == GamePhase.CardRequest) {
			int valueOfFool = 0;
			int valueOfSorcerer = 14;

			if (getPlayedCards().size() == getOtherPlayerData().size() + 1) {
				getModel().openWarning(WarningMsg.RulesetError);
				throw new RulesetException("Der Ablagestapel ist bereits voll");
			} else if (getPlayedCards().size() == 0) {
				send(new MsgCard(card));
				return true;

			} else if (card.getValue() == valueOfFool) {
				send(new MsgCard(card));
				return true;

			} else if (card.getValue() == valueOfSorcerer) {
				send(new MsgCard(card));
				return true;
			}

			Card firstCard = getPlayedCards().get(0).getCard();

			if (firstCard.getValue() == valueOfSorcerer) {
				send(new MsgCard(card));
				return true;
			}

			/*
			 * Falls die nächste Karte Narr ist, wird die als nächstgespielte
			 * Karte als erste Karte gesetzt, außer es liegen keine Karten mehr
			 * im Ablagestapel
			 */
			for (int i = 1; i < getPlayedCards().size(); i++) {
				if (firstCard.getValue() == valueOfFool) {
					firstCard = getPlayedCards().get(i).getCard();
				} else {
					break;
				}
			}

			if (firstCard.getValue() == valueOfFool) {
				send(new MsgCard(card));
				return true;

			} else if (card.getColour() == firstCard.getColour()) {
				send(new MsgCard(card));
				return true;
			}

			List<Card> hand = getGameState().getOwnHand();

			for (Card handCard : hand) {
				if (handCard.getColour() == firstCard.getColour()
						&& handCard.getValue() != valueOfFool
						&& handCard.getValue() != valueOfSorcerer) {
					setGamePhase(GamePhase.CardRequest);
					getModel().openWarning(WarningMsg.UnvalidMove);
					return false;
				}
			}
			send(new MsgCard(card));
			return true;
		} else {
			getModel().openWarning(WarningMsg.WrongPhase);
			throw new IllegalStateException("Jetzt darf keine Karte gespielt werden.");
		}
	}

	/**
	 * Prüft ob die Anzahl der angesagten Stiche vom Spieler gültig sind
	 * 
	 * @param number
	 *            Die Anzahl der angesagten Stichen
	 * @return true falls die Anzahl der Stiche passen, false wenn nicht
	 */
	public boolean isValidTrickNumber(int number) {
		if (getGamePhase() == GamePhase.TrickRequest) {
			if (number < 0 || number > getRoundNumber()) {
				setGamePhase(GamePhase.TrickRequest);
				getModel().openWarning(WarningMsg.WrongNumber);
				return false;
			} else {
				send(new MsgNumber(number));
				return true;
			}
		} else {
			getModel().openWarning(WarningMsg.WrongPhase);
			throw new IllegalStateException("Jetzt darf keine Zahl angesagt werden.");
		}
	}

	/**
	 * Prüft ob die angesagte Trumpffarbe richtig ist
	 * 
	 * @param colour
	 *            Die angesagte Trumpffarbe
	 * @return true falls die Farbe in Ordnung ist, false wenn nicht
	 */
	public boolean isValidColour(Colour colour) {
		if (getGamePhase() == GamePhase.SelectionRequest) {

			if (colour == Colour.RED) {
				send(new MsgSelection(colour));
				return true;

			} else if (colour == Colour.GREEN) {
				send(new MsgSelection(colour));
				return true;

			} else if (colour == Colour.BLUE) {
				send(new MsgSelection(colour));
				return true;

			} else if (colour == Colour.YELLOW) {
				send(new MsgSelection(colour));
				return true;

			} else {
				return false;
			}
		} else {
			getModel().openWarning(WarningMsg.WrongPhase);
			throw new IllegalStateException("Jetzt darf keine Farbe ausgewählt werden.");
		}
	}

	/**
	 * Holt die Trumpffarbe des Spiels
	 * 
	 * @return Gibt die Trumpffarbe zurück
	 */
	public Colour getTrumpColour() {
		return trumpColour;
	}
	
	/**
	 * Gibt die Farben die es im Spiel gibt zurück
	 * @return Die Farben des Spiels
	 */
	public List<Colour> getColours() {
		return colours;
	}
}