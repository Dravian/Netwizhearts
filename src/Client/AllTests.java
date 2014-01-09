package Client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ClientInGameLobbyTest.class, ClientInGameWizardTest.class,
	    ClientInGameHeartsTest.class, Ruleset.ClientViewTestEnd.class,
		ClientInServerLobbyTest.class, ClientLoginTest.class,
		ClientModelChatTest.class })
public class AllTests {

}
