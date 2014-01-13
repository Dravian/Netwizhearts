package Ruleset;

import static org.junit.Assert.*;

import java.net.Socket;
import java.util.List;

import javax.net.SocketFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ComObjects.ComRuleset;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelection;
import ComObjects.MsgSelectionRequest;
import ComObjects.MsgUser;
import ComObjects.WarningMsg;
import test.TestPlayer;
import test.TestGameServer;
import test.TestLobbyServer;

public class TestWizardStart {
	TestPlayer blue;
	TestPlayer red;
	TestPlayer green;
	TestGameServer gameServer;
	TestLobbyServer lobbyServer;
	ServerRuleset wizard;

	@Before
	public void setUp() throws Exception {
		lobbyServer = new TestLobbyServer();

		blue = new TestPlayer(lobbyServer);
		blue.setPlayerName("Blue");

		red = new TestPlayer(lobbyServer);
		red.setPlayerName("Red");

		green = new TestPlayer(lobbyServer);
		green.setPlayerName("Green");

		gameServer = new TestGameServer(lobbyServer, blue, "Mein Spiel",
				RulesetType.Wizard, "", false);
		wizard = new ServerWizard(gameServer);

		gameServer.addPlayer(blue);
		gameServer.addPlayer(red);
		gameServer.addPlayer(green);

		wizard.addPlayerToGame("Blue");
		wizard.addPlayerToGame("Red");
	}

	@Test
	public void testFalseStartWizard() {
		try {
			wizard.runGame();
		} catch (Exception e) {
			assertTrue(wizard.getPlayers().size() < 3
					|| wizard.getPlayers().size() > 6);
		}
	}

