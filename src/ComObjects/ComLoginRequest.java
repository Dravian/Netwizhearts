package ComObjects;

/** 
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie ist eine Nachricht, die beim Login an den Server gesendet wird.
 * Dazu enthält sie den Namen des Spielers, der sich einloggen möchte.
 */
public class ComLoginRequest extends ComObject {

	private String playerName;

    /**
     * Dieser Konstruktor erzeugt eine neue ComLoginRequest-Nachricht.
     * @param name ist der Name des Spielers, des sich einloggen möchte.
     */
    public ComLoginRequest(String name) {
        this.playerName = name;
    }

    /**
     * Diese Methode liefert den Namen des Spielers, des sich einloggen
     * möchte. Dieser muss auf Eindeutigkeit geprüft werden.
     * @return den Spielernamen.
     */
    public String getPlayerName() {
        return playerName;
    }
}