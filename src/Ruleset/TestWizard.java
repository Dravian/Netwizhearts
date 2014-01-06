package Ruleset;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestWizardIsValidMove.class, TestWizardRoundEnd.class,
		TestWizardStart.class, TestWizardCalculateTricks.class,
		TestWizardResolveMsgCard.class, TestWizardResolveColour.class,
		TestWizardResolveMsgNumber.class})
public class TestWizard {

}
