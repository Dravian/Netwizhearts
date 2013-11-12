package testKlassen;

import Ruleset.RulesetType;
import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestGamerServer extends GameServer{

	public TestGamerServer(LobbyServer server, Player gameMaster,
			String GameName, RulesetType ruleset, String password,
			boolean hasPassword) {
		super(server, gameMaster, GameName, ruleset, password, hasPassword);
		
	}
}
