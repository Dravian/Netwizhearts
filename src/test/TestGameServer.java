package test;

import Ruleset.RulesetType;
import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestGameServer extends GameServer{

	public TestGameServer(LobbyServer server, Player gameMaster,
			String GameName, RulesetType ruleset, String password,
			boolean hasPassword) {
		super(server, gameMaster, GameName, ruleset, password, hasPassword);
		// TODO Automatisch erstellter Konstruktoren-Stub
	}

}
