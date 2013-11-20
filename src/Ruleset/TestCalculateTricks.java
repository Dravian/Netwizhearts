package Ruleset;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.TestGameServer;
import test.TestLobbyServer;
import test.TestPlayer;

import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestCalculateTricks {
	ServerRuleset ruleset;
	
	GameServer gameServer;
	
	LobbyServer lobbyServer;
	
	Player player;
	String player1;
	String player2;
	String player3;
	PlayerState playerState1;
	PlayerState playerState2;
	PlayerState playerState3;
	
	@Before
	public void setUp() throws Exception {
		player1 = "Tick";
		player2 = "Trick";
		player3 = "Track";
		lobbyServer = new TestLobbyServer();
		player = new TestPlayer(lobbyServer);
		gameServer = new TestGameServer(lobbyServer,"Tick","Mein Spiel",RulesetType.Wizard, 
				"",false);
		ruleset = new ServerWizard(gameServer);
		
		ruleset.addPlayerToGame(player1);
		ruleset.addPlayerToGame(player2);
		ruleset.addPlayerToGame(player3);
		
		playerState1 = ruleset.getPlayerState(player1);
		playerState2 = ruleset.getPlayerState(player2);
		playerState3 = ruleset.getPlayerState(player3);
		
		ruleset.setFirstPlayer(ruleset.getPlayerState(player1));
		ruleset.setTrumpCard(WizardCard.VierRot);
		
		ruleset.giveACard(playerState1, WizardCard.DreiGruen);
		ruleset.giveACard(playerState1, WizardCard.ZaubererRot);
		ruleset.giveACard(playerState1, WizardCard.ZweiBlau);
		ruleset.giveACard(playerState1, WizardCard.NarrGruen);
		
		ruleset.giveACard(playerState2, WizardCard.ZweiGruen);
		ruleset.giveACard(playerState2, WizardCard.DreiRot);
		ruleset.giveACard(playerState2, WizardCard.ZweiGelb);
		ruleset.giveACard(playerState2, WizardCard.NarrRot);
		
		ruleset.giveACard(playerState3, WizardCard.NarrBlau);
		ruleset.giveACard(playerState3, WizardCard.ZehnGruen);
		ruleset.giveACard(playerState3, WizardCard.ZweiRot);
		ruleset.giveACard(playerState3, WizardCard.ZaubererGruen);
	}

	@After
	public void tearDown() throws Exception {		
		lobbyServer = null;
		player = null;
		gameServer = null;
		ruleset = null;
	}

	@Test
	public void testCalculateTricks() {
		ruleset.setFirstPlayer(playerState1);	
		assertTrue(ruleset.playCard(WizardCard.ZaubererRot));
		
		ruleset.nextPlayer();
		assertTrue(ruleset.playCard(WizardCard.DreiRot));
	
		ruleset.nextPlayer();	
		assertTrue(ruleset.playCard(WizardCard.NarrBlau));
		
		
		assertTrue(ruleset.getPlayedCards().size() == 3);
		
		ruleset.calculateTricks();
		
		assertTrue(ruleset.getPlayedCards().size() == 0);
		assertTrue(playerState1.getOtherData().getNumberOfTricks() == 3);
		assertTrue(playerState2.getOtherData().getNumberOfTricks() == 0);
		assertTrue(playerState3.getOtherData().getNumberOfTricks() == 0);
	}
	
	@Test
	public void testCalculateTricksSecond() {
		ruleset.setFirstPlayer(playerState1);	
		assertTrue(ruleset.playCard(WizardCard.NarrGruen));
		
		ruleset.nextPlayer();
		assertTrue(ruleset.playCard(WizardCard.NarrRot));
	
		ruleset.nextPlayer();	
		assertTrue(ruleset.playCard(WizardCard.NarrBlau));
		
		
		assertTrue(ruleset.getPlayedCards().size() == 3);
		
		ruleset.calculateTricks();
		
		assertTrue(ruleset.getPlayedCards().size() == 0);
		assertTrue(playerState1.getOtherData().getNumberOfTricks() == 3);
		assertTrue(playerState2.getOtherData().getNumberOfTricks() == 0);
		assertTrue(playerState3.getOtherData().getNumberOfTricks() == 0);
	}
	
	@Test
	public void testCalculateTricksThird() {
		ruleset.setFirstPlayer(playerState1);	
		assertTrue(ruleset.playCard(WizardCard.ZweiBlau));
		
		ruleset.nextPlayer();
		assertTrue(ruleset.playCard(WizardCard.ZweiGruen));
	
		ruleset.nextPlayer();	
		assertTrue(ruleset.playCard(WizardCard.ZaubererGruen));
		
		
		assertTrue(ruleset.getPlayedCards().size() == 3);
		
		ruleset.calculateTricks();
		
		assertTrue(ruleset.getPlayedCards().size() == 0);
		assertTrue(playerState1.getOtherData().getNumberOfTricks() == 0);
		assertTrue(playerState2.getOtherData().getNumberOfTricks() == 0);
		assertTrue(playerState3.getOtherData().getNumberOfTricks() == 3);
	}
	
	@Test
	public void testCalculateTricksFourth() {
		ruleset.setFirstPlayer(playerState1);	
		assertTrue(ruleset.playCard(WizardCard.ZweiBlau));
		
		ruleset.nextPlayer();
		assertTrue(ruleset.playCard(WizardCard.DreiRot));
	
		ruleset.nextPlayer();	
		assertTrue(ruleset.playCard(WizardCard.ZweiRot));
		
		
		assertTrue(ruleset.getPlayedCards().size() == 3);
		
		ruleset.calculateTricks();
		
		assertTrue(ruleset.getPlayedCards().size() == 0);
		assertTrue(playerState1.getOtherData().getNumberOfTricks() == 0);
		assertTrue(playerState2.getOtherData().getNumberOfTricks() == 3);
		assertTrue(playerState3.getOtherData().getNumberOfTricks() == 0);
	}
	
	@Test
	public void testCalculateTricksFifth() {
		ruleset.setFirstPlayer(playerState1);	
		assertTrue(ruleset.playCard(WizardCard.ZweiBlau));
		
		ruleset.nextPlayer();
		assertTrue(ruleset.playCard(WizardCard.ZweiGelb));
	
		ruleset.nextPlayer();	
		assertTrue(ruleset.playCard(WizardCard.ZehnGruen));
		
		
		assertTrue(ruleset.getPlayedCards().size() == 3);
		
		ruleset.calculateTricks();
		
		assertTrue(ruleset.getPlayedCards().size() == 0);
		assertTrue(playerState1.getOtherData().getNumberOfTricks() == 3);
		assertTrue(playerState2.getOtherData().getNumberOfTricks() == 0);
		assertTrue(playerState3.getOtherData().getNumberOfTricks() == 0);
	}

}
