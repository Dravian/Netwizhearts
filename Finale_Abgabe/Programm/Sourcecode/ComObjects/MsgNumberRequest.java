package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/**
 * MsgNumberRequest.
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Sie Wird gesendet, wenn die Eingabe einer Zahl gefordert werden
 * soll.
 */
public class MsgNumberRequest implements RulesetMessage, Serializable {

    /**
     * Dies ist der Kontruktor fuer eine neue MsgNumberRequest-Nachricht.
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