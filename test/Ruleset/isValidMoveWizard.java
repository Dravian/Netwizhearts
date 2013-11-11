package Ruleset;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class isValidMoveWizard {
    String player1 = "Hans";
    String player2 = "Josef";
    String player3 = "Joe";
    
    @Mock
    ServerRuleset ruleset;
	
	@Before
	public void setUpGame() {
		ruleset.initGame();
		ruleset.addPlayerToGame(player1);
		ruleset.addPlayerToGame(player2);
		ruleset.addPlayerToGame(player3);
		
		ruleset.setFirstPlayer(ruleset.getPlayerState(player1));
		ruleset.setTrumpCard(WizardCard.VierRot);
		
		ruleset.giveACard(player1, WizardCard.DreiGruen);
		ruleset.giveACard(player1, WizardCard.ZaubererRot);
		ruleset.givaACard(player1, WizardCard.ZweiBlau);
		
		ruleset.giveACard(player2, WizardCard.ZweiGruen);
		ruleset.giveACard(player2, WizardCard.DreiRot);
		ruleset.givaACard(player2, WizardCard.ZweiGelb);
		
		ruleset.giveACard(player3, WizardCard.NarrBlau);
		ruleset.giveACard(player3, WizardCard.EinsGruen);
		ruleset.giveACard(player3, WIzardCard.ZweiRot);
		
	}
	
	public void testSorcerer() {
		ruleset.playCard(WizardCard.ZaubererRot);
		ruleset.setCurrentPlayer(ruleset.getPlayerState(player2));
		
		boolean isValidMove = ruleset.isValidMove(WizardCard.DreiRot);
		
		assertTrue(isValidMove);
	}
	
	@Test
	public void testRed3OnGreen3() {
		ruleset.playCard(WizardCard.DreiGruen);
		ruleset.setCurrentPlayer(ruleset.getPlayerState(player2));
		boolean isValidMove = ruleset.isValidMove(WizardCard.DreiRot);
		
		assertFalse(isValidMove);
	}
	
	@Test
	public void testGreen2OnGreen3) {
		ruleset.playCard(WizardCard.DreiGruen);
		ruleset.setCurrentPlayer(ruleset.getPlayerState(player2));
		
		boolean isValidMove = ruleset.isValidMove(WizardCard.ZweiGruen);
		
		assertTrue(isValidMove);
	}
	
	@Test
	public void testFoolBlueOnGreen2OnGreen3() {
		ruleset.playCard(WizardCard.DreiGruen);
		ruleset.setCurrentPlayer(ruleset.getPlayerState(player2));
		
		ruleset.playCard(WizardCard.ZweiGruen);
		ruleset.setCurrentPlayer(ruleset.getPlayerState(player3));
		
		boolean isValidMove = ruleset.isValidMove(WizardCard.NarrBlau);
		
		assertTrue(isValidMove);
	}

}
