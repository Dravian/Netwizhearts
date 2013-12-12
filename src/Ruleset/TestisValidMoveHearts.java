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

import java.util.List;

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
        ruleset.giveACard(playerState1, HeartsCard.Caro4);
        ruleset.giveACard(playerState1, HeartsCard.Caro10);
        ruleset.giveACard(playerState1, HeartsCard.CaroBube);
        ruleset.giveACard(playerState1, HeartsCard.CaroAss);
        ruleset.giveACard(playerState1, HeartsCard.Pik3);
        ruleset.giveACard(playerState1, HeartsCard.Pik10);
        ruleset.giveACard(playerState1, HeartsCard.PikBube);
        ruleset.giveACard(playerState1, HeartsCard.Herz2);
        ruleset.giveACard(playerState1, HeartsCard.Herz6);
        ruleset.giveACard(playerState1, HeartsCard.HerzKoenig);

        ruleset.giveACard(playerState2, HeartsCard.Kreuz8);
        ruleset.giveACard(playerState2, HeartsCard.KreuzBube);
        ruleset.giveACard(playerState2, HeartsCard.KreuzDame);
        ruleset.giveACard(playerState2, HeartsCard.Caro3);
        ruleset.giveACard(playerState2, HeartsCard.Caro6);
        ruleset.giveACard(playerState2, HeartsCard.CaroDame);
        ruleset.giveACard(playerState2, HeartsCard.Pik6);
        ruleset.giveACard(playerState2, HeartsCard.Pik9);
        ruleset.giveACard(playerState2, HeartsCard.PikAss);
        ruleset.giveACard(playerState2, HeartsCard.Herz3);
        ruleset.giveACard(playerState2, HeartsCard.Herz8);
        ruleset.giveACard(playerState2, HeartsCard.HerzBube);
        ruleset.giveACard(playerState2, HeartsCard.HerzAss);

        ruleset.giveACard(playerState3, HeartsCard.Kreuz5);
        ruleset.giveACard(playerState3, HeartsCard.Kreuz6);
        ruleset.giveACard(playerState3, HeartsCard.Kreuz10);
        ruleset.giveACard(playerState3, HeartsCard.KreuzKoenig);
        ruleset.giveACard(playerState3, HeartsCard.KreuzAss);
        ruleset.giveACard(playerState3, HeartsCard.Kreuz7);
        ruleset.giveACard(playerState3, HeartsCard.Kreuz9);
        ruleset.giveACard(playerState3, HeartsCard.Caro8);
        ruleset.giveACard(playerState3, HeartsCard.Pik4);
        ruleset.giveACard(playerState3, HeartsCard.Pik7);
        ruleset.giveACard(playerState3, HeartsCard.PikKoenig);
        ruleset.giveACard(playerState3, HeartsCard.Herz5);
        ruleset.giveACard(playerState3, HeartsCard.HerzDame);

        ruleset.giveACard(playerState4, HeartsCard.Caro2);
        ruleset.giveACard(playerState4, HeartsCard.Caro9);
        ruleset.giveACard(playerState4, HeartsCard.Caro5);
        ruleset.giveACard(playerState4, HeartsCard.Caro7);
        ruleset.giveACard(playerState4, HeartsCard.CaroKoenig);
        ruleset.giveACard(playerState4, HeartsCard.Pik2);
        ruleset.giveACard(playerState4, HeartsCard.Pik5);
        ruleset.giveACard(playerState4, HeartsCard.Pik8);
        ruleset.giveACard(playerState4, HeartsCard.PikDame);
        ruleset.giveACard(playerState4, HeartsCard.Herz7);
        ruleset.giveACard(playerState4, HeartsCard.Herz4);
        ruleset.giveACard(playerState4, HeartsCard.Herz9);
        ruleset.giveACard(playerState4, HeartsCard.Herz10);
    }

	@Test
	public void testIsValidFirstMove() {

	    boolean isValidMove = ruleset.isValidMove(HeartsCard.Herz2);

	    assertFalse(isValidMove);

	    boolean isValidMove2 = ruleset.isValidMove(HeartsCard.Kreuz2);

	    assertTrue(isValidMove2);
    }

    @Test
    public void testCaroToKreuz() {

        ruleset.playCard(HeartsCard.Kreuz2);
        ruleset.setCurrentPlayer(playerState2);

        boolean isValidMove = ruleset.isValidMove(HeartsCard.Caro3);

        assertFalse(isValidMove);

        assertTrue(ruleset.isValidMove(HeartsCard.KreuzDame));
    }

    @Test
    public void testPikToKreuzWithOtherKreuzLeft() {

        ruleset.playCard(HeartsCard.Kreuz2);
        ruleset.setCurrentPlayer(playerState2);

        ruleset.playCard(HeartsCard.KreuzDame);
        ruleset.setCurrentPlayer(playerState3);

        boolean isValidMove = ruleset.isValidMove(HeartsCard.Pik4);

        assertFalse(isValidMove);

        boolean isValidMove1 = ruleset.isValidMove(HeartsCard.Kreuz5);

        assertTrue(isValidMove1);
    }

    @Test
    public void testHerzToKreuz() {
        ruleset.playCard(HeartsCard.Kreuz2);
        ruleset.setCurrentPlayer(playerState2);

        ruleset.playCard(HeartsCard.KreuzDame);
        ruleset.setCurrentPlayer(playerState3);

        ruleset.playCard(HeartsCard.Kreuz5);
        ruleset.setCurrentPlayer(playerState4);

        boolean isValidMove = ruleset.isValidMove(HeartsCard.Herz7);

        assertFalse(isValidMove);

        boolean isValidMove1 = ruleset.isValidMove(HeartsCard.PikDame);

        assertFalse(isValidMove1);

        boolean isVaildMove2 = ruleset.isValidMove(HeartsCard.CaroKoenig);

        assertTrue(isVaildMove2);
	}

    @Test
    public void testSecondRound() {
        ruleset.playCard(HeartsCard.Kreuz2);
        ruleset.setCurrentPlayer(playerState2);

        ruleset.playCard(HeartsCard.KreuzDame);
        ruleset.setCurrentPlayer(playerState3);

        ruleset.playCard(HeartsCard.Kreuz5);
        ruleset.setCurrentPlayer(playerState4);

        ruleset.playCard(HeartsCard.CaroKoenig);

        ruleset.calculateTricks();

        // Zweite Runde
        // Der Spieler 2 hat den letzten Stick bekommen und fängt nun an
        //????????
        assertTrue(ruleset.getCurrentPlayer().getPlayerStateName() == playerState2.getPlayerStateName());

        //System.out.println(ruleset.getPlayedCards().size());

        ruleset.setCurrentPlayer(playerState1);
        assertFalse(ruleset.isValidMove(HeartsCard.HerzKoenig));

        assertTrue(ruleset.isValidMove(HeartsCard.Kreuz3));
        ruleset.playCard(HeartsCard.Kreuz3);
        ruleset.setCurrentPlayer(playerState2);

        assertFalse(ruleset.isValidMove(HeartsCard.HerzBube));
        assertTrue(ruleset.isValidMove(HeartsCard.KreuzBube));

        ruleset.playCard(HeartsCard.KreuzBube);
        ruleset.setCurrentPlayer(playerState3);

        ruleset.playCard(HeartsCard.KreuzAss);
        ruleset.setCurrentPlayer(playerState4);

       assertTrue(ruleset.isValidMove(HeartsCard.Pik5));
       assertTrue(ruleset.isValidMove(HeartsCard.PikDame));
       assertTrue(ruleset.isValidMove(HeartsCard.Herz10));
    }
}
