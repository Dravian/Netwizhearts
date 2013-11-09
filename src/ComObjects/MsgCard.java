package ComObjects;

import Ruleset.Card;

/** 
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Sie beinhaltet die ausgespielte Karte eines Spielers.
 */
public class MsgCard extends RulesetMessage {

	private Card card;

    /**
     * Dies ist der Kontruktor f�r eine neue MsgCard-Nachricht.
     * Diese enth�lt die Information, welche Karte von einem
     * Spieler gespielt wurde.
     * @param card ist die Karte.
     */
    public MsgCard(Card card) {
        this.card = card;
    }

    /**
     * Diese Methode gibt die ausgespielte Karte des Spielers zur�ck.
     * @return die Karte.
     */
    public Card getCard() {
        return card;
    }
}