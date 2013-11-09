package ComObjects;

/** 
 * Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 */
public class MsgMultiCardsRequest extends RulesetMessage {

    /**
     * Dies ist die Anzahl der Karten, die der Server von Spieler erwartet.
     */
    private int count;

    /**
     * Dies ist der Kontruktor für eine neue MsgMultipleCardsRequest-Nachricht.
     */
    public MsgMultiCardsRequest(int count) {
        this.count = count;
    }

    /**
     * Diese Methode gibt die Anzahl der Karten zurück, die der Server vom
     * Spieler erwartet.
     * @return die Anzahl der Karten.
     */
    public int getCount() {
        return count;
    }
}