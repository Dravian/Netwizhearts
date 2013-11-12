package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;
import java.util.List;

/**
 * ComInitGameLobby.
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie liefert die Liste der Spieler, die sich bereits
 * beim Betreten des Wartefensters darin befinden.
 */
public class ComInitGameLobby implements ComObject, Serializable {

	private List<String> playerList;

    /**
     * Dies ist der Kontruktor fuer eine neue ComInitGameLobby-Nachricht.
     * @param playerList ist die Liste aller Player, die sich im
     *                   Wartefenster befinden.
     */
    public ComInitGameLobby(List<String> playerList) {
        this.playerList = playerList;
    }

    /**
     * Diese Methode liefert die Liste der Player, die sich beim
     * Hinzufuegen eines weiteren Spielers bereits im Wartefenster
     * befinden.
     * @return  die Liste der Spieler, die im Wartefenster sind.
     */
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