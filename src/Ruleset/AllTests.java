package Ruleset;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestClientHeartsAreValidChoosenCards.class, TestClientHeartsIsValidMove.class,
	
		TestClientWizardIsValidMove.class,
		TestClientWizardIsValidColour.class, TestClientWizardIsValidNumber.class,
       
        TestClientRulesetIllegalArguments.class,
        
        TestHeartsCalculateTricks.class, TestHeartsIsValidMove.class,
        TestHeartsIsValidMoveOnly.class, TestHeartsResolveMsgCard.class, TestHeartsRoundEnd.class,
        TestHeartsSwap.class, TestHeartsWinner.class, TestHeartsResolveMultiCard.class, 
     
        TestWizardCalculateTricks.class, TestWizardIsValidMove.class, 
        TestWizardResolveColour.class, TestWizardResolveMsgCard.class,
        TestWizardResolveMsgNumber.class, TestWizardRoundEnd.class, TestWizardStart.class})
 public class AllTests {
 }
