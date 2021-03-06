package ComObjects;

import Client.ClientModel;
import Ruleset.RulesetType;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 * ComCreateGameRequest.
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Diese Nachricht wird versendet, wenn ein neues Spiel erstellt
 * werden soll.
 */
public class ComCreateGameRequest implements ComObject, Serializable {

    private String gameName;

    private RulesetType ruleset;

    private boolean hasPassword;

    private String password;

    /**
     * Dies ist der Kontruktor fuer eine neue ComCreateGameRequest-Nachricht.
     * Wurde kein Passwort gesetzt, bleibt dieses leer.
     * @param name          ist der Name des Spiels.
     * @param ruleset       ist die der Spieltyp, der erstellt werden soll.
     * @param hasPassword   sagt, ob ein Passwort gesetzt wurde.
     * @param password      ist das Passwort, das gesetzt wurde.
     */
    public ComCreateGameRequest(String name, RulesetType ruleset, boolean hasPassword, String password) {
        this.gameName = name;
        this.ruleset = ruleset;
        this.hasPassword = hasPassword;
        this.password = password;
    }

    /**
     * Diese Methode gibt den Namen des Spiels zurueck.
     * @return den Spielnamen.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Diese Methode gibt das Regelwerk zurueck, das benutzt werden soll.
     * @return das Regelwerk, welches benutzt wird.
     */
    public RulesetType getRuleset() {
        return ruleset;
    }

    /**
     * Diese Methode gibt an, ob eine Passwort fuer ein Spiel gesetzt wurde.
     * @return ob es ein Passwort gibt.
     */
    public boolean hasPassword() {
        return hasPassword;
    }

    /**
     * Gibt das Passwort zurueck. Sollte keines gesetzt sein, wird null
     * zurueck gegeben.
     * @return das Passwort.
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