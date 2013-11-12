package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 * ComChatMessage.
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie enthaelt eine Chatnachricht in Form eines Strings.
 */
public class ComChatMessage implements ComObject, Serializable {

	private String chatMessage;

    /**
     * Dies ist der Kontruktor fuer eine neue ComChatMessage-Nachricht.
     * @param message ist die Chatnachricht, die versendet wird.
     */
    public ComChatMessage(String message) {
        this.chatMessage = message;
    }

    /**
     * Hier kann die versendete Nachricht von anderen Klassen
     * ausgelesen werden.
     * @return die Chatnachricht, die versendet wurde.
     */
	public String getChatMessage() {
        return chatMessage;
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