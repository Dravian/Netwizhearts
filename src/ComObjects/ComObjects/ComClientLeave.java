package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie wird zur Benachrichtigung gesendet, wenn ein Spieler
 * ins nächste Fenster möchte und aus dem alten entfernt
 * werden soll.
 *
 */
public class ComClientLeave implements ComObject, Serializable {

    /**
     * Dieser Konstruktor erstellt eine neue ComClientLeave-Nachricht.
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