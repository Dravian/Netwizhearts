package Ruleset;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.MockGameServer;
import test.MockPlayer;
import ComObjects.ComRuleset;
import ComObjects.MsgCard;
import ComObjects.MsgCardRequest;
import ComObjects.MsgUser;
import Server.LobbyServer;

public class TestHeartsResolveMsgCard {
	ServerRuleset ruleset;	
	MockGameServer gameServer;	
	LobbyServer lobbyServer;
	
	MockPlayer player1;
	MockPlayer player2;
	MockPlayer player3;
	PlayerState playerState1;
	PlayerState playerState2;
	PlayerState playerState3;
	
	@Before
	public void setUp() throws Exception {		
		lobbyServer = new LobbyServer();
		player1 = new MockPlayer(lobbyServer);
		player2 = new MockPlayer(lobbyServer);
		player3 = new MockPlayer(lobbyServer);
		
		player1.setName("Tick");
		player2.setName("Trick");
		player3.setName("Track");
		
		player1.setPlayerName("Tick");
		player2.setPlayerName("Trick");
		player3.setPlayerName("Track");
		gameServer = new MockGameServer(lobbyServer,player1,"Mein Spiel",RulesetType.Hearts, 
				"",false);
		ruleset = gameServer.getRuleset();
		gameServer.addPlayer(player1);
		gameServer.addPlayer(player2);
		gameServer.addPlayer(player3);
		
		ruleset.addPlayerToGame(player1.getPlayerName());
		ruleset.addPlayerToGame(player2.getPlayerName());
		ruleset.addPlayerToGame(player3.getPlayerName());
		
		playerState1 = ruleset.getPlayerState(player1.getPlayerName());
		playerState2 = ruleset.getPlayerState(player2.getPlayerName());
		playerState3 = ruleset.getPlayerState(player3.getPlayerName());
		
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
		
		ruleset.playCard(HeartsCard.Caro10);
		
		ruleset.nextPlayer();
		
		ruleset.setGamePhase(GamePhase.CardRequest);
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
	public void testResolveMessageMsgCardString() {
		ComRuleset rules = new ComRuleset(new MsgCard(HeartsCard.Kreuz10));
		player2.injectComObject(rules);
		
		assertTrue(player3.getServerInput().size() == 2);
		
		assertTrue(((ComRuleset) player3.getServerInput().get(0)).getRulesetMessage() 
				instanceof MsgUser);
		assertTrue(((ComRuleset) player3.getServerInput().get(1)).getRulesetMessage() 
				instanceof MsgCardRequest);
	
	}
	
	@Test
	public void testResolveMessageMsgCardString2() {
		
		ruleset.playCard(HeartsCard.Kreuz10);
		ruleset.nextPlayer();
		
		ruleset.setGamePhase(GamePhase.CardRequest);
		ComRuleset rules = new ComRuleset(new MsgCard(HeartsCard.Caro8));
		player3.injectComObject(rules);
		
		// MsgBoolean wird auch geschickt
		assertTrue(player1.getServerInput().size() == 4);
		assertTrue(player2.getServerInput().size() == 3);
		assertTrue(player3.getServerInput().size() == 3);
		
		assertTrue(((ComRuleset) player1.getServerInput().get(0)).getRulesetMessage() 
				instanceof MsgUser);
		
		assertTrue(playerState1.getOtherData().getNumberOfTricks() == 1);
	
	}
	
	@Test(expected = IllegalStateException.class)  
	public void testWrongState() {
		ruleset.setGamePhase(GamePhase.Ending);
		
		ComRuleset rules = new ComRuleset(new MsgCard(HeartsCard.Kreuz10));
		player2.injectComObject(rules);	
	}
	
	
	@Test(expected = IllegalArgumentException.class)  
	public void testWrongPlayer() {	
		ComRuleset rules = new ComRuleset(new MsgCard(HeartsCard.Caro6));
		player1.injectComObject(rules);	
	}
	

	@Test(expected = IllegalArgumentException.class)  
	public void testWrongCard() {	
		ComRuleset rules = new ComRuleset(new MsgCard(WizardCard.AchtBlau));
		player2.injectComObject(rules);	
	}
	
	@Test(expected = RulesetException.class)  
	public void testFullDiscardPile() {	
		
		ruleset.playCard(HeartsCard.Pik10);
		ruleset.playCard(HeartsCard.Kreuz10);
		
		ComRuleset rules = new ComRuleset(new MsgCard(HeartsCard.Pik2));
		player2.injectComObject(rules);	
	}
	
	@Test(expected = IllegalArgumentException.class)  
	public void testUnvalidMove() {	
		
		ruleset.playCard(HeartsCard.Herz10);
		ruleset.nextPlayer();
		
		ComRuleset rules = new ComRuleset(new MsgCard(HeartsCard.PikAss));
		player3.injectComObject(rules);	
	}
	
	@Test(expected = IllegalArgumentException.class)  
	public void testCardNotInHand() {	
		ComRuleset rules = new ComRuleset(new MsgCard(HeartsCard.CaroAss));
		player2.injectComObject(rules);	
	}
	
}
