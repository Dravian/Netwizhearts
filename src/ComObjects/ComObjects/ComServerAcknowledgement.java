package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Diese Nachricht wird von Server als Bestätigung gesendet.
 */
public class ComServerAcknowledgement implements ComObject, Serializable {

    @Override
    public void process(ClientModel model) {
        model.receiveMessage(this);
    }

    @Override
    public void process(Player player, Server server) {
        server.receiveMessage(player,this);
    }
}