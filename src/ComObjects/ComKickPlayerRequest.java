package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 * ComKickPlayerRequest.
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie ist eine Nachricht an den Server, die angibt einen
 * Spieler vom Spiel zu entfernen. Dazu enthaelt sie einen String,
 * der den Namen des Spielers enthaelt.
 */
public class ComKickPlayerRequest implements ComObject, Serializable {

    /**
	 * Dies ist der Name des Players, der entfernt werden soll.
	 */
	private String playerName;


    /**
     * Dies ist der Kontruktor fuer eine neue ComKickPlayerRequest-Nachricht.
     * Diese enthaelt den Namen des Spielers, der aus den Spiel geloescht werden soll.
     * @param playerName ist der Name des Spielers.
     */
    public ComKickPlayerRequest(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Diese Methode liefert den Namen des Spielers, der aus dem Spiel
     * entfernt werden soll.
     * @return den Spielernamen.
     */
    public String getPlayerName() {
        return playerName;
    }

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