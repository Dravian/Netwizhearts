package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.Colour;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/**
 * MsgSelection.
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Diese Nachricht enthaelt eine Kartenfarbe, die der
 * Spieler zuvor ausgewaehlt hat.
 */
public class MsgSelection implements RulesetMessage, Serializable {

	private Colour selection;

    /**
     * Dies ist der Kontruktor fuer eine neue MsgSelection-Nachricht.
     * @param selection ist die Farbe der Karte, die der Spieler
     *                  gewaehlt hat.
     */
	public MsgSelection(Colour selection) {
        this.selection = selection;
    }

    /**
     * Diese Methode gibt die Farbe zurueck, die der Spieler
     * gewaehlt hat.
     * @return die gewaehlte Farbe.
     */
    public Colour getSelection() {
        return selection;
    }

    @Override
    public void visit(ServerRuleset serverRuleset, String name) {
        serverRuleset.resolveMessage(this, name);    }

    @Override
    public void visit(ClientRuleset clientRuleset) {
        clientRuleset.resolveMessage(this);    }
}