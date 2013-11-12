package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 * ComClientQuit.
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Die Nachricht wird verschickt, wenn der Spieler ein Fenster schließt.
 */
public class ComClientQuit implements ComObject, Serializable {

    /**
     * Dies ist der Konstruktor einer neuen ComCLientQuit-Nachricht.
     */
    public ComClientQuit() {
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