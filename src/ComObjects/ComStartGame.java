package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 * ComStartGame.
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie wird versendet, wenn ein Spiel gestartet werden soll.
 */
public class ComStartGame implements ComObject, Serializable {

    /**
     * Dies ist der Kontruktor fuer eine neue ComStartGame-Nachricht.
     */
    public ComStartGame() {
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