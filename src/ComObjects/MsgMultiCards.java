package ComObjects;

import java.util.Set;
import Ruleset.Card;

/** 
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Sie liefert mehrere Karten zum Tausch für das Regelwerk Hearts.
 */
public class MsgMultiCards extends RulesetMessage {

	private int count;
	private Set<Card> cardList;

    /**
     * Dieser Konstruktor erstellt eine neue MsgMultiCards-Nachricht.
     * @param count gibt an, wie viele Karten ausgewählt wurden. ??
     * @param cardList ist die Liste der ausgewählten Karten.
     */
    public MsgMultiCards(int count, Set cardList) {
        this.count = count;
        this.cardList = cardList;
    }

    /**
     * Gibt die Anzahl der gewählten Karten zurück. ??
     * @return die Anzahl der Karten.
     */
    public int getCount() {
        return count;
    }

    /**
     * Gibt die Liste der gewählten Karten zurück.
     * @return die Liste der Karten.
     */
    public Set<Card> getCardList() {
        return cardList;
    }
}