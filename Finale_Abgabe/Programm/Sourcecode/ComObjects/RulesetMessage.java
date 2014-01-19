package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/**
 * RulesetMessage.
 * Dieses Interface ist eine Verfeinerung der ComRuleset-Klasse.
 * Es enthaelt Methoden, die von speziellen RulesetMessages
 * implementiert werden müssen.
 */
public interface RulesetMessage {

    /**
     * Diese Methode ist noetig, damit das ServerRuleset entscheiden kann
     * welche Message es enthaelt und wie diese verarbeitet werden soll.
     * @param serverRuleset ist das Ruleset, welches uebergeben wird, damit
     *                      die ueberladene Methode richtig gewaehlt wird.
     * @param name          ist der Name des Spielers.
     */
    public void visit(ServerRuleset serverRuleset, String name);

    /**
     * Diese Methode ist noetig, damit das ClientRuleset entscheiden kann
     * welche Message es enthaelt und wie diese verarbeitet werden soll.
     * @param clientRuleset ist das Ruleset, welches uebergeben wird, damit
     *                      die ueberladene Methode richtig gewaehlt wird.
     */
    public void visit(ClientRuleset clientRuleset);
}