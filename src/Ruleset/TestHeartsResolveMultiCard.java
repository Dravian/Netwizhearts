package Ruleset;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.MockGameServer;
import test.MockPlayer;
import ComObjects.ComRuleset;
import ComObjects.MsgMultiCards;
import ComObjects.MsgUser;
import Server.LobbyServer;

public class TestHeartsResolveMultiCard {
	ServerRuleset ruleset;	
	MockGameServer gameServer;	
	LobbyServer lobbyServer;
	
	MockPlayer player1;
	MockPlayer player2;
	MockPlayer player3;
	MockPlayer player4;
	PlayerState playerState1;
	PlayerState playerState2;
	PlayerState playerState3;
	PlayerState playerState4;
	
	@Before
	public void setUp() throws Exception {
		lobbyServer = new LobbyServer();
		player1 = new MockPlayer(lobbyServer);
		player2 = new MockPlayer(lobbyServer);
		player3 = new MockPlayer(lobbyServer);
		player4 = new MockPlayer(lobbyServer);
		
		
		player1.setPlayerName("Tick");
		player2.setPlayerName("Trick");
		player3.setPlayerName("Track");
		player4.setPlayerName("Donald");
		gameServer = new MockGameServer(lobbyServer,player1,"Mein Spiel",RulesetType.Hearts, 
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
		ruleset.giveACard(playerState1, HeartsCard.Caro6);
		ruleset.giveACard(playerState1, HeartsCard.Caro3);
		ruleset.giveACard(playerState1, HeartsCard.Caro4);
		
		ruleset.giveACard(playerState2, HeartsCard.Herz10);
		ruleset.giveACard(playerState2, HeartsCard.Kreuz10);
		ruleset.giveACard(playerState2, HeartsCard.Pik10);
		ruleset.giveACard(playerState2, HeartsCard.Pik2);
		
		ruleset.giveACard(playerState3, HeartsCard.Caro8);
		ruleset.giveACard(playerState3, HeartsCard.Pik5);
		ruleset.giveACard(playerState3, HeartsCard.CaroAss);
		ruleset.giveACard(playerState3, HeartsCard.PikAss);
		
		ruleset.giveACard(playerState4, HeartsCard.Kreuz2);
		ruleset.giveACard(playerState4, HeartsCard.Kreuz3);
		ruleset.giveACard(playerState4, HeartsCard.Kreuz4);
		ruleset.giveACard(playerState4, HeartsCard.Kreuz5);
		
		ruleset.setGamePhase(GamePhase.MultipleCardRequest);
	}

	@After
	public void tearDown() throws Exception {
		lobbyServer = null;
		player1 = null;
		player2 = null;
		player3 = null;
		gameServer = null;
		ruleset = null;
	}

	@Test
	public void test() {
		Set<Card> cards = new HashSet<Card>();
		cards.add(HeartsCard.Caro10);
		cards.add(HeartsCard.Caro6);
		cards.add(HeartsCard.Caro3);
		
		ComRuleset rules = new ComRuleset(new MsgMultiCards(cards));
		player1.injectComObject(rules);
		
		assertTrue(player1.getServerInput().size() == 0);
		assertTrue(player2.getServerInput().size() == 0);
		assertTrue(player3.getServerInput().size() == 0);
		assertTrue(player4.getServerInput().size() == 0);
		
		cards = new HashSet<Card>();
		cards.add(HeartsCard.Herz10);
		cards.add(HeartsCard.Kreuz10);
		cards.add(HeartsCard.Pik10);
		
		
		rules = new ComRuleset(new MsgMultiCards(cards));
		player2.injectComObject(rules);
		
		assertTrue(player1.getServerInput().size() == 0);
		assertTrue(player2.getServerInput().size() == 0);
		assertTrue(player3.getServerInput().size() == 0);
		assertTrue(player4.getServerInput().size() == 0);
		
		cards = new HashSet<Card>();
		cards.add(HeartsCard.Caro8);
		cards.add(HeartsCard.Pik5);
		cards.add(HeartsCard.CaroAss);
		
		
		rules = new ComRuleset(new MsgMultiCards(cards));
		player3.injectComObject(rules);
		
		assertTrue(player1.getServerInput().size() == 0);
		assertTrue(player2.getServerInput().size() == 0);
		assertTrue(player3.getServerInput().size() == 0);
		assertTrue(player4.getServerInput().size() == 0);
		
		cards = new HashSet<Card>();
		cards.add(HeartsCard.Kreuz2);
		cards.add(HeartsCard.Kreuz3);
		cards.add(HeartsCard.Kreuz4);
		
		
		rules = new ComRuleset(new MsgMultiCards(cards));
		player4.injectComObject(rules);
		
		//Spieler1 hat Kreuz2
		assertTrue(player1.getServerInput().size() == 2);
		assertTrue(player2.getServerInput().size() == 1);
		assertTrue(player3.getServerInput().size() == 1);
		assertTrue(player4.getServerInput().size() == 1);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void unValidCards1() {
		Set<Card> cards = new HashSet<Card>();
		cards.add(HeartsCard.Caro10);
		cards.add(HeartsCard.Caro6);
		
		ComRuleset rules = new ComRuleset(new MsgMultiCards(cards));
		player1.injectComObject(rules);
	}

	@Test(expected = IllegalArgumentException.class) 
	public void unValidCards2() {
		Set<Card> cards = new HashSet<Card>();
		cards.add(HeartsCard.Caro10);
		cards.add(HeartsCard.Caro6);
		cards.add(HeartsCard.Caro3);
		
		ComRuleset rules = new ComRuleset(new MsgMultiCards(cards));
		player1.injectComObject(rules);
		
		cards = new HashSet<Card>();
		cards.add(HeartsCard.Caro10);
		cards.add(HeartsCard.Caro6);
		cards.add(HeartsCard.Caro3);
		
		rules = new ComRuleset(new MsgMultiCards(cards));
		player1.injectComObject(rules);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void unValidCards3() {
		Set<Card> cards = new HashSet<Card>();
		cards.add(HeartsCard.Caro10);
		cards.add(HeartsCard.Caro6);
		cards.add(HeartsCard.Pik3);
		
		ComRuleset rules = new ComRuleset(new MsgMultiCards(cards));
		player1.injectComObject(rules);
	}
	

	
}
