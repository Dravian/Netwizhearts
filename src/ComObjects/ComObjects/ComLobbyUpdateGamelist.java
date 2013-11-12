package ComObjects;

import Client.ClientModel;
import Server.GameServerRepresentation;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/** 
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie aktualisiert die Gameliste in der Lobby.
 * Dazu enthält sie den GameServer und ein RemoveFlag.
 */
public class ComLobbyUpdateGamelist implements ComObject, Serializable {

	private boolean removeFlag;
	private GameServerRepresentation gameServer;

    /**
     * Dies ist der Kontruktor für eine neue ComLobbyUpdateGamelist-Nachricht.
     * @param removeFlag zeigt an, ob das Spiel gelöscht werden soll.
     * @param gameServer ist das Spiel.
     */
    public ComLobbyUpdateGamelist(boolean removeFlag, GameServerRepresentation gameServer) {
        this.removeFlag = removeFlag;
        this.gameServer = gameServer;
    }

    /**
     * Diese Methode liefert, ob ein Spiel gelöscht werden soll oder nicht.
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

    @Override
    public void process(ClientModel model) {
        model.receiveMessage(this);
    }

    @Override
    public void process(Player player, Server server) {
        server.receiveMessage(player,this);
    }
}