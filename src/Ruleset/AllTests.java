package Ruleset;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestClientHeartsIsValidMove.class, TestClientWizardIsValidMove.class,
		TestClientWizardIsValidColour.class, TestClientWizardIsValidNumber.class,
        TestHeartsCalculateTricks.class, TestHeartsIsValidMove.class,
        TestHeartsIsValidMoveOnly.class, TestHeartsResolveMsgCard.class, TestHeartsRoundEnd.class,
        TestHeartsSwap.class, TestHeartsWinner.class, TestisValidMoveHearts.class,
        TestisValidMoveOnlyHearts.class, TestisValidMoveWizard.class,
        TestRoundEndHearts.class, TestRoundEndWizard.class, TestStartWizard.class,
        TestWizardCalculateTricks.class, TestWizardIsValidMove.class,
        TestWizardResolveColour.class, TestWizardResolveMsgCard.class,
        TestWizardResolveMsgNumber.class, TestWizardRoundEnd.class, TestWizardStart.class})
 public class AllTests {
 }
