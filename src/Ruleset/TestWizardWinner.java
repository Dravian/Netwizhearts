package Ruleset;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.TestGameServer;
import test.TestPlayer;
import test.TestLobbyServer;
import ComObjects.ComObject;
import ComObjects.ComRuleset;
import ComObjects.MsgGameEnd;

/**
 * Testet ob der richtige Sieger ermittelt wird und ob jedem Mitspieler
 * der richtige Sieger mitgeteilt wird
 */
public class TestWizardWinner {

	TestLobbyServer lobbyServer;
	
	TestGameServer gameServer;
	
	ServerRuleset wizardServerRuleset;
	
	TestPlayer blue;
	
	TestPlayer white;
	
	TestPlayer orange;
	
	TestPlayer brown;
	
	List<ComObject> inputList;
	
	ComRuleset comObject;
	
	MsgGameEnd endMsg;
	
	String winner;
	
	String Blue = "Mr. Blue";
	String White = "Mr. White";
	String Orange = "Mr. Orange";
	String Brown = "Mr. Brown";
	
	@Before
	public void setUp() {
		lobbyServer = new TestLobbyServer();
		
		blue = new TestPlayer(lobbyServer);
		blue.setPlayerName(Blue);
		white = new TestPlayer(lobbyServer);
		white.setPlayerName(White);
		orange = new TestPlayer(lobbyServer);
		orange.setPlayerName(Orange);
		brown = new TestPlayer(lobbyServer);
		brown.setPlayerName(Brown);
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
		
		gameServer = new TestGameServer(lobbyServer, Blue, "Test Game", RulesetType.Wizard, "", false);
		gameServer.addPlayer(blue);
		gameServer.addPlayer(white);
		gameServer.addPlayer(orange);
		gameServer.addPlayer(brown);
		
		wizardServerRuleset = new ServerWizard(gameServer);
		wizardServerRuleset.addPlayerToGame(Blue);
		wizardServerRuleset.addPlayerToGame(White);
		wizardServerRuleset.addPlayerToGame(Orange);
		wizardServerRuleset.addPlayerToGame(Brown);
		wizardServerRuleset.setFirstPlayer(wizardServerRuleset.getPlayerState(Blue));
		
		
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState(Blue),80);
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState(White),200);
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState(Orange),130);
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState(Brown),240);
		wizardServerRuleset.setGamePhase(GamePhase.Ending);
		
		wizardServerRuleset.calculateRoundOutcome();
		
		assertTrue((wizardServerRuleset.getWinners().get(0)).equals(Brown));
		
		assertTrue(blue.getServerInput().size() == 2);
		
		inputList = blue.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner = endMsg.getWinnerName();
		assertEquals("Nachricht an Blue", "Mr. Brown", winner);

		inputList = white.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner = endMsg.getWinnerName();
		assertEquals("Nachricht an White", "Mr. Brown", winner);

		inputList = orange.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner = endMsg.getWinnerName();
		assertEquals("Nachricht an Orange", "Mr. Brown", winner);
	
		inputList = brown.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner = endMsg.getWinnerName();
		assertEquals("Nachricht an Brown", "Mr. Brown", winner);
	}
}
