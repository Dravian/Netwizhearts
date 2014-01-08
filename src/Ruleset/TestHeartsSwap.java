package Ruleset;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.TestGameServer;
import test.TestLobbyServer;
import test.TestPlayer;
import ComObjects.ComRuleset;
import ComObjects.MsgMultiCards;
import Server.GameServer;
import Server.LobbyServer;

public class TestHeartsSwap {
	ServerRuleset ruleset;	
	TestGameServer gameServer;	
	LobbyServer lobbyServer;
	
	TestPlayer player1;
	TestPlayer player2;
	TestPlayer player3;
	TestPlayer player4;
	
	PlayerState playerState1;
	PlayerState playerState2;
	PlayerState playerState3;
	PlayerState playerState4;
	
	Set<Card> player1Cards = new HashSet<Card>();
	Set<Card> player2Cards = new HashSet<Card>();
	Set<Card> player3Cards = new HashSet<Card>();
	Set<Card> player4Cards = new HashSet<Card>();
	
	@Before
	public void setUp() throws Exception {
		lobbyServer = new TestLobbyServer();
		player1 = new TestPlayer(lobbyServer);
		player2 = new TestPlayer(lobbyServer);
		player3 = new TestPlayer(lobbyServer);
		player4 = new TestPlayer(lobbyServer);

		
		player1.setPlayerName("Tick");
		player2.setPlayerName("Trick");
		player3.setPlayerName("Track");
		player4.setPlayerName("Duck");
		gameServer = new TestGameServer(lobbyServer,player1,"Mein Spiel",RulesetType.Hearts, 
				"",false);
		
		ruleset = gameServer.getRuleset();
		gameServer.addPlayer(player1);
		gameServer.addPlayer(player2);
		gameServer.addPlayer(player3);
		gameServer.addPlayer(player4);
		
		ruleset.addPlayerToGame(player1.getPlayerName());
		ruleset.addPlayerToGame(player2.getPlayerName());
		ruleset.addPlayerToGame(player3.getPlayerName());
		ruleset.addPlayerToGame(player4.getPlayerName());
		
		playerState1 = ruleset.getPlayerState(player1.getPlayerName());
		playerState2 = ruleset.getPlayerState(player2.getPlayerName());
		playerState3 = ruleset.getPlayerState(player3.getPlayerName());
		playerState4 = ruleset.getPlayerState(player4.getPlayerName());
		
		ruleset.setFirstPlayer(ruleset.getPlayerState(player1.getPlayerName()));
		
		ruleset.giveACard(playerState1, HeartsCard.Caro10);
		ruleset.giveACard(playerState1, HeartsCard.Herz5);
		ruleset.giveACard(playerState1, HeartsCard.HerzDame);
		ruleset.giveACard(playerState1, HeartsCard.Pik5);
		
		ruleset.giveACard(playerState2, HeartsCard.Pik2);
		ruleset.giveACard(playerState2, HeartsCard.Caro3);
		ruleset.giveACard(playerState2, HeartsCard.Caro9);
		ruleset.giveACard(playerState2, HeartsCard.Pik5);
		
		ruleset.giveACard(playerState3, HeartsCard.Kreuz2);
		ruleset.giveACard(playerState3, HeartsCard.Caro6);
		ruleset.giveACard(playerState3, HeartsCard.Kreuz6);
		ruleset.giveACard(playerState3, HeartsCard.Caro8);
		
		ruleset.giveACard(playerState4, HeartsCard.CaroAss);
		ruleset.giveACard(playerState4, HeartsCard.PikDame);
		ruleset.giveACard(playerState4, HeartsCard.PikAss);
		ruleset.giveACard(playerState4, HeartsCard.CaroBube);
		
		player1Cards.add(HeartsCard.Caro10);
		player1Cards.add(HeartsCard.Herz5);
		player1Cards.add(HeartsCard.HerzDame);
		
		player2Cards.add(HeartsCard.Caro3);
		player2Cards.add(HeartsCard.Caro9);
		player2Cards.add(HeartsCard.Pik5);
		
		player3Cards.add(HeartsCard.Caro6);
		player3Cards.add(HeartsCard.Caro8);
		player3Cards.add(HeartsCard.Kreuz6);
		
		player4Cards.add(HeartsCard.PikDame);
		player4Cards.add(HeartsCard.CaroAss);
		player4Cards.add(HeartsCard.CaroBube);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void swapLeft() {
		ruleset.setGamePhase(GamePhase.MultipleCardRequest);
		player1.injectComObject(new ComRuleset(new MsgMultiCards(player1Cards)));
		player2.injectComObject(new ComRuleset(new MsgMultiCards(player2Cards)));
		player3.injectComObject(new ComRuleset(new MsgMultiCards(player3Cards)));
		player4.injectComObject(new ComRuleset(new MsgMultiCards(player4Cards)));
		
		assertTrue(player1.getServerInput().size() == 1);
		assertTrue(player2.getServerInput().size() == 1);
		assertTrue(player3.getServerInput().size() == 2);
		assertTrue(player4.getServerInput().size() == 1);
		
		assertTrue(playerState1.getHand().containsAll(player4Cards));
		assertTrue(playerState2.getHand().containsAll(player1Cards));
		assertTrue(playerState3.getHand().containsAll(player2Cards));
		assertTrue(playerState4.getHand().containsAll(player3Cards));
	}
	
	@Test
	public void swapAcross() {
		ruleset.getGameState().nextRound();
		
		ruleset.setGamePhase(GamePhase.MultipleCardRequest);
		player1.injectComObject(new ComRuleset(new MsgMultiCards(player1Cards)));
		player2.injectComObject(new ComRuleset(new MsgMultiCards(player2Cards)));
		player3.injectComObject(new ComRuleset(new MsgMultiCards(player3Cards)));
		player4.injectComObject(new ComRuleset(new MsgMultiCards(player4Cards)));
		
		assertTrue(player1.getServerInput().size() == 1);
		assertTrue(player2.getServerInput().size() == 1);
		assertTrue(player3.getServerInput().size() == 2);
		assertTrue(player4.getServerInput().size() == 1);
		
		assertTrue(playerState1.getHand().containsAll(player3Cards));
		assertTrue(playerState2.getHand().containsAll(player4Cards));
		assertTrue(playerState3.getHand().containsAll(player1Cards));
		assertTrue(playerState4.getHand().containsAll(player2Cards));
	}
	
	@Test
	public void swapRight() {
		ruleset.getGameState().nextRound();
		ruleset.getGameState().nextRound();
		
		ruleset.setGamePhase(GamePhase.MultipleCardRequest);
		player1.injectComObject(new ComRuleset(new MsgMultiCards(player1Cards)));
		player2.injectComObject(new ComRuleset(new MsgMultiCards(player2Cards)));
		player3.injectComObject(new ComRuleset(new MsgMultiCards(player3Cards)));
		player4.injectComObject(new ComRuleset(new MsgMultiCards(player4Cards)));
		
		assertTrue(player1.getServerInput().size() == 1);
		assertTrue(player2.getServerInput().size() == 1);
		assertTrue(player3.getServerInput().size() == 2);
		assertTrue(player4.getServerInput().size() == 1);
		
		assertTrue(playerState1.getHand().containsAll(player2Cards));
		assertTrue(playerState2.getHand().containsAll(player3Cards));
		assertTrue(playerState3.getHand().containsAll(player4Cards));
		assertTrue(playerState4.getHand().containsAll(player1Cards));
	}

}
