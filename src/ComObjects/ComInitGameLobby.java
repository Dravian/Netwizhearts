package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;
import java.util.List;

/**
 *  Diese Klasse ist ein spezielles Kommunikations-Objekt.
 *  Sie liefert die Liste der Spieler, die sich bereits
 *  beim Betreten des Wartefensters darin befinden.
 */
public class ComInitGameLobby implements ComObject, Serializable {

	private List<String> playerList;

    /**
     * Dies ist der Kontruktor für eine neue ComInitGameLobby-Nachricht.
     * @param playerList ist die Liste aller Player, die sich im
     *                   Wartefenster befinden.
     */
    public ComInitGameLobby(List<String> playerList) {
        this.playerList = playerList;
    }

    public List<String> getPlayerList() {
        return playerList;
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