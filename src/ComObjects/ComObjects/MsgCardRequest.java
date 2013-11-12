package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/**
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Diese Nachricht wird von Server gesendet, um einem Spieler
 * mitzuteilen, dass er das Spielen einer Karte erwartet.
 */
public class MsgCardRequest implements RulesetMessage, Serializable {

    /**
     * Dies ist der Konstruktor einer neuen MsgCardRequest-Nachricht.
     */
    public MsgCardRequest() {
    }


    @Override
    public void visit(ServerRuleset serverRuleset, String name) {
        serverRuleset.resolveMessage(this, name);    }

    @Override
    public void visit(ClientRuleset clientRuleset) {
        clientRuleset.resolveMessage(this);    }
}
