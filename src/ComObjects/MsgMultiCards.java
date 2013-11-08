package ComObjects;

import java.util.Set;
import Ruleset.Card;

/** 
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Sie liefert mehrere Karten zum Tausch f�r das Regelwerk Hearts.
 */
public class MsgMultiCards extends RulesetMessage {

	private Set<Card> cardList;

    /**
     * Dies ist der Kontruktor f�r eine neue MsgMultiCards-Nachricht.
     * @param cardList ist die Liste der ausgew�hlten Karten.
     */
    public MsgMultiCards(Set cardList) {
        this.cardList = cardList;
    }

    /**
     * Gibt die Liste der gew�hlten Karten zurück.
     * @return die Liste der Karten.
     */
    public Set<Card> getCardList() {
        return cardList;
    }
}