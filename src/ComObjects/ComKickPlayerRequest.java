package ComObjects;

/** 
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie ist eine Nachricht an den Server, die angibt einen
 * Spieler vom Spiel zu entfernen. Dazu enthÃ¤lt es einen String,
 * der den Namen des Spielers enthÃ¤lt.
 */
public class ComKickPlayerRequest extends ComObject {

    /**
	 * Dies ist der Name des Players, der gekicked werden soll.
	 */
	private String playerName;


    /**
     * Dies ist der Kontruktor für eine neue ComKickPlayerRequest-Nachricht.
     * Diese enthält den Namen des Spielers, der aus den Spiel gelöscht werden soll.
     * @param playerName ist der Name des Spielers.
     */
    public ComKickPlayerRequest(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Diese Methode liefert den Namen des Spielers, der aus dem Spiel
     * entfernt werden soll.
     * @return den Spielernamen.
     */
    public String getPlayerName() {
        return playerName;
    }
}