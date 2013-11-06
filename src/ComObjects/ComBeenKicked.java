package ComObjects;

/**
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Die Nachricht wird an einen Spieler gesendet, wenn er aus
 * einem Spiel erntfernt wurde. Dies geschieht, wenn ein Spieler
 * ein Spiel verlässt oder wenn der Spielleiter das Wartefenster
 * verlässt.
 */
public class ComBeenKicked {

    private String message;

    /**
     * Dies ist der Kontruktor für eine neue ComBeenKicked-Nachricht.
     * @param message ist die Nachricht.
     */
    public ComBeenKicked(String message) {
        this.message = message;
    }

    /**
     * Diese Methode liefert die Nachricht, die an den Spieler
     * gesendet wird, wenn er entfernt wird.
     * @return die Nachricht.
     */
    public String getMessage() {
        return message;
    }
}
