package Ruleset;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestClientWizardIsValidColour.class,
		TestClientWizardIsValidMove.class, TestClientWizardIsValidNumber.class })
public class TestClientWizard {

}
