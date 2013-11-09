package ComObjects;

/** 
 *  Diese Klasse ist ein spezielles Kommunikations-Objekt.
 *  Sie soll dem Spieler eine Mitteilung senden und so über
 *  ein Fehlerevent informieren.
 */
public class ComWarning extends ComObject {

    private String warning;

    /**
     * Dies ist der Konstruktor einer neuen ComWarning-Nachricht.
     * Er enthält eine Warnung an den Spieler, wenn ein Fehler
     * passiert.
     * @param warning ist die Warnung, die der Spieler erhält.
     */
    public ComWarning(String warning) {
        this.warning = warning;
    }

    /**
     * Diese Methode gibt die Nachricht zurück, die dem Spieler
     * den Fehler mitteilt.
     * @return die Warnnachricht.
     */
    public String getWarning() {
        return warning;
    }
}