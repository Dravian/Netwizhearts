package ComObjects;

import Ruleset.Card;
import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/**
 * MsgCard.
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Sie beinhaltet die ausgespielte Karte eines Spielers.
 */
public class MsgCard implements RulesetMessage, Serializable {

	private Card card;

    /**
     * Dies ist der Kontruktor fuer eine neue MsgCard-Nachricht.
     * Diese enthaelt die Information, welche Karte von einem
     * Spieler gespielt wurde.
     * @param card ist die Karte.
     */
    public MsgCard(Card card) {
        this.card = card;
    }

    /**
     * Diese Methode gibt die ausgespielte Karte des Spielers zurueck.
     * @return die Karte.
     */
    public Card getCard() {
        return card;
    }

    @Override
    public void visit(ServerRuleset serverRuleset, String name) {
        serverRuleset.resolveMessage(this, name);
    }

    @Override
    public void visit(ClientRuleset clientRuleset) {
        clientRuleset.resolveMessage(this);
    }
}