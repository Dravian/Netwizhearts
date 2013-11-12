package ComObjects;

import Client.ClientModel;
import Server.Player;
import Server.Server;

import java.io.Serializable;

/**
 *  Diese Klasse ist ein spezielles Kommunikations-Objekt.
 *  Sie ist die grundlegende Nachricht eines Regelwerkaufrufes
 *  und enthält eine verfeinerte Nachricht mit weiteren Informationen,
 *  die RulesetMessage.
 */
public class ComRuleset implements ComObject, Serializable {

	private RulesetMessage rulesetMessage;

    /**
     * Dies ist der Kontruktor für eine neue ComResult-Nachricht.
     * @param rulesetMessage ist eine Nachricht, die ans Ruleset
     *                       gesendet werden soll.
     */
    public ComRuleset(RulesetMessage rulesetMessage) {
        this.rulesetMessage = rulesetMessage;
    }

    /**
     * Diese Methode gibt die Nachricht zurück, die ans Ruleset
     * gesendet werden soll.
     * @return die Nachricht.
     */
    public RulesetMessage getRulesetMessage() {
        return rulesetMessage;
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