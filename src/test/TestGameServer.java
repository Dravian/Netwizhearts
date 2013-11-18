package test;

import Ruleset.RulesetType;
import Ruleset.ServerHearts;
import Ruleset.ServerRuleset;
import Ruleset.ServerWizard;
import Ruleset.TestServerWizard;
import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestGameServer extends GameServer{
	private ServerRuleset serverRuleset;
	
	public TestGameServer(LobbyServer server, Player gameMaster,
			String GameName, RulesetType ruleset, String password,
			boolean hasPassword) {
		super(server, gameMaster, GameName, ruleset, password, hasPassword);
		
	}
	
	public ServerRuleset getRuleset() {
		return serverRuleset;
	}
	
	public void setRuleset(ServerRuleset ruleset) {
		this.serverRuleset = ruleset;
	}
	

}
