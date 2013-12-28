package Server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GameServerTests.class, LobbyTest.class, LoginTest.class,
		LoginTestNegativ.class })
public class AllTests {

}
