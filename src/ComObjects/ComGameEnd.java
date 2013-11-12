package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;
import java.util.Map;

/**
 * ComGameEnd.
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie liefert den Gewinner eines Spiels und eine Auflistung
 * der Spieler mit ihren erspielten Punkten und wird versendet,
 * wenn ein Spiel zu Ende ist oder eine Runde. In diesem Fall
 * wird der Gewinner einfach leer gelassen, da es noch keinen gibt.
 */
public class ComGameEnd implements ComObject, Serializable {

    private String winner;
    private Map<String, Integer> points;

    /**
     * Dies ist der Kontruktor fuer eine neue ComGameEnd-Nachricht.
     * @param winner    ist der Gewinner des Spiels.
     * @param points    ist eine Auflistung der Spieler mit ihren Punkten.
     */
    public ComGameEnd(String winner, Map<String, Integer> points) {
        this.winner = winner;
        this.points = points;
    }

    /**
     * Diese Methode gibt den Gewinner zurueck.
     * @return  den Gewinner.
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Diese Methode gibt die Auflistung der Spieler mit ihren
     * zugehoerigen Punkten zurueck.
     * @return  die Liste der Spieler mit ihren Punkten.
     */
    public Map<String, Integer> getPoints() {
        return points;
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
