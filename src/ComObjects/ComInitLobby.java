package ComObjects;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import Client.ClientModel;
import Server.GameServerRepresentation;
import Server.Player;
import Server.Server;

/**
 * ComInitLobby.
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie synchronisiert den Client mit der Lobby, wenn er sich mit dem
 * Server verbindet oder nach einem Spiel in die Lobby zurueckkehrt.
 * Dazu enthaelt sie sowohl die playerList, als auch die gameList.
 */
public class ComInitLobby implements ComObject, Serializable {

	private List<String> playerList;

	private Set<GameServerRepresentation> gameList;

    /**
     * Dies ist der Kontruktor fuer eine neue ComInitLobby-Nachricht.
     * @param playerList    ist die Liste der Spieler, die sich in der
     *                      Lobby befinden.
     * @param gameList      ist die Liste der Spiele, die existieren
     *                      und in der Lobby angezeigt werden.
     */
    public ComInitLobby(List<String> playerList, Set gameList) {
        this.playerList = playerList;
        this.gameList = gameList;
    }

    /**
     * Die Methode liefert die Liste aller Spieler, die in der Lobby sind.
     * @return die Liste der Spieler.
     */
    public List<String> getPlayerList() {
        return playerList;
    }

    /**
     * Diese Methode liefert eine Liste aller Spiele, die erstellt wurden,
     * damit sie in der Lobby angezeigt werden koennen.
     * @return die Liste der Spiele.
     */
    public Set<GameServerRepresentation> getGameList() {
        return gameList;
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