package Ruleset;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runners.JUnit4;

public class isValidMoveHeartsTest {
    @Test
    public void testIsValidMove() {
        ServerRuleset server = new ServerHearts();
        String player1 = "Hans";
        String player2 = "Josef";
        String player3 = "Joe";
        String player4 = "Horst";
        server.initGame();
        server.addPlayerToGame(player1);
        server.addPlayerToGame(player2);
        server.addPlayerToGame(player3);
        server.addPlayerToGame(player4);

        server.setFirstPlayer(server.getPlayerState(player1));

        server.giveACard(player1, HeartsCard.Herz2);
        server.giveACard(player1, HeartsCard.Kreuz9);
        server.giveACard(player2, HeartsCard.Caro3);
        server.giveACard(player2, HeartsCard.Caro6);
        server.giveACard(player3, HeartsCard.Pik4);
        server.giveACard(player3, HeartsCard.Pik5);
        server.giveACard(player4, HeartsCard.Pik1);
        server.giveACard(player4, HeartsCard.Herz7);

        boolean isValidMove = server.isValidMove(HeartsCard.Herz2);

        assertEquals(isValidMove, false);

        boolean isValidMove2 = server.isValidMove(HeartsCard.Caro3);

        assertEquals(isValidMove2, true);
    }
}
