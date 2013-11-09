package ComObjects;

import java.util.Set;
import Ruleset.Card;

/** 
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Sie liefert mehrere Karten zum Tausch für das Regelwerk Hearts.
 */
public class MsgMultiCards extends RulesetMessage {

	private Set<Card> cardList;

    /**
     * Dies ist der Kontruktor für eine neue MsgMultiCards-Nachricht.
     * @param cardList ist die Liste der ausgewählten Karten.
     */
    public MsgMultiCards(Set cardList) {
        this.cardList = cardList;
    }

    /**
     * Gibt die Liste der gewählten Karten zurÃ¼ck.
     * @return die Liste der Karten.
     */
    public Set<Card> getCardList() {
        return cardList;
    }
}