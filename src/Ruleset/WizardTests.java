package Ruleset;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestisValidMoveWizard.class, TestRoundEndWizard.class,
		TestStartWizard.class, TestWizardCalculateTricks.class,
		TestWizardResolveMsgCard.class, TestWizardResolveColour.class,
		TestWizardResolveMsgNumber.class})
public class WizardTests {

}
