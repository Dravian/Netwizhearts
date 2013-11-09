package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/**
 * Diese Klasse ist eine Verfeinerung der ComRuleset-Klasse.
 * Sie enth�lt einen Nachrichtentyp und vererbt an alle Nachrichten f�r das Regelwerk.
 */
public abstract class RulesetMessage implements Serializable {

    /**
     * Hier fehlt noch ein Kommentar
     * @param serverRuleset
     * @param name
     */
    public void visit(ServerRuleset serverRuleset, String name) {
        serverRuleset.resolveMessage(this, name);
    }

    /**
     * Hier fehlt noch ein Kommentar
     * @param clientRuleset
     */
    public void visit(ClientRuleset clientRuleset) {
        clientRuleset.resolveMessage(this);
    }
}