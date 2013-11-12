package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 * ComClientLeave.
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie wird zur Benachrichtigung gesendet, wenn ein Spieler
 * ins naechste Fenster moechte und aus dem alten entfernt
 * werden soll.
 */
public class ComClientLeave implements ComObject, Serializable {

    /**
     * Dies ist der Konstruktor einer neuen ComClientLeave-Nachricht.
     */
    public ComClientLeave() {
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