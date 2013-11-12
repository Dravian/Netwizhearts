package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/**
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 */
public class MsgNumberRequest implements RulesetMessage, Serializable {

    /**
     * Dies ist der Kontruktor f�r eine neue MsgNumberRequest-Nachricht.
     */
    public MsgNumberRequest() {
    }

    @Override
    public void visit(ServerRuleset serverRuleset, String name) {
        serverRuleset.resolveMessage(this, name);
    }

    @Override
    public void visit(ClientRuleset clientRuleset) {
        clientRuleset.resolveMessage(this);    }
}