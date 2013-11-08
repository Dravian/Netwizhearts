package ComObjects;

import Ruleset.GameClientUpdate;

/** 
 *  Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 *  Sie wird dem Client gesendet, um dem ClientRuleset den aktuellen
 *  Spielzustand in Form eines GameClientUpdate zu �bermitteln.
 */
public class MsgUser extends RulesetMessage {

	private GameClientUpdate gameClientUpdate;

    /**
     * Dies ist der Konstruktor einer neuen MsgUser-Nachricht.
     * @param gameClientUpdate ist der aktuelle Spielstand.
     */
    public MsgUser(GameClientUpdate gameClientUpdate) {
        this.gameClientUpdate = gameClientUpdate;
    }

    /**
     * Diese Methode liefert den den aktuellen Spielzustand, der f�r ein
     * Update ben�tigt wird.
     * @return den aktuellen Spielzustand.
     */
    public GameClientUpdate getGameClientUpdate() {
        return gameClientUpdate;
    }
}