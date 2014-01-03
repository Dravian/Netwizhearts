package Ruleset;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.TestGameServer;
import test.TestLobbyServer;
import test.TestPlayer;
import ComObjects.ComObject;
import ComObjects.ComRuleset;
import ComObjects.MsgGameEnd;

public class TestRoundEndHearts {

	TestLobbyServer lobbyServer;
	
	TestGameServer gameServer;
	
	ServerRuleset heartsServerRuleset;
	
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
		
		gameServer = new TestGameServer(lobbyServer, blue, "Test Game", RulesetType.Hearts, "", false);
		gameServer.addPlayer(blue);
		gameServer.addPlayer(white);
		gameServer.addPlayer(orange);
		gameServer.addPlayer(brown);
		
		heartsServerRuleset = gameServer.getRuleset();
		heartsServerRuleset.addPlayerToGame(Blue);
		heartsServerRuleset.addPlayerToGame(White);
		heartsServerRuleset.addPlayerToGame(Orange);
		heartsServerRuleset.addPlayerToGame(Brown);
		heartsServerRuleset.setFirstPlayer(heartsServerRuleset.getPlayerState(Blue));
	
		heartsServerRuleset.setGamePhase(GamePhase.RoundEnd);
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
	public void roundEnd() {
		Set<Card> cards1 = new HashSet<Card>();
		Set<Card> cards2 = new HashSet<Card>();
		cards1.add(HeartsCard.CaroAss);
		cards1.add(HeartsCard.HerzAss);
		cards1.add(HeartsCard.Herz9);
		cards1.add(HeartsCard.Caro4);
		
		cards2.add(HeartsCard.PikKoenig);
		cards2.add(HeartsCard.Herz7);
		cards2.add(HeartsCard.PikDame);
		cards2.add(HeartsCard.Kreuz8);
		
		heartsServerRuleset.getGameState().getPlayerState(Blue).
			getOtherData().madeTrick(cards1);
		heartsServerRuleset.getGameState().getPlayerState(White).
			getOtherData().madeTrick(cards2);
		
		heartsServerRuleset.calculateRoundOutcome();
		
		assertTrue(heartsServerRuleset.getGameState().getPlayerState(Blue)
				.getOtherData().getPoints() == 2);
		assertTrue(heartsServerRuleset.getGameState().getPlayerState(White)
				.getOtherData().getPoints() == 14);
		assertTrue(heartsServerRuleset.getGameState().getPlayerState(Orange)
				.getOtherData().getPoints() == 0);
		assertTrue(heartsServerRuleset.getGameState().getPlayerState(Brown)
				.getOtherData().getPoints() == 0);
	}
	
	@Test
	public void testGetWinner() {
		
		heartsServerRuleset.setPoints(heartsServerRuleset.getPlayerState(Blue),80);
		heartsServerRuleset.setPoints(heartsServerRuleset.getPlayerState(White),110);
		heartsServerRuleset.setPoints(heartsServerRuleset.getPlayerState(Orange),30);
		heartsServerRuleset.setPoints(heartsServerRuleset.getPlayerState(Brown),30);
		
		heartsServerRuleset.calculateRoundOutcome();
		
		assertTrue(heartsServerRuleset.getWinners().size() == 2);
		
		assertTrue((heartsServerRuleset.getWinners().get(0)).equals(Orange));
		assertTrue((heartsServerRuleset.getWinners().get(1).equals(Brown)));
		
		inputList = blue.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner1 = endMsg.getWinnerName().get(0);
		winner2 = endMsg.getWinnerName().get(1);
		assertEquals("Nachricht an Blue", "Mr. Orange", winner1);
		assertEquals("Nachricht an Blue", "Mr. Brown", winner2);

		inputList = white.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner2 = endMsg.getWinnerName().get(1);
		winner1 = endMsg.getWinnerName().get(0);
		assertEquals("Nachricht an White", "Mr. Orange", winner1);
		assertEquals("Nachricht an White", "Mr. Brown", winner2);

		inputList = orange.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner2 = endMsg.getWinnerName().get(1);
		winner1 = endMsg.getWinnerName().get(0);
		assertEquals("Nachricht an Orange", "Mr. Orange", winner1);
		assertEquals("Nachricht an Orange", "Mr. Brown", winner2);
	
		inputList = brown.getServerInput();
		comObject = (ComRuleset) inputList.get(1);
		endMsg = (MsgGameEnd) comObject.getRulesetMessage();
		winner2 = endMsg.getWinnerName().get(1);
		winner1 = endMsg.getWinnerName().get(0);
		assertEquals("Nachricht an Brown", "Mr. Orange", winner1);
		assertEquals("Nachricht an Brown", "Mr. Brown", winner2);
	}

}
