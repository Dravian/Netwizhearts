package ComObjects;

import Ruleset.Colour;

/**
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Diese Nachricht enthält Information über eine Auswahl, die der
 * Spieler getroffen hat. Die Wahlmöglichkeiten werden durch Integer
 * repräsentiert.
 */
public class MsgSelection extends RulesetMessage {

	private int selection;

    /**
     * Dies ist der Kontruktor für eine neue MsgSelection-Nachricht.
     * @param selection ist die getroffene Auswahl, repräsentiert
     *                  durch einen Integer.
     */
	public MsgSelection(int selection) {
        this.selection = selection;
    }

    /**
     * Diese Methode gibt die Auswahl des Spieler zurück, die er
     * gemacht hat.
     * @return die Auswahl.
     */
    public int getSelection() {
        return selection;
    }
}