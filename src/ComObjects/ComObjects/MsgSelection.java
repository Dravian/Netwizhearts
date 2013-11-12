package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.Colour;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/**
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Diese Nachricht enth�lt eine Kartenfarbe, die der
 * Spieler zuvor in einem Drop-Down Men� ausgewählt hat.
 */
public class MsgSelection implements RulesetMessage, Serializable {

	private Colour selection;

    /**
     * Dies ist der Kontruktor f�r eine neue MsgSelection-Nachricht.
     * @param selection ist die Farbe der Karte, die der Spieler
     *                  gew�hlt hat.
     */
	public MsgSelection(Colour selection) {
        this.selection = selection;
    }

    /**
     * Diese Methode gibt die Farbauswahl des Spieler zur�ck, die er
     * gemacht hat.
     * @return die gewählte Farbe.
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