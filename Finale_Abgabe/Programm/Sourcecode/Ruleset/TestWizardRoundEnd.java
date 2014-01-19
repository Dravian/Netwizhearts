package Ruleset;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.MockGameServer;
import test.MockPlayer;
import test.MockLobbyServer;
import ComObjects.ComObject;
import ComObjects.ComRuleset;
import ComObjects.MsgGameEnd;

/**
 * Testet ob der richtige Sieger ermittelt wird und ob jedem Mitspieler
 * der richtige Sieger mitgeteilt wird
 */
public class TestWizardRoundEnd {

	MockLobbyServer lobbyServer;
	
	MockGameServer gameServer;
	
	ServerRuleset wizardServerRuleset;
	
	MockPlayer blue;
	
	MockPlayer white;
	
	MockPlayer orange;
	
	MockPlayer brown;
	
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
		lobbyServer = new MockLobbyServer();
		
		blue = new MockPlayer(lobbyServer);
		blue.setPlayerName(Blue);
		white = new MockPlayer(lobbyServer);
		white.setPlayerName(White);
		orange = new MockPlayer(lobbyServer);
		orange.setPlayerName(Orange);
		brown = new MockPlayer(lobbyServer);
		brown.setPlayerName(Brown);
		
		gameServer = new MockGameServer(lobbyServer, blue, "Test Game", RulesetType.Wizard, "", false);
		gameServer.addPlayer(blue);
		gameServer.addPlayer(white);
		gameServer.addPlayer(orange);
		gameServer.addPlayer(brown);
		
		wizardServerRuleset = gameServer.getRuleset();
		wizardServerRuleset.addPlayerToGame(Blue);
		wizardServerRuleset.addPlayerToGame(White);
		wizardServerRuleset.addPlayerToGame(Orange);
		wizardServerRuleset.addPlayerToGame(Brown);
		wizardServerRuleset.setFirstPlayer(wizardServerRuleset.getPlayerState(Blue));
		((ServerWizard)wizardServerRuleset).setPlayingRounds(15);
	
		wizardServerRuleset.setGamePhase(GamePhase.RoundEnd);
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
		blueCards.add(WizardCard.AchtBlau);
		blueCards.add(WizardCard.AchtGelb);
		blueCards.add(WizardCard.AchtGruen);
		blueCards.add(WizardCard.AchtRot);
		
		whiteCards.add(WizardCard.ZaubererBlau);
		whiteCards.add(WizardCard.ZaubererGelb);
		whiteCards.add(WizardCard.ZaubererGruen);
		whiteCards.add(WizardCard.ZaubererRot);
		
		((ServerWizard)wizardServerRuleset).getGameState().
			getPlayerState(Blue).getOtherData().setAnnouncedTricks(1);
		((ServerWizard)wizardServerRuleset).getGameState().
			getPlayerState(White).getOtherData().setAnnouncedTricks(0);
		((ServerWizard)wizardServerRuleset).getGameState().
			getPlayerState(Orange).getOtherData().setAnnouncedTricks(0);
		((ServerWizard)wizardServerRuleset).getGameState().
			getPlayerState(Brown).getOtherData().setAnnouncedTricks(1);
			
		
		wizardServerRuleset.getGameState().getPlayerState(Blue).
			getOtherData().madeTrick(blueCards);
		wizardServerRuleset.getGameState().getPlayerState(White).
			getOtherData().madeTrick(whiteCards);
		
		wizardServerRuleset.calculateRoundOutcome();
		
		assertTrue(wizardServerRuleset.getGameState().getPlayerState(Blue)
				.getOtherData().getPoints() == 30);
		assertTrue(wizardServerRuleset.getGameState().getPlayerState(White)
				.getOtherData().getPoints() == -10);
		assertTrue(wizardServerRuleset.getGameState().getPlayerState(Orange)
				.getOtherData().getPoints() == 20);
		assertTrue(wizardServerRuleset.getGameState().getPlayerState(Brown)
				.getOtherData().getPoints() == -10);
	}
	
	@Test
	public void testGetWinner() {	
		
		for(int i = 1; i < 15; i++) {
			wizardServerRuleset.getGameState().nextRound();
		}
		
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState(Blue),80);
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState(White),240);
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState(Orange),130);
		wizardServerRuleset.setPoints(wizardServerRuleset.getPlayerState(Brown),240);
		
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
