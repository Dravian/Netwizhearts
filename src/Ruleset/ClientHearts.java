/**
 * 
 */
package Ruleset;

import java.util.List;
import java.util.Set;

import Client.ClientModel;
import ComObjects.*;

/**
 * ClientHearts. Diese Klasse bildet das Regelwerk für den Clientmodel bei einer
 * Partie Hearts
 */
public class ClientHearts extends ClientRuleset {
	/**
	 * Der RulesetTyp des Spiels
	 */
	private static final RulesetType RULESET = RulesetType.Hearts;

	/**
	 * Gibt an, ob Herz schon ausgespielt werden darf.
	 */
	private boolean heartBroken = false;

	/**
	 * Erzeugt ein ClientHearts
	 * 
	 * @param client
	 *            Das Model auf dem gespielt wird
	 */
	public ClientHearts(ClientModel client) {
		super(RulesetType.Hearts, client);
	}

	@Override
	protected boolean isValidMove(Card card) {
		// Erste Runde
		if (getGameState().getOwnHand().size() == 13) {

			// Noch kein Spieler hat eine Karte gespielt
			if (getPlayedCards().size() == 0) {

				// Die erste Karte jeder Runde muss die Kreu2 sein.
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

					// Wenn der Spieler nur Herz auf der Hand hat, oder nur
					// Herz und die PikDame
					List<Card> hand = getGameState().getOwnHand();

					for (Card handCard : hand) {

						// Wenn in der Spielerhand eine Karte weder Herz
						// noch PikDame ist
						if (handCard != HeartsCard.PikDame
								&& handCard.getColour() != Colour.HEART) {
							return false;
						}
					}
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
						for (Card handCard : getGameState().getOwnHand()) {
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

			List<Card> hand = getGameState().getOwnHand();
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
		} else {
			// Die Karte hat die selbe Farbe, wie die erste ausgespielte Karte
			// der Runde
			return true;
		}
	}

	/**
	 * Verarbeitet die RulesetMessage dass der Server von dem Spieler verlangt
	 * mehrere Karten anzugeben
	 * 
	 * @param msgMultiCardsRequest
	 *            Die Nachricht vom Server
	 */
	public void resolveMessage(MsgMultiCardsRequest msgMultiCardsRequest) {
		setGamePhase(GamePhase.MultipleCardRequest);
		getModel().openChooseCardsWindow(UserMessages.ChooseCards);
	}

	@Override
	public void resolveMessage(MsgUser gameUpdate) {
		setGamePhase(GamePhase.Playing);
		setGameState(gameUpdate.getGameClientUpdate());

		if (getGameState().getOwnHand().size() == 13) {
			heartBroken = false;
		}
		getModel().updateGame();
	}

	@Override
	public void resolveMessage(MsgBoolean msgBool) {
		heartBroken = msgBool.getBool();
	}

	@Override
	protected boolean areValidChoosenCards(Set<Card> cards) {
		if (cards.size() == 3) {
			for (Card card : cards) {
				if (card.getRuleset() != RulesetType.Hearts) {
					return false;
				}
			}
			if (!getGameState().getOwnHand().containsAll(cards)) {
				return false;
			}

			return true;
		}
		return false;

	}
	

}