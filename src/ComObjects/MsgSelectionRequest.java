package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/**
 * MsgSelectionRequest.
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Diese Nachricht sendet der Server an einen Spieler, wenn er eine
 * Farbauswahl von diesem erwartet.
 */
public class MsgSelectionRequest implements RulesetMessage, Serializable {

    /**
     * Dies ist der Kontruktor fuer eine neue MsgSelectionRequest-Nachricht.
     */
    public MsgSelectionRequest() {
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