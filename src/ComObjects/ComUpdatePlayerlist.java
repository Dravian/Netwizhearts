package ComObjects;

/** 
 *  Diese Klasse ist ein spezielles Kommunikations-Objekt.
 *  Sie sendet eine Nachricht zum Update der Playerliste
 *  in der Lobby und Spiellobby. Dazu enth�lt sie den Player
 *  und ein removeFlag.
 */
public class ComUpdatePlayerlist extends ComObject {

	private String playerName;
	private boolean removeFlag;

    /**
     * Dies ist der Kontruktor f�r eine neue ComUpdatePlayerlist-Nachricht.
     * Diese beinhaltet den Namen des Spielers und die Angabe ob er gel�scht
     * werden soll.
     * @param playerName ist der Name der Spielers.
     * @param removeFlag zeigt, ob der Spieler gel�scht werden soll.
     */
    public ComUpdatePlayerlist(String playerName, boolean removeFlag) {
        this.playerName = playerName;
        this.removeFlag = removeFlag;
    }

    /**
     * Diese Methode gibt den Namen des Spielers zur�ck.
     * @return den Spielernamen.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Diese Methode gibt zur�ck, ob der Spieler aus der Liste gel�scht
     * werden soll oder nicht.
     * @return ob der Spieler gel�scht werden soll.
     */
    public boolean isRemoveFlag() {
        return removeFlag;
    }
}