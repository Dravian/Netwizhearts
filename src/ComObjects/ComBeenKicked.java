package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 * ComBeenKicked.
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Die Nachricht wird an einen Spieler gesendet, wenn er aus
 * einem Spiel erntfernt wurde. Dies geschieht, wenn ein Spieler
 * ein Spiel verlaesst oder wenn der Spielleiter das Wartefenster
 * verlaesst.
 */
public class ComBeenKicked implements ComObject, Serializable {

    private String message;

    /**
     * Dies ist der Kontruktor fuer eine neue ComBeenKicked-Nachricht.
     * @param message ist die Nachricht.
     */
    public ComBeenKicked(String message) {
        this.message = message;
    }

    /**
     * Diese Methode liefert die Nachricht, die an den Spieler
     * gesendet wird, wenn er entfernt wird.
     * @return die Nachricht.
     */
    public String getMessage() {
        return message;
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
