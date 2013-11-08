package Ruleset;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runners.JUnit4;

public class isValidMoveWizard {

	@Test
	public void testIsValidMove() {
		ServerRuleset server = new ServerWizard();	
		String player1 = "Hans";
		String player2 = "Josef";
		String player3 = "Joe";
		server.initGame();
		server.addPlayerToGame(player1);
		server.addPlayerToGame(player2);
		server.addPlayerToGame(player3);
		
		server.setFirstPlayer(server.getPlayerState(player1));
		server.setTrumpCard(WizardCard.VierRot);
		
		server.giveACard(player1, WizardCard.DreiGruen);
		server.giveACard(player1, WizardCard.ZaubererRot);
		server.giveACard(player2, WizardCard.ZweiGruen);
		server.giveACard(player2, WizardCard.DreiRot);
		server.giveACard(player2, WizardCard.NarrBlau);
		server.giveACard(player2, WizardCard.EinsGruen);
		
		server.playCard(WizardCard.DreiGruen);
		server.setCurrentPlayer(server.getPlayerState(player2));
		
		boolean isValidMove = server.isValidMove(WizardCard.DreiRot);
		
		assertEquals(isValidMove, true);
	}

}
