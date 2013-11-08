package ComObjects;

import java.util.List;
import java.util.Set;
import Server.GameServerRepresentation;

/** 
 *  Diese Klasse ist ein spezielles Kommunikations-Objekt.
 *  Sie synchronisiert den Client mit der Lobby, wenn er sich mit dem
 *  Server verbindet oder nach einem Spiel in die Lobby zurückkehrt.
 *  Dazu enthält sie sowohl die playerList, als auch die gameList.
 */
public class ComInitLobby extends ComObject {

	private List playerList;

	private Set<GameServerRepresentation> gameList;

    /**
     * Dies ist der Kontruktor für eine neue ComInitLobby-Nachricht.
     * @param playerList    ist die Liste der Spieler, die sich in der
     *                      Lobby befinden.
     * @param gameList      ist die Liste der Spiele, die existieren
     *                      und in der Lobby angezeigt werden.
     */
    public ComInitLobby(List playerList, Set gameList) {
        this.playerList = playerList;
        this.gameList = gameList;
    }

    /**
     * Die Methode liefert die Liste aller Spieler, die in der Lobby sind.
     * @return die Liste der Spieler.
     */
    public List getPlayerList() {
        return playerList;
    }

    /**
     * Diese Methode liefert eine Liste aller Spiele, die erstellt wurden,
     * damit sie in der Lobby angezeigt werden können.
     * @return die Liste der Spiele.
     */
    public Set<GameServerRepresentation> getGameList() {
        return gameList;
    }
}