package ComObjects;

/** 
 *  Diese Klasse ist ein spezielles Kommunikations-Objekt.
 *  Sie ist die grundlegende Nachricht eines Regelwerkaufrufes
 *  und enth�lt eine verfeinerte Nachricht mit weiteren Informationen,
 *  die RulesetMessage.
 */
public class ComRuleset extends ComObject {

	private RulesetMessage rulesetMessage;

    /**
     * Dies ist der Kontruktor f�r eine neue ComResult-Nachricht.
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
}