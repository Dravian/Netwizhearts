package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/**
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Sie enthält eine Zahl.
 */
public class MsgNumber implements RulesetMessage, Serializable {

	private int number;

    /**
     * Dies ist der Kontruktor für eine neue MsgNumber-Nachricht.
     * @param number ist eine Eingabe eines Spielers
     */
	public MsgNumber(int number) {
        this.number = number;
    }

    /**
     * Diese Methode liefert die Eingabe eines Spielers.
     * @return eine Zahl, die Eingabe des Spielers.
     */
    public int getNumber() {
        return number;
    }

    @Override
    public void visit(ServerRuleset serverRuleset, String name) {
        serverRuleset.resolveMessage(this, name);    }

    @Override
    public void visit(ClientRuleset clientRuleset) {
        clientRuleset.resolveMessage(this);    }
}