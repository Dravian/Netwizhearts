package ComObjects;

/** 
 *  Diese Klasse ist ein spezielles Kommunikations-Objekt.
 *  Sie ist eine Nachricht, die an den Server gesendet wird,
 *  wenn der Spieler einem bestimmten Spiel beitreten will.
 *  Dazu enth�lt es den Namen des Spielleiters als String.
 */
public class ComJoinRequest extends ComObject {

    /**
	 * Der Name der Spielleiters muss enthalten sein um ein Spiel zuzuornen.
     * Der Spielname ist nicht eindeutig, aber der Spielleiter schon.
     * Somit kann jedes Spiel mit Hilfe des Spielleiters identifiziert werden.
	 */
	private String gameMasterName;
	
	private String password;

    /**
     * Dies ist der Kontruktor f�r eine neue ConJoinRequest-Nachricht.
     * Ein Spiel kann durch den eindeutigen Namen der Spielleiters
     * identifiziert werden.
     * @param gameMasterName ist der Name der Spielleiters.
     * @param String Passwort f�r das Spiel.
     */
    public ComJoinRequest(String gameMasterName, String password) {
        this.gameMasterName = gameMasterName;
        this.password = password;
    }

    /**
     * Diese Methode gibt den Namen des Spielleiters zurück.
     * Dieser ist eindeutig, so kann ein bestimmtes Spiel
     * identifiziert werden.
     * @return den Namen des Spielleiters.
     */
    public String getGameMasterName() {
        return gameMasterName;
    }
}