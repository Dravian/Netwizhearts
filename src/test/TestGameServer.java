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
	private ServerRuleset ruleset;
	
	public TestGameServer(LobbyServer server, Player gameMaster,
			String GameName, RulesetType rulesetType, String password,
			boolean hasPassword) {
		super(server, gameMaster, GameName, rulesetType, password, hasPassword);
		players = new HashSet<TestPlayer>();
		
		addPlayer(gameMaster);
		if(rulesetType == RulesetType.Wizard) {
			ruleset = new ServerWizard(this);
		} else {
			ruleset = new ServerHearts(this);
		}
	}

	public void addPlayer(TestPlayer player) {
		players.add(player);
		player.setServer(this);
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
	
	public ServerRuleset getRuleset() {
		return ruleset;
	}
	
	@Override
	public void receiveMessage(Player player, ComRuleset ruleset){
		ruleset.getRulesetMessage().visit(this.ruleset, player.getPlayerName());
	}

}
