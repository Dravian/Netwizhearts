package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/**
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 */
public class MsgMultiCardsRequest implements RulesetMessage, Serializable {

    /**
     * Dies ist die Anzahl der Karten, die der Server von Spieler erwartet.
     */
    private int count;

    /**
     * Dies ist der Kontruktor f�r eine neue MsgMultipleCardsRequest-Nachricht.
     */
    public MsgMultiCardsRequest(int count) {
        this.count = count;
    }

    /**
     * Diese Methode gibt die Anzahl der Karten zur�ck, die der Server vom
     * Spieler erwartet.
     * @return die Anzahl der Karten.
     */
    public int getCount() {
        return count;
    }

    @Override
    public void visit(ServerRuleset serverRuleset, String name) {
        serverRuleset.resolveMessage(this, name);    }

    @Override
    public void visit(ClientRuleset clientRuleset) {
        clientRuleset.resolveMessage(this);    }
}