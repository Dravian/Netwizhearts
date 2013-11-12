package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/**
 *  Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 *  Sie signalisiert dem ClientRuleset, dass das Spiel zu Ende ist.
 */
public class MsgGameEnd implements RulesetMessage, Serializable {

    /**
     * Dies ist der Kontruktor für eine neue MsgGameEnd-Nachricht.
     */
    public MsgGameEnd() {
    }

    @Override
    public void visit(ServerRuleset serverRuleset, String name) {
        serverRuleset.resolveMessage(this, name);    }

    @Override
    public void visit(ClientRuleset clientRuleset) {
        clientRuleset.resolveMessage(this);    }
}