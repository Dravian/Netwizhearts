package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

/**
 * MsgGameEnd.
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Sie signalisiert dem ClientRuleset, dass das Spiel zu Ende ist.
 */
public class MsgGameEnd implements RulesetMessage, Serializable {

    private List<String> winnerNames;

    /**
     * Dies ist der Kontruktor fuer eine neue MsgGameEnd-Nachricht.
     * @param names sind die Namen der Gewinner.
     */
    public MsgGameEnd(List<String> names) {
        this.winnerNames = names;
    }

    /**
     * Diese Methode liefert den Namen des Gewinners eines Spiels.
     * Haben mehrere Leute gewonnen, enthält die Liste mehrere Namen.
     * @return  den Gewinnernamen.
     */
    public List<String> getWinnerName() {
        return winnerNames;
    }

    @Override
    public void visit(ServerRuleset serverRuleset, String name) {
        serverRuleset.resolveMessage(this, name);    }

    @Override
    public void visit(ClientRuleset clientRuleset) {
        clientRuleset.resolveMessage(this);    }
}