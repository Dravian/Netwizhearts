package ComObjects;

/** 
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Diese Nachricht wird versendet, wenn ein neues Spiel erstellt
 * werden soll.
 */
public class ComCreateGameRequest extends ComObject {

    private String gameName;

    private Enum ruleset;

    private boolean hasPassword;

    private String password;

    /**
     * Dies ist der Kontruktor für eine neue ComCreateGameRequest-Nachricht.
     * @param name          ist der Name des Spiels.
     * @param ruleset       ist die der Spieltyp, der erstellt werden soll.
     * @param hasPassword   sagt, ob ein Passwort gesetzt wurde.
     * @param password      ist das Passwort, das gesetzt wurde.
     */
    public ComCreateGameRequest(String name, Enum ruleset, boolean hasPassword, String password) {
        this.gameName = name;
        this.ruleset = ruleset;
        this.hasPassword = hasPassword;
        this.password = password;
    }

    /**
     * Diese Methode gibt den Namen des Spiels zurück.
     * @return den Spielnamen.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Diese Methode gibt das Regelwerk zur�ck, das benutzt werden soll.
     * @return das Regelwerk, welches benutzt wird.
     */
    public Enum getRuleset() {
        return ruleset;
    }

    /**
     * Diese Methode gibt an, ob eine Passwort f�r ein Spiel gesetzt wurde.
     * @return ob es ein Passwort gibt.
     */
    public boolean hasPassword() {
        return hasPassword;
    }

    /**
     * Gibt das Passwort zur�ck. Sollte keines gesetzt sein, wird null
     * zur�ck gegeben.
     * @return das Passwort.
     */
    public String getPassword() {
        return password;
    }
}