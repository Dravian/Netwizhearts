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
import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestHeartsRoundEnd {

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
		Set<Card> blueCards = new HashSet<Card>();
		Set<Card> whiteCards = new HashSet<Card>();
		blueCards.add(HeartsCard.Herz4);
		blueCards.add(HeartsCard.Herz10);
		blueCards.add(HeartsCard.Herz2);
		blueCards.add(HeartsCard.Herz3);
		
		whiteCards.add(HeartsCard.Herz6);
		whiteCards.add(HeartsCard.PikDame);
		whiteCards.add(HeartsCard.Caro10);
		whiteCards.add(HeartsCard.Caro2);
		
		
		heartsServerRuleset.getGameState().getPlayerState(Blue).
			getOtherData().madeTrick(blueCards);
		heartsServerRuleset.getGameState().getPlayerState(White).
			getOtherData().madeTrick(whiteCards);
		
		heartsServerRuleset.calculateRoundOutcome();
		
		assertTrue(heartsServerRuleset.getGameState().getPlayerState(Blue)
				.getOtherData().getPoints() == 4);
		assertTrue(heartsServerRuleset.getGameState().getPlayerState(White)
				.getOtherData().getPoints() == 14);
		assertTrue(heartsServerRuleset.getGameState().getPlayerState(Orange)
				.getOtherData().getPoints() == 0);
		assertTrue(heartsServerRuleset.getGameState().getPlayerState(Brown)
				.getOtherData().getPoints() == 0);
	}
	
	@Test
	public void testGetWinner() {
		
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
		
		
		heartsServerRuleset.setPoints(heartsServerRuleset.getPlayerState(Blue),102);
		heartsServerRuleset.setPoints(heartsServerRuleset.getPlayerState(White),88);
		heartsServerRuleset.setPoints(heartsServerRuleset.getPlayerState(Orange),90);
		heartsServerRuleset.setPoints(heartsServerRuleset.getPlayerState(Brown),88);
		heartsServerRuleset.setGamePhase(GamePhase.RoundEnd);
		
		heartsServerRuleset.calculateRoundOutcome();
		
		assertTrue(heartsServerRuleset.getWinners().get(0).equals("Mr. White"));
		
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
