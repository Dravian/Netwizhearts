package Ruleset;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.TestPlayer;
import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestHeartsCalculateTricks {
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
		player4 = "Donald";
		lobbyServer = new LobbyServer();
		player = new TestPlayer(lobbyServer);
		gameServer = new GameServer(lobbyServer,player,"Mein Spiel",RulesetType.Hearts, 
				"",false);
		ruleset = new ServerHearts(gameServer);
		
		ruleset.addPlayerToGame(player1);
		ruleset.addPlayerToGame(player2);
		ruleset.addPlayerToGame(player3);
		ruleset.addPlayerToGame(player4);
		
		playerState1 = ruleset.getPlayerState(player1);
		playerState2 = ruleset.getPlayerState(player2);
		playerState3 = ruleset.getPlayerState(player3);
		playerState4 = ruleset.getPlayerState(player4);
		
		ruleset.setFirstPlayer(ruleset.getPlayerState(player1));
	}

	@After
	public void tearDown() throws Exception {
		lobbyServer = null;
		player = null;
		gameServer = null;
		ruleset = null;
	}

	@Test
	public void test() {
		ruleset.giveACard(playerState1, HeartsCard.Caro10);
		ruleset.giveACard(playerState1, HeartsCard.Pik2);
		ruleset.giveACard(playerState1, HeartsCard.Herz10);
		ruleset.giveACard(playerState1, HeartsCard.KreuzAss);
		
		ruleset.giveACard(playerState2, HeartsCard.Caro2);
		ruleset.giveACard(playerState2, HeartsCard.Caro3);
		ruleset.giveACard(playerState2, HeartsCard.Caro4);
		ruleset.giveACard(playerState2, HeartsCard.Caro5);
		
		ruleset.giveACard(playerState3, HeartsCard.PikDame);
		ruleset.giveACard(playerState3, HeartsCard.CaroBube);
		ruleset.giveACard(playerState3, HeartsCard.Herz2);
		ruleset.giveACard(playerState3, HeartsCard.Pik10);
		
		ruleset.giveACard(playerState4, HeartsCard.HerzAss);
		ruleset.giveACard(playerState4, HeartsCard.HerzBube);
		ruleset.giveACard(playerState4, HeartsCard.HerzKoenig);
		ruleset.giveACard(playerState4, HeartsCard.HerzDame);
		
		assertTrue(ruleset.playCard(HeartsCard.Caro10));
		ruleset.nextPlayer();
		
		assertTrue(ruleset.playCard(HeartsCard.Caro2));
		ruleset.nextPlayer();
		
		assertTrue(ruleset.playCard(HeartsCard.CaroBube));
		ruleset.nextPlayer();
		
		assertTrue(ruleset.playCard(HeartsCard.HerzAss));
		ruleset.nextPlayer();
		
		ruleset.calculateTricks();
		
		assertTrue(ruleset.getPlayedCards().size() == 0);
		assertTrue(playerState1.getOtherData().getNumberOfTricks() == 0);
		assertTrue(playerState2.getOtherData().getNumberOfTricks() == 0);
		assertTrue(playerState3.getOtherData().getNumberOfTricks() == 1);
		assertTrue(playerState4.getOtherData().getNumberOfTricks() == 0);
	}
	
	@Test
	public void testNoMoreCards() {
		ruleset.giveACard(playerState1, HeartsCard.Caro10);
		
		ruleset.giveACard(playerState2, HeartsCard.Caro2);
		
		ruleset.giveACard(playerState3, HeartsCard.CaroBube);
		
		ruleset.giveACard(playerState4, HeartsCard.HerzAss);
		
		
		assertTrue(ruleset.playCard(HeartsCard.Caro10));
		ruleset.nextPlayer();
		
		assertTrue(ruleset.playCard(HeartsCard.Caro2));
		ruleset.nextPlayer();
		
		assertTrue(ruleset.playCard(HeartsCard.CaroBube));
		ruleset.nextPlayer();
		
		assertTrue(ruleset.playCard(HeartsCard.HerzAss));
		ruleset.nextPlayer();
		
		ruleset.calculateTricks();
		
		assertTrue(ruleset.getPlayedCards().size() == 0);
		assertTrue(playerState1.getOtherData().getPoints() == 0);
		assertTrue(playerState2.getOtherData().getPoints() == 0);
		assertTrue(playerState3.getOtherData().getPoints() == 1);
		assertTrue(playerState4.getOtherData().getPoints() == 0);
		assertTrue(ruleset.getPlayedCards().size() == 0);
	}
	
	@Test(expected = RulesetException.class)
	public void testException() {
		ruleset.giveACard(playerState1, HeartsCard.Caro10);
		
		ruleset.giveACard(playerState2, HeartsCard.Caro2);
		
		ruleset.giveACard(playerState3, HeartsCard.CaroBube);
		
		ruleset.giveACard(playerState4, HeartsCard.HerzAss);
		
		
		assertTrue(ruleset.playCard(HeartsCard.Caro10));
		ruleset.nextPlayer();
		
		assertTrue(ruleset.playCard(HeartsCard.Caro2));
		ruleset.nextPlayer();
		
		assertTrue(ruleset.playCard(HeartsCard.CaroBube));
		ruleset.nextPlayer();
		
		ruleset.calculateTricks();
	}
	
	

}
