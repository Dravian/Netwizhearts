package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 *  Diese Klasse ist ein spezielles Kommunikations-Objekt.
 *  Sie sendet eine Nachricht zum Update der Playerliste
 *  in der Lobby und Spiellobby. Dazu enthält sie den Player
 *  und ein removeFlag.
 */
public class ComUpdatePlayerlist implements ComObject, Serializable {

	private String playerName;
	private boolean removeFlag;

    /**
     * Dies ist der Kontruktor für eine neue ComUpdatePlayerlist-Nachricht.
     * Diese beinhaltet den Namen des Spielers und die Angabe ob er gelöscht
     * werden soll.
     * @param playerName ist der Name der Spielers.
     * @param removeFlag zeigt, ob der Spieler gelöscht werden soll.
     */
    public ComUpdatePlayerlist(String playerName, boolean removeFlag) {
        this.playerName = playerName;
        this.removeFlag = removeFlag;
    }

    /**
     * Diese Methode gibt den Namen des Spielers zurück.
     * @return den Spielernamen.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Diese Methode gibt zurück, ob der Spieler aus der Liste gelöscht
     * werden soll oder nicht.
     * @return ob der Spieler gelöscht werden soll.
     */
    public boolean isRemoveFlag() {
        return removeFlag;
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