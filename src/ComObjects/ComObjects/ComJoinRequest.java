package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 *  Diese Klasse ist ein spezielles Kommunikations-Objekt.
 *  Sie ist eine Nachricht, die an den Server gesendet wird,
 *  wenn der Spieler einem bestimmten Spiel beitreten will.
 *  Dazu enth�lt es den Namen des Spielleiters als String.
 */
public class ComJoinRequest implements ComObject, Serializable {

    /**
	 * Der Name der Spielleiters muss enthalten sein um ein Spiel zuzuornen.
     * Der Spielname ist nicht eindeutig, aber der Spielleiter schon.
     * Somit kann jedes Spiel mit Hilfe des Spielleiters identifiziert werden.
	 */
	private String gameMasterName;
	
	private String password;

    /**
     * Dies ist der Kontruktor f�r eine neue ConJoinRequest-Nachricht.
     * Ein Spiel kann durch den eindeutigen Namen der Spielleiters
     * identifiziert werden.
     * @param gameMasterName ist der Name der Spielleiters.
     * @param password f�r das Spiel.
     */
    public ComJoinRequest(String gameMasterName, String password) {
        this.gameMasterName = gameMasterName;
        this.password = password;
    }

    /**
     * Diese Methode gibt den Namen des Spielleiters zurück.
     * Dieser ist eindeutig, so kann ein bestimmtes Spiel
     * identifiziert werden.
     * @return den Namen des Spielleiters.
     */
    public String getGameMasterName() {
        return gameMasterName;
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