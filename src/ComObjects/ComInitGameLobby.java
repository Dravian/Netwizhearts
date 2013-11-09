package ComObjects;

import java.util.List;

/**
 *  Diese Klasse ist ein spezielles Kommunikations-Objekt.
 *  Sie liefert die Liste der Spieler, die sich bereits
 *  beim Betreten des Wartefensters darin befinden.
 */
public class ComInitGameLobby extends ComObject {

	private List playerList;

    /**
     * Dies ist der Kontruktor für eine neue ComInitGameLobby-Nachricht.
     * @param playerList ist die Liste aller Player, die sich im
     *                   Wartefenster befinden.
     */
    public ComInitGameLobby(List playerList) {
        this.playerList = playerList;
    }

    /**
     * Diese Methode gibt die Liste der Player zurück, die sich momentan
     * inm Wartefenster befinden.
     * @return die Liste der Spieler.
     */
    public Object getPlayerList() {
        return playerList;
    }
}