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
	
	String winner1;
	String winner2;
	
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
		winner1 = null;
		winner2 = null;
	}
	
	@Test
	public void testGetWinner() {
		
		gameServer = new TestGameServer(lobbyServer, blue, "Test Game", RulesetType.Wizard, "", false);
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
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState(White),240);
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState(Orange),130);
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState(Brown),240);
		wizardServerRuleset.setGamePhase(GamePhase.Ending);
		
		wizardServerRuleset.calculateRoundOutcome();
		
		assertTrue(wizardServerRuleset.getWinners().size() == 2);
		
		assertTrue((wizardServerRuleset.getWinners().get(0)).equals(White));
		assertTrue((wizardServerRuleset.getWinners().get(1).equals(Brown)));
		
		inputList = blue.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner1 = endMsg.getWinnerName().get(0);
		winner2 = endMsg.getWinnerName().get(1);
		assertEquals("Nachricht an Blue", "Mr. White", winner1);
		assertEquals("Nachricht an Blue", "Mr. Brown", winner2);

		inputList = white.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner2 = endMsg.getWinnerName().get(1);
		winner1 = endMsg.getWinnerName().get(0);
		assertEquals("Nachricht an White", "Mr. White", winner1);
		assertEquals("Nachricht an White", "Mr. Brown", winner2);

		inputList = orange.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner2 = endMsg.getWinnerName().get(1);
		winner1 = endMsg.getWinnerName().get(0);
		assertEquals("Nachricht an Orange", "Mr. White", winner1);
		assertEquals("Nachricht an Orange", "Mr. Brown", winner2);
	
		inputList = brown.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner2 = endMsg.getWinnerName().get(1);
		winner1 = endMsg.getWinnerName().get(0);
		assertEquals("Nachricht an Brown", "Mr. White", winner1);
		assertEquals("Nachricht an Brown", "Mr. Brown", winner2);
		
	}
}
