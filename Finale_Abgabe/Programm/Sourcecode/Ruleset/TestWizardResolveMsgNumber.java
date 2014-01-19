package Ruleset;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.MockGameServer;
import test.MockPlayer;
import ComObjects.ComRuleset;
import ComObjects.MsgCardRequest;
import ComObjects.MsgNumber;
import ComObjects.MsgNumberRequest;
import Server.LobbyServer;

public class TestWizardResolveMsgNumber {
	ServerRuleset ruleset;	
	MockGameServer gameServer;	
	LobbyServer lobbyServer;
	
	MockPlayer player1;
	MockPlayer player2;
	MockPlayer player3;
	PlayerState playerState1;
	PlayerState playerState2;
	PlayerState playerState3;
	
	@Before
	public void setUp() throws Exception {		
		lobbyServer = new LobbyServer();
		player1 = new MockPlayer(lobbyServer);
		player2 = new MockPlayer(lobbyServer);
		player3 = new MockPlayer(lobbyServer);
		
		player1.setName("Tick");
		player2.setName("Trick");
		player3.setName("Track");
		
		player1.setPlayerName("Tick");
		player2.setPlayerName("Trick");
		player3.setPlayerName("Track");
		gameServer = new MockGameServer(lobbyServer,player1,"Mein Spiel",RulesetType.Wizard, 
				"",false);
		ruleset = gameServer.getRuleset();
		gameServer.addPlayer(player1);
		gameServer.addPlayer(player2);
		gameServer.addPlayer(player3);
		
		ruleset.addPlayerToGame(player1.getPlayerName());
		ruleset.addPlayerToGame(player2.getPlayerName());
		ruleset.addPlayerToGame(player3.getPlayerName());
		
		playerState1 = ruleset.getPlayerState(player1.getPlayerName());
		playerState2 = ruleset.getPlayerState(player2.getPlayerName());
		playerState3 = ruleset.getPlayerState(player3.getPlayerName());
		
		ruleset.setFirstPlayer(ruleset.getPlayerState(player1.getPlayerName()));
		
		ruleset.giveACard(playerState1, WizardCard.DreiGruen);
		ruleset.giveACard(playerState2, WizardCard.ZweiGruen);
		ruleset.giveACard(playerState3, WizardCard.NarrBlau);
		
		ruleset.setGamePhase(GamePhase.TrickRequest);
		
		ruleset.nextPlayer();
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
	public void test() {
		
		
		ComRuleset rules = new ComRuleset(new MsgNumber(1));
		player2.injectComObject(rules);
		
		assertTrue(player1.getServerInput().size() == 1);
		assertTrue(player2.getServerInput().size() == 1);
		assertTrue(player3.getServerInput().size() == 2);
		
		assertTrue(((ComRuleset)player3.getServerInput().get(1)).
				getRulesetMessage() instanceof MsgNumberRequest);
		
		player1.empty();
		player2.empty();
		player3.empty();
		
		player3.injectComObject(new ComRuleset(new MsgNumber(0)));
		
		assertTrue(player1.getServerInput().size() == 2);
		assertTrue(player2.getServerInput().size() == 1);
		assertTrue(player3.getServerInput().size() == 1);
		
		assertTrue(((ComRuleset)player1.getServerInput().get(1)).
				getRulesetMessage() instanceof MsgNumberRequest);
		
		player1.empty();
		player2.empty();
		player3.empty();
		
		player1.injectComObject(new ComRuleset(new MsgNumber(0)));
		
		assertTrue(player1.getServerInput().size() == 1);
		assertTrue(player2.getServerInput().size() == 2);
		assertTrue(player3.getServerInput().size() == 1);
		
		assertTrue(((ComRuleset)player2.getServerInput().get(1)).
				getRulesetMessage() instanceof MsgCardRequest);
		
		player1.empty();
		player2.empty();
		player3.empty();
	}
	
	@Test(expected = IllegalStateException.class)  
	public void testWrongState() {
		ruleset.setGamePhase(GamePhase.Ending);
		
		ComRuleset rules = new ComRuleset(new MsgNumber(0));
		player2.injectComObject(rules);	
	}
	
	@Test(expected = IllegalArgumentException.class)  
	public void testWrongPlayer() {	
		ComRuleset rules = new ComRuleset(new MsgNumber(0));
		player1.injectComObject(rules);	
	}
	
	@Test(expected = IllegalArgumentException.class)  
	public void testUnvalidNumber() {	
		ComRuleset rules = new ComRuleset(new MsgNumber(10));
		player2.injectComObject(rules);	
	}

}
