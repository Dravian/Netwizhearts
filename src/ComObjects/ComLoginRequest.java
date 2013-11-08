package ComObjects;

/** 
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie ist eine Nachricht, die beim Login an den Server gesendet wird.
 * Dazu enth�lt sie den Namen des Spielers, der sich einloggen m�chte.
 */
public class ComLoginRequest extends ComObject {

	private String playerName;

    /**
     * Dies ist der Kontruktor f�r eine neue ComLoginRequest-Nachricht.
     * @param name ist der Name des Spielers, des sich einloggen m�chte.
     */
    public ComLoginRequest(String name) {
        this.playerName = name;
    }

    /**
     * Diese Methode liefert den Namen des Spielers, des sich einloggen
     * m�chte. Dieser muss auf Eindeutigkeit gepr�ft werden.
     * @return den Spielernamen.
     */
    public String getPlayerName() {
        return playerName;
    }
}