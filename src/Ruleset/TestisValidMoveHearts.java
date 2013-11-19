package Ruleset;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import test.TestGameServer;
import test.TestLobbyServer;
import test.TestPlayer;

import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestisValidMoveHearts {
	ServerRuleset ruleset;
	
	GameServer gameServer;
	
	LobbyServer lobbyServer;
	
	Player player;
	String player1;
	String player2;
	String player3;
	String player4;
	
	PlayerState playerState1;
	PlayerState playerState2;
	PlayerState playerState3;
	PlayerState playerState4;
	
	@Before
	public void setUp() throws Exception {
		player1 = "Tick";
		player2 = "Trick";
		player3 = "Track";
		player3 = "Duck";
		lobbyServer = new TestLobbyServer();
		player = new TestPlayer(lobbyServer);
		gameServer = new TestGameServer(lobbyServer,"Tick","Mein Spiel",
				RulesetType.Hearts, "",false);
		ruleset = new ServerHearts(gameServer);
		
		ruleset.addPlayerToGame(player1);
        ruleset.addPlayerToGame(player2);
        ruleset.addPlayerToGame(player3);
        ruleset.addPlayerToGame(player4);
        
        playerState1 = ruleset.getPlayerState(player1);
        playerState2 = ruleset.getPlayerState(player2);
        playerState3 = ruleset.getPlayerState(player3);

        ruleset.setFirstPlayer(playerState1);
	}
	
	@Test
	public void testIsValidMove() {
		 ruleset.giveACard(playerState1, HeartsCard.Herz2);
	     ruleset.giveACard(playerState1, HeartsCard.Kreuz9);
	     ruleset.giveACard(playerState2, HeartsCard.Caro3);
	     ruleset.giveACard(playerState2, HeartsCard.Caro6);
	     ruleset.giveACard(playerState3, HeartsCard.Pik4);
	     ruleset.giveACard(playerState3, HeartsCard.Pik5);
         ruleset.giveACard(playerState4, HeartsCard.Pik2);
	     ruleset.giveACard(playerState4, HeartsCard.Herz7);     
	 
	     boolean isValidMove = ruleset.isValidMove(HeartsCard.Herz2);

	     assertFalse(isValidMove);

	     boolean isValidMove2 = ruleset.isValidMove(HeartsCard.Caro3);
	     
	     assertTrue(isValidMove2);
	}
	
	@Test
	public void testIsValidMoveOnlyHearts() {
		 ruleset.giveACard(playerState1, HeartsCard.Herz2);
	     ruleset.giveACard(playerState1, HeartsCard.Herz5);
	     ruleset.giveACard(playerState2, HeartsCard.Caro3);
	     ruleset.giveACard(playerState2, HeartsCard.Caro6);
	     ruleset.giveACard(playerState3, HeartsCard.Pik4);
	     ruleset.giveACard(playerState3, HeartsCard.Pik5);
         ruleset.giveACard(playerState4, HeartsCard.Pik2);
	     ruleset.giveACard(playerState4, HeartsCard.Herz7);
	     
	     boolean isValidMove = ruleset.isValidMove(HeartsCard.Herz2);

	     assertTrue(isValidMove);

	     boolean isValidMove2 = ruleset.isValidMove(HeartsCard.Herz5);
	     
	     assertTrue(isValidMove2);
	}

}
