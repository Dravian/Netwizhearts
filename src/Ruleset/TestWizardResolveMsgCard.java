package Ruleset;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.MockGameServer;
import test.MockLobbyServer;
import test.MockPlayer;

import ComObjects.ComRuleset;
import ComObjects.MsgCard;
import ComObjects.MsgCardRequest;
import ComObjects.MsgUser;
import ComObjects.RulesetMessage;
import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestWizardResolveMsgCard {
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
		gameServer = new MockGameServer(lobbyServer,player1,"Mein Spiel",RulesetType.Wizard, 
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
		ruleset.setUncoveredCard(WizardCard.VierRot);
		
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
		
		ruleset.playCard(WizardCard.DreiGruen);
		
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
		ComRuleset rules = new ComRuleset(new MsgCard(WizardCard.ZweiGruen));
		player2.injectComObject(rules);
		
		assertTrue(player3.getServerInput().size() == 2);
		
		assertTrue(((ComRuleset) player3.getServerInput().get(0)).getRulesetMessage() 
				instanceof MsgUser);
		assertTrue(((ComRuleset) player3.getServerInput().get(1)).getRulesetMessage() 
				instanceof MsgCardRequest);
	
	}
	
	@Test
	public void testResolveMessageMsgCardString2() {
		
		ruleset.playCard(WizardCard.ZweiGruen);
		ruleset.nextPlayer();

		ruleset.setGamePhase(GamePhase.CardRequest);
		ComRuleset rules = new ComRuleset(new MsgCard(WizardCard.ZehnGruen));
		player3.injectComObject(rules);
		
		assertTrue(player1.getServerInput().size() == 2);
		assertTrue(player2.getServerInput().size() == 2);
		assertTrue(player3.getServerInput().size() == 3);
		
		assertTrue(((ComRuleset) player3.getServerInput().get(0)).getRulesetMessage() 
				instanceof MsgUser);
		
		assertTrue(playerState3.getOtherData().getNumberOfTricks() == 1);
	
	}
	
	@Test(expected = IllegalStateException.class)  
	public void testWrongState() {
		ruleset.setGamePhase(GamePhase.Ending);
		
		ComRuleset rules = new ComRuleset(new MsgCard(WizardCard.ZweiGruen));
		player2.injectComObject(rules);	
	}
	
	
	@Test(expected = IllegalArgumentException.class)  
	public void testWrongPlayer() {	
		ComRuleset rules = new ComRuleset(new MsgCard(WizardCard.ZweiGruen));
		player1.injectComObject(rules);	
	}
	

	@Test(expected = IllegalArgumentException.class)  
	public void testWrongCard() {	
		ComRuleset rules = new ComRuleset(new MsgCard(HeartsCard.Caro10));
		player2.injectComObject(rules);	
	}
	
	@Test(expected = RulesetException.class)  
	public void testFullDiscardPile() {	
		ruleset.playCard(WizardCard.DreiRot);
		ruleset.playCard(WizardCard.ZweiGelb);
		
		ComRuleset rules = new ComRuleset(new MsgCard(WizardCard.ZweiGruen));
		player2.injectComObject(rules);	
	}
	
	@Test(expected = IllegalArgumentException.class)  
	public void testUnvalidMove() {	
		ComRuleset rules = new ComRuleset(new MsgCard(WizardCard.DreiRot));
		player2.injectComObject(rules);	
	}
	
	@Test(expected = IllegalArgumentException.class)  
	public void testCardNotInHand() {	
		ComRuleset rules = new ComRuleset(new MsgCard(WizardCard.ZaubererBlau));
		player2.injectComObject(rules);	
	}
}
