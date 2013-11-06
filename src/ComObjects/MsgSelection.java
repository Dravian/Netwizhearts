/**
 * 
 */
package ComObjects;

/** 
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 */
public class MsgSelection extends RulesetMessage {

	private Object selection;

    /**
     * Dieser Konstruktor erzeugt eine neue MsgSelection-Nachricht.
     * @param selection
     */
	public MsgSelection(Object selection) {
        this.selection = selection;
    }
}