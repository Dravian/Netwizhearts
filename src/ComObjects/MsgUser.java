package ComObjects;

import Ruleset.GameClientUpdate;

/** 
 *  Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 *  Sie wird dem Client gesendet, um eine Nutzereingabe anzufordern
 *  und/oder um dem ClientRuleset den aktuellen Spielzustand in Form
 *  eines GameClientUpdate zu übermitteln.
 */
public class MsgUser extends RulesetMessage {

	private GameClientUpdate gameClientUpdate;

    /**
     * Dieser Konstruktor erstellt eine neue MsgUser-Nachricht.
     * @param gameClientUpdate ist der aktuelle Spielstand.
     */
	public MsgUser(GameClientUpdate gameClientUpdate) {
        this.gameClientUpdate = gameClientUpdate;
    }

    /**
     * Diese Methode liefert den den aktuellen Spielzustand, der für ein
     * Update benötigt wird.
     * @return den aktuellen Spielzustand.
     */
    public GameClientUpdate getGameClientUpdate() {
        return gameClientUpdate;
    }
}