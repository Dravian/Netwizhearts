package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 * ComServerAcknowledgement.
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Diese Nachricht wird vom Server als Bestaetigung gesendet.
 */
public class ComServerAcknowledgement implements ComObject, Serializable {

    @Override
    public void process(ClientModel model) {
        //model.receiveMessage(this);
        throw new UnsupportedOperationException();
    }

    @Override
    public void process(Player player, Server server) {
        server.receiveMessage(player,this);
    }
}