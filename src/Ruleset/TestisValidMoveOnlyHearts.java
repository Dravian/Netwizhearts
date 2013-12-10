package Ruleset;


import Server.GameServer;
import Server.LobbyServer;
import Server.Player;
import org.junit.Before;
import org.junit.Test;
import test.TestGameServer;
import test.TestPlayer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestisValidMoveOnlyHearts {
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
		player4 = "Duck";
		lobbyServer = new LobbyServer();
		player = new TestPlayer(lobbyServer);
		gameServer = new TestGameServer(lobbyServer,player,"Mein Spiel",
				RulesetType.Hearts, "",false);
		ruleset = new ServerHearts(gameServer);

		ruleset.addPlayerToGame(player1);
        ruleset.addPlayerToGame(player2);
        ruleset.addPlayerToGame(player3);
        ruleset.addPlayerToGame(player4);

        playerState1 = ruleset.getPlayerState(player1);
        playerState2 = ruleset.getPlayerState(player2);
        playerState3 = ruleset.getPlayerState(player3);
        playerState4 = ruleset.getPlayerState(player4);

        ruleset.setFirstPlayer(playerState1);

        ruleset.giveACard(playerState1, HeartsCard.Kreuz2);
        ruleset.giveACard(playerState1, HeartsCard.Kreuz3);
        ruleset.giveACard(playerState1, HeartsCard.Kreuz4);
        ruleset.giveACard(playerState1, HeartsCard.Kreuz5);
        ruleset.giveACard(playerState1, HeartsCard.Caro2);
        ruleset.giveACard(playerState1, HeartsCard.Caro3);
        ruleset.giveACard(playerState1, HeartsCard.Caro4);
        ruleset.giveACard(playerState1, HeartsCard.Caro5);
        ruleset.giveACard(playerState1, HeartsCard.Pik2);
        ruleset.giveACard(playerState1, HeartsCard.Pik3);
        ruleset.giveACard(playerState1, HeartsCard.Pik4);
        ruleset.giveACard(playerState1, HeartsCard.Pik5);
        ruleset.giveACard(playerState1, HeartsCard.HerzAss);

        ruleset.giveACard(playerState2, HeartsCard.Herz2);
        ruleset.giveACard(playerState2, HeartsCard.Herz3);
        ruleset.giveACard(playerState2, HeartsCard.Herz4);
        ruleset.giveACard(playerState2, HeartsCard.Herz5);
        ruleset.giveACard(playerState2, HeartsCard.Herz6);
        ruleset.giveACard(playerState2, HeartsCard.Herz7);
        ruleset.giveACard(playerState2, HeartsCard.Herz8);
        ruleset.giveACard(playerState2, HeartsCard.Herz9);
        ruleset.giveACard(playerState2, HeartsCard.Herz10);
        ruleset.giveACard(playerState2, HeartsCard.HerzBube);
        ruleset.giveACard(playerState2, HeartsCard.HerzDame);
        ruleset.giveACard(playerState2, HeartsCard.HerzKoenig);
        ruleset.giveACard(playerState2, HeartsCard.PikDame);

        ruleset.giveACard(playerState3, HeartsCard.Kreuz6);
        ruleset.giveACard(playerState3, HeartsCard.Kreuz7);
        ruleset.giveACard(playerState3, HeartsCard.Kreuz8);
        ruleset.giveACard(playerState3, HeartsCard.Kreuz9);
        ruleset.giveACard(playerState3, HeartsCard.Kreuz10);
        ruleset.giveACard(playerState3, HeartsCard.Caro6);
        ruleset.giveACard(playerState3, HeartsCard.Caro7);
        ruleset.giveACard(playerState3, HeartsCard.Caro8);
        ruleset.giveACard(playerState3, HeartsCard.Caro9);
        ruleset.giveACard(playerState3, HeartsCard.Pik6);
        ruleset.giveACard(playerState3, HeartsCard.Pik7);
        ruleset.giveACard(playerState3, HeartsCard.Pik8);
        ruleset.giveACard(playerState3, HeartsCard.Pik9);

        ruleset.giveACard(playerState4, HeartsCard.KreuzBube);
        ruleset.giveACard(playerState4, HeartsCard.KreuzDame);
        ruleset.giveACard(playerState4, HeartsCard.KreuzKoenig);
        ruleset.giveACard(playerState4, HeartsCard.KreuzAss);
        ruleset.giveACard(playerState4, HeartsCard.Caro10);
        ruleset.giveACard(playerState4, HeartsCard.CaroBube);
        ruleset.giveACard(playerState4, HeartsCard.CaroDame);
        ruleset.giveACard(playerState4, HeartsCard.CaroKoenig);
        ruleset.giveACard(playerState4, HeartsCard.CaroAss);
        ruleset.giveACard(playerState4, HeartsCard.Pik10);
        ruleset.giveACard(playerState4, HeartsCard.PikBube);
        ruleset.giveACard(playerState4, HeartsCard.PikKoenig);
        ruleset.giveACard(playerState4, HeartsCard.PikAss);
    }

	@Test
	public void testIsValidFirstMove() {

	    boolean isValidMove = ruleset.isValidMove(HeartsCard.Herz2);

	    assertFalse(isValidMove);

	    boolean isValidMove2 = ruleset.isValidMove(HeartsCard.Kreuz2);

	    assertTrue(isValidMove2);
    }

    @Test
    public void testFirstRoundOnlyHearts() {

        ruleset.playCard(HeartsCard.Kreuz2);
        ruleset.setCurrentPlayer(playerState2);

        boolean isValidMove = ruleset.isValidMove(HeartsCard.Herz5);

        assertTrue(isValidMove);

        //assertTrue(ruleset.isValidMove(HeartsCard.PikDame));
    }
}