package Ruleset;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runners.JUnit4;

public class isValidMoveWizard {

	@Test
	public void testIsValidMove() {
		ServerWizard testServer = new ServerWizard();
		String player1 = "Hans";
		String player2 = "Josef";
		String player3 = "Max";
		testServer.addPlayerToGame(player1);
		testServer.addPlayerToGame(player2);
		testServer.addPlayerToGame(player3);
		
		fail("Noch nicht implementiert");
	}

}
