package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie ist eine Nachricht, die beim Login an den Server gesendet wird.
 * Dazu enthält sie den Namen des Spielers, der sich einloggen möchte.
 */
public class ComLoginRequest implements ComObject, Serializable {

	private String playerName;

    /**
     * Dies ist der Kontruktor für eine neue ComLoginRequest-Nachricht.
     * @param name ist der Name des Spielers, des sich einloggen möchte.
     */
    public ComLoginRequest(String name) {
        this.playerName = name;
    }

    /**
     * Diese Methode liefert den Namen des Spielers, des sich einloggen
     * möchte. Dieser muss auf Eindeutigkeit geprüft werden.
     * @return den Spielernamen.
     */
    public String getPlayerName() {
        return playerName;
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