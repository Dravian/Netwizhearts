package ComObjects;

import Ruleset.ClientRuleset;
import Ruleset.GameClientUpdate;
import Ruleset.ServerRuleset;

import java.io.Serializable;

/** 
 *  Diese Klasse ist eine Verfeinerung der RulesetMessage-Klasse.
 *  Sie wird dem Client gesendet, um dem ClientRuleset den aktuellen
 *  Spielzustand in Form eines GameClientUpdate zu übermitteln.
 */
public class MsgUser implements RulesetMessage, Serializable {

	private GameClientUpdate gameClientUpdate;

    /**
     * Dies ist der Konstruktor einer neuen MsgUser-Nachricht.
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

    @Override
    public void visit(ServerRuleset serverRuleset, String name) {
        serverRuleset.resolveMessage(this, name);
    }

    @Override
    public void visit(ClientRuleset clientRuleset) {
        clientRuleset.resolveMessage(this);
    }
}