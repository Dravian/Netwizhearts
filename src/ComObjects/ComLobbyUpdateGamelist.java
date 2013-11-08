package ComObjects;

import Server.GameServerRepresentation;

/** 
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie aktualisiert die Gameliste in der Lobby.
 * Dazu enthÃ¤lt sie den GameServer und ein RemoveFlag.
 */
public class ComLobbyUpdateGamelist extends ComObject {

	private boolean removeFlag;
	private GameServerRepresentation gameServer;

    /**
     * Dies ist der Kontruktor für eine neue ComLobbyUpdateGamelist-Nachricht.
     * @param removeFlag zeigt an, ob das Spiel gelÃ¶scht werden soll.
     * @param gameServer ist das Spiel.
     */
    public ComLobbyUpdateGamelist(boolean removeFlag, GameServerRepresentation gameServer) {
        this.removeFlag = removeFlag;
        this.gameServer = gameServer;
    }

    /**
     * Diese Methode liefert, ob ein Spiel gelÃ¶scht werden soll oder nicht.
     * @return ob das Spiel gelöscht wird.
     */
    public boolean isRemoveFlag() {
        return removeFlag;
    }

    /**
     * Diese Methode liefert das Spiel, das geupdated werden soll.
     * @return das Spiel.
     */
    public GameServerRepresentation getGameServer() {
        return gameServer;
    }
}