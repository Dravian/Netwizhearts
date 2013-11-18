package Ruleset;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ComObjects.MsgUser;

import test.TestPlayer;
import test.TestGameServer;
import test.TestLobbyServer;

public class TestStartWizard {
	TestPlayer blue;
	TestPlayer red;
	TestPlayer green;
	TestGameServer gameServer;
	TestLobbyServer lobbyServer;
	TestServerWizard wizard;
	
	@Before
	public void setUp() throws Exception {
		lobbyServer = new TestLobbyServer();
		blue = new TestPlayer(lobbyServer, null, "Blue");
		red = new TestPlayer(lobbyServer, null, "Red");
		green = new TestPlayer(lobbyServer,null, "Green");
		
		
		gameServer = new TestGameServer(lobbyServer,blue,"Mein Spiel",RulesetType.Wizard, 
				"",false);
		wizard = new TestServerWizard(gameServer);
		
		gameServer.addPlayer(red);
		gameServer.addPlayer(green);
		blue.changeServer(gameServer);
		
		wizard.addPlayerToGame("Blue");
		wizard.addPlayerToGame("Red");
		wizard.addPlayerToGame("Green");
	}
	
	@Test
	public void testStartWizard() {
		wizard.startGame();
		assertTrue(wizard.getFirstPlayer() == wizard.getPlayerState("Blue"));
		
		wizard.start1();
		assertTrue(wizard.getGameState().getRoundNumber() == 1);
		assertTrue(wizard.getGameState().getTrumpCard() == wizard.getTrumpCard());
	}
	
	@After
	public void tearDown() throws Exception {
		lobbyServer = null;
		blue = null;
		red = null;
		green = null;
		gameServer = null;
		wizard = null;
	}

}
