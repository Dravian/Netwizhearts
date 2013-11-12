package ComObjects;

import java.io.Serializable;
import java.util.Set;
import Ruleset.Card;
import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

/** 
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Sie liefert mehrere Karten zum Tausch für das Regelwerk Hearts.
 */
public class MsgMultiCards implements RulesetMessage, Serializable {

	private Set<Card> cardList;

    /**
     * Dies ist der Kontruktor für eine neue MsgMultiCards-Nachricht.
     * @param cardList ist die Liste der ausgewählten Karten.
     */
    public MsgMultiCards(Set cardList) {
        this.cardList = cardList;
    }

    /**
     * Gibt die Liste der gewählten Karten zurück.
     * @return die Liste der Karten.
     */
    public Set<Card> getCardList() {
        return cardList;
    }

    @Override
    public void visit(ServerRuleset serverRuleset, String name) {
        serverRuleset.resolveMessage(this, name);    }

    @Override
    public void visit(ClientRuleset clientRuleset) {
        clientRuleset.resolveMessage(this);    }
}