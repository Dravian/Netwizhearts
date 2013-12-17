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
	public boolean isValidMove(Card card) {

		if (getGamePhase() == GamePhase.CardRequest) {
			// Jeder Spieler hat bereits eine Karte gespielt
			if (getPlayedCards().size() == getOtherPlayerData().size() + 1) {
				getModel().openWarning(WarningMsg.RulesetError);
				throw new RulesetException("Der Ablagestapel ist bereits voll.");

				// Die Spieler befinden sich in der ersten Runde
			} else if (getGameState().getOwnHand().size() == 13) {

				// Noch kein Spieler hat eine Karte gespielt
				if (getPlayedCards().size() == 0) {

					// Die erste Karte jeder Runde muss die Kreu2 sein.
					if (card != HeartsCard.Kreuz2) {
						getModel().openWarning(WarningMsg.UnvalidMove);
						getModel().announceTurn(UserMessages.PlayCard);
						return false;
					} else {
						send(new MsgCard(card));
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
							if (handCard != HeartsCard.PikDame && handCard.getColour() != Colour.HEART) {
								getModel().openWarning(WarningMsg.UnvalidMove);
								getModel().announceTurn(UserMessages.PlayCard);
								return false;
							}
						}
						send(new MsgCard(card));
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
						/*if (heartBroken == true) {
							send(new MsgCard(card));
							return true;
						} 
						else {
							for (Card handCard : getGameState().getOwnHand()) {
								if (handCard.getColour() != Colour.HEART){
									getModel().openWarning(WarningMsg.UnvalidMove);
									getModel().announceTurn(UserMessages.PlayCard);
									return false;
								}
							}
							heartBroken = true;
							send(new MsgCard(card));
							return true;
						}*/
						send(new MsgCard(card));
						return true;
						// Die Karte hat nicht die Farbe Herz
					} else {
						send(new MsgCard(card));
						return true;
					}
					// Es wurden schon Karten gespielt
				} else {
					return testOtherHandCards(card);
				}
			}
		} else {
			getModel().openWarning(WarningMsg.WrongPhase);
			throw new IllegalStateException("Jetzt darf keine Karte gespielt werden.");
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
					getModel().openWarning(WarningMsg.UnvalidMove);
					getModel().announceTurn(UserMessages.PlayCard);
					return false;
				} 
			}

			// Die zu spielende Karte hat die Farbe Herz
			if (card.getColour() == Colour.HEART) {
				heartBroken = true;
			}
			send(new MsgCard(card));
			return true;
		} else {
			// Die Karte hat die selbe Farbe, wie die erste ausgespielte Karte
			// der Runde
			send(new MsgCard(card));
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
	public boolean areValidChoosenCards(Set<Card> cards) {
		if (getGamePhase() == GamePhase.MultipleCardRequest) {
			if (cards.size() == 3) {
				for (Card card : cards) {
					if (card.getRuleset() != RulesetType.Hearts) {
						getModel().openWarning(WarningMsg.WrongTradeCards);
						getModel().openChooseCardsWindow(UserMessages.ChooseCards);
						return false;
					}
				}
				if(!getGameState().getOwnHand().containsAll(cards)) {
					getModel().openWarning(WarningMsg.WrongTradeCards);
					getModel().openChooseCardsWindow(UserMessages.ChooseCards);
					return false;
				}
				
				send(new MsgMultiCards(cards));
				return true;
			}
			getModel().openWarning(WarningMsg.WrongTradeCards);
			getModel().openChooseCardsWindow(UserMessages.ChooseCards);
			return false;
		} else {
			getModel().openWarning(WarningMsg.WrongPhase);
			throw new IllegalStateException("Jetzt werden keine Karten getauscht");
		}

	}

}