package ComObjects;

/** 
 * Diese Klasse ist ein spezielles Kommunikations-Objekt.
 * Sie enthält eine Chatnachricht in Form eines Strings.
 */
public class ComChatMessage extends ComObject {

	private String chatMessage;

    /**
     * Dieser Konstruktor speichert die Nachricht im Objekt.
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
}