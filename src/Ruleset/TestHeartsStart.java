package Ruleset;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ComObjects.ComRuleset;
import ComObjects.MsgCardRequest;
import ComObjects.MsgMultiCardsRequest;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelection;
import ComObjects.MsgSelectionRequest;
import ComObjects.MsgUser;
import test.MockGameServer;
import test.MockLobbyServer;
import test.MockPlayer;

public class TestHeartsStart {
	MockPlayer blue;
	MockPlayer red;
	MockPlayer green;
	MockPlayer black;
	MockGameServer gameServer;
	MockLobbyServer lobbyServer;
	ServerRuleset hearts;

	@Before
	public void setUp() throws Exception {
		lobbyServer = new MockLobbyServer();

		blue = new MockPlayer(lobbyServer);
		blue.setPlayerName("Blue");

		red = new MockPlayer(lobbyServer);
		red.setPlayerName("Red");

		green = new MockPlayer(lobbyServer);
		green.setPlayerName("Green");

		black = new MockPlayer(lobbyServer);
		black.setPlayerName("Black");

		gameServer = new MockGameServer(lobbyServer, blue, "Mein Spiel",
				RulesetType.Hearts, "", false);
		hearts = gameServer.getRuleset();
		
		gameServer.addPlayer(blue);
		gameServer.addPlayer(red);
		gameServer.addPlayer(green);
		gameServer.addPlayer(black);
		
		hearts.addPlayerToGame("Blue");
		hearts.addPlayerToGame("Red");
		hearts.addPlayerToGame("Green");
	}

	@After
	public void tearDown() throws Exception {
		lobbyServer = null;
		blue = null;
		red = null;
		green = null;
		gameServer = null;
		hearts = null;
	}

	@Test
	public void falseStart() {
		try {
			hearts.runGame();
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(hearts.getPlayers().size() < 4
					|| hearts.getPlayers().size() > 4);
		}
	}

	@Test
	public void rightStart() {
		hearts.addPlayerToGame("Black");
		try {
			hearts.runGame();
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(hearts.getPlayers().size() < 3
					|| hearts.getPlayers().size() > 6);
		}
	}

	@Test
	public void testStartRoundHearts() {
		hearts.addPlayerToGame("Black");

		hearts.setGamePhase(GamePhase.RoundStart);
		List<PlayerState> players = hearts.getPlayers();

		hearts.setFirstPlayer(hearts.getPlayers().get(0));

		for (int test = 1; test < 1000; test++) {
			hearts.startRound();
			
			if (hearts.getGameState().getRoundNumber() % 4 != 0) {
				assertTrue(((ComRuleset) blue.getServerInput().get(1))
						.getRulesetMessage() instanceof MsgMultiCardsRequest);
				assertTrue(((ComRuleset) red.getServerInput().get(1))
						.getRulesetMessage() instanceof MsgMultiCardsRequest);
				assertTrue(((ComRuleset) green.getServerInput().get(1))
						.getRulesetMessage() instanceof MsgMultiCardsRequest);
				assertTrue(((ComRuleset) black.getServerInput().get(1))
						.getRulesetMessage() instanceof MsgMultiCardsRequest);
			} else {
				
				if(hearts.getGameState().getCurrentPlayer().getPlayerStateName().equals("Blue")) {
					assertTrue(((ComRuleset) blue.getServerInput().get(1))
							.getRulesetMessage() instanceof MsgCardRequest);
				} else if(hearts.getGameState().getCurrentPlayer().getPlayerStateName().equals("Red")) {
					assertTrue(((ComRuleset) red.getServerInput().get(1))
							.getRulesetMessage() instanceof MsgCardRequest);
				} else if(hearts.getGameState().getCurrentPlayer().getPlayerStateName().equals("Green")) {
					assertTrue(((ComRuleset) green.getServerInput().get(1))
							.getRulesetMessage() instanceof MsgCardRequest);
				} else if(hearts.getGameState().getCurrentPlayer().getPlayerStateName().equals("Black")) {
					assertTrue(((ComRuleset) black.getServerInput().get(1))
							.getRulesetMessage() instanceof MsgCardRequest);
				}
			}

			for (PlayerState player : hearts.getPlayers()) {
				assertTrue(player.getHand().size() == 13);
			}

			blue.empty();
			red.empty();
			green.empty();
			black.empty();

			// Roundend
			hearts.setCurrentPlayer(hearts.getFirstPlayer());
			hearts.nextPlayer();
			hearts.setFirstPlayer(hearts.getCurrentPlayer());
			hearts.getGameState().nextRound();

			hearts.getGameState().restartDeck(hearts.createDeck());

			hearts.setGamePhase(GamePhase.RoundStart);
		}

	}

}
