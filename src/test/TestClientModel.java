package test;

import java.util.List;

import Client.ClientModel;
import Client.ClientState;
import Client.MessageListenerThread;
import Client.ViewNotification;
import ComObjects.ComRuleset;
import Ruleset.ClientHearts;
import Ruleset.ClientRuleset;
import Ruleset.ClientWizard;
import Ruleset.RulesetType;
import Ruleset.UserMessages;

public class TestClientModel extends ClientModel {
	ClientRuleset ruleset;
	List<UserMessages> messages;

	public TestClientModel(MessageListenerThread net, RulesetType rulesetType) {
		super(net);

		if (rulesetType == RulesetType.Wizard) {
			ruleset = new ClientWizard(this);
		} else {
			ruleset = new ClientHearts(this);
		}
	}

	public ClientRuleset getRuleset() {
		return ruleset;
	}

	public void receiveMessage(final ComRuleset msg) {

		if (msg != null) {
			if (msg.getRulesetMessage() != null) {
				msg.getRulesetMessage().visit(ruleset);
			} else {
				throw new IllegalArgumentException("Regelwernachricht"
						+ " ist null");
			}
		} else {
			throw new IllegalArgumentException("Argument ist null");
		}
	}
	
	public void updateGame() {
		
	}
	
	
}
