package test;

import Ruleset.RulesetType;
import Ruleset.ServerHearts;
import Ruleset.ServerRuleset;
import Ruleset.ServerWizard;
import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestGameServer extends GameServer{
	public TestGameServer(LobbyServer server, String gameMaster,
			String GameName, RulesetType ruleset, String password,
			boolean hasPassword) {
		super(server, gameMaster, GameName, ruleset, password, hasPassword);
		
	}

	public void addToGame(TestPlayer red) {
		// TODO Automatisch erstellter Methoden-Stub
		
	}
	

}
