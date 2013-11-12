package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 *  Diese Klasse ist ein spezielles Kommunikations-Objekt.
 *  Sie soll dem Spieler eine Mitteilung senden und so �ber
 *  ein Fehlerevent informieren.
 */
public class ComWarning implements ComObject, Serializable {

    private String warning;

    /**
     * Dies ist der Konstruktor einer neuen ComWarning-Nachricht.
     * Er enth�lt eine Warnung an den Spieler, wenn ein Fehler
     * passiert.
     * @param warning ist die Warnung, die der Spieler erh�lt.
     */
    public ComWarning(String warning) {
        this.warning = warning;
    }

    /**
     * Diese Methode gibt die Nachricht zur�ck, die dem Spieler
     * den Fehler mitteilt.
     * @return die Warnnachricht.
     */
    public String getWarning() {
        return warning;
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