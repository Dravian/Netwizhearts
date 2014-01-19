package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;
import java.lang.String;

/**
 * ComJoinRequest.
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie ist eine Nachricht, die an den Server gesendet wird,
 * wenn der Spieler einem bestimmten Spiel beitreten will.
 * Dazu enthaelt sie den Namen des Spielleiters als String
 * und ein Passwort, falls dieses von Spielleiter gesetzt wurde.
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
     * Dies ist der Kontruktor fuer eine neue ConJoinRequest-Nachricht.
     * Ein Spiel kann durch den eindeutigen Namen der Spielleiters
     * identifiziert werden.
     * @param gameMasterName ist der Name der Spielleiters.
     * @param password fuer das Spiel.
     */
    public ComJoinRequest(String gameMasterName, String password) {
        this.gameMasterName = gameMasterName;
        this.password = password;
    }

    /**
     * Diese Methode gibt den Namen des Spielleiters zurueck.
     * Dieser ist eindeutig, so kann ein bestimmtes Spiel
     * identifiziert werden.
     * @return der Name des Spielleiters.
     */
    public String getGameMasterName() {
        return gameMasterName;
    }

    /**
     * Diese Methode liefert das Passwort, mit dem ein Spiel
     * gesichert ist.
     * @return das Passwort eines Spiels.
     */
    public String getPassword() {
        return password;
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