package ComObjects;

import Ruleset.Colour;

/**
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Diese Nachricht enth�lt Information �ber eine Auswahl, die der
 * Spieler getroffen hat. Die Wahlm�glichkeiten werden durch Integer
 * repr�sentiert.
 */
public class MsgSelection extends RulesetMessage {

	private int selection;

    /**
     * Dies ist der Kontruktor f�r eine neue MsgSelection-Nachricht.
     * @param selection ist die getroffene Auswahl, repr�sentiert
     *                  durch einen Integer.
     */
	public MsgSelection(int selection) {
        this.selection = selection;
    }

    /**
     * Diese Methode gibt die Auswahl des Spieler zur�ck, die er
     * gemacht hat.
     * @return die Auswahl.
     */
    public int getSelection() {
        return selection;
    }
}