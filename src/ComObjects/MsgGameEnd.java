package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

import java.io.Serializable;
import java.lang.String;

/**
 * MsgGameEnd.
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Sie signalisiert dem ClientRuleset, dass das Spiel zu Ende ist.
 */
public class MsgGameEnd implements RulesetMessage, Serializable {

    private String winnerName;

    /**
     * Dies ist der Kontruktor fuer eine neue MsgGameEnd-Nachricht.
     * @param name ist der Name des Gewinners.
     */
    public MsgGameEnd(String name) {
        this.winnerName = name;
    }

    /**
     * Diese Methode liefert den Namen des Gewinners eines Spiels.
     * @return  den Gewinnernamen.
     */
    public String getWinnerName() {
        return winnerName;
    }

    @Override
    public void visit(ServerRuleset serverRuleset, String name) {
        serverRuleset.resolveMessage(this, name);    }

    @Override
    public void visit(ClientRuleset clientRuleset) {
        clientRuleset.resolveMessage(this);    }
}