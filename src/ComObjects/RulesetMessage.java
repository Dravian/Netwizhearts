package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/**
 * Diese Klasse ist eine Verfeinerung der ComRuleset-Klasse.
 * Sie enthält einen Nachrichtentyp und vererbt an alle Nachrichten für das Regelwerk.
 */
public abstract class RulesetMessage implements Serializable {

    /**
     * Diese Methode ist nötig, damit das ServerRuleset entscheiden kann
     * welche Message es enthält und wie diese verarbeitet werden soll.
     * @param serverRuleset ist das Ruleset, welches übergeben wird, damit
     *                      die überladene Methode richtig gewählt wird.
     * @param name          ist der Name des Spielers.
     */
    public void visit(ServerRuleset serverRuleset, String name) {
        serverRuleset.resolveMessage(this, name);
    }

    /**
     * Diese Methode ist nötig, damit das ServerRuleset entscheiden kann
     * welche Message es enthält und wie diese verarbeitet werden soll.
     * @param clientRuleset ist das Ruleset, welches übergeben wird, damit
     *                      die überladene Methode richtig gewählt wird.
     */
    public void visit(ClientRuleset clientRuleset) {
        clientRuleset.resolveMessage(this);
    }
}