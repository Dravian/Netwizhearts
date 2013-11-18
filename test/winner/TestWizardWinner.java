package Ruleset;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testKlassen.TestPlayer;
import ComObjects.ComChatMessage;
import ComObjects.ComObject;
import ComObjects.ComRuleset;
import ComObjects.MsgGameEnd;
import Server.GameServer;
import Server.LobbyServer;

/**
 * Testet ob der richtige Sieger ermittelt wird und ob jedem Mitspieler
 * der richtige Sieger mitgeteilt wird
 */
public class TestWizardWinner {

	LobbyServer lobbyServer;
	
	GameServer gameServer;
	
	ServerRuleset wizardServerRuleset;
	
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
		blue = new TestPlayer(lobbyServer, null);
		white = new TestPlayer(lobbyServer, null);
		orange = new TestPlayer(lobbyServer, null);
		brown = new TestPlayer(lobbyServer, null);
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
		
		wizardServerRuleset = new ServerWizard(gameServer);
		wizardServerRuleset.addPlayerToGame("Mr. Blue");
		wizardServerRuleset.addPlayerToGame("Mr. White");
		wizardServerRuleset.addPlayerToGame("Mr. Orange");
		wizardServerRuleset.addPlayerToGame("Mr. Brown");
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState("Mr. Blue"),80);
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState("Mr. White"),200);
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState("Mr. Orange"),130);
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState("Mr. Brown"),240);
		wizardServerRuleset.setGamePhase(GamePhase.Ending);
		wizardServerRuleset.calculateRoundOutcome();
		
		assertTrue(wizardServerRuleset.getWinner().equals("Mr. Brown"));
		
		inputList = blue.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner = endMsg.getWinner();
		assertEquals("Nachricht an Blue", "Mr. Brown", winner);

		inputList = white.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner = endMsg.getWinner();
		assertEquals("Nachricht an White", "Mr. Brown", winner);

		inputList = orange.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner = endMsg.getWinner();
		assertEquals("Nachricht an Orange", "Mr. Brown", winner);
	
		inputList = brown.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner = endMsg.getWinner();
		assertEquals("Nachricht an Brown", "Mr. Brown", winner);
	}
}
