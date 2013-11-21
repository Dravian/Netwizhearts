package Ruleset;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.TestGameServer;
import test.TestLobbyServer;
import test.TestPlayer;

import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestResolveMsgCard {
	ServerRuleset ruleset;	
	GameServer gameServer;	
	LobbyServer lobbyServer;
	
	Player player1;
	Player player2;
	Player player3;
	PlayerState playerState1;
	PlayerState playerState2;
	PlayerState playerState3;
	
	@Before
	public void setUp() throws Exception {		
		player1.setName("Tick");
		player2.setName("Trick");
		player3.setName("Track");
		lobbyServer = new TestLobbyServer();
		player1 = new TestPlayer(lobbyServer);
		player2 = new TestPlayer(lobbyServer);
		player3 = new TestPlayer(lobbyServer);
		
		player1.setPlayerName("Tick");
		player2.setPlayerName("Trick");
		player3.setPlayerName("Track");
		gameServer = new TestGameServer(lobbyServer,player1,"Mein Spiel",RulesetType.Wizard, 
				"",false);
		ruleset = new ServerWizard(gameServer);
		
		ruleset.addPlayerToGame(player1.getPlayerName());
		ruleset.addPlayerToGame(player2.getPlayerName());
		ruleset.addPlayerToGame(player3.getPlayerName());
		
		playerState1 = ruleset.getPlayerState(player1.getPlayerName());
		playerState2 = ruleset.getPlayerState(player2.getPlayerName());
		playerState3 = ruleset.getPlayerState(player3.getPlayerName());
		
		ruleset.setFirstPlayer(ruleset.getPlayerState(player1.getPlayerName()));
		ruleset.setTrumpCard(WizardCard.VierRot);
		
		ruleset.giveACard(playerState1, WizardCard.DreiGruen);
		ruleset.giveACard(playerState1, WizardCard.ZaubererRot);
		ruleset.giveACard(playerState1, WizardCard.ZweiBlau);
		ruleset.giveACard(playerState1, WizardCard.NarrGruen);
		
		ruleset.giveACard(playerState2, WizardCard.ZweiGruen);
		ruleset.giveACard(playerState2, WizardCard.DreiRot);
		ruleset.giveACard(playerState2, WizardCard.ZweiGelb);
		ruleset.giveACard(playerState2, WizardCard.NarrRot);
		
		ruleset.giveACard(playerState3, WizardCard.NarrBlau);
		ruleset.giveACard(playerState3, WizardCard.ZehnGruen);
		ruleset.giveACard(playerState3, WizardCard.ZweiRot);
		ruleset.giveACard(playerState3, WizardCard.ZaubererGruen);
		
		ruleset.setGamePhase(GamePhase.CardRequest);
	}

	@After
	public void tearDown() throws Exception {
		lobbyServer = null;
		player1 = null;
		player2 = null;
		player3 = null;
		gameServer = null;
		ruleset = null;
	}

	@Test
	public void testResolveMessageMsgCardString() {
		fail("Noch nicht implementiert");
	}

}
