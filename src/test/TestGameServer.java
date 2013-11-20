package test;

import java.util.HashSet;
import java.util.Set;

import ComObjects.ComObject;
import ComObjects.ComRuleset;
import ComObjects.RulesetMessage;
import Ruleset.RulesetType;
import Ruleset.ServerHearts;
import Ruleset.ServerRuleset;
import Ruleset.ServerWizard;
import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestGameServer extends GameServer{
	Set<TestPlayer> players;
	
	public TestGameServer(LobbyServer server, String gameMaster,
			String GameName, RulesetType ruleset, String password,
			boolean hasPassword) {
		super(server, gameMaster, GameName, ruleset, password, hasPassword);
		players = new HashSet<TestPlayer>();
	}

	public void addPlayer(TestPlayer player) {
		players.add(player);
	}
	
	public void sendRulesetMessage(String name, RulesetMessage message) {
		for (Player player : players) {
			if(player.getPlayerName().equals(name)){
				player.send(new ComRuleset(message));
			}
		}
	}
	
	public  void broadcast(ComObject com){
		if(com != null){
			for (Player player : players) {
				player.send(com);
			}
		}
	}
	

}
