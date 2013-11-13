package Ruleset;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testKlassen.TestPlayer;
import ComObjects.ComObject;
import ComObjects.ComRuleset;
import ComObjects.MsgGameEnd;
import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestHeartsWinner {

LobbyServer lobbyServer;
	
	GameServer gameServer;
	
	ServerRuleset heartsServerRuleset;
	
	TestPlayer blue;
	
	TestPlayer white;
	
	TestPlayer orange;
	
	TestPlayer brown;
	
	List<ComObject> inputList;
	
	ComRuleset comObject;
	
	MsgGameEnd endMsg;
	
	String winner;
	
	@Before
	public void setUp() {
		lobbyServer = new LobbyServer();
		blue = new TestPlayer(lobbyServer, null, null);
		white = new TestPlayer(lobbyServer, null, null);
		orange = new TestPlayer(lobbyServer, null, null);
		brown = new TestPlayer(lobbyServer, null, null);
	}
	
	@After
	public void tearDown() {	
		blue = null;
		white = null;
		orange = null;
		brown = null;
		lobbyServer = null;
		gameServer = null;
		inputList = null;
		comObject = null;
		endMsg = null;
		winner = null;
	}
	
	@Test
	public void testGetWinner() {
		
		gameServer = new GameServer(lobbyServer, blue, "Test Game", RulesetType.Wizard, "", false);
		gameServer.addPlayer(white);
		gameServer.addPlayer(orange);
		gameServer.addPlayer(brown);
		
		heartsServerRuleset = new ServerHearts(gameServer);
		heartsServerRuleset.addPlayerToGame("Mr. Blue");
		heartsServerRuleset.addPlayerToGame("Mr. White");
		heartsServerRuleset.addPlayerToGame("Mr. Orange");
		heartsServerRuleset.addPlayerToGame("Mr. Brown");
		heartsServerRuleset.setPoints(heartsServerRuleset.getPlayerState("Mr. Blue"),80);
		heartsServerRuleset.setPoints(heartsServerRuleset.getPlayerState("Mr. White"),20);
		heartsServerRuleset.setPoints(heartsServerRuleset.getPlayerState("Mr. Orange"),60);
		heartsServerRuleset.setPoints(heartsServerRuleset.getPlayerState("Mr. Brown"),110);
		heartsServerRuleset.setGamePhase(GamePhase.Ending);
		heartsServerRuleset.calculateRoundOutcome();
		
		assertTrue(heartsServerRuleset.getWinner().equals("Mr. Blue"));
		
		inputList = blue.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner = endMsg.getWinner();
		assertEquals("Nachricht an Blue", "Mr. Blue", winner);

		inputList = white.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner = endMsg.getWinner();
		assertEquals("Nachricht an White", "Mr. Blue", winner);

		inputList = orange.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner = endMsg.getWinner();
		assertEquals("Nachricht an Orange", "Mr. Blue", winner);
	
		inputList = brown.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner = endMsg.getWinner();
		assertEquals("Nachricht an Brown", "Mr. Blue", winner);
	}
}
