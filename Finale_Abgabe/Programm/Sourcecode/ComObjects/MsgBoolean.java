package ComObjects;

import java.io.Serializable;

import Ruleset.ClientRuleset;
import Ruleset.ServerRuleset;

/**
 * Diese Rulesetmessage gibt ein Boolean zurück
 */
public class MsgBoolean implements RulesetMessage, Serializable{
	private boolean bool;
	
	/**
	 * Dies ist der Konstruktor für eine MsgBoolean
	 * @param bool Der boolean Parameter
	 */
	public MsgBoolean(boolean bool) {
		this.bool = bool;
	}
	
	/**
	 * Gibt das enthaltene Boolean zurück
	 * @return Das enthaltene Boolean
	 */
	public boolean getBool() {
		return bool;
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
