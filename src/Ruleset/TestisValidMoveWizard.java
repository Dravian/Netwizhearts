package Ruleset;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.TestGameServer;
import test.TestLobbyServer;
import test.TestPlayer;

import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestisValidMoveWizard {
	
	ServerRuleset ruleset;
	
	GameServer gameServer;
	
	LobbyServer lobbyServer;
	
	Player player;
	String player1;
	String player2;
	String player3;
	
	@Before
	public void setUp() throws Exception {
		player1 = "Tick";
		player2 = "Trick";
		player3 = "Track";
		lobbyServer = new TestLobbyServer();
		player = new TestPlayer(lobbyServer,null,null);
		gameServer = new TestGameServer(lobbyServer,player,"Mein Spiel",RulesetType.Wizard, 
				"",false);
		ruleset = new ServerWizard(gameServer);
		
		ruleset.addPlayerToGame(player1);
		ruleset.addPlayerToGame(player2);
		ruleset.addPlayerToGame(player3);
		
		ruleset.setFirstPlayer(ruleset.getPlayerState(player1));
		ruleset.setTrumpCard(WizardCard.VierRot);
		
		ruleset.giveACard(player1, WizardCard.DreiGruen);
		ruleset.giveACard(player1, WizardCard.ZaubererRot);
		ruleset.givaACard(player1, WizardCard.ZweiBlau);
		
		ruleset.giveACard(player2, WizardCard.ZweiGruen);
		ruleset.giveACard(player2, WizardCard.DreiRot);
		ruleset.givaACard(player2, WizardCard.ZweiGelb);
		
		ruleset.giveACard(player3, WizardCard.NarrBlau);
		ruleset.giveACard(player3, WizardCard.EinsGruen);
		ruleset.giveACard(player3, WizardCard.ZweiRot);
	}
	
	@Test
	public void testSorcerer() {
		ruleset.playCard(WizardCard.ZaubererRot);
		ruleset.setCurrentPlayer(ruleset.getPlayerState(player2));
		
		boolean isValidMove = ruleset.isValidMove(WizardCard.DreiRot);
		
		assertTrue(isValidMove);
	}
	
	@Test
	public void testRed3OnGreen3() {
		ruleset.playCard(WizardCard.DreiGruen);
		ruleset.setCurrentPlayer(ruleset.getPlayerState(player2));
		boolean isValidMove = ruleset.isValidMove(WizardCard.DreiRot);
		
		assertFalse(isValidMove);
	}
	
	@Test
	public void testGreen2OnGreen3() {
		ruleset.playCard(WizardCard.DreiGruen);
		ruleset.setCurrentPlayer(ruleset.getPlayerState(player2));
		
		boolean isValidMove = ruleset.isValidMove(WizardCard.ZweiGruen);
		
		assertTrue(isValidMove);
	}
	
	@Test
	public void testFoolBlueOnGreen2OnGreen3() {
		ruleset.playCard(WizardCard.DreiGruen);
		ruleset.setCurrentPlayer(ruleset.getPlayerState(player2));
		
		ruleset.playCard(WizardCard.ZweiGruen);
		ruleset.setCurrentPlayer(ruleset.getPlayerState(player3));
		
		boolean isValidMove = ruleset.isValidMove(WizardCard.NarrBlau);
		
		assertTrue(isValidMove);
	}

}
