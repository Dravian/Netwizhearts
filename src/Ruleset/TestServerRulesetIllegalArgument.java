package Ruleset;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.MockGameServer;
import test.MockPlayer;
import ComObjects.MsgBoolean;
import ComObjects.MsgCardRequest;
import ComObjects.MsgMultiCards;
import ComObjects.MsgMultiCardsRequest;
import ComObjects.MsgNumber;
import ComObjects.MsgSelection;
import ComObjects.MsgSelectionRequest;
import ComObjects.MsgUser;
import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestServerRulesetIllegalArgument {
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

	@Test(expected = IllegalArgumentException.class)
	public void resolveCardRequest() {

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
		
		ruleset.resolveMessage(new MsgCardRequest(), "Tick");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void resolveMultiCardRequest() {

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
		
		ruleset.resolveMessage(new MsgMultiCardsRequest(3), "Trick");
	}

	@Test(expected = IllegalArgumentException.class)
	public void resolveHeartsMsgNumber() {

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
		
		ruleset.resolveMessage(new MsgNumber(1), "Trick");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void resolveMsgNumberRequest() {

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
		
		ruleset.resolveMessage(new MsgNumber(1), "Trick");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void resolveWizardMsgBoolean() {

		gameServer = new MockGameServer(lobbyServer,player1,"Mein Spiel",RulesetType.Wizard, 
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
		
		ruleset.resolveMessage(new MsgBoolean(false), "Trick");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void resolveWizardMsgMultiCards() {

		gameServer = new MockGameServer(lobbyServer,player1,"Mein Spiel",RulesetType.Wizard, 
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
		Set<Card> cards = new HashSet<Card>();
		ruleset.resolveMessage(new MsgMultiCards(cards), "Trick");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void resolveHeartsMsgSelection() {

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
		
		ruleset.resolveMessage(new MsgSelection(Colour.SPADE), "Trick");;
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void resolveMsgSelectionRequest() {

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
		
		ruleset.resolveMessage(new MsgSelectionRequest(), "Trick");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void resolveMsgUser() {

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
		
		
		List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();
        discardPile.add(new DiscardedCard("Tick", HeartsCard.Kreuz2));

        List<OtherData> enemyData = new ArrayList<OtherData>();

        enemyData.add(playerState2.getOtherData());
        enemyData.add(playerState3.getOtherData());
        enemyData.add(playerState4.getOtherData());

        String currentPlayer = "4";
        int roundNumber = 1;

        GameClientUpdate gameState = new GameClientUpdate(playerState1, discardPile,
                enemyData, currentPlayer, roundNumber, null);
        MsgUser game = new MsgUser(gameState);
		
		ruleset.resolveMessage(game, "Tick");;
	}
	
}