	@Test
	public void testStartWizard() {
		wizard.addPlayerToGame("Green");

		try {
			wizard.runGame();
			assertTrue(wizard.getFirstPlayer() == wizard.getPlayerState("Blue"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testStartRoundWizard() {
		wizard.addPlayerToGame("Green");

		wizard.setGamePhase(GamePhase.RoundStart);
		// int round = 1;
		int numberOfRounds = 0;
		List<PlayerState> players	= wizard.getPlayers();
		
		if(players.size() == 3) {
			numberOfRounds = 20;
		} else if(players.size() == 4) {
			numberOfRounds = 15;
		} else if(players.size() == 5) {
			numberOfRounds = 12;
		} else if(players.size() == 6) {
			numberOfRounds = 10;
		} else {
			assertFalse(true);
		}
		
		((ServerWizard) wizard).setPlayingRounds(numberOfRounds);
		wizard.setFirstPlayer(wizard.getPlayers().get(0));
	
		for (int test = 0; test < 1000; test++) {
			

			while(wizard.getGameState().getRoundNumber()
						!= ((ServerWizard)wizard).getPlayingRounds()) {

				wizard.startRound();

				Card uncoveredCard = wizard.getGameState().getUncoveredCard();
				Colour trumpColour = ((ServerWizard) wizard).getTrumpColour();

				if (uncoveredCard == EmptyCard.Empty) {
					assertTrue(trumpColour == Colour.NONE);

				} else if (uncoveredCard.getValue() == 14) {
					
					// Zweimal GameClientUpdate, einmal MsgSelectionRequest
					if (wizard.getFirstPlayer().getPlayerStateName()
							.equals("Blue")) {
						assertTrue(((ComRuleset) blue.getServerInput().get(2))
								.getRulesetMessage() instanceof MsgSelectionRequest);
						
						assertTrue(red.getServerInput().size() == 2);
						assertTrue(green.getServerInput().size() == 2);
					} else if (wizard.getFirstPlayer().getPlayerStateName()
							.equals("Red")) {
						assertTrue(((ComRuleset) red.getServerInput().get(2))
								.getRulesetMessage() instanceof MsgSelectionRequest);
						
						assertTrue(blue.getServerInput().size() == 2);
						assertTrue(green.getServerInput().size() == 2);
					} else if (wizard.getFirstPlayer().getPlayerStateName()
							.equals("Green")) {
						assertTrue(((ComRuleset) green.getServerInput().get(1))
								.getRulesetMessage() instanceof MsgSelectionRequest);
						assertTrue(blue.getServerInput().size() == 2);
						assertTrue(red.getServerInput().size() == 2);
					}

				} else if (uncoveredCard.getValue() == 0) {
					assertTrue(trumpColour == Colour.NONE);

					assertTrue(((ComRuleset) blue.getServerInput().get(0))
							.getRulesetMessage() instanceof MsgUser);
					assertTrue(((MsgSelection) ((ComRuleset) blue
							.getServerInput().get(1)).getRulesetMessage())
							.getSelection() == trumpColour);

					assertTrue(((ComRuleset) red.getServerInput().get(0))
							.getRulesetMessage() instanceof MsgUser);
					assertTrue(((MsgSelection) ((ComRuleset) red
							.getServerInput().get(1)).getRulesetMessage())
							.getSelection() == trumpColour);

					assertTrue(((ComRuleset) green.getServerInput().get(0))
							.getRulesetMessage() instanceof MsgUser);
					assertTrue(((MsgSelection) ((ComRuleset) green
							.getServerInput().get(1)).getRulesetMessage())
							.getSelection() == trumpColour);

					if (wizard.getCurrentPlayer().getPlayerStateName()
							.equals("Blue")) {
						assertTrue(((ComRuleset) blue.getServerInput().get(2))
								.getRulesetMessage() instanceof MsgNumberRequest);
						
						assertTrue(red.getServerInput().size() == 2);
						assertTrue(green.getServerInput().size() == 2);
						
					} else if (wizard.getCurrentPlayer().getPlayerStateName()
							.equals("Red")) {
						assertTrue(((ComRuleset) red.getServerInput().get(2))
								.getRulesetMessage() instanceof MsgNumberRequest);

						assertTrue(blue.getServerInput().size() == 2);
						assertTrue(green.getServerInput().size() == 2);
					} else if (wizard.getCurrentPlayer().getPlayerStateName()
							.equals("Green")) {
						assertTrue(((ComRuleset) green.getServerInput().get(2))
								.getRulesetMessage() instanceof MsgNumberRequest);
						assertTrue(red.getServerInput().size() == 2);
						assertTrue(blue.getServerInput().size() == 2);
					}

				} else {

					assertTrue(trumpColour == uncoveredCard.getColour());

					assertTrue(((ComRuleset) blue.getServerInput().get(0))
							.getRulesetMessage() instanceof MsgUser);
					assertTrue(((MsgSelection) ((ComRuleset) blue
							.getServerInput().get(1)).getRulesetMessage())
							.getSelection() == trumpColour);

					assertTrue(((ComRuleset) red.getServerInput().get(0))
							.getRulesetMessage() instanceof MsgUser);
					assertTrue(((MsgSelection) ((ComRuleset) red
							.getServerInput().get(1)).getRulesetMessage())
							.getSelection() == trumpColour);

					assertTrue(((ComRuleset) green.getServerInput().get(0))
							.getRulesetMessage() instanceof MsgUser);
					assertTrue(((MsgSelection) ((ComRuleset) green
							.getServerInput().get(1)).getRulesetMessage())
							.getSelection() == trumpColour);

					if (wizard.getCurrentPlayer().getPlayerStateName()
							.equals("Blue")) {
						assertTrue(((ComRuleset) blue.getServerInput().get(2))
								.getRulesetMessage() instanceof MsgNumberRequest);
						
						assertTrue(red.getServerInput().size() == 2);
						assertTrue(green.getServerInput().size() == 2);
					} else if (wizard.getCurrentPlayer().getPlayerStateName()
							.equals("Red")) {
						assertTrue(((ComRuleset) red.getServerInput().get(2))
								.getRulesetMessage() instanceof MsgNumberRequest);
						
						assertTrue(blue.getServerInput().size() == 2);
						assertTrue(green.getServerInput().size() == 2);
					} else if (wizard.getCurrentPlayer().getPlayerStateName()
							.equals("Green")) {
						assertTrue(((ComRuleset) green.getServerInput().get(2))
								.getRulesetMessage() instanceof MsgNumberRequest);
						
						assertTrue(red.getServerInput().size() == 2);
						assertTrue(blue.getServerInput().size() == 2);
					}

				}

				for (PlayerState player : wizard.getPlayers()) {
					assertTrue(player.getHand().size() == wizard.getGameState().getRoundNumber());
				}

				blue.empty();
				red.empty();
				green.empty();

				// Roundend
				wizard.setCurrentPlayer(wizard.getFirstPlayer());
				wizard.nextPlayer();
				wizard.setFirstPlayer(wizard.getCurrentPlayer());

				trumpColour = Colour.NONE;
				wizard.getGameState().setUncoveredCard(EmptyCard.Empty);
				wizard.getGameState().restartDeck(wizard.createDeck());

				wizard.setGamePhase(GamePhase.RoundStart);
				wizard.getGameState().nextRound();
				
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		lobbyServer = null;
		blue = null;
		red = null;
		green = null;
		gameServer = null;
		wizard = null;
	}

}
