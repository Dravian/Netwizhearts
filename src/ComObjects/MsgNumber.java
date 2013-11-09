package ComObjects;

/**
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 * Sie enthält eine Zahl.
 */
public class MsgNumber extends RulesetMessage {

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
}