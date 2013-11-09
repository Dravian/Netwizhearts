package Ruleset;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class isValidMoveWizard {
    ServerRuleset ruleset;
    String player1;
    String player2;
    String player3;
	
	@Before
	public void createRuleset() {
		ruleset = new ServerWizard();	
		player1 = "Hans";
		player2 = "Josef";
		player3 = "Joe";
		
		ruleset.initGame();
		ruleset.addPlayerToGame(player1);
		ruleset.addPlayerToGame(player2);
		ruleset.addPlayerToGame(player3);
		
		ruleset.setFirstPlayer(ruleset.getPlayerState(player1));
		ruleset.setTrumpCard(WizardCard.VierRot);
		
		ruleset.giveACard(player1, WizardCard.DreiGruen);
		ruleset.giveACard(player1, WizardCard.ZaubererRot);
		
		ruleset.giveACard(player2, WizardCard.ZweiGruen);
		ruleset.giveACard(player2, WizardCard.DreiRot);
		
		ruleset.giveACard(player2, WizardCard.NarrBlau);
		ruleset.giveACard(player2, WizardCard.EinsGruen);
		
		ruleset.playCard(WizardCard.DreiGruen);
		ruleset.setCurrentPlayer(ruleset.getPlayerState(player2));
	}
	
	@Test
	public void testSecondPlayerPlaysRed3() {
		boolean isValidMove = ruleset.isValidMove(WizardCard.DreiRot);
		
		assertFalse(isValidMove);
	}
	
	@Test
	public void testSecondPlayerPlaysGreen2() {
		boolean isValidMove = ruleset.isValidMove(WizardCard.ZweiGruen);
		
		assertTrue(isValidMove);
	}
	
	@Test
	public void testThirdPlayerPlaysFoolBlue() {
		ruleset.playCard(WizardCard.ZweiGruen);
		ruleset.setCurrentPlayer(ruleset.getPlayerState(player3));
		
		boolean isValidMove = ruleset.isValidMove(WizardCard.NarrBlau);
		
		assertTrue(isValidMove);
	}

}
