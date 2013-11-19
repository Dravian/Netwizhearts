package Ruleset;


import static org.junit.Assert.*;

import java.net.Socket;

import javax.net.SocketFactory;

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
	ServerRuleset wizard;
	
	@Before
	public void setUp() throws Exception {
		lobbyServer = new TestLobbyServer();
		
		blue = new TestPlayer(lobbyServer);
		blue.setPlayerName("Blue");
		
		red = new TestPlayer(lobbyServer);
		red.setPlayerName("Red");
		
		green = new TestPlayer(lobbyServer);
		green.setPlayerName("Green");
				
		gameServer = new TestGameServer(lobbyServer,blue,"Mein Spiel",RulesetType.Wizard, 
				"",false);
		wizard = new ServerWizard(gameServer);
		
		gameServer.addPlayer(blue);
		gameServer.addPlayer(red);
		gameServer.addPlayer(green);
		
		wizard.addPlayerToGame("Blue");
		wizard.addPlayerToGame("Red");
		wizard.addPlayerToGame("Green");
	}
	
	@Test
	public void testStartWizard() {
		wizard.runGame();
		assertTrue(wizard.getFirstPlayer() == wizard.getPlayerState("Blue"));
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
